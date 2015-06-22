import java.util.ArrayList;
import javax.swing.JToggleButton;

/**	The class Model manages communication between the view in the
	class View the business layer in the class QueenBoard. 
	It responds to messages from a controller. It sends messages to the 
	View class and QueenBoard class. */
public class Model {
	public static final int ROWS_DEFAULT = 8,
	COLS_DEFAULT = 8,
	BIG_ROWS = 13;
	public static final String SPACE = " ",
	UNDERSCORE = "_",
	WHITE_SPACE = "\\s+";

	private View frame;
	private Chessboard myBoard;

	/**	Construct a Model object linked to a View object.
	@param gameFrame, an existing JFrame to serve as GUI for non attacking 
	queens */
	public Model(View gameFrame) {
		frame = gameFrame;
		myBoard = new Chessboard(8, 8);
	}

	/**	Respond to a command initiated by a client's pressing of a button. */
	public void respond(String command) {
		boolean found = false;
		for (int i = 0;
		(i < View.MENU_NAMES.length) && !found;
		i++) {
			for (int j = 0;
			(j < View.MENU_NAMES[i].length) && !found;
			j++) {
				found = Character.isDigit(command.charAt(0)) || command.equals(View.MENU_NAMES[i][j]);
				if (found) {
					parseCommand(command, i, j);
				}
			}
		}
	}

	/**	Parse the command in the string parameter.
	@param command, a string containing an action command from the
	menu or a button */
	private void parseCommand(String command, int i, int j) {
		if (Character.isDigit(command.charAt(0))) {
			changeIcon(command);
		} else if (command.equals("Set nm")) {
			set_nm();
		} else if (command.equals("Random")) {
			random();
		} else if (command.equals("Quit")) {
			quit();
		} else if (command.equals("Check")) {
			check();
		} else if (command.equals("Solve")) {
			solve();
		} else if (command.equals("Clear")) {
			clear();
		} else if (command.equals("Rules")) {
			showRules();
		} else if (command.equals("About")) {
			showAbout();
		}
	}

	/**	Quit the program. */
	private void quit() {
		System.exit(0);
	}

	/**	Display some information about the non attacking queens problem. */
	private void showAbout() {
		frame.showInfo();
	}

	/**	Display some rules for the non attacking queens problem. */
	private void showRules() {
		frame.showRules();
	}

	/**	Change the icon for the action command of a button.
	@param command, a string action command of the form "row space column" */
	private void changeIcon(String command) {
		String[] tokens = command.split(WHITE_SPACE);
		int x = new Integer(tokens[0]).intValue(),
		y = new Integer(tokens[1]).intValue();

		frame.toggleCell(x, y);
		placeOrRemoveAtCell(x, y);
		int[][] attacked = myBoard.getBoard();
		frame.colorCells(attacked);
	}

	/**	Set random values for the number of rows and the number of columns.
	(Certain dimensions occasionally generates 'java.awt.IllegalComponentStateException' 
	errors... This seems to be an issue with Java, not with the code. My logic still works.) */
	private void random() {
		int randomRow = (int)(15 * Math.random()),
		randomColumn = (int)(15 * Math.random());
		while ((randomRow > randomColumn) || (randomRow <= 1) || (randomColumn <= 1)) {
			randomRow = (int)(15 * Math.random());
			randomColumn = (int)(15 * Math.random());
		}
		myBoard = new Chessboard(randomRow, randomColumn);
		frame.setSize(randomRow, randomColumn);
	}

	/**	Set values for the number of rows and columns based on the client's 
	choice. */
	private void set_nm() {
		String s = frame.getParameters();
		String[] tokens = s.split(WHITE_SPACE);
		int r = new Integer(tokens[0]).intValue(),
		c = new Integer(tokens[1]).intValue();

		if ((r <= 0) || (c <= 0)) {
			frame.showInvalidDim();
		} else if (s != null) {
			myBoard = new Chessboard(r, c);
			frame.setSize(r, c);
		}
	}

	/**	Tell the frame to clear the board. */
	private void clear() {
		myBoard.removeAllQueens();
		frame.clear();
	}

	/**	Determine whether the queens on the board are non attacking and 
 	their number is maximal. */
	private void check() {
		String checkString = "";
		checkString += ("Are Queens Attacking: " + String.valueOf(myBoard.areQueensAttacked()));
		checkString += ("\nNumber of Queens: ");
		if (myBoard.tooFewManyOrMax() == 0) {
			checkString += ("maximum");
		} else if (myBoard.tooFewManyOrMax() == 1) {
			checkString += ("too many");
		} else if (myBoard.tooFewManyOrMax() == -1) {
			checkString += ("too few");
		}
		checkString += ("\nIs solution: " + String.valueOf(myBoard.isSolution()) + "\n");
		frame.showCheck(checkString);
	}

	/**	If possible, solve the non attacking queens problem starting with the 
	current positions of the queens on the board. */
	private void solve() {
		int r, c;
		String solveString = "";
		if (myBoard.isSolution()) {
			solveString += ("\nsolution already on board!\n");
		} else if (myBoard.solve(0)) {
			solveString += ("\nsolution found!\n");
			ArrayList < Queen > queens = myBoard.getQueens();
			int[][] attacked = myBoard.getBoard();
			frame.colorCells(attacked);
			for (int i = 0; i < myBoard.getNumOfQueens(); i++) {
				if (!queens.get(i).isFixed()) {
					r = queens.get(i).getRow();
					c = queens.get(i).getColumn();
					frame.toggleCell(r, c);
				}
			}
			myBoard.setAllQueensToFixed();
		} else {
			solveString += ("\nno solution found...\n");
		}
		frame.showSolve(solveString);
	}

	/**	Find the buttons of cells with a queen icon.
	@return an array list of Cell objects corresponding to the
	positions of the icons */
	private String getPositions(JToggleButton[][] cells) {
		StringBuffer buf = new StringBuffer();
		buf.append(cells.length + " ");
		buf.append(cells[0].length + " ");

		for (int r = 0; r < cells.length; r++) {
			for (int c = 0; c < cells[0].length; c++) {
				if (cells[r][c].getIcon() != null) {
					buf.append(r + " " + c + " ");
				}
			}
		}

		return buf.toString().trim();
	}

	/** Place or remove a Queen in the business layer by Looking at the JToggleButton cells
    (Had to do here since I can't have View talk directly to the Business layer, else 
    I would have just called "placeOrRemove" for the "toggleCell" method in View) */
	private void placeOrRemoveAtCell(int r, int c) {
		JToggleButton[][] cells = frame.getCells();
		System.out.println("Place at row: " + r + ", column: " + c);
		if (cells[r][c].getIcon() == null) {
			myBoard.placeOrRemoveQueen(r, c, 1);
			myBoard.removeFixedQueen(r, c);
		} else {
			myBoard.placeOrRemoveQueen(r, c, -1);
			myBoard.addFixedQueen(myBoard.getQueenAt(r, c));
		}
		System.out.println(myBoard.boardToString() + "\n");
	}


}