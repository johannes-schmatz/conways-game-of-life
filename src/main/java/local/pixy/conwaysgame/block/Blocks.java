package local.pixy.conwaysgame.block;

import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.Direction;

/**
 * @author pixy
 *
 */
public class Blocks {
	/**
	 * An {@linkplain Error} that tells you that you have a wrong block with a wrong id somewhere.
	 * @author pixy
	 *
	 */
	public static class InvalidBlockIdException extends Error {
		private static final long serialVersionUID = -1802558919699434978L;
		private BlockPos pos;
		private BlockPos fromPos;
		private Direction offsetDirection;

		public InvalidBlockIdException(BlockPos pos, Direction direction) {
			super("Invalid Block Id");
			this.fromPos = pos;
			this.offsetDirection = direction;
			this.pos = pos.offset(direction);
		}
	}

	public static int AIR;
	public static int ALIVE;
	public static int NEXT_AIR;
	public static int NEXT_ALIVE;
	public static int PREV_AIR;
	public static int PREV_ALIVE;
	public static int PORTAL; // TODO: add portal functionality!

	public static void swapAlphabets() {
		int tmp = Blocks.NEXT_AIR;
		Blocks.NEXT_AIR = Blocks.PREV_ALIVE;
		Blocks.PREV_ALIVE = tmp;

		tmp = Blocks.NEXT_ALIVE;
		Blocks.NEXT_ALIVE = Blocks.PREV_AIR;
		Blocks.PREV_AIR = tmp;
	}

	static {
		int i = 0;
		AIR = i++;
		ALIVE = i++;
		NEXT_AIR = i++;
		NEXT_ALIVE = i++;
		PREV_AIR = i++;
		PREV_ALIVE = i++;
		PORTAL = i++;
	}
	
	public static boolean isAlive(int state) {
		return state == ALIVE || state == NEXT_AIR || state == PREV_AIR;
	}
	
	public static boolean isAir(int state) {
		return state == AIR || state == NEXT_ALIVE || state == PREV_ALIVE;
	}

	/*
	 * public static final IBlockState newAlive() { return new BlockState(ALIVE); }
	 * 
	 * public static final IBlockState newDead() { return new BlockState(DEAD); }
	 * 
	 * public static final IBlockState newDefault() { return BlockState.newDead(); }
	 * 
	 * private int nextState = DEAD; private int state = DEAD;
	 * 
	 * public BlockState(int state) { this.state = state; }
	 * 
	 * @Override public boolean equals(Object obj) { if (!(obj instanceof
	 * BlockState) || obj == null) return false; if (obj == this) return true;
	 * 
	 * BlockState state = (BlockState) obj; return this.state == state.state; }
	 * 
	 * @Override public int hashCode() { return this.state; }
	 * 
	 * @Override public boolean is(int state) { return this.state == state; }
	 * 
	 * @Override public boolean isNot(int state) { return this.state != state; }
	 * 
	 * @Override public void tick() { }
	 * 
	 * @Override public void tickCalculate(IBlockState[] neightbours) { int count =
	 * 0; for (IBlockState i : neightbours) { if (i.is(ALIVE)) count++; }
	 * 
	 * this.nextState = switch (count) { case 0, 1, 4, 5, 6, 7, 8 -> DEAD; case 2 ->
	 * this.state; case 3 -> ALIVE; default -> throw new
	 * IllegalArgumentException("Unexpected value: " + count); }; }
	 * 
	 * @Override public void tickUpdateState() { this.state = this.nextState;
	 * this.nextState = DEAD; }
	 * 
	 * @Override public String toString() { return super.toString() + "[state=" +
	 * this.state + ", nextState=" + this.nextState + "]"; }
	 * 
	 * @Override public boolean is(IBlockState state) { return
	 * this.is(state.getIntegerState()); }
	 * 
	 * @Override public boolean isNot(IBlockState state) { return
	 * this.isNot(state.getIntegerState()); }
	 * 
	 * @Override public int getIntegerState() { return this.state; }
	 */
	/**
	 * 
	 */

}
