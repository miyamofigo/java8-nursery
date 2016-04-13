package com.miyamofigo.java8.nursery;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;

public class ResultTest {
  @Test 
  public void testOk() { 
		Result<Integer, String> ok = Result.ok(1);
    assert(ok.isOk()); 
    assert(ok.ok().get() == 1);
    assert(!ok.isErr());
    assertEquals((long) 2, (long) ok.map(i -> i+1).ok().get()); 
    assertEquals("one", ok.map(i -> "one").ok().get()); 
    assert(ok.mapErr(str -> "error").ok().get() == 1);
    assert(ok.and(Result.ok(2)).ok().get() == 2);
    assertEquals((Integer) 2, ok.andThen(i -> i+1).ok().get()); 
    assertEquals(ok, ok.or(Result.ok(2)));
		assertEquals(ok, ok.orElse(str -> 3));
    assert(ok.unwrapOr(2) == 1);
    assertEquals((Integer) 1, ok.unwrapOrElse(str -> 3));
    try { 
      assert(ok.unwrap() == 1); 
      assert(ok.expect("SUCCESS") == 1);
      ok.unwrapErr();
      fail();
    } catch (Exception e) {}  
  }

  @Test
  public void testErr() {
    Result<Integer, String> err = Result.err("test");
    assert(!err.isOk());
		try { err.ok(); fail(); } catch (NoSuchElementException e) {}
    assert(err.isErr());
    assertEquals("test", err.err().get());
    assert(err.map(i -> i+1).isErr());
    assertEquals("error", err.mapErr(str -> "error").err().get());
    assertEquals((Integer) 1, err.mapErr(str -> 1).err().get());
    assert(err.and(Result.ok(1)).isErr());
    assert(err.andThen(i -> i+1).isErr());
    assert(err.or(Result.ok(2)).isOk());
    assertEquals(Integer.valueOf(3), err.orElse(str -> 3).ok().get());
    assert(err.unwrapOr(2) == 2);
    assertEquals((int) 4, (int) err.unwrapOrElse(str -> str.length()));
    try { err.unwrap(); fail(); } catch (NoSuchElementException e) {} 
    try { err.expect("SUCCESS: "); fail(); } 
		catch (NoSuchElementException e) { assertEquals("SUCCESS: test", e.getMessage()); }
    try { assertEquals("test", err.unwrapErr()); } catch (NoSuchElementException e) { fail(); } 
  }

	@Test
	public void testProjection() {
		MIterator<Integer> it0 = Result.ok(1).iter();
		assertEquals((Integer) 1, it0.next().get()); 
		assert(!it0.next().isPresent());

		MIterator<Integer> it1 = Result.ok(1).iter();
		it1.remove();	
		assert(!it1.next().isPresent());	

		MIterator<Integer> it2 = Result.ok(1).iter();
		MIterator.MMap<Integer,Integer> m1 = it2.map(i -> i*3);
		assertEquals((Integer) 3, m1.next().get());
		assert(!m1.next().isPresent());

		MIterator<Integer> it3 = Result.ok(1).iter();
		assertEquals("hello", it3.map(i -> "hello").next().get());

		MIterator<Integer> it4 = Result.ok(1).iter();
		assertEquals((Integer) 1, it4.filter(i -> i == 1).next().get());

		MIterator<Integer> it5 = Result.ok(1).iter();
		assert(!it5.filter(i -> i != 1).next().isPresent());

		MIterator<Integer> it6 = Result.ok(1).iter();
		assertEquals((Integer) 2, it6.filter(i -> i == 1).map(i -> i*2).next().get()); 

		MIterator<Integer> it7 = Result.ok(1).iter();
		assertEquals("hello", it7.filter(i -> i == 1).map(i -> "hello").next().get()); 

		assertEquals((Integer) 2, Result.ok(1).iter().map(i -> i*2).collect(Collectors.toList()).get(0));	
		assertEquals("hello", Result.ok(1).iter().map(i -> "hello").rewrap(str -> Result.ok(str)).unwrap()); 
	}	
}
