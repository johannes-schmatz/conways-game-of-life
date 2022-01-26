package local.pixy.conwaysgame.block;

/**
 * @author pixy
 *
 */
public class BlockState implements IBlockState {
	public static final int ALIVE = 1;
	public static final int DEAD = 0;

	public static final IBlockState newAlive() {
		return new BlockState(ALIVE);
	}

	public static final IBlockState newDead() {
		return new BlockState(DEAD);
	}

	public static final IBlockState newDefault() {
		return BlockState.newDead();
	}

	private int nextState = DEAD;
	private int state = DEAD;

	public BlockState(int state) {
		this.state = state;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BlockState) || obj == null)
			return false;
		if (obj == this)
			return true;

		BlockState state = (BlockState) obj;
		return this.state == state.state;
	}

	@Override
	public int hashCode() {
		return super.hashCode() + this.state;
	}

	@Override
	public boolean is(int state) {
		return this.state == state;
	}

	@Override
	public boolean isNot(int state) {
		return this.state != state;
	}

	@Override
	public void tick() {
	}

	@Override
	public void tickCalculate(IBlockState[] neightbours) {
		int count = 0;
		for (IBlockState i : neightbours) {
			if (i.is(ALIVE))
				count++;
		}
		
		this.nextState = switch (count) {
		case 0, 1, 4, 5, 6, 7, 8 -> DEAD;
		case 2 -> this.state;
		case 3 -> ALIVE;
		default -> throw new IllegalArgumentException("Unexpected value: " + count);
		};
	}

	@Override
	public void tickUpdateState() {
		this.state = this.nextState;
		this.nextState = DEAD;
	}

	@Override
	public String toString() {
		return super.toString() + "[state=" + this.state + ", nextState=" + this.nextState + "]";
	}

	@Override
	public boolean is(IBlockState state) {
		return this.is(state.getIntegerState());
	}

	@Override
	public boolean isNot(IBlockState state) {
		return this.isNot(state.getIntegerState());
	}

	@Override
	public int getIntegerState() {
		return this.state;
	}
}
