package edu.umb.cs210.p2;

import stdlib.StdOut;

import java.util.Iterator;

// An immutable data type to systematically iterate over binary
// strings of length n.
public class BinaryStrings implements Iterable<String> {
    private final int n; // need all binary strings of length n

    // Construct an iterable BinaryStrings object given the length
    // of binary strings needed.
    public BinaryStrings(int n) {
        this.n = n;
    }

    // A BinaryStringsIterator object.
    public Iterator<String> iterator() {
        BinaryStringsIterator x = new BinaryStringsIterator();
        return x;
    }
    
    // Binary strings iterator.
    private class BinaryStringsIterator implements Iterator<String> {
        private int count; // number of binary strings returned
        private int p;     // current number

        // Construct a BinaryStringsIterator object.
        BinaryStringsIterator() {
            count = 0;
            p = 0;
        }

        // Are there anymore binary strings left to be iterated?
        public boolean hasNext() { 
            if (count == Math.pow(2, n))
            {
                return false;
            }
            return true;
        }

        // The next binary string.
        public String next() {
            ++count;
            return binary(p++);
        }
        
        // Remove is not supported.
        public void remove() {
            // nothing to do
        }

        // The n-bit representation of x.
        private String binary(int x) {
            String s = Integer.toBinaryString(x);
            int padding = n - s.length();
            for (int i = 1; i <= padding; i++) {
                s = "0" + s;
            }
            return s;
        }
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (String s : new BinaryStrings(n)) {
            StdOut.println(s);
        }
    }
}
