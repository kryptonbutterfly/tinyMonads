package de.tinycodecrank.monads.opt;

import java.util.Iterator;
import java.util.NoSuchElementException;
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
import de.tinycodecrank.monads.OptInt;

/**
 * This Class provides an alternative to null that doesn't rely on runtime null
 * checking, but instead leverages javas type system, to ensure validity
 * 
 * @author tinycodecrank
 * @param <T>
 */
public sealed interface Opt<T> extends AutoCloseable, Iterable<T>permits OptContent<T>, OptEmpty<T>
{
	static final OptEmpty<?> EMPTY = new OptEmpty<>();
	
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
			return new OptContent<T>(value);
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
	public default T getIgnore()
	{
		return get(() -> null);
	}
	
	/**
	 * @deprecated Use {@link Opt#getThrows(Supplier)} instead!
	 * @param fallback
	 * @return the contained value or the supplied fallback value
	 * @throws Err
	 */
	@Deprecated(forRemoval = true)
	public abstract <Err extends Throwable> T getThrows(SupplierThrowing<T, Err> fallback) throws Err;
	
	/**
	 * @param <Err>
	 * @param errorSupplier
	 * @return
	 * @throws Err
	 */
	public abstract <Err extends Throwable> T getThrows(Supplier<Err> errorSupplier) throws Err;
	
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
}