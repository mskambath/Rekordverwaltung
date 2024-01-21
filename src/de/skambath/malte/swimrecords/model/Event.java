package de.skambath.malte.swimrecords.model;


/**
 * Represents an event in a swimming competition.
 * This class encapsulates the details of a swimming event, including its length, course type, sex category, and swim style.
 */
public class Event {

	/**
	 * Constructor for creating a new Event.
	 * 
	 * @param length The length of the event in meters or yards.
	 * @param courseType The type of course (e.g., SHORT_COURSE, LONG_COURSE).
	 * @param sex The sex category for the event (e.g., MALE, FEMALE, MIXED).
	 * @param swimStyle The swimming style used in the event (e.g., FREESTYLE, BACKSTROKE).
	 */
	public Event(int length, CourseType courseType, Sex sex, SwimStyle swimStyle){
		this.length = length;
		this.courseType = courseType;
		this.sex = sex;
		this.swimStyle = swimStyle;
	}
	
	// private members
	
    /** The length of the event in meters or yards. */
    private int length;

    /** The type of course for the event, such as short course or long course. */
    private CourseType courseType;

    /** The sex category of the event, such as male, female, or mixed. */
    private Sex sex;

    /** The style of swimming used in the event, such as freestyle, backstroke, etc. */
    private SwimStyle swimStyle;

    // public getters and setters
    
    /**
     * Gets the length of the event.
     * @return The length of the event.
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the length of the event.
     * @param length The length to set for the event.
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Gets the course type of the event.
     * @return The course type of the event.
     */
    public CourseType getCourseType() {
        return courseType;
    }

    /**
     * Sets the course type of the event.
     * @param courseType The course type to set for the event.
     */
    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    /**
     * Gets the sex category of the event.
     * @return The sex category of the event.
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Sets the sex category of the event.
     * @param sex The sex category to set for the event.
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * Gets the swim style of the event.
     * @return The swim style of the event.
     */
    public SwimStyle getSwimStyle() {
        return swimStyle;
    }

    /**
     * Sets the swim style of the event.
     * @param swimStyle The swim style to set for the event.
     */
    public void setSwimStyle(SwimStyle swimStyle) {
        this.swimStyle = swimStyle;
    }
    
    /**
     * Returns a string representation of the Event.
     * @return String representing the event.
     */
    @Override
    public String toString() {
        return length+ swimStyle.toString() + " " + sex.toString() + " " + courseType.toString() + " Bahn" ;
    }
}

