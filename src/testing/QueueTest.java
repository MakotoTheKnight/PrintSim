package testing;

import org.junit.Test;
import printsim.Queue;
import printsim.Job;

import static org.junit.Assert.*;

public class QueueTest {

    @Test
    public void testConstructor() {
        Queue q = new Queue(1, "White", "Black");
        assertTrue(q.getQueueID() == 1);
        assertTrue(q.getPaperColor().equals("White"));
        assertTrue(q.getInkColor().equals("Black"));
    }

    @Test
    public void testEnqueue() {
        Queue q = new Queue(1, "White", "Black");
        Job j;
        do {
            j = new Job(false);
        } while(!(j.getPaperColor().equals(q.getPaperColor()) && j.getInkColor().equals(q.getInkColor())));
        q.enqueue(j);
        assertTrue(q.peek(0).getQNumber() == q.getQueueID());
        assertTrue(q.peek(0).getInkColor().equals(q.getInkColor()));
        assertTrue(q.peek(0).getPaperColor().equals(q.getPaperColor()));
        assertTrue(q.peek(0).isContained());
    }

    @Test
    public void testDequeue() {
        Queue q = new Queue(1, "White", "Black");
        Job j;
        Job k;
        do {
            j = new Job(false);
        } while(!(j.getPaperColor().equals(q.getPaperColor()) && j.getInkColor().equals(q.getInkColor())));
        q.enqueue(j);
        k = q.dequeue();
        assertTrue(k.getQNumber() == q.getQueueID());
        assertTrue(q.getInkColor().equals(k.getInkColor()));
        assertTrue(q.getPaperColor().equals(k.getPaperColor()));
        assertFalse(k.isContained());

    }



}
