package local.pixy.conwaysgame.world;

import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.ChunkPos;

/**
 * A interface that describes all methods that must be used.
 * 
 * @author pixy
 *
 */
public interface IChunk {
	/**
	 * The size of a IChunk. This should be used when the size of a chunk is
	 * required.
	 */
	static final int SIZE = 16;

	/**
	 * Gets a block by position. {@linkplain LoadLevel} matters.
	 * 
	 * @param pos The position of the block. All values are converted down to chunk
	 *            internal coordinates.
	 * @return The block.
	 */
	int getBlock(BlockPos pos);

	/**
	 * Gets a block by position. Ignores the {@linkplain LoadLevel}.
	 * 
	 * @param pos The position of the block. All values are converted down to chunk
	 *            internal coordinates.
	 * @return The block.
	 */
	int getBlockNoLoadCheck(BlockPos pos);

	/**
	 * Method to get all blocks inside a chunk in a ordered way. To access a block
	 * at the position x,y you can access the array like {@code getContent()[x][y]}.
	 * 
	 * @return All blocks as an array on the x-axis that contains arrays for each
	 *         y-row of cells.
	 */
	int[][] getContent();

	/**
	 * Gets the current load level of the chunk.
	 * 
	 * @return A LoadLevel.
	 * @apiNote This value can be updated by the chunk automatically.
	 * @see IChunk#setLoadLevel(LoadLevel)
	 */
	LoadLevel getLoadLevel();

	/**
	 * Gets the position of the chunk object.
	 * 
	 * @return The position as a {@linkplain ChunkPos}.
	 */
	ChunkPos getPos();

	/**
	 * Method to check if the chunk contains any non dead cells.
	 * 
	 * @return True if all blocks in the {@linkplain IChunk} are dead.
	 */
	boolean isEmpty();

	/**
	 * sets a block inside the chunk to a specific state
	 * 
	 * @param pos   The position of the block. Gets converted down to chunk internal
	 *              coordinates.
	 * @param state The state of the block.
	 */
	void setBlock(BlockPos pos, int state);

	/**
	 * Sets the load level of the chunk to a different value.
	 * 
	 * @param level The level to set.
	 * @see IChunk#getLoadLevel()
	 */
	void setLoadLevel(LoadLevel level);

	/**
	 * Ticks a chunk. This function must be called 20 times per second.
	 */
	void tick();
	
	void updateLoadLevel();
	
	void needsLLCheck();
}
