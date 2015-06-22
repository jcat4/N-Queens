import java.lang.Math.*;
import java.util.*;

/**This class is used to keep track of all the instances of the chessboard, the Data
structures containing the Queen objects and a list of confirmed solutions, and various
methods that dictate the Queen objects' placements on the board
*/
public class Chessboard {

	private int board[][];
	private int numOfQueens = 0, queenArrayIndex = 0, rows, columns;
	private ArrayList < Queen > queens = new ArrayList < Queen > ();
	private ArrayList < Queen > fixedQueens = new ArrayList < Queen > ();

	/**	Create a Chessboard object and specify the number of rows and columns on the board.
	@param r, a positive integer number of rows on the board.
	@param c, a positive integer number of columns on the board. 
	*/
	public Chessboard(int r, int c) {
		rows = r;
		columns = c;
		if (r > c) {
			rows = c;
			columns = r;
		}
		board = new int[rows][columns];
	}

	/** Get the board array
	@return board the global board array
	*/
	public int[][] getBoard() {
		return board;
	}

	/**Check if Queen can be placed at row r and column c
	@param r, an integer value for a row of board.
	@param c, an integer value for a column of board.
	@return canPlace, a boolean value that is true if Queen can be placed on row r and column c.
	*/
	public boolean checkIfPlace(int r, int c) {
		boolean canPlace = true;
		if ((r >= rows) || (c >= columns)) {
			canPlace = false;
		} else if ((board[r][c] < 0)) {
			canPlace = false;
		}
		return canPlace;
	}

	/** Check if Queen is at a certain place on the Board
	@param r, an integer value for a row of board.
	@param c, an integer value for a column of board.
	@return isHere, a boolean value that is true if Queen is on row r and column c.
	*/
	public boolean isQueenHere(int r, int c) {
		boolean isHere = false;
		if (numOfQueens != 0) {
			for (int i = 0; i < numOfQueens; i++) {
				if ((queens.get(i).getRow() == r) && (queens.get(i).getColumn() == c)) {
					isHere = true;
				}
			}
		}
		return isHere;
	}

