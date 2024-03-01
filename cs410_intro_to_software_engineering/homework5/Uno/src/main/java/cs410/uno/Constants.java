package cs410.uno;

import java.util.ArrayList;

/*  This class holds data that is immutable. In Uno, the colors and the 3 instructions supported
    for special cards (Skip, Reverse, Add Two) are always constant.
    Returns the colors and instructions as ArrayLists of Strings.
    Used by Deck and GameState.
 */
public class Constants {
    // Returns all the colors possible on any Uno card.
    public static ArrayList<String> colors() {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("Blue");
        colors.add("Red");
        colors.add("Green");
        colors.add("Yellow");
        return colors;
    }

    // Returns all the instructions for special cards supported in this simplified Uno game.
    public static ArrayList<String> instructions() {
        ArrayList<String> instructions = new ArrayList<>();
        instructions.add("Skip");
        instructions.add("Reverse");
        instructions.add("Add Two");
        return instructions;
    }
}
