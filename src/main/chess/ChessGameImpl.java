package chess;

import java.util.ArrayList;
import java.util.Collection;

public class ChessGameImpl implements ChessGame{

    private ChessBoardImpl board;
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
        moves.removeIf(this::invalidMove);
        return moves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if(piece.getTeamColor() != turn){
            throw new InvalidMoveException("It is not your turn dummy!");
        }
        else if(invalidMove(move)){
            throw new InvalidMoveException("Move leaves King in check!");
        }
        else{
            board.movePiece(move);
            changeTurns();
        }
    }

    private boolean invalidMove(ChessMove move) {
        //is it part of the pieces available moves?
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if(!piece.pieceMoves(board, move.getStartPosition()).contains(move)){
            return true;
        }
        //will this move put our king in check?
        //copy our current board state
        ChessBoardImpl copyBoard = new ChessBoardImpl(board);
        board.movePiece(move);
        boolean inCheck = isInCheck(piece.getTeamColor());
        //reset board state
        setBoard(copyBoard);
        return inCheck;
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
            if(move.getEndPosition().equals(kingPosition)){
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
        this.board = (ChessBoardImpl) board;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }
}
