package de.tinycodecrank.monads.oneof3;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.tinycodecrank.monads.opt.Opt;

final class Right<L, M, R> implements OneOf3<L, M, R>
{
	private final R right;
	
	Right(R right)
	{
		this.right = right;
	}
	
	@Override
	public void forEither(Consumer<L> left, Consumer<M> middle, Consumer<R> right)
	{
		right.accept(this.right);
	}
	
	@Override
	public Opt<L> left()
	{
		return Opt.empty();
	}
	
	@Override
	public Opt<M> middle()
	{
		return Opt.empty();
	}
	
	@Override
	public Opt<R> right()
	{
		return Opt.of(right);
	}
	
	@Override
	public L left(Supplier<L> fallback)
	{
		return fallback.get();
	}
	
	@Override
	public M middle(Supplier<M> fallback)
	{
		return fallback.get();
	}
	
	@Override
	public R right(Supplier<R> fallback)
	{
		return right;
	}
	
	@Override
	public boolean isLeft()
	{
		return false;
	}
	
	@Override
	public boolean isMiddle()
	{
		return false;
	}
	
	@Override
	public boolean isRight()
	{
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <U> OneOf3<U, M, R> mapL(Function<L, U> bind)
	{
		return (OneOf3<U, M, R>) this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <U> OneOf3<L, U, R> mapM(Function<M, U> bind)
	{
		return (OneOf3<L, U, R>) this;
	}
	
	@Override
	public <U> OneOf3<L, M, U> mapR(Function<R, U> bind)
	{
		return new Right<>(bind.apply(right));
	}
	
	@Override
	public <U, V, W> OneOf3<U, V, W> map(Function<L, U> left, Function<M, V> middle, Function<R, W> right)
	{
		return new Right<>(right.apply(this.right));
	}
	
	@Override
	public <Ret> Ret fold(Function<L, Ret> left, Function<M, Ret> middle, Function<R, Ret> right)
	{
		return right.apply(this.right);
	}
	
	@Override
	public String toString()
	{
		return String.format(ToString, "∅", right, "∅");
	}
}