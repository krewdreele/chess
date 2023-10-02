package chess;

import java.util.Collection;

public class King implements ChessPiece{
    private final ChessGame.TeamColor color;
    public King(ChessGame.TeamColor pieceColor) {
        color = pieceColor;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
