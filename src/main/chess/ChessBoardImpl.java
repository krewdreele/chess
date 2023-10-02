package chess;

public class ChessBoardImpl implements ChessBoard {

    private ChessPiece[][] board;

    public ChessBoardImpl(){
        board = new ChessPiece[8][8];
    }
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()-1][position.getColumn()-1] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()-1][position.getColumn()-1];
    }

    @Override
    public void resetBoard() {
        board = new ChessPiece[8][8];
        resetWhitePieces();
        resetBlackPieces();
    }

    private void resetWhitePieces(){
        board[0][0] = new Rook(ChessGame.TeamColor.WHITE);
        board[0][1] = new Knight(ChessGame.TeamColor.WHITE);
        board[0][2] = new Bishop(ChessGame.TeamColor.WHITE);
        board[0][3] = new Queen(ChessGame.TeamColor.WHITE);
        board[0][4] = new King(ChessGame.TeamColor.WHITE);
        board[0][5] = new Bishop(ChessGame.TeamColor.WHITE);
        board[0][6] = new Knight(ChessGame.TeamColor.WHITE);
        board[0][7] = new Rook(ChessGame.TeamColor.WHITE);
        //white pawns
        for(int i=0; i<8; i++){
            board[1][i] = new Pawn(ChessGame.TeamColor.WHITE);
        }
    }

    private void resetBlackPieces(){
        board[7][0] = new Rook(ChessGame.TeamColor.BLACK);
        board[7][1] = new Knight(ChessGame.TeamColor.BLACK);
        board[7][2] = new Bishop(ChessGame.TeamColor.BLACK);
        board[7][3] = new Queen(ChessGame.TeamColor.BLACK);
        board[7][4] = new King(ChessGame.TeamColor.BLACK);
        board[7][5] = new Bishop(ChessGame.TeamColor.BLACK);
        board[7][6] = new Knight(ChessGame.TeamColor.BLACK);
        board[7][7] = new Rook(ChessGame.TeamColor.BLACK);
        //black pawns
        for(int i=0; i<8; i++){
            board[6][i] = new Pawn(ChessGame.TeamColor.BLACK);
        }
    }
}
