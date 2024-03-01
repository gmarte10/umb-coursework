package edu.umb.cs210.p5;

import dsa.LinkedQueue;
import dsa.MinPQ;
import dsa.Point2D;
import dsa.RectHV;
import dsa.RedBlackBinarySearchTreeST;
import stdlib.StdIn;
import stdlib.StdOut;


public class BrutePointST<Value> implements PointST<Value> {
    // bst to store key/value pairs
    RedBlackBinarySearchTreeST<Point2D, Value> bst;

    // Construct an empty symbol table of points.
    public BrutePointST() {
        bst = new RedBlackBinarySearchTreeST<Point2D, Value>();
    }

    // Is the symbol table empty?
    public boolean isEmpty() { 
        return bst.isEmpty();
    }

    // Number of points in the symbol table.
    public int size() {
        return bst.size();
    }

    // Associate the value val with point p.
    public void put(Point2D p, Value val) {
        // corner case
        if (p == null || val == null)
        {
            throw new NullPointerException("null argument");
        }
        bst.put(p, val);
    }

    // Value associated with point p.
    public Value get(Point2D p) {
        // corner case
        if (p == null)
        {
            throw new NullPointerException("null argument");
        }
        return bst.get(p);
    }

    // Does the symbol table contain the point p?
    public boolean contains(Point2D p) {
        // corner case
        if (p == null)
        {
            throw new NullPointerException("null argument");
        }
        return bst.contains(p);
    }

    // All points in the symbol table.
    public Iterable<Point2D> points() {
        return bst.keys();
    }

    // All points in the symbol table that are inside the rectangle rect.
    public Iterable<Point2D> range(RectHV rect) {
        // corner case
        if (rect == null)
        {
            throw new NullPointerException("null argument");
        }
        // create a queue to store points
        LinkedQueue<Point2D> queue = new LinkedQueue<Point2D>();
        // go through all points in bst
        for (Point2D point : bst.keys())
        {
            // check if rectangle contains point
            if (rect.contains(point))
            {
                // put point into queue
                queue.enqueue(point);
            }
        }
        return queue;
    }

    // A nearest neighbor to point p; null if the symbol table is empty.
    public Point2D nearest(Point2D p) {
        // corner case
        if (p == null)
        {
            throw new NullPointerException("null argument");
        }
        // check if bst is empty
        if (bst.isEmpty())
        {
            return null;
        }
        // number used to get closest distance
        double min = 100000;
        // used to get closest point
        Point2D nearest = new Point2D(0, 0);
        // go through points in bst
        for (Point2D point : bst.keys())
        {
            // check if key is different than given
            if (!point.equals(p))
            {
                // check if it is nearest distance
                if (p.distanceSquaredTo(point) < min)
                {
                    // set point as nearest
                    nearest = point;
                    // update smallest distance
                    min = p.distanceSquaredTo(point);
                }
            }
        }
        return nearest;
    }

    // k points that are closest to point p.
    public Iterable<Point2D> nearest(Point2D p, int k) {
        // corner case
        if (p == null)
        {
            throw new NullPointerException("null argument");
        }
        // check if bst has no key/value pairs
        if (bst.isEmpty())
        {
            return null;
        }
        // pq to store points w/ comparator
        MinPQ<Point2D> pq = new MinPQ<Point2D>(p.distanceToOrder());
        // go through keys in bst
        for (Point2D point : bst.keys())
        {
            // insert into pq
            pq.insert(point);
        }

        // stores current point
        Point2D currentP;
        // pq to store k nearest points
        MinPQ<Point2D> pqK = new MinPQ<Point2D>(p.distanceToOrder());
        // store k nearest points
        for (int i = 0; i < k; i++)
        {
            // store current smallest point
            currentP = pq.delMin();
            // check if point is different from given
            if (!p.equals(currentP))
            {
                // insert point into pqk
                pqK.insert(currentP);
            }
        }
        // return pqk with k nearest points
        return pqK;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        BrutePointST<Integer> st = new BrutePointST<Integer>();
        double qx = Double.parseDouble(args[0]);
        double qy = Double.parseDouble(args[1]);
        double rx1 = Double.parseDouble(args[2]);
        double rx2 = Double.parseDouble(args[3]);
        double ry1 = Double.parseDouble(args[4]);
        double ry2 = Double.parseDouble(args[5]);
        int k = Integer.parseInt(args[6]);
        Point2D query = new Point2D(qx, qy);
        RectHV rect = new RectHV(rx1, ry1, rx2, ry2);
        int i = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            st.put(p, i++);
        }
        StdOut.println("st.empty()? " + st.isEmpty());
        StdOut.println("st.size() = " + st.size());
        StdOut.println("First " + k + " values:");
        i = 0;
        for (Point2D p : st.points()) {
            StdOut.println("  " + st.get(p));
            if (i++ == k) {
                break;
            }
        }
        StdOut.println("st.contains(" + query + ")? " + st.contains(query));
        StdOut.println("st.range(" + rect + "):");
        for (Point2D p : st.range(rect)) {
            StdOut.println("  " + p);
        }
        StdOut.println("st.nearest(" + query + ") = " + st.nearest(query));
        StdOut.println("st.nearest(" + query + ", " + k + "):");
        for (Point2D p : st.nearest(query, k)) {
            StdOut.println("  " + p);
        }
    }
}
