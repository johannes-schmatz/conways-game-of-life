package local.pixy.conwaysgame.world;

import org.joml.Vector2d;

public class GravitySource implements IEntity {
	private int color;
	private final double mass;
	private Vector2d pos;

	public double getMass() {
			return mass;
	}

	public GravitySource(double x, double y, int color, double mass) {
		this.pos = new Vector2d(x, y);
		this.color = color;
		this.mass = mass;
	}

	@Override
	public Vector2d getPos() {
		return pos;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public void tick() {
	}
}
