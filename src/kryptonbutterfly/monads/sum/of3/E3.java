package kryptonbutterfly.monads.sum.of3;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import kryptonbutterfly.monads.opt.Opt;

final class E3<L, M, R> implements Sum3<L, M, R>
{
	private final R right;
	
	E3(R right)
	{
		this.right = right;
	}
	
	@Override
	public void forEither(Consumer<L> left, Consumer<M> middle, Consumer<R> right)
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
	public <U> Sum3<L, M, U> mapR(Function<R, U> bind)
	{
		return new E3<>(bind.apply(right));
	}
	
	@Override
	public <U, V, W> Sum3<U, V, W> map(Function<L, U> left, Function<M, V> middle, Function<R, W> right)
	{
		return new E3<>(right.apply(this.right));
	}
	
	@Override
	public <Ret> Ret fold(Function<L, Ret> left, Function<M, Ret> middle, Function<R, Ret> right)
	{
		return right.apply(this.right);
	}
	
	@Override
	public String toString()
	{
		return ToString.formatted("∅", right, "∅");
	}
}