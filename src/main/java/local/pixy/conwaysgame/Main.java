package local.pixy.conwaysgame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import local.pixy.conwaysgame.block.Blocks;
import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.Direction;
import local.pixy.conwaysgame.render.BallRenderThread;
import local.pixy.conwaysgame.render.ClientRenderThread;
import local.pixy.conwaysgame.world.IWorld;
import local.pixy.conwaysgame.world.World;
import local.pixy.conwaysgame.world.BallWorld;

/**
 * @author pixy
 */
public class Main {
	private IWorld world = new World();
	private boolean worldTicking = false;
	private ClientRenderThread cr;
	private long fps = 2L;

	@SuppressWarnings("javadoc")
	public static void main(String[] args) {
		//Main.main1(args);
		Main.main_ball(args);
	}
	
	public static void main_ball(String[] args) {
		BallMain main = new BallMain();
		main.main();
	}

	public static void main1(String[] args) {
		Main main = new Main();

		// addBlinker(world, new BlockPos(10,10));
		addBlinker(main.world, new BlockPos(7, 7));
		addBlinker(main.world, new BlockPos(7, 20));
		addBlinker(main.world, new BlockPos(20, 7));
		addBlinker(main.world, new BlockPos(20, 20));

		main.main();
	}

	private Thread thread;

	public Main() {
		this.cr = new ClientRenderThread(this.world, this::toggleWorldTicking, this::tickWorld, this::loadWorld);
		this.thread = new Thread(null, this.cr, "render_thread");
		this.thread.start();
	}

	public void main() {
		System.out.println("starting!");
		while (this.thread.getState() != Thread.State.TERMINATED) {
			while (this.worldTicking && this.thread.getState() != Thread.State.TERMINATED) {
				this.world.tick();
				System.out.println("world was ticked!");
				try {
					Thread.sleep((long) (1000 / this.fps));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("exiting!");
	}

	private void toggleWorldTicking() {
		this.worldTicking = !this.worldTicking;
	}

	private static void addBlinker(IWorld world, BlockPos pos) {
		world.setBlock(pos.offset(Direction.NORTH), Blocks.ALIVE);
		world.setBlock(pos, Blocks.ALIVE);
		world.setBlock(pos.offset(Direction.SOUTH), Blocks.ALIVE);
	}

	private void tickWorld() {
		synchronized (this.world) {
			this.world.tick();
		}
	}

	private String askUser(String question) {
		System.out.println(question + " ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;
	}

	private class UserExitEvent extends Throwable {
		private static final long serialVersionUID = 5438060353201021695L;
	}

	private File askForFile() throws UserExitEvent {
		String baseURI = System.getProperty("conwaysgame.loadpath", "");
		if (baseURI == "")
			throw new UserExitEvent();
		if (!baseURI.endsWith("/"))
			baseURI = baseURI + "/";

		String file = "";

		while (true) {
			while (file == "") {
				file = this.askUser("Enter File to load from (use EXIT to quit):");
				file = file.replaceAll(" ", "%20");
			}
			if (file.equals("EXIT"))
				throw new UserExitEvent();

			try {
				URL path = new URL(baseURI + file);
				File f = new File(path.toURI());
				return f;
			} catch (NullPointerException | MalformedURLException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	private List<BlockPos> loadWorld() {
		List<BlockPos> toPlace = new ArrayList<>();
		
		try {
			File f = this.askForFile();
			BufferedReader filereader = new BufferedReader(new FileReader(f));

			String line;
			int i = 0;
			String split = " ";
			
			while ((line = filereader.readLine()) != null) {
				if (line.startsWith("#"))
					continue;

				if (line.startsWith("\"")) {
					int len = line.length();
					if (len > 3 && line.endsWith("\"")) {
						split = line.substring(1, len - 1);
					} else {
						System.out.println(
								"please use the \" command in the following way: you start the line wiht \", then you add the character to split the lines with, then you add another \". skipping line "
										+ i + ": " + line);
					}
					continue;
				}

				BlockPos pos = BlockPos.fromString(line, split);
				if (pos == null) {
					System.out.println("error in line " + i + ", skipping it: " + line);
					continue;
				}
				
				toPlace.add(pos);
				
				i++;
			}
			filereader.close();
		} catch (UserExitEvent e) {

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("loading successfull!");
		
		return toPlace;
	}

	private static void printWorld(IWorld world, int size) {
		int n = 5;
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				System.out.print(world.getBlock(new BlockPos(x, y)) == Blocks.ALIVE ? "#"
						: (((x % n == 0) || (y % n == 0)) ? "‌." : " "));
			}
			System.out.println();
		}
	}
	
	static class BallMain {
		private int mspt = 100;
		private BallWorld world = new BallWorld(mspt);
		private BallRenderThread renderer = new BallRenderThread(this.world);
		private Thread render = new Thread(null, this.renderer, "render_thread");
		public BallMain() {
			this.render.start();
		}
		
		public void main() {
			int i = -1;
			while(this.render.getState() != Thread.State.TERMINATED && (i == -1 || i-- > 0)) {
				world.tick();
				try {
					Thread.sleep((long) (mspt));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
