package local.pixy.conwaysgame.render;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.joml.Vector2d;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;

import local.pixy.conwaysgame.block.Blocks;
import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.ChunkPos;
import local.pixy.conwaysgame.math.Direction;
import local.pixy.conwaysgame.world.IChunk;
import local.pixy.conwaysgame.world.IWorld;
import local.pixy.conwaysgame.world.LoadLevel;
import local.pixy.conwaysgame.world.World;

/**
 * @author pixy
 *
 */
public class ClientRenderThread implements Runnable {
	private IWorld world;
	private Display display;
	private Runnable toggleWorldTicking;
	private Runnable tickWorld;
	private Supplier<List<BlockPos>> loadWorld;
	private List<BlockPos> toPlace = null;

	public ClientRenderThread(IWorld world, Runnable toggleWorldTicking, Runnable tickWorld,
			Supplier<List<BlockPos>> loadWorld) {
		this.world = world;
		this.toggleWorldTicking = toggleWorldTicking;
		this.tickWorld = tickWorld;
		this.loadWorld = loadWorld;
	}

	@Override
	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		HashMap<Integer, Boolean> config = new HashMap<>();
		config.put(GLFW_VISIBLE, false);
		config.put(GLFW_RESIZABLE, true);
		config.put(GLFW_MAXIMIZED, true);

		display = new Display();

		display.configWindowHints(config);
		display.configEnableVsync();
		display.configSize(DefaultValues.width / 2, DefaultValues.height / 2);
		// display.configSizeToScreen();

		display.init();

		display.setKeyCallback((window, key, scancode, action, mods) -> {
			if (action == GLFW_RELEASE) {
				switch (key) {
				case GLFW_KEY_ESCAPE -> glfwSetWindowShouldClose(window, true);
				case GLFW_KEY_F -> this.tickWorld.run();
				case GLFW_KEY_UP -> this.sensitivity++;
				case GLFW_KEY_DOWN -> this.sensitivity--;
				case GLFW_KEY_E -> this.toggleWorldTicking.run();
				case GLFW_KEY_Q -> this.loadWorldStart();
				case GLFW_KEY_G -> this.loadWorldPlace();
				case GLFW_KEY_L -> this.toggleChunkBorders();
				}
			}
			switch (key) {
			case GLFW_KEY_W, GLFW_KEY_A, GLFW_KEY_S, GLFW_KEY_D -> this.handleMovementInput(window, key, scancode,
					action, mods);
			}
		});

		gameLoop();

