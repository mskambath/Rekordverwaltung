package de.skambath.malte.swimrecords.model;

/**
 * Represents an individual event in a swimming competition.
 * This class extends from the base Event class, tailored for events where individual athletes compete.
 * It includes attributes and behaviors specific to individual swimming events.
 */
public class IndividualEvent extends Event {

    /**
     * Constructor for IndividualEvent.
     * Initializes a new instance of IndividualEvent with specified parameters.
     * 
     * @param length The length of the individual event in meters or yards.
     * @param courseType The type of course (e.g., SHORT_COURSE, LONG_COURSE).
     * @param sex The sex category for the individual event (e.g., MALE, FEMALE, MIXED).
     * @param swimStyle The swimming style used in the individual event (e.g., FREESTYLE, BACKSTROKE).
     */
    public IndividualEvent(int length, CourseType courseType, Sex sex, SwimStyle swimStyle) {
        super(length, courseType, sex, swimStyle);
    }
}
