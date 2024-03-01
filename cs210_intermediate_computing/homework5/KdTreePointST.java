package edu.umb.cs210.p5;

import dsa.LinkedQueue;
import dsa.MaxPQ;
import dsa.Point2D;
import dsa.RectHV;
import stdlib.StdIn;
import stdlib.StdOut;

public class KdTreePointST<Value> implements PointST<Value> {
    // root of 2d tree
    Node root;
    // number of nodes(key/value pairs) in tree
    int N;
    
    // 2d-tree (generalization of a BST in 2d) representation.
    private class Node {
        private Point2D p;   // the point
        private Value val;   // the symbol table maps the point to this value
        private RectHV rect; // the axis-aligned rectangle corresponding to 
                             // this node
        private Node lb;     // the left/bottom subtree
        private Node rt;     // the right/top subtree

        // Construct a node given the point, the associated value, and the 
        // axis-aligned rectangle corresponding to the node.
        Node(Point2D p, Value val, RectHV rect) {
            this.p = p;
            this.val = val;
            this.rect = rect;
        }
    }

    // Construct an empty symbol table of points.
    public KdTreePointST() {
        // set size to 0
        this.N = 0;
        // set root to null
        this.root = null;
    }

    // Is the symbol table empty?
    public boolean isEmpty() { 
        return N == 0;
    }

    // Number of points in the symbol table.
    public int size() {
        return N;
    }

    // Associate the value val with point p.
    public void put(Point2D p, Value val) {
        // corner case for p
        if (p == null)
        {
            throw new NullPointerException("null argument");
        }
        // corner case for val
        if (val == null)
        {
            throw new NullPointerException("null argument");
        }
        // create root rectangle with + and - infinity as dimensions
        RectHV rectangle = new RectHV(Double.NEGATIVE_INFINITY,
                Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY);
        // call private method put() with appropriate arguments
        put(root, p, val, rectangle, true);

    }

    // Helper for put(Point2D p, Value val).
    private Node put(Node x, Point2D p, Value val, RectHV rect, boolean lr) {
        // check if no rect argument was given
        if (rect == null)
        {
            throw new NullPointerException("null argument");
        }
        // check to see if x is null (no more nodes, add a node, increase size)
        if (x == null)
        {
            // create a new Node to add
            Node newNode = new Node(p, val, rect);
            // increase size
            N++;
            // return the new node
            return newNode;
        }
        // check if key in x is the same as given key (update value) and
        // return it
        else if (p.equals(x.p))
        {
            // update value to given value
            x.val = val;
        }
        // insert given pair into left or right subtree
        else {
            // X aligned (lr = true)
            if (lr) {
                // Insert into LEFT subtree if given x is smaller than x.x
                if (p.x() < x.p.x()) {
                    // create a new rectangle with new x max = x.x dimension
                    RectHV newRectangle = new RectHV(rect.xmin(),
                            rect.ymin(), x.p.x(), rect.ymax());
                    // recursive call to put with newRectangle, x.lb and
                    // flipped lr
                    x.lb = put(x.lb, p, val, newRectangle, !lr);
                }
                // Insert into RIGHT subtree if given x is bigger than x.x
                else {
                    // make new rectangle with x min = x.x
                    RectHV newRectangle = new RectHV(x.p.x(), rect.ymin(),
                            rect.xmax(), rect.ymax());
                    // recursive call with new rectangle, x.rt and flipped lr
                    x.rt = put(x.rt, p, val, newRectangle, !lr);

                }
            }
            // Y aligned (lr == false)
            else
            {
                // insert into LEFT subtree if given y is smaller than x.y
                if (p.y() < x.p.y()) {
                    // create a new rectangle with y max = x.y
                    RectHV newRectangle = new RectHV(rect.xmin(), rect.ymin(),
                            rect.xmax(), x.p.y());
                    // insert with new changes
                    x.lb = put(x.lb, p, val, newRectangle, !lr);
                }
                // insert into RIGHT subtree if given y is bigger than x.y
                else {
                    // create new rectangle with y min = x.y
                    RectHV newRectangle = new RectHV(rect.xmin(), x.p.y(),
                            rect.xmax(), rect.ymax());
                    // insert with new changes
                    x.rt = put(x.rt, p, val, newRectangle, !lr);
                }
            }
        }
        return x;
    }

    // Value associated with point p.
    public Value get(Point2D p) {
        // corner case
        if (p == null)
        {
            throw new NullPointerException("null argument");
        }
        // return private get's return
        return get(root, p, true);
    }

    // Helper for get(Point2D p).
    private Value get(Node x, Point2D p, boolean lr) {
        // check if you've reached a dead end in tree, key not in tree
        if (x == null)
        {
            return null;
        }
        // check if given key = key in x, found key, return value
        if (p.equals(x.p))
        {
            return x.val;
        }
        // X aligned
        if (lr) {
            // search left
            if (p.x() < x.p.x()) {

                x.val = get(x.lb, p, !lr);
            }
            // search right
            else
            {
                x.val = get(x.rt, p, !lr);
            }
        }
        // y aligned
        else
        {
            // search left
            if (p.y() < x.p.y()) {

                x.val = get(x.lb, p, !lr);
            }
            // search right
            else
            {
                x.val = get(x.rt, p, !lr);
            }
        }
        return x.val;

    }

