package chess;

import java.util.Collection;

public class Knight implements ChessPiece{
    private final ChessGame.TeamColor color;
    public Knight(ChessGame.TeamColor pieceColor) {
        color = pieceColor;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
