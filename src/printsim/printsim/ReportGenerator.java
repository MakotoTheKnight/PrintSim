//package printsim;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//public class ReportGenerator {
//    private final Clock clock;
//
//    public void writeToFile(String file, String input) throws IOException {
//
//        try {
//            PrintWriter out = new PrintWriter(new FileWriter(file));
//            out.println("Jason Black, CS 2050 - Assignment #7, Printer Simulation\n");
//            out.println("Simulation stats:");
//            double ratio = clock.getCurrentTick() / Clock.MAX_TIME_ALLOT;
//            out.println("\tSimulation ran for " + clock.getCurrentTick() + "/" + Clock.MAX_TIME_ALLOT + " ticks; " +
//                    ratio + " jobs were generated versus one job leaving the queue per tick.");
//            out.println("\tNumber of Jobs generated per tick: " + Clock.jobCount + "\n\tJob Length range: Between 1 and " +
//                    Clock.maxJobLength + "\n\n");
//            out.println(input);
//
//            out.close();
//        } catch (IOException ioex) {
//            System.out.println("IOException occurred.  Program aborting.");
//            System.exit(0);
//        }
//    }
//}
