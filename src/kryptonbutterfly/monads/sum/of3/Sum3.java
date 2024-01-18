package kryptonbutterfly.monads.sum.of3;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import kryptonbutterfly.monads.opt.Opt;

public sealed interface Sum3<L, M, R> permits E1, E2, E3
{
	static final String ToString = "(%s, %s, %s)";
	
	public static <L, M, R> Sum3<L, M, R> left(L left)
	{
		return new E1<L, M, R>(left);
	}
	
	public static <L, M, R> Sum3<L, M, R> middle(M middle)
	{
		return new E2<L, M, R>(middle);
	}
	
	public static <L, M, R> Sum3<L, M, R> right(R right)
	{
		return new E3<L, M, R>(right);
	}
	
	void forEither(Consumer<L> left, Consumer<M> middle, Consumer<R> right);
	
	default L left(Supplier<L> fallback)
	{
		return fallback.get();
	}
	
	default M middle(Supplier<M> fallback)
	{
		return fallback.get();
	}
	
	default R right(Supplier<R> fallback)
	{
		return fallback.get();
	}
	
	default Opt<L> left()
	{
		return Opt.empty();
	}
	
	default Opt<M> middle()
	{
		return Opt.empty();
	}
	
	default Opt<R> right()
	{
		return Opt.empty();
	}
	
	default boolean isLeft()
	{
		return false;
	}
	
	default boolean isMiddle()
	{
		return false;
	}
	
	default boolean isRight()
	{
		return false;
	}
	
	@SuppressWarnings("unchecked")
	default <U> Sum3<U, M, R> mapL(Function<L, U> bind)
	{
		return (Sum3<U, M, R>) this;
	}
	
	@SuppressWarnings("unchecked")
	default <U> Sum3<L, U, R> mapM(Function<M, U> bind)
	{
		return (Sum3<L, U, R>) this;
	}
	
	@SuppressWarnings("unchecked")
	default <U> Sum3<L, M, U> mapR(Function<R, U> bind)
	{
		return (Sum3<L, M, U>) this;
	}
	
	<U, V, W> Sum3<U, V, W> map(Function<L, U> left, Function<M, V> middle, Function<R, W> right);
	
	<Ret> Ret fold(Function<L, Ret> left, Function<M, Ret> middle, Function<R, Ret> right);
}