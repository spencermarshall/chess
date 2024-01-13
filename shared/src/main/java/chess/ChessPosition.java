package chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private final int row;
    private final int col;

    // I made this bool to make life easier
    boolean isValidPos;
    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
        this.isValidPos = true;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */

    //I edited these files, the problem was it would throw error if negative index,
    //so i made a new bool isValid and if negative index made it not valid
    //then returned 0 so it wouldnt give an error
    //but in ChessMove() if it's not valid it won't add it
    public int getRow() {
        if (row < 0)
        {
            isValidPos = false;
            return 0;
        }
        return row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */


    //I edited these files, the problem was it would throw error if negative index,
    //so i made a new bool isValid and if negative index made it not valid
    //then returned 0 so it wouldnt give an error
    //but in ChessMove() if it's not valid it won't add it
    public int getColumn() {
        if (col < 0)
        {
            isValidPos = false;
            return 0;
        }
        return col;
    }
    public boolean isValid()
    {
        return isValidPos;
    }
}
