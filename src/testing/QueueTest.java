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
}
