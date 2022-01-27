package local.pixy.conwaysgame.util;

import java.util.Objects;

/**
 * @author pixy
 *
 */
@FunctionalInterface
public interface QuadConsumer<S1, S2, S3, S4> {
	void accept(S1 a, S2 b, S3 c, S4 d);
    default QuadConsumer<S1, S2, S3, S4> andThen(QuadConsumer<? super S1, ? super S2, ? super S3, ? super S4> after) {
        Objects.requireNonNull(after);

        return (a, b, c, d) -> {
            accept(a, b, c, d);
            after.accept(a, b, c, d);
        };
    }
}
