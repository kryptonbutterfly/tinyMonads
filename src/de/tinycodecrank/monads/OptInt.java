package de.tinycodecrank.monads;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

import de.tinycodecrank.functions.throwing.ConsumerThrowing;
import de.tinycodecrank.functions.throwing.FunctionThrowing;
import de.tinycodecrank.functions.throwing.RunnableThrowing;
import de.tinycodecrank.functions.throwing.SupplierThrowing;

public abstract class OptInt
{
	private static final Empty EMPTY = new Empty();
	
	/**
	 * @param value
	 * @return An {@code Opt} containing the value or an empty {@code Opt} if value
	 *         is {@code null}
	 */
	public static OptInt of(int value)
	{
		return new Content(value);
	}
	
	/**
	 * @return An instance of {@code OptionalEmpty}
	 */
	public static OptInt empty()
	{
		return EMPTY;
	}
	
	/**
	 * @return {@code true} if a value is present else {@code false}
	 */
	public abstract boolean isPresent();
	
	/**
	 * @return The contained value or {@code null} if {@code this} {@code Opt} is
	 *         empty
	 */
	public abstract Integer getIgnore();
	
	/**
	 * @param function
	 * @return An {@code Opt} containing the result of the given function or
	 *         {@code this} if {@code this} is empty
	 */
	public abstract <R> Opt<R> map(IntFunction<R> function);
	
	/**
	 * @param function
	 * @return An {@code OptInt} containing the resulting int or
	 *         {@code OptInt#empty()} if {@code this} is empty
	 */
	public abstract OptInt mapInt(IntUnaryOperator function);
	
	/**
	 * @param function
	 * @return An {@code Opt} containing the result of the given function or
	 *         {@code this} if {@code this} is empty
	 * @throws Err
	 */
	public abstract <R, Err extends Throwable> Opt<R> mapThrows(FunctionThrowing<Integer, R, Err> function) throws Err;
	
	/**
	 * @param function
	 * @return The {@code Opt} supplied by the given function or {@code this} if
	 *         {@code this} is empty
	 */
	public abstract <R> Opt<R> flatmap(IntFunction<Opt<R>> function);
	
	/**
	 * @param function
	 * @return The {@code Opt} supplied by the given function or {@code this} if
	 *         {@code this} is empty
	 * @throws Err
	 */
	public abstract <R, Err extends Throwable> Opt<R> flatmapThrows(FunctionThrowing<Integer, Opt<R>, Err> function)
		throws Err;
	
	/**
	 * @param fallback
	 * @return {@code this} if present, else an {@code Opt} containing the supplied
	 *         value
	 */
	public abstract OptInt or(IntSupplier fallback);
	
	/**
	 * @param fallback
	 * @return {@code this} if present, else the supplied {@code Opt}
	 */
	public abstract OptInt flatOr(Supplier<OptInt> fallback);
	
	/**
	 * @return the contained value if present else throws a
	 *         {@code NullPointerException}
	 */
	public abstract int get();
	
	/**
	 * @param fallback
	 * @return the contained value or the supplied fallback value
	 */
	public abstract int get(IntSupplier fallback);
	
	/**
	 * @param fallback
	 * @return the contained value or the supplied fallback value
	 * @throws Err
	 */
	public abstract <Err extends Throwable> int getThrows(SupplierThrowing<Integer, Err> fallback) throws Err;
	
	/**
	 * @param filter
	 * @return An empty {@code Opt} if {@code this} is empty or the filter evaluates
	 *         to {@code false} else returns {@code this}
	 */
	public abstract OptInt filter(IntPredicate filter);
	
	/**
	 * consume {@code this} content if present, else skip
	 * 
	 * @param use
	 * @return this
	 */
	public abstract OptInt if_(IntConsumer use);
	
	/**
	 * consume {@code this} content if present, else skip
	 * 
	 * @param use
	 * @return this
	 * @throws Err
	 */
	public abstract <Err extends Throwable> OptInt if_Throws(ConsumerThrowing<Integer, Err> use) throws Err;
	
