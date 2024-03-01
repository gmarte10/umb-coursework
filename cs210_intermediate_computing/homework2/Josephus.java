package edu.umb.cs210.p2;

import dsa.LinkedQueue;
import stdlib.StdOut;

public class Josephus {
    protected static LinkedQueue<Integer> calculateJosephus(String[] args) {
        // Get N and M from command line as ints.


        // Create a queue q and enqueue integers
        // 1, 2, ... N.


        // Create a queue outQ to store the ordering


        int i = 0;
        // As long as q is not empty: increment i;
        // dequeue an element pos; if M divides i,
        // enqueue pos to outQ, otherwise enqueue pos to q.


        // Return outQ
        return null;
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        for (int i : calculateJosephus(args)) {
            StdOut.println(i);
        }
    }
}
