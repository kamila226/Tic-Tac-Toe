package tictactoe;

public class Move {
    private int row, column;
    public Move(int row, int column) {
        this.row = row;
        this.column = column;
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setColumn(int col) {
        this.column = col;
    }
}
