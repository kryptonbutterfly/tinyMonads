package de.tinycodecrank.monads.failable;

import java.util.function.Consumer;
import java.util.function.Function;

import de.tinycodecrank.functions.throwing.FunctionThrowing;
import de.tinycodecrank.functions.throwing.SupplierThrowing;
import de.tinycodecrank.monads.opt.Opt;

public sealed interface Failable<T, E extends Throwable> permits Success, Failure
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
	
	public Opt<T> toOpt(Consumer<E> failure);
	
	@Deprecated
	public T get() throws E;
	
	public T get(Function<E, T> failure);
	
	public <R> R fold(Function<T, R> success, Function<E, R> failure);
	
	public <S> Failable<S, E> map(Function<T, S> bind);
	
	public <S, E2 extends E> Failable<S, E2> flatmap(FunctionThrowing<T, S, E2> bind);
	
	public Failable<T, E> try_(Consumer<T> bind);
	
	public Failable<T, E> catch_(Consumer<E> bind);
}