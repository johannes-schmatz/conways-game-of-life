package local.pixy.conwaysgame.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Represents an operation that accepts two input arguments and returns no
 * result. This is the three-arity specialization of {@link Consumer}. Unlike
 * most other functional interfaces, {@code TriConsumer} is expected to operate
 * via side-effects.
 *
 * <p>
 * This is a {@linkplain FunctionalInterface} whose functional method is
 * {@link #accept(Object, Object, Object)}.
 *
 * @param <S1> the type of the first argument to the operation
 * @param <S2> the type of the second argument to the operation
 * @param <S3> the type of the third argument to the operation
 *
 * @see Consumer
 * @see BiConsumer
 * @see QuadConsumer
 * 
 * @author pixy
 */
@FunctionalInterface
public interface TriConsumer<S1, S2, S3> {
	/**
	 * Performs this operation on the given arguments.
	 *
	 * @param a the first input argument
	 * @param b the second input argument
	 * @param c the third input argument
	 */
	void accept(S1 a, S2 b, S3 c);
}
