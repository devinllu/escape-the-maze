package UI;

import Model.Game;
import Model.Maze;

import java.util.Scanner;

// NOTE: for this assignment, TextUI class is not needed AT ALL
// please ignore

// outputs UI information
// calls Game class to manage the data
public class TextUI {
    private Game game;
    private static final char PASSAGE = ' ';
    private static final char WALL = '#';
    private static final char CAT = '!';
    private static final char CHEESE = '$';
    private static final char UNSEEN = '.';
    private static final char DEAD = 'X';

    // cannot make final, since we need to set
    // the mouse to an 'X' if they lose
    private static char MOUSE = '@';


    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;

    private static boolean cheatCodeShow = false;

    private static final int ROW = 15;
    private static final int COLUMN = 20;

    // keeps track of which values to display or hide
    private boolean textMap[][];

    private Scanner input = new Scanner(System.in);

    public TextUI(Game game) {
        this.game = game;
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

    public void start() {
        printGreeting();
        printHelp();
        boolean isPlaying = true;

        show();

        while (isPlaying) {
            char userInput = checkInput();

            switch (userInput) {
                case 'W':
                    game.moveMouse(NORTH);

                    if (!game.isEaten()) {
                        updateUI(game.getMouse().getRow(), game.getMouse().getColumn());
                        game.updateGameState();
                        show();
                    }

                    break;

                case 'A':
                    game.moveMouse(WEST);

                    if (!game.isEaten()) {
                        updateUI(game.getMouse().getRow(), game.getMouse().getColumn());
                        game.updateGameState();
                        show();
                    }

                    break;

                case 'S':
                    game.moveMouse(SOUTH);
                    if (!game.isEaten()) {
                        updateUI(game.getMouse().getRow(), game.getMouse().getColumn());
                        game.updateGameState();
                        show();
                    }

                    break;

                case 'D':
                    game.moveMouse(EAST);
                    if (!game.isEaten()) {
                        updateUI(game.getMouse().getRow(), game.getMouse().getColumn());
                        game.updateGameState();
                        show();
                    }

                    break;

                case 'C':

                    if(game.getScore() == 0){
                        game.setMaxCheeseToOne();
                    }

                    break;

                case 'M':

                    cheatCodeShow = true;
                    show();
                    break;

                case '?':

                    printHelp();
                    break;
            }

            if (game.isEaten()) {

                printGameOver();
                isPlaying = false;
                cheatCodeShow = true;
                MOUSE = DEAD;
                show();


            } else if (game.isWin()) {
                printCongratulations();
                isPlaying = false;
                cheatCodeShow = true;
                show();
            }


        }
    }

    private char checkInput() {

        // char c will be overwritten, just initializing variable
        char c = 'a';
        boolean loop = true;
        int row = game.getMouse().getRow();
        int column = game.getMouse().getColumn();

        String errorMsg = "Invalid move: you cannot move through walls!";


        while (loop) {

            System.out.print("Enter your move [WASD?]:");
            c = input.next().charAt(0);
            c = Character.toUpperCase(c);


            if (c == 'W' || c == 'A' || c == 'S' || c == 'D' || c == 'M' || c == 'C' || c == '?') {
                switch (c) {
                    case 'W':

                        if (!game.maze.isWallFront(NORTH, row, column)) {
                            loop = false;
                        } else {
                            System.out.println(errorMsg);
                        }
                        break;

                    case 'A':

                        if (!game.maze.isWallFront(WEST, row, column)) {
                            loop = false;
                        } else {
                            System.out.println(errorMsg);
                        }
                        break;

                    case 'S':

                        if (!game.maze.isWallFront(SOUTH, row, column)) {
                            loop = false;
                        } else {
                            System.out.println(errorMsg);
                        }
                        break;

                    case 'D':

                        if (!game.maze.isWallFront(EAST, row, column)) {
                            loop = false;
                        } else {
                            System.out.println(errorMsg);
                        }
                        break;

                    case 'M':
                    case 'C':
                    case '?':

                        loop = false;
                        break;

                }
            }

            else {
                System.out.println("Invalid move. Please enter just A (left), S (down), D (right), or W (up).");
            }
        }

        return c;

    }

    private void printGreeting() {
        System.out.println("----------------------------------------\n" +
                "Welcome to Cat and Mouse Maze Adventure!\n" +
                "by Devin & Hoang\n" +
                "----------------------------------------");
    }

    private void printHelp() {
        System.out.println("DIRECTIONS:\n" +
                "\tFind 5 cheese before a cat eats you!\n" +
                "LEGEND:\n" +
                "\t#: Wall\n" +
                "\t@: You (a mouse)\n" +
                "\t!: Cat\n" +
                "\t$: Cheese\n" +
                "\t.: Unexplored space\n" +
                "MOVES:\n" +
                "\tUse W (up), A (left), S (down) and D (right) to move.\n" +
                "\t(You must press enter after each move).\n");
    }


    private void printScore() {
        System.out.println("Cheese collected: " + game.getScore() +
                " of " + game.getMaxCheese());
    }

    private void printCongratulations() {
        System.out.println("Congratulations! You won!\n");
    }
    private void printGameOver() {
        System.out.println("I'm sorry, you have been eaten!\n"+
                "GAME OVER, please try again.");
    }


    private void updateUI(int r, int c) {
        // updates all 8 directions of the user movement
        for (int i = r - 1; i < (r + 2); i++) {
            for (int j = c - 1; j < (c + 2); j++) {
                textMap[i][j] = true;
            }
        }
    }




    private void show() {
        if (cheatCodeShow) {
            System.out.println("Maze:\n" + drawWholeMaze());
            printScore();

        } else {
            System.out.println("Maze:\n" + drawUnexploredMaze());
            printScore();
        }

    }

    private String drawWholeMaze(){
        Maze map = game.getMaze();
        final StringBuffer b = new StringBuffer();
        for (int row = 0; row < map.getColumn(); row++){
            for (int column = 0; column < map.getRow(); column++) {
                if ( map.isMouse(row, column)) {
                    b.append(MOUSE);
                }

                if ( map.isCat(row, column)) {
                    b.append(CAT);
                }

                if ( map.isCheese(row, column)) {
                    b.append(CHEESE);
                }

                if ( map.isWall(row, column)) {
                    b.append(WALL);
                }

                if ( map.isPassage(row, column)) {
                    b.append(PASSAGE);
                }

            }
            b.append('\n');

        }
        return b.toString();
    }

    private String drawUnexploredMaze() {
        final StringBuffer b = new StringBuffer();
        Maze map = game.getMaze();

        for (int i = 0; i < ROW; i++) {

            for (int j = 0; j < COLUMN; j++) {

                if (!textMap[i][j] && (!map.isCat(i, j) && !map.isCheese(i, j)) ) {
                    b.append(UNSEEN);

                } else {
                    b.append(retCellValue(i, j));
                }
            }

            b.append('\n');
        }

        return b.toString();
    }

    private char retCellValue(int row, int column) {
        Maze map = game.getMaze();
        char c = '/';

        if (map.isMouse(row, column)) {
            c = MOUSE;
        }

        if (map.isCat(row, column)) {
            c = CAT;
        }

        if (map.isCheese(row, column)) {
            c = CHEESE;
        }

        if (map.isWall(row, column)) {
            c = WALL;
        }

        if (map.isPassage(row, column)) {
            c = PASSAGE;
        }

        return c;
    }

}