	/**
	 * is run if {@code this} is empty
	 * 
	 * @param action
	 * @return this
	 */
	public abstract OptInt else_(Runnable action);
	
	/**
	 * is run if {@code this} is empty
	 * 
	 * @param empty
	 * @return this
	 * @throws Err
	 */
	public abstract <Err extends Throwable> OptInt else_Throws(RunnableThrowing<Err> empty) throws Err;
	
	/**
	 * is run if {@code this} is empty
	 * 
	 * @param use
	 * @param empty
	 * @return this
	 */
	public abstract OptInt if_else(IntConsumer use, Runnable empty);
	
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
	 * <li>both instances are {@code OptionalEmpty} or;
	 * <li>both instances are {@code OptionalContent} and;
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
	 * Returns {@code Opt.empty} if empty else returns the string representation of
	 * the contained value wrapped in {@code Opt[…]}
	 *
	 * @return the string representation of this instance
	 */
	@Override
	public abstract String toString();
	
	public abstract OptInt add(int value);
	
	public abstract OptInt add(OptInt value);
	
	public abstract OptInt sub(int value);
	
	public abstract OptInt sub(OptInt value);
	
	public abstract OptInt div(int value);
	
	public abstract OptInt div(OptInt value);
	
	public abstract OptInt mul(int value);
	
	public abstract OptInt mul(OptInt value);
	
	public abstract OptInt mod(int value);
	
	public abstract OptInt mod(OptInt value);
	
	public abstract OptInt shiftLeft(int value);
	
	public abstract OptInt shiftLeft(OptInt value);
	
	public abstract OptInt shiftRight(int value);
	
	public abstract OptInt shiftRight(OptInt value);
	
	public abstract OptInt shiftRightSigned(int value);
	
	public abstract OptInt shiftRightSigned(OptInt value);
	
	public abstract OptInt and(int value);
	
	public abstract OptInt and(OptInt value);
	
	public abstract OptInt or(int value);
	
	public abstract OptInt or(OptInt value);
	
	public abstract OptInt xor(int value);
	
	public abstract OptInt xor(OptInt value);
	
	public abstract OptInt komp2();
	
	private static final class Content extends OptInt
	{
		private final int content;
		
		Content(int value)
		{
			this.content = value;
		}
		
		@Override
		public boolean isPresent()
		{
			return true;
		}
		
		@Override
		public Integer getIgnore()
		{
			return content;
		}
		
		@Override
		public <R> Opt<R> map(IntFunction<R> function)
		{
			return Opt.of(function.apply(content));
		}
		
		@Override
		public OptInt mapInt(IntUnaryOperator function)
		{
			return OptInt.of(function.applyAsInt(content));
		}
		
		@Override
		public <R, Err extends Throwable> Opt<R> mapThrows(FunctionThrowing<Integer, R, Err> function) throws Err
		{
			return Opt.of(function.apply(this.content));
		}
		
		@Override
		public <R> Opt<R> flatmap(IntFunction<Opt<R>> function)
		{
			return function.apply(this.content);
		}
		
		@Override
		public <R, Err extends Throwable> Opt<R> flatmapThrows(FunctionThrowing<Integer, Opt<R>, Err> function)
			throws Err
		{
			return function.apply(this.content);
		}
		
		@Override
		public OptInt or(IntSupplier fallback)
		{
			return this;
		}
		
		@Override
		public OptInt flatOr(Supplier<OptInt> fallback)
		{
			return this;
		}
		
		@Override
		public int get()
		{
			return content;
		}
		
		@Override
		public int get(IntSupplier fallback)
		{
			return content;
		}
		
		@Override
		public <Err extends Throwable> int getThrows(SupplierThrowing<Integer, Err> fallback) throws Err
		{
			return content;
		}
		
		@Override
		public OptInt filter(IntPredicate filter)
		{
			if (filter.test(content))
			{
				return this;
			}
			else
			{
				return OptInt.empty();
			}
		}
		
