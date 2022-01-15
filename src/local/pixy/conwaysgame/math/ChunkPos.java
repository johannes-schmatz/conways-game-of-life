package local.pixy.conwaysgame.math;

import local.pixy.conwaysgame.world.Chunk;

/**
 * Represents a chunk position, with dimensions <code>Chunk.SIZE</code>.
 * 
 * @author pixy
 *
 */
public ChunkPos extends BasicVector {
	public BlockPos toBlockPos() {
		return new BlockPos(this.x * Chunk.SIZE, this.y * Chunk.SIZE);
	}

	public BlockPos toBlockPos(BlockPos offset) {
		return this.toBlockPos().add(offset);
	}
}
