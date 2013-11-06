//package org.latlonproject.printsim.factory;
//
//import org.latlonproject.printsim.Clock;
//import org.latlonproject.printsim.InkColor;
//import org.latlonproject.printsim.PaperColor;
//
//public class PrinterFactory {
//
//    private static volatile boolean isSet = false;
//    private static Printer[] printers;
//
//    private PrinterFactory() {
//    }
//
//    public static synchronized Printer[] createPrinters() {
//        if(!isSet) {
//            final Printer[] tempPrinters = new Printer[Clock.getMaxPrinters()];
//            int i = 0;
//            for (PaperColor paper : PaperColor.values()) {
//                for (InkColor ink : InkColor.values()) {
//                    tempPrinters[i] = new Printer(i+1, paper, ink);
//                    i++;
//                }
//            }
//            isSet = true;
//            printers = tempPrinters;
//        }
//        return printers;
//    }
//
//}
