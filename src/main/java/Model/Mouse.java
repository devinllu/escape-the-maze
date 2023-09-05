package Model;

// player character, also handled by game class
public class Mouse {
    private int row;
    private int column;

    public Mouse() {
        row = 1;
        column = 1;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}