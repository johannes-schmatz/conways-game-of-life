package local.pixy.conwaysgame.test;

import static org.junit.Assert.*;

import org.junit.Test;

import local.pixy.conwaysgame.block.Block;
import local.pixy.conwaysgame.block.BlockState;
import local.pixy.conwaysgame.block.IBlock;
import local.pixy.conwaysgame.block.IBlockState;
import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.ChunkPos;
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
	public void blockPosOffset() {
		int a = 10, b = 20;
		BlockPos originalPos = new BlockPos(10, 20);
		for (Direction i : Direction.values())
			assertEquals(new BlockPos(a + i.x, b + i.y), originalPos.offset(i));
	};

	@SuppressWarnings("javadoc")
	@Test
	public void chunkPosOffset() {
		int a = 10, b = 20;
		ChunkPos originalPos = new ChunkPos(10, 20);
		for (Direction i : Direction.values())
			assertEquals(new ChunkPos(a + i.x, b + i.y), originalPos.offset(i));
	};

	@SuppressWarnings("javadoc")
	@Test
	public void blockState() {
		IBlockState bs1 = BlockState.newAlive();
		IBlockState bs2 = BlockState.newDead();
		IBlockState bs3 = BlockState.newDefault();

		assertTrue(bs1.is(BlockState.ALIVE));
		assertTrue(bs2.is(BlockState.DEAD));
		assertTrue(bs3.is(BlockState.DEAD));

		assertFalse(bs1.is(BlockState.DEAD));
		assertFalse(bs2.is(BlockState.ALIVE));
		assertFalse(bs3.is(BlockState.ALIVE));

		assertFalse(bs1.isNot(BlockState.ALIVE));
		assertFalse(bs2.isNot(BlockState.DEAD));
		assertFalse(bs3.isNot(BlockState.DEAD));

		assertTrue(bs1.isNot(BlockState.DEAD));
		assertTrue(bs2.isNot(BlockState.ALIVE));
		assertTrue(bs3.isNot(BlockState.ALIVE));

		assertTrue(bs1.is(bs1));
		assertTrue(bs2.is(bs2));
		assertTrue(bs3.is(bs3));

		assertTrue(bs2.is(bs3));
		assertTrue(bs3.is(bs2));

		assertTrue(bs1.isNot(bs2));
		assertTrue(bs2.isNot(bs1));

		assertTrue(bs1.isNot(bs3));
		assertTrue(bs3.isNot(bs1));

		assertFalse(bs1.is(bs2));
		assertFalse(bs2.is(bs1));

		assertFalse(bs1.is(bs3));
		assertFalse(bs3.is(bs1));

		IBlockState[] neightbours = { bs2, bs2, bs2, bs2, bs3, bs3, bs3, bs3, bs3 };

		bs1.tick();
		bs1.tickCalculate(neightbours);
		bs1.tickUpdateState();
	}

	@SuppressWarnings("javadoc")
	@Test
	public void block() {
		IBlockState bs1 = BlockState.newAlive();
		IBlock block1 = new Block(null, new BlockPos(0, 0), bs1);
		assertEquals(bs1, block1.getState());

		IBlockState bs2 = BlockState.newDead();
		IBlock block2 = new Block(null, new BlockPos(0, 0), bs2);
		assertEquals(bs2, block2.getState());

		IBlockState bs3 = BlockState.newDefault();
		IBlock block3 = new Block(null, new BlockPos(0, 0), bs3);
		assertEquals(bs3, block3.getState());

		IBlockState bs4 = BlockState.newAlive();
		IBlock block4 = new Block(new IWorld() {
			@Override
			public void tick() {
			}

			@Override
			public void setChunk(ChunkPos pos, IChunk chunk) {
			}

			@Override
			public void setBlock(BlockPos pos, IBlockState state) {
			}

			@Override
			public IChunk getChunk(ChunkPos pos) {
				return null;
			}

			@Override
			public IBlock getBlock(BlockPos pos) {
				return new Block(null, pos, BlockState.newAlive());
			}
		}, new BlockPos(0, 0), bs4);
		
		IBlockState[] states4 = block4.getNeightbourStates();
		for(int i = 0; i < states4.length; i++) {
			assertEquals(BlockState.newAlive(), states4[i]);
		}
		assertEquals(bs4, block4.getState());

		IBlockState bs5 = BlockState.newAlive();
		IBlock block5 = new Block(new IWorld() {
			@Override
			public void tick() {
			}

			@Override
			public void setChunk(ChunkPos pos, IChunk chunk) {
			}

			@Override
			public void setBlock(BlockPos pos, IBlockState state) {
			}

			@Override
			public IChunk getChunk(ChunkPos pos) {
				return null;
			}

			@Override
			public IBlock getBlock(BlockPos pos) {
				return new Block(null, pos, BlockState.newDead());
			}
		}, new BlockPos(0, 0), bs4);
		
		IBlockState[] states5 = block4.getNeightbourStates();
		for(int i = 0; i < states5.length; i++) {
			assertEquals(BlockState.newAlive(), states5[i]);
		}
		assertEquals(bs4, block4.getState());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void abstractWorld() {
		IWorld world = new World();
		BlockPos pos = new BlockPos(10, 10);

		world.setBlock(pos.offset(Direction.NORTH), BlockState.newAlive());
		world.setBlock(pos, BlockState.newAlive());
		world.setBlock(pos.offset(Direction.SOUTH), BlockState.newAlive());

		IChunk chunk = world.getChunk(pos.getChunkPos());

		assertEquals(LoadLevel.FULL, chunk.getLoadLevel());
		assertEquals(BlockState.newAlive(), world.getBlock(pos).getState());
	}
}
