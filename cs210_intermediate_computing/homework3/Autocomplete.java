package edu.umb.cs210.p3;

// import dsa.BinarySearch;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

import java.util.Arrays;
import java.util.Comparator;

// A data type that provides autocomplete functionality for a given set of 
// string and weights, using Term and BinarySearchDeluxe. 
public class Autocomplete {
    private Term[] terms;   // The set of terms

    // Initialize the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null)
        {
            throw new NullPointerException();
        }

        // defensive copy of terms
        Term[] termsCopy = new Term[terms.length];
        for (int i = 0; i < terms.length; i++)
        {
            termsCopy[i] = terms[i];
        }
        this.terms = termsCopy;

        // sort lexicographically
        Arrays.sort(this.terms);
    }

    // All terms that start with the given prefix, in descending order of
    // weight.
    public Term[] allMatches(String prefix) {

        // corner case check
        if (prefix == null)
        {
            throw new NullPointerException();
        }

        // obtains the first index of occurrence of prefix
        Term prefixTerm = new Term(prefix, 0);
        Comparator<Term> prefixLen = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(this.terms,
                prefixTerm, prefixLen);

        // finds number of terms that match prefix
        int n = numberOfMatches(prefix);

        // constructs array containing n elements from terms starting at i
        Term [] matches = new Term[n + i];
        for (int x = 0; x < n; n++)
        {
            matches[x] = this.terms[x + i];
        }

        // sort matches in reverse order by weight
        Arrays.sort(matches, Term.byReverseWeightOrder());

        // return sorted matches
        return matches;
    }

    // The number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {

        // corner case check
        if (prefix == null)
        {
            throw new NullPointerException();
        }

        // first index
        Term first = new Term(prefix, 0);
        Comparator<Term> fPrefixLen = Term.byPrefixOrder(prefix.length());
        int f = BinarySearchDeluxe.firstIndexOf(this.terms, first, fPrefixLen);

        // last index
        Term last = new Term(prefix, 0);
        Comparator<Term> lPrefixLen = Term.byPrefixOrder(prefix.length());
        int l = BinarySearchDeluxe.lastIndexOf(this.terms, last, lPrefixLen);

        // returns total number of terms
        return l - f + 1;
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query.trim(), weight); 
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix: ");
        boolean firstLoop = true;
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches, in descending order by weight:";
            if (results.length == 0) msg = "No matches";
            else if (results.length > k) msg = "First " + k + msg;
            else msg = "All" + msg;
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
            StdOut.print("\nEnter a prefix, or press <ctrl-d> to quit : ");
            if (firstLoop) {
                StdOut.print("\n(Unless you're using IntelliJ to run. Then the "
                        + "stop button will quit)\nThat prefix, please: ");
                firstLoop = false;
            }
        }
    }
}
