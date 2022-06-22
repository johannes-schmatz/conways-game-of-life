package local.pixy.conwaysgame.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import local.pixy.conwaysgame.block.Blocks;
import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.ChunkPos;
import local.pixy.conwaysgame.math.Direction;
import local.pixy.conwaysgame.util.Nullable;

/**
 * @author pixy
 *
 */
public class World implements IWorld {
	private Map<ChunkPos, IChunk> content = new HashMap<>();

	@Override
	public int getBlock(BlockPos pos) {
		IChunk chunk = this.getChunk(pos.getChunkPos());
		if (chunk == null)
			return Blocks.AIR;
		return chunk.getBlock(pos);
	}

	@Override
	@Nullable
	public IChunk getChunk(ChunkPos pos) {
		return this.content.get(pos);
	}
	
	@Override
	public IChunk getChunkNew(ChunkPos pos) {
		if (!this.content.containsKey(pos))
			this.setChunk(pos, new Chunk(this, pos));
		return this.getChunk(pos);
	}

	@Override
	public void setBlock(BlockPos pos, int state) {
		this.getChunkNew(pos.getChunkPos()).setBlock(pos, state);
	}

	@Override
	public void setChunk(ChunkPos pos, IChunk chunk) {
		if (this.content.containsKey(pos))
			this.content.remove(pos);
		this.content.put(pos, chunk);
	}

	@Override
	public void tick() {
		Collection<ChunkPos> posCollection = this.content.keySet();
		List<ChunkPos> posList = new ArrayList<>();
		posList.addAll(posCollection);

		posList.forEach(pos -> {
			IChunk chunk = this.content.get(pos);
			chunk.tick();
		});
		
		Blocks.swapAlphabets();
	}

	@Override
	public int countNeightbours(BlockPos pos) {
		int neightbours = 0;
		int state = 0;
		for (Direction i : Direction.notSelf) {
			state = this.getBlock(pos.offset(i));
			if (Blocks.isAlive(state))
				neightbours++;
			else {
				if (!Blocks.isAir(state))
					throw new Blocks.InvalidBlockIdException(pos, i);
			}
		}
		return neightbours;
	}

	@Override
	public void updateState(BlockPos pos) {
		int neightbours = this.countNeightbours(pos);
		int state = this.getBlock(pos);
		int nextState = switch (neightbours) {
		case 2 -> state == Blocks.PREV_AIR ? Blocks.ALIVE : (state == Blocks.PREV_ALIVE ? Blocks.AIR : state);
		case 3 -> Blocks.isAlive(state) ? Blocks.ALIVE : Blocks.NEXT_ALIVE;
		default -> Blocks.isAlive(state) ? Blocks.NEXT_AIR : Blocks.AIR;
		};
		if (nextState != 0)
			System.out.println(pos.toString() + " " + nextState);
		this.setBlock(pos, nextState);
	}

	@Override
	public Iterator<ChunkPos> getChunkIterator() {
		return this.content.keySet().iterator();
	}
	
	@Override
	public Iterator<ChunkPos> getChunkCopyIterator() {
		Map<ChunkPos, IChunk> mapCopy = new HashMap<>(this.content);
		return mapCopy.keySet().iterator();
	}

	@Override
	public void unloadChunk(IChunk chunk) {
		ChunkPos cpos = chunk.getPos();
		this.content.remove(cpos);
	}
	
	public Map<ChunkPos, IChunk> getContent(){
		return this.content;
	}
}
