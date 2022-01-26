package local.pixy.conwaysgame.world;

import local.pixy.conwaysgame.block.IBlock;
import local.pixy.conwaysgame.block.IBlockState;
import local.pixy.conwaysgame.math.BlockPos;

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
	IBlock getBlock(BlockPos pos);

	/**
	 * Gets a block by position. Ignores the {@linkplain LoadLevel}.
	 * 
	 * @param pos The position of the block. All values are converted down to chunk
	 *            internal coordinates.
	 * @return The block.
	 */
	IBlock getBlockNoLoadCheck(BlockPos pos);

	/**
	 * Method to get all blocks inside a chunk in a ordered way. To access a block
	 * at the position x,y you can access the array like {@code getContent()[x][y]}.
	 * 
	 * @return All blocks as an array on the x-axis that contains arrays for each
	 *         y-row of cells.
	 */
	IBlock[][] getContent();

	/**
	 * Gets the current load level of the chunk.
	 * 
	 * @return A LoadLevel.
	 * @apiNote This value can be updated by the chunk automatically.
	 * @see IChunk#setLoadLevel(LoadLevel)
	 */
	LoadLevel getLoadLevel();

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
	void setBlock(BlockPos pos, IBlockState state);

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

	/**
	 * The chunk ticks all blocks once, but only to let the blocks calculate their
	 * next state.
	 */
	void tickCalculate();

	/**
	 * The chunk ticks all blocks once, to let the blocks update their state.
	 */
	void tickUpdateState();
}
