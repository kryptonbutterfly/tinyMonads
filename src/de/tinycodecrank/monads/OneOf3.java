package de.tinycodecrank.monads;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class OneOf3<L, M, R>
{
	private OneOf3()
	{}
	
	public static <L, M, R> OneOf3<L, M, R> left(L left)
	{
		return new Left<L, M, R>(left);
	}
	
	public static <L, M, R> OneOf3<L, M, R> middle(M middle)
	{
		return new Middle<L, M, R>(middle);
	}
	
	public static <L, M, R> OneOf3<L, M, R> right(R right)
	{
		return new Right<L, M, R>(right);
	}
	
	abstract L left();
	
	abstract M middle();
	
	abstract R right();
	
	public abstract void forEither(Consumer<L> left, Consumer<M> middle, Consumer<R> right);
	
	public abstract Opt<L> getLeft();
	
	public abstract Opt<M> getMiddle();
	
	public abstract Opt<R> getRight();
	
	public abstract boolean isLeft();
	
	public abstract boolean isMiddle();
	
	public abstract boolean isRight();
	
	public abstract <U> OneOf3<U, M, R> mapL(Function<L, U> bind);
	
	public abstract <U> OneOf3<L, U, R> mapM(Function<M, U> bind);
	
	public abstract <U> OneOf3<L, M, U> mapR(Function<R, U> bind);
	
	public abstract <U, V, W> OneOf3<U, V, W> map(Function<L, U> left, Function<M, V> middle, Function<R, W> right);
	
	public abstract <Ret> Ret fold(Function<L, Ret> left, Function<M, Ret> middle, Function<R, Ret> right);
	
	private static final class Left<L, M, R> extends OneOf3<L, M, R>
	{
		private final L left;
		
		private Left(L left)
		{
			this.left = left;
		}
		
		@Override
		L left()
		{
			return left;
		}
		
		@Override
		M middle()
		{
			throw new NoSuchElementException("called middle() on Left");
		}
		
		@Override
		R right()
		{
			throw new NoSuchElementException("called right() on Left");
		}
		
		@Override
		public void forEither(Consumer<L> left, Consumer<M> middle, Consumer<R> right)
		{
			left.accept(this.left);
		}
		
		@Override
		public Opt<L> getLeft()
		{
			return Opt.of(left);
		}
		
		@Override
		public Opt<M> getMiddle()
		{
			return Opt.empty();
		}
		
		@Override
		public Opt<R> getRight()
		{
			return Opt.empty();
		}
		
		@Override
		public boolean isLeft()
		{
			return true;
		}
		
		@Override
		public boolean isMiddle()
		{
			return false;
		}
		
		@Override
		public boolean isRight()
		{
			return false;
		}
		
		@Override
		public <U> OneOf3<U, M, R> mapL(Function<L, U> bind)
		{
			return left(bind.apply(left));
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <U> OneOf3<L, U, R> mapM(Function<M, U> bind)
		{
			return (OneOf3<L, U, R>) this;
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
			return left(left.apply(this.left));
		}
		
		@Override
		public <Ret> Ret fold(Function<L, Ret> left, Function<M, Ret> middle, Function<R, Ret> right)
		{
			return left.apply(this.left);
		}
		
		@Override
		public String toString()
		{
			return "(" + left + ", ∅, ∅)";
		}
	}
	
	private static final class Middle<L, M, R> extends OneOf3<L, M, R>
	{
		private final M middle;
		
		private Middle(M middle)
		{
			this.middle = middle;
		}
		
		@Override
		L left()
		{
			throw new NoSuchElementException("called left() on Middle");
		}
		
		@Override
		M middle()
		{
			return middle;
		}
		
		@Override
		R right()
		{
			throw new NoSuchElementException("called right() on Middle");
		}
		
		@Override
		public void forEither(Consumer<L> left, Consumer<M> middle, Consumer<R> right)
		{
			middle.accept(this.middle);
		}
		
		@Override
		public Opt<L> getLeft()
		{
			return Opt.empty();
		}
		
		@Override
		public Opt<M> getMiddle()
		{
			return Opt.of(middle);
		}
		
		@Override
		public Opt<R> getRight()
		{
			return Opt.empty();
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
			return middle(bind.apply(this.middle));
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
			return middle(middle.apply(this.middle));
		}
		
		@Override
		public <Ret> Ret fold(Function<L, Ret> left, Function<M, Ret> middle, Function<R, Ret> right)
		{
			return middle.apply(this.middle);
		}
		
		@Override
		public String toString()
		{
			return "(∅, " + middle + ", ∅)";
		}
	}
	
	private static final class Right<L, M, R> extends OneOf3<L, M, R>
	{
		private final R right;
		
		private Right(R right)
		{
			this.right = right;
		}
		
		@Override
		L left()
		{
			throw new NoSuchElementException("called left() on Right");
		}
		
		@Override
		M middle()
		{
			throw new NoSuchElementException("called middle() on Right");
		}
		
		@Override
		R right()
		{
			return right;
		}
		
		@Override
		public void forEither(Consumer<L> left, Consumer<M> middle, Consumer<R> right)
		{
			right.accept(this.right);
		}
		
		@Override
		public Opt<L> getLeft()
		{
			return Opt.empty();
		}
		
		@Override
		public Opt<M> getMiddle()
		{
			return Opt.empty();
		}
		
		@Override
		public Opt<R> getRight()
		{
			return Opt.of(right);
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
			return right(bind.apply(right));
		}
		
		@Override
		public <U, V, W> OneOf3<U, V, W> map(Function<L, U> left, Function<M, V> middle, Function<R, W> right)
		{
			return right(right.apply(this.right));
		}
		
		@Override
		public <Ret> Ret fold(Function<L, Ret> left, Function<M, Ret> middle, Function<R, Ret> right)
		{
			return right.apply(this.right);
		}
		
		@Override
		public String toString()
		{
			return "(∅, ∅, " + right + ")";
		}
	}
}