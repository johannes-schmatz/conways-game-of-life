package local.pixy.conwaysgame.math;

/**
 * @author pixy
 * @param <T> The type of the class that extends {@link BasicVector}.
 *
 */
public abstract class BasicVector<T extends BasicVector<T>> {
	private final int x;
	private final int y;

	/**
	 * Creates a new vector.
	 * @param x The x-value of the vector.
	 * @param y The y-value of the vector.
	 */
	public BasicVector(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Function to get the x-value.
	 * @return The x-value of the vector.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Function to get the y-value.
	 * @return The y-value of the vector.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Function to add a vector.
	 * @param add The vector to add to the current instance.
	 * @return The result of the addition.
	 */
	public T add(T add) {
		return this.from(this.x + add.getX(), this.y + add.getY());
	}

	/**
	 * Function to move a vector.
	 * @param add The integer to add to x and y-value of the current instance. 
	 * @return The result of the addition.
	 */
	public T add(int add) {
		return this.from(this.x + add, this.y + add);
	}
	
	/**
	 * Function to multiply a vector.
	 * @param mul The vector, whose x-value and y-value will be multiplied with the x-value and y-value of the current instance.
	 * @return The result of the multiplication.
	 */
	public T multiply(T mul) {
		return this.from(this.x * mul.getX(), this.y * mul.getY());
	}

	/**
	 * Function to multiply a vector.
	 * @param mul The factor to multiply both x and y-value of the current instance with.
	 * @return The result of the multiplication.
	 */
	public T multiply(int mul) {
		return this.from(this.x * mul, this.y * mul);
	}

	
	/**
	 * Function to divide a vector.
	 * @param div The vector to divide the x and the y-value of the current instance with.
	 * @return The result of the division.
	 * @implNote Values are getting casted to integer.
	 */
	public T divide(T div) {
		return this.from((int) this.x / div.getX(), (int) this.y / div.getY());
	}
	
	/**
	 * Function to divide a vector.
	 * @param div The value to divide the x and y-value of the current instance with.
	 * @return The result of the division.
	 * @implNote Values are getting casted to integer.
	 */
	public T divide(int div) {
		return this.from((int) this.x / div, (int) this.y / div);
	}

	/**
	 * Function to mod a vector.
	 * @param mod The value to modulo the x and y-value of the current instance with the x and y-value with.
	 * @return The result of the modulo operation.
	 */
	public T modulo(T mod) {
		return this.from(this.x % mod.getX(), this.y % mod.getY());
	}
	
	/**
	 * Function to mod a vector.
	 * @param mod The value to modulo the x and y-value of the current instance with.
	 * @return The result of the modulo operation.
	 */
	public T modulo(int mod) {
		return this.from(this.x % mod, this.y % mod);
	}

	/**
	 * Function to negate a vector.
	 * @return The vector with both x and y-value multiplied by <code>-1</code>.
	 */
	public T invert() {
		return this.from(-this.getX(), -this.getY());
	}

	/**
	 * Function to generate a <code>T</code>-object of the values.
	 * @param x The x-value of the <code>T</code>-object to create.
	 * @param y The y-value of the <code>T</code>-object to create.
	 * @return The generated <code>T</code>-object.
	 * @apiNote Each class that extends this class must implement this.
	 * @implSpec A sample implementation could look like:
	 * <blockquote><pre>
	 * {@literal @}Override
	 * public BlockPos from(int x, int y) {
	 * 	return new BlockPos(x, y);
	 * }
	 * </pre></blockquote>
	 */
	public abstract T from(int x, int y);
}
