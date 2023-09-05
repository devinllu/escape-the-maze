package Model;

// a single instance of cheese, managed later by Game
public class Cheese {
    private int row;
    private int column;
    public Cheese(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
}