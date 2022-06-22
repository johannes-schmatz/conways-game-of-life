package local.pixy.conwaysgame.test;

import static org.junit.Assert.*;

import org.junit.Test;

import local.pixy.conwaysgame.world.LoadLevel;

/**
 * @author pixy
 *
 */
public class LoadLevelTest {

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.world.LoadLevel#fromNumber(int)}.
	 */
	@Test
	public void testFromNumber() {
		int i = 0;
		for (LoadLevel ll : LoadLevel.values) {
			assertEquals(ll, LoadLevel.fromNumber(i++));
		}
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.world.LoadLevel#getHigherLevel(local.pixy.conwaysgame.world.LoadLevel, local.pixy.conwaysgame.world.LoadLevel)}.
	 */
	@Test
	public void testGetHigherLevel() {
		int i = 0;
		for (LoadLevel l1 : LoadLevel.values) {
			int j = 0;
			for (LoadLevel l2 : LoadLevel.values) {
				assertEquals(i > j++ ? l1 : l2, LoadLevel.getHigherLevel(l1, l2));
			}
			i++;
		}
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.world.LoadLevel#getLoadLevelLess(local.pixy.conwaysgame.world.LoadLevel, int)}.
	 */
	@Test
	public void testGetLoadLevelLess() {
		for (LoadLevel l1 : LoadLevel.values) {
			for (LoadLevel l2 : LoadLevel.values) {
				int l2level = l2.getLevel();
				int actual = LoadLevel.getLoadLevelLess(l1, l2level).getLevel();
				int expected = l1.getLevel() - l2level;
				if (expected < 0)
					expected = 0;
				assertEquals(expected, actual);
			}
		}
	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.world.LoadLevel#getLevel()}.
	 */
	@Test
	public void testGetLevel() {
		int i = 0;
		for (LoadLevel ll : LoadLevel.values) {
			assertEquals(i, LoadLevel.fromNumber(i).getLevel());
			assertEquals(i++, ll.getLevel());
		}
	}

}
