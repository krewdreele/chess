package chess;

public class ChessBoardImpl implements ChessBoard {

    private ChessPiece[][] pieces;

    public ChessBoardImpl(){
        pieces = new ChessPiece[8][8];
    }

    public ChessBoardImpl(ChessBoardImpl other){
        pieces = new ChessPiece[8][8];
        for(int i=1; i<=8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition position = new ChessPositionImpl(i, j);
                ChessPiece piece = other.getPiece(position);
                addPiece(position, piece);
            }
        }
    }
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        pieces[position.getRow()-1][position.getColumn()-1] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return pieces[position.getRow()-1][position.getColumn()-1];
    }

    @Override
    public void resetBoard() {
        pieces = new ChessPiece[8][8];
        resetWhitePieces();
        resetBlackPieces();
    }

    public void movePiece(ChessMove move){
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        ChessPiece piece = getPiece(move.getStartPosition());

        if(move.getPromotionPiece() != null){
            piece = switch (move.getPromotionPiece()){
                case ROOK -> new Rook(piece.getTeamColor());
                case KNIGHT -> new Knight(piece.getTeamColor());
                case QUEEN -> new Queen(piece.getTeamColor());
                case BISHOP -> new Bishop(piece.getTeamColor());
                default -> throw new IllegalStateException("Promotion type cannot be of type: " + move.getPromotionPiece());
            };
        }
        pieces[start.getRow() - 1][start.getColumn() - 1] = null;
        pieces[end.getRow() - 1][end.getColumn() - 1] = piece;
    }

    private void resetWhitePieces(){
        pieces[0][0] = new Rook(ChessGame.TeamColor.WHITE);
        pieces[0][1] = new Knight(ChessGame.TeamColor.WHITE);
        pieces[0][2] = new Bishop(ChessGame.TeamColor.WHITE);
        pieces[0][3] = new Queen(ChessGame.TeamColor.WHITE);
        pieces[0][4] = new King(ChessGame.TeamColor.WHITE);
        pieces[0][5] = new Bishop(ChessGame.TeamColor.WHITE);
        pieces[0][6] = new Knight(ChessGame.TeamColor.WHITE);
        pieces[0][7] = new Rook(ChessGame.TeamColor.WHITE);
        //white pawns
        for(int i=0; i<8; i++){
            pieces[1][i] = new Pawn(ChessGame.TeamColor.WHITE);
        }
    }

    private void resetBlackPieces(){
        pieces[7][0] = new Rook(ChessGame.TeamColor.BLACK);
        pieces[7][1] = new Knight(ChessGame.TeamColor.BLACK);
        pieces[7][2] = new Bishop(ChessGame.TeamColor.BLACK);
        pieces[7][3] = new Queen(ChessGame.TeamColor.BLACK);
        pieces[7][4] = new King(ChessGame.TeamColor.BLACK);
        pieces[7][5] = new Bishop(ChessGame.TeamColor.BLACK);
        pieces[7][6] = new Knight(ChessGame.TeamColor.BLACK);
        pieces[7][7] = new Rook(ChessGame.TeamColor.BLACK);
        //black pawns
        for(int i=0; i<8; i++){
            pieces[6][i] = new Pawn(ChessGame.TeamColor.BLACK);
        }
    }
}
