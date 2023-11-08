package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Knight extends ChessPieceImpl{
    private final ChessGame.TeamColor color;
    private final PieceType type = PieceType.KNIGHT;
    public Knight(ChessGame.TeamColor pieceColor) {
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
        super.addMove(myPosition.getRow() - 2, myPosition.getColumn() - 1, board, myPosition, moves, color);
        super.addMove(myPosition.getRow() - 2, myPosition.getColumn() + 1, board, myPosition, moves, color);
        super.addMove(myPosition.getRow() - 1, myPosition.getColumn() + 2, board, myPosition, moves, color);
        super.addMove(myPosition.getRow() - 1, myPosition.getColumn() - 2, board, myPosition, moves, color);
        super.addMove(myPosition.getRow() + 1, myPosition.getColumn() + 2, board, myPosition, moves, color);
        super.addMove(myPosition.getRow() + 1, myPosition.getColumn() - 2, board, myPosition, moves, color);
        super.addMove(myPosition.getRow() + 2, myPosition.getColumn() - 1, board, myPosition, moves, color);
        super.addMove(myPosition.getRow() + 2, myPosition.getColumn() + 1, board, myPosition, moves, color);
        return moves;
    }
}
