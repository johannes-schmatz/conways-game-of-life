package local.pixy.conwaysgame.world;

import java.util.Iterator;

import local.pixy.conwaysgame.block.Block;
import local.pixy.conwaysgame.block.BlockState;
import local.pixy.conwaysgame.block.IBlock;
import local.pixy.conwaysgame.block.IBlockState;
import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.ChunkPos;
import local.pixy.conwaysgame.math.Direction;

/**
 * The implementation of {@linkplain IChunk}.
 * 
 * @author pixy
 */
public class Chunk implements IChunk {
	private IBlock[][] content = new Block[SIZE][SIZE];
	private LoadLevel loadLevel = LoadLevel.NOT;
	private ChunkPos pos;
	private IWorld world;

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
	public IBlock getBlock(BlockPos pos) {
		pos = pos.getBlockPosChunk();
		switch (this.loadLevel) {
		case NOT, BORDER:
			return new Block(this.world, pos, BlockState.newDefault());
		case TICKING, FULL:
			return this.getBlockNoLoadCheck(pos);
		default:
			throw new IllegalArgumentException("Unexpected value: " + this.loadLevel);
		}
	}

	@Override
	public IBlock getBlockNoLoadCheck(BlockPos pos) {
		pos = pos.getBlockPosChunk();
		IBlock block = this.content[pos.getX()][pos.getY()];
		if (block == null) {
			block = new Block(this.world, pos, BlockState.newDefault());
			this.content[pos.getX()][pos.getY()] = block;
		}
		else 
			System.out.println(pos.toString());
		return block;
	}

	@Override
	public IBlock[][] getContent() {
		return this.content;
	}

	@Override
	public LoadLevel getLoadLevel() {
		return this.loadLevel;
	}

	@Override
	public boolean isEmpty() {
		Iterator<BlockPos> iter = BlockPos.chunkBlockIterator();
		while (iter.hasNext()) {
			if (this.getBlockNoLoadCheck(iter.next()).getState().isNot(BlockState.DEAD)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void setBlock(BlockPos pos, IBlockState state) {
		pos = pos.getBlockPosChunk();

		this.content[pos.getX()][pos.getY()] = new Block(this.world, pos, state);

		//this.updateLoadLevel();
	}

	@Override
	public void setLoadLevel(LoadLevel level) {
		this.loadLevel = level;
	}

	@Override
	public void tick() {
		this.updateLoadLevel();

		if (this.loadLevel.getLevel() >= LoadLevel.TICKING.getLevel()) {
			Iterator<BlockPos> iter = BlockPos.chunkBlockIterator();
			iter.forEachRemaining(pos -> {
				this.getBlock(pos).tick();
			});
		}
	}

	@Override
	public void tickCalculate() {
		if (this.loadLevel.getLevel() >= LoadLevel.TICKING.getLevel()) {
			Iterator<BlockPos> iter = BlockPos.chunkBlockIterator();
			iter.forEachRemaining(pos -> {
				this.getBlock(pos).tickCalculate();
			});
		}
	}

	@Override
	public void tickUpdateState() {
		if (this.loadLevel.getLevel() >= LoadLevel.TICKING.getLevel()) {
			Iterator<BlockPos> iter = BlockPos.chunkBlockIterator();
			iter.forEachRemaining(pos -> {
				this.getBlock(pos).tickUpdatesState();
			});
		}
	}

	@Override
	public String toString() {
		return super.toString() + "[x=" + this.pos.getX() + ", y=" + this.pos.getY() + ", ll=" + this.loadLevel.toString() + "]";
	}

	private void updateLoadLevel() {
		if (this.isEmpty()) {
			int level, maxLoadLevel = LoadLevel.NOT.getLevel();
			for (Direction i : Direction.directionNoSelf) {
				level = this.world.getChunk(this.pos.offset(i)).getLoadLevel().getLevel();
				if (level > maxLoadLevel)
					maxLoadLevel = level;
			}
			this.setLoadLevel(LoadLevel.fromNumber(--maxLoadLevel));
		} else {
			this.setLoadLevel(LoadLevel.FULL);
		}
	}
}
