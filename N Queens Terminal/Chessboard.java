import java.lang.Math.*;
import java.util.*;

/* This class acts as a representation of the chessboard
the Queens will be placed on */
public class Chessboard {

	private int board[][];
	private int numOfQueens, numOfSolutions, queenArrayIndex, rows, columns;
	private ArrayList < Queen[] > queenSolutions = new ArrayList < Queen[] > ();
	private Queen[] queens;

	/**	Create a Chessboard object and specify the number of rows and columns on the board.
	@param r, a positive integer number of rows on the board.
	@param c, a positive integer number of columns on the board. */
	public Chessboard(int r, int c) {
		numOfQueens = 0;
		numOfSolutions = 0;
		queenArrayIndex = 0;
		rows = r;
		columns = c;
		if (r > c) {
			rows = c;
			columns = r;
		}
		board = new int[rows][columns];
		queens = new Queen[r];
	}

	/**Check if Queen can be placed at row r and column c
	@param r, an integer value for a row of board.
	@param c, an integer value for a column of board.
	@return canPlace, a boolean value that is true if 
	Queen can be placed on row r and column c. */
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
	@return isHere, a boolean value that is true if Queen is 
	on row r and column c. */
	public boolean isQueenHere(int r, int c) {
		boolean isHere = false;
		if (numOfQueens != 0) {
			for (int i = 0; i < numOfQueens; i++) {
				if ((queens[i].getRow() == r) && (queens[i].getColumn() == c)) {
					isHere = true;
				}
			}
		}
		return isHere;
	}

	/** Either place or remove a Queen from the board and decrement or 
	increment all values at indexes of board that are attacked by that Queen.
	@param queen, a Queen that is placed or removed from the chessboard. */
	public void placeOrRemoveQueen(int r, int c, int incOrDec) {
		if (incOrDec == -1) {
			Queen queen = new Queen(r, c);
			queens[queenArrayIndex] = queen;
			numOfQueens++;
			queenArrayIndex++;
		} else if (incOrDec == 1) {
			numOfQueens--;
			queenArrayIndex--;
			queens[queenArrayIndex] = null;
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

	/**Remove all Queens from Board and queens array */
	public void removeAllQueens() {
		if (numOfQueens != 0) {
			for (int i = (numOfQueens - 1); i >= 0; i--) {
				placeOrRemoveQueen(queens[i].getRow(), queens[i].getColumn(), 1);
			}
		}
	}

	/** Find the total number of solutions for the non 
	attacking queens problem for a Chessboard object.
	@param row, an integer value of a row of the board.
	@return ans, a boolean that indicates whether there 
	is a solution for this Chessboard object. */
	public boolean solve(int r) {
		boolean ans = false;

		if (r == rows) {
			ans = true;
		} else {
			for (int c = 0;
			(c < columns) && (ans == false); c++) {
				if (board[r][c] == 0) {
					placeOrRemoveQueen(r, c, -1);
					ans = solve((r + 1));
					if (ans) {
						ans = false;
						queenSolutions.add(queens);
						numOfSolutions++;
					}
					placeOrRemoveQueen(r, c, 1);
				}
			}
		}
		return ans;
	}

	/**@return numOfSolutions */
	public int getNumOfSolutions() {
		return numOfSolutions;
	}

	/**@return queenSolutions */
	public List < Queen[] > getQueenArraySolutions() {
		return queenSolutions;
	}

	/**Print the chessboard's board array represented as a String
	@return boardString, a String representation of the chessboard's board array. */
	public String boardToString() {
		String boardString = "\n";
		for (int c = 0; c < columns; c++) {
			for (int r = 0; r < rows; r++) {
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

}