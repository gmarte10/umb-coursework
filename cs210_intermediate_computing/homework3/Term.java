package edu.umb.cs210.p3;

// import net.sf.saxon.query.XQueryFunctionLibrary;
import stdlib.In;
import stdlib.StdOut;

import java.util.Arrays;
import java.util.Comparator;

// An immutable data type that represents an autocomplete term: a query string 
// and an associated real-valued weight.
public class Term implements Comparable<Term> {
    // Query String: query
    String query;
    // Query Weight: weight
    long weight;

    // Construct a term given the associated query string, having weight 0.
    public Term(String query) {
        this(query, 0L);
    }

    // Construct a term given the associated query string and weight.
    public Term(String query, long weight) {
        if (query == null)
        {
            throw new NullPointerException();
        }

        if (weight < 0)
        {
            throw new IllegalArgumentException();
        }

        this.query = query;
        this.weight = weight;
    }

    // Compare this term to that in lexicographic order by query and 
    // return a negative, zero, or positive integer based on whether this 
    // term is smaller, equal to, or larger than that term.
    public int compareTo(Term that) {

        int compareQuery = this.query.compareTo(that.query);

        if (compareQuery == -1)
        {
            return compareQuery;
        }
        else if (compareQuery == 0)
        {
            return compareQuery;
        }
        return compareQuery;
    }

    // A reverse-weight comparator.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    // Helper reverse-weight comparator.
    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term v, Term w)
        {
            if (Double.compare(w.weight, v.weight) == -1)
            {
                return -1;
            }
            else if (Double.compare(w.weight, v.weight) == 0)
            {
                return 0;
            }
            return 1;
        }
    }

    // A prefix-order comparator.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0)
        {
            throw new IllegalArgumentException();
        }
        return new PrefixOrder(r);
    }

    // Helper prefix-order comparator.
    private static class PrefixOrder implements Comparator<Term> {
        // Prefix length: r
        int r;

        // Construct a new PrefixOrder object
        PrefixOrder(int r) {
            this.r = r;
        }

        public int compare(Term v, Term w) {
            int a = Math.min(r, v.query.length());
            int b = Math.min(r, w.query.length());

            String newVQuery = v.query.substring(0, a);
            String newWString = w.query.substring(0, b);

            int compareVW = newVQuery.compareTo(newWString);

            if (compareVW < 0)
            {
                return compareVW;
            }
            else if (compareVW == 0)
            {
                return compareVW;
            }
            return compareVW;
        }
    }

    // A string representation of this term.
    public String toString() {
        String x = weight + "\t" + query;
        return x;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query.trim(), weight); 
        }
        StdOut.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
        StdOut.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
    }
}
