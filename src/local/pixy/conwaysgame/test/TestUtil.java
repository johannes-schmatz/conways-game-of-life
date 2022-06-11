package local.pixy.conwaysgame.test;

import static org.junit.Assert.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import local.pixy.conwaysgame.util.QuadConsumer;
import local.pixy.conwaysgame.util.TriConsumer;

/**
 * A class that contains function to loop over several dimension and call a
 * {@linkplain Consumer}. It also contains a function to assert that a getter
 * returns a value that is set by the setter before.
 * 
 * @see #consumer1dimUser(Consumer)
 * @see #testSetGet(Consumer, Supplier, Object)
 * 
 * @author pixy
 */
public class TestUtil {
	/**
	 * A configuration for looping over multiple dimension inside each other.
	 * 
	 * @author pixy
	 */
	public static class TestConfig {
		/**
		 * The end value for the fourth dimension. This value will never be given to the
		 * {@link Consumer}.
		 */
		public int wEnd = 20;
		/**
		 * An operation to do to get the next value for the fourth dimension.
		 */
		public Function<Integer, Integer> wOperation = (x) -> (int) (x + 1);
		/**
		 * The start value for the fourth dimension.
		 */
		public int wStart = -20;
		/**
		 * The end value for the first dimension. This value will never be given to the
		 * {@link Consumer}.
		 */
		public int xEnd = 100_000;
		/**
		 * An operation to do to get the next value for the first dimension.
		 */
		public Function<Integer, Integer> xOperation = (x) -> (int) (x * x * 0.5 + 3);
		/**
		 * The start value for the first dimension.
		 */
		public int xStart = 1;
		/**
		 * The end value for the second dimension. This value will never be given to the
		 * {@link Consumer}.
		 */
		public int yEnd = 100_000;
		/**
		 * An operation to do to get the next value for the second dimension.
		 */
		public Function<Integer, Integer> yOperation = (x) -> (int) (x * x * 0.78 + 5 - x);
		/**
		 * The start value for the second dimension.
		 */
		public int yStart = 1;
		/**
		 * The end value for the third dimension. This value will never be given to the
		 * {@link Consumer}.
		 */
		public int zEnd = 20;
		/**
		 * An operation to do to get the next value for the third dimension.
		 */
		public Function<Integer, Integer> zOperation = (x) -> (int) (x + 1);
		/**
		 * The start value for the third dimension.
		 */
		public int zStart = -20;
	}

	/**
	 * Calls a consumer multiple times with different values. The config used is
	 * {@linkplain TestConfig}.
	 * 
	 * @param consumer A consumer to call.
	 * @see TestConfig
	 * @see TestUtil#consumer1dimUser(TestConfig, Consumer)
	 */
	public static void consumer1dimUser(Consumer<Integer> consumer) {
		TestUtil.consumer1dimUser(new TestConfig(), consumer);
	}

	/**
	 * Calls a consumer multiple times with different values.
	 * 
	 * @param config   The {@linkplain TestUtil.TestConfig} that contains the config
	 *                 for the iteration.
	 * @param consumer A consumer to call
	 * @see TestUtil#consumer2dimUser(TestConfig, BiConsumer)
	 * @see TestUtil#consumer3dimUser(TestConfig, TriConsumer)
	 * @see TestUtil#consumer4dimUser(TestConfig, QuadConsumer)
	 * @see TestUtil#consumer1dimUser(Consumer)
	 */
	public static void consumer1dimUser(TestConfig config, Consumer<Integer> consumer) {
		for (int x = config.xStart; x < config.xEnd; x = config.xOperation.apply(x)) {
			consumer.accept(x);
		}
	}

	/**
	 * Calls a consumer multiple times with different values. The config used is
	 * {@linkplain TestConfig}.
	 * 
	 * @param consumer A consumer to call.
	 * @see TestConfig
	 * @see TestUtil#consumer2dimUser(TestConfig, BiConsumer)
	 */
	public static void consumer2dimUser(BiConsumer<Integer, Integer> consumer) {
		TestUtil.consumer2dimUser(new TestConfig(), consumer);
	}

	/**
	 * Calls a consumer multiple times with different values.
	 * 
	 * @param config   The {@linkplain TestUtil.TestConfig} that contains the config
	 *                 for the iteration.
	 * @param consumer A consumer to call.
	 * @see TestUtil#consumer1dimUser(TestConfig, Consumer)
	 * @see TestUtil#consumer3dimUser(TestConfig, TriConsumer)
	 * @see TestUtil#consumer4dimUser(TestConfig, QuadConsumer)
	 * @see TestUtil#consumer2dimUser(BiConsumer)
	 */
	public static void consumer2dimUser(TestConfig config, BiConsumer<Integer, Integer> consumer) {
		TestUtil.consumer1dimUser(config, (x) -> {
			for (int y = config.yStart; y < config.yEnd; y = config.yOperation.apply(y)) {
				consumer.accept(x, y);
			}
		});
	}

	/**
	 * Calls a consumer multiple times with different values.
	 * 
	 * @param config   The {@linkplain TestUtil.TestConfig} that contains the config
	 *                 for the iteration.
	 * @param consumer A consumer to call
	 * @see TestUtil#consumer1dimUser(TestConfig, Consumer)
	 * @see TestUtil#consumer2dimUser(TestConfig, BiConsumer)
	 * @see TestUtil#consumer4dimUser(TestConfig, QuadConsumer)
	 * @see TestUtil#consumer3dimUser(TriConsumer)
	 */
	public static void consumer3dimUser(TestConfig config, TriConsumer<Integer, Integer, Integer> consumer) {
		TestUtil.consumer2dimUser(config, (x, y) -> {
			for (int z = config.zStart; z < config.zEnd; z = config.zOperation.apply(z)) {
				consumer.accept(x, y, z);
			}
		});
	}

	/**
	 * Calls a consumer multiple times with different values. The config used is
	 * {@linkplain TestConfig}.
	 * 
	 * @param consumer A consumer to call.
	 * @see TestConfig
	 * @see TestUtil#consumer3dimUser(TestConfig, TriConsumer)
	 */
	public static void consumer3dimUser(TriConsumer<Integer, Integer, Integer> consumer) {
		TestUtil.consumer3dimUser(new TestConfig(), consumer);
	}

	/**
	 * Calls a consumer multiple times with different values. The config used is
	 * {@linkplain TestConfig}.
	 * 
	 * @param consumer A consumer to call.
	 * @see TestConfig
	 * @see TestUtil#consumer4dimUser(TestConfig, QuadConsumer)
	 */
	public static void consumer4dimUser(QuadConsumer<Integer, Integer, Integer, Integer> consumer) {
		TestUtil.consumer4dimUser(new TestConfig(), consumer);
	}

	/**
	 * Calls a consumer multiple times with different values.
	 * 
	 * @param config   The {@linkplain TestUtil.TestConfig} that contains the config
	 *                 for the iteration.
	 * @param consumer A consumer to call.
	 * @see TestUtil#consumer1dimUser(TestConfig, Consumer)
	 * @see TestUtil#consumer2dimUser(TestConfig, BiConsumer)
	 * @see TestUtil#consumer3dimUser(TestConfig, TriConsumer)
	 * @see TestUtil#consumer4dimUser(QuadConsumer)
	 */
	public static void consumer4dimUser(TestConfig config, QuadConsumer<Integer, Integer, Integer, Integer> consumer) {
		TestUtil.consumer3dimUser(config, (x, y, z) -> {
			for (int w = config.wStart; w < config.wEnd; w = config.wOperation.apply(w)) {
				consumer.accept(x, y, z, w);
			}
		});
	}

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
}
