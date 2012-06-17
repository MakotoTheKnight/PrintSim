 /*  SimDriver.java
    This class is is the "driver", or the only class that includes main.
    Its purpose is also to reduce the amount of functions that the Clock
    does.  Individual sections commented in-line.

*/
package printsim;
import javax.swing.JOptionPane;
import java.io.IOException;

public class SimDriver {

	public static boolean overtime = false, multiPrinting = false, experimental = false, writeToFile = false, writeToTerminal = true;
	public static int paperType = 0, inkType = 0, tick = 0, totalJobs = 0, interval = -1, duration = 0;
	public static int remainingTicks = 0;
	public static Clock overlord;
	public static MultiPrinter mprinter;
	public static Printer[] printers;
	public static PriorityQueue[] priQueues = new PriorityQueue[6]; // Can be set by default automatically
	public static Queue[] queues = new Queue[6]; // Can be set by default automatically
	public static String output = "", file = "";


	/* *********** BEGIN MAIN *********** */

	public static void main(String[] args) {

		/* --- BEGIN I/O FUNCTIONS --- */

		int experiment = Integer.parseInt(JOptionPane.showInputDialog(null,
		"Experiment[] experiments =\n1) Default, no experiments at all\n" +
		"2) Increase to 4 printers\n3) Printer -> Longest waiting queue\n" +
		"4) 70% B/W Jobs\n5) 70% B/W Jobs + 1 Constant B/W printer\n" +
		"6) Job priority (uses PriorityQueue)\n7) Custom: Multiprinter\n" +
		"8) Overlord Mode (assumes no experiments)\n" +
		"-1) Abort\n"));

		switch(experiment) {

			// In most cases, create queues and printers using a loop
			case 1:
				overlord = new Clock();
				printers = new Printer[3];
				for(int i = 0; i < queues.length; i++) {
					if(inkType == 2) {
						inkType = 0;
						paperType++;
					}
					queues[i] = overlord.setUniqueQueue((i+1), paperType, inkType++);
				}
				for(int i = 0; i < printers.length; i++) {
					printers[i] = new Printer(i+1, false);
				}
				output += "Experiment #1:  Default\n";
				break;

			case 2:
				overlord = new Clock();
				for(int i = 0; i < queues.length; i++) {
					if(inkType == 2) {
						inkType = 0;
						paperType++;
					}
					queues[i] = overlord.setUniqueQueue((i+1), paperType, inkType++);
				}
				printers = new Printer[4];
				for(int i = 0; i < printers.length; i++) {
					printers[i] = new Printer(i+1, false);
				}
				output += "Experiment #2:  Increase to Four Printers\n";
				break;
                
			case 3:
				int patience = Integer.parseInt(JOptionPane.showInputDialog(null,
				"How many ticks should a Job wait before it DEMANDS service?"));
				overlord = new Clock(true, patience);
				for(int i = 0; i < queues.length; i++) {
					if(inkType == 2) {
						inkType = 0;
						paperType++;
					}
					queues[i] = overlord.setUniqueQueue((i+1), paperType, inkType++);
				}
				printers = new Printer[3];
				for(int i = 0; i < printers.length; i++) {
					printers[i] = new Printer(i+1, false, true);
				}
				output += "Experiment #3:  Longest Waiting Queue\nJobs wait " + patience +
				" ticks before demanding that their queue gets attention next.";
				break;

			case 4:
				overlord = new Clock(100, true);
				for(int i = 0; i < queues.length; i++) {
					if(inkType == 2) {
						inkType = 0;
						paperType++;
					}
					queues[i] = overlord.setUniqueQueue((i+1), paperType, inkType++);
				}
				printers = new Printer[3];
				for(int i = 0; i < printers.length; i++) {
					printers[i] = new Printer(i+1, false);
				}
				output += "Experiment #4:  70% Black/White Job Bias\n";
				break;

			case 5:
				overlord = new Clock(100, true);
				for(int i = 0; i < queues.length; i++) {
					if(inkType == 2) {
						inkType = 0;
						paperType++;
					}
					queues[i] = overlord.setUniqueQueue((i+1), paperType, inkType++);
				}
				printers = new Printer[3];
				for(int i = 0; i < printers.length; i++) {
					printers[i] = new Printer(i+1, false);
				}
				// Set one static printer up (printers[0])
				printers[0] = new Printer(true, 1);
				output += "Experiment #5:  70% Black/White Job Bias + 1 Static B/W Printer\n";
				break;

			case 6:
				experimental = true;
				overlord = new Clock(true);
				for(int i = 0; i < priQueues.length; i++) {
					if(inkType == 2) {
						inkType = 0;
						paperType++;
					}
					priQueues[i] = overlord.setUniqueQueue(priQueues[i],
					(i+1), paperType, inkType++);

				}
				printers = new Printer[3];
				for(int j = 0; j < printers.length; j++) {
					printers[j] = new Printer((j+1), false);
				}
				output += "Experiment #6: Priority Jobs/Priority Queues\n";
				break;

			case 7:
				experimental = true;
				overlord = new Clock();
				for(int i = 0; i < queues.length; i++) {
					if(inkType == 2) {
						inkType = 0;
						paperType++;
					}
					queues[i] = overlord.setUniqueQueue((i+1), paperType, inkType++);
				}
				multiPrinting = true;
				printers = new Printer[2];
				mprinter = new MultiPrinter(1, false);
				for(int j = 0; j < printers.length; j++) {
					printers[j] = new Printer((j+1)+1, false);
				}
				output += "Experiment #7:  The MultiPrinter - handling " + mprinter.getSize() +
				" different bins AS one\n";
				break;
			case 8:
				int jobs = Integer.parseInt(JOptionPane.showInputDialog(null, "How many jobs per tick do you want this simulation to generate?"));
				int maxlength = Integer.parseInt(JOptionPane.showInputDialog(null, "What is the LONGEST job you want to generate?"));
				int clock = Integer.parseInt(JOptionPane.showInputDialog(null, "How long is this simulation going to run?"));
				int pri = Integer.parseInt(JOptionPane.showInputDialog(null, "How many printers would you like to have in this simulation?"));

				overlord = new Clock(jobs, maxlength, clock);
				for(int i = 0; i < queues.length; i++) {
					if(inkType == 2) {
						inkType = 0;
						paperType++;
					}
					queues[i] = overlord.setUniqueQueue((i+1), paperType, inkType++);
				}
				printers = new Printer[pri];
				for(int i = 0; i < printers.length; i++) {
					printers[i] = new Printer(i+1, false);
				}
				output += "Experiment #8:  Overlord Mode\n";
				break;

			default:
				System.exit(0);
				break;

		}
		
		// Specify if we're writing to file or not.  If we aren't, all data is dumped to the terminal.
		// If we are, only the summary is written to file.
		int filewrite = JOptionPane.showConfirmDialog(null, "Are we saving to a file?");
		if(filewrite == JOptionPane.YES_OPTION) {
			writeToFile = true;
			file = JOptionPane.showInputDialog(null, "What file to save the info?");
		} else {
			writeToFile = false;
		}
		int terminalwrite = JOptionPane.showConfirmDialog(null, "Do you want to dump the data to a terminal?");
		if(terminalwrite == JOptionPane.YES_OPTION) {
			writeToTerminal = true;
			// Specify interval for pauses (real-time information only)
			interval = Integer.parseInt(JOptionPane.showInputDialog(null,
			"Pause Interval=?\n0=no interval"));
			if(interval == 0) {
				interval = -1; // We use modulo for the interval, so we can't divide by zero.
			}
		} else {
			writeToTerminal = false;
		}
		

		/* --- END I/O FUNCTIONS ---*/


		/* *********** BEGIN MAIN LOOP *********** */

		while(tick < overlord.getTotalSimTime()) {

			overlord.setCurrentTick(++tick);

			if(experimental) {
				experimentalFunctions();
			} else {
				normalFunctions();
			}

			if(totalJobs > 0 && overlord.getCurrentTick() == overlord.getTotalSimTime()) {
				overtime = true;
				if(writeToTerminal) {
					System.out.println("\t{WARNING} Simulation not complete in time allotted.  Will " +
					"continue processing jobs in another loop.\n\tEstimated remaining Jobs: " +
					totalJobs + "\n");
				}
				if(!(experimental) || multiPrinting){
					for(Queue q: queues) {
						remainingTicks += q.getSize();
					}
				} else if(!(multiPrinting)) {
					for(PriorityQueue q: priQueues) {
						remainingTicks += q.getSize();
					}
				}
			}
			overlord.pause(interval);
		}

		/* *********** END MAIN LOOP *********** */

		/* *********** BEGIN CLEAN-UP LOOP *********** */

		if(overtime) {

			do {
				overlord.setCurrentTick(++tick);
				if(experimental) {
					experimentalFunctions();
				} else {
					normalFunctions();
				}
				overlord.pause(interval);

			} while(totalJobs > 0);
		}

		if(multiPrinting) {
			output += "\n" + mprinter.getStats();
			System.out.println(mprinter.getStats());
		}

		for(Printer pr: printers) {
			if(writeToFile) {
				output += "\n" + pr.getStats();
			} else if(writeToTerminal) {
				System.out.println(pr.getStats());
			}
		}

		/* *********** END CLEAN-UP LOOP *********** */
		
		if(writeToFile) {
			try {
				overlord.writeToFile(file, output);
			} catch(IOException ioex) {
				System.out.println("Could not write to file, sorry.");
			}
		}
			
		
	}


