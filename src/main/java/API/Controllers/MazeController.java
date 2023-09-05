package API.Controllers;

import API.ApiBoardWrapper;
import API.ApiGameWrapper;
import Model.Game;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// Maze Controller
// handles all inputs from client and updates model code
@RestController
public class MazeController {
    private List<ApiGameWrapper> games = new ArrayList<>();
    private List<Game> gamesList = new ArrayList<>();
    AtomicInteger nextID = new AtomicInteger(0);


    // part 1
    @GetMapping("/api/about")
    public String getName() {
        return "Devin Lu";
    }


    // games
    @GetMapping("/api/games")
    public List<ApiGameWrapper> getGames() {
        return games;
    }

    @PostMapping("/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiGameWrapper postGame() {
        Game game = new Game();
        // storing the id to be the index in the array
        int gameID = nextID.incrementAndGet() - 1;
        game.setId(gameID);

        ApiGameWrapper wrapper = ApiGameWrapper.makeFromGame(game, gameID);

        games.add(wrapper);
        gamesList.add(game);

        return wrapper;

    }

    @GetMapping("/api/games/{id}")
    public ApiGameWrapper getGame1(@PathVariable("id") int id) {
        for (ApiGameWrapper wrapper : games) {
            if (wrapper.gameNumber == id) {
                return wrapper;
            }
        }

        throw new FileNotFoundException();
    }


    // board
    @GetMapping("/api/games/{id}/board")
    public ApiBoardWrapper getCurrentBoard(@PathVariable("id") int id) {

        if (id >= games.size()) {
            throw new FileNotFoundException();
        }

        Game game = gamesList.get(id);
        ApiBoardWrapper boardWrapper = ApiBoardWrapper.makeFromGame(game);
        return boardWrapper;

    }

    // moves
    @PostMapping("/api/games/{id}/moves")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postMoves(@RequestBody String move, @PathVariable("id") int id) {

        if (id >= games.size()) {
            throw new FileNotFoundException();
        }

        Game game = gamesList.get(id);
        ApiGameWrapper wrapper = games.get(id);
        boolean valid = game.validInput(move);

        if (!valid) {
            throw new BadRequestException();
        }


        switch (move) {
            case "MOVE_UP":
                game.moveMouse(0);
                game.updateGameState();
                wrapper.numCheeseFound = game.getScore();
                game.updateUI(game.getMouse().getRow(), game.getMouse().getColumn());
                break;
            case "MOVE_DOWN":
                game.moveMouse(2);
                game.updateGameState();
                wrapper.numCheeseFound = game.getScore();
                game.updateUI(game.getMouse().getRow(), game.getMouse().getColumn());

                break;
            case "MOVE_LEFT":
                game.moveMouse(3);
                game.updateGameState();
                wrapper.numCheeseFound = game.getScore();
                game.updateUI(game.getMouse().getRow(), game.getMouse().getColumn());

                break;
            case "MOVE_RIGHT":
                game.moveMouse(1);
                game.updateGameState();
                wrapper.numCheeseFound = game.getScore();
                game.updateUI(game.getMouse().getRow(), game.getMouse().getColumn());

                break;
            case "MOVE_CATS":
                game.moveCats();
                break;

        }


        wrapper.isGameWon = game.isWin();
        wrapper.isGameLost = game.isEaten();

    }

    @PostMapping("/api/games/{id}/cheatstate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postCheats(@RequestBody String cheats, @PathVariable("id") int id) {

        if (id >= games.size()) {
            throw new FileNotFoundException();
        }

        Game game = gamesList.get(id);
        ApiGameWrapper wrapper = games.get(id);

        switch (cheats) {
            case "1_CHEESE":
                game.setMaxCheeseToOne();
                wrapper.numCheeseGoal = 1;
                break;
            case "SHOW_ALL":
                game.getMaze().showMaze();
                break;
            default:
                throw new BadRequestException();
        }
    }


    // error handling
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "file not found")
    public static class FileNotFoundException extends RuntimeException {

    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid request")
    public static class BadRequestException extends RuntimeException {

    }


}
