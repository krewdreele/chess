package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends ChessPieceImpl{
    private final ChessGame.TeamColor color;
    public Pawn(ChessGame.TeamColor pieceColor) {
        color = pieceColor;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        if(color == ChessGame.TeamColor.WHITE){
            //move 1
            ChessPosition newPosition = new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn());
            if(board.getPiece(newPosition) == null) moves.add(new ChessMoveImpl(myPosition, newPosition, null));
            //move 2
            if(myPosition.getRow() == 2) {
                ChessPosition newPosition2 = new ChessPositionImpl(myPosition.getRow() + 2, myPosition.getColumn());
                if (board.getPiece(newPosition2) == null) {
                    moves.add(new ChessMoveImpl(myPosition, newPosition2, null));
                }
            }
        }

        if(color == ChessGame.TeamColor.BLACK){
            //move 1
            ChessPosition newPosition = new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn());
            if(board.getPiece(newPosition) == null) {
                moves.add(new ChessMoveImpl(myPosition, newPosition, null));
                //move 2
                if (myPosition.getRow() == 7) {
                    ChessPosition newPosition2 = new ChessPositionImpl(myPosition.getRow() - 2, myPosition.getColumn());
                    if (board.getPiece(newPosition2) == null) {
                        moves.add(new ChessMoveImpl(myPosition, newPosition2, null));
                    }
                }
            }
        }


        ChessPosition diagLeft;
        ChessPosition diagRight;
        //take piece
        if(color == ChessGame.TeamColor.WHITE) {
            diagLeft = new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() - 1);
            diagRight = new ChessPositionImpl(myPosition.getRow() + 1, myPosition.getColumn() + 1);
        }
        else {
            diagLeft = new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() - 1);
            diagRight = new ChessPositionImpl(myPosition.getRow() - 1, myPosition.getColumn() + 1);
        }

        if(canTakePiece(board, diagLeft)) moves.add(new ChessMoveImpl(myPosition, diagLeft, null));
        if(canTakePiece(board, diagRight)) moves.add(new ChessMoveImpl(myPosition, diagRight, null));

        return moves;
    }

    private boolean canTakePiece(ChessBoard board, ChessPosition newPosition){
        if(newPosition.getRow() > 8 || newPosition.getRow() < 1 || newPosition.getColumn() > 8 || newPosition.getColumn() < 1){
            return false;
        }
       return (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != color);
    }
}
