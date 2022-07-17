package de.tinycodecrank.monads.either;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.tinycodecrank.monads.opt.Opt;

final class Left<L, R> implements Either<L, R>
{
	private final L left;
	
	Left(L left)
	{
		this.left = left;
	}
	
	@Override
	public <Ret> Ret fold(Function<L, Ret> leftMapper, Function<R, Ret> rightMapper)
	{
		return leftMapper.apply(left);
	}
	
	@Override
	public void forEither(Consumer<L> left, Consumer<R> right)
	{
		left.accept(this.left);
	}
	
	@Override
	public Opt<L> left()
	{
		return Opt.of(left);
	}
	
	@Override
	public boolean isLeft()
	{
		return true;
	}
	
	@Override
	public Opt<R> right()
	{
		return Opt.empty();
	}
	
	@Override
	public boolean isRight()
	{
		return false;
	}
	
	@Override
	public L left(Supplier<L> fallback)
	{
		return left;
	}
	
	@Override
	public R right(Supplier<R> fallback)
	{
		return fallback.get();
	}
	
	@Override
	public <U> Either<U, R> mapLeft(Function<L, U> bind)
	{
		return new Left<>(bind.apply(left));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <U> Either<L, U> mapRight(Function<R, U> bind)
	{
		return (Either<L, U>) this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <U, V> Either<U, V> map(Function<L, U> bindLeft, Function<R, V> bindRight)
	{
		return (Either<U, V>) mapLeft(bindLeft);
	}
	
	@Override
	public String toString()
	{
		return String.format(ToString, left, "∅");
	}
}