
/**
 * Defines a generic Job token to be performed.
 * 
 * This token can take an arbitrary length of operation time, and
 * can contain any information required for it to operate.
 * Duration and data may be specified by the external Clock reference.
 * Written by Jason Black, completed 6 November 2008, revised  16 June 2012
*/

package printsim;
public class Job implements ClockEvent {
	
	private int jobNumber, idleCounter, queueNumber, departureTime;
	private final int arrivalTime, duration;
	private String paperColor, inkColor;
	private boolean inQueue, isPrinting;
	

	/** Build against a bias of jobs, as specified by our external controller.
	 * 
	 * @param biased a boolean indicating a specific testing bias.
	 */
	public Job(boolean biased) {
		
		this(Clock.getCurrentJob() + 1, Clock.getCurrentTick(), Clock.generateJobDuration(),
		Clock.setPaperColor(biased), Clock.setInkColor(biased));
	}
	
	private Job(int order, int arrivedAt, int length, String paper, String ink) {
		
		jobNumber = order;
		arrivalTime = arrivedAt;
		duration = length;
		paperColor = paper;
		inkColor = ink;
	}
	
	
	/** 
	 * Return useful information about this instance of a Job.
	 * @return a String containing information about the job, its number,
	 * what data its operating on, and its duration. 
	*/
	
	public String toString() {
	    return "Job #: " + jobNumber + " {" + paperColor + " paper, " + inkColor +
			" ink, duration " + duration + "}";
	}
		
	/**
	 * Update the state of the object.  Routinely done once every simulation tick.
	*/
	public void update() {
	    if(inQueue) {
		idleCounter++;
	    }
	    if(isPrinting) {
		departureTime = ((arrivalTime + idleCounter) + duration);
	    }
	}
	
	/**
	 * Determine if a Job is complete, based on its departure time. 
	 * @param tick the tick it is currently on.
	 * @return true if the current tick is the same as the Job's internally calculated departure time.
	 */
	
	public boolean isComplete(int tick) {
		return tick == departureTime;
	}
	
	/**
	 *  Establish that a Job has been successfully placed into a Queue of any sort.
	 */
	
	public void setQueued() {
		inQueue = true;
		departureTime = ((arrivalTime + idleCounter) + duration);
	}
	
	/**
	 * Determine if a particular Job can perform its task.
	 * @return a boolean indicating such.
	 */
	
	public boolean canPrint() {
	    return inQueue && idleCounter > 0;
	}
	
	/**
	 * Determine if a particular Job is enqueued.
	 * @return a boolean indicating such.
	 */
	
	public boolean isQueued() {
	    return inQueue;
	}
	
	/**
	 * Accessor - retrieve the arrival time of the Job.
	 * @return the arrival time
	 */
	
	public int getArrivalTime() {
	    return arrivalTime;
	}
	
	/**
	 * Accessor - retrieve the departure time of the Job.
	 * @return departure time
	 */
	
	public int getDepartureTime() {
	    return departureTime;
	}

	/**
	 * Accessor - retrieve the duration of the Job.
	 * @return duration
	 */
	public int getDuration() {
	    return duration;
	}
	
	/**
	 * Accessor - retrieve the time spent not actively being worked on.
	 * @return the idle time
	 */
	
	public int getIdleTime() {
	    return idleCounter;
	}
	
	/**
	 * Accessor - retrieve the chronological numbering of the Job.
	 * @return the creation number of the Job
	 */
	
	public int getJobNumber() {
	    return jobNumber;
	}
	
	/**
	 * Accessor - retrieve the queue's number, which the Job is enqueued in
	 * @return the ID number of its bound Queue
	 */
	
	public int getQueueNumber() { 
		return queueNumber;
	}
	
	/**
	 * Retrieve the current Job's paper color.
	 * @return the color of this Job's paper
	 */
	
	public String getPaperColor() {
	    return paperColor;
	}
	
	/**
	 * Retrieve the current Job's ink color.
	 * @return the color of this Jon's ink
	 */
	
	public String getInkColor() {
	    return inkColor;
	}
	
	/**
	 * Establish that the Job is now printing.
	 * Printing and enqueueing are mutually exclusive, so
	 * if the task is printing, it is not enqueued.
	 */
	
	public void setPrinting() {
	    isPrinting = true;
	    inQueue = false;
	}
	
	/**
	 * Set the queue number for this Job
	 * @param id the queue number to be used.
	 */
	
	public void setQueueNumber(int id) {
	    queueNumber = id;
	}
}		
