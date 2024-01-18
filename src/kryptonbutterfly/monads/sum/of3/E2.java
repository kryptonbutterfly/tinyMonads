package kryptonbutterfly.monads.sum.of3;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import kryptonbutterfly.monads.opt.Opt;

final class E2<L, M, R> implements Sum3<L, M, R>
{
	private final M middle;
	
	E2(M middle)
	{
		this.middle = middle;
	}
	
	@Override
	public void forEither(Consumer<L> left, Consumer<M> middle, Consumer<R> right)
	{
		middle.accept(this.middle);
	}
	
	@Override
	public Opt<M> middle()
	{
		return Opt.of(middle);
	}
	
	@Override
	public M middle(Supplier<M> fallback)
	{
		return middle;
	}
	
	@Override
	public boolean isMiddle()
	{
		return true;
	}
	
	@Override
	public <U> Sum3<L, U, R> mapM(Function<M, U> bind)
	{
		return new E2<>(bind.apply(this.middle));
	}
	
	@Override
	public <U, V, W> Sum3<U, V, W> map(Function<L, U> left, Function<M, V> middle, Function<R, W> right)
	{
		return new E2<>(middle.apply(this.middle));
	}
	
	@Override
	public <Ret> Ret fold(Function<L, Ret> left, Function<M, Ret> middle, Function<R, Ret> right)
	{
		return middle.apply(this.middle);
	}
	
	@Override
	public String toString()
	{
		return ToString.formatted("∅", middle, "∅");
	}
}