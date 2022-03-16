package local.pixy.conwaysgame.math;

import local.pixy.conwaysgame.world.Chunk;

/**
 * Describes a chunk position, with dimensions {@code Chunk.SIZE} on both axis.
 * 
 * @author pixy
 */
public class ChunkPos extends BasicVector<ChunkPos> {
	/**
	 * Create a new {@linkplain ChunkPos}.
	 * 
	 * @param x The x-value of this instance.
	 * @param y The y-value of this instance.
	 */
	public ChunkPos(int x, int y) {
		super(x, y);
	}

	/**
	 * Function to get the chunks 0,0 as a {@linkplain BlockPos}.
	 * 
	 * @return The {@linkplain BlockPos} that describes the chunk's origin.
	 */
	public BlockPos toBlockPos() {
		return new BlockPos(this.getX() * Chunk.SIZE, this.getY() * Chunk.SIZE);
	}

	/**
	 * Function to get a absolute block {@linkplain BlockPos} from a relative
	 * {@linkplain BlockPos} and the {@linkplain ChunkPos} instance.
	 * 
	 * @param offset The relative {@linkplain BlockPos} from the origin of the
	 *               chunk.
	 * @return The absolute {@linkplain BlockPos} value of the block.
	 */
	public BlockPos toBlockPos(BlockPos offset) {
		return new BlockPos(this.getX() * Chunk.SIZE + offset.getX(), this.getY() * Chunk.SIZE + offset.getY());
	}

	@Override
	public ChunkPos from(int x, int y) {
		return new ChunkPos(x, y);
	}
}
