package cs410.uno;

import org.junit.jupiter.api.Test;

import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;

/* Directly tests the Deck class. Only checks for sizes.
 */
class DeckTest {

    @Test
    void deck() {
        Deck unoDeck = new Deck(0, 0, 0);
        assertEquals(40, unoDeck.deck().size());

        unoDeck = new Deck(0, 1, 0);
        assertEquals(52, unoDeck.deck().size());

        unoDeck = new Deck(1, 0, 1);
        assertEquals(41, unoDeck.deck().size());
    }
}