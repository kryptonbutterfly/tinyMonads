package kryptonbutterfly.monads.opt;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import kryptonbutterfly.functions.throwing.ConsumerThrowing;
import kryptonbutterfly.functions.throwing.FunctionThrowing;
import kryptonbutterfly.functions.throwing.RunnableThrowing;
import kryptonbutterfly.functions.throwing.SupplierThrowing;
import kryptonbutterfly.monads.OptInt;

final class OptEmpty<T> implements Opt<T>
{
	OptEmpty()
	{}
	
	@Override
	public <R> Opt<R> map(Function<T, R> bind)
	{
		return Opt.empty();
	}
	
	@Override
	public <R, Err extends Throwable> Opt<R> mapThrows(FunctionThrowing<T, R, Err> bind) throws Err
	{
		return Opt.empty();
	}
	
	@Override
	public <R> Opt<R> flatmap(Function<T, Opt<R>> bind)
	{
		return Opt.empty();
	}
	
	@Override
	public <R, Err extends Throwable> Opt<R> flatmapThrows(FunctionThrowing<T, Opt<R>, Err> function)
		throws Err
	{
		return Opt.empty();
	}
	
	@Override
	public Opt<T> flatOr(Supplier<Opt<T>> fallback)
	{
		return fallback.get();
	}
	
	@Override
	public OptInt flatmapInt(Function<T, OptInt> function)
	{
		return OptInt.empty();
	}
	
	@Override
	public Opt<T> filter(Predicate<T> filter)
	{
		return Opt.empty();
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return Collections.emptyIterator();
	}
	
	@Override
	public boolean isPresent()
	{
		return false;
	}
	
	@Override
	public T get() throws NoSuchElementException
	{
		throw new NoSuchElementException();
	}
	
	@Override
	public <Err extends Throwable> T getThrows(SupplierThrowing<T, Err> fallback) throws Err
	{
		return fallback.get();
	}
	
	@Override
	public <Err extends Throwable> T getThrows(Supplier<Err> errorSupplier) throws Err
	{
		throw errorSupplier.get();
	}
	
	@Override
	public String toString()
	{
		return "Opt.empty";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return this == obj;
	}
	
	@Override
	public T get(Supplier<T> fallback)
	{
		return fallback.get();
	}
	
	@Override
	public Opt<T> or(Supplier<T> fallback)
	{
		return Opt.of(fallback.get());
	}
	
	@Override
	public <Err extends Throwable> Opt<T> orThrows(SupplierThrowing<T, Err> fallback) throws Err
	{
		return Opt.of(fallback.get());
	}
	
	@Override
	public Opt<T> if_(Consumer<T> use)
	{
		return this;
	}
	
	@Override
	public <Err extends Throwable> Opt<T> if_Throws(ConsumerThrowing<T, Err> use) throws Err
	{
		return this;
	}
	
	@Override
	public Opt<T> else_(Runnable alternative)
	{
		alternative.run();
		return this;
	}
	
	@Override
	public <Err extends Throwable> Opt<T> else_Throws(RunnableThrowing<Err> empty) throws Err
	{
		empty.run();
		return this;
	}
	
	@Override
	public Optional<T> toOptional()
	{
		return Optional.empty();
	}
	
	@Override
	public void close() throws Exception
	{}
	
	@Override
	public int hashCode()
	{
		return 0;
	}
	
	@Override
	public Stream<T> stream()
	{
		return Stream.empty();
	}
}