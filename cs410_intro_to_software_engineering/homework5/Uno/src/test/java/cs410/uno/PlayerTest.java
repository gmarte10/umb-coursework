package cs410.uno;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void playableCard() {
        ArrayList<ICard> hand = new ArrayList<>();
        NormalCard digitCard1 = new NormalCard("Blue", new Face(9));
        NormalCard digitCard2 = new NormalCard("Red", new Face(2));
        NormalCard digitCard3 = new NormalCard("Green", new Face(3));
        NormalCard digitCard4 = new NormalCard("Yellow", new Face(1));
        NormalCard digitCard5 = new NormalCard("Blue", new Face(0));
        NormalCard digitCard6 = new NormalCard("Red", new Face(8));
        NormalCard digitCard7 = new NormalCard("Green", new Face(4));
        NormalCard digitCard8 = new NormalCard("Yellow", new Face(5));
        hand.add(digitCard1);
        hand.add(digitCard2);
        hand.add(digitCard3);
        hand.add(digitCard4);
        hand.add(digitCard5);
        hand.add(digitCard6);
        hand.add(digitCard7);
        hand.add(digitCard8);
        Player player = new Player(hand);
        ICard discard = digitCard2;
        int index = player.playableCard(discard).get();
        assertEquals(1, index);
    }

    @Test
    void addCardToHand() {
        ArrayList<ICard> hand = new ArrayList<>();
        Player player = new Player(hand);
        Face faceDigit = new Face(3);
        Face faceInstruction = new Face("Add Two");
        NormalCard normalDigit = new NormalCard("Red", faceDigit);
        hand.add(normalDigit);
        player.addCardToHand(normalDigit);
        assertEquals(hand, player.hand());
    }

    @Test
    void removeCardFromHand() {
        Face faceDigit = new Face(3);
        Face faceInstruction = new Face("Add Two");
        NormalCard normalDigit = new NormalCard("Red", faceDigit);
        NormalCard normalInstruction = new NormalCard("Blue", faceInstruction);
        ArrayList<ICard> hand = new ArrayList<>();
        hand.add(normalDigit);
        hand.add(normalInstruction);
        Player player = new Player(hand);
        player.removeCardFromHand(0);
        hand.remove(0);
        assertEquals(hand, player.hand());
    }

    @Test
    void hand() {
        Face faceDigit = new Face(3);
        Face faceInstruction = new Face("Add Two");
        NormalCard normalDigit = new NormalCard("Red", faceDigit);
        NormalCard normalInstruction = new NormalCard("Blue", faceInstruction);
        ArrayList<ICard> hand = new ArrayList<>();
        hand.add(normalDigit);
        hand.add(normalInstruction);
        Player player = new Player(hand);
        assertEquals(hand, player.hand());
    }
}