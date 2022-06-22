package local.pixy.conwaysgame.test;

import static org.junit.Assert.*;

import org.junit.Test;

import local.pixy.conwaysgame.block.Blocks;
import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.Direction;
import local.pixy.conwaysgame.world.IChunk;
import local.pixy.conwaysgame.world.IWorld;
import local.pixy.conwaysgame.world.LoadLevel;
import local.pixy.conwaysgame.world.World;

/**
 * Generic test class for {@linkplain local.pixy.conwaysgame}
 * 
 * @author pixy
 *
 */
public class TestClass {

	@SuppressWarnings("javadoc")
	@Test
	public void abstractWorld() {
		IWorld world = new World();
		BlockPos pos = new BlockPos(10, 10);

		world.setBlock(pos.offset(Direction.NORTH), Blocks.ALIVE);
		world.setBlock(pos, Blocks.ALIVE);
		world.setBlock(pos.offset(Direction.SOUTH), Blocks.ALIVE);

		IChunk chunk = world.getChunk(pos.getChunkPos());

		assertEquals(LoadLevel.FULL, chunk.getLoadLevel());
		assertEquals(Blocks.ALIVE, world.getBlock(pos));
	}
}
