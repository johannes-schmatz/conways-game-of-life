package local.pixy.conwaysgame.render;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import local.pixy.conwaysgame.world.IEntityWorld;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;

import local.pixy.conwaysgame.world.BallWorld;

/**
 * @author pixy
 *
 */
public class WorldRenderThread implements Runnable {
	private Display display;
	private IEntityWorld world;

	public WorldRenderThread(IEntityWorld world) {
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

		display.init();
		//display.configSizeToScreen();

		display.setKeyCallback((window, key, scancode, action, mods) -> {
			if (action == GLFW_RELEASE) {
				switch (key) {
				case GLFW_KEY_ESCAPE -> glfwSetWindowShouldClose(window, true);
				case GLFW_KEY_SPACE -> this.world.tick();
				}
			}
		});

		gameLoop();

		display.quit();
	}

	private void gameLoop() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		int width = mode.width();
		int height = mode.height();
		glOrtho(0, width, 0, height, -1, 1);

		//glOrtho(0, DefaultValues.width / 2, 0, DefaultValues.height / 2, -1, 1);
		glMatrixMode(GL_MODELVIEW_MATRIX);

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glColor3f(0.0f, 1.0f, 0.0f);

		glDisable(GL_DEPTH_TEST);
		glPointSize(1.0f);
		glLineWidth(0.5f);
		
		this.world.prerender(width, height);

		while (!this.display.shouldClose()) {
			glClear(GL_COLOR_BUFFER_BIT);

			glPushMatrix();

			this.world.render();

			glPopMatrix();

			glFlush();
			glfwSwapBuffers(this.display.getDisplayId());
			glfwPollEvents();
		}
	}
}