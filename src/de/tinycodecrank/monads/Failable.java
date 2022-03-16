package de.tinycodecrank.monads;

import java.util.function.Consumer;
import java.util.function.Function;

import de.tinycodecrank.functions.throwing.ConsumerThrowing;
import de.tinycodecrank.functions.throwing.FunctionThrowing;
import de.tinycodecrank.functions.throwing.SupplierThrowing;

public abstract class Failable<T, E extends Throwable>
{
	@SuppressWarnings("unchecked")
	public static <T, E extends Throwable> Failable<T, E> attempt(SupplierThrowing<T, E> action)
	{
		try
		{
			return new Success<T, E>(action.get());
		}
		catch (Throwable exception)
		{
			return new Failure<T, E>((E) exception);
		}
	}
	
	public abstract Opt<T> handleException(Consumer<E> result);
	
	public abstract T get() throws E;
	
	public abstract <E2 extends Throwable> Failable<T, E2> use(ConsumerThrowing<T, E2> use) throws E;
	
	public abstract <S> Failable<S, E> map(Function<T, S> bind);
	
	public abstract <S, E2 extends Throwable> Failable<S, E2> flatmap(FunctionThrowing<T, S, E2> bind) throws E;
	
	public static final class Success<T, E extends Throwable> extends Failable<T, E>
	{
		private final T value;
		
		private Success(T value)
		{
			this.value = value;
		}
		
		@Override
		public Opt<T> handleException(Consumer<E> result)
		{
			return Opt.of(this.value);
		}
		
		@Override
		public T get()
		{
			return value;
		}
		
		@Override
		public <S> Failable<S, E> map(Function<T, S> bind)
		{
			return new Success<S, E>(bind.apply(value));
		}
		
		@Override
		public <S, E2 extends Throwable> Failable<S, E2> flatmap(FunctionThrowing<T, S, E2> bind) throws E
		{
			return attempt(() -> bind.apply(value));
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <E2 extends Throwable> Failable<T, E2> use(ConsumerThrowing<T, E2> use) throws E
		{
			try
			{
				use.accept(value);
				return new Success<T, E2>(value);
			}
			catch (Throwable exception)
			{
				return new Failure<T, E2>((E2) exception);
			}
		}
	}
	
	public static final class Failure<T, E extends Throwable> extends Failable<T, E>
	{
		private final E exception;
		
		private Failure(E exception)
		{
			this.exception = exception;
		}
		
		@Override
		public Opt<T> handleException(Consumer<E> result)
		{
			result.accept(this.exception);
			return Opt.empty();
		}
		
		@Override
		public T get() throws E
		{
			throw exception;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <S> Failable<S, E> map(Function<T, S> bind)
		{
			return (Failable<S, E>) this;
		}
		
		@Override
		public <S, E2 extends Throwable> Failable<S, E2> flatmap(FunctionThrowing<T, S, E2> bind) throws E
		{
			throw exception;
		}
		
		@Override
		public <E2 extends Throwable> Failable<T, E2> use(ConsumerThrowing<T, E2> use) throws E
		{
			throw exception;
		}
	}
}