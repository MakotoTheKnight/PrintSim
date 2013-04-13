package printsim;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Printer implements ClockEvent, IPrinter {

    private int printer_ID;
    private int changeoverTick;
    private int queue_ID, idleCounter, beginChangeTick, jobCounter, changes;
    private ArrayList<Integer> changelog;
    private boolean busy;
    private boolean hasQueue;
    private boolean setPrintJob;
    private boolean hasBegun;
    private boolean settingInk;
    private boolean settingPaper;
    private final int INK_CHANGEOVER = 10, PAPER_CHANGEOVER = 5;
    private PaperColor paperColor;
    private InkColor inkColor;
    private ArrayList<String> oldConfig, newConfig;
    private Queue currentQueue, softQueue;
    private Job processingJobs;
    private ArrayList<Job> completeJobs;
    private Clock clock = new Clock();


    public Printer(int thePrinterId, PaperColor thePaper, InkColor theInk) {
        paperColor = thePaper;
        inkColor = theInk;
        hasQueue = busy = setPrintJob = hasBegun = false;
        printer_ID = thePrinterId;
        output = "";
        queue_ID = jobCounter = changes = 0;
        currentQueue = softQueue = null;
        departureTick = arrivalTick = changeoverTick = 0;
        currentTick = beginChangeTick = clock.getCurrentTick();
        completeJobs = new ArrayList<>(300);
        changelog = new ArrayList<>(100);
        oldConfig = new ArrayList<>(100);
        newConfig = new ArrayList<>(100);
        processingJobs = null;

    }

    // ************ BEGIN INTERFACE FUNCTIONS ************

    /* toString():  Return the String status of any object. */
    @Override
    public String toString() {
        String info = "";
        if (hasQueue) {
            info = "Attached to Queue #" + queue_ID;
        } else {
            info = "not attached to any queue";
        }
        return "\tPrinter #" + printer_ID + " {" + paperColor + ", " + inkColor +
                ", " + info + "}";
    }


    /* update(): Done on essentially every tick. */
    @Override
    public void update() {

        if (!(busy)) {
            idleCounter++;
        }

        if (!(currentPriQueue == null) || !(softPriQueue == null)) {
        }

        currentTick = Clock.getCurrentTick();
        removeQueue();
    }

    // ************ END INTERFACE FUNCTIONS ************


    public boolean hasJob() {
        return !(processingJobs == null);
    }

    public boolean isBusy() {
        return busy;
    }

    public boolean isAttached() {
        return hasQueue;
    }

    // Flush most variables this way
    protected void reset() {
        hasQueue = busy = setPrintJob = false;
        queue_ID = 0;
        currentQueue = softQueue = null;
        processingJobs = null;
    }

    //Remove a queue from this Printer
    public void removeQueue() {
        if (currentQueue != null && processingJobs == null && currentQueue.canDetach()) {
            currentQueue.detach();
            output = "\t{WARNING} Queue #" + currentQueue.queue_ID +
                    " <{] [}> Printer #" + printer_ID + "\n";
            currentQueue = softQueue = null;
            hasQueue = false;
            reset();
        }
    }

    // symlink(Queue or PriorityQueue) symbolically links a Queue to a Printer.
    public void symlink(Queue[] queues, boolean multiPrinting) {

        for (Queue q : queues) {

            if (q.canSymlink()) {

                PaperColor paper = q.getPaperColor();
                InkColor ink = q.getInkColor();

                if (inkColor.equals(ink) && paperColor.equals(paper)) {

                    hasQueue = true;
                    currentQueue = q;
                    softQueue = null;
                    q.setPrinterID(printer_ID);
                    queue_ID = q.getQueueID();
                    q.setAttached();
                    if (!multiPrinting) {
                        output = "\t{NOTICE} " + q.toString() +
                                " <{]-[}> Printer #" + printer_ID + "; successful.\n";
                    } else {
                        output = "\t{NOTICE} " + q.toString() +
                                " <{]-[}> MultiPrinter #" + printer_ID + "; successful.\n";
                    }
                    busy = false;
                    break;
                }
            }
        }
    }

    public void print(boolean multiPrinting) {

        output = "";
        if (!(setPrintJob)) {
            if (!(currentQueue == null) && !busy) {
                if (!(currentQueue.peek(0) == null) && currentQueue.peek(0).canPrint()) {
                    processingJobs = currentQueue.dequeue();
                    setPrintJob = true;
                    print(processingJobs);
                } else {
                    removeQueue();
                }
            }
        } else {
            print(processingJobs);
        }
    }

    /*  The print() routine assumes that the job it's getting CAN print, so it won't question
         or check that, thanks to the canPrint() method.
     */
    protected void print(final Job j) {

        output = "";
        if (!(j == null)) {

            j.update();
            busy = true;
            j.declarePrinting();
            // The job MUST be complete on tick C = (a + i + d), where i = idle time, d = duration,
            // a = arrival time, and C = current tick
            // Example:  Job arrives at 37, idle for 20, duration 6.  Will be done on 63.

            if (!(hasBegun)) {

                output = "\t{STARTPRINT} Printer #" + printer_ID + " starts " +
                        j.toString() + " this tick.\n";
                hasBegun = true;
            } else {
                output = "\t{...PRINT} Printer #" + printer_ID + ", working on " +
                        j.toString() + "\n";

            }

            if (j.getDepartureTime() - 1 == clock.getCurrentTick()) {

                output = "\t{ENDPRINT} Printer #" + printer_ID +
                        " completes Job #" + j.getJobNumber() + " on this tick.\n";
            }
            completeJobs.add(processingJobs);
            busy = hasBegun = setPrintJob = false;
            processingJobs = null;
            jobCounter++;

        } else {
            output = "";
        }
    }

    // Should be reasonable enough to state that a Queue won't link until both paper & ink match anyway
    public void changeConfiguration(Queue[] queues, boolean multiPrinting) {

        if (isConstant) {
            return;
        } // We don't want this printer to change if it's constant

        if (softQueue == null) {
            findQueue(queues);
        } else if (softQueue.isEmpty()) { // Check to see if the queue went empty prematurely
            softQueue = null;
            changeConfiguration(queues, multiPrinting);
        } else { // SoftQueue IS already set

            PaperColor queue_paper = softQueue.getPaperColor();
            InkColor queue_ink = softQueue.getInkColor();
            //System.out.println("Printer " + printer_ID + " " + queue_paper + " " + queue_ink);

            if ((!settingInk && !settingPaper) && !busy) {

                //beginChangeTick = Clock.getCurrentTick();
                if (!(inkColor.equals(queue_ink)) && paperColor.equals(queue_paper) && !settingInk) {

                    changeoverTick = INK_CHANGEOVER;
                    busy = true;
                    settingInk = true;

                } else if (!(paperColor.equals(queue_paper)) && inkColor.equals(queue_ink)
                        && !settingPaper) {
                    changeoverTick = PAPER_CHANGEOVER;
                    busy = true;
                    settingPaper = true;

                } else if (!(paperColor.equals(queue_paper)) &&
                        !(inkColor.equals(queue_ink)) && !settingPaper && !settingInk) {
                    changeoverTick = (PAPER_CHANGEOVER + INK_CHANGEOVER);
                    busy = true;
                    settingPaper = settingInk = true;
                } else if (paperColor.equals(queue_paper) && inkColor.equals(queue_ink)) {
                    return;
                }
            } else {
                if (!multiPrinting) {
                    output = "\t{ALERT} Printer #: " + printer_ID + " to change config on tick " +
                            (beginChangeTick + changeoverTick) + ", changing to match Queue #" + softQueue.queue_ID;
                } else {
                    output = "\t{ALERT} MultiPrinter #: " + printer_ID + " to change config on tick " +
                            (beginChangeTick + changeoverTick);

                }
                //System.out.println(beginChangeTick);
                //System.out.println(changeoverTick);
                if (softQueue.isEmpty()) {
                    return;
                } else if (Clock.getCurrentTick() == (beginChangeTick + changeoverTick)) {
                    changelog.add(Clock.getCurrentTick());
                    oldConfig.add("{" + paperColor + " & " + inkColor + "}");
                    inkColor = queue_ink;
                    paperColor = queue_paper;
                    newConfig.add("{" + paperColor + " & " + inkColor + "}");
                    busy = hasQueue = false;
                    currentQueue = softQueue;
                    if (!multiPrinting) {
                        output = "\t{NOTICE} Printer #" + printer_ID + " [}>-<{] " +
                                softQueue.toString() + "; successful.";
                    } else {
                        output = "\t{NOTICE} MultiPrinter #" + printer_ID + " [}>-<{] " +
                                softQueue.toString() + "; successful.";
                    }
                    softQueue.detach();
                    softQueue = null;
                    changes++;
                }
            }
        }
    }

    protected void findQueue(Queue[] queues) {

        if (impatient && !(hasQueue) && !(busy)) {
            for (Queue q : queues) {
                if (q.isAttached()) {
                    q.detach();
                    reset();
                }
                if (!(q.isEmpty()) && !(q.isSoftlinked()) && q.getPrinterID() == 0) {
                    //reset();
                    softQueue = q;
                    softQueue.setSoftlinked();
                    softQueue.setPrinterID(printer_ID);
                    beginChangeTick = Clock.getCurrentTick();
                    break;
                } else {
                    q.detach();
                    reset();
                }
            }

        } else if (!(hasQueue) && !(busy)) {
            for (Queue q : queues) {
                if (!(q.isEmpty()) && !q.isSoftlinked() && q.printer_ID == 0) {
                    softQueue = q;
                    softQueue.setSoftlinked();
                    //softQueue.setAttached();
                    softQueue.setPrinterID(printer_ID);
                    beginChangeTick = Clock.getCurrentTick();
                    break;
                } else {
                    q.detach();
                    reset();
                }
            }
        }
    }



    // Returns the total statistics of this printer.
    public String getStats() {

        DecimalFormat df = new DecimalFormat("###.##");

        String foreword = "\n-------------------------------\n" +
                "Printer #" + printer_ID + ":\n\t - Idle for " + idleCounter + " total ticks\n\t" +
                " - Changed configuration from ";

        for (int i = 0; i < oldConfig.size(); i++) {
            String s = oldConfig.get(i);
            if (i == oldConfig.size() - 1) { // if we're on the last one, truncate " to ".
                foreword += s;
            } else {
                foreword += s + " to ";
            }
        }
        foreword += " on ticks: ";
        for (int j = 0; j < changelog.size(); j++) {
            int i = changelog.get(j);
            foreword += "{" + i + "}";
        }

        foreword += "\nJob readout for this Printer.\nJobs read from L-R, in processed order.\n" +
                "EX: {Job #0, paper/ink, arrival, duration, [level]}\n";
        String reply = "";
        String[] jobReadout = new String[completeJobs.size()];
        //System.out.println(jobReadout.length);
        double avgIdle = 0, avgLength = 0;
        //int i = 0;
        Job j = null;
        for (int i = 0; i < jobReadout.length; i++) {
            j = completeJobs.get(i);
            Integer priority = new Integer(0);
            int arrival = j.getArrivalTime();
            String level = "";
            String inkColor = j.getInkColor();
            String paperColor = j.getPaperColor();
            if (j.getRealPriorityLevel() > 0) {
                priority = j.getRealPriorityLevel();
                level = ", " + Integer.toString(priority);
            }
            if (paperColor.equals("White")) {
                paperColor = "Wht";
            } else if (paperColor.equals("Goldenrod")) {
                paperColor = "Gldnrd";
            } else if (paperColor.equals("Gray")) {
                paperColor = "Gry";
            }
            if (inkColor.equals("Black")) {
                inkColor = "Blk";
            } else if (inkColor.equals("Gray")) {
                inkColor = "Gry";
            }

            jobReadout[i] = "{Job #" + j.getJobNumber() + ", " + paperColor +
                    "/" + inkColor + ", " + arrival + ", " + j.getDuration() + level + "}";
            avgIdle += j.getIdleTime();
            avgLength += j.getDuration();
            j = null;
        }

        avgIdle /= completeJobs.size();
        avgLength /= completeJobs.size();

        reply += "\n\n";
        for (int i = 0; i < jobReadout.length; i++) {
            if (i % 3 == 0) {
                reply += "\n";
            }

            reply += jobReadout[i] + "\t";

        }
        String afterword = "";
        afterword += "\n\n";
        afterword += "Avg. Time Enqueued per Job: " + df.format(avgIdle) + "\n" +
                "Avg. Length per Job: " + df.format(avgLength) +
                "\n" + "# of Jobs processed: " + completeJobs.size() + "\n" +
                "# of Printer reconfigurations: " + changes;

        return foreword + reply + afterword;


    }
}
