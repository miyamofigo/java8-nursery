package com.miyamofigo.java8.nursery;

public interface Tuple {

	int arity();

	default boolean isEmpty() {
		return false;
	}

	static Tuple0 empty() {
		return Tuple0.instance();
	}

	static <T> Tuple1<T> of(T t) {
		return new Tuple1<>(t);
	}

	static <T1,T2> Tuple2<T1,T2> of(T1 t1, T2 t2) {
		return new Tuple2<>(t1, t2);
	}

	static <T1,T2,T3> Tuple3<T1,T2,T3> of(T1 t1, T2 t2, T3 t3) {
		return new Tuple3<>(t1, t2, t3);
	}

	static <T1,T2,T3,T4> Tuple4<T1,T2,T3,T4> of(T1 t1, T2 t2, T3 t3, T4 t4) {
		return new Tuple4<>(t1, t2, t3, t4);
	}

	static <T1,T2,T3,T4,T5> Tuple5<T1,T2,T3,T4,T5> of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
		return new Tuple5<>(t1, t2, t3, t4, t5);
	}
}
