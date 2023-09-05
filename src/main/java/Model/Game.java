package Model;

import java.util.Random;


// handles all the other main classes, i.e. Cheese, CatManager, etc
// gets called by the TextUI class
public class Game {
    public Maze maze;
    private CatsManager catsManager;
    private Mouse mouse;
    private Cheese cheese;

    private int score = 0;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cheese getCheese() {
        return cheese;
    }

    private int maxCheese = 5;

    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;

    public CatsManager getCatsManager() {
        return catsManager;
    }

    private static final int WEST = 3;

    public Game() {
        // Setup the maze
        maze = new Maze();
        catsManager = new CatsManager();
        mouse = new Mouse();
        populateCheese();
        populateCats();
        maze.setMouseLocation(mouse);
    }

    private void populateCheese(){
        int column = 0;
        int row = 0;
        while (!maze.isPassage(row,column)) {
            final Random random = new Random();
            row = random.nextInt(maze.getColumn() - 1) + 1;
            column = random.nextInt(maze.getRow() - 2) + 1;
        }
        cheese = new Cheese(row,column);
        maze.setCheeseLocation(cheese);
    }

    private void populateCats() {
        catsManager.add(new Cat(1, maze.getRow() - 2));
        catsManager.add(new Cat(maze.getColumn() - 2, maze.getRow() - 2));
        catsManager.add(new Cat(maze.getColumn() - 2,1));
        maze.setCatsLocation(catsManager);
    }

    // move the cats and spawns new cheese if necessary
    public void updateGameState() {
        if(!isEaten() || !isWin()) {
            moveCats();
            spawnNewCheese();
        }
    }

    public boolean isWin() {
        return score == maxCheese;
    }

    public boolean isEaten() {
        for (Cat cat : catsManager){
            if(cat.getRow() == mouse.getRow() && cat.getColumn() == mouse.getColumn()) {
                return true;
            }
        }
        return false;
    }

    // checks for dead end first, -> if true then cat is allowed to backtrack
    // otherwise it must move randomly in 3 possible directions
    public void moveCats() {
        for (Cat cat : catsManager) {
            Random rand = new Random();
            int dir = rand.nextInt(4);
            int prevDir = cat.getDirection();
            int row = cat.getRow();
            int col = cat.getColumn();

            if (maze.isDeadEnd(prevDir, row, col)) {
                switch (prevDir) {
                    case NORTH:
                        setEmptyForCats(row, col);

                        cat.setLocation(row + 1, col);
                        cat.setDirection(SOUTH);
                        break;
                    case EAST:
                        setEmptyForCats(row, col);
                        cat.setLocation(row, col - 1);
                        cat.setDirection(WEST);
                        break;
                    case SOUTH:
                        setEmptyForCats(row, col);
                        cat.setLocation(row - 1, col);
                        cat.setDirection(NORTH);
                        break;
                    case WEST:
                        setEmptyForCats(row, col);
                        cat.setLocation(row, col + 1);
                        cat.setDirection(EAST);
                        break;
                }
                maze.setCatsLocation(catsManager);


            } else {
                // prevents cats from backtracking, and from hitting a wall
                while (((Math.abs(dir - prevDir) == 2) || (maze.isWallFront(dir, row, col)))) {
                    dir = rand.nextInt(4);
                }

                switch (dir) {
                    case NORTH:
                        setEmptyForCats(row, col);
                        cat.setLocation(row - 1, col);
                        cat.setDirection(dir);
                        break;
                    case EAST:
                        setEmptyForCats(row, col);
                        cat.setLocation(row, col + 1);
                        cat.setDirection(dir);
                        break;
                    case SOUTH:
                        setEmptyForCats(row, col);
                        cat.setLocation(row + 1, col);
                        cat.setDirection(dir);
                        break;
                    case WEST:
                        setEmptyForCats(row, col);
                        cat.setLocation(row, col - 1);
                        cat.setDirection(dir);
                        break;

                }
            }

        }


        maze.setCatsLocation(catsManager);
    }

    // only sets an empty spot if there isn't a mouse or cheese
    // prevents the UI from erasing the mouse/cheese position
    private void setEmptyForCats(int row, int column){
        if (!(row == cheese.getRow() && column == cheese.getColumn()) && !(row == mouse.getRow() && column == mouse.getColumn())) {
            maze.setEmpty(row, column);
        }
        else {
            maze.setCheeseLocation(cheese);
        }
    }


    public Maze getMaze() {
        return maze;
    }

    public Mouse getMouse() { return mouse; }

    public void moveMouse(int dir) {
        switch (dir) {
            case NORTH:
                maze.setEmpty(mouse.getRow(), mouse.getColumn());
                mouse.setRow(mouse.getRow() - 1);
                maze.setMouseLocation(mouse);
                break;
            case SOUTH:
                maze.setEmpty(mouse.getRow(), mouse.getColumn());
                mouse.setRow(mouse.getRow() + 1);
                maze.setMouseLocation(mouse);
                break;
            case EAST:
                maze.setEmpty(mouse.getRow(), mouse.getColumn());
                mouse.setColumn(mouse.getColumn() + 1);
                maze.setMouseLocation(mouse);
                break;
            case WEST:
                maze.setEmpty(mouse.getRow(), mouse.getColumn());
                mouse.setColumn(mouse.getColumn() - 1);
                maze.setMouseLocation(mouse);
                break;
        }

    }

    private void spawnNewCheese() {
        if (isCheeseEaten() && !isWin()) {
            score++;
            populateCheese();
        }
    }

    public int getScore() {
        return score;
    }

    public int getMaxCheese() {
        return maxCheese;
    }

    public void setMaxCheeseToOne() {
        maxCheese = 1;
    }

    public boolean isCheeseEaten() {
        return (mouse.getRow() == cheese.getRow() && mouse.getColumn() == cheese.getColumn());
    }



    // new code for the web app
    public boolean validInput(String move) {

        // char c will be overwritten, just initializing variable
        int row = getMouse().getRow();
        int column = getMouse().getColumn();


        switch (move) {
            case "MOVE_UP":

                if (!maze.isWallFront(NORTH, row, column)) {
                } else {
                    return false;
                }
                break;

            case "MOVE_LEFT":

                if (!maze.isWallFront(WEST, row, column)) {
                } else {
                    return false;
                }
                break;

            case "MOVE_DOWN":

                if (!maze.isWallFront(SOUTH, row, column)) {
                } else {
                    return false;
                }
                break;

            case "MOVE_RIGHT":

                if (!maze.isWallFront(EAST, row, column)) {
                } else {
                    return false;
                }
                break;

            case "SHOW_ALL":
            case "1_CHEESE":

                break;

        }




        return true;

    }

    public void updateUI(int r, int c) {
        boolean[][] textMap = getMaze().getTextMap();
        // updates all 8 directions of the user movement
        for (int i = r - 1; i < (r + 2); i++) {
            for (int j = c - 1; j < (c + 2); j++) {
                textMap[i][j] = true;
            }
        }
    }


}