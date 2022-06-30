package local.pixy.conwaysgame.world;

import org.joml.Vector2d;
import org.joml.Vector3i;

public interface IEntity {
	void tick();
	Vector2d getPos();
	double getMass();
	int getColor();
	void setColor(int color);
}
