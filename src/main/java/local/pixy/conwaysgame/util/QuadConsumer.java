package local.pixy.conwaysgame.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Represents an operation that accepts two input arguments and returns no
 * result. This is the three-arity specialization of {@link Consumer}. Unlike
 * most other functional interfaces, {@code QuadConsumer} is expected to operate
 * via side-effects.
 *
 * <p>
 * This is a {@linkplain FunctionalInterface} whose functional method is
 * {@link #accept(Object, Object, Object, Object)}.
 *
 * @param <S1> the type of the first argument to the operation
 * @param <S2> the type of the second argument to the operation
 * @param <S3> the type of the third argument to the operation
 * @param <S4> the type of the fourth argument to the operation
 *
 * @see Consumer
 * @see BiConsumer
 * @see TriConsumer
 * 
 * @author pixy
 */
@FunctionalInterface
public interface QuadConsumer<S1, S2, S3, S4> {
	/**
	 * Performs this operation on the given arguments.
	 *
	 * @param a the first input argument
	 * @param b the second input argument
	 * @param c the third input argument
	 * @param d the fourth input argument
	 */
	void accept(S1 a, S2 b, S3 c, S4 d);
}
