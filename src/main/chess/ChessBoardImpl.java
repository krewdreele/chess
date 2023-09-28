package chess;

public class ChessBoardImpl implements ChessBoard {

    private ChessPiece[][] board;

    public ChessBoardImpl(){
        resetBoard();
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
        board[0][0] = new ChessPieceImpl(ChessPiece.PieceType.ROOK, ChessGame.TeamColor.WHITE);
        board[0][1] = new ChessPieceImpl(ChessPiece.PieceType.KNIGHT, ChessGame.TeamColor.WHITE);
        board[0][2] = new ChessPieceImpl(ChessPiece.PieceType.BISHOP, ChessGame.TeamColor.WHITE);
        board[0][3] = new ChessPieceImpl(ChessPiece.PieceType.QUEEN, ChessGame.TeamColor.WHITE);
        board[0][4] = new ChessPieceImpl(ChessPiece.PieceType.KING, ChessGame.TeamColor.WHITE);
        board[0][5] = new ChessPieceImpl(ChessPiece.PieceType.BISHOP, ChessGame.TeamColor.WHITE);
        board[0][6] = new ChessPieceImpl(ChessPiece.PieceType.KNIGHT, ChessGame.TeamColor.WHITE);
        board[0][7] = new ChessPieceImpl(ChessPiece.PieceType.ROOK, ChessGame.TeamColor.WHITE);
        //white pawns
        for(int i=0; i<8; i++){
            board[1][i] = new ChessPieceImpl(ChessPiece.PieceType.PAWN, ChessGame.TeamColor.WHITE);
        }
    }

    private void resetBlackPieces(){
        board[7][0] = new ChessPieceImpl(ChessPiece.PieceType.ROOK, ChessGame.TeamColor.BLACK);
        board[7][1] = new ChessPieceImpl(ChessPiece.PieceType.KNIGHT, ChessGame.TeamColor.BLACK);
        board[7][2] = new ChessPieceImpl(ChessPiece.PieceType.BISHOP, ChessGame.TeamColor.BLACK);
        board[7][3] = new ChessPieceImpl(ChessPiece.PieceType.QUEEN, ChessGame.TeamColor.BLACK);
        board[7][4] = new ChessPieceImpl(ChessPiece.PieceType.KING, ChessGame.TeamColor.BLACK);
        board[7][5] = new ChessPieceImpl(ChessPiece.PieceType.BISHOP, ChessGame.TeamColor.BLACK);
        board[7][6] = new ChessPieceImpl(ChessPiece.PieceType.KNIGHT, ChessGame.TeamColor.BLACK);
        board[7][7] = new ChessPieceImpl(ChessPiece.PieceType.ROOK, ChessGame.TeamColor.BLACK);
        //black pawns
        for(int i=0; i<8; i++){
            board[6][i] = new ChessPieceImpl(ChessPiece.PieceType.PAWN, ChessGame.TeamColor.BLACK);
        }
    }
}
