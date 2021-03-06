package local.pixy.conwaysgame.world;

/**
 * Describes a load level.
 * 
 * @see LoadLevel#NOT
 * @see LoadLevel#BORDER
 * @see LoadLevel#TICKING
 * @see LoadLevel#FULL
 * 
 * @author pixy
 */
public enum LoadLevel {
	/**
	 * Indicates the chunk is empty and won't be ticked.
	 */
	BORDER(1),
	/**
	 * Indicates a chunk is fully loaded, and every clock is getting ticked. All
	 * chunks that contain blocks have this level.
	 */
	FULL(3),
	/**
	 * Indicates a Chunk is not loaded at all. No functions of that chunk will be
	 * called
	 */
	NOT(0),
	/**
	 * Indicates that a Chunk is empty, but will still get ticked.
	 */
	TICKING(2);

	/**
	 * An array containing all {@linkplain LoadLevel}s by integer level.
	 */
	public static LoadLevel[] values = { NOT, BORDER, TICKING, FULL };

	/**
	 * Gets the load level for the loaded value. Values below 0 are converted to 0,
	 * values out of range are converted to the last position.
	 * 
	 * @param index The index of the LoadLevel, from {@linkplain LoadLevel#values}
	 * @return The LoadLevel.
	 */
	public static LoadLevel fromNumber(int index) {
		int length = LoadLevel.values.length;
		if (index < 0)
			index = 0;
		if (index >= length)
			index = length - 1;
		return LoadLevel.values[index];
	}

	/**
	 * Get the LoadLevel with the higher {@linkplain LoadLevel#getLevel()}.
	 * 
	 * @param a The first level to compare.
	 * @param b The second level to compare.
	 * @return Either a or b, depending on the level.
	 */
	public static LoadLevel getHigherLevel(LoadLevel a, LoadLevel b) {
		if (a.getLevel() > b.getLevel())
			return a;
		return b;
	}

	/**
	 * Subtract amount from the level number of level.
	 * 
	 * @param level  The LoadLevel to subtract from.
	 * @param amount The amount of levels to subtract.
	 * @return The calculated LoadLevel.
	 */
	public static LoadLevel getLoadLevelLess(LoadLevel level, int amount) {
		return LoadLevel.fromNumber(level.getLevel() - amount);
	}

	private final int level;

	private LoadLevel(int i) {
		this.level = i;
	}

	/**
	 * Gets the level as an integer, lower means less loaded.
	 * 
	 * @return The level index.
	 */
	public int getLevel() {
		return level;
	}
}
