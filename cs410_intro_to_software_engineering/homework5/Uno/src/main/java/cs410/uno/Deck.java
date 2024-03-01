package cs410.uno;

import java.util.ArrayList;


/* Represents a deck of Uno cards. A deck is generated using the amount of digit cards per color, the amount
   of special cards per color and the number of wild cards. The deck is returned in an ordered fashion with
   digits first, then specials and lastly wilds. The color and instructions are ordered the same as they are in the
   Constant class. The deck supports repetition of cards. All the Wild cards have no color. This class is used by
   Initializer.
   Example:
   * parenthesis are used to make repetitions clearer.
   digit cards per color = 2
   (Blue 0...Blue 9, Red 0...Red 9, Green 0...Green 9, Yellow 0...Yellow 9),
   (Blue 0...Blue 9, Red 0...Red 9, Green 0...Green 9, Yellow 0...Yellow 9)

   special cards per color = 3
   (Blue Skip, Blue Reverse, Blue Add Two, Red Skip, Red Reverse, Red Add Two,
    Green Skip, Green Reverse, Green Add Two, Yellow Skip, Yellow Reverse, Yellow Add Two),
   (Blue Skip, Blue Reverse, Blue Add Two, Red Skip, Red Reverse, Red Add Two,
    Green Skip, Green Reverse, Green Add Two, Yellow Skip, Yellow Reverse, Yellow Add Two),
   (Blue Skip, Blue Reverse, Blue Add Two, Red Skip, Red Reverse, Red Add Two,
    Green Skip, Green Reverse, Green Add Two, Yellow Skip, Yellow Reverse, Yellow Add Two)

   wild cards = 5
   (No Color, No Color, No Color, No Color, No Color)

   Full Deck:
   (Blue 0...Blue 9, Red 0...Red 9, Green 0...Green 9, Yellow 0...Yellow 9),
   (Blue 0...Blue 9, Red 0...Red 9, Green 0...Green 9, Yellow 0...Yellow 9)
   (Blue Skip, Blue Reverse, Blue Add Two, Red Skip, Red Reverse, Red Add Two,
    Green Skip, Green Reverse, Green Add Two, Yellow Skip, Yellow Reverse, Yellow Add Two),
   (Blue Skip, Blue Reverse, Blue Add Two, Red Skip, Red Reverse, Red Add Two,
    Green Skip, Green Reverse, Green Add Two, Yellow Skip, Yellow Reverse, Yellow Add Two),
   (Blue Skip, Blue Reverse, Blue Add Two, Red Skip, Red Reverse, Red Add Two,
    Green Skip, Green Reverse, Green Add Two, Yellow Skip, Yellow Reverse, Yellow Add Two)
   (No Color, No Color, No Color, No Color, No Color)
 */
public class Deck {
    // Full deck of Uno cards.
    private final ArrayList<ICard> deck;
    public Deck(int digitsCount, int specialsCount, int wildsCount) {
        this.deck = generateDeck(digitsCount, specialsCount, wildsCount);
    }

    // Returns the full deck.
    public ArrayList<ICard> deck() {
        return this.deck;
    }

    /* Creates a deck of Uno digit cards. The colors are ordered in the same fashion as they are in the Constants class.
       The deck supports repetitions of cards. The minimum number of cards returned is 40, which is one digit per color
       (Blue 0-9, Red 0-9, Green 0-9, Yellow 0-9). In otherwords zero digits per color count is not allowed. The digits
       go from zero to nine.
     */
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

    /* Creates a deck of Uno special cards. The colors and instructions are ordered in the same fashion as they are in
       the Constants class. The deck supports repetitions of cards. If the specials per color count is zero, an empty
       array list returned.
     */
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

    /* Creates a deck of Uno wild cards. Each wild card in the deck has no color. The deck supports repetitions
       of cards. If the wilds per color count is zero, an empty array list returned.
     */
    private ArrayList<ICard> generateWilds(int wc) {
        ArrayList<ICard> w = new ArrayList<>();
        for (int i = 0; i < wc; i++) {
            WildCard card = new WildCard();
            w.add(card);
        }
        return w;
    }

    /* Creates a full deck of Uno cards with digits, specials and wilds. The colors are ordered in the same fashion
       as they are in the Constants class. The deck supports repetitions of cards. The number of specials and wilds per
       color count can be zero.
     */
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
