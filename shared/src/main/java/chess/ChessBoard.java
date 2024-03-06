package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[9][9];
    public ChessBoard() {
        resetBoard();
    }

    public String toString()
    {
        String output = "";
        for (int r = 1; r < 9; ++r)
        {
            for (int c = 1; c < 9; ++c)
            {
                if (this.squares[r][c] != null)
                    output += this.squares[r][c].toString();
            }
            output += "\n";
        }
        return output;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(squares);
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
        for (int row = 1; row < 9; ++row) {
            for (int col = 1; col < 9; ++col) {
                //if statement below adds row of white pawns at bottom
                if (row == 2) {
                    ChessPosition pawnPos = new ChessPosition(2, col);
                    ChessPiece mine = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
                    this.addPiece(pawnPos, mine);
                }
                //if statement below adds row of black pawns at top
                if (row == 7) {
                    ChessPosition pawnPos = new ChessPosition(7, col);
                    ChessPiece mine = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
                    this.addPiece(pawnPos, mine);
                }
            }
        }
        //these 8 add first row of white
        this.squares[1][1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        this.squares[1][2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        this.squares[1][3] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        this.squares[1][4] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        this.squares[1][5] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        this.squares[1][6] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        this.squares[1][7] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        this.squares[1][8] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        //these 8 add last row for black
        this.squares[8][1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        this.squares[8][2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        this.squares[8][3] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        this.squares[8][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        this.squares[8][5] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        this.squares[8][6] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        this.squares[8][7] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        this.squares[8][8] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
    }
    //i made this, returns true is the position is open
    public boolean isValid(ChessPosition pos) {
        if (pos == null) {
            return false;
        }
        if (pos.getRow() < 0 || pos.getRow() > 8) {
            return false;
        }
        if (pos.getColumn() < 0 || pos.getColumn() > 8) {
            return false;
        }
        //if position is empty, it returns null
        return squares[pos.getRow()][pos.getColumn()] == null;
    }
}