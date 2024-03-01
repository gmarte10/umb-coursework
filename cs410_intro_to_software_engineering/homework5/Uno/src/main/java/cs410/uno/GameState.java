package cs410.uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/* This class represents the state of a game just before the first player takes their turn. From this state, a single
   turn can be run or a full game. This class also can determine if the game is over. It also has a static factory
   method that returns a GameState object for testing. This class is overall responsible for running the game. It
   also prints out each turn as strings.
 */
public class GameState {
    // The discard pile, draw pile and the players playing the game.
    ArrayList<ICard> discardPile;
    ArrayList<ICard> drawPile;
    ArrayList<Player> players;
    // The index of the current player who is supposed to take a turn.
    int currentPlayer;
    /* The direction in which the players are taking their turn. When it is false, it goes in the forward (increasing
       index), when it is true it goes backward (decreasing index).
     */
    boolean reverse;

    public GameState(int countPlayers,
                            int countInitialCardsPerPlayer,
                            int countDigitCardsPerColor,
                            int countSpecialCardsPerColor,
                            int countWildCards) {
        /* Create the moment before the first player takes their turn. Give the players their cards, set up
           the discard pile, draw pile, current player index and the reverse state.
         */
        Initializer initializer = new Initializer(countPlayers, countInitialCardsPerPlayer,
                countDigitCardsPerColor, countSpecialCardsPerColor,
                countWildCards);
        this.players = initializer.players();
        this.discardPile = initializer.discardPile();
        this.drawPile = initializer.drawPile();
        this.currentPlayer = initializer.currentPlayer();
        this.reverse = initializer.reverse();
    }

    /* After the startGame method ends, the game state should represent the
     * situation immediately before the first player takes their first turn.
     * That is, the players should be arranged, their initial hands have been dealt,
     * and the discard pile and draw pile have been created.
     */
    public static GameState startGame(int countPlayers,
                                             int countInitialCardsPerPlayer,
                                             int countDigitCardsPerColor,
                                             int countSpecialCardsPerColor,
                                             int countWildCards) {
        return new GameState(countPlayers,countInitialCardsPerPlayer,
                countDigitCardsPerColor,
                countSpecialCardsPerColor,
                countWildCards);
    }

    /* Indicates whether the game is over or not. Goes through each player and checks if one of them has no cards left.
       This method is called before a player takes their turn, which means the first player to have an empty hand will
       be found and not skipped.
     */
    boolean isGameOver() {
        for (Player player : players) {
            if (player.hand().isEmpty()) {
                System.out.println("\n--------Game Over---------");
                System.out.println("Final Play:");
                for (int i = 0; i < players.size(); i++) {
                    Player p = players.get(i);
                    System.out.println("Player #" + i);
                    System.out.println(p.hand());
                }
                return true;
            }
        }
        return false;
    }

    // Run one game by running a turn until the game is over.
    public void runOneGame() {
        while (!isGameOver()) {
            runOneTurn();
        }
    }

    // Reverse the order in which players take their turn.
    private void reverse() {
        reverse = !reverse;
    }

    // Skip the next player.
    private void skip() {
        nextPlayer();
    }

    // Add two cards from the draw pile to the next player and then skip that player's turn.
    private void addTwo() {
        nextPlayer();
        Player player = players.get(currentPlayer);
        player.addCardToHand(drawPile.remove(drawPile.size() - 1));
        player.addCardToHand(drawPile.remove(drawPile.size() - 1));
    }

    // Go to the next player depending on the reverse state.
    private void nextPlayer() {
        // If reverse is false, go forward increasing the current player index.
        if (!reverse) {
            currentPlayer = currentPlayer + 1;
            if (currentPlayer == players.size()) {
                currentPlayer = 0;
            }
        }
        // If reverse is true, go backwards decreasing the current player index.
        else {
            currentPlayer = currentPlayer - 1;
            if (currentPlayer == -1) {
                currentPlayer = players.size() - 1;
            }
        }
    }

    // Check if a card is a WildCard.
    private boolean isWild(ICard card) {
        if (card instanceof WildCard) {
            return true;
        }
        return false;
    }

    // Set the color of a wild card to a random supported color.
    private void setColor(WildCard card) {
        ArrayList<String> colors = Constants.colors();
        Collections.shuffle(colors);
        card.setColor(colors.get(0));
    }

    /* If the draw pile is empty, it is refreshed from the discard pile. The top card of the discard pile is
       retained; the new discard pile consists of only that card. The other cards in the discard pile become the
       new draw pile after being shuffled.
     */
    private void resetDrawPile() {
        ICard dcard = this.discardPile.remove(this.discardPile.size() - 1);
        Collections.shuffle(this.discardPile);
        this.drawPile = new ArrayList<>(this.discardPile);
        this.discardPile = new ArrayList<>();
        this.discardPile.add(dcard);
    }

