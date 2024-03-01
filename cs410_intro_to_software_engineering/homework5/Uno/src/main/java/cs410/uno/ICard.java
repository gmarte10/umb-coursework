package cs410.uno;

/* This interface is used to group the NormalCard and WildCard classes together based on the return color method and
   the isCardPlayable method. This interface also represents the general idea of an Uno card. An Uno card can either
   be a normal card or a wild card, which is why it groups both classes together.
 */
public interface ICard {
    // Return the color of the card
    public String color();
    // Check if a card is playable given another card.
    public boolean isCardPlayable(ICard card);

}
