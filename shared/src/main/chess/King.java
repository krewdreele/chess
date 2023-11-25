package chess;

import java.util.ArrayList;
import java.util.Collection;

public class King extends ChessPieceImpl{
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
        ArrayList<ChessMove> moves = new ArrayList<>();
        super.addDiagonalMoves(board, myPosition, moves, color, true);
        super.addVertHorizMoves(board, myPosition, moves, color, true);
        return moves;
    }
}
