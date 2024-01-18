package kryptonbutterfly.monads.sum.of2;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import kryptonbutterfly.monads.opt.Opt;

final class E1<L, R> implements Sum2<L, R>
{
	private final L left;
	
	E1(L left)
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
	public L left(Supplier<L> fallback)
	{
		return left;
	}
	
	@Override
	public <U> Sum2<U, R> mapLeft(Function<L, U> bind)
	{
		return new E1<>(bind.apply(left));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <U, V> Sum2<U, V> map(Function<L, U> bindLeft, Function<R, V> bindRight)
	{
		return (Sum2<U, V>) mapLeft(bindLeft);
	}
	
	@Override
	public String toString()
	{
		return String.format(ToString, left, "∅");
	}
}