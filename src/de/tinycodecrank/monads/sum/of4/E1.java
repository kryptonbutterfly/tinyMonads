package de.tinycodecrank.monads.sum.of4;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.tinycodecrank.monads.opt.Opt;

final class E1<A, B, C, D> implements Sum4<A, B, C, D>
{
	private final A a;
	
	E1(A a)
	{
		this.a = a;
	}
	
	@Override
	public A a(Supplier<A> fallback)
	{
		return a;
	}
	
	@Override
	public Opt<A> a()
	{
		return Opt.of(a);
	}
	
	@Override
	public boolean isA()
	{
		return true;
	}
	
	@Override
	public <R> Sum4<R, B, C, D> mapA(Function<A, R> bind)
	{
		return new E1<>(bind.apply(a));
	}
	
	@Override
	public void forEither(Consumer<A> a, Consumer<B> b, Consumer<C> c, Consumer<D> d)
	{
		a.accept(this.a);
	}
	
	@Override
	public <W, X, Y, Z> Sum4<W, X, Y, Z> map(Function<A, W> a, Function<B, X> b, Function<C, Y> c, Function<D, Z> d)
	{
		return new E1<>(a.apply(this.a));
	}
	
	@Override
	public <R> R fold(Function<A, R> a, Function<B, R> b, Function<C, R> c, Function<D, R> d)
	{
		return a.apply(this.a);
	}
	
	@Override
	public String toString()
	{
		return ToString.formatted(a, "ø", "ø", "ø");
	}
}