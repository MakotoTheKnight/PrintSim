public interface ClockEvent {
	
	/**
	 * Provides a convenient way to get information about the object through its life in the simulation.
	 * @return a String with details on its progress through the simulation.
	*/
	@Override
	public String toString();
		
	/**
	 *  Every object in the simulation has an update cycle.  What this entails is specific to the object.
	*/
	public void update();
	
}
