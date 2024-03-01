package cs410.uno;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

/* This class runs one simple game until completion. It checks the discard pile, draw pile, players, current
   player index and reverse state when a runOneTurn is called.
 */
public class GameStateManualTest {
    @Test
    void runOneTurn() {
        ArrayList<ICard> discardPile = new ArrayList<>();
        ArrayList<ICard> drawPile = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        int currentPlayer = 0;
        boolean reverse = false;
        ICard digitCard1 = new NormalCard("Blue", new Face(9));
        ICard digitCard2 = new NormalCard("Red", new Face(2));
        ICard digitCard3 = new NormalCard("Green", new Face(3));
        ICard digitCard4 = new NormalCard("Yellow", new Face(1));
        ICard digitCard5 = new NormalCard("Blue", new Face(0));
        ICard digitCard6 = new NormalCard("Red", new Face(8));
        ICard digitCard7 = new NormalCard("Green", new Face(4));
        ICard digitCard8 = new NormalCard("Yellow", new Face(5));

        ArrayList<ICard> hand1 = new ArrayList<>();
        hand1.add(digitCard1);

        ArrayList<ICard> hand2 = new ArrayList<>();
        hand2.add(digitCard2);

        Player player1 = new Player(hand1);
        Player player2 = new Player(hand2);

        discardPile.add(digitCard6);
        drawPile.add(digitCard7);
        drawPile.add(digitCard8);
        drawPile.add(digitCard2);
        drawPile.add(digitCard1);
        drawPile.add(digitCard3);

        players.add(player1);
        players.add(player2);

        GameStateManual gameStateManual = GameStateManual.startGame(discardPile, drawPile, players, currentPlayer, reverse);
        gameStateManual.runOneTurn();
        assertEquals(discardPile, gameStateManual.discardPile());
        ICard draw = drawPile.remove(drawPile.size() - 1);
        assertEquals(drawPile, gameStateManual.drawPile());
        players.get(currentPlayer).addCardToHand(draw);
        assertEquals(players, gameStateManual.players());
        assertEquals(1, gameStateManual.currentPlayer());
        assertEquals(false, gameStateManual.isReverse());
        gameStateManual.runOneTurn();
    }
}
