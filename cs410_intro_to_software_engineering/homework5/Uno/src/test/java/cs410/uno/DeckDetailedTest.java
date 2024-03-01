package cs410.uno;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* This class is used for testing the Deck class and it's components.
 */
public class DeckDetailedTest {
    @Test
    void digits() {
        // Test a deck with zero to two digit cards.
        DeckDetailed unoDeck = new DeckDetailed(1, 0, 0);
        ArrayList<ICard> zeroToTwo = new ArrayList<>();
        zeroToTwo.add(new NormalCard("Blue", new Face(0)));
        zeroToTwo.add(new NormalCard("Blue", new Face(1)));
        zeroToTwo.add(new NormalCard("Blue", new Face(2)));
        zeroToTwo.add(new NormalCard("Red", new Face(0)));
        zeroToTwo.add(new NormalCard("Red", new Face(1)));
        zeroToTwo.add(new NormalCard("Red", new Face(2)));
        zeroToTwo.add(new NormalCard("Green", new Face(0)));
        zeroToTwo.add(new NormalCard("Green", new Face(1)));
        zeroToTwo.add(new NormalCard("Green", new Face(2)));
        zeroToTwo.add(new NormalCard("Yellow", new Face(0)));
        zeroToTwo.add(new NormalCard("Yellow", new Face(1)));
        zeroToTwo.add(new NormalCard("Yellow", new Face(2)));
        assertEquals(zeroToTwo, unoDeck.zeroToTwoDigits());
    }

    @Test
    void deck() {
        // Check the sizes of digit, special and wild cards of a deck.
        DeckDetailed unoDeck = new DeckDetailed(1, 1, 1);
        assertEquals(40, unoDeck.digits().size());
        assertEquals(12, unoDeck.specials().size());
        assertEquals(1, unoDeck.wilds().size());
        assertEquals(53, unoDeck.deck().size());
    }
}
