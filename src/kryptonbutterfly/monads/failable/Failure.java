package kryptonbutterfly.monads.failable;

import java.util.function.Consumer;
import java.util.function.Function;

import kryptonbutterfly.functions.throwing.FunctionThrowing;
import kryptonbutterfly.monads.opt.Opt;

final class Failure<T, E extends Throwable> implements Failable<T, E>
{
	private final E exception;
	
	Failure(E exception)
	{
		this.exception = exception;
	}
	
	@Override
	public Opt<T> toOpt(Consumer<E> failure)
	{
		failure.accept(this.exception);
		return Opt.empty();
	}
	
	@Override
	public T get() throws E
	{
		throw exception;
	}
	
	@Override
	public T get(Function<E, T> failure)
	{
		return failure.apply(exception);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <S> Failable<S, E> map(Function<T, S> bind)
	{
		return (Failable<S, E>) this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <S, E2 extends E> Failable<S, E2> flatmap(FunctionThrowing<T, S, E2> bind)
	{
		return (Failable<S, E2>) this;
	}
	
	@Override
	public <R> R fold(Function<T, R> success, Function<E, R> failure)
	{
		return failure.apply(exception);
	}
	
	@Override
	public Failable<T, E> try_(Consumer<T> bind)
	{
		return this;
	}
	
	@Override
	public Failable<T, E> catch_(Consumer<E> bind)
	{
		bind.accept(exception);
		return this;
	}
}