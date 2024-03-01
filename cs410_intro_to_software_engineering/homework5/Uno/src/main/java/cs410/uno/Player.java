package cs410.uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/* Represents a player. A player has a hand that can have a card added or removed from it. This class is used by
   Initializer and GameState. A player's hand can have both NormalCards and WildCards. It can return a playable card's
   index from the hand in an Optional object.
   Example:
   Player 1 -> hand = [Blue 9, Green 0, ...]
 */
public class Player {
    // The player's hand
    private ArrayList<ICard> hand;
    public Player(ArrayList<ICard> hand) {
        this.hand = hand;
    }

    // Add a card to the hand
    public void addCardToHand(ICard card) {
        this.hand.add(card);
    }

    // Remove a card from the hand.
    public void removeCardFromHand(int index) {
        this.hand.remove(index);
    }

    // Returns the player's hand.
    public ArrayList<ICard> hand() {
        return this.hand;
    }

    // If a card is playable, then return it's index in an Optional Object, if not return an empty Optional
    public Optional<Integer> playableCard(ICard discard) {
        // Go through each card in the hand and return the first card that is playable.
        for (int i = 0; i < hand.size(); i++) {
            ICard card = hand.get(i);
            if (discard.isCardPlayable(card)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }
}
