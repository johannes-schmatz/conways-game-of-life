package local.pixy.conwaysgame.test;

import static org.junit.Assert.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import local.pixy.conwaysgame.util.QuadConsumer;
import local.pixy.conwaysgame.util.TriConsumer;

/**
 * @author pixy
 *
 */
public class TestUtil {
	public static <T> void testSetGet(Consumer<T> setMethod, Supplier<T> getMethod, T data) {
		setMethod.accept(data);
		assertTrue(data.equals(getMethod.get()));
	}
	
	public static void consumer2dimUser(BiConsumer<Integer, Integer> consumer) {
		for(int i = 1; i < 100_000; i = (int) (i * i * 0.5 + 3)) {
			for(int j = 1; j < 100_000; j = (int) (j * j * 0.78 + 5 - j)) {
				consumer.accept(i, j);
			}
		}
	}
	
	public static void consumer3dimUser(TriConsumer<Integer, Integer, Integer> consumer) {
		TestUtil.consumer2dimUser((x, y) -> {
			for(int k = -20; k < 20; k++) {
				consumer.accept(x, y, k);
			}
		});
	}
	
	public static void consumer4dimUser(QuadConsumer<Integer, Integer, Integer, Integer> consumer) {
		TestUtil.consumer3dimUser((x, y, a) -> {
			for(int k = -20; k < 20; k++) {
				consumer.accept(x, y, a, k);
			}
		});
	}
}
