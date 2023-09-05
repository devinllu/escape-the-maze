package API;

import Model.Cat;
import Model.CatsManager;
import Model.Game;

import java.util.List;

public class ApiBoardWrapper {
    // width == col
    public int boardWidth;
    // height == row
    public int boardHeight;
    public ApiLocationWrapper mouseLocation;
    public ApiLocationWrapper cheeseLocation;
    public List<ApiLocationWrapper> catLocations;
    public boolean[][] hasWalls;
    public boolean[][] isVisible;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    // API wrapper classes
    public static ApiBoardWrapper makeFromGame(Game game) {
        ApiBoardWrapper wrapper = new ApiBoardWrapper();

        wrapper.boardWidth = game.getMaze().getRow();
        wrapper.boardHeight = game.getMaze().getColumn();
        wrapper.mouseLocation = ApiLocationWrapper.makeFromCellLocation(game.getMouse().getRow(), game.getMouse().getColumn());
        wrapper.cheeseLocation = ApiLocationWrapper.makeFromCellLocation(game.getCheese().getRow(), game.getCheese().getColumn());
        wrapper.catLocations = ApiLocationWrapper.makeFromCellLocations(game.getCatsManager());

        int totalRow = game.getMaze().getColumn();
        int totalCol = game.getMaze().getRow();

        wrapper.hasWalls = new boolean[totalRow][totalCol];
        wrapper.isVisible = new boolean[totalRow][totalCol];

        // sets the visibility of the walls
        // and sets the walls/empty space into position
        for (int i = 0; i < totalRow; i++) {
            for (int j = 0; j < totalCol; j++) {

                wrapper.isVisible[i][j] = game.getMaze().checkVisible(i, j);

                if (game.getMaze().isWall(i, j)) {
                    wrapper.hasWalls[i][j] = true;
                } else {
                    wrapper.hasWalls[i][j] = false;
                }
            }
        }


        // Populate this object!

        return wrapper;
    }
}