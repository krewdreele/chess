package chess;

import java.util.ArrayList;
import java.util.Collection;

public class ChessGameImpl implements ChessGame{

    private ChessBoard board;
    private TeamColor turn;
    public ChessGameImpl(){
        turn = TeamColor.WHITE;
    }
    @Override
    public TeamColor getTeamTurn() {
        return turn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        if(piece == null){
            return null;
        }
        ArrayList<ChessMove> moves = new ArrayList<>(piece.pieceMoves(board, startPosition));
        for(ChessMove move : moves){
            TeamColor color = board.getPiece(move.getStartPosition()).getTeamColor();
            if(invalidMove(move, color)){
                moves.remove(move);
            }
        }
        return moves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        TeamColor color = board.getPiece(move.getStartPosition()).getTeamColor();
        if(color != turn){
            throw new InvalidMoveException("It is not your turn dummy!");
        }
        else if(invalidMove(move, color)){
            throw new InvalidMoveException("Move leaves King in check!");
        }
        else{
            move(move, null);
            changeTurns();
        }
    }

    private boolean invalidMove(ChessMove move, TeamColor color) {
        boolean invalid;
        ChessPiece takenPiece = board.getPiece(move.getEndPosition());
        ChessMove undo = new ChessMoveImpl(move.getEndPosition(), move.getStartPosition(), null);
        move(move, null);
        invalid = isInCheck(color);
        move(undo, takenPiece);
        return invalid;
    }

    private void move(ChessMove move, ChessPiece replace){
        ChessPiece start = board.getPiece(move.getStartPosition());
        board.addPiece(move.getEndPosition(), start);
        board.addPiece(move.getStartPosition(), replace);
    }

    private void changeTurns(){
        if(turn == TeamColor.WHITE){
            turn = TeamColor.BLACK;
        }
        else if(turn == TeamColor.BLACK){
            turn = TeamColor.WHITE;
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        ChessPosition kingPosition = null;
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                ChessPosition position = new ChessPositionImpl(i, j);
                ChessPiece piece = board.getPiece(position);
                if(piece == null){
                    continue;
                }
                if(piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() ==  teamColor){
                    kingPosition = position;
                }
                else if(piece.getTeamColor() != teamColor) {
                    possibleMoves.addAll(piece.pieceMoves(board, position));
                }
            }
        }

        for(ChessMove move : possibleMoves){
            if(move.getEndPosition() == kingPosition){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }
}
