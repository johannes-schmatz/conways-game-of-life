package local.pixy.conwaysgame.test;

import static org.junit.Assert.*;

import org.junit.Test;

import local.pixy.conwaysgame.math.BasicVector;
import local.pixy.conwaysgame.math.Direction;

/**
 * @author pixy
 *
 */
public class BasicVectorTest {
	private class Vector extends BasicVector<BasicVectorTest.Vector> {
		public Vector(int x, int y) {
			super(x, y);
		}

		@Override
		public Vector from(int x, int y) {
			return new Vector(x, y);
		}

	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.math.BasicVector#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		TestUtil.consumer2dimUser((x, y) -> {
			int hc1 = new Vector(x, y).hashCode();
			int hc2 = new Vector(x, y).hashCode();
			assertEquals(hc1, hc2);
		});
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.math.BasicVector#BasicVector(int, int)}.
	 */
	@Test
	public void testBasicVector() {
		TestUtil.consumer2dimUser((x, y) -> {
			Vector v = new Vector(x, y);
			assertEquals(v.getX(), x.intValue());
			assertEquals(v.getY(), y.intValue());
		});
	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.math.BasicVector#add(int)}.
	 */
	@Test
	public void testAddInt() {
		TestUtil.consumer3dimUser((x, y, n) -> {
			Vector v1 = new Vector(x, y);
			Vector v2 = v1.add(n);
			
			assertEquals(x + n, v2.getX());
			assertEquals(y + n, v2.getY());
		});
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.math.BasicVector#add(local.pixy.conwaysgame.math.BasicVector)}.
	 */
	@Test
	public void testAddT() {
		TestUtil.consumer4dimUser((x, y, i, j) -> {
			Vector v1 = new Vector(x, y);
			Vector v2 = new Vector(i, j);
			Vector v3 = v1.add(v2);
			
			assertEquals(x + i, v3.getX());
			assertEquals(y + j, v3.getY());
		});
	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.math.BasicVector#divide(int)}.
	 */
	@Test
	public void testDivideInt() {
		TestUtil.consumer3dimUser((x, y, n) -> {
			if(n == 0)
				return;
			
			Vector v1 = new Vector(x, y);
			Vector v2 = v1.divide(n);
			
			assertEquals((int) x / n, v2.getX());
			assertEquals((int) y / n, v2.getY());
		});
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.math.BasicVector#divide(local.pixy.conwaysgame.math.BasicVector)}.
	 */
	@Test
	public void testDivideT() {
		TestUtil.consumer4dimUser((x, y, i, j) -> {
			if(i == 0 || j == 0)
				return;
			
			Vector v1 = new Vector(x, y);
			Vector v2 = new Vector(i, j);
			Vector v3 = v1.divide(v2);
			
			assertEquals((int) x / i, v3.getX());
			assertEquals((int) y / j, v3.getY());
		});
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.math.BasicVector#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		int x = 2022, y = 598;
		Vector v1 = new Vector(x--, y--);
		Vector v2 = new Vector(x++, y++);
		Vector v3 = new Vector(++x, ++y);
		Vector v4 = new Vector(--x, --y);
		assertFalse(v1.equals(null));
		assertFalse(v2.equals(null));
		assertFalse(v3.equals(null));
		
		assertFalse(v1.equals(v2));
		assertFalse(v1.equals(v3));
		assertTrue(v1.equals(v4));
		assertFalse(v1 == v4);
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.math.BasicVector#from(int, int)}. Also tests
	 * {@link local.pixy.conwaysgame.math.BasicVector#getX()} and
	 * {@link local.pixy.conwaysgame.math.BasicVector#getY()}.
	 */
	@Test
	public void testFrom() {
		TestUtil.consumer2dimUser((x, y) -> {
			Vector v1 = new Vector(x, y);
			Vector v2 = v1.from(x, y);
			assertEquals(v1.getX(), v2.getX());
			assertEquals(v1.getY(), v2.getY());
		});
	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.math.BasicVector#invert()}.
	 */
	@Test
	public void testInvert() {
		TestUtil.consumer2dimUser((x, y) -> {
			Vector v1 = new Vector(x, y);
			Vector v2 = v1.invert();
			Vector v3 = v1.add(v2);

			assertEquals(-x, v2.getX());
			assertEquals(-y, v2.getY());

			assertEquals(0, v3.getX());
			assertEquals(0, v3.getY());
		});
	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.math.BasicVector#modulo(int)}.
	 */
	@Test
	public void testModuloInt() {
		TestUtil.consumer3dimUser((x, y, n) -> {
			if(n == 0)
				return;
			
			Vector v1 = new Vector(x, y);
			Vector v2 = v1.modulo(n);
			
			assertEquals(x % n, v2.getX());
			assertEquals(y % n, v2.getY());
		});
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.math.BasicVector#modulo(local.pixy.conwaysgame.math.BasicVector)}.
	 */
	@Test
	public void testModuloT() {
		TestUtil.consumer4dimUser((x, y, i, j) -> {
			if(i == 0 || j == 0)
				return;
			
			Vector v1 = new Vector(x, y);
			Vector v2 = new Vector(i, j);
			Vector v3 = v1.modulo(v2);
			
			assertEquals(x % i, v3.getX());
			assertEquals(y % j, v3.getY());
		});
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.math.BasicVector#multiply(int)}.
	 */
	@Test
	public void testMultiplyInt() {
		TestUtil.consumer3dimUser((x, y, n) -> {
			Vector v1 = new Vector(x, y);
			Vector v2 = v1.multiply(n);
			
			assertEquals(x * n, v2.getX());
			assertEquals(y * n, v2.getY());
		});
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.math.BasicVector#multiply(local.pixy.conwaysgame.math.BasicVector)}.
	 */
	@Test
	public void testMultiplyT() {
		TestUtil.consumer4dimUser((x, y, i, j) -> {
			Vector v1 = new Vector(x, y);
			Vector v2 = new Vector(i, j);
			Vector v3 = v1.multiply(v2);
			
			assertEquals(x * i, v3.getX());
			assertEquals(y * j, v3.getY());
		});
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.math.BasicVector#offset(local.pixy.conwaysgame.math.Direction)}.
	 */
	@Test
	public void testOffset() {
		int x = 3078, y = 98723;
		Vector v1 = new Vector(x, y);
		Vector v2;
		for(Direction i : Direction.values()) {
			v2 = v1.offset(i);
			assertEquals(x + i.x, v2.getX());
			assertEquals(y + i.y, v2.getY());
		}
	}
}
