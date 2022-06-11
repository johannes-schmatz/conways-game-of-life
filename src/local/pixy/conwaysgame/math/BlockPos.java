package local.pixy.conwaysgame.math;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

import local.pixy.conwaysgame.util.Nullable;
import local.pixy.conwaysgame.world.Chunk;

/**
 * Describes a block's position.
 * 
 * @author pixy
 */
public class BlockPos extends BasicVector<BlockPos> {
	/**
	 * Gets a {@linkplain Iterator} for iterating over all blocks inside a square
	 * with size {@linkplain Chunk#SIZE}.
	 * 
	 * @return The iterator.
	 */
	public static Iterator<BlockPos> chunkBlockIterator() {
		return BlockPos.chunkBlockIterator(new ChunkPos(0, 0));
	}

	/**
	 * Gets a {@linkplain Iterator} for iterating over all positions inside a chunk.
	 * 
	 * @param cpos The chunk to iterate over all containing blocks.
	 * @return The iterator.
	 */
	public static Iterator<BlockPos> chunkBlockIterator(ChunkPos cpos) {
		Set<BlockPos> collection = new HashSet<>();
		for (int i = 0; i < Chunk.SIZE; i++) {
			for (int j = 0; j < Chunk.SIZE; j++) {
				collection.add(cpos.toBlockPos(new BlockPos(i, j)));
			}
		}
		return collection.iterator();
	}

	/**
	 * Creates a new {@linkplain BlockPos}.
	 * 
	 * @param x The x-value of this instance.
	 * @param y The y-value of this instance.
	 */
	public BlockPos(int x, int y) {
		super(x, y);
	}

	@Override
	public BlockPos from(int x, int y) {
		return new BlockPos(x, y);
	}

	/**
	 * Creates a new {@linkplain BlockPos} from a string with space separated
	 * values.
	 * 
	 * @implSpec this just needs to call
	 *           {@linkplain BlockPos#fromString(String, String)} with the second
	 *           parameter being {@code " "}.
	 * 
	 * @param input The string to create the instance from.
	 * @return The newly created instance of {@linkplain BlockPos}
	 * 
	 * @see BlockPos#fromString(String, String)
	 */
	@Nullable
	public static BlockPos fromString(String input) {
		return BlockPos.fromString(input, " ");
	}

	/**
	 * Creates a new {@linkplain BlockPos} instance from a string and a separator.
	 * If any parsing errors happen, numbers will default to 0. This will return
	 * null when the length of the split string isn't exactly 2.
	 * 
	 * @param input An input to split and use as x and y values in that order.
	 * @param split A string to split input with.
	 * @return A new instance.
	 */
	@Nullable
	public static BlockPos fromString(String input, String split) {
		String[] splitted = input.split(split);

		if (splitted.length == 2) {
			int x = 0;
			int y = 0;
			
			try {
				x = Integer.parseInt(splitted[0]);
				y = Integer.parseInt(splitted[1]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.out.println("couldn't parse \"" + input + "\" with a splitter of \"" + split + "\"");
			}
			
			return new BlockPos(x, y);
		} else {
			return null;
		}
	}

	/**
	 * Generates a new {@linkplain BlockPos} instance, that is converted down to
	 * only contain position within one
	 * {@linkplain local.pixy.conwaysgame.world.IChunk}.
	 * 
	 * @return The generated {@linkplain BlockPos} instance.
	 */
	public BlockPos getBlockPosChunk() {
		Function<Integer, Integer> op = (i) -> (i < 0 ? (i + 1) % Chunk.SIZE + Chunk.SIZE - 1 : i % Chunk.SIZE);
		return new BlockPos(op.apply(this.getX()), op.apply(this.getY()));
	}

	/**
	 * Generates a {@linkplain ChunkPos} instance, with coordinates converted, so
	 * that the chunk at 0,0 starts at 0,0.
	 * 
	 * @return The {@linkplain ChunkPos} instance.
	 */
	public ChunkPos getChunkPos() {
		Function<Integer, Integer> op = i -> (i < 0 ? (i - Chunk.SIZE + 1) / Chunk.SIZE : i / Chunk.SIZE);
		return new ChunkPos(op.apply(this.getX()), op.apply(this.getY()));
	}
}
