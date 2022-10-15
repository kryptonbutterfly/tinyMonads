package de.tinycodecrank.monads.sum.of4;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.tinycodecrank.monads.opt.Opt;

final class E2<A, B, C, D> implements Sum4<A, B, C, D>
{
	private final B b;
	
	E2(B b)
	{
		this.b = b;
	}
	
	@Override
	public B b(Supplier<B> fallback)
	{
		return b;
	}
	
	@Override
	public Opt<B> b()
	{
		return Opt.of(b);
	}
	
	@Override
	public boolean isB()
	{
		return true;
	}
	
	@Override
	public <R> Sum4<A, R, C, D> mapB(Function<B, R> bind)
	{
		return new E2<>(bind.apply(b));
	}
	
	@Override
	public void forEither(Consumer<A> a, Consumer<B> b, Consumer<C> c, Consumer<D> d)
	{
		b.accept(this.b);
	}
	
	@Override
	public <W, X, Y, Z> Sum4<W, X, Y, Z> map(Function<A, W> a, Function<B, X> b, Function<C, Y> c, Function<D, Z> d)
	{
		return new E2<>(b.apply(this.b));
	}
	
	@Override
	public <R> R fold(Function<A, R> a, Function<B, R> b, Function<C, R> c, Function<D, R> d)
	{
		return b.apply(this.b);
	}
	
	@Override
	public String toString()
	{
		return ToString.formatted("ø", b, "ø", "ø");
	}
}