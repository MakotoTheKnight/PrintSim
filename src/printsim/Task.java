/**
 *
 */
package printsim;

/**
 * Define an abstraction that specifies what a task should do.
 *
 * We define a task to be an action, which may be performed in parallel,
 * that takes a finite amount of time.  Indefinite length tasks are not supported.
 *
 *
 * @author makoto
 *
 */
public abstract class Task {

    protected int taskNumber;
    protected int timeIdle;
    protected int qNumber;
    protected int timeComplete;
    protected int timeCreated;
    protected int timeWorking;
    protected boolean contained = false;

    /**
     * Report the contents of this Task.
     *
     * Useful information for each Task is implementation dependent.
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

    public boolean getContained() {
	    return contained;
    }
    public void setContained() {
        if(!contained) {
	        contained = true;
	    }
    }

    public void unsetContained() {
        if(contained) {
	        contained = false;
	    }
    }
}
