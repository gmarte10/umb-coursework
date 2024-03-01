package edu.umb.cs210.p5;

import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

public class Spell {
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] lines = in.readAllLines();
        in.close();

        // Create an RefArrayST<String, String> object called st.
        ArrayST<String, String> st = new ArrayST<String, String>();

        // For each line in lines, split it into two tokens using
        // "," as delimiter; insert into st the key-value pair
        // (token 1, token 2).

        // variables
        String token1;
        String token2;
        String[] temp;
        // go through lines, split and enter into st
        for (int i = 0; i < lines.length; i++)
        {
            // split
            temp = lines[i].split(",");
            // store
            token1 = temp[0];
            token2 = temp[1];
            // put into arrayST
            st.put(token1, token2);
        }
            
        // Read from standard input one line at a time; increment
        // a line number counter; split the line into words using
        // "\\b" as the delimiter; for each word in words, if it
        // exists in st, write the (misspelled) word, its line number, and
        // corresponding value (correct spelling) from st.

        // variables
        int lineCount = 0;
        String[] temp2;
        // read from standard input
        while (!StdIn.isEmpty())
        {
            // read a line
            String currentLine = StdIn.readLine();
            // increase line count
            lineCount++;
            // split
            temp2 = currentLine.split("\\b");
            // go through strings in array of strings
            for (String word : temp2)
            {
                // check if st contains the word/string
                if (st.contains(word))
                {
                    // print
                    StdOut.printf("%s:%d -> %s\n", word, lineCount,
                            st.get(word));
                }
            }
        }
    }
}
