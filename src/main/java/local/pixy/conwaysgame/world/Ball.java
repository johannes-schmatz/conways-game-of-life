package local.pixy.conwaysgame.world;

import org.joml.Vector2d;

class Ball extends AbstractBall implements IEntity {
	private final double delta_t;
	private Vector2d velocity;

	public static final Vector2d g = new Vector2d(0, -9.81);
	private final Vector2d f_G;

	public Ball(double delta_t, double x, double y, double vx, double vy,
	            double mass, int color) {
		super(x, y, mass, color);
		this.f_G = new Vector2d(Ball.g).mul(mass);
		this.velocity = new Vector2d(vx, vy);
		this.delta_t = delta_t;
	}

	@Override
	public void tick() {
		double x = (velocity.x < 0 ? 1d : -1d) * velocity.x * velocity.x;
		double y = (velocity.y < 0 ? 1d : -1d) * velocity.y * velocity.y;
		
		tmp.set(f_G);
		tmp.add(x, y);
		tmp.div(mass);
		tmp.mul(delta_t);
		velocity.add(tmp);
		
		if (pos.x < 0)
			velocity.x *= -1;
		
		if (pos.y < 0)
			velocity.y *= -1;

		tmp.set(velocity);
		tmp.mul(delta_t);
		tmp.add(pos);

		synchronized (pos) {
			pos.set(tmp.x, tmp.y);
		}
	}
}