package local.pixy.conwaysgame.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import local.pixy.conwaysgame.block.IBlock;
import local.pixy.conwaysgame.block.IBlockState;
import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.ChunkPos;

/**
 * @author pixy
 *
 */
public class World implements IWorld {
	private Map<ChunkPos, Integer> reference = new HashMap<>();
	private List<IChunk> content = new ArrayList<>();
	@Override
	public IBlock getBlock(BlockPos pos) {
		return this.getChunk(pos.getChunkPos()).getBlock(pos);
	}

	@Override
	public IChunk getChunk(ChunkPos pos) {
		if(!this.reference.containsKey(pos))
			this.setChunk(pos, new Chunk(this, pos));
		int index = this.reference.get(pos);
		return this.content.get(index);
	}

	@Override
	public void setBlock(BlockPos pos, IBlockState state) {
		ChunkPos cpos = pos.getChunkPos();
		this.getChunk(cpos).setBlock(pos, state);
	}
	
	@Override
	public void setChunk(ChunkPos pos, IChunk chunk) {
		this.content.add(chunk);
		int index = this.content.size() - 1;
		if(this.reference.containsKey(pos))
			this.reference.remove(pos);
		this.reference.put(pos, index);
	}

	@Override
	public void tick() {
		List<IChunk> currentContent = new ArrayList<>(this.content);
		
		currentContent.forEach(chunk -> {
			chunk.tick();
			chunk.tickCalculate();
		});
		currentContent.forEach(chunk -> {
			chunk.tickUpdateState();
		});
	}
}
