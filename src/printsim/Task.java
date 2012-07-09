/**
 * 
 */
package printsim;

/**
 * Define an interface that specifies what a task should do.
 * 
 * We define a task to be an action, which may be performed in parallel,
 * that takes a finite amount of time.  Indefinite length tasks are not supported.
 * 
 * 
 * @author makoto
 *
 */
public abstract class Task {
    
    private int taskNumber, timeIdle, qNumber, timeComplete;
    private int timeCreated, timeWorking;
    private boolean inQ = false;
    
    /**
     * Report the contents of this Task.  This should be overridden in the subclass.
     * @return a String containing useful state information about this task
     */
    @Override
    public String toString() {
	return "";
    }
    
    /**
     * Retrieve the generated task's task number.
     * @return the task number
     */
    
    public int getTaskNumber() {
	return taskNumber;
    }
    
    /**
     * Retrieve the generated task's idle time.
     * @return the time spent not doing useful work
     */
    
    public int getIdleTime() {
	return timeIdle;
    }
    
    public int getQNumber() {
	return qNumber;
    }
    
    public int getTimeComplete() {
	return timeComplete;
    }
    
    public int getTimeCreated() {
	return timeCreated;
    }
    
    public int getTimeWorking() {
	return timeWorking;
    }
    
    public void setQNumber(int newQ) {
	qNumber = (newQ != qNumber) ? newQ : qNumber;  
    }
    
    public boolean getInQ() {
	return inQ;
    }
    public void setInQ() {
	if(!inQ) {
	    inQ = true;
	}
    }
    
    public void unsetInQ() {
	if(inQ) {
	    inQ = false;
	}
    }
}
