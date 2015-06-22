/** A representation of each queen */
public class Queen {
	private int row, column;

	/** Creates a queen at a certain row and column
	@param r The row the queen is at
	@param c The column the queen is at */
	public Queen(int r, int c) {
		row = r;
		column = c;
	}

	/** Return the row this queen is at
	@return row The row this queen is at */
	public int getRow() {
		return row;
	}

	/** Return the column this queen is at
	@return column The column this queen is at */
	public int getColumn() {
		return column;
	}
}