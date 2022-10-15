package de.tinycodecrank.monads.sum.of4;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.tinycodecrank.monads.opt.Opt;

final class E4<A, B, C, D> implements Sum4<A, B, C, D>
{
	private final D d;
	
	E4(D d)
	{
		this.d = d;
	}
	
	@Override
	public D d(Supplier<D> fallback)
	{
		return d;
	}
	
	@Override
	public Opt<D> d()
	{
		return Opt.of(d);
	}
	
	@Override
	public boolean isD()
	{
		return true;
	}
	
	@Override
	public <R> Sum4<A, B, C, R> mapD(Function<D, R> bind)
	{
		return new E4<>(bind.apply(this.d));
	}
	
	@Override
	public void forEither(Consumer<A> a, Consumer<B> b, Consumer<C> c, Consumer<D> d)
	{
		d.accept(this.d);
	}
	
	@Override
	public <W, X, Y, Z> Sum4<W, X, Y, Z> map(Function<A, W> a, Function<B, X> b, Function<C, Y> c, Function<D, Z> d)
	{
		return new E4<>(d.apply(this.d));
	}
	
	@Override
	public <R> R fold(Function<A, R> a, Function<B, R> b, Function<C, R> c, Function<D, R> d)
	{
		return d.apply(this.d);
	}
	
	@Override
	public String toString()
	{
		return ToString.formatted("ø", "ø", "ø", d);
	}
}