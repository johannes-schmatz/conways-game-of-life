package local.pixy.conwaysgame.render;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWVidMode;

import local.pixy.conwaysgame.world.World;

/**
 * @author pixy
 *
 */
public class ClientRenderThread implements Runnable {
	private World world;
	private Display display;

	public ClientRenderThread(World world) {
		this.world = world;
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
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
			if (key == GLFW_KEY_B && action == GLFW_RELEASE)
				synchronized (this.world) {
					this.world.tick();
				}
		});

		loop();

		display.quit();
	}

	private void loop() {
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

			Draw.grid(100, 200, 300, 400, 5);
//			getChunkRender(0, 0);
//			getChunkRender(0, 1);
//			getChunkRender(1, 0);
//			getChunkRender(1, 1);
			glFlush();
			glfwSwapBuffers(this.display.getDisplayId());
			glfwPollEvents();
		}
	}
	private void getChunkRender(int x, int y) {
		int f = 16;
		int e = 16*14;
		//boolean[][] content = world.getChunk(new ChunkPos(x, y)).toBoolArr();
		//Draw.squareGridWithFilled(x * f, y * f, e, content);
	}
}