		@Override
		public OptInt if_(IntConsumer use)
		{
			use.accept(content);
			return this;
		}
		
		@Override
		public <Err extends Throwable> OptInt if_Throws(ConsumerThrowing<Integer, Err> use) throws Err
		{
			use.accept(content);
			return this;
		}
		
		@Override
		public OptInt else_(Runnable action)
		{
			return this;
		}
		
		@Override
		public <Err extends Throwable> OptInt else_Throws(RunnableThrowing<Err> empty) throws Err
		{
			return this;
		}
		
		@Override
		public OptInt if_else(IntConsumer use, Runnable empty)
		{
			return if_(use);
		}
		
		@Override
		public int hashCode()
		{
			final int	prime	= 31;
			int			result	= 1;
			result = prime * result + content;
			return result;
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
			Content other = (Content) obj;
			return content == other.content;
		}
		
		@Override
		public String toString()
		{
			return String.format("OptInt[%s]", this.content);
		}
		
		@Override
		public OptInt add(int value)
		{
			return OptInt.of(content + value);
		}
		
		@Override
		public OptInt add(OptInt value)
		{
			return value.add(content);
		}
		
		@Override
		public OptInt sub(int value)
		{
			return OptInt.of(content - value);
		}
		
		@Override
		public OptInt sub(OptInt value)
		{
			return value.mapInt(v -> content - v);
		}
		
		@Override
		public OptInt div(int value)
		{
			return div(OptInt.of(value));
		}
		
		@Override
		public OptInt div(OptInt value)
		{
			return value.filter(v -> v != 0)
				.mapInt(v -> content / v);
		}
		
		@Override
		public OptInt mul(int value)
		{
			return OptInt.of(content * value);
		}
		
		@Override
		public OptInt mul(OptInt value)
		{
			return value.mul(content);
		}
		
		@Override
		public OptInt mod(int value)
		{
			return mod(OptInt.of(value));
		}
		
		@Override
		public OptInt mod(OptInt value)
		{
			return value.filter(v -> v != 0)
				.mapInt(v -> content % v);
		}
		
		@Override
		public OptInt shiftLeft(int value)
		{
			return OptInt.of(content << value);
		}
		
		@Override
		public OptInt shiftLeft(OptInt value)
		{
			return value.mapInt(v -> content << v);
		}
		
		@Override
		public OptInt shiftRight(int value)
		{
			return OptInt.of(content >>> value);
		}
		
		@Override
		public OptInt shiftRight(OptInt value)
		{
			return value.mapInt(v -> content >>> v);
		}
		
		@Override
		public OptInt shiftRightSigned(int value)
		{
			return OptInt.of(content >> value);
		}
		
		@Override
		public OptInt shiftRightSigned(OptInt value)
		{
			return value.mapInt(v -> content >> v);
		}
		
		@Override
		public OptInt and(int value)
		{
			return OptInt.of(content & value);
		}
		
		@Override
		public OptInt and(OptInt value)
		{
			return value.and(content);
		}
		
		@Override
		public OptInt or(int value)
		{
			return OptInt.of(content | value);
		}
		
		@Override
		public OptInt or(OptInt value)
		{
			return value.or(content);
		}
		
		@Override
		public OptInt xor(int value)
		{
			return OptInt.of(content ^ value);
		}
		
		@Override
		public OptInt xor(OptInt value)
		{
			return value.xor(content);
		}
		
		@Override
		public OptInt komp2()
		{
			return OptInt.of(~content);
		}
	}
	
	private static final class Empty extends OptInt
	{
		private Empty()
		{}
		
		@Override
		public boolean isPresent()
		{
			return false;
		}
		
		@Override
		public Integer getIgnore()
		{
			return null;
		}
		
		@Override
		public <R> Opt<R> map(IntFunction<R> function)
		{
			return Opt.empty();
		}
		
		@Override
		public OptInt mapInt(IntUnaryOperator function)
		{
			return this;
		}
		
