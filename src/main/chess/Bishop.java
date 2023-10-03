package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Bishop extends ChessPieceImpl implements ChessPiece{
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
        super.addDiagonalMoves(board, myPosition, moves, color, false);
        return moves;
    }


}
