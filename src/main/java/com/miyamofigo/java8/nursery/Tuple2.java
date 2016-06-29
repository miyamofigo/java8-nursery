package com.miyamofigo.java8.nursery;

import java.util.Objects;

public final class Tuple2<T1,T2> implements Tuple {

	public final T1 _1;
	public final T2 _2;

	public Tuple2(T1 t1, T2 t2) {
		this._1 = t1;
		this._2 = t2;
	}

	@Override
	public int arity() {
		return 2;
	}

	@Override 
	public boolean equals(Object o) {

		if (o == this) return true;

		else if (!(o instanceof Tuple2)) 
			return false ; 

		else {
			Tuple2<?,?> that = (Tuple2<?,?>) o;
			return Objects.equals(this._1, that._1) && Objects.equals(this._2, that._2);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(_1, _2);
	}
}

