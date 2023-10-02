package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Bishop implements ChessPiece{
    private final ChessGame.TeamColor color;

    public Bishop(ChessGame.TeamColor pieceColor){
        this.color = pieceColor;
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        do {
            row++;
            column++;
        } while (addMove(row, column, board, myPosition, moves));

        row = myPosition.getRow();
        column = myPosition.getColumn();
        do {
            row--;
            column++;
        } while (addMove(row, column, board, myPosition, moves));

        row = myPosition.getRow();
        column = myPosition.getColumn();
        do {
            row--;
            column--;
        } while (addMove(row, column, board, myPosition, moves));

        row = myPosition.getRow();
        column = myPosition.getColumn();
        do {
            row++;
            column--;
        } while (addMove(row, column, board, myPosition, moves));

        return moves;
    }

    private boolean addMove(int row, int column, ChessBoard board, ChessPosition myPosition, ArrayList<ChessMove> moves)
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
        else if(board.getPiece(newPosition).getTeamColor() != color){
            moves.add(newMove);
            return false;
        }
        //same team's piece
        return false;
    }
}
