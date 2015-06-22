/** The class Cell manages a square of a chess board.
 
    @author yanushka
 */
public class Cell {
    private int row, col;

    /** Create a Cell object for row r and column c.
    @param r, a nonnegative row index
    @param c, a nonnegative column index
 */
    public Cell(int r, int c) {
        row = r;
        col = c;
    }

    /** Define getters and setters.
     */

    /** @return the row number of this Cell object
     */
    public int getRow() {
        return row;
    }

    /** @return the column number of this Cell object
     */
    public int getCol() {
        return col;
    }

    /** Reset the row and column numbers of this Cell object to the parameters.
    @int r, a row number
    @int c, a column number
 */
    public void setCell(int r, int c) {
        row = r;
        col = c;
    }

    /** Reset the row number of this Cell object to the parameter.
    @int c, a row number
 */
    public void setRow(int r) {
        row = r;
    }

    /** Reset the column number of this Cell object to the parameter.
    @int c, a column number
 */
    public void setCol(int c) {
        col = c;
    }

    /** @return a String representation of this Cell object
     */
    public String toString() {
        return "(" + row + " " + col + ") ";
    }
}