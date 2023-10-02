package chess;

import java.util.Collection;

public class Rook implements ChessPiece{
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
        return null;
    }
}
