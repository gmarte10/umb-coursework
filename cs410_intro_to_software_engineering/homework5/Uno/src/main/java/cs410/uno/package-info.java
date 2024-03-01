package cs410.uno;
/* Code for a simplified version of Uno. Supports wild cards with or without a color. It also supports normal cards with
   either a digit or an instruction (skip, reverse and draw two). Class NormalCard represents a normal Uno card with a
   face. A face can be either a digit or an instruction. Class WildCard represents a wild Uno card. It is created
   with no color, and its color can be set after that. Both NormalCard and WilCard can check if a card is playable.
   Class Player represents a player. Each player has a hand that a card can be added to or removed from. A player can
   also return a playable card's index in an Optional Object. Class Deck represents a full Uno deck based on the
   given parameters in GameState. The deck returned from Deck is not shuffled. Class Initializer represents the moment
   before the first player takes a turn. All players have been given a hand, the draw pile, discard pile, current
   player index and reverse state are all set up. GameState represents all the actions and components to conduct a
   turn and a full game.

   Improvements to be made:
   -Modularity: Most of the program is done in GameState. A better idea would be to have some methods put into
                the other classes.
   -Testing: A lot of the testing was simple. A better method would be to focus more on the higher level testing.
             By making the entire program more modular this would be easier to do.
 */