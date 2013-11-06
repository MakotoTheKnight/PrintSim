package org.latlonproject.printsim;

import java.util.LinkedList;
import java.util.List;

public class Queue implements ClockEvent {

    private List<Job> queue = new LinkedList<>();
    private int printerId;
    private static int id = 1;
    private boolean isAttached;
    private final InkColor inkColor;
    private final PaperColor paperColor;
    private int attachLength;

    public Queue(PaperColor paperColor, InkColor inkColor) {
        this.paperColor = paperColor;
        this.inkColor = inkColor;
    }

    /* toString():  Return the String status of any object. */
    public String toString() {

        return "Queue #" + id + " {" + paperColor + " paper, " +
                inkColor + " ink, total size " + queue.size() + "}";
    }


    /* update(): Done on essentially every tick. */
    public void update() {

        if (isAttached) {
            ++attachLength;
        }
        for (Job j : queue) {
            j.update();
            j.setQueueNumber(this.id);
        }
    }

    public boolean isAttached() {
        return isAttached;
    }

    public boolean isEmpty() {
        return queue.size() == 0;
    }


    public boolean canSymlink() {
        return !(isEmpty()) && !(isAttached);
    }

    public boolean canDetach() {
        return (queue.size() == 0 && isAttached);
    }

    public int getQueueID() {
        return id;
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
    }

    public void setSoftlinked() {
        isAttached = false;
    }

    public void detach() {
        printerId = 0;
    }

    public InkColor getInkColor() {
        return inkColor;
    }

    public PaperColor getPaperColor() {
        return paperColor;
    }

    public Job peek(int number) {
        return queue.size() > 0 ? queue.get(number) : null;
    }

    public Job dequeue() {
        return queue.size() > 0 ? queue.remove(0) : null;
    }

    public void enqueue(Job j) {
        if (j.getPaperColor().equals(paperColor) && j.getInkColor().equals(inkColor)) {
            j.setQueueNumber(this.id);
            j.setQueued();
            queue.add(j);
        }
    }

}
