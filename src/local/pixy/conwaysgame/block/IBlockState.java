package local.pixy.conwaysgame.block;

/**
 * @author pixy
 *
 */
public interface IBlockState {
	int getIntegerState();
	boolean is(int state);

	boolean isNot(int state);
	
	boolean is(IBlockState state);

	boolean isNot(IBlockState state);

	/**
	 * 
	 */
	void tick();

	/**
	 * @param neightbours
	 */
	void tickCalculate(IBlockState[] neightbours);

	/**
	 * 
	 */
	void tickUpdateState();

}
