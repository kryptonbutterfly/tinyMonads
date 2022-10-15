package de.tinycodecrank.monads.sum.of4;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.tinycodecrank.monads.opt.Opt;

public sealed interface Sum4<A, B, C, D> permits E1<A, B, C, D>, E2<A, B, C, D>, E3<A, B, C, D>, E4<A, B, C, D>
{
	static final String ToString = "(%s, %s, %s, %s)";
	
	public static <A, B, C, D> Sum4<A, B, C, D> e1(A a)
	{
		return new E1<A, B, C, D>(a);
	}
	
	public static <A, B, C, D> Sum4<A, B, C, D> e2(B b)
	{
		return new E2<A, B, C, D>(b);
	}
	
	public static <A, B, C, D> Sum4<A, B, C, D> e3(C c)
	{
		return new E3<A, B, C, D>(c);
	}
	
	public static <A, B, C, D> Sum4<A, B, C, D> e4(D d)
	{
		return new E4<A, B, C, D>(d);
	}
	
	void forEither(Consumer<A> a, Consumer<B> b, Consumer<C> c, Consumer<D> d);
	
	default A a(Supplier<A> fallback)
	{
		return fallback.get();
	}
	
	default B b(Supplier<B> fallback)
	{
		return fallback.get();
	}
	
	default C c(Supplier<C> fallback)
	{
		return fallback.get();
	}
	
	default D d(Supplier<D> fallback)
	{
		return fallback.get();
	}
	
	default Opt<A> a()
	{
		return Opt.empty();
	}
	
	default Opt<B> b()
	{
		return Opt.empty();
	}
	
	default Opt<C> c()
	{
		return Opt.empty();
	}
	
	default Opt<D> d()
	{
		return Opt.empty();
	}
	
	default boolean isA()
	{
		return false;
	}
	
	default boolean isB()
	{
		return false;
	}
	
	default boolean isC()
	{
		return false;
	}
	
	default boolean isD()
	{
		return false;
	}
	
	@SuppressWarnings("unchecked")
	default <R> Sum4<R, B, C, D> mapA(Function<A, R> bind)
	{
		return (Sum4<R, B, C, D>) this;
	}
	
	@SuppressWarnings("unchecked")
	default <R> Sum4<A, R, C, D> mapB(Function<B, R> bind)
	{
		return (Sum4<A, R, C, D>) this;
	}
	
	@SuppressWarnings("unchecked")
	default <R> Sum4<A, B, R, D> mapC(Function<C, R> bind)
	{
		return (Sum4<A, B, R, D>) this;
	}
	
	@SuppressWarnings("unchecked")
	default <R> Sum4<A, B, C, R> mapD(Function<D, R> bind)
	{
		return (Sum4<A, B, C, R>) this;
	}
	
	<W, X, Y, Z> Sum4<W, X, Y, Z> map(Function<A, W> a, Function<B, X> b, Function<C, Y> c, Function<D, Z> d);
	
	<R> R fold(Function<A, R> a, Function<B, R> b, Function<C, R> c, Function<D, R> d);
}