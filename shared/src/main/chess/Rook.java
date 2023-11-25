package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Rook extends ChessPieceImpl{
    private final ChessGame.TeamColor color;
    public Rook(ChessGame.TeamColor pieceColor) {
        this.color = pieceColor;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        super.addVertHorizMoves(board, myPosition, moves, color, false);
        return moves;
    }
}
