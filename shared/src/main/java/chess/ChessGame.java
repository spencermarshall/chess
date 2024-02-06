package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    boolean isWhiteTurn;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        if (isWhiteTurn)
        {
            return TeamColor.WHITE;
        }
        else
            return TeamColor.BLACK;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        if (team == TeamColor.WHITE)
        {
            isWhiteTurn = true;
        }
        else
            isWhiteTurn = false;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK,

    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {

        //myPiece is piece we start with
        ChessPiece myPiece = new ChessPiece(board.getPiece(startPosition).getTeamColor(),board.getPiece(startPosition).getPieceType());


        Collection<ChessMove> moves = new HashSet<>();
        moves = myPiece.pieceMoves(this.board,startPosition);
        ChessMove [] manyMoves = moves.toArray(new ChessMove[0]);
        for (int i = 0; i < manyMoves.length; ++i)
        {
            //test if each move if still in check remove from moves
            boolean check = checkAfterMove(manyMoves[i],myPiece.getTeamColor());
            if (check)
            {
                //we are still in check, can't do that move
                moves.remove(manyMoves[i]);
            }

        }




        // check for when king could be threatened
        //if piece is king
        //loop thru enemies, if enemy pieceMove() coordinates is in king Piece moves
        // then remove it from king piece moves

        if (myPiece.getPieceType() != ChessPiece.PieceType.KING)
            return moves;
        //now we know it's king
        //TODO need to check if moving a piece puts king in check, if it does then remove move
        Collection<ChessMove> actualMoves = new HashSet<>();
        ChessMove[] myMoves = moves.toArray(new ChessMove[0]);

        if (myMoves.length == 0)
        {
            return new HashSet<ChessMove>();
        }

        ChessPosition startTemp = new ChessPosition(startPosition.getRow(), startPosition.getColumn());

        for (int r = 1; r < 9; ++r)
        {
            for (int c = 1; c < 9; ++c)
            {
                ChessPosition testPos = new ChessPosition(r,c);
                //account for ally pieces that can block check
                if (board.getPiece(testPos) != null && board.getPiece(testPos).getTeamColor() == myPiece.getTeamColor() && board.getPiece(testPos).getPieceType() != ChessPiece.PieceType.KING)
                {
                    //if my color piece is at this square and not my king
                    //do the move
                    Collection<ChessMove> allyMoves = board.getPiece(testPos).pieceMoves(board,testPos);
                    ChessMove[] allyMovesArr = allyMoves.toArray(new ChessMove[0]);
                    for (int i = 0; i < allyMovesArr.length; ++i)
                    {
                        boolean check = checkAfterMove(allyMovesArr[i], getTeamTurn());
                        if (isInCheck(getTeamTurn()) && !check)
                        {
                            //not in check, add it to moves
                            moves.add(allyMovesArr[i]);
                        }
                    }

                }




                if (board.getPiece(testPos) != null && board.getPiece(testPos).getTeamColor() != myPiece.getTeamColor())
                {
                    Collection<ChessMove> enemyMoves = board.getPiece(testPos).pieceMoves(board,testPos);

                    //loop thru possible king moves
                    for (int k = 0; k < myMoves.length; ++k)
                    {
                        ChessPosition potLoc = myMoves[k].getEndPosition();
                        ChessPiece testPiece = board.getPiece(myMoves[k].getEndPosition());
                        board.addPiece(potLoc,myPiece);
                        ChessGame.TeamColor reverse = TeamColor.WHITE;
                        if (isWhiteTurn)
                        {
                            reverse = TeamColor.BLACK;
                        }
                        board.addPiece(potLoc,testPiece);
                        boolean futureCheck = isInCheck(reverse);
                       // board.addPiece(potLoc,testPiece);
                        if (futureCheck)
                        {
                            //if it'll put me in check remove it from an option to move there
                            moves.remove(myMoves[k]);
                        }
                    }



                    if (board.getPiece(testPos).getPieceType() == ChessPiece.PieceType.PAWN && myPiece.getTeamColor() == TeamColor.WHITE)
                    {
                        //remove old pawn attack pos, enemy is black pawn
                        ChessPosition endOldPawn = new ChessPosition(testPos.getRow()-1, testPos.getColumn());
                        ChessMove oldPawn = new ChessMove(startPosition, endOldPawn, null);
                        enemyMoves.remove(oldPawn);
                        ChessPosition pawn1 = new ChessPosition(testPos.getRow()-1, testPos.getColumn()-1);
                        ChessPosition pawn2 = new ChessPosition(testPos.getRow()-1, testPos.getColumn()+1);
                        ChessMove pawnAttack1 = new ChessMove(startPosition, pawn1, null);
                        ChessMove pawnAttack2 = new ChessMove(startPosition, pawn2, null);
                        enemyMoves.add(pawnAttack1);
                        enemyMoves.add(pawnAttack2);
                        int dw = 2;

                    }
                    if (board.getPiece(testPos).getPieceType() == ChessPiece.PieceType.PAWN && myPiece.getTeamColor() == TeamColor.BLACK)
                    {
                        //remove old pawn attack pos, enemy is white pawn
                        ChessPosition endOldPawn = new ChessPosition(testPos.getRow()+1, testPos.getColumn());
                        ChessMove oldPawn = new ChessMove(startPosition, endOldPawn, null);
                        enemyMoves.remove(oldPawn);
                        ChessPosition pawn1 = new ChessPosition(testPos.getRow()+1, testPos.getColumn()-1);
                        ChessPosition pawn2 = new ChessPosition(testPos.getRow()+1, testPos.getColumn()+1);
                        ChessMove pawnAttack1 = new ChessMove(startPosition, pawn1, null);
                        ChessMove pawnAttack2 = new ChessMove(startPosition, pawn2, null);
                        enemyMoves.add(pawnAttack1);
                        enemyMoves.add(pawnAttack2);
                        int dw = 2;
                    }

                        //this removes pos where king can't go because it's in enemy line of sight
                    moves = removeBadKingPos(moves, enemyMoves);
                    int h = 3;
                }
            }
        }

        return moves;
    }

    ///todo, moves, checks if still in test, undo and if still in check return true
    boolean checkAfterMove(ChessMove test, ChessGame.TeamColor color )
    {
        ChessPiece pieceAtEnd = board.getPiece(test.getEndPosition());

        boolean check = false;
        ChessPosition startPos = test.getStartPosition();
        ChessPosition endPos = test.getEndPosition();
        ChessPiece testingPiece = board.getPiece(test.getStartPosition());
        board.addPiece(endPos, testingPiece);
        //this is null b/c we move our piece so there's nothing at old pos
        board.addPiece(startPos, null);
        check = isInCheck(color);

        //moves everything back to original, like it never happened
        board.addPiece(startPos,testingPiece);
        board.addPiece(endPos, pieceAtEnd);

        return check;
    }



    //TODO takes moves and removes the moves that enemyMoves prohibits, we assume piece is king
    public Collection<ChessMove> removeBadKingPos(Collection<ChessMove>moves, Collection<ChessMove> enemyMoves)
    {
        Collection<ChessMove> returnValues = moves;
        ChessMove[]myMoves = moves.toArray(new ChessMove[0]);
        ChessMove[]enemyMovesArray = enemyMoves.toArray(new ChessMove[0]);
        for (int i = 0; i < enemyMovesArray.length; ++i)
        {
            for (int j = 0; j < myMoves.length; ++j)
            {

                if (enemyMovesArray[i].getEndPosition().equals(myMoves[j].getEndPosition()))
                {
                    //end pos are equal, king can't move there
                    returnValues.add(myMoves[j]);
                    ChessMove removeit = myMoves[j];
                    returnValues.remove(removeit);
                }


            }
        }
        return returnValues;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> possibleMoves = validMoves(move.getStartPosition());
        //it's not ur turn
        ChessGame.TeamColor moveColor = board.getPiece(move.getStartPosition()).getTeamColor();
        if (moveColor != getTeamTurn())
        {
            throw new InvalidMoveException();
        }
        if (!possibleMoves.contains(move) || board.getPiece(move.getStartPosition()) == null)
        {
            throw new InvalidMoveException();
        }

        //loop thru every piece and reset if it just movedTwo
        for (int r = 1; r < 9; ++r)
        {
            for (int c = 1; c < 9; ++c)
            {
                ChessPosition test = new ChessPosition(r,c);
                if (board.getPiece(test) != null)
                {
                    board.getPiece(test).movedTwo(false);
                }
            }
        }
        //since we gonna be moving, for the point in the move.startPos(), set that piece to true for hasMoved
                board.getPiece(move.getStartPosition()).hasMoved(true);

        if (board.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.PAWN)
        {
            //check for white and black
            if (board.getPiece(move.getStartPosition()).getTeamColor() == TeamColor.WHITE && move.getEndPosition().getRow() == 4 && move.getStartPosition().getRow() == 2)
            {
                //white pawn, starts at 2 and ends at 4
                board.getPiece(move.getStartPosition()).movedTwo(true);

            }
            if (board.getPiece(move.getStartPosition()).getTeamColor() == TeamColor.BLACK && move.getEndPosition().getRow() == 5 && move.getStartPosition().getRow() == 7)
            {
                //black pawn, starts at 7 ends at 5
                board.getPiece(move.getStartPosition()).movedTwo(true);
            }
        }


        //if we advance pawn and change piece type
        if (board.getPiece(move.getStartPosition()) != null && move.promotion != null)
        {
            ChessPiece adv = new ChessPiece(board.getPiece(move.getStartPosition()).getTeamColor(), move.promotion);
            board.addPiece(move.getEndPosition(),adv);
        }
        else
        { // this is the always the case unless we advance a pawn
            //check if pawn && if moving 2, if yes then make enPassantPossible true

            board.addPiece(move.getEndPosition(),board.getPiece(move.getStartPosition()));

        }
        //this adds the piece to the pos of where we want lol
        board.addPiece(move.getStartPosition(),null);
        if (isWhiteTurn)
        {
            setTeamTurn(TeamColor.BLACK);
        }
        else
        {
            setTeamTurn(TeamColor.WHITE);
        }

        //todo pawn enpassant implementation
        boolean enPassant = board.getPiece(move.getEndPosition()).justDidEnPassant;
        int newCol = move.getEndPosition().getColumn();
        if (enPassant)
        {
            ChessPosition removePos = new ChessPosition(move.getStartPosition().getRow(), newCol);
            board.addPiece(removePos, null);
        }





    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        //loop thru board, find where king is, take that pos,
        //see if enemy valid moves includes there
        int row = 0;
        int col = 0;
        for (int r = 1; r < 9; ++r)
        {
            for (int c = 1; c < 9; ++c)
            {
                ChessPosition myPos = new ChessPosition(r,c);

                //if king and color we check is a match (once the entire game)
                if (this.board.getPiece(myPos)!= null && this.board.getPiece(myPos).getPieceType() == ChessPiece.PieceType.KING && this.board.getPiece(myPos).getTeamColor() == teamColor)
                {
                    row = r;
                    col = c;
                }
            }
        }
        //this is for sure where king is
        ChessPosition kingPos = new ChessPosition(row,col);
        //loop thru board, for every pos test if piece there and enemy and can attack king
        for (int r = 1; r < 9; ++r)
        {
            for (int c = 1; c < 9; ++c)
            {
                ChessPosition testPos = new ChessPosition(r,c);
                if (this.board.getPiece(testPos)!= null &&
                    this.board.getPiece(testPos).getTeamColor() != teamColor)
                {
                    Collection<ChessMove> possibleMoves = board.getPiece(testPos).pieceMoves(board,testPos);

                 //   Collection<ChessMove> possibleMoves = validMoves(testPos);
                    //for every enemy we see if it can attack coordinates of king
                    for (int i = 0; i < possibleMoves.size(); ++i)
                    {
                        //move from enemy to king
                        ChessMove kingMove = new ChessMove(testPos,kingPos,null);
                        if (possibleMoves.contains(kingMove))
                        {
                            /// TODO
                            //if it's a possible move for the enemy at this pos
                            // to attack the king
                            //returns true, is in Check
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //loop thru board, find all ally pieces, if they have any valid moves return false
        //at end return true;

        //if im not even in check, can't be in checkmate
        if (!isInCheck(teamColor))
        {
            return false;
        }
        boolean flag = true;
        for (int r = 1; r < 9; ++r)
        {
            for (int c = 1; c < 9; ++c)
            {
                ChessPosition testPos = new ChessPosition(r,c);
                if(this.board.getPiece(testPos) != null && this.board.getPiece(testPos).getPieceType() == ChessPiece.PieceType.KING &&
                    this.board.getPiece(testPos).getTeamColor() == teamColor)
                {
                    Collection<ChessMove> possibleMoves = validMoves(testPos);
                    //if this piece moves is not empty, return false, we CAN move
                    if (!possibleMoves.isEmpty())
                    {
                        flag = false;
                    }

                }
            }
        }
       return flag;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //find king see if his moves is 0
        for (int r = 1; r < 9; ++r)
        {
            for (int c = 1; c < 9; ++c)
            {
                ChessPosition testPos = new ChessPosition(r,c);
                if (board.getPiece(testPos)!= null && board.getPiece(testPos).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(testPos).getTeamColor() == teamColor)
                {
                    //this is king
                    Collection<ChessMove> kingMoves = validMoves(testPos);
                    return kingMoves.isEmpty();
                }
            }
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }


}