    // Check if a card is a WildCard with no color.
    private boolean isEmptyWild(ICard card) {
        if (card instanceof WildCard) {
            if (card.color().equals("No Color")) {
                return true;
            }
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

    // Carry out an instruction from a special card.
    private void playInstruction(String instruction) {
        if (instruction.equals("Skip")) {
            skip();
        }
        else if (instruction.equals("Reverse")) {
            reverse();
        }
        else if (instruction.equals("Add Two")) {
            addTwo();
        }
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

    // Check if two special cards have the same instruction.
    private boolean sameInstruction(ICard card, ICard discard) {
        NormalCard nd = (NormalCard) discard;
        NormalCard nc = (NormalCard) card;
        if (nd.face().instruction().get().equals(nc.face().instruction().get())) {
            return true;
        }
        return false;
    }


    /* The current player takes their turn, and if they play a special card
     * the corresponding effects are performed. When the method returns,
     * the next player is ready to take their turn.
     * If the game is already over, this method has no effect.
     */
    public void runOneTurn() {
        // Check if the game is over.
        if (isGameOver()) {
            return;
        }
        // Reset the draw pile if it is empty.
        if (drawPile.isEmpty()) {
            resetDrawPile();
        }
        ICard discardCard = discardPile.get(discardPile.size() - 1);
        Player currPlayer = players.get(currentPlayer);
        // Check if the discard card is a wild card with no color. If so, set its color to a random supported one.
        if (isEmptyWild(discardCard)) {
            setColor((WildCard) discardCard);
        }

        // Print out each player's hand, the discarded card, and the player's turn.
        System.out.println("\nNEW TURN");
        System.out.print("Discard-> " + discardCard);
        System.out.println("Player turn-> " + currentPlayer);
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.println("Player #" + i);
            System.out.println(player.hand());
        }
        // Find playable card in the current player's hand
        Optional<Integer> optionalPlayableCard = currPlayer.playableCard(discardCard);

        // If there is a playable card in the current player's hand, then play it.
        if (optionalPlayableCard.isPresent()) {
            int i = optionalPlayableCard.get();
            ICard card = currPlayer.hand().get(i);
            // If it's a wild card, play it.
            if (isWild(card)) {
                setColor((WildCard) card);
                this.discardPile.add(card);
                this.players.get(currentPlayer).removeCardFromHand(i);
            }
            // If both cards have the same color.
            else if (sameColors(card, discardCard)) {
                if (isSpecial(card)) {
                    NormalCard nc = (NormalCard) card;
                    playInstruction(nc.face().instruction().get());
                }
                discardPile.add(card);
                currPlayer.removeCardFromHand(i);
            }
            // If both cards are special cards, and they have the same instruction.
            else if (isSpecial(discardCard) && isSpecial(card)) {
                if (sameInstruction(discardCard, card)) {
                    NormalCard nc = (NormalCard) card;
                    playInstruction(nc.face().instruction().get());
                    discardPile.add(card);
                    currPlayer.removeCardFromHand(i);
                }
            }
            // If both cards have the same digit.
            else if (sameDigit(card, discardCard)) {
                discardPile.add(card);
                currPlayer.removeCardFromHand(i);
            }
        }
        /* If no playable card was found in the hand, then draw a card and play it if possible. If not, add it to the
           current player's hand.
         */
        else{
            // Get a card from the top of the draw pile
            ICard draw = drawPile.remove(drawPile.size() - 1);
            System.out.print("Draw-> " + draw);
            // If it's a wild card play it.
            if (isWild(draw)) {
                setColor((WildCard) draw);
                discardPile.add(draw);
            }
            // If both cards have the same color.
            else if (sameColors(draw, discardCard)) {
                if (isSpecial(draw)) {
                    NormalCard ndraw = (NormalCard) draw;
                    playInstruction(ndraw.face().instruction().get());
                }
                discardPile.add(draw);
            }
            // If both cards are special cards, and they have the same instruction.
            else if (isSpecial(discardCard) && isSpecial(draw)) {
                if (sameInstruction(draw, discardCard)) {
                    NormalCard ndraw = (NormalCard) draw;
                    playInstruction(ndraw.face().instruction().get());
                    discardPile.add(draw);
                }
                currPlayer.addCardToHand(draw);
            }
            // If both cards have the same digit.
            else if (sameDigit(draw, discardCard)) {
                discardPile.add(draw);
            }
            // If the draw card is not playable, add it to the current player's hand.
            else {
                currPlayer.addCardToHand(draw);
            }
        }
        // Go to the next player.
        nextPlayer();
    }
}
