package printsim;

import java.util.LinkedList;
import java.util.List;

public class Queue implements ClockEvent {

    private List<Job> queue = new LinkedList<>();
    private int printerId;
    private int queueId;
    private int attachLength;
    private boolean isAttached;
    private boolean isSoftlinked;
    private final InkColor INK_COLOR;
    private final PaperColor PAPER_COLOR;

    public Queue(int id, PaperColor paperPaperColor, InkColor inkColor) {
        queueId = id;
        PAPER_COLOR = paperPaperColor;
        INK_COLOR = inkColor;
        printerId = 0;
        attachLength = 0;
        isSoftlinked = false;
        isAttached = false;

    }

    /* toString():  Return the String status of any object. */
    public String toString() {

        return "Queue #" + queueId + " {" + PAPER_COLOR + " paper, " +
                INK_COLOR + " ink, total size " + queue.size() + "}";
    }


    /* update(): Done on essentially every tick. */
    public void update() {

        if (isAttached) {
            ++attachLength;
        }
        for (Job j : queue) {
            j.update();
            j.setQueueNumber(queueId);
        }
    }

    public boolean isAttached() {
        return isAttached;
    }

    public boolean isEmpty() {
        return queue.size() == 0;
    }

    public boolean isSoftlinked() {
        return isSoftlinked;
    }

    public boolean canSymlink() {
        return !(isEmpty()) && !(isAttached) && !(isSoftlinked);
    }

    public boolean canDetach() {
        return (queue.size() == 0 && isAttached);
    }

    public int getQueueID() {
        return queueId;
    }

    public int getSize() {
        return queue.size();
    }

    public int getPrinterID() {
        return printerId;
    }

    public void setPrinterID(int id) {
        printerId = id;
    }

    public void setAttached() {
        isAttached = true;
        isSoftlinked = false;
    }

    public void setSoftlinked() {
        isSoftlinked = true;
        isAttached = false;
    }

    public void detach() {
        isSoftlinked = isAttached = false;
        printerId = 0;
    }

    public InkColor getInkColor() {
        return INK_COLOR;
    }

    public PaperColor getPaperColor() {
        return PAPER_COLOR;
    }

    public Job peek(int number) {
        return queue.size() > 0 ? queue.get(number) : null;
    }

    public Job dequeue() {
        return queue.size() > 0 ? queue.remove(0) : null;
    }

    public void enqueue(Job j) {
        if (j.getPaperColor().equals(PAPER_COLOR) && j.getInkColor().equals(INK_COLOR)) {
            j.setQueueNumber(queueId);
            j.setQueued();
            queue.add(j);
        }
    }

}
