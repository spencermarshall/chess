package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private PieceType type;
    private ChessGame.TeamColor color;
   // private TeamColor thisTeam;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.type = type;
        this.color = pieceColor;


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
        ChessPosition[] testThese = new ChessPosition[0];


        if(type == PieceType.KING)
        {
            //look at position and board
            //everywhere he can move add it set
            //return set


            ChessPosition[] testTheseKing = new ChessPosition[0];

            //first top left, then top middle, then top right

            testThese[0] = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
            testThese[1] = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
            testThese[2] = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);

            //then immediate left, then immediate right
            testThese[3] = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1);
            testThese[4] = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1);

            //lastly, bottom left, bottom straight, bottom right
            testThese[5] = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
            testThese[6] = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
            testThese[7] = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
            









            /*if (board.getPiece(check) == null)
                {
                    ChessMove potMove = new ChessMove(myPosition, newPos, null);
                    set.add(potMove);

                }


                // then 2 next to

                // then 3 below
                ChessPosition endPos = new ChessPosition(1,2);
               // ChessMove possibleMove = new ChessMove(myPosition,/* end position *///,null);
                //set.add(possibleMove);

        }
        else if(type == PieceType.QUEEN)
        {
            //look at position and board
            //everywhere she can move add it set
            //return set

        }
        else if(type == PieceType.ROOK)
        {
            //look at position and board
            //everywhere he can move add it set
            //return set

        }
        else if(type == PieceType.KNIGHT)
        {
            //look at position and board
            //everywhere he can move add it set
            //return set

        }
        else if(type == PieceType.BISHOP)
        {
            //make 4 array's of 7 positions each along the diagonal
            //because it could be anywhere
            //test if the pos is valid
            //if it is add it to set

            //I make 4 for loops to test each possible direction
            for (int i = 0; i < 7; ++i)
            {

            }
            for (int i = 0; i < 7; ++i)
            {

            }
            for (int i = 0; i < 7; ++i)
            {

            }
            for (int i = 0; i < 7; ++i)
            {

            }






        }
        else if(type == PieceType.PAWN)
        {
            //look at position and board
            //everywhere he can move add it set
            //return set

        }
        else {
            int error = 3;
        }

        //now we know what piece it was and added all possible pos to testThese
        //we add the array of testThese pos to the set
        for (int i = 0; i < testThese.length; ++i)
        {
            if (board.isValid(testThese[i]) && testThese[i].isValid())
            {
                set.add(new ChessMove(myPosition,testThese[i],null));
            }
        }

        return set;
    }
}
