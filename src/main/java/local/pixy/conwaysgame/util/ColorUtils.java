package local.pixy.conwaysgame.util;

import org.joml.Vector3i;

import static org.lwjgl.opengl.GL11.glColor3b;

public class ColorUtils {
	public static void setOpenGLColorState(int color){
		int r = ((color >> 16) % 256) / 2;
		int g = ((color >> 8) % 256) / 2;
		int b = (color % 256) / 2;
		glColor3b((byte) r, (byte) g, (byte) b);
	}

}
