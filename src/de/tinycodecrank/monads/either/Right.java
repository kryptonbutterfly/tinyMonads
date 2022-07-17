package de.tinycodecrank.monads.either;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.tinycodecrank.monads.opt.Opt;

final class Right<L, R> implements Either<L, R>
{
	private final R right;
	
	Right(R right)
	{
		this.right = right;
	}
	
	@Override
	public <Ret> Ret fold(Function<L, Ret> leftMapper, Function<R, Ret> rightMapper)
	{
		return rightMapper.apply(right);
	}
	
	@Override
	public void forEither(Consumer<L> left, Consumer<R> right)
	{
		right.accept(this.right);
	}
	
	@Override
	public Opt<L> left()
	{
		return Opt.empty();
	}
	
	@Override
	public boolean isLeft()
	{
		return false;
	}
	
	@Override
	public Opt<R> right()
	{
		return Opt.of(right);
	}
	
	@Override
	public L left(Supplier<L> fallback)
	{
		return fallback.get();
	}
	
	@Override
	public R right(Supplier<R> fallback)
	{
		return right;
	}
	
	@Override
	public boolean isRight()
	{
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <U> Either<U, R> mapLeft(Function<L, U> bind)
	{
		return (Either<U, R>) this;
	}
	
	@Override
	public <U> Either<L, U> mapRight(Function<R, U> bind)
	{
		return new Right<>(bind.apply(right));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <U, V> Either<U, V> map(Function<L, U> bindLeft, Function<R, V> bindRight)
	{
		return (Either<U, V>) mapRight(bindRight);
	}
	
	@Override
	public String toString()
	{
		return String.format(ToString, "∅", right);
	}
}