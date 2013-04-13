package printsim;

public interface IPrinter {

    public void print();

    public void bindToQueue(Queue theQueue);

    public void unbindFromQueue();

}
