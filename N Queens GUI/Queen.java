/** This class is simply used as an instance for each placed Queen so 
that each Queen's location on the board can be easily kept track of. */
public class Queen {
	private int row, column;
	private boolean fixed = false;

	// Constructor -- i
	public Queen(int r, int c) {
		row = r;
		column = c;
	}

	// get row
	public int getRow() {
		return row;
	}

	// get collumn
	public int getColumn() {
		return column;
	}

	// check whether or not Queen is fixed
	public boolean isFixed() {
		return fixed;
	}

	// set Queen as fixed or not (FIXED IS SET TO TRUE WHEN THE USER PLACES A QUEEN)
	public void setFixed(boolean trueOrFalse) {
		if (trueOrFalse) {
			fixed = true;
		} else {
			fixed = false;
		}
	}
}