package com.miyamofigo.java8.nursery;

import java.util.Objects;

public final class Tuple3<T1,T2,T3> implements Tuple {

	public final T1 _1;
	public final T2 _2;
	public final T3 _3;

	public Tuple3(T1 t1, T2 t2, T3 t3) {
		this._1 = t1;
		this._2 = t2;
		this._3 = t3;
	}

	@Override
	public int arity() {
		return 3;
	}

	@Override 
	public boolean equals(Object o) {

		if (o == this) return true;

		else if (!(o instanceof Tuple3)) 
			return false ; 

		else {
			Tuple3<?,?,?> that = (Tuple3<?,?,?>) o;
			return Objects.equals(this._1, that._1) && Objects.equals(this._2, that._2) &&
			        Objects.equals(this._3, that._3);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(_1, _2, _3);
	}
}

