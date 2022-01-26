package local.pixy.conwaysgame;

import local.pixy.conwaysgame.block.BlockState;
import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.Direction;
import local.pixy.conwaysgame.world.IWorld;
import local.pixy.conwaysgame.world.World;

/**
 * @author pixy
 */
public class Main {
	@SuppressWarnings("javadoc")
	public static void main(String[] args) {
		// new ClientRenderThread(new World()).run();

		IWorld world = new World();
		
		//addBlinker(world, new BlockPos(10, 10));
		addBlinker(world, new BlockPos(20, 20));
		
		System.out.println("start");

		for(int i = 0; i < 2; i++) {
			if(i != 0)
				System.out.println("----");
			printWorld(world, 31);
			world.tick();
		}
	}

	private static void addBlinker(IWorld world, BlockPos pos) {
		world.setBlock(pos.offset(Direction.NORTH), BlockState.newAlive());
		world.setBlock(pos, BlockState.newAlive());
		world.setBlock(pos.offset(Direction.SOUTH), BlockState.newAlive());
	}

	private static void printWorld(IWorld world, int size) {
		int n = 5;
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				System.out.print(world.getBlock(new BlockPos(x, y)).getState().is(BlockState.ALIVE) ? "#" : (((x % n == 0) || (y % n == 0)) ? "‌." : " "));
			}
			System.out.println();
		}
	}
}
