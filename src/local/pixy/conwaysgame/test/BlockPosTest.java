package local.pixy.conwaysgame.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.ChunkPos;

/**
 * @author pixy
 *
 */
public class BlockPosTest {
	private final int testSize = 50;
	private final int chunkSize = 16;
	private final int n = 5;
	private final int start = -n * chunkSize;

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.math.BlockPos#chunkBlockIterator()}.
	 */
	@Test
	public void testChunkBlockIterator() {
		Set<BlockPos> notCheckedYet = new HashSet<>();

		assertTrue(notCheckedYet.isEmpty());
		
		Iterator<BlockPos> iter = BlockPos.chunkBlockIterator();
		while (iter.hasNext())
			notCheckedYet.add(iter.next());

		assertFalse(notCheckedYet.isEmpty());

		iter = BlockPos.chunkBlockIterator(new ChunkPos(0, 0));
		while (iter.hasNext())
			notCheckedYet.remove(iter.next());

		assertTrue(notCheckedYet.isEmpty());
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.math.BlockPos#chunkBlockIterator(local.pixy.conwaysgame.math.ChunkPos)}.
	 */
	@Test
	public void testChunkBlockIteratorChunkPos() {
		int x = 3, y = 56;
		Set<BlockPos> notCheckedYet = new HashSet<>();
		
		assertTrue(notCheckedYet.isEmpty());

		for (int i = 0; i < this.chunkSize; i++) {
			for (int j = 0; j < this.chunkSize; j++) {
				notCheckedYet.add(new BlockPos(x * this.chunkSize + i, y * this.chunkSize + j));
			}
		}

		assertFalse(notCheckedYet.isEmpty());

		Iterator<BlockPos> iter = BlockPos.chunkBlockIterator(new ChunkPos(x, y));
		while (iter.hasNext()) {
			notCheckedYet.remove(iter.next());
		}

		assertTrue(notCheckedYet.isEmpty());
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.math.BlockPos#BlockPos(int, int)}.
	 */
	@Test
	public void testBlockPos() {
		for(int i = this.start; i < this.testSize; i++) {
			for(int j = this.start; j < this.testSize; j++) {
				BlockPos pos = new BlockPos(i, j);
				assertTrue(i == pos.getX());
				assertTrue(j == pos.getY());
			}
		}
	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.math.BlockPos#from(int, int)}.
	 */
	@Test
	public void testFromIntInt() {
		for (int x = this.start; x < this.testSize; x++) {
			for (int y = this.start; y < this.testSize; y++) {
				assertEquals(new BlockPos(x, y), new BlockPos(-1, 2).from(x, y));
				assertEquals(new BlockPos(x, y), new BlockPos(2, -1).from(x, y));
				assertEquals(new BlockPos(x, y), new BlockPos(2, 2).from(x, y));
				assertEquals(new BlockPos(x, y), new BlockPos(-1, -1).from(x, y));
			}
		}
	}

	/**
	 * Test method for
	 * {@link local.pixy.conwaysgame.math.BlockPos#getBlockPosChunk()}. This method
	 * first tries some random values, then tests with some for loops.
	 */
	@Test
	public void testGetBlockPosChunk() {
		assertEquals(new BlockPos(1, 2), new BlockPos(1, 2).getBlockPosChunk());
		assertEquals(new BlockPos(1, 4), new BlockPos(17, 20).getBlockPosChunk());
		assertEquals(new BlockPos(0, 15), new BlockPos(32, 15).getBlockPosChunk());
		assertEquals(new BlockPos(0, 0), new BlockPos(0, 0).getBlockPosChunk());
		assertEquals(new BlockPos(15, 0), new BlockPos(-1, 0).getBlockPosChunk());
		assertEquals(new BlockPos(0, 15), new BlockPos(0, -1).getBlockPosChunk());
		assertEquals(new BlockPos(0, 0), new BlockPos(0, -16).getBlockPosChunk());
		assertEquals(new BlockPos(0, 0), new BlockPos(-16, -16).getBlockPosChunk());

		int x = 0, y = 0;
		for (int i = this.start; i < this.testSize; i++) {
			for (int j = this.start; j < this.testSize; j++) {
				assertEquals(new BlockPos(x, y), new BlockPos(i, j).getBlockPosChunk());

				if (++y == this.chunkSize) {
					y = 0;
				}
			}
			y = 0;
			if (++x == this.chunkSize) {
				x = 0;
			}
		}
	}

	/**
	 * Test method for {@link local.pixy.conwaysgame.math.BlockPos#getChunkPos()}.
	 * This method first tries some random values, then test with some for loops the
	 * behavior.
	 */
	@Test
	public void testGetChunkPos() {
		assertEquals(new ChunkPos(0, 0), new BlockPos(1, 2).getChunkPos());
		assertEquals(new ChunkPos(1, 1), new BlockPos(17, 20).getChunkPos());
		assertEquals(new ChunkPos(2, 0), new BlockPos(32, 15).getChunkPos());
		assertEquals(new ChunkPos(0, 0), new BlockPos(0, 0).getChunkPos());
		assertEquals(new ChunkPos(-1, 0), new BlockPos(-1, 0).getChunkPos());
		assertEquals(new ChunkPos(0, -1), new BlockPos(0, -1).getChunkPos());
		assertEquals(new ChunkPos(0, -1), new BlockPos(0, -16).getChunkPos());
		assertEquals(new ChunkPos(-1, -1), new BlockPos(-16, -16).getChunkPos());

		int x = -this.n, y = -this.n;
		int a = 0, b = 0;
		for (int i = this.start; i < this.testSize; i++) {
			b = 0;
			y = -n;
			for (int j = this.start; j < 100; j++) {
				assertEquals(new ChunkPos(x, y), new BlockPos(i, j).getChunkPos());

				if (++b == this.chunkSize) {
					b = 0;
					y++;
				}
			}
			if (++a == this.chunkSize) {
				a = 0;
				x++;
			}
		}
	}
}
