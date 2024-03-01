package edu.umb.cs210.p4;

import stdlib.StdIn;
import stdlib.StdOut;

public class CertifyHeap {
    // Return true if v is less than w and false otherwise.
    private static <T extends Comparable<T>> boolean less(T v, T w) {
        return (v.compareTo(w) < 0);
    }

    // Return true if a[] represents a maximum-ordered heap
    // and false otherwise.
    protected static <T extends Comparable<T>> boolean maxOrderedHeap(T[] a) {
        int N = a.length;

        // For each node 1 <= i <= N / 2, if i is less than
        // either of its children, return false, meaning a[]
        // does not represent a maximum-ordered heap.
        // Otherwise, return true.
        for (int i = 1; i <= N / 2; i++)
        {
            // checks if left and right child are out of bounds
            if ((2 * i >= N) && (2 * i + 1 >= N))
            {
                continue;
            }

            // checks if right child is out of bounds
            if (2 * i + 1 >= N)
            {
                // checks if parent is less than left child
                if (less(a[i], a[2 * i]))
                {
                    return false;
                }
                return true;
            }

            // checks if parent is less than either of it's children
            if (less(a[i], a[2 * i]) || less(a[i], a[2 * i + 1]))
            {
                return false;
            }
        }
        return true;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        StdOut.println(maxOrderedHeap(a));
    }
}
