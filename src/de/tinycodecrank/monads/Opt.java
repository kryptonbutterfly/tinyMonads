package de.tinycodecrank.monads;

import java.util.Collections;
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

/**
 * This Class provides an alternative to null that doesn't rely on runtime null
 * checking, but instead leverages javas type system, to ensure validity
 * 
 * @author christian
 * @param <T>
 */
public abstract class Opt<T> implements AutoCloseable, Iterable<T>
{
	private static final OptEmpty<?> EMPTY = new OptEmpty<>();
	
	/**
	 * @param value
	 *            The value to wrap
	 * @return An {@code Opt} containing the value or an empty {@code Opt} if value
	 *         is {@code null}
	 */
	public static <T> Opt<T> of(T value)
	{
		if (value != null)
		{
			return new OptContent<>(value);
		}
		else
		{
			return empty();
		}
	}
	
	/*
	 * @return An empty instance of Opt
	 */
	@SuppressWarnings("unchecked")
	public static <V> Opt<V> empty()
	{
		return (Opt<V>) EMPTY;
	}
	
	public static <T> Opt<T> convert(Optional<T> opt)
	{
		return of(opt.orElse(null));
	}
	
	private Opt()
	{}
	
	/**
	 * @param bind
	 * @return An {@cod Opt} containing the result of the given function or
	 *         {@code this} if {@code this} is empty
	 */
	public abstract <R> Opt<R> map(Function<T, R> bind);
	
	/**
	 * @param bind
	 * @return An {@code Opt} containing the result of the given function or
	 *         {@code this} if {@code this} is empty
	 * @throws Err
	 */
	public abstract <R, Err extends Throwable> Opt<R> mapThrows(FunctionThrowing<T, R, Err> bind) throws Err;
	
	/**
	 * @param filter
	 * @return An empty {@code Opt} if {@code this} is empty or the filter evaluates
	 *         to {@code false} else returns {@code this}
	 */
	public abstract Opt<T> filter(Predicate<T> filter);
	
	/**
	 * @param bind
	 * @return The {@code Opt} supplied by the given function or {@code this} if
	 *         {@code this} is empty
	 */
	public abstract <R> Opt<R> flatmap(Function<T, Opt<R>> bind);
	
	/**
	 * @param function
	 * @return The {@code Opt} supplied by the given function or {@code this} if
	 *         {@code this} is empty
	 * @throws Err
	 */
	public abstract <R, Err extends Throwable> Opt<R> flatmapThrows(FunctionThrowing<T, Opt<R>, Err> function)
		throws Err;
	
	/**
	 * @param function
	 * @return The {@code OptInt} supplied by the given function or
	 *         {@code OptInt#empty()} if {@code this} is empty
	 */
	public abstract OptInt flatmapInt(Function<T, OptInt> function);
	
	/**
	 * @return {@code true} if a value is present else {@code false}
	 */
	public abstract boolean isPresent();
	
	/**
	 * @deprecated The use of this method is discouraged.
	 * @return the contained value if present, else throws a
	 *         {@code NoSuchElementException}
	 * @throws NoSuchElementException
	 */
	@Deprecated
	public abstract T get() throws NoSuchElementException;
	
	/**
	 * @param fallback
	 * @return the contained value or the supplied fallback value
	 */
	public abstract T get(Supplier<T> fallback);
	
	/**
	 * @deprecated Did you mean to call {@linkplain #get()}?</br>
	 *             The use of this method is strongly discouraged.</br>
	 *             After this method call all guarantees made by this class are
	 *             invalid.</br>
	 *             Only use this method if unavoidable!
	 * @return The contained value or {@code null} if {@code this} {@code Opt} is
	 *         empty
	 */
	@Deprecated
	public T getIgnore()
	{
		return get(() -> null);
	}
	
	/**
	 * @param fallback
	 * @return the contained value or the supplied fallback value
	 * @throws Err
	 */
	public abstract <Err extends Throwable> T getThrows(SupplierThrowing<T, Err> fallback) throws Err;
	
	/**
	 * @param fallback
	 * @return {@code this} if present, else an {@code Opt} containing the supplied
	 *         value
	 */
	public abstract Opt<T> or(Supplier<T> fallback);
	
	/**
	 * @param fallback
	 * @return {@code this} if present, else an {@code Opt} containing the supplied
	 *         value
	 * @throws Err
	 */
	public abstract <Err extends Throwable> Opt<T> orThrows(SupplierThrowing<T, Err> fallback) throws Err;
	
	/**
	 * @param fallback
	 * @return {@code this} if present, else the supplied {@code Opt}
	 */
	public abstract Opt<T> flatOr(Supplier<Opt<T>> fallback);
	
	/**
	 * Consumes {@code this} content if present, else skips.
	 * 
	 * @param use
	 *            The function consuming the value if present.
	 * @return this
	 */
	public abstract Opt<T> if_(Consumer<T> use);
	
	/**
	 * Consumes {@code this} content if present, else skips.
	 * 
	 * @param use
	 *            The function consuming the value if present.
	 * @return this
	 * @throws Err
	 */
	public abstract <Err extends Throwable> Opt<T> if_Throws(ConsumerThrowing<T, Err> use) throws Err;
	
	/**
	 * is run if {@code this} is empty
	 * 
	 * @param alternative
	 * @return this
	 */
	public abstract Opt<T> else_(Runnable alternative);
	
	/**
	 * is run if {@code this} is empty
	 * 
	 * @param empty
	 * @return this
	 * @throws Err
	 */
	public abstract <Err extends Throwable> Opt<T> else_Throws(RunnableThrowing<Err> empty) throws Err;
	
	/**
	 * @return The equivalent {@link Optional} of this Opt.
	 */
	public abstract Optional<T> toOptional();
	
	/**
	 * Returns the hash code value of the present value or 0 if no value is present.
	 * 
	 * @return hash code value of the present value or 0 if no value is present
	 */
	@Override
	public abstract int hashCode();
	
	/**
	 * Indicates whether some other object is "equal to" this {@code Opt}. The other
	 * object is considered equal if:
	 * <ul>
	 * <li>it is also an {@code Opt} and;
	 * <li>both instances are empty or;
	 * <li>both instances contain content and;
	 * <li>the present values are "equal to" each other via {@code equals()}.
	 * </ul>
	 *
	 * @param obj
	 *            an object to be tested for equality
	 * @return {@code true} if the other object is "equal to" this object otherwise
	 *         {@code false}
	 */
	@Override
	public abstract boolean equals(Object obj);
	
	/**
	 * Returns {@code Opt.empty} if empty,</br>
	 * else returns the string representation of the contained value wrapped in
	 * {@code Opt[…]}
	 * 
	 * @return The string representation of this instance
	 */
	@Override
	public abstract String toString();
	
	@Override
	public abstract Iterator<T> iterator();
	
	public abstract Stream<T> stream();
	
	private static final class OptContent<T> extends Opt<T>
	{
		private final T content;
		
		private OptContent(T value)
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
				return empty();
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
			if (content.getClass().isAssignableFrom(AutoCloseable.class))
			{
				((AutoCloseable) content).close();
			}
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
	
	private static final class OptEmpty<T> extends Opt<T>
	{
		private OptEmpty()
		{}
		
		@Override
		public <R> Opt<R> map(Function<T, R> bind)
		{
			return empty();
		}
		
		@Override
		public <R, Err extends Throwable> Opt<R> mapThrows(FunctionThrowing<T, R, Err> bind) throws Err
		{
			return empty();
		}
		
		@Override
		public <R> Opt<R> flatmap(Function<T, Opt<R>> bind)
		{
			return empty();
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
			return empty();
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
}