package com.miyamofigo.java8.nursery;

import java.util.function.Function;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public final class Result<T,E> {

  // I gotta determine whether these fields should be implemeted like a union or not..
  private final Optional<T> value;
  private final Optional<E> reason;

  private final boolean _isOk;

  private Result(T value, E reason, boolean isOk) { 
    this.value = isOk ? Optional.of(value) : Optional.empty();
    this.reason = isOk ? Optional.empty() : Optional.of(reason);
    this._isOk = isOk;  
  } 

  public static <T,E> Result<T,E> ok(T value) { 
    Objects.requireNonNull(value);
    return new Result<T,E>(value, null, true); 
  } 

  public static <T,E> Result<T,E> err(E reason) { 
    Objects.requireNonNull(reason);
    return new Result<T,E>(null, reason, false); 
  } 

  public boolean isOk() { return _isOk; }

  public boolean isErr() { return !_isOk; }

  public Optional<T> ok() { 
    if (isOk()) return value; else return Optional.empty(); 
  }

  public Optional<E> err() {  
    if (isErr()) return reason; else return Optional.empty();
  }

  public Result<?,E> map(Function<T,?> mapper) {
    Objects.requireNonNull(mapper);
    try { return Result.ok(mapper.apply(ok().get())); } catch (NoSuchElementException e) { return this; } 
  }

  public Result<T,?> mapErr(Function<E,?> mapper) {
    Objects.requireNonNull(mapper);
    try { return Result.err(mapper.apply(err().get())); } catch (NoSuchElementException e) { return this; } 
  }

  public Result<T,E> and(Result<T,E> res) { if (isOk()) return res; else return this; }

  public Result<?,E> andThen(Function<T, Result<?,E>> op) {
    Objects.requireNonNull(op);
    try { return op.apply((ok().get())); } catch (NoSuchElementException e) { return this; }
  } 

  public Result<T,E> or(Result<T,E> res) { if (isErr()) return res; else return this; }

  public Result<T,?> orElse(Function<E, Result<T,?>> op) {
    Objects.requireNonNull(op);
    try { return op.apply((err().get())); } catch (NoSuchElementException e) { return this; }
  }

  public T unwrapOr(T optb) {
    try { return ok().get(); } catch (NoSuchElementException e) { return optb; }
  }

  public T unwrapOrElse(Function<E,T> op) {
    Objects.requireNonNull(op);
    try { return ok().get(); } catch (NoSuchElementException e) { return op.apply((err().get())); }
  }

  public T unwrap() throws Exception {
    try { return ok().get(); } 
    catch (NoSuchElementException e) { 
      throw new Exception("failed to unwrap on an `error` value: " + reason.get().toString()); 
    }
  }

  public T expect(String msg) throws Exception {
    try { return ok().get(); } 
    catch (NoSuchElementException e) { throw new Exception(msg + reason.get().toString()); }
  }

  public E unwrapErr() throws Exception {
    try { return err().get(); } 
    catch (NoSuchElementException e) { 
      throw new Exception("failed to unwrap on an `ok` reason: " + value.get().toString());
    }
  } 

}
