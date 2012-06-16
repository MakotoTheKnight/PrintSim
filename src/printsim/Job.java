
/* Job.java
   The epicentre of this simulation.
   Each job is created by the clock, and can receive durations 1-6.
   Color configuration and duration are both handled by the Clock.
   Written by Jason Black, completed 6 November 2008
*/

package printsim;
public class Job implements ClockEvent {
	
	private int MAX_WAIT_TIME;
	private int jobNumber, idleCounter, queueNumber, priorityLevel, priorityCounter;
	private int departureTime;
	private final int arrivalTime, duration;
	private String paperColor, inkColor;
	private boolean inQueue = false, isPrinting = false, priorityFlag = false;
	
	/*  Constructors:
	    A Job is created with three ints (Job Number, Arrival Time and Job Duration),
	    and two Strings (Paper Color and Ink Color).  Since, when the object is created,
	    one does not know what any of these values will be, we will be calling them using
	    specific static methods from the Clock class.
	*/
	
	public Job() {
		
		this(Clock.getCurrentJob() + 1, Clock.getCurrentTick(), Clock.generateJobDuration(),
		Clock.setPaperColor(false), Clock.setInkColor(false));
		
	}
	// A constructor biased against black/white paper combos
	public Job(boolean biased) {
		
		this(Clock.getCurrentJob() + 1, Clock.getCurrentTick(), Clock.generateJobDuration(),
		Clock.setPaperColor(true), Clock.setInkColor(true));
	}
	// A constructor with priorities, and with a specified wait time
	public Job(boolean prioritized, int wait) {
		this();
		priorityLevel = Clock.generatePriority();
		MAX_WAIT_TIME = wait;
		priorityFlag = prioritized;
	}
	
	private Job(int order, int arrivedAt, int length, String paper, String ink) {
		
		jobNumber = order;
		arrivalTime = arrivedAt;
		duration = length;
		paperColor = paper;
		inkColor = ink;
		priorityCounter = 0;
		
	}
	
	// ************ BEGIN INTERFACE FUNCTIONS ************
	
	/* toString():  Return the String status of any object. */
	public String toString() {
		if(priorityFlag) {
			return "Job #: " + jobNumber +	" {" + paperColor + " paper, " + inkColor +
			" ink, duration " + duration + ", priority " + priorityLevel + "}";
		} else {
			return "Job #: " + jobNumber +	" {" + paperColor + " paper, " + inkColor +
			" ink, duration " + duration + "}";
		}
	}
		
	/* update(): Done on essentially every tick. */
	public void update() {
		if(inQueue) {
			idleCounter++;
		}
		if(isPrinting) {
			departureTime = ((arrivalTime + idleCounter) + duration);
		}
	}
	
	// ************ END INTERFACE FUNCTIONS ************
	
	
	public boolean isComplete(int tick) {
		
		if(tick == departureTime) {
			return true;
		} else {
			return false;
		}
	}
	
	// Set up the complain flag for the job; meaning the printer *should* switch to this queue

	
	public void setQueued() {
	
		inQueue = true;
		departureTime = ((arrivalTime + idleCounter) + duration);
	}
	
	public boolean canPrint() { return inQueue && idleCounter > 0; }
	public boolean getPriority() { return (Clock.getCurrentTick() - arrivalTime) == MAX_WAIT_TIME; }
	public boolean isQueued() { return inQueue; }
	public int getArrivalTime() { return arrivalTime; }
	public int getDepartureTime() {
		if(MAX_WAIT_TIME > 0) {
			return departureTime + MAX_WAIT_TIME;
		} else {
			return departureTime;
		}
	}
	public int getDuration() { return duration; }
	public int getIdleTime() { return idleCounter; }
	public int getJobNumber() { return jobNumber; }
	public int getPriorityLevel() { return priorityLevel; }
	public int getQueueNumber() { return queueNumber; }
	public int getRealPriorityLevel() { return priorityLevel + priorityCounter; }
	public String getPaperColor() { return paperColor; }
	public String getInkColor() { return inkColor; }
	public void declarePrinting() { isPrinting = true; inQueue = false; }
	public void setPriorityLevel(int level) { priorityLevel = level; priorityCounter++; }
	public void setQueueNumber(int id) { queueNumber = id; }
}		
