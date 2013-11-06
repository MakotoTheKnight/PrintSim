package org.latlonproject.printsim.factory;

import org.latlonproject.printsim.Clock;
import org.latlonproject.printsim.InkColor;
import org.latlonproject.printsim.Job;
import org.latlonproject.printsim.PaperColor;

public class JobFactory {

    public static final int THREE_FOURTHS = 75;
    public static final int TWO_THIRDS = 66;
    public static final int ONE_HALF = 50;
    public static final int ONE_THIRD = 33;
    public static final int ONE_FOURTH = 25;

    private JobFactory() {
    }

    public static Job[] generateJobs(Clock clock, boolean isBiased) {
        return generateJobs(clock, isBiased, -1);
    }

    /* Generate a job and place it in the queue in the same step (legal).  Uses either Queue type.*/
    public static Job[] generateJobs(Clock clock, boolean isBiased, int priorityLevel) {
        Job[] retVal = new Job[0];
        int jobs = generateNumberOfJobs(clock);

        if (0 < jobs) {
            retVal = new Job[jobs];
            for (int i = 0; i < jobs; i++) {
                retVal[i] = new Job(clock.getCurrentJob(), clock.getCurrentTick(),
                        generateJobDuration(clock), setPaperColor(isBiased),
                                           setInkColor(isBiased));
            }
        }
        return retVal;
    }

    private static PaperColor setPaperColor(boolean biased) {
        int key = (int) (Math.random() * 100);

        if (biased) {
            if(0 <= key && key < THREE_FOURTHS) {
                return PaperColor.WHITE;
            } else if(THREE_FOURTHS <= key &&
                key < (int)(THREE_FOURTHS + (ONE_FOURTH / 2.0))) {
                return PaperColor.LIGHT_GRAY;
            } else {
                return PaperColor.GOLDENROD;
            }
        } else {
            if (0 <= key && key < ONE_THIRD) {
                return PaperColor.WHITE;
            } else if (ONE_THIRD <= key && key < TWO_THIRDS) {
                return PaperColor.LIGHT_GRAY;
            } else {
                return PaperColor.GOLDENROD;
            }
        }
    }

    private static InkColor setInkColor(boolean biased) {
        int key = (int) (Math.random() * 100);
        if (biased) {
            if (0 <= key && key < THREE_FOURTHS) {
                return InkColor.BLACK;
            } else {
                return InkColor.DARK_GRAY;
            }
        } else {
            if (0 <= key && key < ONE_HALF) {
                return InkColor.BLACK;
            } else {
                return InkColor.DARK_GRAY;
            }
        }
    }

    private static int generateNumberOfJobs(Clock theClock) {
        return 1 + (int) (theClock.getJobsPerTick() * Math.random());
    }

    private static int generateJobDuration(Clock theClock) {
        return (int) (1 + theClock.getMaxJobDuration() * Math.random());
    }

}
