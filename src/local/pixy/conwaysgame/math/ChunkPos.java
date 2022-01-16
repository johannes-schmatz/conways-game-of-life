package local.pixy.conwaysgame.math;

import local.pixy.conwaysgame.world.Chunk;

/**
 * Describes a chunk position, with dimensions <code>Chunk.SIZE</code> on both
 * axis.
 * 
 * @author pixy
 */
public class ChunkPos extends BasicVector<ChunkPos> {
	/**
	 * Create a new {@link ChunkPos}.
	 * 
	 * @param x The x-value of this instance.
	 * @param y The y-value of this instance.
	 */
	public ChunkPos(int x, int y) {
		super(x, y);
	}

	/**
	 * Function to get the chunks 0,0 as a {@link BlockPos}.
	 * 
	 * @return The {@link BlockPos} that describes the chunk's origin.
	 */
	public BlockPos toBlockPos() {
		return new BlockPos(this.getX() * Chunk.SIZE, this.getY() * Chunk.SIZE);
	}

	/**
	 * Function to get a absolute block {@link BlockPos} from a relative
	 * {@link BlockPos} and the {@link ChunkPos} instance.
	 * 
	 * @param offset The relative {@link BlockPos} from the origin of the chunk.
	 * @return The absolute {@link BlockPos} value of the block.
	 */
	public BlockPos toBlockPos(BlockPos offset) {
		return new BlockPos(this.getX() * Chunk.SIZE + offset.getX(), this.getY() * Chunk.SIZE + offset.getY());
	}

	@Override
	public ChunkPos from(int x, int y) {
		return new ChunkPos(x, y);
	}
}
