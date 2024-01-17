package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private final int row;
    private final int col;
    boolean hasEnemy = false;

    // I made this bool to make life easier
    boolean isValidPos;
    public ChessPosition(int row, int col) {
        if (row <= 0)
        {
            //all this 6 and excess stuff might not be necessary
            this.row = 6;
            this.col = 6;
            isValidPos = false;
            return;
        }
        else if (col <= 0)
        {
            this.col = 6;
            this.row = 6;
            isValidPos = false;
            return;
        }
        else if (col > 8)
        {
            this.col = 6;
            this.row = 6;
            isValidPos = false;
            return;
        }
        else if (row > 8)
        {
            this.row = 6;
            this.col = 6;
            isValidPos = false;
            return;
        }
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
        if (row < 0 || row > 9)
        {
            //isValidPos = false;
            return 0;
        }
        return row;
        //returns the +1 because chess has 1-8 grid but our array starts at 0-7
    }
    public boolean hasEnemy()
    {
        return hasEnemy;
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
        if (col < 0 || col > 9)
        {
            //isValidPos = false;
            return 0;
        }
        return col;
        //returns the +1 because chess has 1-8 grid but our array starts at 0-7
    }
    public boolean isValid()
    {
        return isValidPos;
    }
    public String toString()
    {
        String row = Integer.toString(this.row);
        String col = Integer.toString(this.col);
        return row + " " + col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, isValidPos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition that = (ChessPosition) o;
        return row == that.row && col == that.col && isValidPos == that.isValidPos;
    }
}
