/* MultiPrinter.java

   Conceptually, this is nothing more than an array of Printers,
   all working in tandem as one MultiPrinter.  Hence,
   most everything it does is ripped from Printer.
   No real reason that it extends Printer; thought it'd be nice to attempt
   some inheritance in this project.
   Written by Jason Black, 19 November 2008
   
*/
package printsim;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;

public class MultiPrinter extends Printer implements ClockEvent {

	protected Printer[] printers;
	protected int printer_ID, idleCounter;

	public MultiPrinter(int id, boolean no_config_chg) {
		
		super(id, no_config_chg);
		printer_ID = id;
		int bins = Integer.parseInt(JOptionPane.showInputDialog(null,
		"How many bins do you want this printer to be able to handle?"));
		
		printers = new Printer[bins];
		for(int i = 0; i < printers.length; i++) {
			printers[i] = new Printer(id, no_config_chg);
		}
	}
	
	// ************ BEGIN INTERFACE FUNCTIONS ************
	
	/* toString():  Return the String status of any object. */
	@Override
	public String toString() {
		
		String info = "Attached to Queue(s) # ";
		String header = "\tMultiPrinter #" + printer_ID;
		String middle = "{ ";
		
		for(Printer pr: printers) {
			if(pr.isAttached()) {
				info += pr.queue_ID + " ";
			}
			middle += "[" + pr.getPaperColor() + ", " + pr.getInkColor() + "]";
		}
		
		return header + middle + info + "}\n";
	}
	
		
	/* update():  If I'm going to update, then please do so. */
	@Override
	public void update() {
	
		for(Printer pr: printers) {
			
			pr.update();
			idleCounter = Integer.MAX_VALUE;
			if(pr.getIdleTime() < idleCounter) {
				idleCounter = pr.getIdleTime();
			}
			idleCounter = (int)(idleCounter / printers.length);
		}		
	}
	
	// ************ END INTERFACE FUNCTIONS ************
	
	public int getSize() { return printers.length; }
	
	public int remainingJobs() {
		
		int jobs = 0;	
		for(Printer pr: printers) {
			if(pr.getCurrentJob() != null) {
				jobs++;
			}
		}
		return jobs;
	}
			
	@Override
	public String printStatus() {
		
		String status = "";
		for(Printer pr: printers) {
			if(!(pr.printStatus().equals(""))) {
				status += pr.printStatus() + "\n";
			}
		}
		
		return status;
	}

	@Override
	protected void reset() {
		
		for(Printer pr: printers) {
			pr.reset();
		}
	}

	@Override
	public void removeQueue() {
		
		for(Printer pr: printers) {
			pr.removeQueue();
		}
	}

	public void symlink(Queue[] q) {
		
		for(Printer pr: printers) {
			pr.symlink(q, true);
		}
	}

	@Override
	public void print() {
		
		for(Printer pr: printers) {
			pr.print(true);
		}
	}
	
	public void changeConfiguration(Queue[] q) {
		for(Printer pr: printers) {
			pr.changeConfiguration(q, true);
		}
	}

	@Override
	public String getStats() {
	
		DecimalFormat df = new DecimalFormat("###.##");
		
		String reply = "\n-------------------------------\n" +
		"MultiPrinter #" + printer_ID +", idle for an estimated " +
		(idleCounter / printers.length) + " total ticks.\n" +
		"Job readout for this MultiPrinter.\nJobs read from L-R, in processed order.\n" +
		"EX: {Job #0, paper/ink, duration}";
		ArrayList<Job> processed = new ArrayList<Job>(80);
		int changes = 0;
		double avgIdle = 0, avgLength = 0;
		for(Printer pr: printers) {
			ArrayList<Job> temp = pr.getCompleteJobs();
			for(Job j: temp) {
				processed.add(j);
				avgLength += j.getDuration();
			}
			changes += pr.getChanges();
		}
		
		String[] jobReadout = new String[processed.size()];

		for(int i = 0; i < jobReadout.length; i++) {
			Job j = processed.get(i);
			int jNum = j.getJobNumber();
			int jDuration = j.getDuration();
			String inkColor = j.getInkColor();
			String paperColor = j.getPaperColor();
			avgIdle += j.getIdleTime();
			
			
			if(paperColor.equals("White")) {
				paperColor = "Wht";
			} else if(paperColor.equals("Goldenrod")) {
				paperColor = "Gldnrd";
			} else if(paperColor.equals("Gray")) {
				paperColor = "Gry";
			}
			if(inkColor.equals("Black")) {
				inkColor = "Blk";
			} else if(inkColor.equals("Gray")) {
				inkColor = "Gry";
			}
				
			jobReadout[i] = "{Job #" + jNum + ", " + paperColor +
			"/" + inkColor + ", " + jDuration+ "}";
		
			j = null;
		}
		
		avgIdle /= processed.size();
		avgLength /= processed.size();
		
		reply += "\n\n";
		for(int i = 0; i < jobReadout.length; i++) {
			if(i % 4 == 0) {
				reply += "\n";
			}
			reply += jobReadout[i] + "\t";
		
		}
		reply += "\n\n";
		reply += "Avg. Time Enqueued per Job: " +  df.format(avgIdle) + "\n" +
		"Avg. Length per Job: " + df.format(avgLength) +
		"\n" + "# of Jobs processed: " + processed.size() + "\n" +
		"# of MultiPrinter reconfigurations: " + (changes / printers.length);
		
		return reply;
		
	}
}
