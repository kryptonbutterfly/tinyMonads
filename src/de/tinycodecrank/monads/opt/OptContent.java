package de.tinycodecrank.monads.opt;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import de.tinycodecrank.functions.throwing.ConsumerThrowing;
import de.tinycodecrank.functions.throwing.FunctionThrowing;
import de.tinycodecrank.functions.throwing.RunnableThrowing;
import de.tinycodecrank.functions.throwing.SupplierThrowing;
import de.tinycodecrank.iterator.SingleElementIterator;
import de.tinycodecrank.monads.OptInt;

final class OptContent<T> implements Opt<T>
{
	private final T content;
	
	OptContent(T value)
	{
		this.content = value;
	}
	
	@Override
	public <R> Opt<R> map(Function<T, R> bind)
	{
		return Opt.of(bind.apply(content));
	}
	
	@Override
	public <R, Err extends Throwable> Opt<R> mapThrows(FunctionThrowing<T, R, Err> bind) throws Err
	{
		return Opt.of(bind.apply(content));
	}
	
	@Override
	public <R> Opt<R> flatmap(Function<T, Opt<R>> bind)
	{
		return bind.apply(content);
	}
	
	@Override
	public <R, Err extends Throwable> Opt<R> flatmapThrows(FunctionThrowing<T, Opt<R>, Err> function)
		throws Err
	{
		return function.apply(content);
	}
	
	@Override
	public Opt<T> flatOr(Supplier<Opt<T>> fallback)
	{
		return this;
	}
	
	@Override
	public OptInt flatmapInt(Function<T, OptInt> function)
	{
		return function.apply(content);
	}
	
	@Override
	public Opt<T> filter(Predicate<T> filter)
	{
		if (filter.test(content))
		{
			return this;
		}
		else
		{
			return Opt.empty();
		}
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return new SingleElementIterator<>(content);
	}
	
	@Override
	public boolean isPresent()
	{
		return true;
	}
	
	@Override
	public T get() throws NoSuchElementException
	{
		return content;
	}
	
	@Override
	public String toString()
	{
		return "Opt(" + content + ")";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (Objects.isNull(obj))
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		OptContent<?> other = (OptContent<?>) obj;
		return content.equals(other.content);
	}
	
	@Override
	public T get(Supplier<T> fallback)
	{
		return this.content;
	}
	
	@Override
	public <Err extends Throwable> T getThrows(SupplierThrowing<T, Err> fallback) throws Err
	{
		return this.content;
	}
	
	@Override
	public <Err extends Throwable> T getThrows(Supplier<Err> errorSupplier) throws Err
	{
		return this.content;
	}
	
	@Override
	public Opt<T> or(Supplier<T> alternative)
	{
		return this;
	}
	
	@Override
	public <Err extends Throwable> Opt<T> orThrows(SupplierThrowing<T, Err> fallback) throws Err
	{
		return this;
	}
	
	@Override
	public Opt<T> if_(Consumer<T> use)
	{
		use.accept(content);
		return this;
	}
	
	@Override
	public <Err extends Throwable> Opt<T> if_Throws(ConsumerThrowing<T, Err> use) throws Err
	{
		use.accept(content);
		return this;
	}
	
	@Override
	public Opt<T> else_(Runnable alternative)
	{
		return this;
	}
	
	@Override
	public <Err extends Throwable> Opt<T> else_Throws(RunnableThrowing<Err> empty) throws Err
	{
		return this;
	}
	
	@Override
	public Optional<T> toOptional()
	{
		return Optional.of(content);
	}
	
	@Override
	public void close() throws Exception
	{
		if (content instanceof AutoCloseable auto)
			auto.close();
	}
	
	@Override
	public int hashCode()
	{
		final int	prime	= 31;
		int			result	= 1;
		result = prime * result + content.hashCode();
		return result;
	}
	
	@Override
	public Stream<T> stream()
	{
		return Stream.of(this.content);
	}
}