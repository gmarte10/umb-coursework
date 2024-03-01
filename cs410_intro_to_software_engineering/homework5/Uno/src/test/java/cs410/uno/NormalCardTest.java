package cs410.uno;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/* This class checks the components of the NormalCard class.
 */
class NormalCardTest {
    @Test
    void isCardPlayable() {
        ICard card = new NormalCard("Blue", new Face(9));
        ICard discard = new NormalCard("Blue", new Face("Skip"));
        assertTrue(discard.isCardPlayable(card));

        card = new WildCard();
        discard = new NormalCard("Green", new Face(0));
        assertTrue(discard.isCardPlayable(card));

        card = new NormalCard("Blue", new Face("Reverse"));
        discard = new NormalCard("Green", new Face("Skip"));
        assertFalse(discard.isCardPlayable(card));

        card = new NormalCard("Green", new Face("Reverse"));
        discard = new NormalCard("Green", new Face("Skip"));
        assertTrue(discard.isCardPlayable(card));

        card = new NormalCard("Yellow", new Face(0));
        discard = new NormalCard("Red", new Face(0));
        assertTrue(discard.isCardPlayable(card));

        card = new NormalCard("Blue", new Face("Reverse"));
        discard = new NormalCard("Green", new Face("Reverse"));
        assertTrue(discard.isCardPlayable(card));

        card = new WildCard();
        discard = new NormalCard("Blue", new Face(9));
        assertTrue(discard.isCardPlayable(card));

        discard = new WildCard();
        ((WildCard) discard).setColor("Blue");
        card = new NormalCard("Blue", new Face(9));
        assertTrue(discard.isCardPlayable(card));
    }

    @Test
    void color() {
        Face face = new Face(3);
        NormalCard normalCard = new NormalCard("Blue", face);
        assertEquals("Blue", normalCard.color());
    }

    @Test
    void face() {
        Face fd = new Face(3);
        Face fi = new Face("Add Two");
        NormalCard nd = new NormalCard("Red", fd);
        NormalCard ni = new NormalCard("Yellow", fi);
        assertEquals("Add Two", ni.face().instruction().get());
        assertEquals(3, nd.face().digit().get());
    }

    @Test
    void testToString() {
        Face fd = new Face(3);
        Face fi = new Face("Add Two");
        NormalCard nd = new NormalCard("Red", fd);
        NormalCard ni = new NormalCard("Yellow", fi);
        String d = "NormalCard -> Color: Red | Digit: 3\n";
        String i = "NormalCard -> Color: Yellow | Instruction: Add Two\n";
        assertEquals(i, ni.toString());
        assertEquals(d, nd.toString());
    }

    @Test
    void testEquals() {
        Face fd = new Face(3);
        Face fd2 = new Face(3);
        Face fi = new Face("Add Two");
        Face fi2 = new Face("Add Two");
        NormalCard nd = new NormalCard("Red", fd);
        NormalCard nd2 = new NormalCard("Red", fd2);
        NormalCard ni = new NormalCard("Yellow", fi);
        NormalCard ni2 = new NormalCard("Yellow", fi2);
        Face fd3 = new Face(5);
        Face fi3 = new Face("Reverse");
        NormalCard nd3 = new NormalCard("Red", fd3);
        NormalCard ni3 = new NormalCard("Yellow", fi3);
        assertTrue(nd.equals(nd2));
        assertEquals(ni, ni2);
        assertNotEquals(nd, nd3);
        assertFalse(ni.equals(ni3));
    }
}