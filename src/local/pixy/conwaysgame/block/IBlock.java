package local.pixy.conwaysgame.block;

/**
 * @author pixy
 *
 */
public interface IBlock {

	/**
	 * @return
	 */
	IBlockState[] getNeightbourStates();

	/**
	 * @return
	 */
	IBlockState getState();

	/**
	 * 
	 */
	void tick();

	/**
	 * 
	 */
	void tickCalculate();
	
	/**
	 * 
	 */
	void tickUpdatesState();
	
	void setState(IBlockState state);

}
