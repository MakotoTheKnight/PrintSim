/**
 *  Testing the details of the Job object.
 *  Geared to succeed based on the current specifications of the existing project.
 */

package testing;

import printsim.Job;
import printsim.Queue;
import printsim.Clock;
import org.junit.Test;
import static org.junit.Assert.*;

public class JobTest {

    @Test
    public void testJobNumber() {
        assertTrue(new Job(false).getTaskNumber() >= 1);
    }

    @Test
    public void testDuration() {
        Job j = new Job(false);
        assertTrue(j.getTimeWorking() >= 1 && j.getTimeWorking() <= 6);
    }

    @Test
    public void testArrivalTime() {
        assertTrue(new Job(false).getTimeCreated() >= 0);
    }

    @Test
    public void testUpdate() {
        Queue q = new Queue(1, "White", "Black");
        Job j = new Job(false);
        assertTrue(j.getQNumber() == 0);
        assertTrue(!(j.isContained()));
        assertTrue(j.getIdleTime() == 0);
        while(!(q.getInkColor().equals(j.getInkColor()) && q.getPaperColor().equals(j.getPaperColor()))) {
            j = new Job(false);
        }
        q.enqueue(j);
        q.update();
        assertTrue(q.peek(0).getQNumber() == 1);
        assertTrue(q.peek(0).isContained());
        assertTrue(q.peek(0).getIdleTime() == 1);
    }

    @Test
    public void testComplete() {
        Job j = new Job(false);
        j.setContained();
        assertTrue(j.getTimeComplete() - Clock.getCurrentTick() ==  j.getTimeComplete());
    }

    @Test
    public void testQueued() {
        Queue q = new Queue(1, "White", "Black");
        Job j = new Job(false);
        assertTrue(j.getQNumber() == 0);
        assertTrue(!(j.isContained()));
        assertTrue(j.getIdleTime() == 0);
        while(!(q.getInkColor().equals(j.getInkColor()) && q.getPaperColor().equals(j.getPaperColor()))) {
            j = new Job(false);
        }
        q.enqueue(j);
        q.update();
        assertTrue(q.peek(0).isContained());
    }

    @Test
    public void testCanPrint() {
        Queue q = new Queue(1, "White", "Black");
        Job j = new Job(false);
        while(!(q.getInkColor().equals(j.getInkColor()) && q.getPaperColor().equals(j.getPaperColor()))) {
            j = new Job(false);
        }
        q.enqueue(j);
        q.update();
        assertTrue(q.peek(0).canPrint());
    }

}
