package chess;

import java.util.Collection;

public abstract class ChessPieceImpl implements ChessPiece
{
    protected boolean addMove(int row, int column, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves, ChessGame.TeamColor myColor)
    {
        if(row > 8 || row < 1 || column > 8 || column < 1){
            return false;
        }
        ChessPosition newPosition = new ChessPositionImpl(row, column);
        ChessMoveImpl newMove = new ChessMoveImpl(myPosition, newPosition, null);
        //empty space
        if(board.getPiece(newPosition) == null){
            moves.add(newMove);
            return true;
        }//other team's piece
        else if(board.getPiece(newPosition).getTeamColor() != myColor){
            moves.add(newMove);
            return false;
        }
        //same team's piece
        return false;
    }

    protected void addDiagonalMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves, ChessGame.TeamColor myColor, boolean oneSpace){
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        do {
            row++;
            column++;
        } while (addMove(row, column, board, myPosition, moves, myColor) && !oneSpace);

        row = myPosition.getRow();
        column = myPosition.getColumn();
        do {
            row--;
            column++;
        } while (addMove(row, column, board, myPosition, moves, myColor) && !oneSpace);

        row = myPosition.getRow();
        column = myPosition.getColumn();
        do {
            row--;
            column--;
        } while (addMove(row, column, board, myPosition, moves, myColor) && !oneSpace);

        row = myPosition.getRow();
        column = myPosition.getColumn();
        do {
            row++;
            column--;
        } while (addMove(row, column, board, myPosition, moves, myColor) && !oneSpace);
    }

    protected void addVertHorizMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves, ChessGame.TeamColor myColor, boolean oneSpace){
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        do {
            row++;
        } while (addMove(row, column, board, myPosition, moves, myColor) && !oneSpace);

        row = myPosition.getRow();
        do {
            row--;
        } while (addMove(row, column, board, myPosition, moves, myColor) && !oneSpace);

        row = myPosition.getRow();
        do {
            column++;
        } while (addMove(row, column, board, myPosition, moves, myColor) && !oneSpace);

        column = myPosition.getColumn();
        do {
            column--;
        } while (addMove(row, column, board, myPosition, moves, myColor) && !oneSpace);
    }
}
