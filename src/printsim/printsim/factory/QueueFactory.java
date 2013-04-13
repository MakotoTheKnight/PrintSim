package printsim.factory;

import printsim.Clock;
import printsim.InkColor;
import printsim.PaperColor;
import printsim.Queue;

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
                    tempQueues[i] = new Queue(i+1, paper, ink);
                    i++;
                }
            }
            isSet = true;
            queues = tempQueues;
        }
        return queues;
    }
}
