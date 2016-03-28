package com.miyamofigo.java8.nursery;

import org.junit.Test;
import static org.junit.Assert.*;

public class ResultTest {
  @Test 
  public void testOk() { 
    Result<Integer, Exception> ok = Result.ok(1); 
    assert(ok.isOk());
    assert(ok.ok().get() == 1); 
    assert(!ok.isErr());
    assert(!ok.err().isPresent());
    assertEquals(2, ok.map(i -> i+1).ok().get()); 
    assertEquals("one", ok.map(i -> "one").ok().get()); 
    assert(ok.mapErr(str -> "error").ok().get() == 1);
    assert(ok.and(Result.ok(2)).ok().get() == 2);
    assertEquals((Integer) 2, ok.andThen(i -> Result.ok(i+1)).ok().get()); 
    assertEquals(ok, ok.or(Result.ok(2)));
    assertEquals(ok, ok.orElse(e -> { 
                       if (e instanceof NullPointerException) return Result.ok(2);
                       else return Result.ok(3);
                     }));
    assert(ok.unwrapOr(2) == 1);
    assertEquals((Integer) 1, ok.unwrapOrElse(e -> 3));
    try { 
      assert(ok.unwrap() == 1); 
      assert(ok.expect("SUCCESS") == 1);
      ok.unwrapErr();
      fail();
    } catch (Exception e) {}  
  }

  @Test
  public void testErr() {
    Result<Integer, Exception> err = Result.err(new Exception("test"));
    assert(!err.isOk());
    assert(!err.ok().isPresent());
    assert(err.isErr());
    assertEquals("test", err.err().get().getMessage());
    assert(err.map(i -> i+1).isErr());
    assertEquals("test", err.mapErr(e -> e.getMessage()).err().get());
    assert(err.and(Result.ok(1)).isErr());
    assert(err.andThen(i -> Result.ok(i+1)).isErr());
    assert(err.or(Result.ok(2)).isOk());
    assertEquals(Integer.valueOf(3),
                 err.orElse(e -> {
                       if (e instanceof NullPointerException) return Result.ok(2);
                       else return Result.ok(3);
                     })
                    .ok().get());
    assert(err.unwrapOr(2) == 2);
    assertEquals((int) 4, (int) err.unwrapOrElse(e -> e.getMessage().length()));
    try { err.unwrap(); fail(); } 
    catch (Exception e) { 
      assertEquals("failed to unwrap on an `error` value: java.lang.Exception: test", 
                   e.getMessage()); 
    }
    try { err.expect("SUCCESS: "); fail(); } 
    catch (Exception e) { assertEquals("SUCCESS: java.lang.Exception: test", e.getMessage()); }
    try { assertEquals("test", err.unwrapErr().getMessage()); } catch (Exception e) {}
  }
}
