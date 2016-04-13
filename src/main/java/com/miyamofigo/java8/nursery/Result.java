package com.miyamofigo.java8.nursery;

import java.util.function.Function;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

<<<<<<< HEAD
public final class Result<T,E> {
=======
public interface Result<T,E> {
>>>>>>> dev

  static <T,E> Result<T,E> ok(T val) { return new Ok<>(val); }
  static <T,E> Result<T,E> err(E val) { return new Err<>(val); }

  boolean isOk();
  boolean isErr();

  Optional<T> ok(); 
  Optional<E> err();

  @SuppressWarnings("unchecked")
  default <U> Result<U,E> map(Function<? super T,? extends U> mapper) {
    Objects.requireNonNull(mapper);
    if (isOk()) return Result.ok(mapper.apply(ok().get())); else return (Result<U,E>) this;
  }

  @SuppressWarnings("unchecked")
  default <F> Result<T,F> mapErr(Function<? super E,? extends F> mapper) { 
    Objects.requireNonNull(mapper);
    if (isErr()) return Result.err(mapper.apply(err().get())); else return (Result<T,F>) this;
  }

  default Iter<T> iter() { return new Iter<>(ok().get()); } 

  class Iter<T> implements MIterator<T> {

    private Optional<T> inner; 
    
    Iter(T val) { inner = Optional.of(val); }

    public Optional<T> next() { Optional<T> res = inner; remove(); return res; }
  
    public void remove() { inner = Optional.empty(); }
  }

  default Result<T,E> and(Result<T,E> res) { return isOk() ? res : this; } 

  @SuppressWarnings("unchecked")
  default <U> Result<U,E> andThen(Function<? super T,? extends U> op) {
    return isOk() ? Result.ok(op.apply(ok().get())) : (Result<U,E>) this;
  }

  default Result<T,E> or(Result<T,E> res) { return isErr() ? res : this; }

  @SuppressWarnings("unchecked")
  default Result<T,E> orElse(Function<? super E,? extends T> op) {
    return isErr() ? Result.ok(op.apply(err().get())) : (Result<T,E>) this;
  }   

<<<<<<< HEAD
  public Result<T,E> or(Result<T,E> res) { if (isErr()) return res; else return this; }
=======
  default T unwrapOr(T optb) { return isOk() ? ok().get() : optb; }
>>>>>>> dev

  default T unwrapOrElse(Function<? super E,T> op) { return isOk() ? ok().get() : op.apply(err().get()); }

  default T unwrap() throws NoSuchElementException { return ok().get(); }

  default E unwrapErr() throws NoSuchElementException { return err().get(); } 

  default T expect(String msg) { 
    try { return ok().get(); } catch (NoSuchElementException e) {
      throw new NoSuchElementException(msg + err().get().toString());
    }
  }

  final class Ok<T,E> implements Result<T,E> {

    private final T value; 
    
    private Ok(T value) {
      Objects.requireNonNull(value);
      this.value = value;
    }

    @Override 
    public boolean isOk() { return true; } 

    @Override 
    public boolean isErr() { return false; } 

    @Override 
    public Optional<T> ok() { return Optional.of(value); }  

    @Override 
    public Optional<E> err() { throw new NoSuchElementException("err() on Ok"); }
  }

  final class Err<T,E> implements Result<T,E> {

    private final E value; 
    
    private Err(E value) {
      Objects.requireNonNull(value);
      this.value = value;
    }

    @Override 
    public boolean isOk() { return false; } 

    @Override 
    public boolean isErr() { return true; } 

    @Override 
    public Optional<T> ok() { throw new NoSuchElementException("ok() on Err"); }

    @Override 
    public Optional<E> err() { return Optional.of(value); }   
  }
} 

