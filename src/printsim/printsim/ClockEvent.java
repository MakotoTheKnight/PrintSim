package printsim;

public interface ClockEvent {

    /**
     * On every tick, the implementing object is required to update its state.
     * What this means varies between objects.
     */
    public void update();

}
