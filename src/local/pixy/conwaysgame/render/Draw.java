package local.pixy.conwaysgame.render;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author pixy
 *
 */
public class Draw {
	public static void triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
		glBegin(GL_TRIANGLES);
		glVertex2d(x1, y1);
		glVertex2d(x2, y2);
		glVertex2d(x3, y3);
		glEnd();
	}

	public static void line(double x1, double y1, double x2, double y2) {
		glBegin(GL_LINES);
		glVertex2d(x1, y1);
		glVertex2d(x2, y2);
		glEnd();
	}

	public static void rect(double x1, double y1, double x2, double y2) {
		glBegin(GL_QUADS);
		glVertex2d(x1, y1);
		glVertex2d(x2, y1);
		glVertex2d(x2, y2);
		glVertex2d(x1, y2);
		glEnd();
	}

	public static void square(double x, double y, double a) {
		Draw.rect(x, y, x + a, y + a);
	}

	public static void grid(double x1, double y1, double x2, double y2, int n) {
		double a = (x2 - x1) / n;
		double b = (y2 - y1) / n;
		glBegin(GL_LINES);
		for (int i = 0; i <= n; i++) {
			glVertex2d(x1 + i * a, y1);
			glVertex2d(x1 + i * a, y2);
			glVertex2d(x1, y1 + i * b);
			glVertex2d(x2, y1 + i * b);
		}
		glEnd();
	}

	public static void squareGrid(double x, double y, double a, int n) {
		Draw.grid(x, y, x + a, y + a, n);
	}

	public static void squareGridWithFilled(double x, double y, double a, boolean[][] content) {
		// Draw.squareGrid(x, y, a, n);
		for (int i = 0; i < content.length; i++) {
			for (int j = 0; j < content[i].length; j++) {
				if (content[i][j])
					Draw.rect(x + i * a / content.length, y + j * a / content[i].length,
							x + (i + 1) * a / content.length, y + (j + 1) * a / content[i].length);
			}
		}
	}

	public static void foo(Display display) {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 100, 0, 100, -1, 1);
		glMatrixMode(GL_MODELVIEW_MATRIX);

		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glColor3f(0.0f, 1.0f, 0.0f);

		glDisable(GL_DEPTH_TEST);
		glPointSize(1.0f);

		while (!display.shouldClose()) {
			// glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

			glBegin(GL_POINTS);
			double x, y;
			for (double i = 0; i < (2 * Math.PI); i += 0.001d) {
				x = 40 * Math.cos(i) + 50;
				y = 40 * Math.sin(i) + 50;

				glVertex2d(x, y);
			}
			glEnd();

			glPushMatrix();
			glTranslated(30, 30, 0);
			glRotated(10, 0, 0, 1);

			glBegin(GL_POLYGON);
			glVertex2d(10, 10);
			glVertex2d(20, 10);
			glVertex2d(20, 20);
			glEnd();
			glPopMatrix();

			glFlush();
			glfwSwapBuffers(display.getDisplayId()); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}
}
