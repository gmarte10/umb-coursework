package cs410.uno;

/* Represents a wild Uno card. A wild Uno card has no color when it is first created. The color can be set using
   the provided setColor method. The class also implements the ICard interface. This class is used by Deck and
   GameState. This class can check if a card is playable given a WildCard and another card.
   Example:
   WildCard: "No Color"
   WildCard.setColor("Yellow"): "Yellow"
 */
public class WildCard implements ICard {
    // The color is first "No Color" and can be changed after.
    private String color;

    // When a wild card is created, it has no color.
    public WildCard() {
        this.color = "No Color";
    }

    // A wild card color can be set.
    public void setColor(String colorToSet) {
        this.color = colorToSet;
    }

    // Return the color of the wild card, if it has none, "No Color" is returned.
    public String color() {
        return this.color;
    }

    // Returns a string representation of the wild card.
    @Override
    public String toString() {
        return "WildCard -> Color: " + this.color + "\n";
    }

    // Check if two cards have the same color.
    private boolean sameColors(ICard card) {
        if (card.color().equals(this.color())) {
            return true;
        }
        return false;
    }
    public boolean isCardPlayable(ICard card) {
        if (sameColors(card)) {
            return true;
        }
        return false;
    }

    // Checks if two WildCards are the same
    @Override
    public boolean equals(Object other) {
        // Object is compared with itself
        if (other == this) {
            return true;
        }
        // Check if the Object is not an instance of WildCard class.
        if (!(other instanceof WildCard)) {
            return false;
        }
        // Cast the object to WildCard type.
        WildCard c = (WildCard) other;
        // Check if the colors are the same.
        if (this.color.equals(c.color)) {
            return true;
        }
        return false;
    }
}
