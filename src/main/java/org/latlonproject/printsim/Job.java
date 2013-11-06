package org.latlonproject.printsim;

public class Job implements ClockEvent {

    private final int jobNumber;
    private final int arrivalTime;
    private final int duration;
    private int idleCounter = 0;
    private int queueNumber = 0;
    private int departureTime = 0;
    private PaperColor paperColor;
    private InkColor inkColor;
    private boolean queued = false;
    private boolean printing = false;

    public Job(int theJobNumber, int theDuration, int theArrivalTime,
               PaperColor thePaperColor, InkColor theInkColor) {
        duration = theDuration;
        arrivalTime = theArrivalTime;
        jobNumber = theJobNumber;
        paperColor = thePaperColor;
        inkColor = theInkColor;
    }

    public String toString() {
        return "Job #: " + jobNumber + " {" + paperColor + " paper, " +
                       "" + inkColor +
                       " ink, duration " + duration + "}";
    }

    public void update() {
        if (queued) {
            idleCounter++;
        }
    }

    public boolean isComplete(int tick) {
        return tick == departureTime;
    }

    public void setQueued() {
        queued = true;
        departureTime = ((arrivalTime + idleCounter) + duration);
    }

    public boolean canPrint() {
        return queued && idleCounter > 0;
    }

    public boolean isQueued() {
        return queued;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getDepartureTime() {
        return arrivalTime + idleCounter + duration;
    }

    public int getDuration() {
        return duration;
    }

    public int getIdleTime() {
        return idleCounter;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public PaperColor getPaperColor() {
        return paperColor;
    }

    public InkColor getInkColor() {
        return inkColor;
    }

    public void declarePrinting() {
        printing = true;
        queued = false;
    }

    public void setQueueNumber(int id) {
        queueNumber = id;
    }

    public boolean isPrinting() {
        return printing;
    }

    public void setPrinting(final boolean thePrinting) {
        printing = thePrinting;
    }
}
