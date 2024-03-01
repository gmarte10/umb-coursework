package edu.umb.cs210.p6;

import stdlib.In;
import stdlib.StdOut;

// An immutable data type for outcast detection.
public class Outcast {
    WordNet wordnet;

    // Construct an Outcast object given a WordNet object.
    public Outcast(WordNet wordnet) {
        // initialize
        this.wordnet = wordnet;
    }

    // The outcast noun from nouns.
    public String outcast(String[] nouns) {
        // declare variables
        String outcast = null;
        int dist;
        int max = 0;

        // find outcast
        for (String i : nouns) {
            // reset distance
            dist = 0;
            for (String j : nouns) {
                // sum distances
                dist += wordnet.distance(i, j);
            }
            // check if current distance is max
            if (dist > max) {
                max = dist;
                outcast = i;
            }
        }
        return outcast;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println("outcast(" + args[t] + ") = "
                           + outcast.outcast(nouns));
        }
    }
}
