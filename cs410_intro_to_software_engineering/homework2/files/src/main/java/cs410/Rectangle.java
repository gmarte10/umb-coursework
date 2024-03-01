package cs410;

import org.w3c.dom.css.Rect;

import static java.lang.Math.*;

/*
Represents a rectangle on a 2d grid with integer coordinates (x1, y1), (x2, y2) that
represent opposite corners of the rectangle.
Responsibilities: factory method with opposite corner points, calculate the area of the rectangle, check if the
rectangle contains a point and check if two rectangles overlap.
 */
public class Rectangle {
    /*
    x1 != x2 or y1 != y2 -> because a line, not a rectangle is formed
    x1 != x2 != y1 != y2 -> because it will just be equivalent to one point
    Example: (0, 4), (0, 3), (5, 5), (5, 5)
    coordinates can have negative integers
     */
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    // returns a rectangle from the given arguments
    public static Rectangle of(int x1, int y1, int x2, int y2) {
        return new Rectangle(x1, y1, x2, y2);
    }

    // calculates and returns the area of the rectangle
    // Example: Area of (0, 2), (5, 0) is 10
    public int area() {
        int w = abs(this.x1 - this.x2);
        int l = abs(this.y1 - this.y2);
        return w * l;
    }

    // checks if the given point is inside the rectangle
    /*
    Example: Rectangle(2, 2, 8, -1).contains(4, 0) = true
     */
    public boolean contains(int x, int y) {
        int xlo = Math.min(this.x1, this.x2);
        int ylo = Math.min(this.y1, this.y2);
        int xhi = Math.max(this.x1, this.x2);
        int yhi = Math.max(this.y1, this.y2);

        return (x >= xlo) && (x <= xhi) && (y >= ylo) && (y <= yhi);
    }

    // checks if two rectangles overlap with one another on the grid
    /*
    Example: Rectangle(1, 4, 10, 2).overlaps(Rectangle(0, 2, 6, 5) = true
     */
    public boolean overlaps(Rectangle other) {
        boolean other1 = other.contains(this.x1, this.y1) || other.contains(this.x2, this.y2);
        boolean other2 = other.contains(this.x1, this.y2) || other.contains(this.x2, this.y1);

        boolean this1 = this.contains(other.x1, other.y1) || this.contains(other.x2, other.y2);
        boolean this2 = this.contains(other.x1, other.y2) || this.contains(other.x2, other.y1);

        return other1 || other2 || this1 || this2;
    }

    // method to check if two Rectangle objects are equal
    @Override
    public boolean equals(Object other) {
        if (other instanceof Rectangle) {
            Rectangle otherR = (Rectangle) other;
            return (this.x1 == otherR.x1 && this.x2 == otherR.x2 && this.y1 == otherR.y1
                    && this.y2 == otherR.y2);
        }
        return false;
    }
}
