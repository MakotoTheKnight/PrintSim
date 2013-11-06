package org.latlonproject.printsim;

public interface Printer {

    public void print();

    public void bindToQueue(Queue theQueue);

    public void unbindFromQueue();

}
