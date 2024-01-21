package de.skambath.malte.swimrecords.model;

/**
 * Represents a table of records for a specific category in swimming competitions.
 * This table categorizes records based on age class, sex, and course type.
 */
public class RecordTable {
    private AgeClass ageClass; // The age class for this record table
    private Sex sex;           // The sex category for this record table
    private CourseType courseType; // The course type for this record table
    private String name;
    
    /**
     * Constructor for RecordTable.
     * 
     * @param ageClass The age class of the record table.
     * @param sex The sex category of the record table.
     * @param courseType The course type of the record table.
     */
    public RecordTable(AgeClass ageClass, Sex sex, CourseType courseType, String name) {
        this.ageClass = ageClass;
        this.sex = sex;
        this.courseType = courseType;
        this.name = name;
    }

    /**
     * Gets the age class of the record table.
     * 
     * @return The age class.
     */
    public AgeClass getAgeClass() {
        return ageClass;
    }

    /**
     * Sets the age class of the record table.
     * 
     * @param ageClass The age class to set.
     */
    public void setAgeClass(AgeClass ageClass) {
        this.ageClass = ageClass;
    }

    
    /**
     * Gets the name of the record table.
     * 
     * @return The name.
     */
    public String getName() {
		return name;
	}

    /**
     * Sets the name of the record table.
     * 
     * @param name The name to set.
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Gets the sex category of the record table.
     * 
     * @return The sex category.
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Sets the sex category of the record table.
     * 
     * @param sex The sex category to set.
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * Gets the course type of the record table.
     * 
     * @return The course type.
     */
    public CourseType getCourseType() {
        return courseType;
    }

    /**
     * Sets the course type of the record table.
     * 
     * @param courseType The course type to set.
     */
    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    /**
     * Returns a string representation of the RecordTable.
     * The name is composed of the age class, sex, and course type.
     * 
     * @return String representing the record table name.
     */
    @Override
    public String toString() {
        return ageClass.toString() + " " + sex.toString() + " " + courseType.toString();
    }
}
