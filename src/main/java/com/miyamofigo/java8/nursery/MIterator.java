package com.miyamofigo.java8.nursery;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

public interface MIterator<E> extends Iterator<Optional<E>> {

  default boolean hasNext() { throw new UnsupportedOperationException(); }  

  default <U> MMap<E,U> map(Function<? super E,? extends U> mapper) { 
    Objects.requireNonNull(mapper);
    return new MMap<>(this, mapper); 
  }

  default MFilter<E> filter(Predicate<? super E> predicate) {
    Objects.requireNonNull(predicate);
    return new MFilter<>(this, predicate);
  }

  default <R,A> R collect(Collector<? super E,A,R> collector) {
    A init = collector.supplier().get();
    BiConsumer<A,? super E> accumulator = collector.accumulator(); 

    Optional<E> current = next();
    while (current.isPresent()) { 
      accumulator.accept(init, current.get());
      current = next();
    } 
    return collector.finisher().apply(init); 
  } 

  class MMap<E,U> implements MIterator<U> {

    private MIterator<E> iter;
    private Function<? super E,? extends U> mapper; 

    public MMap(MIterator<E> iter, Function<? super E,? extends U> mapper) { 
      this.iter = iter; 
      this.mapper = mapper; 
    }     
  
    public Optional<U> next() { return iter.next().map(mapper); }  
  }

  class MFilter<E> implements MIterator<E> {

    private MIterator<E> iter;
    private Predicate<? super E> predicate;
    
    public MFilter(MIterator<E> iter, Predicate<? super E> predicate) {
      this.iter = iter;
      this.predicate = predicate;
    }

    public Optional<E> next() {
      Optional<E> current = iter.next();
      if (!current.isPresent() || current.filter(predicate).isPresent()) return current;
      else return next();
    }
  }

  default <R> R rewrap(Function<? super E,R> wrapper) { return wrapper.apply(next().get()); }
}
