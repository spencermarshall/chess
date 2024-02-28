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
    private boolean movedTwo;
    public boolean justDidEnPassant;
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


        if(type == PieceType.KING)
        {
            kingMoves(board, myPosition, testThese, this);
        }
        else if(type == PieceType.QUEEN)
        {
            //look at position and board
            //everywhere she can move add it set
            //return set

            // maybe copy and paste code from rook and bishop and see if it'll work lol
            //edit: it worked
            //first rook
            for (int i = 1; i < 8; ++i)
            {
                //row stays same, col moves down, piece looks to left
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-i);
                testThese[i] = testPosQueen;
                boolean isOpen;
                boolean isEnemy = false;
                isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);

                if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != this.color) {
                    isEnemy = true;
                    testPosQueen.hasEnemy = true;
                }
                if (isOpen || isEnemy) {
                    testThese[i] = testPosQueen;
                }
                if (isEnemy) {
                    break;
                }
                if (!isOpen)
                {
                    break;
                }


            }
            for (int i = 1; i < 8; ++i)
            {
                //row moves up, column stays same, piece looks up
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn());
                testThese[i+7] = testPosQueen;
                boolean isOpen;
                boolean isEnemy = false;
                isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);

                if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != this.color) {
                    isEnemy = true;
                    testPosQueen.hasEnemy = true;
                }
                if (isOpen || isEnemy) {
                    testThese[i+7] = testPosQueen;
                }
                if (isEnemy) {
                    break;
                }
                if (!isOpen)
                {
                    break;
                }

            }
            for (int i = 1; i < 8; ++i)
            {
                // row stays same, column looks to right, piece looks right
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+i);
                testThese[i+14] = testPosQueen;
                boolean isOpen;
                boolean isEnemy = false;
                isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);

                if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != this.color) {
                    isEnemy = true;
                    testPosQueen.hasEnemy = true;
                }
                if (isOpen || isEnemy) {
                    testThese[i+14] = testPosQueen;
                }
                if (isEnemy) {
                    break;
                }
                if (!isOpen)
                {
                    break;
                }

            }
            for (int i = 1; i < 8; ++i)
            {
                // row moves down, col stays same, piece looks down
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn());
                testThese[i+21] = testPosQueen;
                boolean isOpen;
                boolean isEnemy = false;
                isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);

                if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != this.color) {
                    isEnemy = true;
                    testPosQueen.hasEnemy = true;
                }
                if (isOpen || isEnemy) {
                    testThese[i+21] = testPosQueen;
                }
                if (isEnemy) {
                    break;
                }
                if (!isOpen)
                {
                    break;
                }

            }
            for (int i = 1; i < 8; ++i)
            {
                //top left
                //ChessPosition[] testThese = new ChessPosition[64];
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()+i);
                testThese[i+28] = testPosQueen;
                boolean isOpen;
                boolean isEnemy = false;
                isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);

                if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != this.color) {
                    isEnemy = true;
                    testPosQueen.hasEnemy = true;
                }
                if (isOpen || isEnemy) {
                    testThese[i+28] = testPosQueen;
                }
                if (isEnemy) {
                    break;
                }
                if (!isOpen)
                {
                    break;
                }

            }
            for (int i = 1; i < 8; ++i)
            {
                //top right
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()-i);
                testThese[i+35] = testPosQueen;
                boolean isOpen;
                boolean isEnemy = false;
                isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);

                if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != this.color) {
                    isEnemy = true;
                    testPosQueen.hasEnemy = true;
                }
                if (isOpen || isEnemy) {
                    testThese[i+35] = testPosQueen;
                }
                if (isEnemy) {
                    break;
                }
                if (!isOpen)
                {
                    break;
                }

            }
            for (int i = 1; i < 8; ++i)
            {
                // bottom right
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()+i);
                testThese[i+42] = testPosQueen;
                boolean isOpen;
                boolean isEnemy = false;
                isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);

                if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != this.color) {
                    isEnemy = true;
                    testPosQueen.hasEnemy = true;
                }
                if (isOpen || isEnemy) {
                    testThese[i+42] = testPosQueen;
                }
                if (isEnemy) {
                    break;
                }
                if (!isOpen)
                {
                    break;
                }

            }
            for (int i = 1; i < 8; ++i)
            {
                //bottom left
                ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()-i);
                testThese[i+49] = testPosQueen;
                boolean isOpen;
                boolean isEnemy = false;
                isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);

                if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != this.color) {
                    isEnemy = true;
                    testPosQueen.hasEnemy = true;
                }
                if (isOpen || isEnemy) {
                    testThese[i+49] = testPosQueen;
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
        else if(type == PieceType.ROOK)
        {
            rookMoves(board, myPosition, testThese, this);


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

            //these 8 test if the 8 possible pos have an enemy, it add's all to testThese but in
            //the check at bottom it checks the .hasEnemy attribute
            if ((board.getPiece(testThese[0]) != null) && board.getPiece(testThese[0]).color != this.color) {
                testThese[0].hasEnemy = true;
            }
            if ((board.getPiece(testThese[1]) != null) && board.getPiece(testThese[1]).color != this.color) {
                testThese[1].hasEnemy = true;
            }
            if ((board.getPiece(testThese[2]) != null) && board.getPiece(testThese[2]).color != this.color) {
                testThese[2].hasEnemy = true;
            }
            if ((board.getPiece(testThese[3]) != null) && board.getPiece(testThese[3]).color != this.color) {
                testThese[3].hasEnemy = true;
            }
            if ((board.getPiece(testThese[4]) != null) && board.getPiece(testThese[4]).color != this.color) {
                testThese[4].hasEnemy = true;
            }
            if ((board.getPiece(testThese[5]) != null) && board.getPiece(testThese[5]).color != this.color) {
                testThese[5].hasEnemy = true;
            }
            if ((board.getPiece(testThese[6]) != null) && board.getPiece(testThese[6]).color != this.color) {
                testThese[6].hasEnemy = true;
            }
            if ((board.getPiece(testThese[7]) != null) && board.getPiece(testThese[7]).color != this.color) {
                testThese[7].hasEnemy = true;
            }






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


            //move forward 1
            ChessPosition myPawnPos0 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
            //can't move 1 if enemy is there
            if (myPosition.getRow() != 7 && board.isValid(myPawnPos0))
            {
                testThese[counter] = myPawnPos0;
                counter++;
            }


            // if pawn's first move then his row is always on 2, bidirectional statement of fact
            if (myPosition.getRow() == 2)
            {
                ChessPosition myPawnPos1 = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn());
                //can't move there if enemy tho
                ChessPosition blockPos = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
                if (board.isValid(myPawnPos1) && board.isValid(blockPos))
                {
                    testThese[counter] = myPawnPos1;
                    counter++;
                }
            }


            //now needs to check the two diagonal
            ChessPosition myPawnPos2 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1);
            ChessPosition myPawnPos3 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);

            if (board.getPiece(myPawnPos2) != null && board.getPiece(myPawnPos2).color == ChessGame.TeamColor.BLACK)
            {
                myPawnPos2.hasEnemy = true;
                testThese[counter] = myPawnPos2;
                counter++;
                if (myPawnPos2.getRow() == 8)
                {
                    ChessPosition newPos0 = new ChessPosition(myPawnPos2.getRow(), myPawnPos2.getColumn(), PieceType.QUEEN);
                    ChessPosition newPos1 = new ChessPosition(myPawnPos2.getRow(), myPawnPos2.getColumn(), PieceType.BISHOP);
                    ChessPosition newPos2 = new ChessPosition(myPawnPos2.getRow(), myPawnPos2.getColumn(), PieceType.KNIGHT);
                    ChessPosition newPos3 = new ChessPosition(myPawnPos2.getRow(), myPawnPos2.getColumn(), PieceType.ROOK);

                    testThese[counter] = newPos0;
                    testThese[counter+1] = newPos1;
                    testThese[counter+2] = newPos2;
                    testThese[counter+3] = newPos3;
                    counter += 4;
                }
            }
            if (board.getPiece(myPawnPos3) != null && board.getPiece(myPawnPos3).color == ChessGame.TeamColor.BLACK)
            {
                myPawnPos3.hasEnemy = true;
                testThese[counter] = myPawnPos3;
                counter++;
                //this means enemy capture in last row
                if (myPawnPos3.getRow() == 8)
                {

                    ChessPosition newPos0 = new ChessPosition(myPawnPos3.getRow(), myPawnPos3.getColumn(), PieceType.QUEEN);
                    ChessPosition newPos1 = new ChessPosition(myPawnPos3.getRow(), myPawnPos3.getColumn(), PieceType.BISHOP);
                    ChessPosition newPos2 = new ChessPosition(myPawnPos3.getRow(), myPawnPos3.getColumn(), PieceType.KNIGHT);
                    ChessPosition newPos3 = new ChessPosition(myPawnPos3.getRow(), myPawnPos3.getColumn(), PieceType.ROOK);

                    testThese[counter] = newPos0;
                    testThese[counter+1] = newPos1;
                    testThese[counter+2] = newPos2;
                    testThese[counter+3] = newPos3;
                    counter += 4;
                }

            }
            //4 possible positions for advancement to get to other side
            if (myPosition.getRow() == 7)
            {
                //create the 4 posible pieces then carry it thru the poisition to myPawnAdv0

                ChessPosition myPawnAdv0 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn(), PieceType.QUEEN );
                ChessPosition myPawnAdv1 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn(),PieceType.ROOK );
                ChessPosition myPawnAdv2 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn(),PieceType.BISHOP);
                ChessPosition myPawnAdv3 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn(),PieceType.KNIGHT );
                testThese[counter] = myPawnAdv0;
                testThese[counter+1] = myPawnAdv1;
                testThese[counter+2] = myPawnAdv2;
                testThese[counter+3] = myPawnAdv3;
                counter += 4;


            }
            //check for enpassant, add moves to testThese


            ///todo pawn enpassant implementation
            if (myPosition.getRow() == 5)
            {
                //test for 2 pos of pawns next to me
                ChessPosition test1 = new ChessPosition(5, myPosition.getColumn()+1);
                ChessPosition test2 = new ChessPosition(5, myPosition.getColumn()-1);
                ChessPosition actual1 = new ChessPosition(6, myPosition.getColumn() + 1);
                ChessPosition actual2 = new ChessPosition(6, myPosition.getColumn() - 1);

                if (board.getPiece(test1) != null && board.getPiece(test1).getPieceType() == PieceType.PAWN && board.getPiece(actual1) == null && board.getPiece(test1).movedTwo)
                {
                    //valid enpassant
                    board.isValid(test1);
                    board.isValid(actual1);
                    testThese[counter] = actual1;
                    counter++;
                    board.getPiece(myPosition).justDidEnPassant = true;
                }
                if (board.getPiece(test2) != null && board.getPiece(test2).getPieceType() == PieceType.PAWN && board.getPiece(actual2) == null && board.getPiece(test2).movedTwo)
                {
                    //valid enpassant
                    board.isValid(test2);
                    board.isValid(actual2);
                    testThese[counter] = actual2;
                    counter++;
                    board.getPiece(myPosition).justDidEnPassant = true;
                }

            }
        }
        else if(type == PieceType.PAWN && this.color == ChessGame.TeamColor.BLACK)
        {
            //black starts on top... (always?)
            int counter = 0;
            //counter counts num of times we add something to testThese


            //move forward 1
            ChessPosition myPawnPos0 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
            //can't move 1 if enemy is there
            if (myPosition.getRow() != 2 && board.isValid(myPawnPos0))
            {
                testThese[counter] = myPawnPos0;
                counter++;
            }


            // if pawn's first move then his row is always on 2, bidirectional statement of fact
            if (myPosition.getRow() == 7)
            {
                ChessPosition myPawnPos1 = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn());
                //can't move there if enemy tho
                ChessPosition blockPos = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());

                if (board.isValid(myPawnPos1) && board.isValid(blockPos))
                {
                    testThese[counter] = myPawnPos1;
                    counter++;
                }
            }

            //now needs to check the two diagonal
            ChessPosition myPawnPos2 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
            ChessPosition myPawnPos3 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
            if (board.getPiece(myPawnPos2) != null && board.getPiece(myPawnPos2).color == ChessGame.TeamColor.WHITE)
            {
                myPawnPos2.hasEnemy = true;
                if (myPawnPos2.getRow() == 1)
                {
                    ChessPosition newPos0 = new ChessPosition(myPawnPos2.getRow(), myPawnPos2.getColumn(), PieceType.QUEEN);
                    ChessPosition newPos1 = new ChessPosition(myPawnPos2.getRow(), myPawnPos2.getColumn(), PieceType.BISHOP);
                    ChessPosition newPos2 = new ChessPosition(myPawnPos2.getRow(), myPawnPos2.getColumn(), PieceType.KNIGHT);
                    ChessPosition newPos3 = new ChessPosition(myPawnPos2.getRow(), myPawnPos2.getColumn(), PieceType.ROOK);

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
            if (board.getPiece(myPawnPos3) != null && board.getPiece(myPawnPos3).color == ChessGame.TeamColor.WHITE)
            {
                myPawnPos3.hasEnemy = true;
                if (myPawnPos3.getRow() == 1)
                {
                    ChessPosition newPos0 = new ChessPosition(myPawnPos3.getRow(), myPawnPos3.getColumn(), PieceType.QUEEN);
                    ChessPosition newPos1 = new ChessPosition(myPawnPos3.getRow(), myPawnPos3.getColumn(), PieceType.BISHOP);
                    ChessPosition newPos2 = new ChessPosition(myPawnPos3.getRow(), myPawnPos3.getColumn(), PieceType.KNIGHT);
                    ChessPosition newPos3 = new ChessPosition(myPawnPos3.getRow(), myPawnPos3.getColumn(), PieceType.ROOK);

                    testThese[counter] = newPos0;
                    testThese[counter+1] = newPos1;
                    testThese[counter+2] = newPos2;
                    testThese[counter+3] = newPos3;
                    counter += 4;
                }
                else
                {
                    testThese[counter] = myPawnPos3;
                    counter++;
                }

            }
            if (myPosition.getRow() == 2)
            {
                //create the 4 posible pieces then carry it thru  the poisition to myPawnAdv0

                ChessPosition myPawnAdv0 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn(), PieceType.QUEEN );
                ChessPosition myPawnAdv1 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn(),PieceType.ROOK );
                ChessPosition myPawnAdv2 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn(),PieceType.BISHOP);
                ChessPosition myPawnAdv3 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn(),PieceType.KNIGHT );
                testThese[counter] = myPawnAdv0;
                testThese[counter+1] = myPawnAdv1;
                testThese[counter+2] = myPawnAdv2;
                testThese[counter+3] = myPawnAdv3;
                counter += 4;


            }
            ///todo pawn enpassant implementation i gave up on lol
            if (myPosition.getRow() == 4)
            {
                //test for 2 pos of pawns next to me
                ChessPosition test1 = new ChessPosition(4, myPosition.getColumn()+1);
                ChessPosition test2 = new ChessPosition(4, myPosition.getColumn()-1);
                ChessPosition actual1 = new ChessPosition(3, myPosition.getColumn() + 1);
                ChessPosition actual2 = new ChessPosition(3, myPosition.getColumn() - 1);

                if (board.getPiece(test1) != null && board.getPiece(test1).getPieceType() == PieceType.PAWN && board.getPiece(actual1) == null && board.getPiece(test1).movedTwo)
                {
                    //valid enpassant
                    board.isValid(test1);
                    board.isValid(actual1);
                    testThese[counter] = actual1;
                    counter++;
                    board.getPiece(myPosition).justDidEnPassant = true;
                }
                if (board.getPiece(test2) != null && board.getPiece(test2).getPieceType() == PieceType.PAWN && board.getPiece(actual2) == null && board.getPiece(test2).movedTwo)
                {
                    //valid   enpassant
                    board.isValid(test2);
                    board.isValid(actual2);
                    testThese[counter] = actual2;
                    counter++;
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
