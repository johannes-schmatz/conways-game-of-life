package local.pixy.conwaysgame.world;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joml.Vector2d;
import org.joml.Vector3i;

import org.lwjgl.opengl.GL11;

public class BallWorld {
	private List<Ball> entities;
	public final double delta_t;
	private double maxs = 0d;
	
	public BallWorld(int mspt){
		this.entities = new ArrayList<>();
		
		this.delta_t = (double) mspt / 1000d;
		this.addBall(50, 100, 0, 0, 1, 0xFF0000);
		this.addBall(60, 100, -5, 50, 2, 0x00FF00);
		this.addBall(70, 100, -20, -20, 3, 0x0000FF);
	}
	
	private void addBall(double s_0x, double s_0y, double v_0x, double v_0y, double m, int color) {
		int r = ((color >> 16) % 256) / 2;
		int g = ((color >> 8) % 256) / 2;
		int b = (color % 256) / 2;
		if (r > 127 || g > 127 || b > 127)
			throw new IllegalArgumentException("r = " + r + "; g = " + g + "; b = "+ b + "; of of them bigger than 127!!! openGL wants them to be below that!");
		this.entities.add(
				new Ball(
						this.delta_t,
						new Vector2d(s_0x, s_0y),
						new Vector2d(v_0x, v_0y),
						new Vector3i(r, g, b),
						m
				)
		);
		
		if(s_0y > this.maxs)
			this.maxs = s_0y;
	}
	
	public synchronized void tick() {
		for(Ball i : this.entities) {
			i.tick();
		}
	}
	
	private double aspectRatio = 0d;
	private double floorHeight = 0d;
	private double wallWidth = 0d;
	private int width = 0;
	private int height = 0;
	private double ballSizeX = 0d;
	private double ballSizeY = 0d;
	private double mppxx = 0d;
	private double mppxy = 0d;
	
	public void prerender(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.aspectRatio = (double) width / (double) height;
		
		double b = 4;
		
		this.ballSizeX = b * this.aspectRatio;
		this.ballSizeY = b;
		
		double f = 0.01d;
		this.floorHeight = f * height;
		this.wallWidth = f * width;
		
		double spaceHeigth = height - this.floorHeight;
		
		this.mppxx = spaceHeigth / (1d * this.maxs);
		this.mppxy = this.mppxx / this.aspectRatio;
		
		this.ballSizeX /= 4;
	}
	
	public void render() {
		{
			glColor3d(64, 64, 0);
			
			glPushMatrix();
			glBegin(GL_QUADS);
			
			glVertex2d(this.wallWidth, 0);
			glVertex2d(this.width, 0);
			glVertex2d(this.width, this.floorHeight);
			glVertex2d(this.wallWidth, this.floorHeight);
			
			glVertex2d(0, this.floorHeight);
			glVertex2d(this.wallWidth, this.floorHeight);
			glVertex2d(this.wallWidth, this.height);
			glVertex2d(0, this.height);
			
			glEnd();
			glPopMatrix();
		}
		
		glTranslated(this.wallWidth, this.floorHeight, 0);
		glPushMatrix();
		for(Ball i : this.entities) {
			i.render(this.mppxx, this.mppxy, this.ballSizeX, this.ballSizeY);
		}
		glPopMatrix();
	}
}