package de.tinycodecrank.monads.oneof3;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.tinycodecrank.monads.opt.Opt;

final class Middle<L, M, R> implements OneOf3<L, M, R>
{
	private final M middle;
	
	Middle(M middle)
	{
		this.middle = middle;
	}
	
	@Override
	public void forEither(Consumer<L> left, Consumer<M> middle, Consumer<R> right)
	{
		middle.accept(this.middle);
	}
	
	@Override
	public Opt<L> left()
	{
		return Opt.empty();
	}
	
	@Override
	public Opt<M> middle()
	{
		return Opt.of(middle);
	}
	
	@Override
	public Opt<R> right()
	{
		return Opt.empty();
	}
	
	@Override
	public L left(Supplier<L> fallback)
	{
		return fallback.get();
	}
	
	@Override
	public M middle(Supplier<M> fallback)
	{
		return middle;
	}
	
	@Override
	public R right(Supplier<R> fallback)
	{
		return fallback.get();
	}
	
	@Override
	public boolean isLeft()
	{
		return false;
	}
	
	@Override
	public boolean isMiddle()
	{
		return true;
	}
	
	@Override
	public boolean isRight()
	{
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <U> OneOf3<U, M, R> mapL(Function<L, U> bind)
	{
		return (OneOf3<U, M, R>) this;
	}
	
	@Override
	public <U> OneOf3<L, U, R> mapM(Function<M, U> bind)
	{
		return new Middle<>(bind.apply(this.middle));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <U> OneOf3<L, M, U> mapR(Function<R, U> bind)
	{
		return (OneOf3<L, M, U>) this;
	}
	
	@Override
	public <U, V, W> OneOf3<U, V, W> map(Function<L, U> left, Function<M, V> middle, Function<R, W> right)
	{
		return new Middle<>(middle.apply(this.middle));
	}
	
	@Override
	public <Ret> Ret fold(Function<L, Ret> left, Function<M, Ret> middle, Function<R, Ret> right)
	{
		return middle.apply(this.middle);
	}
	
	@Override
	public String toString()
	{
		return String.format(ToString, "∅", middle, "∅");
	}
}