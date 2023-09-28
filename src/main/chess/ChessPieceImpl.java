package chess;

import java.util.Collection;

public class ChessPieceImpl implements ChessPiece{

    private PieceType type;
    private ChessGame.TeamColor color;

    public ChessPieceImpl(PieceType type, ChessGame.TeamColor color){
        this.type = type;
        this.color = color;
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return null;
    }

    @Override
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
