package tictactoe.player;

public class PlayerFactory {
    /**
    * Factory method to create new player for Tic-Tac-Toe game
    *
    * @param p - String representation of player to be created
    * @return new Player object
    */
    public static Player makePlayer (String p) {
        switch (p) {
            case "easy":
                return new Easy();
            case "medium":
                return new Medium();
            case "hard":
                return new Hard();
            default:
                return new User();
        }
    }
}
