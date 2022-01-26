package local.pixy.conwaysgame.block;

import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.Direction;
import local.pixy.conwaysgame.world.IWorld;

public class Block implements IBlock {
	private BlockPos pos;
	private IBlockState state;
	private IWorld world;

	/**
	 * @param world
	 * @param pos
	 * @param state
	 */
	public Block(IWorld world, BlockPos pos, IBlockState state) {
		this.state = state;
		this.pos = pos;
		this.world = world;
	}

	@Override
	public IBlockState[] getNeightbourStates() {
		IBlockState[] output = new IBlockState[8];
		int i = 0;
		for (Direction direction : Direction.directionNoSelf) {
			output[i++] = this.world.getBlock(this.pos.offset(direction)).getState();
		}

		return output;
	}

	@Override
	public IBlockState getState() {
		return this.state;
	}

	@Override
	public void setState(IBlockState state) {
		this.state = state;
	}

	@Override
	public void tick() {
		this.state.tick();
	}

	@Override
	public void tickCalculate() {
		this.state.tickCalculate(this.getNeightbourStates());
	}

	@Override
	public void tickUpdatesState() {
		this.state.tickUpdateState();
	}

	@Override
	public String toString() {
		return super.toString() + "[state=" + this.state.toString() + "]";
	}
}
