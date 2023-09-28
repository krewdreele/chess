package chess;

public class ChessPositionImpl implements ChessPosition{
    private int row;
    private int column;
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
}
