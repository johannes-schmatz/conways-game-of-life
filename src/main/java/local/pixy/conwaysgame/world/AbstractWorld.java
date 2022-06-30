package local.pixy.conwaysgame.world;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorld implements IEntityWorld {
	protected List<IEntity> entities = new ArrayList<>();
	public final double delta_t;

	public AbstractWorld(int mspt){
		this.delta_t = (double) mspt / 1000d;
	}

	@Override
	public synchronized void tick() {
		for(IEntity i : this.entities) {
			i.tick();
		}
	}

	protected void addGravitySource(double x, double y, double mass, int color){
		IEntity entity = new GravitySource(x, y, color, mass);
		entities.add(entity);
	}

	protected void addGravityBall(double x, double y, double vx, double vy, double mass, int color) {
		IEntity entity = new GravityBall(entities, delta_t, x, y, vx, vy, mass, color);
		entities.add(entity);
	}

	protected void addBall(double x, double y, double vx, double vy, double mass, int color) {
		IEntity entity = new Ball(delta_t, x, y, vx, vy, mass, color);
		entities.add(entity);
	}
}
