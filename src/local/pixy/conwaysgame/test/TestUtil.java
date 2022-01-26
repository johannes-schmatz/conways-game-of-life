package local.pixy.conwaysgame.test;

import static org.junit.Assert.*;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author pixy
 *
 */
public class TestUtil {
	public static <T> void testSetGet(Consumer<T> setMethod, Supplier<T> getMethod, T data) {
		setMethod.accept(data);
		assertTrue(data.equals(getMethod.get()));
	}

}
