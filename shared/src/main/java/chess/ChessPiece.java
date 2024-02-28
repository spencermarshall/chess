package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;


/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece extends MoveHelp {

    private PieceType type;
    public ChessGame.TeamColor color;
    protected boolean movedTwo;
    protected boolean justDidEnPassant;
   // private TeamColor thisTeam;
    private boolean hasMoved;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.type = type;
        this.color = pieceColor;
        this.movedTwo = false;
        this.justDidEnPassant = false;
        this.hasMoved = false;


    }
    public void hasMoved(boolean set)
    {
        this.hasMoved = set;
    }

    public void movedTwo(boolean set)
    {
        movedTwo = set;
    }
    public boolean getMovedTwo()
    {
        return movedTwo;
    }
    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return type == that.type && color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color);
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> set = new HashSet<>();
        ChessPosition[] testThese = new ChessPosition[64];


        if(type == PieceType.KING) {
            kingMoves(board, myPosition, testThese, this);
        }
        else if(type == PieceType.QUEEN) {
            queenMoves(board, myPosition, testThese, this);
        }
        else if(type == PieceType.ROOK) {
            rookMoves(board, myPosition, testThese, this);
        }
        else if(type == PieceType.KNIGHT) {
            knightMoves(board, myPosition, testThese, this);
        }
        else if(type == PieceType.BISHOP) {
            bishopMoves(board, myPosition, testThese, this);
        }







        else if(type == PieceType.PAWN && this.color == ChessGame.TeamColor.WHITE)
        {
            whitePawnMoves(board, myPosition, testThese, this);

        }
        else if(type == PieceType.PAWN && this.color == ChessGame.TeamColor.BLACK)
        {
           blackPawnMoves(board, myPosition, testThese, this);
        }
        else {
            int error = 3;
        }
        //this adds moves for castling
        ChessPosition[] addMore = addCastling();
        for (int castle = 0; castle < addMore.length; ++castle)
        {
         ///todo   testThese[testThese.length + castle] = addMore[castle];
        }

        //now we know what piece it was and added all possible pos to testThes e
        //we add the array of testThese pos to the set
        for (int i = 0; i < testThese.length; ++i)
        {


            if (testThese[i] != null && testThese[i].isValidPos) {

                boolean isEnemy = testThese[i].hasEnemy();
                //checks if enemy then auto add
                // if ((board.getPiece(testThese[i]).type != null) &&  board.getPiece(testThese[i]).color != this.color)

                if (testThese[i].getPiece() == PieceType.ROOK ||testThese[i].getPiece() == PieceType.QUEEN || testThese[i].getPiece() == PieceType.BISHOP || testThese[i].getPiece() == PieceType.KNIGHT)
                {
                    set.add(new ChessMove(myPosition,testThese[i], testThese[i].getPiece()));
                }
                else if ((isEnemy) || board.isValid(testThese[i]) && testThese[i].isValid()) {
                    set.add(new ChessMove(myPosition, testThese[i], null));
                }
            }
        }

        return set;
    }

    public ChessPosition[] addCastling()
    {
        ChessPosition[] castleMoves = new ChessPosition[10];

        return castleMoves;

    }

    public String toString()
    {
        if (this.type == PieceType.PAWN && this.color == ChessGame.TeamColor.WHITE)
        {
            return "P";
        }
        else if (this.type == PieceType.PAWN && this.color == ChessGame.TeamColor.BLACK)
        {
            return "p";
        }
        else if  (this.type == PieceType.BISHOP && this.color == ChessGame.TeamColor.WHITE)
        {
            return "B";
        }
        else if (this.type == PieceType.BISHOP && this.color == ChessGame.TeamColor.BLACK)
        {
            return "b";
        }
        else if  (this.type == PieceType.KNIGHT && this.color == ChessGame.TeamColor.WHITE)
        {
            return "N";
        }
        else if (this.type == PieceType.KNIGHT && this.color == ChessGame.TeamColor.BLACK)
        {
            return "n";
        }
        else if  (this.type == PieceType.QUEEN && this.color == ChessGame.TeamColor.WHITE)
        {
            return "Q";
        }
        else if (this.type == PieceType.QUEEN && this.color == ChessGame.TeamColor.BLACK)
        {
            return "q";
        }
        else if  (this.type == PieceType.KING && this.color == ChessGame.TeamColor.WHITE)
        {
            return "K";
        }
        else if (this.type == PieceType.KING && this.color == ChessGame.TeamColor.BLACK)
        {
            return "k";
        }
        else if  (this.type == PieceType.ROOK && this.color == ChessGame.TeamColor.WHITE)
        {
            return "R";
        }
        else if (this.type == PieceType.ROOK && this.color == ChessGame.TeamColor.BLACK)
        {
            return "r";
        }
        else
            return "?";
    }
}
