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
    public void whitePawnMoves(ChessBoard board, ChessPosition myPosition, ChessPosition[] testThese, ChessPiece thisPiece) {
        //look at position and board
        //everywhere he can move add it set
        int counter = 0;
        //counter counts num of times we add something to testThese
        //move forward 1
        ChessPosition myPawnPos0 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
        //can't move 1 if enemy is there
        if (myPosition.getRow() != 7 && board.isValid(myPawnPos0)) {
            testThese[counter] = myPawnPos0;
            counter++;
        }
        // if pawn's first move then his row is always on 2, bidirectional statement of fact
        if (myPosition.getRow() == 2) {
            ChessPosition myPawnPos1 = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn());
            //can't move there if enemy tho
            ChessPosition blockPos = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
            if (board.isValid(myPawnPos1) && board.isValid(blockPos)) {
                testThese[counter] = myPawnPos1;
                counter++;
            }
        }
        //now needs to check the two diagonal
        ChessPosition myPawnPos2 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1);
        ChessPosition myPawnPos3 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);
        if (board.getPiece(myPawnPos2) != null && board.getPiece(myPawnPos2).color == ChessGame.TeamColor.BLACK) {
            myPawnPos2.hasEnemy = true;
            testThese[counter] = myPawnPos2;
            counter++;
            if (myPawnPos2.getRow() == 8) {
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
        }
        if (board.getPiece(myPawnPos3) != null && board.getPiece(myPawnPos3).color == ChessGame.TeamColor.BLACK) {
            myPawnPos3.hasEnemy = true;
            testThese[counter] = myPawnPos3;
            counter++;
            //this means enemy capture in last row
            if (myPawnPos3.getRow() == 8) {
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
        }
        //4 possible positions for advancement to get to other side
        if (myPosition.getRow() == 7) {
            //create the 4 posible pieces then carry it thru the poisition to myPawnAdv0
            ChessPosition myPawnAdv0 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn(), ChessPiece.PieceType.QUEEN );
            ChessPosition myPawnAdv1 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn(), ChessPiece.PieceType.ROOK );
            ChessPosition myPawnAdv2 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn(), ChessPiece.PieceType.BISHOP);
            ChessPosition myPawnAdv3 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn(), ChessPiece.PieceType.KNIGHT );
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
            if (board.getPiece(test1) != null && board.getPiece(test1).getPieceType() == ChessPiece.PieceType.PAWN && board.getPiece(actual1) == null && board.getPiece(test1).movedTwo) {
                //valid enpassant
                board.isValid(test1);
                board.isValid(actual1);
                testThese[counter] = actual1;
                counter++;
                board.getPiece(myPosition).justDidEnPassant = true;
            }
            if (board.getPiece(test2) != null && board.getPiece(test2).getPieceType() == ChessPiece.PieceType.PAWN && board.getPiece(actual2) == null && board.getPiece(test2).movedTwo) {
                //valid enpassant
                board.isValid(test2);
                board.isValid(actual2);
                testThese[counter] = actual2;
                board.getPiece(myPosition).justDidEnPassant = true;
            }
        }
    }
    public void blackPawnMoves(ChessBoard board, ChessPosition myPosition, ChessPosition[] testThese, ChessPiece thisPiece) {
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
