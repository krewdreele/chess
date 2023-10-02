package chess;

public class ChessMoveImpl implements ChessMove{

    private final ChessPosition start;
    private final ChessPosition end;
    private final ChessPiece.PieceType promotionPiece;

    public ChessMoveImpl(ChessPosition start, ChessPosition end, ChessPiece.PieceType promotionPiece){
        this.start = start;
        this.end = end;
        this.promotionPiece = promotionPiece;
    }
    @Override
    public ChessPosition getStartPosition() {
        return start;
    }

    @Override
    public ChessPosition getEndPosition() {
        return end;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }

    @Override
    public boolean equals(Object other){
        if(other == null) return false;
        if(other.getClass() != this.getClass()) return false;
        return ((ChessMoveImpl) other).getStartPosition().equals(getStartPosition()) &&
                ((ChessMoveImpl) other).getEndPosition().equals(getEndPosition());
    }

    @Override
    public int hashCode(){
        return getStartPosition().getRow() * getEndPosition().getColumn();
    }
}
