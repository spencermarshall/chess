package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[9][9];


    public ChessBoard() {
        
    }



    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        //throw new RuntimeException("Not implemented");

        squares[position.getRow()][position.getColumn()] = piece;


    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()][position.getColumn()];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        throw new RuntimeException("Not implemented");
    }


    //i made this, returns true is the position is open
    public boolean isValid(ChessPosition pos)
    {
        if (pos == null)
        {
            return false;
        }
        if (pos.getRow() < 0 || pos.getRow() >= 8)
        {
            return false;
        }
        if (pos.getColumn() < 0 || pos.getColumn() >= 8)
        {
            return false;
        }
        //if position is empty, it returns null
        if (squares[pos.getRow()][pos.getColumn()] == null)
        {
            return true;
        }
        else
            return false;
    }
}
