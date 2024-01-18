package kryptonbutterfly.monads.sum.of3;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import kryptonbutterfly.monads.opt.Opt;

final class E1<L, M, R> implements Sum3<L, M, R>
{
	private final L left;
	
	E1(L left)
	{
		this.left = left;
	}
	
	@Override
	public void forEither(Consumer<L> left, Consumer<M> middle, Consumer<R> right)
	{
		left.accept(this.left);
	}
	
	@Override
	public Opt<L> left()
	{
		return Opt.of(left);
	}
	
	@Override
	public L left(Supplier<L> fallback)
	{
		return left;
	}
	
	@Override
	public boolean isLeft()
	{
		return true;
	}
	
	@Override
	public <U> Sum3<U, M, R> mapL(Function<L, U> bind)
	{
		return new E1<>(bind.apply(left));
	}
	
	@Override
	public <U, V, W> Sum3<U, V, W> map(Function<L, U> left, Function<M, V> middle, Function<R, W> right)
	{
		return new E1<>(left.apply(this.left));
	}
	
	@Override
	public <Ret> Ret fold(Function<L, Ret> left, Function<M, Ret> middle, Function<R, Ret> right)
	{
		return left.apply(this.left);
	}
	
	@Override
	public String toString()
	{
		return ToString.formatted(left, "∅", "∅");
	}
}