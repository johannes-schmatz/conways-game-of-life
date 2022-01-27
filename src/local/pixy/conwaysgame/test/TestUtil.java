package local.pixy.conwaysgame.test;

import static org.junit.Assert.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import local.pixy.conwaysgame.util.QuadConsumer;
import local.pixy.conwaysgame.util.TriConsumer;

/**
 * @author pixy
 *
 */
public class TestUtil {
	private static final int xStart = 1;
	private static final int xEnd = 100_000;
	private static final Function<Integer, Integer> xOperation = (x) -> (int) (x * x * 0.5 + 3);
	private static final int yStart = 1;
	private static final int yEnd = 100_000;
	private static final Function<Integer, Integer> yOperation = (x) -> (int) (x * x * 0.78 + 5 - x);
	private static final int zStart = -20;
	private static final int zEnd = 20;
	private static final Function<Integer, Integer> zOperation = (x) -> (int) (x + 1);
	private static final int wStart = -20;
	private static final int wEnd = 20;
	private static final Function<Integer, Integer> wOperation = (x) -> (int) (x + 1);

	/**
	 * Calls first a setter, then a getter, also verifies that the data that data is
	 * {@linkplain Object#equals(Object)} to value given by the getter.
	 * 
	 * @param <T>       The type to give the set method and read from the getter.
	 *                  The data input has also this type.
	 * @param setMethod A reference to a setter.
	 * @param getMethod A reference to a getter.
	 * @param data      The data to set and read later with the getter.
	 */
	public static <T> void testSetGet(Consumer<T> setMethod, Supplier<T> getMethod, T data) {
		setMethod.accept(data);
		assertTrue(data.equals(getMethod.get()));
	}

	/**
	 * Calls a consumer multiple times with different values.
	 * 
	 * @param consumer A consumer to call
	 * @see TestUtil#consumer2dimUser(BiConsumer)
	 * @see TestUtil#consumer3dimUser(TriConsumer)
	 * @see TestUtil#consumer4dimUser(QuadConsumer)
	 */
	public static void consumer1dimUser(Consumer<Integer> consumer) {
		for (int x = TestUtil.xStart; x < TestUtil.xEnd; x = TestUtil.xOperation.apply(x)) {
			consumer.accept(x);
		}
	}

	/**
	 * Calls a consumer multiple times with different values.
	 * 
	 * @param consumer A consumer to call.
	 * @see TestUtil#consumer1dimUser(Consumer)
	 * @see TestUtil#consumer3dimUser(TriConsumer)
	 * @see TestUtil#consumer4dimUser(QuadConsumer)
	 */
	public static void consumer2dimUser(BiConsumer<Integer, Integer> consumer) {
		TestUtil.consumer1dimUser((x) -> {
			for (int y = TestUtil.yStart; y < TestUtil.yEnd; y = TestUtil.yOperation.apply(y)) {
				consumer.accept(x, y);
			}
		});
	}

	/**
	 * Calls a consumer multiple times with different values.
	 * 
	 * @param consumer A consumer to call
	 * @see TestUtil#consumer1dimUser(Consumer)
	 * @see TestUtil#consumer2dimUser(BiConsumer)
	 * @see TestUtil#consumer4dimUser(QuadConsumer)
	 */
	public static void consumer3dimUser(TriConsumer<Integer, Integer, Integer> consumer) {
		TestUtil.consumer2dimUser((x, y) -> {
			for (int z = TestUtil.zStart; z < TestUtil.zEnd; z = TestUtil.zOperation.apply(z)) {
				consumer.accept(x, y, z);
			}
		});
	}

	/**
	 * Calls a consumer multiple times with different values.
	 * 
	 * @param consumer A consumer to call.
	 * @see TestUtil#consumer1dimUser(Consumer)
	 * @see TestUtil#consumer2dimUser(BiConsumer)
	 * @see TestUtil#consumer3dimUser(TriConsumer)
	 */
	public static void consumer4dimUser(QuadConsumer<Integer, Integer, Integer, Integer> consumer) {
		TestUtil.consumer3dimUser((x, y, z) -> {
			for (int w = TestUtil.wStart; w < TestUtil.wEnd; w = TestUtil.wOperation.apply(w)) {
				consumer.accept(x, y, z, w);
			}
		});
	}
}
