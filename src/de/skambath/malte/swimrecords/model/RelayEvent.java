package de.skambath.malte.swimrecords.model;

/**
 * Represents a relay event in a swimming competition.
 * This class extends from the base Event class and includes additional attributes specific to relay events.
 */
public class RelayEvent extends Event {
	
	// Additional attribute for relay events
    private int factor;
	
	/**
     * Constructor for RelayEvent.
     * Initializes a new instance of RelayEvent with the specified parameters.
     * 
     * @param length The length of the relay event in meters or yards.
     * @param courseType The type of course (e.g., SHORT_COURSE, LONG_COURSE).
     * @param sex The sex category for the relay event (e.g., MALE, FEMALE, MIXED).
     * @param swimStyle The swimming style used in the relay event (e.g., FREESTYLE, BACKSTROKE).
     */
	public RelayEvent(int length, CourseType courseType, Sex sex, SwimStyle swimStyle) {
		super(length, courseType, sex, swimStyle);
		// TODO Auto-generated constructor stub
	}

	/**
     * Gets the factor associated with the relay event.
     * This factor might represent a scoring factor, team size, or other relay-specific metric.
     * 
     * @return The factor of the relay event.
     */
	public int getFactor() {
		return factor;
	}
	
	/**
     * Sets the factor for the relay event.
     * 
     * @param factor The factor to set for this relay event.
     */
	public void setFactor(int factor) {
		this.factor = factor;
	}
}
