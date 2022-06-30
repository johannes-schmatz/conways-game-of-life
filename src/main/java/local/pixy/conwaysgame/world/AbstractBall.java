package local.pixy.conwaysgame.world;

import org.joml.Vector2d;

public abstract class AbstractBall implements IEntity {
	protected Vector2d tmp = new Vector2d();
	protected final Vector2d pos;
	protected final double mass;
	protected int color;

	public AbstractBall(double x, double y, double mass, int color){
		this.pos = new Vector2d(x, y);
		this.mass = mass;
		this.color = color;
	}

	@Override
	public synchronized Vector2d getPos() {
		return pos;
	}

	@Override
	public double getMass() {
		return mass;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public void setColor(int color) {
		this.color = color;
	}
}
