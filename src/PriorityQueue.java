/* PriorityQueue.java
   The class which is the priority queue.  A lot of functions are ripped from Queue.
   Written by Jason Black, 17 November 2008
*/

import java.util.ArrayList;

public class PriorityQueue extends Queue implements ClockEvent {

	private Queue[] queue = new Queue[3];  // hold specific levels of jobs
	public int printer_ID, queue_ID;
	private int attachLength, size, promoteCounter;
	private final String INK_COLOR, PAPER_COLOR;
	private boolean attached, softlinked;

	// Standard construct with super() method; required for inheritance
	public PriorityQueue(int id, String paper, String ink) {
		super(id, paper, ink);
		INK_COLOR = ink;
		PAPER_COLOR = paper;
		queue_ID = id;
		for(int i = 0; i < queue.length; i++) {
			queue[i] = new Queue(id, paper, ink);
		}
		size = promoteCounter = 0;
		attached = softlinked = false;
	}
	
	// ************ BEGIN INTERFACE FUNCTIONS ************
	
	/* toString():  Return the String status of any object. */
	@Override
	public String toString() {
	
		return "PriorityQueue #" + queue_ID + " {" + PAPER_COLOR + " paper, " +
		INK_COLOR + " ink, total size " + getNumberOfJobs() + ", attached to Printer # " +
		printer_ID + "}";
	}
	
	
	/* update(): Done on essentially every tick. */
	@Override
	public void update() {
		
		size = 0;
		
		if(attached) {
			attachLength++;
		}
		if(promoteCounter == 5 || (queue[0].peek(0) == null && !isEmpty())) {
			promote();
		}
		//internal queue methods here
		for(int i = 0; i < queue.length; i++) {
			queue[i].update();
		}
		
		for(int i = 0; i < queue.length; i++) {
			size += queue[i].getSize();
		}
	}
	
	// ************ END INTERFACE FUNCTIONS ************

	@Override
	public boolean isAttached() { return attached; }
	@Override
	public boolean isEmpty() { return getNumberOfJobs() == 0; }
	@Override
	public boolean isSoftlinked() { return softlinked; }
	@Override
	public boolean canSymlink() { return !(isEmpty()) && !(attached) && !(softlinked); }
	@Override
	public boolean canDetach() { return isEmpty() && attached; }
	public int getQueueID() { return queue_ID; }
	public int getSize() { return size; }
	public int getPrinterID() { return printer_ID; }	
	public void setPrinterID(int id) { printer_ID = id; }
	public void setSoftlinked() { softlinked = true; attached = false; }
	public String getInkColor() { return INK_COLOR; }
	public String getPaperColor() { return PAPER_COLOR; }
	public Job peek() { return queue[0].peek(0); }
	
	// Resets most variables
	public void detach() {
		attached = false;
		softlinked = false;
		printer_ID = 0;
	}
	// Sets queue to be attached
	public void setAttached() {
		attached = true;
		softlinked = false;
	}
	// Counts the number of jobs
	public int getNumberOfJobs() {
		
		int total = 0;
		
		for(int i = 0; i < queue.length; i++) {
			total += queue[i].getSize();
		}
		
		return total;		
	}
	// Enqueues a job based on its priority.
	public void enqueue(Job j) {
		
		if(j.getPaperColor().equals(PAPER_COLOR) && j.getInkColor().equals(INK_COLOR)) {
			int jobPriority = (j.getPriorityLevel() - 1); // Places it in the correct bin
			queue[jobPriority].enqueue(j);
		}
	}
	//Dequeues a job, always the first priority bin and queue.
	public Job dequeue() {
		promoteCounter++;
		return queue[0].dequeue();
	}
	//"promote" schema explained in write-up
	private void promote() {

		// Holds five jobs designated to promote in any column which isn't the first
		ArrayList<Job> holder = new ArrayList<Job>(5);

		//System.out.println("PROMOTE, " + toString() + " = ENVOKED");
		
		for(int i = 1; i < queue.length; i++) { // Not going to look to the first priority bin
			for(int j = 0; j < queue[i].getSize(); j++) {
				
				if(holder.size() == 5) { // ensure persistent five job ArrayList
					break;
				} else if(queue[i].peek(j) != null) { // take the job from the queue
					holder.add(queue[i].dequeue(j));
				}
			}
		}
		// Reduce the Job's original priority level by one, and re-enqueue them
		for (Job j: holder) {
			j.setPriorityLevel(j.getPriorityLevel() - 1);
			this.enqueue(j);
		}
		promoteCounter = 0;
	}
}
