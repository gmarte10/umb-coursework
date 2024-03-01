package cs410.uno;

import java.util.ArrayList;
import java.util.Collections;

/* Represents a normal Uno card. A normal Uno card has a color and a face. A face is either a digit or an instruction.
   The color and the face cannot be altered; in other words, they are immutable. The class also implements the
   ICard interface. It is used by Deck and GameState. It can also check if a card is playable given another card.
   Example:
   Normal Card: "Blue", (Face = 9)
   Normal Card: "Red", (Face = "Skip");
 */
public class NormalCard implements ICard {
    // The color and the face of a normal card
    private final String color;
    private final Face face;
    public NormalCard(String color, Face face) {
        this.color = color;
        this.face = face;
    }
    
    // Returns the color and the face of the normal card.
    public String color() {
        return this.color;
    }
    public Face face() {
        return this.face;
    }

    // Creates a string representation of the card with its color and face.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String c = "NormalCard -> Color: " + this.color + " | ";
        sb.append(c);
        if (this.face.digit().isPresent()) {
            String d = "Digit: " + this.face.digit().get() + "\n";
            sb.append(d);
        } else if (this.face.instruction().isPresent()) {
            String i = "Instruction: " + this.face.instruction().get() + "\n";
            sb.append(i);
        }
        return sb.toString();
    }
    // Set the color of a wild card to a random color.
    private void setColor(WildCard card) {
        ArrayList<String> colors = Constants.colors();
        Collections.shuffle(colors);
        card.setColor(colors.get(0));
    }

    // Check a card is a wild card
    private boolean isWild(ICard card) {
        if (card instanceof WildCard) {
            return true;
        }
        return false;
    }

    // Check if two cards have the same color.
    private boolean sameColors(ICard card, ICard discard) {
        if (card.color().equals(discard.color())) {
            return true;
        }
        return false;
    }
    // Check if a card is a special normal card.
    private boolean isSpecial(ICard card) {
        if (card instanceof NormalCard) {
            if (((NormalCard) card).face().instruction().isPresent()) {
                return true;
            }
        }
        return false;
    }
    // Check if two special cards have the same instruction.
    private boolean sameInstruction(ICard card, ICard discard) {
        NormalCard nd = (NormalCard) discard;
        NormalCard nc = (NormalCard) card;
        if (nd.face().instruction().get().equals(nc.face().instruction().get())) {
            return true;
        }
        return false;
    }
    // Check if two cards have the same digit.
    private boolean sameDigit(ICard card, ICard discard) {
        if (card instanceof NormalCard && discard instanceof NormalCard) {
            NormalCard nc = (NormalCard) card;
            NormalCard nd = (NormalCard) discard;
            if (nc.face().digit().isPresent() && nd.face().digit().isPresent()) {
                if (nc.face().digit().get().equals(nd.face().digit().get())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCardPlayable(ICard card) {
        if (isWild(card)) {
            setColor((WildCard) card);
            return true;
        }
        // If both cards have the same color.
        else if (sameColors(card, this)) {
            return true;
        }
        // If both cards are special cards and they have the same instruction.
        else if (isSpecial(this) && isSpecial(card)) {
            if (sameInstruction(this, card)) {
                return true;
            }
        }
        // If both cards have the same digit.
        else if (sameDigit(card, this)) {
            return true;
        }
        return false;
    }

    // Checks if two NormalCards are equal to one another.
    @Override
    public boolean equals(Object other) {
        // Object is compared with itself
        if (other == this) {
            return true;
        }
        // Check if the Object is not an instance of NormalCard class.
        if (!(other instanceof NormalCard)) {
            return false;
        }
        // Cast the object to NormalCard type.
        NormalCard c = (NormalCard) other;
        // Check if the face and the colors are the same.
        if ((this.face.equals(c.face)) && (this.color.equals(c.color))) {
            return true;
        }
        return false;
    }
}
