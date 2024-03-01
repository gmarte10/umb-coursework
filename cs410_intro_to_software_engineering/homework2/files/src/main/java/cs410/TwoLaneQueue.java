package cs410;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/*
Represents a fast and slow lane. The items contained in each lane are String objects.
Responsibilities: enqueueFast, enqueueSlow and dequeue
 */
public class TwoLaneQueue {
    /*
    Fast queue, slow queue and a count for how many times fast lane is dequeued.
     */
    private final Queue<String> fast;
    private final Queue<String> slow;
    private int fastCount;
    public TwoLaneQueue() {
        fast = new LinkedList<>();
        slow = new LinkedList<>();
        fastCount = 0;
    }

    // add String to fast lane
    public void enqueueFast(String s) {
        fast.add(s);
    }

    // add String to slow lane
    public void enqueueSlow(String s) {
        slow.add(s);
    }

    /*
    If both lanes are empty throw an error. If fast lane has been dequeued 3 times in a row or more
    (if fastCount >= 3) then dequeue from slow lane. Reset fastCount to 0 after slow dequeue. Increase fastCount
    by one after fast dequeue. If fast lane is empty, dequeue from slow lane.
     */
    public String dequeue() {
        if (fast.isEmpty() && slow.isEmpty()) {
            throw new NoSuchElementException();
        }
        if (fastCount >= 3 && !slow.isEmpty()) {
            fastCount = 0;
            return slow.remove();
        }
        if (fast.isEmpty()) {
            fastCount = 0;
            return slow.remove();
        }
        ++fastCount;
        return fast.remove();
    }

    // method to check if two TwoLaneQueue objects are equal, used for testing
    @Override
    public boolean equals(Object other) {
        if (other instanceof TwoLaneQueue) {
            TwoLaneQueue otherTlq = (TwoLaneQueue) other;
            return (this.fast.equals(otherTlq.fast) && this.slow.equals(otherTlq.slow) && this.fastCount == otherTlq.fastCount);
        }
        return false;
    }
}
