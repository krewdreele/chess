package chess;

import java.util.ArrayList;
import java.util.Collection;

public class ChessGameImpl implements ChessGame{

    private ChessBoard board;
    private TeamColor turn;
    public ChessGameImpl(){
        turn = TeamColor.WHITE;
        board = new ChessBoardImpl();
        board.resetBoard();
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
        else if(!validMoves(move.getStartPosition()).contains(move)){
            throw new InvalidMoveException("Move leaves King in check!");
        }
        else{
            board.movePiece(move);
            changeTurns();
        }
    }

    private boolean invalidMove(ChessMove move){
        TeamColor color = board.getPiece(move.getStartPosition()).getTeamColor();
        //copy our current board state
        ChessBoard copyBoard = new ChessBoardImpl(board);
        board.movePiece(move);
        boolean inCheck = isInCheck(color);
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
        TeamColor opposingTeamColor;
        if(teamColor == TeamColor.WHITE){
            opposingTeamColor = TeamColor.BLACK;
        }
        else{
            opposingTeamColor = TeamColor.WHITE;
        }
        ArrayList<ChessMove> opponentMoves = getAllMoves(opposingTeamColor);
        ChessPosition kingPosition = getKingPosition(teamColor);
        for(ChessMove move : opponentMoves){
            if(move.getEndPosition().equals(kingPosition)){
                return true;
            }
        }
        return false;
    }

    private ArrayList<ChessMove> getAllMoves(TeamColor teamColor){
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                ChessPosition position = new ChessPositionImpl(i, j);
                ChessPiece piece = board.getPiece(position);
                if(piece != null && piece.getTeamColor() == teamColor) {
                    possibleMoves.addAll(piece.pieceMoves(board, position));
                }
            }
        }
        return possibleMoves;
    }

    private ChessPosition getKingPosition(TeamColor teamColor){
        for(int i=1; i<=8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition position = new ChessPositionImpl(i, j);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                    return position;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        ArrayList<ChessMove> allMoves = getAllValidMoves(teamColor);
        return allMoves.isEmpty();
    }

    private ArrayList<ChessMove> getAllValidMoves(TeamColor teamColor){
        ArrayList<ChessMove> moves = new ArrayList<>();
        for(int i=1; i<=8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPositionImpl(i, j);
                ChessPiece piece = board.getPiece(pos);
                if(piece != null && piece.getTeamColor() == teamColor){
                    moves.addAll(validMoves(pos));
                }
            }
        }
        return moves;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        if(turn != teamColor) return false;
        ArrayList<ChessMove> allMoves = getAllValidMoves(teamColor);
        return allMoves.isEmpty();
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
