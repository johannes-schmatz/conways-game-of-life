package local.pixy.conwaysgame.world;

import local.pixy.conwaysgame.util.ColorUtils;

import static org.lwjgl.opengl.GL11.*;

public class GravityWorld extends AbstractWorld implements IEntityWorld {
	private double maxs = 100d;//0d;

	public GravityWorld(int mspt){
		super(mspt);

		//addGravitySource(25, 60, 700, 0xFFFFFF);
		addGravitySource(50, 50, 500, 0xFFFFFF);
		//addGravitySource(70, 40, 1000, 0xFFFFFF);
		//addGravityBall(50, 100, 0, -7, 150, 0xFF0000);
		//addGravityBall(60, 100, -5, 5, 20, 0x00FF00);
		//addGravityBall(75, 65, 1, 3, 100, 0x0000FF);
		//addGravityBall(77, 66, -30, 4, 100, 0xFF00FF);
		addGravityBall(66, 23, -3, -1, 1, 0x888888);
		addGravityBall(34, 23, 3, -1, 1, 0x00FF00);
		addGravityBall(34, 77, -3, 1, 1, 0xFFFF00);
		addGravityBall(23, 34, 1, -3, 1, 0x00FFFF);
		addGravityBall(77, 66, -1, 3, 1, 0xFF00FF);
	}

	private double aspectRatio = 0d;
	private double ballSizeX = 0d;
	private double ballSizeY = 0d;
	private double mppxx = 0d;
	private double mppxy = 0d;

	@Override
	public void prerender(int width, int height) {
		aspectRatio = (double) width / (double) height;
		
		double b = 10;
		
		ballSizeX = b * aspectRatio;
		ballSizeY = b;
		
		ballSizeX /= 4;


		mppxx = height / maxs;
		mppxy = mppxx / aspectRatio;
	}

	@Override
	public void render() {
		glPushMatrix();
		glBegin(GL_QUADS);

		for(IEntity i : entities) {
			double x = i.getPos().x * mppxx;
			double y = i.getPos().y * mppxy;

			ColorUtils.setOpenGLColorState(i.getColor());

			glVertex2d(x - ballSizeX, y);
			glVertex2d(x + ballSizeX, y);
			glVertex2d(x + ballSizeX, y + ballSizeY);
			glVertex2d(x - ballSizeX, y + ballSizeY);
		}

		glEnd();
		glPopMatrix();
	}
}