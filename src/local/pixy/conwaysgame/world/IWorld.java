package local.pixy.conwaysgame.world;

import local.pixy.conwaysgame.block.IBlock;
import local.pixy.conwaysgame.block.IBlockState;
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
	IBlock getBlock(BlockPos pos);

	/**
	 * @param pos
	 * @return
	 */
	IChunk getChunk(ChunkPos pos);

	/**
	 * @param pos
	 * @param state
	 */
	void setBlock(BlockPos pos, IBlockState state);

	/**
	 * @param pos
	 * @param chunk
	 */
	void setChunk(ChunkPos pos, IChunk chunk);

	/**
	 * 
	 */
	void tick();
}
