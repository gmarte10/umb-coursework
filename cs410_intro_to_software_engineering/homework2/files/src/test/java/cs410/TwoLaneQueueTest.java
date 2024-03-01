package cs410;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TwoLaneQueueTest {

    @Test
    void enqueueFast() {
        // Two TwoLaneQueue objects for testing
        TwoLaneQueue tlq = new TwoLaneQueue();
        TwoLaneQueue tlq2 = new TwoLaneQueue();

        // check if they are equal after enqueueing the same objects in fast lane
        // enqueue "Hello" to both objects in fast lane
        tlq.enqueueFast("Hello");
        tlq2.enqueueFast("Hello");
        assertEquals(tlq, tlq2);

        // check if they are not equal after enqueueing different objects in fast lane
        // enqueue "Bye" in first object
        tlq.enqueueFast("Bye");
        assertNotEquals(tlq, tlq2);
    }

    @Test
    void enqueueSlow() {
        // Two TwoLaneQueue objects for testing
        TwoLaneQueue tlq = new TwoLaneQueue();
        TwoLaneQueue tlq2 = new TwoLaneQueue();

        // check if they are equal after enqueueing the same objects in slow lane
        // enqueue "Pizza" to both objects in slow lane
        tlq.enqueueSlow("Pizza");
        tlq2.enqueueSlow("Pizza");
        assertEquals(tlq, tlq2);

        // check if they are not equal after enqueueing different objects in slow lane
        // enqueue "Chips" in first object
        tlq.enqueueSlow("Chips");
        assertNotEquals(tlq, tlq2);
    }

    @Test
    void dequeue() {
        // Two TwoLaneQueue objects for testing
        TwoLaneQueue tlq = new TwoLaneQueue();
        TwoLaneQueue tlq2 = new TwoLaneQueue();

        // dequeue from both objects throws NoSuchElements Exception
        assertThrows(NoSuchElementException.class, tlq::dequeue);

        // enqueue "Car", "Apple", "Flower", "Water", "Plane" to both objects in fast lane
        tlq.enqueueFast("Car");
        tlq.enqueueFast("Apple");
        tlq.enqueueFast("Flower");
        tlq.enqueueFast("Water");
        tlq.enqueueFast("Plane");

        tlq2.enqueueFast("Car");
        tlq2.enqueueFast("Apple");
        tlq2.enqueueFast("Flower");
        tlq2.enqueueFast("Water");
        tlq2.enqueueFast("Plane");

        // enqueue "Tiger", "Bird", "Dog", "Whale" to both objects in slow lane
        tlq.enqueueSlow("Tiger");
        tlq.enqueueSlow("Bird");
        tlq.enqueueSlow("Dog");
        tlq.enqueueSlow("Whale");

        tlq2.enqueueSlow("Tiger");
        tlq2.enqueueSlow("Bird");
        tlq2.enqueueSlow("Dog");
        tlq2.enqueueSlow("Whale");

        // dequeue from both objects and check if equal strings
        assertEquals(tlq.dequeue(), tlq2.dequeue());

        // dequeue from tlq and check if returned string equals to "Apple"
        assertEquals("Apple", tlq.dequeue());

        // dequeue 2 more times from tlq2
        tlq2.dequeue();
        tlq2.dequeue();

        // check if dequeue is equal to the first element in slow queue
        assertEquals("Tiger", tlq2.dequeue());

        // check if dequeue is equal to "Water" from the fast lane
        assertEquals("Water", tlq2.dequeue());

        // check if dequeue is not equal to "Bird"
        assertNotEquals("Bird", tlq2.dequeue());

        // check if dequeue is equal to Bird from slow lane
        assertEquals("Bird", tlq2.dequeue());
    }
}