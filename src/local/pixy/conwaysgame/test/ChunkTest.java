package local.pixy.conwaysgame.test;

import static org.junit.Assert.*;

import org.junit.Test;

import local.pixy.conwaysgame.world.Chunk;
import local.pixy.conwaysgame.world.LoadLevel;
import local.pixy.conwaysgame.world.World;

/**
 * @author pixy
 *
 */
public class ChunkTest {

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.world.Chunk#Chunk(local.pixy.conwaysgame.world.World, local.pixy.conwaysgame.math.ChunkPos)}.
	 */
	@Test
	public void testChunk() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.world.Chunk#getBlock(local.pixy.conwaysgame.math.BlockPos)}.
	 */
	@Test
	public void testGetBlock() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.world.Chunk#getBlockNoLoadCheck(local.pixy.conwaysgame.math.BlockPos)}.
	 */
	@Test
	public void testGetBlockNoLoadCheck() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.world.Chunk#getContent()}.
	 */
	@Test
	public void testGetContent() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.world.Chunk#getLoadLevel()} and
	 * {@link local.pixy.conwaysgame.world.Chunk#setLoadLevel(local.pixy.conwaysgame.world.LoadLevel)}.
	 */
	@Test
	public void testLoadLevel() {
		Chunk obj = new Chunk(null, null);
		for(LoadLevel i : LoadLevel.values())
			TestUtil.<LoadLevel>testSetGet(obj::setLoadLevel, obj::getLoadLevel, i);
	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.world.Chunk#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.world.Chunk#setBlock(local.pixy.conwaysgame.math.BlockPos, local.pixy.conwaysgame.block.IBlockState)}.
	 */
	@Test
	public void testSetBlock() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.world.Chunk#tick()}.
	 */
	@Test
	public void testTick() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.world.Chunk#tickCalculate()}.
	 */
	@Test
	public void testTickCalculate() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.world.Chunk#tickUpdateState()}.
	 */
	@Test
	public void testTickUpdateState() {
//		fail("Not yet implemented");
	}
}
