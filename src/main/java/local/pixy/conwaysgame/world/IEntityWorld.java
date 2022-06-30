package local.pixy.conwaysgame.world;

import local.pixy.conwaysgame.math.BlockPos;
import local.pixy.conwaysgame.math.ChunkPos;

import java.util.Iterator;
import java.util.Map;

/**
 * @author pixy
 * TODO: write javadoc!
 */
public interface IEntityWorld {
	void tick();
	void render();
	void prerender(int width, int height);
}
