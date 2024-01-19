package kryptonbutterfly.monads.sum.of2;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import kryptonbutterfly.monads.opt.Opt;

/**
 * Contains either the left or right element
 * 
 * @author kryptonbutterfly
 *
 * @param <L>
 *            The type of the left option
 * @param <R>
 *            The type of the right option
 */
public sealed interface Sum2<L, R> permits E1, E2
{
	static final String ToString = "(%s, %s)";
	
	/**
	 * Creates an instance containing the right type
	 * 
	 * @param right
	 * @return
	 */
	public static <L, R> Sum2<L, R> right(R right)
	{
		return new E2<L, R>(right);
	}
	
	/**
	 * Creates an instance containing the left type
	 * 
	 * @param left
	 * @return
	 */
	public static <L, R> Sum2<L, R> left(L left)
	{
		return new E1<L, R>(left);
	}
	
	/**
	 * @param left
	 * @param right
	 */
	public void forEither(Consumer<L> left, Consumer<R> right);
	
	default boolean isLeft()
	{
		return false;
	}
	
	default boolean isRight()
	{
		return false;
	}
	
	default Opt<L> left()
	{
		return Opt.empty();
	}
	
	default Opt<R> right()
	{
		return Opt.empty();
	}
	
	default L left(Supplier<L> fallback)
	{
		return fallback.get();
	}
	
	default R right(Supplier<R> fallback)
	{
		return fallback.get();
	}
	
	@SuppressWarnings("unchecked")
	default <U> Sum2<U, R> mapLeft(Function<L, U> bind)
	{
		return (Sum2<U, R>) this;
	}
	
	@SuppressWarnings("unchecked")
	default <U> Sum2<L, U> mapRight(Function<R, U> bind)
	{
		return (Sum2<L, U>) this;
	}
	
	public <U, V> Sum2<U, V> map(Function<L, U> bindLeft, Function<R, V> bindRight);
	
	public <Ret> Ret fold(Function<L, Ret> leftMapper, Function<R, Ret> rightMapper);
}