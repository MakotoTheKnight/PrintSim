package org.latlonproject.printsim.factory;

import org.latlonproject.printsim.Clock;
import org.latlonproject.printsim.InkColor;
import org.latlonproject.printsim.PaperColor;
import org.latlonproject.printsim.Queue;

public class QueueFactory {

    private static volatile boolean isSet = false;
    private static Queue[] queues;

    private QueueFactory() {
    }

    public static synchronized Queue[] createQueues() {
        if(!isSet) {
            final Queue[] tempQueues = new Queue[Clock.getMaxQueues()];
            int i = 0;
            for (PaperColor paper : PaperColor.values()) {
                for (InkColor ink : InkColor.values()) {
                    tempQueues[i] = new Queue(paper, ink);
                    i++;
                }
            }
            isSet = true;
            queues = tempQueues;
        }
        return queues;
    }
}
