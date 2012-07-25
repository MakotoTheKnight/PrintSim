
/**
 * Defines a generic Job token to be performed.
 *
 * This token can take an arbitrary length of operation time, and
 * can contain any information required for it to operate.
 * Duration and data may be specified by the external Clock reference.
 * Written by Jason Black, completed 6 November 2008, revised  16 June 2012
*/

package printsim;
public class Job extends Task implements ClockEvent {

    private String paperColor;
    private String inkColor;
    private boolean isPrinting;


    /** Build against a bias of jobs, as specified by our external controller.
     *
     * @param biased a boolean indicating a specific testing bias.
     */
    public Job(boolean biased) {

        this(Clock.getCurrentJob() + 1, Clock.getCurrentTick(), Clock.generateJobDuration(),
        Clock.setPaperColor(biased), Clock.setInkColor(biased));
    }

    private Job(int order, int arrivedAt, int length, String paper, String ink) {

        taskNumber = order;
        timeCreated = arrivedAt;
        timeWorking = length;
        paperColor = paper;
        inkColor = ink;
    }


    /**
     * Return useful information about this instance of a Job.
     * @return a String containing information about the job, its number,
     * what data its operating on, and its duration.
    */

    public String toString() {
        return "Job #: " + taskNumber + " {" + paperColor + " paper, " + inkColor +
            " ink, duration " + timeWorking + "}";
    }

    /**
     * Update the state of the object.  Routinely done once every simulation tick.
    */
    public void update() {
        if(contained) {
            timeIdle++;
        }
        if(isPrinting) {
            timeComplete = timeCreated + timeIdle + timeWorking;
        }
    }

    /**
     * Determine if a Job is complete, based on its departure time.
     * @param tick the tick it is currently on.
     * @return true if the current tick is the same as the Job's internally calculated departure time.
     */

    public boolean isComplete(int tick) {
        return tick == timeComplete;
    }

    /**
     * Determine if a particular Job can perform its task.
     * @return a boolean indicating such.
     */

    public boolean canPrint() {
        return contained && timeIdle > 0;
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
        contained = false;
    }


}
