package chess;

public class MoveHelp {
    public void rookMoves(ChessBoard board, ChessPosition myPosition, ChessPosition[] testThese, ChessPiece thisPiece)
    {
        //look at position and board
        //everywhere he can move add it set
        //return set
        /// similar to bishop he needs to look in all 4 directions


        for (int i = 1; i < 8; ++i)
        {
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
            if (isEnemy) {
                break;
            }
            if (!isOpen)
            {
                break;
            }

        }

    }
    public void kingMoves(ChessBoard board, ChessPosition myPosition, ChessPosition[] testThese, ChessPiece thisPiece)
    {
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
        for (int i = 0; i < 8; ++i)
        {
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
