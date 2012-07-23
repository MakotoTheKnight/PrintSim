/*  Clock.java:
    This class is referred to in this program as an almost entirely static class.
    Most everything that it does doesn't require that an object of type Clock be created, but for
    ease of use and reference, one is usually created anyway in main.
    Written by Jason Black, 29 October 2008
*/

package printsim;
import java.util.Scanner; // Used for the pause() method
import java.io.*; // Used to write the output file (summations)

public class Clock implements ClockEvent {

    	private static double randomizer = Math.random();
	private static int currentTick, currentJob, jobCount = 3, maxJobLength = 6;
	private String generatedJobInformation;
	private static int MAX_TIME_ALLOT, WAIT_TIME; // WAIT_TIME used to flag impatient jobs
	private static String[] uniquePaper, uniqueInk;
	private static boolean priorities = false, bias = false; // Bias used to represent 70% B/W job/printers

	public Clock() {

		this(100); // maximum time allotted for the simulation

	}

	public Clock (int jobs, int maxlength, int clock) {
		this(clock);
		jobCount = jobs;
		maxJobLength = maxlength;
	}

	public Clock(boolean priority) {
		this();
		priorities = priority; // Specify if job is a priority-type job
	}

	public Clock(int maxSimTime, boolean biased) {
		this(maxSimTime);
		bias = biased;
	}

	public Clock(boolean priority, int patienceLevel) {
		this(priority);
		WAIT_TIME = patienceLevel;
	}

	public Clock(int maxSimTime) {

		MAX_TIME_ALLOT = maxSimTime;
		currentTick = 0;
		currentJob = 0;
		generatedJobInformation = "";
		uniquePaper = new String[3];
		uniqueInk = new String[2];
		for(int i = 0; i < uniquePaper.length; i++) {
			if(i == 0) {
				uniquePaper[i] = "White";
			} else if(i == 1) {
				uniquePaper[i] = "Gray";
			} else if(i == 2) {
				uniquePaper[i] = "Goldenrod";
			}
		}

		for(int j = 0; j < uniqueInk.length; j++) {
			if(j == 0) {
				uniqueInk[j] = "Black";
			} else if(j == 1) {
				uniqueInk[j] = "Gray";
			}
		}
	}



	// ************ BEGIN INTERFACE FUNCTIONS ************

	/* toString():  Return the String status of any object. */
	public String toString() {

		if(currentTick <= MAX_TIME_ALLOT) {
			return "\n-----------------------\n" + "Current Simulation: Tick #" + currentTick +
			" of " + MAX_TIME_ALLOT + "\n" +
			generatedJobInformation + "\n";
		} else {
			return "\n------------------------\n" + "Current Simulation: Tick #" + currentTick +
			" of " + MAX_TIME_ALLOT + "\n\t{ALERT} Simulation in overtime!\n" + "\tEstimated end: ";
		}

	}

	/* update(): Done on essentially every tick. */
	// NOTE:  update() for a Clock is stubbed.
	public void update(){ }

	// ************ END INTERFACE FUNCTIONS ************

	public void setCurrentTick(int index) { currentTick = index; }
	public static int getTotalSimTime() { return MAX_TIME_ALLOT; }
	public static int getCurrentTick() { return currentTick; }
	public static int getCurrentJob() { return currentJob; }
	// How many jobs do you wish to burden-I MEAN do you wish to let me work on?
	public static int generateNumberOfJobs() { return (int)(jobCount * Math.random()); }
	public static int generateJobDuration() { return (int)(maxJobLength * Math.random() + 1); }

	/* pause():  Pauses the simulation every "interval" ticks. */

	public static void pause(int interval) {
		if(currentTick % interval == 0 && interval > 0) {
			Scanner input = new Scanner(System.in);
			//String breakWord = "";
			System.out.println("\t{PAUSE} Simulation has paused, every " + interval + " times.\n" +
			"\tIf you wish to break out of the pause, please enter a number.\n");
			while(!(input.hasNextInt())) {
				// Put the entire simulation into an infinite loop until
				// the user types a number.

             }

		}
	}


	/*  This method MUST be called from a loop to create each queue. */

	public static Queue setUniqueQueue(int id, int i, int j) {
		return new Queue(id, uniquePaper[i], uniqueInk[j]);
	}


	/* Generate a job and place it in the queue in the same step (legal).  Uses either Queue type.*/

	public void generateJob(Queue[] queues) {

		Job[] orders;
		String reply = "";
		int jobs = generateNumberOfJobs();

		if(jobs > 0) {
			orders = new Job[jobs];
			for(int i = 0; i < jobs; i++) {
				currentJob++;
				orders[i] = new Job(bias); // Construct for biased jobs
				for(int j = 0; j < queues.length; j++) {
					queues[j].enqueue(orders[i]);
				}
			}
			for(int i = 0; i < orders.length; i++) {
				reply += "\t" + orders[i].toString() + "\n";
			}
		}
		generatedJobInformation = jobs + " job(s) generated this tick.\n" + reply;
	}




	/* setPaperColor(), setInkColor():
	   Both of these functions use the same algorithm to generate a random color paper for the queues.
	   Six queues exist, so it will always match one of them.
	*/

	public static String setPaperColor(boolean biased) {

		// Key: 30 numbers, 1-10 = white, 11-20 = gray, 21-30 = goldenrod
		// Note:  75% of 30 is 22.
		double key = (30 * (Math.random()) + 1);

		if(biased) {

			if(key >= 1 && key <=22) {
				return "White";
			} else if(key > 22 && key <= 26) {
				return "Gray";
			} else {
				return "Goldenrod";
			}
		} else {
			// Key: 30 numbers, 1-10 = white, 11-20 = gray, 21-30 = goldenrod

			if(key >= 1 && key <=10) {
				return "White";
			} else if(key > 10 && key <= 20) {
				return "Gray";
			} else {
				return "Goldenrod";
			}
		}
	}

	public static String setInkColor(boolean biased) {

		// Key: 30 numbers, 1-22 = black, 23-30 = gray
		// Note:  75% of 30 is 22.

		double key = 30 * (Math.random()) + 1;
		if(biased) {
			if(key >= 1 && key <=22) {
				return "Black";
			} else {
				return "Gray";
			}
		} else {
			// Key: 30 numbers, 1-15 = black, 16-30 = gray

			if(key >= 1 && key <=15) {
				return "Black";
			} else {
				return "Gray";
			}
		}
	}

	// Just how important is a particular job?

	public static int generatePriority() {
		// Default priority is 3; if the other two numbers fall through, then Priority 3 will be returned.
		int answer = 3;

		double key = 30 * (randomizer) + 1;

		if(key >= 0 && key < 10) {
			answer = 1;
		} else if(key >= 10 && key < 20) {
			answer = 2;
		}

		return answer;
	}

	public void writeToFile(String file, String input) throws IOException {

		try {
			PrintWriter out = new PrintWriter(new FileWriter(file));
			out.println("Jason Black, CS 2050 - Assignment #7, Printer Simulation\n");
			out.println("Simulation stats:");
			double ratio = getCurrentTick() / MAX_TIME_ALLOT;
			out.println("\tSimulation ran for " + getCurrentTick() + "/" + MAX_TIME_ALLOT + " ticks; " +
			ratio + " jobs were generated versus one job leaving the queue per tick.");
			out.println("\tNumber of Jobs generated per tick: " + jobCount + "\n\tJob Length range: Between 1 and " +
			maxJobLength + "\n\n");
			out.println(input);

			out.close();
		} catch (IOException ioex) {
			System.out.println("IOException occurred.  Program aborting.");
			System.exit(0);
		}
	}
}
