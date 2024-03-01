package cs410.uno;

import java.util.ArrayList;
import java.util.Collections;

/* Represents the moment before the first player takes their turn. This class sets up the discard pile, the draw
   pile, the players, the current player index and the reverse state. This class returns those things to be used
   in the GameState class. An Initializer object is created with the number of players, the number of cards per
   player, the number of digit cards per color, the number of special cards per color and the number of wild cards.
   This class is used by GameState and GameStatePrinted. This class is essentially a helper class for GameState.
 */
public class Initializer {
    // The starting discard pile, drawpile, players, current player index and the reverse state.
    private final ArrayList<ICard> discardPile;
    private final ArrayList<ICard> drawPile;
    private final ArrayList<Player> players;
    // This is the index of the current player in the players array list.
    private final int currentPlayer;
    // This determines the direction in which players take their turn.
    private final boolean reverse;
    /* The deck is only used within this class and never returned. This deck gets cards removed from it as they are
       added to each player hand, the discard pile and the draw pile.
     */
    private ArrayList<ICard> deck;
    public Initializer(int playerCount, int cardsPerPlayer,
                       int digitsCount, int specialsCount, int wildsCount) {
        this.currentPlayer = 0;
        this.reverse = false;
        this.deck = getDeck(digitsCount, specialsCount, wildsCount);
        this.players = setupPlayers(playerCount, cardsPerPlayer);
        this.discardPile = setupDiscardPile();
        this.drawPile = setupDrawPile();
    }

    // Return the already setup discard pile, draw pile, players, current player index and the reverse state.
    public ArrayList<ICard> discardPile() {
        return discardPile;
    }
    public ArrayList<ICard> drawPile() {
        return drawPile;
    }
    public ArrayList<Player> players() {
        return players;
    }
    public int currentPlayer() {
        return currentPlayer;
    }
    public boolean reverse() {
        return reverse;
    }

    // Creates an unshuffled Uno Deck and returns it.
    private ArrayList<ICard> getDeck(int dc, int sc, int wc) {
        Deck deck = new Deck(dc, sc, wc);
        return deck.deck();
    }

    // Gives each player a hand of shuffled cards. The number of cards given is the same as cards per player parameter.
    private ArrayList<Player> setupPlayers(int pc, int cpp) {
        Collections.shuffle(this.deck);
        ArrayList<Player> allPlayers = new ArrayList<>();
        for (int i = 0; i < pc; i++) {
            ArrayList<ICard> hand = new ArrayList<>();
            for (int j = 0; j < cpp; j++) {
                ICard card = deck.remove(j);
                hand.add(card);
            }
            Player player = new Player(hand);
            allPlayers.add(player);
        }
        return allPlayers;
    }

    // Creates a discard pile with one card of the deck. That card is removed from the deck.
    private ArrayList<ICard> setupDiscardPile() {
        ArrayList<ICard> discard = new ArrayList<>();
        discard.add(this.deck.remove(0));
        return discard;
    }

    // Creates a draw pile by making it the same as the leftover cards in the deck.
    private ArrayList<ICard> setupDrawPile() {
        return new ArrayList<>(this.deck);
    }
}
