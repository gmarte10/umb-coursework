package cs410.uno;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/* Tests the GameState class, by running multiple games with different parameters. It checks to see if they finish.
 */
class GameStateTest {
    @Test
    void runOneGame2() {
        GameState gameState = GameState.startGame(3, 5, 2,
                4, 5);
        gameState.runOneGame();
    }

    @Test
    void runOneGame() {
        // Run 10 different games to see if they all finish.
        GameState gameState = GameState.startGame(4, 5, 2, 4, 5);
        for (int i = 0; i < 10; i++) {
            gameState.runOneGame();
        }
        gameState = GameState.startGame(2, 10, 3, 2, 12);
        for (int i = 0; i < 10; i++) {
            gameState.runOneGame();
        }
    }
}