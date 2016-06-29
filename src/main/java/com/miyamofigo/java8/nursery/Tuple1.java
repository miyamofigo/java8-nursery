package com.miyamofigo.java8.nursery;

import java.util.Objects;

public final class Tuple1<T1> implements Tuple {

	public final T1 _1;

	public Tuple1(T1 t1) {
		this._1 = t1;
	}

	@Override
	public int arity() {
		return 1;
	}

	@Override 
	public boolean equals(Object o) {
		return o == this ? 
						 true : 
						 ( !(o instanceof Tuple1) ? 
						   false : 
							 Objects.equals(this._1, ((Tuple1<?>) o)._1) );
	}

	@Override
	public int hashCode() {
		return Objects.hash(_1);
	}
}