	/** Check if the Chessboard is currently empty
	@return empty, a boolean value that is true if any Queens exist on the board
	*/
	public boolean isBoardEmpty() {
		boolean empty = true;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				if (isQueenHere(r, c)) {
					empty = false;
				}
			}
		}
		return empty;
	}

	/** Either place or remove a Queen from the board and decrement or 
	increment all values at indexes of board that are attacked by that Queen.
	@param queen, a Queen that is placed or removed from the chessboard.
	*/
	public void placeOrRemoveQueen(int r, int c, int incOrDec) { //Variable x is either -1 for Remove or 1 for Insert
		if (incOrDec == -1) {
			Queen queen = new Queen(r, c);
			queens.add(queenArrayIndex, queen);
			numOfQueens++;
			queenArrayIndex++;
		} else if (incOrDec == 1) {
			numOfQueens--;
			queenArrayIndex--;
			queens.remove(queenArrayIndex);
		}

		board[r][c] += incOrDec;

		for (int i = 0; i < columns; i++) {
			if (i != c) {
				board[r][i] += incOrDec;
			}
		}

		for (int i = 0; i < rows; i++) {
			if (i != r) {
				board[i][c] += incOrDec;
			}
		}

		int tempR = r + 1;
		int tempC = c + 1;

		while ((tempR < rows) && (tempC < columns)) {
			board[tempR][tempC] += incOrDec;
			tempR++;
			tempC++;
		}

		tempR = r - 1;
		tempC = c - 1;

		while ((tempR < rows) && (tempC < columns) && (0 <= tempR) && (0 <= tempC)) {
			board[tempR][tempC] += incOrDec;
			tempR--;
			tempC--;
		}

		tempR = r + 1;
		tempC = c - 1;

		while ((tempR < rows) && (tempC < columns) && (0 <= tempR) && (0 <= tempC)) {
			board[tempR][tempC] += incOrDec;
			tempR++;
			tempC--;
		}

		tempR = r - 1;
		tempC = c + 1;

		while ((tempR < rows) && (tempC < columns) && (0 <= tempR) && (0 <= tempC)) {
			board[tempR][tempC] += incOrDec;
			tempR--;
			tempC++;
		}
	}

	/**Remove all Queens from Board and queens array
	 */
	public void removeAllQueens() {
		if (numOfQueens != 0) {
			fixedQueens.clear();
			for (int i = (numOfQueens - 1); i >= 0; i--) {
				placeOrRemoveQueen(queens.get(i).getRow(), queens.get(i).getColumn(), 1);
			}
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < columns; c++) {
					board[r][c] = 0;
				}
			}
		}
	}

	/** Find a solution (if possible) for the non attacking queens 
	problem for a Chessboard object given any placement of Queens
	@param row, an integer value of a row of the board.
	@return ans, a boolean that indicates whether there 
	is a solution for this Chessboard object.
	*/
	public boolean solve(int r) {
		boolean ans = false;
		if (isSolution()) {
			ans = true;
		} else if (!areQueensAttacked()) {
			for (int c = 0;
			(c < columns) && (ans == false); c++) {
				if (isFixedQueenOnRow(r)) {
					ans = solve((r + 1));
				} else if (board[r][c] == 0) {
					placeOrRemoveQueen(r, c, -1);
					ans = solve((r + 1));
					if (!ans) {
						placeOrRemoveQueen(r, c, 1);
					}
				}
			}
		}
		return ans;
	}

	/**Print the chessboard's board array represented as a String
	@return boardString, a String representation of the chessboard's board array.
	*/
	public String boardToString() {
		String boardString = "\n";
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				if (board[r][c] == 0) {
					boardString += "00 ";
				} else {
					if (isQueenHere(r, c)) {
						boardString += ("Qn ");
					} else {
						boardString += (String.valueOf(board[r][c]) + " ");
					}
				}
			}
			boardString += "\n";
		}
		return boardString;
	}

	/**Check if there's a fixed Queen on a specified row
	@param r the row (array index) to check for fixed Queens
	@return fixedQueenOnRow a boolean signifying whether 
	there's a fixed Queen on the given row
	*/
	public boolean isFixedQueenOnRow(int r) {
		boolean fixedQueenOnRow = false;
		if (fixedQueens.size() != 0) {
			for (int i = 0;
			(i < fixedQueens.size()) && (!fixedQueenOnRow); i++) {
				if (fixedQueens.get(i).getRow() == r) {
					fixedQueenOnRow = true;
				}
			}
		}
		return fixedQueenOnRow;
	}

	/**Return the Queen (if any) at a specified coordinate
	@param r the row of the Queen
	@param c the column of the Queen
	@return the desired Queen; if it doesn't exist, return null
	*/
	public Queen getQueenAt(int r, int c) {
		Queen desiredQueen = null;
		if (numOfQueens != 0) {
			for (int i = 0; i < numOfQueens; i++) {
				if ((queens.get(i).getRow() == r) && (queens.get(i).getColumn() == c)) {
					desiredQueen = queens.get(i);
				}
			}
		}
		return desiredQueen;
	}

	/**Return the Queen (if any) at a specified coordinate
	@param r the row of the Queen
	@param c the column of the Queen
	@return the desired Queen; if it doesn't exist, return null
	*/
	public Queen getFixedQueenAt(int r, int c) {
		Queen desiredQueen = null;
		if (numOfQueens != 0) {
			for (int i = 0; i < fixedQueens.size(); i++) {
				if ((fixedQueens.get(i).getRow() == r) && (fixedQueens.get(i).getColumn() == c)) {
					desiredQueen = fixedQueens.get(i);
				}
			}
		}
		return desiredQueen;
	}

	/** Check if any Queens on the board are attacking eachother
	@return areQueensAttacked a boolean determining if a Queen has been attacked or not
	*/
	public boolean areQueensAttacked() {
		boolean areQueensAttacked = false;
		if (numOfQueens != 0) {
			for (int i = 0;
			(i < numOfQueens) && (!areQueensAttacked); i++) {
				if (board[queens.get(i).getRow()][queens.get(i).getColumn()] < -1) {
					areQueensAttacked = true;
				}
			}
		}
		return areQueensAttacked;
	}

	/** Add a Fixed Queen to the fixedQueens ArrayList (CALLED FOR QUEENS USER PLACES IN GUI)
	@param queen the Queen to be added to the fixedQueens ArrayList
	*/
	public void addFixedQueen(Queen queen) {
		queen.setFixed(true);
		fixedQueens.add(queen);
	}

	/** Remove a Fixed Queen to the fixedQueens ArrayList (CALLED FOR QUEENS USER PLACES IN GUI)
	@param queen the Queen to be removed from the fixedQueens ArrayList
	*/
	public void removeFixedQueen(int r, int c) {
		if (fixedQueens.size() != 0) {
			for (int i = 0; i < fixedQueens.size(); i++) {
				Queen currentQueen = fixedQueens.get(i);
				if ((currentQueen.getRow() == r) && (currentQueen.getColumn() == c)) {
					fixedQueens.remove(i);
				}
			}
		}
	}

	/** Check if there are too many, not enough, or the maximum needed
	number of Queens placed on the board
	@return howMany an int variable that is set to '0' if the maximum
	number of Queens are placed, '1' is too many Queens are placed to
	be a solution, or '-1' if too little Queens are placed to be a solution
	*/
	public int tooFewManyOrMax() {
		int howMany = 0;
		if (numOfQueens != rows) {
			if (numOfQueens > rows) {
				howMany = 1;
			} else if (numOfQueens < rows) {
				howMany = -1;
			}
		}
		return howMany;
	}

	/** Check if the current arrangement of Queens is a solution
	@return solution a boolean variable that determines if the 
	current arrangement of Queens is a solution 
	*/
	public boolean isSolution() {
		boolean solution = false;
		if ((!areQueensAttacked()) && (numOfQueens == rows)) {
			solution = true;
		}
		return solution;
	}

	/** Get the Queens array
	@return queens the Queen array to be returned
	*/
	public ArrayList < Queen > getQueens() {
		return queens;
	}

	/** Get the number of Queens in the Queens array
	@return queens the number of Queens in the Queen array
	*/
	public int getNumOfQueens() {
		return numOfQueens;
	}

	/** Get the number of rows of the chessboard
	@return rows the number of rows of the chessboard
	*/
	public int getRows() {
		return rows;
	}

	/** Get the number of columns of the chessboard
	@return columns the number of columns of the chessboard
	*/
	public int getColumns() {
		return columns;
	}

	/** Set every Queen on the board to be "fixed"
	 */
	public void setAllQueensToFixed() {
		if (numOfQueens != 0) {
			for (int i = 0; i < numOfQueens; i++) {
				queens.get(i).setFixed(true);
				addFixedQueen(queens.get(i));
			}
		}
	}

}