package chess;

/**
 * (1-8)
 */
public class ChessPositionImpl implements ChessPosition{
    private final int row;
    private final int column;
    public ChessPositionImpl(int row, int column){
        this.row = row;
        this.column = column;
    }
    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object other){
        if(other == null) return false;
        if(other.getClass() != this.getClass()) return false;
        return ((ChessPositionImpl) other).getRow() == getRow() && ((ChessPositionImpl) other).getColumn() == getColumn();
    }
}
