package de.tinycodecrank.monads;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Either<L, R>
{
	private Either()
	{}
	
	public static <L, R> Either<L, R> right(R right)
	{
		return new Right<L, R>(right);
	}
	
	public static <L, R> Either<L, R> left(L left)
	{
		return new Left<L, R>(left);
	}
	
	abstract L left();
	
	abstract R right();
	
	public abstract void forEither(Consumer<L> left, Consumer<R> right);
	
	public abstract Opt<L> getLeft();
	
	public abstract boolean isLeft();
	
	public abstract Opt<R> getRight();
	
	public abstract boolean isRight();
	
	public abstract <U> Either<U, R> mapLeft(Function<L, U> bind);
	
	public abstract <U> Either<L, U> mapRight(Function<R, U> bind);
	
	public abstract <U, V> Either<U, V> map(Function<L, U> bindLeft, Function<R, V> bindRight);
	
	public abstract <Ret> Ret fold(Function<L, Ret> leftMapper, Function<R, Ret> rightMapper);
	
	private static final class Left<L, R> extends Either<L, R>
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
		R right()
		{
			throw new NoSuchElementException("called right() on Left");
		}
		
		@Override
		public <Ret> Ret fold(Function<L, Ret> leftMapper, Function<R, Ret> rightMapper)
		{
			return leftMapper.apply(left);
		}
		
		@Override
		public void forEither(Consumer<L> left, Consumer<R> right)
		{
			left.accept(this.left);
		}
		
		@Override
		public Opt<L> getLeft()
		{
			return Opt.of(left);
		}
		
		@Override
		public boolean isLeft()
		{
			return true;
		}
		
		@Override
		public Opt<R> getRight()
		{
			return Opt.empty();
		}
		
		@Override
		public boolean isRight()
		{
			return false;
		}
		
		@Override
		public <U> Either<U, R> mapLeft(Function<L, U> bind)
		{
			return left(bind.apply(left));
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <U> Either<L, U> mapRight(Function<R, U> bind)
		{
			return (Either<L, U>) this;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <U, V> Either<U, V> map(Function<L, U> bindLeft, Function<R, V> bindRight)
		{
			return (Either<U, V>) mapLeft(bindLeft);
		}
		
		@Override
		public String toString()
		{
			return "(" + left + ", ∅)";
		}
	}
	
	private static final class Right<L, R> extends Either<L, R>
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
		R right()
		{
			return right;
		}
		
		@Override
		public <Ret> Ret fold(Function<L, Ret> leftMapper, Function<R, Ret> rightMapper)
		{
			return rightMapper.apply(right);
		}
		
		@Override
		public void forEither(Consumer<L> left, Consumer<R> right)
		{
			right.accept(this.right);
		}
		
		@Override
		public Opt<L> getLeft()
		{
			return Opt.empty();
		}
		
		@Override
		public boolean isLeft()
		{
			return false;
		}
		
		@Override
		public Opt<R> getRight()
		{
			return Opt.of(right);
		}
		
		@Override
		public boolean isRight()
		{
			return true;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <U> Either<U, R> mapLeft(Function<L, U> bind)
		{
			return (Either<U, R>) this;
		}
		
		@Override
		public <U> Either<L, U> mapRight(Function<R, U> bind)
		{
			return right(bind.apply(right));
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <U, V> Either<U, V> map(Function<L, U> bindLeft, Function<R, V> bindRight)
		{
			return (Either<U, V>) mapRight(bindRight);
		}
		
		@Override
		public String toString()
		{
			return "(∅, " + right + ")";
		}
	}
}