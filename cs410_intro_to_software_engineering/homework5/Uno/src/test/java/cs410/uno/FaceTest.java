package cs410.uno;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/* Tests the individual components of the Face class.
 */
class FaceTest {
    @Test
    void digit() {
        Face face = new Face(8);
        assertEquals(8, face.digit().get());
    }

    @Test
    void instruction() {
        Face face = new Face("Skip");
        assertEquals("Skip", face.instruction().get());
    }

    @Test
    void testEquals() {
        Face f1 = new Face("Reverse");
        Face f2 = new Face("Reverse");
        Face f3 = new Face(3);
        Face f4 = new Face(3);
        assertEquals(f1, f2);
        assertEquals(f3, f4);
        assertNotEquals(f1, f4);
    }
}