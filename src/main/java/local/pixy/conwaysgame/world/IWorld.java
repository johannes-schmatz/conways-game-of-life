package local.pixy.conwaysgame.world;

import java.util.Iterator;
import java.util.Map;

import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.ChunkPos;

/**
 * @author pixy
 *
 */
public interface IWorld {
	int countNeightbours(BlockPos pos);

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
	
	// same as getChunk, but also allows generation
	IChunk getChunkNew(ChunkPos pos);

	Iterator<ChunkPos> getChunkIterator();
	Iterator<ChunkPos> getChunkCopyIterator(); // Iterator over copy of the chunk set

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

	void unloadChunk(IChunk chunk);
	
	void updateState(BlockPos pos);
	
	Map<ChunkPos, IChunk> getContent();
}
