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
            //black starts on top... (always?)
            int counter = 0;
            //counter counts num of times we add something to testThese
            //move forward 1
            ChessPosition myPawnPos0 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
            //can't move 1 if enemy is there
            if (myPosition.getRow() != 2 && board.isValid(myPawnPos0)) {
                testThese[counter] = myPawnPos0;
                counter++;
            }
            // if pawn's first move then his row is always on 2, bidirectional statement of fact
            if (myPosition.getRow() == 7) {
                ChessPosition myPawnPos1 = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn());
                //can't move there if enemy tho
                ChessPosition blockPos = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
                if (board.isValid(myPawnPos1) && board.isValid(blockPos)) {
                    testThese[counter] = myPawnPos1;
                    counter++;
                }
            }
            //now needs to check the two diagonal
            ChessPosition myPawnPos2 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
            ChessPosition myPawnPos3 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
            if (board.getPiece(myPawnPos2) != null && board.getPiece(myPawnPos2).color == ChessGame.TeamColor.WHITE) {
                myPawnPos2.hasEnemy = true;
                if (myPawnPos2.getRow() == 1) {
                    ChessPosition newPos0 = new ChessPosition(myPawnPos2.getRow(), myPawnPos2.getColumn(), ChessPiece.PieceType.QUEEN);
                    ChessPosition newPos1 = new ChessPosition(myPawnPos2.getRow(), myPawnPos2.getColumn(), ChessPiece.PieceType.BISHOP);
                    ChessPosition newPos2 = new ChessPosition(myPawnPos2.getRow(), myPawnPos2.getColumn(), ChessPiece.PieceType.KNIGHT);
                    ChessPosition newPos3 = new ChessPosition(myPawnPos2.getRow(), myPawnPos2.getColumn(), ChessPiece.PieceType.ROOK);
                    testThese[counter] = newPos0;
                    testThese[counter+1] = newPos1;
                    testThese[counter+2] = newPos2;
                    testThese[counter+3] = newPos3;
                    counter += 4;
                }
                else {
                    testThese[counter] = myPawnPos2;
                    counter++;
                }
            }
            if (board.getPiece(myPawnPos3) != null && board.getPiece(myPawnPos3).color == ChessGame.TeamColor.WHITE) {
                myPawnPos3.hasEnemy = true;
                if (myPawnPos3.getRow() == 1) {
                    ChessPosition newPos0 = new ChessPosition(myPawnPos3.getRow(), myPawnPos3.getColumn(), ChessPiece.PieceType.QUEEN);
                    ChessPosition newPos1 = new ChessPosition(myPawnPos3.getRow(), myPawnPos3.getColumn(), ChessPiece.PieceType.BISHOP);
                    ChessPosition newPos2 = new ChessPosition(myPawnPos3.getRow(), myPawnPos3.getColumn(), ChessPiece.PieceType.KNIGHT);
                    ChessPosition newPos3 = new ChessPosition(myPawnPos3.getRow(), myPawnPos3.getColumn(), ChessPiece.PieceType.ROOK);
                    testThese[counter] = newPos0;
                    testThese[counter+1] = newPos1;
                    testThese[counter+2] = newPos2;
                    testThese[counter+3] = newPos3;
                    counter += 4;
                }
                else {
                    testThese[counter] = myPawnPos3;
                    counter++;
                }
            }
            if (myPosition.getRow() == 2) {
                //create the 4 posible pieces then carry it thru  the poisition to myPawnAdv0
                ChessPosition myPawnAdv0 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn(), ChessPiece.PieceType.QUEEN );
                ChessPosition myPawnAdv1 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn(), ChessPiece.PieceType.ROOK );
                ChessPosition myPawnAdv2 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn(), ChessPiece.PieceType.BISHOP);
                ChessPosition myPawnAdv3 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn(), ChessPiece.PieceType.KNIGHT );
                testThese[counter] = myPawnAdv0;
                testThese[counter+1] = myPawnAdv1;
                testThese[counter+2] = myPawnAdv2;
                testThese[counter+3] = myPawnAdv3;
                counter += 4;
            }
            ///todo pawn enpassant implementation i gave up on lol
            if (myPosition.getRow() == 4) {
                //test for 2 pos of pawns next to me
                ChessPosition test1 = new ChessPosition(4, myPosition.getColumn()+1);
                ChessPosition test2 = new ChessPosition(4, myPosition.getColumn()-1);
                ChessPosition actual1 = new ChessPosition(3, myPosition.getColumn() + 1);
                ChessPosition actual2 = new ChessPosition(3, myPosition.getColumn() - 1);
                if (board.getPiece(test1) != null && board.getPiece(test1).getPieceType() == ChessPiece.PieceType.PAWN && board.getPiece(actual1) == null && board.getPiece(test1).movedTwo) {
                    //valid enpassant
                    board.isValid(test1);
                    board.isValid(actual1);
                    testThese[counter] = actual1;
                    counter++;
                    board.getPiece(myPosition).justDidEnPassant = true;
                }
                if (board.getPiece(test2) != null && board.getPiece(test2).getPieceType() == ChessPiece.PieceType.PAWN && board.getPiece(actual2) == null && board.getPiece(test2).movedTwo) {
                    //valid   enpassant
                    board.isValid(test2);
                    board.isValid(actual2);
                    testThese[counter] = actual2;
                    board.getPiece(myPosition).justDidEnPassant = true;
                }
            }

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
        if (this.type == PieceType.PAWN && this.color == ChessGame.TeamColor.WHITE) {
            return "P";
        }
        else if (this.type == PieceType.PAWN && this.color == ChessGame.TeamColor.BLACK) {
            return "p";
        }
        else if  (this.type == PieceType.BISHOP && this.color == ChessGame.TeamColor.WHITE) {
            return "B";
        }
        else if (this.type == PieceType.BISHOP && this.color == ChessGame.TeamColor.BLACK) {
            return "b";
        }
        else if  (this.type == PieceType.KNIGHT && this.color == ChessGame.TeamColor.WHITE) {
            return "N";
        }
        else if (this.type == PieceType.KNIGHT && this.color == ChessGame.TeamColor.BLACK) {
            return "n";
        }
        else if  (this.type == PieceType.QUEEN && this.color == ChessGame.TeamColor.WHITE) {
            return "Q";
        }
        else if (this.type == PieceType.QUEEN && this.color == ChessGame.TeamColor.BLACK) {
            return "q";
        }
        else if  (this.type == PieceType.KING && this.color == ChessGame.TeamColor.WHITE) {
            return "K";
        }
        else if (this.type == PieceType.KING && this.color == ChessGame.TeamColor.BLACK) {
            return "k";
        }
        else if  (this.type == PieceType.ROOK && this.color == ChessGame.TeamColor.WHITE) {
            return "R";
        }
        else if (this.type == PieceType.ROOK && this.color == ChessGame.TeamColor.BLACK) {
            return "r";
        }
        else
            return "?";
    }
}
