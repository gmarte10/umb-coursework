package cs410.uno;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/* This class tests the components of the initializer class.
 */
class InitializerTest {

    @Test
    void discardPile() {
        Initializer initializer = new Initializer(3, 1, 1, 1, 1);
        assertEquals(1, initializer.discardPile().size());
    }

    @Test
    void drawPile() {
        Initializer initializer = new Initializer(3, 3, 1, 1, 1);
        int drawSize = 43;
        assertEquals(drawSize, initializer.drawPile().size());
    }

    @Test
    void players() {
        Initializer initializer = new Initializer(3, 3, 1, 1, 1);
        assertEquals(3, initializer.players().size());
    }

    @Test
    void currentPlayer() {
        Initializer initializer = new Initializer(3, 3, 4, 2, 10);
        assertEquals(0, initializer.currentPlayer());
    }

    @Test
    void reverse() {
        Initializer initializer = new Initializer(3, 3, 4, 2, 10);
        assertFalse(initializer.reverse());
    }
}