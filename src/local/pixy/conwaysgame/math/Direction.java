package local.pixy.conwaysgame.math;

import java.util.function.Consumer;

/**
 * @author pixy
 */
public enum Direction {
	@SuppressWarnings("javadoc")
	EAST(1, 0), @SuppressWarnings("javadoc")
	NORTH(0, 1), @SuppressWarnings("javadoc")
	NORTHEAST(1, 1), @SuppressWarnings("javadoc")
	NORTHWEST(-1, 1), @SuppressWarnings("javadoc")
	SELF(0, 0), @SuppressWarnings("javadoc")
	SOUTH(0, -1), @SuppressWarnings("javadoc")
	SOUTHEAST(1, -1), @SuppressWarnings("javadoc")
	SOUTHWEST(-1, -1), @SuppressWarnings("javadoc")
	WEST(-1, 0);

	/**
	 * Calls the function for every direction, without {@linkplain Direction#SELF}.
	 * 
	 * @param callback The function to call with the direction it currently
	 *                 iterates.
	 */
	public static void iterate(Consumer<Direction> callback) {
		for (Direction i : Direction.values()) {
			if (i != Direction.SELF)
				callback.accept(i);
		}
	}

	/**
	 * All directions without {@linkplain Direction#SELF} in an array.
	 */
	public static final Direction[] directionNoSelf = { NORTHWEST, NORTH, NORTHEAST, WEST, EAST, SOUTHWEST, SOUTH,
			SOUTHEAST };

	/**
	 * The offset as a {@linkplain ChunkPos}.
	 */
	public final ChunkPos cOffset;
	/**
	 * The offset as a {@linkplain BlockPos}.
	 */
	public final BlockPos offset;
	/**
	 * The x-Value of the offset.
	 */
	public final int x;

	/**
	 * The y-Value of the offset.
	 */
	public final int y;

	Direction(int x, int y) {
		this.x = x;
		this.y = y;
		this.offset = new BlockPos(this.x, this.y);
		this.cOffset = new ChunkPos(this.x, this.y);
	}
}
