package Model;

import UI.TextUI;

// main class managed by textUI and game class
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        TextUI ui = new TextUI(game);
        ui.start();
    }
}
