package com.miyamofigo.java8.nursery;

public final class Tuple0 implements Tuple {

	private static final Tuple0 INSTANCE = new Tuple0();

	private Tuple0() {}
	
	@Override 
	public int arity() {
		return 0;
	}

	@Override 
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		return o == this;
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override 
	public String toString() {
		return "()";
	}

	public static Tuple0 instance() {
		return INSTANCE;
	}
}
