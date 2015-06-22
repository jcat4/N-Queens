public class Cell {
    private int row, col;

    public Cell(int r, int c) {
        row = r;
        col = c;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCell(int r, int c) {
        row = r;
        col = c;
    }

    public void setRow(int r) {
        row = r;
    }

    public void setCol(int c) {
        col = c;
    }

    public String toString() {
        return "(" + row + " " + col + ") ";
    }
}