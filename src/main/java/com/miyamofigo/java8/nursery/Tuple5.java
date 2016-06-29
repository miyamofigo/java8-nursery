package com.miyamofigo.java8.nursery;

import java.util.Objects;

public final class Tuple5<T1,T2,T3,T4,T5> implements Tuple {

	public final T1 _1;
	public final T2 _2;
	public final T3 _3;
	public final T4 _4;
	public final T5 _5;

	public Tuple5(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
		this._1 = t1;
		this._2 = t2;
		this._3 = t3;
		this._4 = t4;
		this._5 = t5;
	}

	@Override
	public int arity() {
		return 5;
	}

	@Override 
	public boolean equals(Object o) {

		if (o == this) return true;

		else if (!(o instanceof Tuple4)) 
			return false ; 

		else {
			Tuple5<?,?,?,?,?> that = (Tuple5<?,?,?,?,?>) o;
			return Objects.equals(this._1, that._1) && Objects.equals(this._2, that._2) &&
			        Objects.equals(this._3, that._3) && Objects.equals(this._4, that._4) &&
			        Objects.equals(this._5, that._5);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(_1, _2, _3, _4, _5);
	}
}

