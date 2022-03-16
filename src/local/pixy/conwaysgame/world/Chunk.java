package local.pixy.conwaysgame.world;

import java.util.Iterator;

import local.pixy.conwaysgame.block.Blocks;
import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.ChunkPos;
import local.pixy.conwaysgame.math.Direction;

/**
 * The implementation of {@linkplain IChunk}.
 * 
 * @author pixy
 */
public class Chunk implements IChunk {
	private int[][] content = new int[SIZE][SIZE];
	private LoadLevel loadLevel = LoadLevel.NOT;
	private ChunkPos pos;
	private IWorld world;
	private int aliveCount = 0;

	/**
	 * Creates a new chunk.
	 * 
	 * @param world The world the chunk belongs to.
	 * @param pos   The position of the chunk.
	 */
	public Chunk(World world, ChunkPos pos) {
		this.world = world;
		this.pos = pos;
	}

	@Override
	public int getBlock(BlockPos pos) {
		pos = pos.getBlockPosChunk();
		switch (this.loadLevel) {
		case NOT, BORDER:
			return 0;
		case TICKING, FULL:
			return this.getBlockNoLoadCheck(pos);
		default:
			throw new IllegalArgumentException("Unexpected value: " + this.loadLevel);
		}
	}

	@Override
	public int getBlockNoLoadCheck(BlockPos pos) {
		pos = pos.getBlockPosChunk();
		int blockId = this.content[pos.getX()][pos.getY()];
		this.content[pos.getX()][pos.getY()] = blockId;
		return blockId;
	}

	@Override
	public int[][] getContent() {
		return this.content;
	}

	@Override
	public LoadLevel getLoadLevel() {
		return this.loadLevel;
	}

	@Override
	public boolean isEmpty() {
		return this.aliveCount == 0;
	}

	@Override
	public void setBlock(BlockPos pos, int state) {
		this.setBlock(pos, state, true);
	}
	public void setBlock(BlockPos pos, int state, boolean doDebug) {
		pos = pos.getBlockPosChunk();

		this.content[pos.getX()][pos.getY()] = state;

		if(doDebug) {
			System.out.println("yikes");
			this.debugPrint();
		}

		if (state != Blocks.AIR)
			this.setLoadLevel(LoadLevel.FULL);
		this.neighbourChunkLoadLevelUpdate();
	}

	@Override
	public void setLoadLevel(LoadLevel level) {
		this.loadLevel = level;
		this.markDirty();
	}
	
	public void markDirty() {}
	
	public void debugPrint() {
		if (this.pos.getX() != 1 || this.pos.getY() != 1) return;
		System.out.println("AIR: " + Blocks.AIR + ", ALIVE: " + Blocks.ALIVE + ", NEXT_AIR: " + Blocks.NEXT_AIR + ", NEXT_ALIVE: " + Blocks.NEXT_ALIVE);
		System.out.println(this.pos.toString());
		System.out.println("load level set to " + this.loadLevel.toString());
		for(int i = 0; i < this.content.length; i++) {
			for(int j = 0; j < this.content[i].length; j++)
				System.out.print(this.content[i][j]);
			System.out.println();
		}
	}

	@Override
	public void tick() {
		this.updateLoadLevel();
		this.aliveCount = 0;

		if (this.loadLevel.getLevel() >= LoadLevel.TICKING.getLevel()) {
			System.out.println(this.pos.toString() + " is loaded as ticking!");
			Iterator<BlockPos> iter = BlockPos.chunkBlockIterator(this.pos);
			iter.forEachRemaining(pos -> {
				if (this.getBlock(pos) == Blocks.ALIVE)
					this.aliveCount++;

				this.world.updateState(pos);
			});
		}
		this.debugPrint();
	}

	@Override
	public String toString() {
		return super.toString() + "[x=" + this.pos.getX() + ", y=" + this.pos.getY() + ", ll="
				+ this.loadLevel.toString() + "]";
	}
	
	public void neighbourChunkLoadLevelUpdate() {
		for(Direction i : Direction.directionNoSelf) {
			IChunk chunk = this.world.getChunk(this.pos.offset(i));
			chunk.updateLoadLevel();
		}
	}

	@Override
	public void updateLoadLevel() {
		if (this.loadLevel != LoadLevel.NOT) {
			int level, maxLoadLevel = LoadLevel.NOT.getLevel();
			for (Direction i : Direction.directionNoSelf) {
				level = this.world.getChunk(this.pos.offset(i)).getLoadLevel().getLevel();
				//System.out.println("level: " + i.toString() + " has " + level);
				if (level > maxLoadLevel)
					maxLoadLevel = level;
			}
			this.setLoadLevel(LoadLevel.fromNumber(--maxLoadLevel));
			//for (Direction i : Direction.directionNoSelf) {
			//	this.world.getChunk(this.pos.offset(i)).updateLoadLevel();
			//}
//		if (this.isEmpty()) {
//			int level, maxLoadLevel = LoadLevel.NOT.getLevel();
//			for (Direction i : Direction.directionNoSelf) {
//				level = this.world.getChunk(this.pos.offset(i)).getLoadLevel().getLevel();
//				if (level > maxLoadLevel)
//					maxLoadLevel = level;
//			}
//			this.setLoadLevel(LoadLevel.fromNumber(--maxLoadLevel));
//		} else {
//			this.setLoadLevel(LoadLevel.FULL);
		}
	}
}
