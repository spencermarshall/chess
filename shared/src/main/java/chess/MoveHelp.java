package chess;

public class MoveHelp {
    public void bishopMoves(ChessBoard board, ChessPosition myPosition, ChessPosition[] testThese, ChessPiece thisPiece)
    {
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
            if ((board.getPiece(testPosBishop) != null) && board.getPiece(testPosBishop).color != thisPiece.color) {
                isEnemy = true;
                testPosBishop.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i] = testPosBishop;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
        for (int i = 1; i < 8; ++i) {
            //top right
            ChessPosition testPosBishop = new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i);
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosBishop.isValid() && board.isValid(testPosBishop);
            if ((board.getPiece(testPosBishop) != null) && board.getPiece(testPosBishop).color != thisPiece.color) {
                isEnemy = true;
                testPosBishop.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i + 7] = testPosBishop;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
        for (int i = 1; i < 8; ++i) {
            // bottom right
            ChessPosition testPosBishop = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i);
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosBishop.isValid() && board.isValid(testPosBishop);
            if ((board.getPiece(testPosBishop) != null) && board.getPiece(testPosBishop).color != thisPiece.color) {
                isEnemy = true;
                testPosBishop.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i + 14] = testPosBishop;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
        for (int i = 1; i < 8; ++i) {
            //bottom left
            ChessPosition testPosBishop = new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i);
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosBishop.isValid() && board.isValid(testPosBishop);
            if ((board.getPiece(testPosBishop) != null) && board.getPiece(testPosBishop).color != thisPiece.color) {
                isEnemy = true;
                testPosBishop.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i + 21] = testPosBishop;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
    }
    public void knightMoves(ChessBoard board, ChessPosition myPosition, ChessPosition[] testThese, ChessPiece thisPiece)
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
        if ((board.getPiece(testThese[0]) != null) && board.getPiece(testThese[0]).color != thisPiece.color) {
            testThese[0].hasEnemy = true;
        }
        if ((board.getPiece(testThese[1]) != null) && board.getPiece(testThese[1]).color != thisPiece.color) {
            testThese[1].hasEnemy = true;
        }
        if ((board.getPiece(testThese[2]) != null) && board.getPiece(testThese[2]).color != thisPiece.color) {
            testThese[2].hasEnemy = true;
        }
        if ((board.getPiece(testThese[3]) != null) && board.getPiece(testThese[3]).color != thisPiece.color) {
            testThese[3].hasEnemy = true;
        }
        if ((board.getPiece(testThese[4]) != null) && board.getPiece(testThese[4]).color != thisPiece.color) {
            testThese[4].hasEnemy = true;
        }
        if ((board.getPiece(testThese[5]) != null) && board.getPiece(testThese[5]).color != thisPiece.color) {
            testThese[5].hasEnemy = true;
        }
        if ((board.getPiece(testThese[6]) != null) && board.getPiece(testThese[6]).color != thisPiece.color) {
            testThese[6].hasEnemy = true;
        }
        if ((board.getPiece(testThese[7]) != null) && board.getPiece(testThese[7]).color != thisPiece.color) {
            testThese[7].hasEnemy = true;
        }
    }
    public void rookMoves(ChessBoard board, ChessPosition myPosition, ChessPosition[] testThese, ChessPiece thisPiece)
    {
        //look at position and board
        //everywhere he can move add it set
        //return set
        /// similar to bishop he needs to look in all 4 directions
        for (int i = 1; i < 8; ++i) {
            //row stays same, col moves down, piece looks to left
            ChessPosition testPosRook = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-i);
            testThese[i] = testPosRook;
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosRook.isValid() && board.isValid(testPosRook);
            if ((board.getPiece(testPosRook) != null) && board.getPiece(testPosRook).color != thisPiece.color) {
                isEnemy = true;
                testPosRook.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i] = testPosRook;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
        for (int i = 1; i < 8; ++i) {
            //row moves up, column stays same, piece looks up
            ChessPosition testPosRook = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn());
            testThese[i+7] = testPosRook;
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosRook.isValid() && board.isValid(testPosRook);
            if ((board.getPiece(testPosRook) != null) && board.getPiece(testPosRook).color != thisPiece.color) {
                isEnemy = true;
                testPosRook.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i+7] = testPosRook;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
        for (int i = 1; i < 8; ++i) {
            // row stays same, column looks to right, piece looks right
            ChessPosition testPosRook = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+i);
            testThese[i+14] = testPosRook;
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosRook.isValid() && board.isValid(testPosRook);

            if ((board.getPiece(testPosRook) != null) && board.getPiece(testPosRook).color != thisPiece.color) {
                isEnemy = true;
                testPosRook.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i+14] = testPosRook;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
        for (int i = 1; i < 8; ++i) {
            // row moves down, col stays same, piece looks down
            ChessPosition testPosRook = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn());
            testThese[i+21] = testPosRook;
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosRook.isValid() && board.isValid(testPosRook);
            if ((board.getPiece(testPosRook) != null) && board.getPiece(testPosRook).color != thisPiece.color) {
                isEnemy = true;
                testPosRook.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i+21] = testPosRook;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
    }
    public void queenMoves(ChessBoard board, ChessPosition myPosition, ChessPosition[] testThese, ChessPiece thisPiece)
    {
        //look at position and board
        //everywhere she can move add it set
        //first rook moves
        for (int i = 1; i < 8; ++i) {
            //row stays same, col moves down, piece looks to left
            ChessPosition testPosQueen = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-i);
            testThese[i] = testPosQueen;
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);
            if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != thisPiece.color) {
                isEnemy = true;
                testPosQueen.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i] = testPosQueen;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
        for (int i = 1; i < 8; ++i) {
            //row moves up, column stays same, piece looks up
            ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn());
            testThese[i+7] = testPosQueen;
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);
            if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != thisPiece.color) {
                isEnemy = true;
                testPosQueen.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i+7] = testPosQueen;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
        for (int i = 1; i < 8; ++i) {
            // row stays same, column looks to right, piece looks right
            ChessPosition testPosQueen = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+i);
            testThese[i+14] = testPosQueen;
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);
            if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != thisPiece.color) {
                isEnemy = true;
                testPosQueen.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i+14] = testPosQueen;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
        for (int i = 1; i < 8; ++i) {
            // row moves down, col stays same, piece looks down
            ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn());
            testThese[i+21] = testPosQueen;
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);
            if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != thisPiece.color) {
                isEnemy = true;
                testPosQueen.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i+21] = testPosQueen;
            }
            if (!isOpen || isEnemy) {
                break;
            }
        }
        for (int i = 1; i < 8; ++i) {
            //top left
            ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()+i);
            testThese[i+28] = testPosQueen;
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);
            if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != thisPiece.color) {
                isEnemy = true;
                testPosQueen.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i+28] = testPosQueen;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
        for (int i = 1; i < 8; ++i) {
            //top right
            ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()-i);
            testThese[i+35] = testPosQueen;
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);
            if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != thisPiece.color) {
                isEnemy = true;
                testPosQueen.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i+35] = testPosQueen;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
        for (int i = 1; i < 8; ++i) {
            // bottom right
            ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()+i);
            testThese[i+42] = testPosQueen;
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);
            if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != thisPiece.color) {
                isEnemy = true;
                testPosQueen.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i+42] = testPosQueen;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
        for (int i = 1; i < 8; ++i) {
            //bottom left
            ChessPosition testPosQueen = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()-i);
            testThese[i+49] = testPosQueen;
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = testPosQueen.isValid() && board.isValid(testPosQueen);
            if ((board.getPiece(testPosQueen) != null) && board.getPiece(testPosQueen).color != thisPiece.color) {
                isEnemy = true;
                testPosQueen.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i+49] = testPosQueen;
            }
            if (isEnemy || !isOpen) {
                break;
            }
        }
    }
    public void kingMoves(ChessBoard board, ChessPosition myPosition, ChessPosition[] testThese, ChessPiece thisPiece) {
        //look at position and board
        //everywhere he can move add it set
        //return set
        ChessPosition[] testTheseKing = new ChessPosition[10];
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
        for (int i = 0; i < 8; ++i) {
            ChessPosition kingPos = testTheseKing[i];
            boolean isOpen;
            boolean isEnemy = false;
            isOpen = kingPos.isValid() && board.isValid(kingPos);
            if ((board.getPiece(kingPos) != null) && board.getPiece(kingPos).color != thisPiece.color) {
                isEnemy = true;
                kingPos.hasEnemy = true;
            }
            if (isOpen || isEnemy) {
                testThese[i] = kingPos;
            }
        }
    }
}
