package edu.umb.cs210.p3;

import stdlib.StdIn;
import stdlib.StdOut;

import java.util.Arrays;
import java.util.Comparator;

// An immutable data type representing a 3D point.
public class Point3D implements Comparable<Point3D> {
    private final double x; // x coordinate
    private final double y; // y coordinate
    private final double z; // z coordinate

    // Construct a point in 3D given its coordinates.
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // The Euclidean distance between this point and that.
    public double distance(Point3D that) {
        // use euclidean distance formula
        double distance = Math.sqrt((Math.pow(this.x - that.x, 2))
                + (Math.pow(this.y - that.y, 2)) +
                (Math.pow(this.z - that.z, 2)));
        return distance;
    }

    // -1, 0, or 1 depending on this point's Euclidean
    // distance to the origin (0, 0, 0) is less than,
    // equal to, or greater than that point's Euclidean
    // distance to the origin.
    public int compareTo(Point3D that) {
        Point3D origin = new Point3D(0, 0, 0);
        double thisOrigin = this.distance(origin);
        double thatOrigin = that.distance(origin);

        if (Double.compare(thisOrigin, thatOrigin) == -1)
        {
            return -1;
        }
        else if (Double.compare(thisOrigin, thatOrigin) == 0)
        {
            return 0;
        }
        return 1;

    }

    // An x-coordinate comparator.
    public static Comparator<Point3D> xOrder() {
        return new XOrder();
    }

    // Helper x-coordinate comparator.
    private static class XOrder implements Comparator<Point3D> {
        // -1, 0, or 1 depending on whether p1's x-coordinate
        // is less than, equal to, or greater than p2's
        // x-coordinate.
        public int compare(Point3D p1, Point3D p2) {
            if (Double.compare(p1.x, p2.x) == -1)
            {
                return -1;
            }
            else if (Double.compare(p1.x, p2.x) == 0)
            {
                return 0;
            }
            return 1;
        }
    }

    // A y-coordinate comparator.
    public static Comparator<Point3D> yOrder() {
        return new YOrder();
    }
    
    // Helper y-coordinate comparator.
    private static class YOrder implements Comparator<Point3D> {
        // -1, 0, or 1 depending on whether p1's y-coordinate
        // is less than, equal to, or greater than p2's
        // y-coordinate.
        public int compare(Point3D p1, Point3D p2) {
            if (Double.compare(p1.y, p2.y) == -1)
            {
                return -1;
            }
            else if (Double.compare(p1.y, p2.y) == 0)
            {
                return 0;
            }
            return 1;
        }
    }

    // A z-coordinate comparator.
    public static Comparator<Point3D> zOrder() {
        return new ZOrder();
    }

    // Helper z-coordinate comparator.
    private static class ZOrder implements Comparator<Point3D> {
        // -1, 0, or 1 depending on whether p1's z-coordinate
        // is less than, equal to, or greater than p2's
        // z-coordinate.
        public int compare(Point3D p1, Point3D p2) {
            if (Double.compare(p1.z, p2.z) == -1)
            {
                return -1;
            }
            else if (Double.compare(p1.z, p2.z) == 0)
            {
                return 0;
            }
            return 1;
        }
    }

    // A string representation of the point, as "(x, y, z)".
    public String toString() {
        String point = "(" + x + ", " + y + ", " + z + ")";
        return point;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        StdOut.print("How many points?: ");
        int n = StdIn.readInt();
        Point3D[] points = new Point3D[n];
        StdOut.printf("Enter %d doubles, separated by whitespace: ", n*3);
        for (int i = 0; i < n; i++) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            double z = StdIn.readDouble();
            points[i] = new Point3D(x, y, z);
        }
        StdOut.println("\nHere are the points in the order entered.");
        for (Point3D point : points) {
            StdOut.println(point);
        }
        Arrays.sort(points);
        StdOut.println("Sorted by their natural ordering (compareTo).");
        for (Point3D point : points) {
            StdOut.println(point);
        }
        Arrays.sort(points, Point3D.xOrder());
        StdOut.println("Sorted by their x value (xOrder).");
        for (Point3D point : points) {
            StdOut.println(point);
        }
        Arrays.sort(points, Point3D.yOrder());
        StdOut.println("Sorted by their y value (yOrder).");
        for (Point3D point : points) {
            StdOut.println(point);
        }
        Arrays.sort(points, Point3D.zOrder());
        StdOut.println("Sorted by their z value (zOrder).");
        for (Point3D point : points) {
            StdOut.println(point);
        }
    }
}
