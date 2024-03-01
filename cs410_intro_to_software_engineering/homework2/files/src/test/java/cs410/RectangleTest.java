package cs410;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void of() {
        // test representation
        // Example: Rectangle made by (4, 10) and (0, 3) is represented by a rectangle object
        assertEquals(new Rectangle(4,10,0, 3), Rectangle.of(4, 10, 0, 3));

        // given (40, -2), (4, -8) check if not equal to the rectangle of (2, 0), (-1, -19)
        assertNotEquals(new Rectangle(2, 0, -1, -19), Rectangle.of(40, -2, 4, 8));
    }

    @Test
    void area() {
        // given a rectangle made by (0, 5), (4, 0), it's area will return 20
        assertEquals(20, Rectangle.of(0, 5, 4, 0).area());

        // check if the area of (10, 2), (0, -4) is not equal to 188
        assertNotEquals(188, Rectangle.of(10, 2, 0, -4).area());
    }

    @Test
    void contains() {
        // check if the point (5, 2) is inside the rectangle made by (2, 8), (10, 1)
        assertTrue(Rectangle.of(2, 8, 10, 1).contains(5, 2));

        // if point (4, 0) is in Rectangle(2, 2, 8, -1)
        assertTrue(Rectangle.of(2, 2, 8, -1).contains(4, 0));

        // check if (0, 0), (5, 4) does not contain the point (-1, 0)
        assertFalse(Rectangle.of(0, 0, 5, 4).contains(-1, 0));
    }

    @Test
    void overlaps() {
        // check if the rectangle made by (1, 8), (5, 3) overlaps with the rectangle made by (2, 4), (9, 1)
        assertTrue(Rectangle.of(1, 8, 5, 4).overlaps(Rectangle.of(2, 4, 9, 1)));

        // check if the rectangle made by (0, 1), (5, 7) does not overlap with the rectangle made by (2, -4), (10, -7)
        assertFalse((Rectangle.of(0, 1, 5, 7).overlaps(Rectangle.of(2, -4, 10, -7))));
    }
}