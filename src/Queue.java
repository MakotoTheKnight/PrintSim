/*  Queue.java
    A custom queue of mine, written personally.
    Written by Jason Black, 1 November 2008
*/
package printsim;
import java.util.ArrayList;
public class Queue implements ClockEvent {

	private ArrayList<Job> queue = new ArrayList<Job>(25);
	public int printer_ID, queue_ID;
    private int attachLength;
	private boolean attached, softlinked;
	private final String INK_COLOR, PAPER_COLOR;
	
	public Queue(int id, String paper, String ink) {
		
		queue_ID = id;
		PAPER_COLOR = paper;
		INK_COLOR = ink;
		printer_ID = attachLength = 0;
		softlinked = attached = false;
		
	}
		
	// ************ BEGIN INTERFACE FUNCTIONS ************
	
	/* toString():  Return the String status of any object. */
	public String toString() {
	
		return "Queue #" + queue_ID + " {" + PAPER_COLOR + " paper, " +
		INK_COLOR + " ink, total size " + queue.size() + "}";
	}
	
	
	/* update(): Done on essentially every tick. */
	public void update() {
		
		if(attached) {
            attachLength++;
		}
		for(Job j: queue) {
			j.update();
			j.setQueueNumber(queue_ID);
		}
	}

    public boolean isAttached() { return attached; }
	public boolean isEmpty() { return queue.size() == 0; }
	public boolean isSoftlinked() { return softlinked; }
	public boolean canSymlink() { return !(isEmpty()) && !(attached) && !(softlinked); }
	public boolean canDetach() { return (queue.size() == 0 && attached); }
	public int getQueueID() { return queue_ID; }
	public int getSize() { return queue.size(); }
	public int getPrinterID() { return printer_ID; }
	public void setPrinterID(int id) { printer_ID = id; }
    public void setAttached() { attached = true; softlinked = false;	}
	public void setSoftlinked() { softlinked = true; attached = false; }
    public void detach() { softlinked = attached = false; printer_ID = 0; }
	public String getInkColor() { return INK_COLOR; }
	public String getPaperColor() { return PAPER_COLOR; }
	public Job peek(int number) { return queue.size() > 0 ? queue.get(number) : null; }
	public Job dequeue() { return queue.size() > 0 ? queue.remove(0) : null; }
	//Used for PriorityQueue, dequeues a Job from a specific place
	public Job dequeue(int location) { return queue.size() > 0 ? queue.remove(location) : null; }

	//Adds a job into the Queue
	public void enqueue(Job j) {
		if(j.getPaperColor().equals(PAPER_COLOR) && j.getInkColor().equals(INK_COLOR)) {
			j.setQueueNumber(queue_ID);
			j.setQueued();
			queue.add(j);
		}
	}
	
	// ************ END INTERFACE FUNCTIONS ************

	
}
