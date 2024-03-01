package edu.umb.cs210.p3;

import stdlib.StdOut;
import stdlib.StdRandom;

// A data type representing a six-sided die.
public class Die implements Comparable<Die> {
    private int value; // face value

    // Construct a die.
    public Die() {
        value = 0;
    }
    
    // Roll the die.
    public void roll() {
        value = StdRandom.uniform(1, 7);
    }

    // Face value of the die.
    public int value() {
        return value;
    }

    // Does the die have the same face value as that?
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null) return false;
        if (this.getClass() != that.getClass()) return false;
        Die thatDie = (Die) that;

        if (this.value == thatDie.value) return true;
        return false;
    }

    // A negative integer, zero, or positive integer depending on
    // whether this die's value is less than, equal to, or greater
    // than the that die's value.
    public int compareTo(Die that) {
        if (this.value < that.value)
        {
            return this.value - that.value;
        }
        else if (this.value == that.value)
        {
            return this.value - that.value;
        }

        return this.value - that.value; // greater than
    }

    // A string representation of the die giving the current
    // face value.
    public String toString() {
        String dieOne = "     \n  *  \n     ";
        String dieTwo = "*    \n     \n    *";
        String dieThree = "*    \n  *  \n    *";
        String dieFour = "*   *\n     \n*   *";
        String dieFive = "*   *\n  *  \n*   *";
        String dieSix = "* * *\n     \n* * *";

        if (value == 1)
        {
            return dieOne;
        }
        else if (value == 2)
        {
            return dieTwo;
        }
        else if (value == 3)
        {
            return dieThree;
        }
        else if (value == 4)
        {
            return dieFour;
        }
        else if (value == 5)
        {
            return dieFive;
        }

        return dieSix;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        Die a = new Die();
        a.roll();
        while (a.value() != x) {
            a.roll();
        }
        Die b = new Die();        
        b.roll();
        while (b.value() != y) {
            b.roll();
        }
        Die c = new Die();        
        c.roll();
        while (c.value() != z) {
            c.roll();
        }
        StdOut.println(a);
        StdOut.println(a.equals(b));
        StdOut.println(b.equals(c));        
        StdOut.println(a.compareTo(b) > 0);
        StdOut.println(b.compareTo(c) > 0);        
    }
}
