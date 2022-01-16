package local.pixy.conwaysgame.math;

import java.util.function.Function;

import local.pixy.conwaysgame.world.Chunk;

/**
 * Describes a block's position.
 * 
 * @author pixy
 */
public class BlockPos extends BasicVector<BlockPos> {
	/**
	 * Creates a new {@link BlockPos}.
	 * 
	 * @param x The x-value of this instance.
	 * @param y The y-value of this instance.
	 */
	public BlockPos(int x, int y) {
		super(x, y);
	}

	/**
	 * Generates a {@link ChunkPos} instance, with coordinates converted, so that
	 * the chunk at 0,0 starts at 0,0.
	 * 
	 * @return The {@link ChunkPos} instance.
	 */
	public ChunkPos getChunkPos() {
		Function<Integer, Integer> op = i -> (i < 0 ? (i - Chunk.SIZE - 1) / Chunk.SIZE : i / Chunk.SIZE);
		return new ChunkPos(op.apply(this.getX()), op.apply(this.getY()));
	}

	/**
	 * Generates a new {@link BlockPos} instance, that is converted down to only
	 * contain position within one {@link local.pixy.conwaysgame.world.IChunk}.
	 * 
	 * @return The generated {@link BlockPos} instance.
	 */
	public BlockPos getBlockPosChunk() {
		Function<Integer, Integer> op = (i) -> (i < 0 ? (i + 1) % Chunk.SIZE + Chunk.SIZE - 1 : i % Chunk.SIZE);
		return new BlockPos(op.apply(this.getX()), op.apply(this.getY()));
	}

	@Override
	public BlockPos from(int x, int y) {
		return new BlockPos(x, y);
	}
}
