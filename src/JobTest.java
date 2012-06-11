/**
 *	Testing the details of the Job object.
 *  Geared to succeed based on the current specifications of the existing project. 
 */

package testing;

import printsim.Clock;
import printsim.Job;
import printsim.Queue;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class JobTest {
	
	@Test
	public boolean testDefaultConstructor() {
		boolean[] fieldTests = new boolean[4];
		String[] paperColors = {"White", "Goldenrod", "Gray"};
		String[] inkColors = {"Black", "Gray"};
		Job j = new Job();
		//assertTrue(j.getJobNumber() >= 1);
		//assertTrue(j.getDuration() >= 1 && j.getDuration() <= 6);
		//assertEquals(j.get >= 1 && j.getDuration() <= 6);
		//assertTrue(j.getDuration() >= 1 && j.getDuration() <= 6);
		System.out.println("Job number = " + j.getJobNumber());
		System.out.println("Job duration = " + j.getDuration());
		System.out.println("Job paper color = " + j.getPaperColor());
		System.out.println("Job ink color = " + j.getInkColor());
		//Assert a = new Assert();
		//a.assertTrue(j.
		return false;
	}
	
	@Test
	public boolean testUpdate() {
		return false;
	}
	
	@Test
	public boolean testComplete() {
		return false;
	}
	
	@Test
	public boolean testQueued() {
		return false;
	}
	
	public static void main(String[] args) {
	
		JobTest jt = new JobTest();
		jt.testDefaultConstructor();
		
		
	}
	
}
