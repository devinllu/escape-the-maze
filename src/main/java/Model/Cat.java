package Model;

// handles one individual cat
// same model as Assignment 3
public class Cat {
    private int row;
    private int column;

    // setting an initial random direction, doesn't matter what it's set to
    private int direction = 1;

    public Cat(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int dir) {
        direction = dir;
    }

    public void setLocation(int row, int column) {
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