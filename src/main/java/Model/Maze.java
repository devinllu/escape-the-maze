package Model;

import java.util.LinkedList;
import java.util.Random;

// generates a random maze via Prim's Algorithm
// also generates all character components, later called by the Game class
public class Maze {

    public static final int WALL = 0;
    public static final int PASSAGE = 1;
    public static final int MOUSE = 2;
    public static final int CAT = 3;
    public static final int CHEESE = 4;

    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;


    private final int map[][];
    private final boolean textMap[][];
    private static final int COLUMN = 20;
    private static final int ROW = 15;

    public boolean[][] getTextMap() {
        return textMap;
    }

    // generate the maze via prim's algorithm
    public Maze(){
        this.map = new int[ROW][COLUMN];

        final LinkedList<int[]> frontiers = new LinkedList<>();
        final Random random = new Random();

        int row = 1;
        int column = 1;
        frontiers.add(new int[] {row, column, row, column});

        while (!frontiers.isEmpty()){
            final int[] f = frontiers.remove(random.nextInt(frontiers.size()));
            row = f[2];
            column = f[3];
            if (map[row][column] == WALL) {

                map[f[0]][f[1]] = map[row][column] = PASSAGE;

                if (row >= 3 && map[row - 2][column] == WALL) {
                    frontiers.add(new int[]{row - 1, column, row - 2, column});
                }

                if (column >= 3 && map[row][column - 2] == WALL) {
                    frontiers.add(new int[]{row, column - 1, row, column - 2});
                }

                if (row < ROW - 3 && map[row + 2][column] == WALL) {
                    frontiers.add(new int[]{row + 1, column, row + 2, column});
                }

                if (column < COLUMN - 3 && map[row][column + 2] == WALL) {
                    frontiers.add(new int[]{row, column + 1, row, column + 2});
                }

            }
        }


        textMap = new boolean[ROW][COLUMN];

        // hide the whole grid
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                textMap[i][j] = false;
            }
        }

        // show the initial position
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                textMap[i][j] = true;
            }
        }

        // show borders
        for (int i = 0; i < COLUMN; i++) {
            textMap[0][i] = true;
            textMap[ROW - 1][i] = true;
        }

        for (int i = 0; i < ROW; i++) {
            textMap[i][0] = true;
            textMap[i][COLUMN - 1] = true;
        }

    }

    public void showMaze() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                textMap[i][j] = true;
            }
        }
    }

    public boolean checkVisible(int r, int c) {
        return textMap[r][c];
    }

    public int getRow() {
        return COLUMN;
    }

    public int getColumn() {
        return ROW;
    }

    public boolean isPassage(int row, int column) {
        return map[row][column] == PASSAGE;
    }
    public boolean isMouse(int row, int column) {
        return map[row][column] == MOUSE;
    }
    public boolean isCat(int row, int column) {
        return map[row][column] == CAT;
    }
    public boolean isCheese(int row, int column) {
        return map[row][column] == CHEESE;
    }
    public boolean isWall(int row, int column) {
        return map[row][column] == WALL;
    }

    // checks for dead ends for cat movements
    // cats cannot backtrack unless there is a dead end
    public boolean isDeadEnd(int prevDir, int row, int col) {
        boolean deadEnd = false;

        switch (prevDir) {
            case NORTH:
                if (isWall(row, col - 1)) {
                    if (isWall(row - 1, col)) {
                        if (isWall(row, col + 1)) {
                            deadEnd = true;
                        }
                    }
                }
                break;

            case EAST:
                if (isWall(row + 1, col)) {
                    if (isWall(row - 1, col)) {
                        if (isWall(row, col + 1)) {
                            deadEnd = true;
                        }
                    }
                }
                break;

            case SOUTH:
                if (isWall(row, col - 1)) {
                    if (isWall(row + 1, col)) {
                        if (isWall(row, col + 1)) {
                            deadEnd = true;
                        }
                    }
                }
                break;

            case WEST:
                if (isWall(row, col - 1)) {
                    if (isWall(row - 1, col)) {
                        if (isWall(row + 1, col)) {
                            deadEnd = true;
                        }
                    }
                }
                break;
        }
        return deadEnd;
    }

    public boolean isWallFront(int dir, int row, int col) {
        boolean result = false;

        switch (dir) {
            case NORTH:
                if (isWall(row - 1, col)) {
                    result = true;
                }
                break;

            case EAST:
                if (isWall(row, col + 1)) {
                    result = true;
                }
                break;

            case SOUTH:
                if (isWall(row + 1, col)) {
                    result = true;
                }
                break;

            case WEST:
                if (isWall(row, col - 1)) {
                    result = true;
                }
                break;
        }

        return result;
    }


    public void setEmpty(int row, int column) {
        map[row][column] = PASSAGE;
    }

    public void setMouseLocation(Mouse mouse) {
        map[mouse.getRow()][mouse.getColumn()] = MOUSE;
    }
    public void setCatsLocation(CatsManager cats) {
        for(Cat cat: cats) {
            map[cat.getRow()][cat.getColumn()] = CAT;
        }
    }

    public void setCheeseLocation(Cheese cheese) {
        map[cheese.getRow()][cheese.getColumn()] = CHEESE;
    }

}