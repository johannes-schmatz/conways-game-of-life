package local.pixy.conwaysgame.world;

import org.joml.Vector2d;
import org.joml.Vector3i;

import local.pixy.conwaysgame.render.Draw;

import static org.lwjgl.opengl.GL11.*;

class Ball {
	private final Vector3i color;
	
	public static final Vector2d g = new Vector2d(0, -9.81); //m*s^-2
	private final Vector2d f_G;
	private final double delta_t;
	public static final double c_w = 0.032d;
	
	private Vector2d v;
	private Vector2d tmp = new Vector2d();
	private final double m;
	private Vector2d pos;
	
	public Ball(double delta_t, Vector2d s_0, Vector2d v_0, Vector3i color, double mass) {
		this.pos = new Vector2d(s_0);
		this.v = new Vector2d(v_0);
		this.delta_t = delta_t;
		this.color = color;
		this.m = mass;
		
		this.f_G = new Vector2d(Ball.g).mul(m);
	}
	
	public void tick() {
		//System.out.println("m = " + this.m + "; delta_t = " + this.delta_t + "; color = " + this.color.toString());
		double x = (this.v.x < 0 ? 1d : -1d) * Ball.c_w * this.v.x * this.v.x;
		double y = (this.v.y < 0 ? 1d : -1d) * Ball.c_w * this.v.y * this.v.y;
		
		this.tmp = this.tmp.set(this.f_G);
		
		//System.out.println("F_G = " + this.tmp.toString());
		
		this.tmp.add(x, y);
		
		//System.out.println("F = " + this.tmp.toString());
		
		this.tmp.div(this.m);
		
		System.out.println("a = " + this.tmp.toString());
		
		this.tmp.mul(this.delta_t);
		
		this.v.add(tmp);
		
		if (this.pos.x < 0)
			this.v.x *= -1;
		
		if (this.pos.y < 0)
			this.v.y *= -1;
		
		this.tmp.set(this.v);
		
		//System.out.println("v = " + this.tmp.toString());
		
		this.tmp.mul(this.delta_t);
		
		//System.out.println("delta_s " + this.tmp.toString());
		
		this.tmp.add(this.pos);
		
		synchronized (this.pos) {
			this.pos.set(this.tmp.x, this.tmp.y);
		}
		
		//System.out.println("s = " + this.pos.toString());
	}
	
	public void render(double mppxx, double mppxy, double bx, double by) {
		double x, y;
		synchronized (this.pos) {
			x = this.pos.x;
			y = this.pos.y;
		}
		
		x *= mppxx;
		y *= mppxy;
		
		if(x < 0d)
			x = 0d;
		if(y < 0d)
			y = 0d;
		
		glColor3b((byte) this.color.x, (byte) this.color.y, (byte) this.color.z);
		
		glBegin(GL_QUADS);
		glVertex2d(x - bx, y);
		glVertex2d(x + bx, y);
		glVertex2d(x + bx, y + by);
		glVertex2d(x - bx, y + by);
		glEnd();
		
		//System.out.println("" + x + " " + y);
	}
}