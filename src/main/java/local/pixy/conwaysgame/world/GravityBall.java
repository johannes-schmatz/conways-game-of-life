package local.pixy.conwaysgame.world;

import org.joml.Vector2d;
import org.joml.Vector3i;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

class GravityBall extends AbstractBall implements IEntity {
	private final double delta_t;
	private Vector2d velocity;
	private final List<IEntity> entities;

	public GravityBall(List<IEntity> entities, double delta_t, double x,
	                   double y, double vx, double vy, double mass, int color) {
		super(x, y, mass, color);
		this.entities = entities;
		this.velocity = new Vector2d(vx, vy);
		this.delta_t = delta_t;
	}

	@Override
	public void tick() {
		tmp.set(0d, 0d);

		double dx, dy, r2, phi, F;
		for(IEntity i : entities){
			if(i == this)
				continue;
			dx = pos.x - i.getPos().x;
			dy = pos.y - i.getPos().y;
			phi = Math.atan2(dx, dy);

			r2 = dx * dx + dy * dy;

			F = i.getMass() * mass / r2;

			tmp.add(-F * Math.sin(phi), -F * Math.cos(phi));
		}

		tmp.div(getMass());
		tmp.mul(delta_t);
		velocity.add(tmp);
		tmp.set(velocity);
		tmp.mul(delta_t);
		tmp.add(pos);

		synchronized (pos) {
			pos.set(tmp.x, tmp.y);
		}
	}
}