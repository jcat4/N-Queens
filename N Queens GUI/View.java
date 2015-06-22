import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

/**	The class View manages a graphical user interface (GUI) for 
non attacking queens on a rectangular chess board.
It has a menu of commands and a 2 dimensional array of JButton
objects that represent the squares of a chess board. 

Each button can display an image of a queen. It acts as a toggle 
switch between not showing or and showing an image. The default is 
no image. */
public class View extends JFrame {
	private static final int QUEEN_PIXELS = 50;
	private static final Color MARKED_RED = new Color(155, 56, 84);
	private static final Dimension BUTTON_DIM = new Dimension(QUEEN_PIXELS, QUEEN_PIXELS);
	private static final String TITLE = "Non Attacking Queens",
	SIZE_REQUEST =
		"Enter values for the number of rows and columns separated by a space",
	RULES =
		"                                   Rules\n\n" +
		"Place queens on chess board with n rows and m columns so that\n\n" +
		"no queen attacks another queen.\n\n",
	SOLVED = "Your solution is correct\n\n" +
		"No queen attacks another queen.",
	INFO = "CS 234 Project\n\n" +
		"Source: another Data Structures textbook",
	ROW_COL_INFO =
		"    Rows " + Model.ROWS_DEFAULT + " Columns " + Model.COLS_DEFAULT,
	BAD_DIMS = "Entered dimensions are invalid! \n(row or column <= 0)";

	public static final String[] BAR_NAMES = {
		"Start", "Play", "Help"
	};
	public static final String[][] MENU_NAMES = {
		{
			"Set nm", "Random", "Quit"
		}, {
			"Check", "Solve", "Clear"
		}, {
			"Rules", "About"
		}
	};

	private JToggleButton[][] cells;
	private JPanel ctrP;

	/**	Construct a View object. */
	public View() {
		super(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Model model = new Model(this);
		Controller ctrl = new Controller(model);
		Container can = getContentPane();
		can.setLayout(new BorderLayout());
		ctrP = makeCells(Model.ROWS_DEFAULT, Model.COLS_DEFAULT, ctrl);
		can.add(ctrP, "Center");
		setJMenuBar(makeMenu(ctrl));
		pack();
		setVisible(true);
	}

	/**	Construct a JMenuBar object that responds to a Controller object.
	@param ctrl, a Controller object to respond to events on the menu */
	private JMenuBar makeMenu(Controller ctrl) {
		JMenuBar bar = new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;

		for (int i = 0; i < MENU_NAMES.length; i++) {
			menu = new JMenu(BAR_NAMES[i]);
			for (int j = 0; j < MENU_NAMES[i].length; j++) {
				menuItem = new JMenuItem(MENU_NAMES[i][j]);
				menuItem.addActionListener(ctrl);
				menu.add(menuItem);
			}
			bar.add(menu);
		}
		menuItem = new JMenuItem(ROW_COL_INFO);
		bar.add(menuItem);

		return bar;
	}

	/*	Create a central panel consisting of buttons for each cell of the board.
	@param rows, a positive integer number of rows of the board
	@param cols, a positive integer number of columns of the board
	@param ctrl, a Controller object to respond to events on the panel */
	private JPanel makeCells(int rows, int cols, Controller ctrl) {
		JPanel buttonP = new JPanel(new GridLayout(rows, rows));
		JToggleButton button;

		cells = new JToggleButton[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				button = new JToggleButton();
				button.setActionCommand("" + i + " " + j);
				button.addActionListener(ctrl);
				button.setPreferredSize(BUTTON_DIM);
				cells[i][j] = button;
				buttonP.add(button);
			}
		}

		return buttonP;
	}

	/**	Show the rules of the non attacking queens problem. */
	public void showRules() {
		JOptionPane.showMessageDialog(this, RULES);
	}

	/**	Show some information about the non attacking queens problem. */
	public void showInfo() {
		JOptionPane.showMessageDialog(this, INFO);
	}

	/**	Show some information about the non attacking queens problem. */
	public void showSolve(String solveString) {
		JOptionPane.showMessageDialog(this, solveString);
	}

	/**	Show some information about the non attacking queens problem. */
	public void showCheck(String checkString) {
		JOptionPane.showMessageDialog(this, checkString);
	}

	/** Show that entered dimensions are invalid */
	public void showInvalidDim() {
		JOptionPane.showMessageDialog(this, BAD_DIMS);
	}

	/**	Set the cell in row r and column c.
	(Flip between a null icon and a queen icon)
	@param r, a positive integer row number of the cell 
	@param c, a positive integer column number of the cell */
	public void toggleCell(int r, int c) {
		if (cells[r][c].getIcon() == null) {
			cells[r][c].setIcon(new ImageIcon("q.gif"));
		} else {
			cells[r][c].setIcon(null);
			cells[r][c].setSelected(false);
		}

		if (cells[r][c].getBackground().equals(MARKED_RED)) {
			cells[r][c].setBackground(getBackground());
		}
	}

	/**	Set the size of the board to rows by columns.
	@param n, a positive integer number of rows of the board
	@param m, a positive integer number of columns of the board */
	public void setSize(int n, int m) {
		Dimension dim = new Dimension(QUEEN_PIXELS * m, QUEEN_PIXELS * n);
		ActionListener[] ala = cells[0][0].getActionListeners();
		Controller ctrl = (Controller) ala[0];

		ctrP = makeCells(n, m, ctrl);

		JMenuBar bar = getJMenuBar();
		JMenuItem valMI = (JMenuItem) bar.getComponent(bar.getMenuCount() - 1);
		valMI.setText("    Rows " + n + " Columns " + m);

		setPreferredSize(dim);
		ctrP.setOpaque(true);
		Container can = getContentPane();
		can.removeAll();
		can.add(ctrP, "Center");
		pack();
		validate();
		setVisible(true);
	}

	/**	Clear each button to have no icon and a neutral background. */
	public void clear() {
		for (int r = 0; r < cells.length; r++) {
			for (int c = 0; c < cells[0].length; c++) {
				cells[r][c].setIcon(null);
				cells[r][c].setBackground(getBackground());
				cells[r][c].setSelected(false);
			}
		}
	}

	/**	Clear each button to have a neutral background. */
	public void clearColor() {
		for (int r = 0; r < cells.length; r++) {
			for (int c = 0; c < cells[0].length; c++) {
				cells[r][c].setBackground(getBackground());
			}
		}
	}

	/**	@return the 2 dimensional cells array of JToggleButton objects */
	public JToggleButton[][] getCells() {
		return cells;
	}

	/**	Ask the client to enter the number of rows and the number of columns
	@return a string with her entries or null if she presses cancel */
	public String getParameters() {
		String s = JOptionPane.showInputDialog(this, SIZE_REQUEST);
		return s;
	}

	/**	Color each cell without a queen that has a positive count.
 	@param counts a 2D array of integers */
	public void colorCells(int[][] counts) {
		for (int r = 0; r < cells.length; r++) {
			for (int c = 0; c < cells[0].length; c++) {
				if (cells[r][c].getIcon() == null) {
					if (counts[r][c] < 0) {
						cells[r][c].setBackground(MARKED_RED);
					} else if (counts[r][c] == 0) {
						cells[r][c].setBackground(getBackground());
					}
				}
			}
		}
	}
}