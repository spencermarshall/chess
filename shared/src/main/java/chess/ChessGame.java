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

        for (int r = 1; r < 9; ++r)
        {
            for (int c = 1; c < 9; ++c)
            {
                ChessPosition testPos = new ChessPosition(r,c);
                if (board.getPiece(testPos) != null && board.getPiece(testPos).getTeamColor() != myPiece.getTeamColor())
                {
                    Collection<ChessMove> enemyMoves = board.getPiece(testPos).pieceMoves(board,testPos);

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
        if (!possibleMoves.contains(move) || board.getPiece(move.getStartPosition()) == null)
        {
            throw new InvalidMoveException();
        }
        //if we advance pawn and change piece type
        if (board.getPiece(move.getStartPosition()) != null && move.promotion != null)
        {
            ChessPiece adv = new ChessPiece(board.getPiece(move.getStartPosition()).getTeamColor(), move.promotion);
            board.addPiece(move.getEndPosition(),adv);
        }
        else
        { // this is the always the case unless we advance a pawn
            board.addPiece(move.getEndPosition(),board.getPiece(move.getStartPosition()));
        }
        //this adds the piece to the pos of where we want lol

        board.addPiece(move.getStartPosition(),null);


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
                    Collection<ChessMove> possibleMoves = validMoves(testPos);
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
