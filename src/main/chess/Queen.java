package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Queen extends ChessPieceImpl{
    private final ChessGame.TeamColor color;
    private final PieceType type = PieceType.QUEEN;
    public Queen(ChessGame.TeamColor pieceColor) {
        color = pieceColor;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    @Override
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        super.addDiagonalMoves(board, myPosition, moves, color, false);
        super.addVertHorizMoves(board, myPosition, moves, color, false);
        return moves;
    }
}