	/* *********** END MAIN *********** */

	/* *********** BEGIN HELPER METHODS *********** */

	// Defines "normal" operations with this program
	public static void normalFunctions() {

		if(!(overtime)) {
			overlord.generateJob(queues);
			if(writeToTerminal) {
				System.out.println(overlord.toString());
			}

		} else {
			if(writeToTerminal) {
				System.out.println(overlord.toString() + totalJobs + " Job(s) left.\n");
			}
		}
		totalJobs = 0;

		for(Queue q: queues) {
			q.update();
			totalJobs += q.getSize();
		}

		for(Printer pr: printers) {
			pr.update();
			pr.symlink(queues, multiPrinting);
			pr.print(multiPrinting);

			if(!(pr.isAttached())) {
				pr.changeConfiguration(queues, multiPrinting);
			}


			if(!(pr.getCurrentJob() == null)) {
				totalJobs++;
			}
			if(!(pr.printStatus().equals(""))) {
				if(writeToTerminal) {
					System.out.println(pr.printStatus());
				}
			}
		}
	}

	// Defines "experimental" operations with this program
	public static void experimentalFunctions() {

		if(!(overtime) && !(multiPrinting)) {

			overlord.generateJob(priQueues);
			totalJobs = 0;
			if(writeToTerminal) {
				System.out.println(overlord.toString());
			}

		} else if(!(overtime) && multiPrinting) {

			overlord.generateJob(queues);
			if(writeToTerminal) {
				System.out.println(overlord.toString());
			}
			totalJobs = 0;
		} else if(writeToTerminal) {
				System.out.println(overlord.toString() + totalJobs + " Job(s) left.\n");
		}
		totalJobs = 0;
	

// CASE:  Priority Queue experiment
		if(!(multiPrinting)) {

			for(PriorityQueue pq: priQueues) {
				pq.update();
				totalJobs += pq.getNumberOfJobs();
			}

			for(Printer pr: printers) {
				pr.update();
				pr.symlink(priQueues);
				pr.print();

				if(!(pr.isAttached())) {
					pr.changeConfiguration(priQueues);
				}

				if(!(pr.getCurrentJob() == null)) {
					totalJobs++;
				}
				if(!(pr.printStatus().equals("")) && writeToTerminal) {
					System.out.println(pr.printStatus());
				}
			}
		} else {
			totalJobs = 0;

			for(Queue q: queues) {
				q.update();
				totalJobs += q.getSize();
			}
			mprinter.update();
			mprinter.print();
			if(!(mprinter.isAttached())) {
				mprinter.changeConfiguration(queues);
			}

			if(!(mprinter.printStatus().equals("")) && writeToTerminal) {
				System.out.println(mprinter.printStatus());
			}

			totalJobs += mprinter.remainingJobs();

			for(Printer pr: printers) {
				pr.update();
				pr.symlink(queues, multiPrinting);
				pr.print(multiPrinting);

				if(!(pr.isAttached())) {
					pr.changeConfiguration(queues, multiPrinting);
				}
				
				if(!(pr.getCurrentJob() == null)) {
					totalJobs++;
				}
				if(!(pr.printStatus().equals("")) && writeToTerminal) {
					System.out.println(pr.printStatus());
				}
			}
		}
	}
        /* *********** END HELPER METHODS *********** */
}
