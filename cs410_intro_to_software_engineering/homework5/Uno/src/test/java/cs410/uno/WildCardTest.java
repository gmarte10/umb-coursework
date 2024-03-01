package cs410.uno;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/* This class tests the components of the WildCard class.
 */
class WildCardTest {

    @Test
    void setColor() {
        WildCard wildCard = new WildCard();
        wildCard.setColor("Yellow");
        assertEquals("Yellow", wildCard.color());
    }

    @Test
    void color() {
        WildCard wildCard = new WildCard();
        assertEquals("No Color", wildCard.color());
        wildCard.setColor("Yellow");
        assertEquals("Yellow", wildCard.color());
    }

    @Test
    void testToString() {
        WildCard wildCard = new WildCard();
        wildCard.setColor("Green");
        String w = "WildCard -> Color: Green\n";
        assertEquals(w, wildCard.toString());
    }

    @Test
    void testEquals() {
        WildCard wildCard = new WildCard();
        WildCard wildCard1 = new WildCard();
        assertTrue(wildCard1.equals(wildCard));
        wildCard1.setColor("Blue");
        assertFalse(wildCard1.equals(wildCard));
    }
}