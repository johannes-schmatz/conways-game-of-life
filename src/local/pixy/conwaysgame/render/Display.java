package local.pixy.conwaysgame.render;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.Map;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

/**
 * @author pixy
 *
 */
public class Display {
	private long displayId;
	private String title = "LWJGL " + Version.getVersion();
	private Map<Integer, Boolean> windowHints;
	private boolean vsync = false;
	private int width;
	private int height;

	public void configWindowHints(Map<Integer, Boolean> config) {
		this.windowHints = config;
	}

	public void configEnableVsync() {
		this.vsync = true;
	}

	public void configTitle(String title) {
		this.title = title;
	}

	public void configSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void configSizeToScreen() {
		GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		this.width = mode.width();
		this.height = mode.height();
	}

	public void quit() {
		glfwFreeCallbacks(this.displayId);
		glfwDestroyWindow(this.displayId);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public void init() {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();

		this.windowHints.forEach((hint, value) -> {
			glfwWindowHint(hint, value ? GLFW_TRUE : GLFW_FALSE);
		});

		this.displayId = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

		if (this.displayId == NULL)
			throw new RuntimeException("Failed to create window.");

		glfwMakeContextCurrent(this.displayId);
		
		if (this.vsync)
			glfwSwapInterval(1);

		glfwShowWindow(this.displayId);
		GL.createCapabilities();
	}

	public void setTitle(String title) {
		this.title = title;
		glfwSetWindowTitle(this.displayId, title);
	}

	public String getTitle() {
		return this.title;
	}

	public long getDisplayId() {
		return this.displayId;
	}

	public void setKeyCallback(GLFWKeyCallbackI fun) {
		glfwSetKeyCallback(this.displayId, fun);
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(this.displayId);
	}
}
