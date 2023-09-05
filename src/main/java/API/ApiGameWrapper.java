package API;

import Model.Game;

public class ApiGameWrapper {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int numCheeseFound;
    public int numCheeseGoal;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiGameWrapper makeFromGame(Game game, int id) {
        ApiGameWrapper wrapper = new ApiGameWrapper();
        wrapper.gameNumber = id;
        wrapper.isGameWon = game.isWin();
        wrapper.isGameLost = game.isEaten();
        wrapper.numCheeseFound = game.getScore();
        wrapper.numCheeseGoal = game.getMaxCheese();

        // Populate this object!

        return wrapper;
    }
}