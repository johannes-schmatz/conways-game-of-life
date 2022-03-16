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
		for (Direction i : Direction.directionNoSelf) {
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

	/**
	 * Finds a direction based on x and y values. The inputs will be converted to
	 * either 1, 0 or -1.
	 * 
	 * @param x The x value to search.
	 * @param y The y value to search.
	 * @return A direction whose x and y values match the positive/zero/negative
	 *         numbers in x and y.
	 */
	public static Direction findDirectionForXY(int x, int y) {
		x = x != 0 ? x / Math.abs(x) : 0;
		y = y != 0 ? y / Math.abs(y) : 0;
		for (Direction i : Direction.values()) {
			if (i.x == x && i.y == y)
				return i;
		}
		throw new IllegalArgumentException("Direction not found for x = {} and y = {}".formatted(x, y));
	}

	/**
	 * Adds a {@linkplain Direction} to another itself, returning the result.
	 * 
	 * @param add The {@linkplain Direction} to add.
	 * @return The result of the addition.
	 */
	public Direction add(Direction add) {
		int x = this.x + add.x;
		int y = this.y + add.y;
		return Direction.findDirectionForXY(x, y);
	}

	/**
	 * Inverts a {@linkplain Direction}.
	 * @return A Direction with negated x and y values.
	 */
	public Direction invert() {
		return Direction.findDirectionForXY(-this.x, -this.y);
	}
}
