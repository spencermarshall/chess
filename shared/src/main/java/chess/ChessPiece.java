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


        if(type == PieceType.KING)
        {
            //look at position and board
            //everywhere he can move add it set
            //return set


            ChessPosition[] testTheseKing = new ChessPosition[9];

            //first top left, then top middle, then top right

            testTheseKing[0] = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
            testTheseKing[1] = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
            testTheseKing[2] = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);

            //then immediate left, then immediate right
            testTheseKing[3] = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1);
            testTheseKing[4] = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1);

            //lastly, bottom left, bottom straight, bottom right
            testTheseKing[5] = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1);
            testTheseKing[6] = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
            testTheseKing[7] = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);
            for (int i = 0; i < 8; ++i)
            {
                ChessPosition kingPos = testTheseKing[i];
                boolean isOpen;
                boolean isEnemy = false;
                isOpen = kingPos.isValid() && board.isValid(kingPos);

                if ((board.getPiece(kingPos) != null) && board.getPiece(kingPos).color != this.color) {
                    isEnemy = true;
                    kingPos.hasEnemy = true;
                }
                if (isOpen || isEnemy) {
                    testThese[i] = kingPos;
                }
                if (isEnemy) {
                    break;
                }
            }

        }
        else if(type == PieceType.QUEEN)
        {
            //look at position and board
            //everywhere she can move add it set
            //return set

            // maybe copy and paste code from rook and bishop and see if it'll work lol
            //edit: it worked
            //first rook
            for (int i = 0; i < 8; ++i)
            {
                //row stays same, col moves down, piece looks to left
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-i);
                testThese[i] = testPosQueen;

            }
            for (int i = 0; i < 8; ++i)
            {
                //row moves up, column stays same, piece looks up
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn());
                testThese[i+7] = testPosQueen;

            }
            for (int i = 0; i < 8; ++i)
            {
                // row stays same, column looks to right, piece looks right
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+i);
                testThese[i+14] = testPosQueen;

            }
            for (int i = 0; i < 8; ++i)
            {
                // row moves down, col stays same, piece looks down
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn());
                testThese[i+21] = testPosQueen;

            }
            for (int i = 0; i < 8; ++i)
            {
                //top left
                //ChessPosition[] testThese = new ChessPosition[64];
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()+i);
                testThese[i+28] = testPosQueen;

            }
            for (int i = 0; i < 8; ++i)
            {
                //top right
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()-i);
                testThese[i+35] = testPosQueen;

            }
            for (int i = 0; i < 8; ++i)
            {
                // bottom right
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()+i);
                testThese[i+42] = testPosQueen;

            }
            for (int i = 0; i < 8; ++i)
            {
                //bottom left
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()-i);
                testThese[i+49] = testPosQueen;

            }
        }
        else if(type == PieceType.ROOK)
        {
            //look at position and board
            //everywhere he can move add it set
            //return set
            /// similar to bishop he needs to look in all 4 directions


            for (int i = 0; i < 8; ++i)
            {
                //row stays same, col moves down, piece looks to left
                ChessPosition testPosRook = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-i);
                testThese[i] = testPosRook;

            }
            for (int i = 0; i < 8; ++i)
            {
                //row moves up, column stays same, piece looks up
                ChessPosition testPosRook = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn());
                testThese[i+7] = testPosRook;

            }
            for (int i = 0; i < 8; ++i)
            {
                // row stays same, column looks to right, piece looks right
                ChessPosition testPosRook = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+i);
                testThese[i+14] = testPosRook;

            }
            for (int i = 0; i < 8; ++i)
            {
                // row moves down, col stays same, piece looks down
                ChessPosition testPosRook = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn());
                testThese[i+21] = testPosRook;

            }

        }
        else if(type == PieceType.KNIGHT)
        {
            //look at position and board
            //everywhere he can move add it set
            //return set
            // hard code 8 positions, sorry bro


            // create 8 positions on where the knight could potentially end up, test them
            //clock wise starting at noon, loop at bottom will check if out of bounds
            ChessPosition knightPos0 = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()+1);
            ChessPosition knightPos1 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+2);
            ChessPosition knightPos2 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+2);
            ChessPosition knightPos3 = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()+1);
            ChessPosition knightPos4 = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()-1);
            ChessPosition knightPos5 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-2);
            ChessPosition knightPos6 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-2);
            ChessPosition knightPos7 = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()-1);
            testThese[0] = knightPos0;
            testThese[1] = knightPos1;
            testThese[2] = knightPos2;
            testThese[3] = knightPos3;
            testThese[4] = knightPos4;
            testThese[5] = knightPos5;
            testThese[6] = knightPos6;
            testThese[7] = knightPos7;
            //these add the 8 pos to the testThese


        }
        else if(type == PieceType.BISHOP) {
            //make 4 array's of 7 positions each along the diagonal
            //because it could be anywhere
            //test if the pos is valid
            //if it is add it to set

            //I make 4 for loops to test each possible direction

            for (int i = 1; i < 8; ++i) {
                //top left
                ChessPosition testPosBishop = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i);
                boolean isOpen;
                boolean isEnemy = false;
                isOpen = testPosBishop.isValid() && board.isValid(testPosBishop);

                if ((board.getPiece(testPosBishop) != null) && board.getPiece(testPosBishop).color != this.color) {
                    isEnemy = true;
                    testPosBishop.hasEnemy = true;
                }
                if (isOpen || isEnemy) {
                    testThese[i] = testPosBishop;
                }
                if (isEnemy) {
                    break;
                }
                if (!isOpen)
                {
                    break;
                }



            }
            for (int i = 1; i < 8; ++i) {
                //top right
                ChessPosition testPosBishop = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i);

                boolean isOpen;
                boolean isEnemy = false;
                isOpen = testPosBishop.isValid() && board.isValid(testPosBishop);
                if ((board.getPiece(testPosBishop) != null) && board.getPiece(testPosBishop).color != this.color) {
                    isEnemy = true;
                    testPosBishop.hasEnemy = true;
                }
                if (isOpen || isEnemy) {
                    testThese[i + 7] = testPosBishop;
                }
                if (isEnemy) {
                    break;
                }
                if (!isOpen)
                {
                    break;
                }

            }
            for (int i = 1; i < 8; ++i) {
                // bottom right
                ChessPosition testPosBishop = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i);
                boolean isOpen;
                boolean isEnemy = false;
                isOpen = testPosBishop.isValid() && board.isValid(testPosBishop);
                if ((board.getPiece(testPosBishop) != null) && board.getPiece(testPosBishop).color != this.color) {
                    isEnemy = true;
                    testPosBishop.hasEnemy = true;
                }
                if (isOpen || isEnemy) {
                    testThese[i + 14] = testPosBishop;
                }
                if (isEnemy) {
                    break;
                }
                if (!isOpen)
                {
                    break;
                }

            }
            for (int i = 1; i < 8; ++i) {
                //bottom left
                ChessPosition testPosBishop = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i);
                boolean isOpen;
                boolean isEnemy = false;
                isOpen = testPosBishop.isValid() && board.isValid(testPosBishop);
                if ((board.getPiece(testPosBishop) != null) && board.getPiece(testPosBishop).color != this.color) {
                    isEnemy = true;
                    testPosBishop.hasEnemy = true;
                }
                if (isOpen || isEnemy) {
                    testThese[i + 21] = testPosBishop;
                }
                if (isEnemy) {
                    break;
                }
                if (!isOpen)
                {
                    break;
                }

            }
        }







        else if(type == PieceType.PAWN && this.color == ChessGame.TeamColor.WHITE)
        {
            //look at position and board
            //everywhere he can move add it set
            //return set
            //how do I know what color/direction he goes?
            //do i implement advancement now?

            //white starts on bottom... (always?)
            int counter = 0;
            //counter counts num of times we add something to testThese


            ChessPosition myPawnPos0 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
            testThese[counter] = myPawnPos0;
            counter++;

            // if pawn's first move then his row is always on 2, bidirectional
            if (myPosition.getColumn() == 2)
            {
                ChessPosition myPawnPos1 = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn());
                testThese[counter] = myPawnPos1;
                counter++;
            }

        }
        else if(type == PieceType.PAWN && this.color == ChessGame.TeamColor.BLACK)
        {
            //black starts on bottom... (always?)
            int counter = 0;
            //counter counts num of times we add something to testThese


            ChessPosition myPawnPos0 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
            testThese[counter] = myPawnPos0;
            counter++;

            // if pawn's first move then his row is always on 2, bidirectional
            if (myPosition.getColumn() == 2)
            {
                ChessPosition myPawnPos1 = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn());
                testThese[counter] = myPawnPos1;
                counter++;
            }
        }
        else {
            int error = 3;
        }

        //now we know what piece it was and added all possible pos to testThese
        //we add the array of testThese pos to the set
        for (int i = 0; i < testThese.length; ++i)
        {
            if (testThese[i] != null) {

                boolean isEnemy = testThese[i].hasEnemy();
                //checks if enemy then auto add
                // if ((board.getPiece(testThese[i]).type != null) && board.getPiece(testThese[i]).color != this.color)

                if ((isEnemy) || board.isValid(testThese[i]) && testThese[i].isValid()) {
                    set.add(new ChessMove(myPosition, testThese[i], null));
                }
            }
        }

        return set;
    }
}
