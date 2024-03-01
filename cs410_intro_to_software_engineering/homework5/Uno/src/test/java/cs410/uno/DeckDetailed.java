package cs410.uno;

import java.util.ArrayList;

/* This class is the same as the Deck.java class. The difference is that this one has extra methods for testing. This
   class is only supposed to be used for testing. The parts added for testing are splitting the deck into digits per
   color, specials per color and wilds. There is also a method zeroToDigits used for testing only numbers 0 to 2 per
   color. This is supposed to allow for testing smaller numbers of cards.
 */
public class DeckDetailed {
    private ArrayList<ICard> deck;
    private ArrayList<ICard> digits;
    private ArrayList<ICard> specials;
    private ArrayList<ICard> wilds;
    private ArrayList<ICard> zeroToTwoDigits;
    private ArrayList<ICard> onlySkip;
    private ArrayList<ICard> onlyAddTwo;
    private ArrayList<ICard> onlyReverse;
    public DeckDetailed(int digitsCount, int specialsCount, int wildsCount) {
        this.deck = generateDeck(digitsCount, specialsCount, wildsCount);
        this.digits = generateDigits(digitsCount);
        this.specials = generateSpecials(specialsCount);
        this.wilds = generateWilds(wildsCount);
        this.zeroToTwoDigits = generateZeroToTwo(digitsCount);
        this.onlySkip = generateSkip(specialsCount);
        this.onlyReverse = generateReverse(specialsCount);
        this.onlyAddTwo = generateAddTwo(specialsCount);
    }
    public ArrayList<ICard> deck() {
        return this.deck;
    }

    public ArrayList<ICard> zeroToTwoDigits() {
        return this.zeroToTwoDigits;
    }
    public ArrayList<ICard> onlySkip() {
        return this.onlySkip;
    }
    public ArrayList<ICard> onlyReverse() {
        return this.onlyReverse;
    }

    public ArrayList<ICard> onlyAddTwo() {
        return this.onlyAddTwo;
    }

    public ArrayList<ICard> specials() {
        return this.specials;
    }
    public ArrayList<ICard> digits() {
        return this.digits;
    }

    public ArrayList<ICard> wilds() {
        return this.wilds;
    }

    private ArrayList<ICard> generateAddTwo(int sc) {
        ArrayList<ICard> s = new ArrayList<>();
        if (sc == 0) {
            return s;
        }
        for (String color : Constants.colors()) {
            Face face = new Face("Add Two");
            NormalCard card = new NormalCard(color, face);
            s.add(card);
        }
        ArrayList<ICard> original = new ArrayList<>(s);
        for (int i = 1; i < sc; i++) {
            s.addAll(original);
        }
        return s;
    }

    private ArrayList<ICard> generateReverse(int sc) {
        ArrayList<ICard> s = new ArrayList<>();
        if (sc == 0) {
            return s;
        }
        for (String color : Constants.colors()) {
            Face face = new Face("Reverse");
            NormalCard card = new NormalCard(color, face);
            s.add(card);
        }
        ArrayList<ICard> original = new ArrayList<>(s);
        for (int i = 1; i < sc; i++) {
            s.addAll(original);
        }
        return s;
    }

    private ArrayList<ICard> generateSkip(int sc) {
        ArrayList<ICard> s = new ArrayList<>();
        if (sc == 0) {
            return s;
        }
        for (String color : Constants.colors()) {
            Face face = new Face("Skip");
            NormalCard card = new NormalCard(color, face);
            s.add(card);
        }
        ArrayList<ICard> original = new ArrayList<>(s);
        for (int i = 1; i < sc; i++) {
            s.addAll(original);
        }
        return s;
    }

    private ArrayList<ICard> generateZeroToTwo(int dc) {
        ArrayList<ICard> d = new ArrayList<>();
        for (String color : Constants.colors()) {
            for (int i = 0; i < 3; i++) {
                Face face = new Face(i);
                NormalCard card = new NormalCard(color, face);
                d.add(card);
            }
        }
        ArrayList<ICard> original = new ArrayList<>(d);
        for (int i = 1; i < dc; i++) {
            d.addAll(original);
        }
        return d;
    }
    private ArrayList<ICard> generateDigits(int dc) {
        ArrayList<ICard> d = new ArrayList<>();
        for (String color : Constants.colors()) {
            for (int i = 0; i < 10; i++) {
                Face face = new Face(i);
                NormalCard card = new NormalCard(color, face);
                d.add(card);
            }
        }
        ArrayList<ICard> original = new ArrayList<>(d);
        for (int i = 1; i < dc; i++) {
            d.addAll(original);
        }
        return d;
    }
    private ArrayList<ICard> generateSpecials(int sc) {
        ArrayList<ICard> s = new ArrayList<>();
        if (sc == 0) {
            return s;
        }
        for (String color : Constants.colors()) {
            for (String instruct : Constants.instructions()) {
                Face face = new Face(instruct);
                NormalCard card = new NormalCard(color, face);
                s.add(card);
            }
        }
        ArrayList<ICard> original = new ArrayList<>(s);
        for (int i = 1; i < sc; i++) {
            s.addAll(original);
        }
        return s;
    }
    private ArrayList<ICard> generateWilds(int wc) {
        ArrayList<ICard> w = new ArrayList<>();
        for (int i = 0; i < wc; i++) {
            WildCard card = new WildCard();
            w.add(card);
        }
        return w;
    }
    private ArrayList<ICard> generateDeck(int digitsCount, int specialsCount, int wildsCount) {
        ArrayList<ICard> digits = generateDigits(digitsCount);
        ArrayList<ICard> specials = generateSpecials(specialsCount);
        ArrayList<ICard> wilds = generateWilds(wildsCount);
        ArrayList<ICard> all = new ArrayList<>();
        all.addAll(digits);
        all.addAll(specials);
        all.addAll(wilds);
        return all;
    }
}

