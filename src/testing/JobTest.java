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
	    assertTrue(new Job(false).getJobNumber() >= 1);
	}
	
	@Test
	public void testDuration() {
	    Job j = new Job(false);
	    assertTrue(j.getDuration() >= 1 && j.getDuration() <= 6);
	}
	
	@Test
	public void testArrivalTime() {
	    assertTrue(new Job(false).getArrivalTime() >= 0);
	}
	
	@Test
	public void testUpdate() {
	    Queue q = new Queue(1, "White", "Black");
	    Job j = new Job(false);
	    assertTrue(j.getQueueNumber() == 0);
	    assertTrue(!(j.isQueued()));
	    assertTrue(j.getIdleTime() == 0);
	    while(!(q.getInkColor().equals(j.getInkColor()) && q.getPaperColor().equals(j.getPaperColor()))) {
		j = new Job(false);
	    }
	    q.enqueue(j);
	    q.update();
	    assertTrue(q.peek(0).getQueueNumber() == 1);
	    assertTrue(q.peek(0).isQueued());
	    assertTrue(q.peek(0).getIdleTime() == 1);
	}
	
	@Test
	public void testComplete() {
	    Job j = new Job(false);
	    j.setQueued();
	    assertTrue(j.getDepartureTime() - Clock.getCurrentTick() ==  j.getDepartureTime());
	}
	
	@Test
	public void testQueued() {
	    Queue q = new Queue(1, "White", "Black");
	    Job j = new Job(false);
	    assertTrue(j.getQueueNumber() == 0);
	    assertTrue(!(j.isQueued()));
	    assertTrue(j.getIdleTime() == 0);
	    while(!(q.getInkColor().equals(j.getInkColor()) && q.getPaperColor().equals(j.getPaperColor()))) {
		j = new Job(false);
	    }
	    q.enqueue(j);
	    q.update();
	    assertTrue(q.peek(0).isQueued());
	}
	
}
