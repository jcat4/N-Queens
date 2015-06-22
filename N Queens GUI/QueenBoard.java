import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

/**	The class QueenBoard manages the placement of non attacking queens
	a rectangular chess board with dimension rows by cols.
	The class is not complete.

	@author yanushka
 */
public class QueenBoard
{
    private static final String WHITE_SPACE = "\\s+",
				RANDOM = "random";

    public final static int 	ROWS = 8,
				COLS = 8,
				BIG_ROWS = 12,
                                BIG_COLS = 13;

    private int 	rows, cols;
    private int []	queens;
    private int [][]	attacked;

/**	Create a QueenBoard object based on default values.
 */
    public QueenBoard()
    {
	this( ROWS, COLS );
    }

/**	Create a QueenBoard object based on the contents of a string parameter.
	@param s, a string with values for rows and cols or with the word RANDOM

	In the first case parse the string. 
	In the second case create random values. 
 */
    public QueenBoard( String s )
    {
	if ( s.equals( RANDOM ) ) {
	    Random  rand = new Random();
            rows = rand.nextInt( BIG_ROWS ) + 4;
            cols = rand.nextInt( BIG_COLS ) + 4;
	    if ( rows > cols ) {
		int	tmp = rows;
		rows = cols;
		cols = tmp;
	    }
	    initialize( rows, cols );
	} else {
	    String []       tokens = s.split( WHITE_SPACE );
            rows = new Integer( tokens[ 0 ] );
            cols = new Integer( tokens[ 1 ] );
            int	minRC = Math.min( rows, cols );
            queens = new int[ minRC ];
            attacked = new int[ rows ][ cols ];
            if ( tokens.length > 2 ) 
                fillQueens( tokens );
	}
    }

    private void fillQueens( String [] tokens )
    {
        for ( int i = 2; i < tokens.length; i += 2 ) {
            int r = new Integer( tokens[ i ] ),
                c = new Integer( tokens[ i + 1 ] );
	    if ( queens[ r ] == -1 )
                queens[ r ] = c;
	    mark( r, c, true );
        }
    }

/**	Create a QueenBoard object based on the values of the parameters.
	@param n, a positive integer number of rows
	@param m, a positive integer number of columns 
 */
    public QueenBoard( int n, int m )
    {
	initialize( n, m );
    }

/**	Initialize a QueenBoard object with the values of the parameters.
	@param n, a positive integer number of rows
	@param m, a positive integer number of columns 
 */
    private void initialize( int r, int c )
    {
	rows = r;
	cols = c;
	queens = new int[ rows ];
	for ( int i = 0; i < rows; i++ )
	    queens[ i ] = -1;
	attacked = new int[ rows ][ cols ];
    }

/**	@return a String representation of this QueenBoard object
 */
    public String toString()
    {
	return Arrays.toString( queens );
    }

/**	@return the number of rows of the board
 */
    public int getRows()
    {
	return rows;
    }

/**	@return the number of columns of the board
 */
    public int getCols()
    {
	return cols;
    }

/**	Toggle the cell at row r and column c.
	@param r a non negative integer for the number of the row
	@param c a non negative integer for the number of the column
	@precondition 0 <= r < rows and 0 <= c < cols
 */
    public void toggleCell( int r, int c )
    {
		if ( queens[ r ] == -1 ) {
			queens[ r ] = c;
			mark( r, c, true );
		} else {
			if ( queens[ r ] == c ) {
				queens[ r ] = -1;
				mark( r, c, false );
			} else 
				mark( r, c, true );
		}
    }

/**	Update the attacked table based on the values of the parameters.
	@param row a non negative integer 
	@param col a non negative integer 
	@param tf a boolean

	if tf
	    Increment each cell a queen at ( row, col ) attacks
	else
	    Decrement each cell a queen at ( row, col ) attacks
 */
    private void mark( int row, int col, boolean tf )
    {
	for ( int r = 0; r < rows; r++ )
	    if ( tf )
	        attacked[ r ][ col ]++;
	    else
	        attacked[ r ][ col ]--;
	for ( int c = 0; c < cols; c++ )
	    if ( c != col )
	        if ( tf )
	            attacked[ row ][ c ]++;
	        else
	            attacked[ row ][ c ]--;
	int	mrc = Math.max( rows, cols );
	for ( int i = 1; i < mrc; i++ ) {
	    if ( onBoard( row + i, col + i ) )
	        if ( tf )
	            attacked[ row + i ][ col + i ]++;
	        else
	            attacked[ row + i ][ col + i ]--;
	    if ( onBoard( row + i, col - i ) )
	        if ( tf )
	            attacked[ row + i ][ col - i ]++;
	        else
	            attacked[ row + i ][ col - i ]--;
	    if ( onBoard( row - i, col + i ) )
	        if ( tf )
	            attacked[ row - i ][ col + i ]++;
	        else
	            attacked[ row - i ][ col + i ]--;
	    if ( onBoard( row - i, col - i ) )
	        if ( tf )
	            attacked[ row - i ][ col - i ]++;
	        else
	            attacked[ row - i ][ col - i ]--;
	}
    } 

/**	Decide whether the parameters represent a cell on the board.
	@param r a non negative integer for the number of the row
	@param c a non negative integer for the number of the column
 	@return true if ( r, c ) is on the board and false otherwise
 */
    private boolean onBoard( int r, int c )
    {
	return ( 0 <= r ) && ( r < rows ) && ( 0 <= c ) && ( c < cols );
    }

/**	@return the attacked attribute
 */
    public int [][] getAttacked()
    {
	return attacked;
    }
}
