package de.tinycodecrank.monads.sum.of2;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.tinycodecrank.monads.opt.Opt;

final class E2<L, R> implements Sum2<L, R>
{
	private final R right;
	
	E2(R right)
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
	public Opt<R> right()
	{
		return Opt.of(right);
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
	
	@Override
	public <U> Sum2<L, U> mapRight(Function<R, U> bind)
	{
		return new E2<>(bind.apply(right));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <U, V> Sum2<U, V> map(Function<L, U> bindLeft, Function<R, V> bindRight)
	{
		return (Sum2<U, V>) mapRight(bindRight);
	}
	
	@Override
	public String toString()
	{
		return String.format(ToString, "∅", right);
	}
}