		display.quit();
	}

	private void handleMovementInput(long window, int key, int scancode, int action, int mods) { // TODO: Bad code,
																									// could be
																									// optimized!!
		Direction keyDirection = switch (key) {
		case GLFW_KEY_W -> Direction.NORTH;
		case GLFW_KEY_A -> Direction.WEST;
		case GLFW_KEY_S -> Direction.SOUTH;
		case GLFW_KEY_D -> Direction.EAST;
		default -> Direction.SELF;
		};

		if (action == GLFW_RELEASE)
			keyDirection = keyDirection.invert();

		this.inputDirection = this.inputDirection.add(keyDirection);

		if (Direction.SELF.equals(this.inputDirection))
			return;

		if (this.toPlace == null || (mods & GLFW_KEY_MENU) != 0) {
			//this.movement.add(this.inputDirection.x * this.sensitivity, this.inputDirection.y * this.sensitivity);
			this.movement.sub(this.inputDirection.x * this.sensitivity, this.inputDirection.y * this.sensitivity);
		} else {
			Iterator<BlockPos> iter = this.toPlace.iterator();
			List<BlockPos> newList = new ArrayList<>();
			BlockPos pos;
			while (iter.hasNext()) {
				pos = iter.next();

				newList.add(pos.offset(keyDirection));
			}

			this.toPlace = newList;
		}
	}

	private Direction inputDirection = Direction.SELF;
	private Vector2d movement = new Vector2d(0d, 0d);
	private double sensitivity = 20;

	private void loadWorldStart() {
		if (this.toPlace == null) {
			this.toPlace = this.loadWorld.get();
		} else {
			this.toPlace = null;
		}
	}

	private void loadWorldPlace() {
		if (this.toPlace != null) {
			for (BlockPos i : this.toPlace) {
				synchronized (this.world) {
					this.world.setBlock(i, Blocks.ALIVE);
				}
			}
		}
	}
	
	private boolean displayChunkBorders = false;
	private void toggleChunkBorders() {
		this.displayChunkBorders = !this.displayChunkBorders;
	}

	private void gameLoop() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glOrtho(0, mode.width(), 0, mode.height(), -1, 1);

		// glOrtho(0, DefaultValues.width / 2, 0, DefaultValues.height / 2, -1, 1);
		glMatrixMode(GL_MODELVIEW_MATRIX);

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glColor3f(0.0f, 1.0f, 0.0f);

		glDisable(GL_DEPTH_TEST);
		glPointSize(1.0f);
		glLineWidth(0.5f);

		while (!this.display.shouldClose()) {
			glClear(GL_COLOR_BUFFER_BIT);

			glPushMatrix();
			glTranslated(this.movement.x, this.movement.y, 0);

			this.renderAllChunks();

			glPopMatrix();

			glFlush();
			glfwSwapBuffers(this.display.getDisplayId());
			glfwPollEvents();
		}
	}

	private void renderAllChunks() {
		int m = 1;
		int spaceBetweenChunks = 8 ; //8;
		int sizePerChunk = 16 * 16;
		int f = 15 * 8;
		synchronized (this.world) {
			// Ich muss hier eine kopie verwenden, sonst gibt es irgendwo her einen crash TODO!!!
			//Iterator<ChunkPos> iterator = this.world.getChunkIterator();
			//Map<ChunkPos, IChunk> contentMap = new HashMap<>(((World) this.world).content);
			Map<ChunkPos, IChunk> contentMap = new HashMap<>(this.world.getContent());
			Iterator<ChunkPos> iterator = contentMap.keySet().iterator();
			//Iterator<ChunkPos> iterator = this.world.getChunkCopyIterator();
			while (iterator.hasNext()) {
				ChunkPos cpos = iterator.next();
				int x_ = cpos.getX();
				int y_ = cpos.getY();
				
				int[][] content = this.world.getChunk(cpos).getContent();

				x_ += m;
				y_ += m;
				
				Draw.squareGridWithFilled(x_, y_, spaceBetweenChunks, sizePerChunk, content,
						(pos, block) -> {
							//return true;
							//return ((World) this.world).content.containsKey(pos.getChunkPos());
							return Blocks.isAlive(block);
							//return this.world.getChunk(pos.getChunkPos()).getLoadLevel().getLevel() == LoadLevel.FULL.getLevel();
						}, (x, y) -> {
							IChunk c = this.world.getChunk(cpos);
							if (c != null)
							return 16 * c.getLoadLevel().getLevel() + 16;
							return 16;}, (x, y) -> 0 /*x * 8*/, (x, y) -> 0 /*y * 8*/);
				
				LoadLevel ll = this.world.getChunk(cpos).getLoadLevel();
				
				switch (ll) {
				case NOT -> glColor3b(127, 0, 0);
				case BORDER -> glColor3b(0, 127, 0);
				case TICKING -> glColor3b(0, 0, 127);
				case FULL -> glColor3b(127, 127, 127);
				}
				
				if (this.displayChunkBorders) {
					x_ = x_ * (spaceBetweenChunks + sizePerChunk) - spaceBetweenChunks;
					y_ = y_ * (spaceBetweenChunks + sizePerChunk) - spaceBetweenChunks;
					Draw.line(x_, y_, x_ + sizePerChunk + spaceBetweenChunks, y_);
					Draw.line(x_, y_, x_, y_ + sizePerChunk + spaceBetweenChunks);
				}
			}
		}

		if (this.toPlace != null) {
			BlockPos chunkSize = new BlockPos(IChunk.SIZE, IChunk.SIZE);
			for (BlockPos i : this.toPlace) {
				Draw.drawBlocksFromList(i.add(chunkSize), spaceBetweenChunks, sizePerChunk, (x, y) -> f, (x, y) -> f,
						(x, y) -> f);
			}
		}
	}

	private void glColor3b(int i, int j, int k) {
		GL11.glColor3b((byte) i, (byte) j, (byte) k);
	}
}
