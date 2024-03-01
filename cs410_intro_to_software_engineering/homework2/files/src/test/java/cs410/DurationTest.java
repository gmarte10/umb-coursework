package cs410;

import static org.junit.jupiter.api.Assertions.*;

class DurationTest {

    @org.junit.jupiter.api.Test
    void of() {
        // Test actual representation
        // Example: the duration of 4hr, 25m, 50s is represented by 4:25:50, which is 15950 seconds
        assertEquals(new Duration(4,25,50), Duration.of(15950));

        // check if 15:21:01 is equal to the duration of 55261s
        assertEquals(new Duration(15, 21, 1), Duration.of(55261));

        // check if 0:05:25 is not equal to the duration of 150s
        assertNotEquals(new Duration(0, 5, 25), Duration.of(150));

        // given -250s throws
        assertThrows(IllegalArgumentException.class, () -> Duration.of(-250));
    }

    @org.junit.jupiter.api.Test
    void testOf() {
        // Testing the representation using the hr, min, sec
        // is the duration of 10:20:30 as int arguments the same
        assertEquals(new Duration(10, 20, 30), Duration.of(10, 20, 30));

        // check if the duration of 100:50:10 is equal to the duration returned by of(7, 20, 12)
        assertNotEquals(new Duration(100, 50, 10), Duration.of(7, 20, 12));

        // given 0:-02:01 throws
        assertThrows(IllegalArgumentException.class, () -> Duration.of(0, -2, 1));
    }

    @org.junit.jupiter.api.Test
    void seconds() {
        // Testing total seconds of a duration object
        // given 4 hours 25min and 50s return 15950 seconds
        assertEquals(15950, Duration.of(4,25, 50).seconds());

        // given 58:48:57, return 211737
        assertEquals(211737, Duration.of(58, 48, 57).seconds());

        // given 10:05:08, check if the total seconds is not equal to 100s
        assertNotEquals(100, Duration.of(10, 5, 8).seconds());
    }

    @org.junit.jupiter.api.Test
    void add() {
        // given two durations of 400 seconds, return a duration of 800 seconds
        assertEquals(Duration.of(400).add(Duration.of(400)), Duration.of(800));

        // adding 15:21:01 and 32:58:59, returns 48:20:00
        assertEquals(Duration.of(48, 20, 0), Duration.of(15, 21, 1).add(Duration.of(32, 58, 59)));

        assertNotEquals(Duration.of(203), Duration.of(10).add(Duration.of(15, 8, 19)));
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        // given the string "10:20:05", return the same string from a duration
        assertEquals("10:20:05", Duration.of(10, 20, 5).toString());

        // the toString of the duration 0:19:04 is equal to "0:19:04"
        assertEquals("0:19:04", Duration.of(0, 19, 4).toString());

        // check if "0:00:00" is not equal to the string of the duration 10:05:01
        assertNotEquals("00:00:00", Duration.of(10, 5, 1).toString());
    }
}