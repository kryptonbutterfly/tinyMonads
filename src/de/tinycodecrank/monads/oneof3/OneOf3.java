package de.tinycodecrank.monads.oneof3;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.tinycodecrank.monads.opt.Opt;

public sealed interface OneOf3<L, M, R> permits Left<L, M, R>, Middle<L, M, R>, Right<L, M, R>
{
	static final String ToString = "(%s, %s, %s)";
	
	public static <L, M, R> OneOf3<L, M, R> left(L left)
	{
		return new Left<L, M, R>(left);
	}
	
	public static <L, M, R> OneOf3<L, M, R> middle(M middle)
	{
		return new Middle<L, M, R>(middle);
	}
	
	public static <L, M, R> OneOf3<L, M, R> right(R right)
	{
		return new Right<L, M, R>(right);
	}
	
	void forEither(Consumer<L> left, Consumer<M> middle, Consumer<R> right);
	
	L left(Supplier<L> fallback);
	
	M middle(Supplier<M> fallback);
	
	R right(Supplier<R> fallback);
	
	Opt<L> left();
	
	Opt<M> middle();
	
	Opt<R> right();
	
	boolean isLeft();
	
	boolean isMiddle();
	
	boolean isRight();
	
	<U> OneOf3<U, M, R> mapL(Function<L, U> bind);
	
	<U> OneOf3<L, U, R> mapM(Function<M, U> bind);
	
	<U> OneOf3<L, M, U> mapR(Function<R, U> bind);
	
	<U, V, W> OneOf3<U, V, W> map(Function<L, U> left, Function<M, V> middle, Function<R, W> right);
	
	<Ret> Ret fold(Function<L, Ret> left, Function<M, Ret> middle, Function<R, Ret> right);
}