    // Does the symbol table contain the point p?
    public boolean contains(Point2D p) {
        // corner case
        if (p == null)
        {
            throw new NullPointerException("null argument");
        }
        // return if key exists or not
        return get(p) != null;
    }

    // All points in the symbol table, in level order.
    public Iterable<Point2D> points() {
        // queue to store points/keys
        LinkedQueue<Point2D> pointQueue = new LinkedQueue<Point2D>();
        // queue to store nodes and traverse tree
        LinkedQueue<Node> nodeQueue = new LinkedQueue<Node>();

        // insert the root into node queue, start at root
        nodeQueue.enqueue(root);
        // fill both queues
        while (!nodeQueue.isEmpty())
        {
            // delete current node and store it
            Node current = nodeQueue.dequeue();
            // insert the current node's left child
            if (current.lb == null || current.rt == null)
            {
                // insert the current node's point
                pointQueue.enqueue(current.p);
                continue;
            }
            nodeQueue.enqueue(current.lb);
            // insert the current node's right child
            nodeQueue.enqueue(current.rt);
            // insert the current node's point
            pointQueue.enqueue(current.p);
        }
        // return points in level order
        return pointQueue;
    }

    // All points in the symbol table that are inside the rectangle rect.
    public Iterable<Point2D> range(RectHV rect) {
        // corner case
        if (rect == null)
        {
            throw new NullPointerException("null argument");
        }
        // create an empty queue
        LinkedQueue<Point2D> queue = new LinkedQueue<Point2D>();
        // pass appropriate arguments into private range
        range(root, rect, queue);
        return queue;
    }

    // Helper for public range(RectHV rect).
    private void range(Node x, RectHV rect, LinkedQueue<Point2D> q) {
        // check if you've reached the end
        if (x == null)
        {
            return;
        }
        // range search pruning rule
        if (!x.rect.intersects(rect))
        {
            // don't do anything bc no way points in other rectangle
            // will be in the one we are looking at
            return;
        }
        // check if rect contains key in x
        if (rect.contains(x.p))
        {
            // insert x into queue
            q.enqueue(x.p);
        }
        // recursive call to left subtree
        range(x.lb, rect, q);
        // recursive call to right subtree
        range(x.rt, rect, q);
    }

    // A nearest neighbor to point p; null if the symbol table is empty.
    public Point2D nearest(Point2D p) {
        // corner case
        if (p == null)
        {
            throw new NullPointerException("null argument");
        }
        // check if symbol table is empty
        if (isEmpty())
        {
            return null;
        }
        // call private method w/ appropriate arguments
        return nearest(root, p, null, Double.POSITIVE_INFINITY, true);
    }
    
    // Helper for public nearest(Point2D p).
    private Point2D nearest(Node x, Point2D p, Point2D nearest, 
                            double nearestDistance, boolean lr) {
        // check if x is null and return nearest
        if (x == null)
        {
            return nearest;
        }
        // check if key in x is different from given and
        // distance between both points is smaller than nearest distance
        if (!p.equals(x.p) && p.distanceSquaredTo(x.p) < nearestDistance)
        {
            // update nearest and nearest distance
            nearest = x.p;
            nearestDistance = p.distanceSquaredTo(x.p);
        }
        // used to recursively call nearest() on left subtree
        if (p.distanceSquaredTo(x.lb.p) < p.distanceSquaredTo(x.rt.p))
        {
            x.lb.p = nearest(x.lb.p);
        }
        Point2D temp = nearest(x.lb.p);
        if (!temp.equals(nearest))
        {
            nearest = temp;
            nearestDistance = p.distanceSquaredTo(temp);
        }
        // recursively call right subtree
        x.rt.p = nearest(x.rt.p);
        // return nearest point
        return nearest;
    }

    // k points that are closest to point p.
    public Iterable<Point2D> nearest(Point2D p, int k) {
        // corner case
        if (p == null)
        {
            throw new NullPointerException("null argument");
        }
        // make an empty MaxPQ of point objects
        MaxPQ<Point2D> pq = new MaxPQ<Point2D>(p.distanceToOrder());
        // call nearest using to fill MaxPQ
        nearest(root, p, k, pq, true);
        // return MaxPQ
        return pq;
    }

    // Helper for public nearest(Point2D p, int k).
    private void nearest(Node x, Point2D p, int k, MaxPQ<Point2D> pq, 
                         boolean lr) {
        // check id x is null or if pq is greater than k
        if (x == null || pq.size() > k)
        {
            return;
        }
        // if key in x is different from give, insert it
        if (!p.equals(x.p))
        {
            pq.insert(p);
        }
        // check if size of pq is bigger than k
        if (pq.size() > k)
        {
            // remove the max
            pq.delMax();
        }
        // recursive call on left and right subtree
        nearest(x.lb, p, k, pq, !lr);
        nearest(x.rt, p, k, pq, !lr);
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        KdTreePointST<Integer> st = new KdTreePointST<Integer>();
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
