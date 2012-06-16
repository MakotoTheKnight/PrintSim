/**
 *  Testing the details of the Job object.
 *  Geared to succeed based on the current specifications of the existing project. 
 */

package testing;

import printsim.Job;
import printsim.Queue;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assume.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.hamcrest.BaseMatcher;
import org.hamcrest.core.AnyOf;
import junit.framework.TestSuite;

public class JobTest {
	
	@Test
	public void testJobNumber() {
		assertTrue(new Job().getJobNumber() >= 1);
	}
	
	@Test
	public void testDuration() {
		Job j = new Job();
		assertTrue(j.getDuration() >= 1 && j.getDuration() <= 6);
	}
	
	@Test
	public void testArrivalTime() {
		assertTrue(new Job().getArrivalTime() >= 0);
	}
	
	@Test
	public void testUpdate() {
		Queue q = new Queue(1, "White", "Black");
		Job j = new Job();
		assertTrue(j.getQueueNumber() == 0);
		assertTrue(!(j.isQueued()));
		assertTrue(j.getIdleTime() == 0);
		q.enqueue(j);
		q.update();
		assertTrue(q.peek(0).getQueueNumber() == 1);
		assertTrue(q.peek(0).isQueued());
		assertTrue(q.peek(0).getIdleTime() == 1);
	}
	
	//@Test
	public void testComplete() {
	}
	
	//@Test
	public void testQueued() {
	}
	
}
