package edu.umb.cs210.p1;

import stdlib.StdOut;

public class GreatCircle {
    // calculates the great circle distance given two sets of coordinates
    protected static double calculateGreatCircleDistance(String[] args) {
        // Get angles lat1, lon1, lat2, and lon2 from command line as
        // doubles.
        double lat1 = Double.parseDouble(args[0]);
        double lon1 = Double.parseDouble(args[1]);
        double lat2 = Double.parseDouble(args[2]);
        double lon2 = Double.parseDouble(args[3]);

        double x1 = Math.toRadians(lat1);
        double y1 = Math.toRadians(lon1);
        double x2 = Math.toRadians(lat2);
        double y2 = Math.toRadians(lon2);

        // Calculate great-circle distance d.
        double d = Math.acos(Math.sin(x1) * Math.sin(x2) +
                Math.cos(x1) * Math.cos(x2) * Math.cos(y1 - y2));
        d = Math.toDegrees(d);
        d = d * 111;
        // Return d.
        return d;
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        StdOut.println(GreatCircle.calculateGreatCircleDistance(args));
    }
}
