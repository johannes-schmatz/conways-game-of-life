package local.pixy.conwaysgame.world;

import static org.lwjgl.opengl.GL11.*;

import local.pixy.conwaysgame.util.ColorUtils;

public class BallWorld extends AbstractWorld implements IEntityWorld {
	private double maxs = 100d;
	
	public BallWorld(int mspt){
		super(mspt);

		addBall(50, 100, 0, 0, 1, 0xFF0000);
		addBall(60, 100, -5, 50, 2, 0x00FF00);
		addBall(70, 100, -20, -20, 3, 0x0000FF);
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

	@Override
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

	@Override
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
		glBegin(GL_QUADS);

		for (IEntity i : entities) {
			double x = i.getPos().x * mppxx;
			double y = i.getPos().y * mppxy;

			if (x < 0d)
				x = 0d;
			if (y < 0d)
				y = 0d;

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