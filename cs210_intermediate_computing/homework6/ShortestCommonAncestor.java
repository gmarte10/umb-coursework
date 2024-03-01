package edu.umb.cs210.p6;

import dsa.DiGraph;
import dsa.LinkedQueue;
import dsa.SeparateChainingHashST;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

// An immutable data type for computing shortest common ancestors.
public class ShortestCommonAncestor {
    // rooted DAG
    DiGraph G;

    // Construct a ShortestCommonAncestor object given a rooted DAG.
    public ShortestCommonAncestor(DiGraph G) {
        // check if G is null
        if (G == null) {
            throw new NullPointerException("G is null");
        }
        // initialize G (instance)
        this.G = defensiveCopy(G);
    }

    // Length of the shortest ancestral path between v and w.
    public int length(int v, int w) {
        // check if v is or w is out of bounds
        if (v < 0 || v > G.V()) {
            throw new IndexOutOfBoundsException("v is out of bounds");
        }
        if (w < 0 || w > G.V()) {
            throw new IndexOutOfBoundsException("w is out of bounds");
        }

        int ancestor = ancestor(v, w);
        // sum distances from ancestor & return
        return distFrom(v).get(ancestor) + distFrom(w).get(ancestor);
    }

    // Shortest common ancestor of vertices v and w.
    public int ancestor(int v, int w) {
        // check if v or w is out of bounds
        if (v < 0 || v > G.V()) {
            throw new IndexOutOfBoundsException("v is out of bounds");
        }
        if (w < 0 || w > G.V()) {
            throw new IndexOutOfBoundsException("w is out of bounds");
        }

        // declare variables
        int min = Integer.MAX_VALUE;
        int current;
        int sca = 0;
        // get shortest common ancestor
        for (Integer x : distFrom(v).keys()) {
            if (distFrom(w).contains(x)) {
                // get current distance
                current = distFrom(v).get(x) + distFrom(w).get(x);
                // check if distance is the smallest
                if (current < min) {
                    // update
                    min = current;
                    sca = x;
                }
            }
        }
        return sca;
    }

    // Length of the shortest ancestral path of vertex subsets A and B.
    public int length(Iterable<Integer> A, Iterable<Integer> B) {
        // check if A or B is null
        if (A == null) {
            throw new NullPointerException("A is null");
        }
        if (B == null) {
            throw new NullPointerException("B is null");
        }

        // check if A or B has 0 vertices
        if (!A.iterator().hasNext()) {
            throw new IllegalArgumentException("A is empty");
        }
        if (!B.iterator().hasNext()) {
            throw new IllegalArgumentException("B is empty");
        }

        // array to hold => [sca, v, w]
        int[] threeElem = triad(A, B);
        // length of v & w
        return length(threeElem[1], threeElem[2]);
    }

    // A shortest common ancestor of vertex subsets A and B.
    public int ancestor(Iterable<Integer> A, Iterable<Integer> B) {
        // check if A or B is null
        if (A == null) {
            throw new NullPointerException("A is null");
        }
        if (B == null) {
            throw new NullPointerException("B is null");
        }

        // check if A or B has 0 vertices
        if (!A.iterator().hasNext()) {
            throw new IllegalArgumentException("A is empty");
        }
        if (!B.iterator().hasNext()) {
            throw new IllegalArgumentException("B is empty");
        }

        // array to hold => [sca, v, w]
        int[] threeElem = triad(A, B);
        // sca
        return threeElem[0];
    }

    // Helper: Return a map of vertices reachable from v and their 
    // respective shortest distances from v.
    private SeparateChainingHashST<Integer, Integer> distFrom(int v) {
        // stores => <vertex, shortest distance>
        SeparateChainingHashST<Integer, Integer> map =
                new SeparateChainingHashST<Integer, Integer>();
        // keeps track of vertex
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();

        // manual Breadth First Search
        map.put(v, 0);
        queue.enqueue(v);
        // calculate & put distance into map
        while (!queue.isEmpty()) {
            // current vertex
            int current = queue.dequeue();
            for (int i : G.adj(current)) {
                // insert vertex & distance
                map.put(i, map.get(current) + 1);
                // enqueue vertex
                queue.enqueue(i);
            }
        }
        return map;
    }

    // Helper: Return an array consisting of a shortest common ancestor a 
    // of vertex subsets A and B, and vertex v from A and vertex w from B 
    // such that the path v-a-w is the shortest ancestral path of A and B.
    private int[] triad(Iterable<Integer> A, Iterable<Integer> B) {
        // check if A or B has 0 vertices
        if (!A.iterator().hasNext()) {
            throw new IllegalArgumentException("A is null");
        }
        if (!B.iterator().hasNext()) {
            throw new IllegalArgumentException("B is null");
        }

        // declare variables
        int[] threeElem = new int[3]; // [sca, v, w]
        int dist, ancestor;
        int min = Integer.MAX_VALUE;

        // fill threeElem
        for (Integer v : A) {
            for (Integer w : B) {
                // get distance
                dist = length(v, w);
                // check if it is smallest distance
                if (dist < min) {
                    min = dist;
                    // sca
                    ancestor = ancestor(v, w);
                    // update array
                    threeElem[0] = ancestor;
                    threeElem[1] = v;
                    threeElem[2] = w;
                }
            }
        }
        return threeElem;
    }

    // helper method that produces a defensive copy of G
    private DiGraph defensiveCopy(DiGraph g) {
        DiGraph copy = new DiGraph(g.V());
        for (int from = 0; from < g.V(); from++) {
            for (int to : g.adj(from)) {
                copy.addEdge(from, to);
            }
        }
        return copy;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        DiGraph G = new DiGraph(in);
        in.close();
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
