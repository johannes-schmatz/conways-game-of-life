package local.pixy.conwaysgame.world;

import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.ChunkPos;

/**
 * @author pixy
 *
 */
public interface IWorld {
	/**
	 * @param pos
	 * @return
	 */
	int getBlock(BlockPos pos);

	/**
	 * @param pos
	 * @return
	 */
	IChunk getChunk(ChunkPos pos);

	/**
	 * @param pos
	 * @param state
	 */
	void setBlock(BlockPos pos, int state);

	/**
	 * @param pos
	 * @param chunk
	 */
	void setChunk(ChunkPos pos, IChunk chunk);

	/**
	 * 
	 */
	void tick();

	int countNeightbours(BlockPos pos);

	void updateState(BlockPos pos);
}