		@Override
		public <R, Err extends Throwable> Opt<R> mapThrows(FunctionThrowing<Integer, R, Err> function) throws Err
		{
			return Opt.empty();
		}
		
		@Override
		public <R> Opt<R> flatmap(IntFunction<Opt<R>> function)
		{
			return Opt.empty();
		}
		
		@Override
		public <R, Err extends Throwable> Opt<R> flatmapThrows(FunctionThrowing<Integer, Opt<R>, Err> function)
			throws Err
		{
			return Opt.empty();
		}
		
		@Override
		public OptInt or(IntSupplier fallback)
		{
			return OptInt.of(fallback.getAsInt());
		}
		
		@Override
		public OptInt flatOr(Supplier<OptInt> fallback)
		{
			return fallback.get();
		}
		
		@Override
		public int get()
		{
			throw new NoSuchElementException();
		}
		
		@Override
		public int get(IntSupplier fallback)
		{
			return fallback.getAsInt();
		}
		
		@Override
		public <Err extends Throwable> int getThrows(SupplierThrowing<Integer, Err> fallback) throws Err
		{
			return fallback.get();
		}
		
		@Override
		public OptInt filter(IntPredicate filter)
		{
			return this;
		}
		
		@Override
		public OptInt if_(IntConsumer use)
		{
			return this;
		}
		
		@Override
		public <Err extends Throwable> OptInt if_Throws(ConsumerThrowing<Integer, Err> use) throws Err
		{
			return this;
		}
		
		@Override
		public OptInt else_(Runnable action)
		{
			action.run();
			return this;
		}
		
		@Override
		public <Err extends Throwable> OptInt else_Throws(RunnableThrowing<Err> empty) throws Err
		{
			empty.run();
			return this;
		}
		
		@Override
		public OptInt if_else(IntConsumer use, Runnable empty)
		{
			return else_(empty);
		}
		
		@Override
		public int hashCode()
		{
			return 0;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			return this == obj;
		}
		
		@Override
		public String toString()
		{
			return "OptInt.empty";
		}
		
		@Override
		public OptInt add(int value)
		{
			return this;
		}
		
		@Override
		public OptInt add(OptInt value)
		{
			return this;
		}
		
		@Override
		public OptInt sub(int value)
		{
			return this;
		}
		
		@Override
		public OptInt sub(OptInt value)
		{
			return this;
		}
		
		@Override
		public OptInt div(int value)
		{
			return this;
		}
		
		@Override
		public OptInt div(OptInt value)
		{
			return this;
		}
		
		@Override
		public OptInt mul(int value)
		{
			return this;
		}
		
		@Override
		public OptInt mul(OptInt value)
		{
			return this;
		}
		
		@Override
		public OptInt mod(int value)
		{
			return this;
		}
		
		@Override
		public OptInt mod(OptInt value)
		{
			return this;
		}
		
		@Override
		public OptInt shiftLeft(int value)
		{
			return this;
		}
		
		@Override
		public OptInt shiftLeft(OptInt value)
		{
			return this;
		}
		
		@Override
		public OptInt shiftRight(int value)
		{
			return this;
		}
		
		@Override
		public OptInt shiftRight(OptInt value)
		{
			return this;
		}
		
		@Override
		public OptInt shiftRightSigned(int value)
		{
			return this;
		}
		
		@Override
		public OptInt shiftRightSigned(OptInt value)
		{
			return this;
		}
		
		@Override
		public OptInt and(int value)
		{
			return this;
		}
		
		@Override
		public OptInt and(OptInt value)
		{
			return this;
		}
		
		@Override
		public OptInt or(int value)
		{
			return this;
		}
		
		@Override
		public OptInt or(OptInt value)
		{
			return this;
		}
		
		@Override
		public OptInt xor(int value)
		{
			return this;
		}
		
		@Override
		public OptInt xor(OptInt value)
		{
			return this;
		}
		
		@Override
		public OptInt komp2()
		{
			return this;
		}
	}
}