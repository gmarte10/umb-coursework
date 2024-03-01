package edu.umb.cs210.p6;

import dsa.DiGraph;
import dsa.RedBlackBinarySearchTreeST;
import dsa.Set;
import stdlib.In;
import stdlib.StdOut;

// An immutable WordNet data type.
public class WordNet {
    // st => noun to id
    RedBlackBinarySearchTreeST<String, Set<Integer>> st;
    // rst => id to synset
    RedBlackBinarySearchTreeST<Integer, String> rst;
    // declare sca
    ShortestCommonAncestor sca;

    // Construct a WordNet object given the names of the input (synset and
    // hypernym) files.
    public WordNet(String synsets, String hypernyms) {
        // check if synsets or hypernyms is null
        if (synsets == null) {
            throw new NullPointerException("synsets is null");
        }
        if (hypernyms == null) {
            throw new NullPointerException("hypernyms is null");
        }

        // initialize both symbol tables
        st = new RedBlackBinarySearchTreeST<String, Set<Integer>>();
        rst = new RedBlackBinarySearchTreeST<Integer, String>();

        // open file and declare variables
        In syn = new In(synsets);
        String lineSyn, synset;
        int id;
        String[] splitLineRST, splitLineST;

        // fill both symbol tables
        while (!syn.isEmpty()) {
            lineSyn = syn.readLine();

            // fill rst
            splitLineRST = lineSyn.split(",");
            id = Integer.parseInt(splitLineRST[0]);
            synset =  splitLineRST[1];
            rst.put(id, synset);

            // fill st
            splitLineST = splitLineRST[1].split(" ");
            for (String noun : splitLineST) {
                if (!st.contains(noun)) {
                    Set<Integer> set = new Set<Integer>();
                    st.put(noun, set);
                }
                st.get(noun).add(id);
            }
        }
        // close synsets
        syn.close();

        // create digraph of st size
        DiGraph G = new DiGraph(st.size());

        // open file and declare variables
        In hyp = new In(hypernyms);
        String lineHyp;
        String[] splitHyp;

        // add edges to digraph
        while (!hyp.isEmpty()) {
            lineHyp = hyp.readLine();
            splitHyp = lineHyp.split(",");
            int synsetId = Integer.parseInt(splitHyp[0]);
            for (int i = 1; i < splitHyp.length; i++) {
                G.addEdge(synsetId, Integer.parseInt(splitHyp[i]));
            }
        }
        // close hypernyms
        hyp.close();

        // initialize sca
        sca = new ShortestCommonAncestor(G);
    }

    // All WordNet nouns.
    public Iterable<String> nouns() {
        return st.keys();
    }

    // Is the word a WordNet noun?
    public boolean isNoun(String word) {
        // check if word is null
        if (word == null) {
            throw new NullPointerException("word is null");
        }
        return st.contains(word);
    }

    // A synset that is a shortest common ancestor of noun1 and noun2.
    public String sca(String noun1, String noun2) {
        // check if noun1 or noun2 is null
        if (noun1 == null) {
            throw new NullPointerException("noun1 is null");
        }
        if (noun2 == null) {
            throw new NullPointerException("noun2 is null");
        }

        // check if noun1 or noun2 is a WordNet noun
        if (!isNoun(noun1)) {
            throw new IllegalArgumentException("noun1 is not a noun");
        }
        if (!isNoun(noun2)) {
            throw new IllegalArgumentException("noun2 is not a noun");
        }

        // get id of ancestor
        int ancestorId = sca.ancestor(st.get(noun1), st.get(noun2));
        // get & return synset
        return rst.get(ancestorId);
    }

    // Distance between noun1 and noun2.
    public int distance(String noun1, String noun2) {
        // check if noun1 or noun2 is null
        if (noun1 == null) {
            throw new NullPointerException("noun1 is null");
        }
        if (noun2 == null) {
            throw new NullPointerException("noun2 is null");
        }

        // check if noun1 or noun2 is a WordNet noun
        if (!isNoun(noun1)) {
            throw new IllegalArgumentException("noun1 is not a noun");
        }
        if (!isNoun(noun2)) {
            throw new IllegalArgumentException("noun2 is not a noun");
        }

        // get & return distance
        return sca.length(st.get(noun1), st.get(noun2));
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        String word1 = args[2];
        String word2 = args[3];        
        int nouns = 0;
        for (String noun : wordnet.nouns()) {
            nouns++;
        }
        StdOut.println("# of nouns = " + nouns);
        StdOut.println("isNoun(" + word1 + ") = " + wordnet.isNoun(word1));
        StdOut.println("isNoun(" + word2 + ") = " + wordnet.isNoun(word2));
        StdOut.println("isNoun(" + (word1 + " " + word2) + ") = "
                       + wordnet.isNoun(word1 + " " + word2));
        StdOut.println("sca(" + word1 + ", " + word2 + ") = "
                       + wordnet.sca(word1, word2));
        StdOut.println("distance(" + word1 + ", " + word2 + ") = "
                       + wordnet.distance(word1, word2));
    }
}
