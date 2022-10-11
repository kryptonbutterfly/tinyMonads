package de.tinycodecrank.monads.failable;

import java.util.function.Consumer;
import java.util.function.Function;

import de.tinycodecrank.functions.throwing.FunctionThrowing;
import de.tinycodecrank.monads.opt.Opt;

final class Success<T, E extends Throwable> implements Failable<T, E>
{
	private final T value;
	
	Success(T value)
	{
		this.value = value;
	}
	
	@Override
	public Opt<T> toOpt(Consumer<E> failure)
	{
		return Opt.of(this.value);
	}
	
	@Override
	public T get()
	{
		return value;
	}
	
	@Override
	public T get(Function<E, T> failure)
	{
		return value;
	}
	
	@Override
	public <S> Failable<S, E> map(Function<T, S> bind)
	{
		return new Success<S, E>(bind.apply(value));
	}
	
	@Override
	public <S, E2 extends E> Failable<S, E2> flatmap(FunctionThrowing<T, S, E2> bind)
	{
		return Failable.attempt(() -> bind.apply(value));
	}
	
	@Override
	public <R> R fold(Function<T, R> success, Function<E, R> failure)
	{
		return success.apply(value);
	}
	
	@Override
	public Failable<T, E> try_(Consumer<T> bind)
	{
		bind.accept(value);
		return this;
	}
	
	@Override
	public Failable<T, E> catch_(Consumer<E> bind)
	{
		return this;
	}
}