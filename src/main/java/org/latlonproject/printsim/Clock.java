/*  Clock.java:
    This class is referred to in this program as an almost entirely static
    class.
    Most everything that it does doesn't require that an object of type Clock
     be created, but for
    ease of use and reference, one is usually created anyway in main.
    Written by Jason Black, 29 October 2008
*/

package org.latlonproject.printsim;

import java.io.IOException;
import java.util.Scanner;

public class Clock implements ClockEvent {

    private int currentTick;
    private int currentJob;
    private int jobsPerTick = 3;
    private int maxJobDuration = 6;
    private String generatedJobInformation;
    private int MAX_TIME_ALLOT;
    private static final int MAX_QUEUES = 6;
    private static final int MAX_PRINTERS = 6;

    public Clock() {
        this(100); // maximum time allotted for the simulation
    }

    public Clock(int theJobGenerationLimit, int theMaxJobLength,
                 int theTimeAllotted) {
        this(theTimeAllotted);
        jobsPerTick = theJobGenerationLimit;
        maxJobDuration = theMaxJobLength;
    }

    public Clock(int maxSimTime) {

        MAX_TIME_ALLOT = maxSimTime;
        currentTick = 0;
        currentJob = 0;
        generatedJobInformation = "";
    }

    public String toString() {
        if (currentTick <= MAX_TIME_ALLOT) {
            return "\n-----------------------\n" + "Current Simulation: Tick " +
                           "#" + currentTick +
                           " of " + MAX_TIME_ALLOT + "\n" +
                           generatedJobInformation + "\n";
        } else {
            return "\n------------------------\n" + "Current Simulation: Tick" +
                           " #" + currentTick +
                           " of " + MAX_TIME_ALLOT + "\n\t{ALERT} Simulation " +
                           "in overtime!\n" + "\tEstimated end: ";
        }
    }

    public void update() {
    }


    public int getTotalSimTime() {
        return MAX_TIME_ALLOT;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public int getCurrentJob() {
        return ++currentJob;
    }

    public void pause(int interval) {
        if (currentTick % interval == 0 && interval > 0) {
            Scanner input = new Scanner(System.in);
            System.out
                  .println("\t{PAUSE} Simulation has paused, " +
                                   "every " + interval + " times.\n" +
                                   "\tIf you wish to break out of the pause, " +
                                   "please enter a number.\n");
            while (!(input.hasNextInt())) {
            }
        }
    }


    public void writeToFile(String file, String input) throws IOException {

//        reportGenerator.writeToFile(file, input);
    }

    public static int getMaxQueues() {
        return MAX_QUEUES;
    }


    public int getJobsPerTick() {
        return jobsPerTick;
    }

    public int getMaxJobDuration() {
        return maxJobDuration;
    }

    public static int getMaxPrinters() {
        return MAX_PRINTERS;
    }
}
