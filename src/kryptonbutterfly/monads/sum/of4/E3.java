package kryptonbutterfly.monads.sum.of4;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import kryptonbutterfly.monads.opt.Opt;

final class E3<A, B, C, D> implements Sum4<A, B, C, D>
{
	private final C c;
	
	E3(C c)
	{
		this.c = c;
	}
	
	@Override
	public C c(Supplier<C> fallback)
	{
		return c;
	}
	
	@Override
	public Opt<C> c()
	{
		return Opt.of(c);
	}
	
	@Override
	public boolean isC()
	{
		return true;
	}
	
	@Override
	public <R> Sum4<A, B, R, D> mapC(Function<C, R> bind)
	{
		return new E3<>(bind.apply(c));
	}
	
	@Override
	public void forEither(Consumer<A> a, Consumer<B> b, Consumer<C> c, Consumer<D> d)
	{
		c.accept(this.c);
	}
	
	@Override
	public <W, X, Y, Z> Sum4<W, X, Y, Z> map(Function<A, W> a, Function<B, X> b, Function<C, Y> c, Function<D, Z> d)
	{
		return new E3<>(c.apply(this.c));
	}
	
	@Override
	public <R> R fold(Function<A, R> a, Function<B, R> b, Function<C, R> c, Function<D, R> d)
	{
		return c.apply(this.c);
	}
	
	@Override
	public String toString()
	{
		return ToString.formatted("ø", "ø", c, "ø");
	}
}