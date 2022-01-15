package local.pixy.conwaysgame.math;

import java.util.function.Function;

import local.pixy.conwaysgame.world.Chunk;

/**
 * Describes a block's position.
 * 
 * @author pixy
 */
public BlockPos extends BasicVector{
	public BlockPos add(BlockPos offset) {
		return new BlockPos(this.x + offset.x, this.y + offset.y);
	}

	public BlockPos divide(BlockPos div) {
		return new BlockPos((int) this.x / div.x, (int) this.y / div.y);
	}

	public BlockPos mod(BlockPos div) {
		return new BlockPos(this.x % div.x, this.y % div.y);
	}

	public ChunkPos getChunkPos() {
		Function<Integer, Integer> op = i -> (i < 0 ? (i - Chunk.SIZE - 1) / Chunk.SIZE : i / Chunk.SIZE);
		return new ChunkPos(op.apply(this.x), op.apply(this.y));
	}

	public BlockPos getBlockPosChunk() {
		Function<Integer, Integer> op = (i) -> (i < 0 ? (i + 1) % Chunk.SIZE + Chunk.SIZE - 1 : i % Chunk.SIZE);
		return new BlockPos(op.apply(this.x), op.apply(this.y));
	}
}
