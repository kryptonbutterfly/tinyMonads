package de.tinycodecrank.monads.either;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.tinycodecrank.monads.opt.Opt;

/**
 * Contains either the left or right element
 * 
 * @author tinycodecrank
 *
 * @param <L>
 *            The type of the left option
 * @param <R>
 *            The type of the right option
 */
public sealed interface Either<L, R> permits Left<L, R>, Right<L, R>
{
	static final String ToString = "(%s, %s)";
	
	/**
	 * Creates an instance containing the right type
	 * 
	 * @param right
	 * @return
	 */
	public static <L, R> Either<L, R> right(R right)
	{
		return new Right<L, R>(right);
	}
	
	/**
	 * Creates an instance containing the left type
	 * 
	 * @param left
	 * @return
	 */
	public static <L, R> Either<L, R> left(L left)
	{
		return new Left<L, R>(left);
	}
	
	/**
	 * @param left
	 * @param right
	 */
	public void forEither(Consumer<L> left, Consumer<R> right);
	
	public boolean isLeft();
	
	public boolean isRight();
	
	public Opt<L> left();
	
	public Opt<R> right();
	
	public L left(Supplier<L> fallback);
	
	public R right(Supplier<R> fallback);
	
	public <U> Either<U, R> mapLeft(Function<L, U> bind);
	
	public <U> Either<L, U> mapRight(Function<R, U> bind);
	
	public <U, V> Either<U, V> map(Function<L, U> bindLeft, Function<R, V> bindRight);
	
	public <Ret> Ret fold(Function<L, Ret> leftMapper, Function<R, Ret> rightMapper);
}