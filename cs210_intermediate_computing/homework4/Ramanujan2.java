package edu.umb.cs210.p4;

import dsa.MinPQ;
import stdlib.StdOut;

public class Ramanujan2 {
    // A data type that encapsulates a pair of numbers (i, j) 
    // and the sum of their cubes, ie, i^3 + j^3.
    private static class Pair implements Comparable<Pair> {
        private int i;          // first element of the pair
        private int j;          // second element of the pair
        private int sumOfCubes; // i^3 + j^3

        // Construct a pair (i, j).
        Pair(int i, int j) {
            this.i = i;
            this.j = j;
            sumOfCubes = i * i * i + j * j * j;
        }

        // Compare this pair to the other by sumOfCubes.
        public int compareTo(Pair other) {
            return sumOfCubes - other.sumOfCubes;
        }
    }

    public static void main(String[] args) {
        // gets the input
        int N = Integer.parseInt(args[0]);
        // creates a MinPQ
        MinPQ<Pair> pq = new MinPQ<Pair>();

        // initialize pq with pairs (i, i + 1)
        for (int i = 0; i * i * i < N; i++)
        {
            // creates a pair by making a Pair object
            Pair x = new Pair(i, i + 1);
            // inserts the Pair object with pair into MinPQ
            pq.insert(x);
        }

        while (!pq.isEmpty())
        {
            // current pair stored to be used as previous and is deleted from pq
            Pair previous = pq.delMin();
            // breaks the loop if pq is empty to prevent pq underflow error
            // that I kept getting
            if (pq.isEmpty())
            {
                break;
            }
            // checks to see if previous pair = current pair(pq.min() pair)
            // and is less than or equal to N
            if (previous.sumOfCubes == pq.min().sumOfCubes &&
                    pq.min().sumOfCubes <= N)
            {
                StdOut.printf("%d = %d^3 + %d^3 = %d^3 + %d^3\n",
                        pq.min().sumOfCubes, previous.i, previous.j,
                        pq.min().i, pq.min().j);
            }
            // checks if current(pq.min) j is less than cube root of N
            if (pq.min().j * pq.min().j * pq.min().j < N)
            {
                // constructs a pair (current i, current j + 1) for insertion
                // by making Pair object
                Pair forInsert = new Pair(pq.min().i, pq.min().j + 1);
                // inserts the new pair
                pq.insert(forInsert);
            }
        }



    }
}
