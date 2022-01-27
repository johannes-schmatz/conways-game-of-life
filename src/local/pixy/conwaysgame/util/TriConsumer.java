package local.pixy.conwaysgame.util;

import java.util.Objects;

/**
 * @author pixy
 *
 */
@FunctionalInterface
public interface TriConsumer<S1, S2, S3> {
	void accept(S1 a, S2 b, S3 c);
    default TriConsumer<S1, S2, S3> andThen(TriConsumer<? super S1, ? super S2, ? super S3> after) {
        Objects.requireNonNull(after);

        return (a, b, c) -> {
            accept(a, b, c);
            after.accept(a, b, c);
        };
    }
}
