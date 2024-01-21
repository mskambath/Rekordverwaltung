package de.skambath.malte.swimrecords.model;

/**
 * Enum representing different types of swimming courses.
 * It includes various course lengths and types, such as short and long courses, both in meters and yards,
 * as well as open water swimming.
 */
public enum CourseType {
	// Enum constants, each representing a specific type of course.
    SHORT_COURSE_METRIC("25m"), // Represents a short course in metric units (25 meters).
    LONG_COURSE_METRIC("50m"), // Represents a long course in metric units (50 meters).
    SHORT_COURSE_YARDS("25yd"), // Represents a short course in yard units (25 yards).
    LONG_COURSE_YARDS("50yd"), // Represents a long course in yard units (50 yards).
    OPEN_WATER_METRIC("Open-m"), // Represents an open water course in metric units.
    OPEN_WATER_YARDS("Open-yd"); // Represents an open water course in yard units.

    private final String str; // A string representation of the course type.

    /**
     * Constructor for CourseType enum.
     * @param courseStr The string representation of the course type.
     */
    CourseType(String courseStr) {
        this.str = courseStr;
    }

    /**
     * Returns the string representation of the course type.
     * @return The string representation.
     */
    @Override
    public String toString() {
        return str;
    }
    
    /**
     * Converts a string identifier to a CourseType.
     * @param courseTypeId The string identifier for the course type.
     * @return The corresponding CourseType.
     * @throws IllegalArgumentException If the string does not match any CourseType.
     */
    public static CourseType fromCourseTypeId(String courseTypeId) {
        for (CourseType ct : values()) {
            if (ct.str.equals(courseTypeId)) {
                return ct;
            }
        }
        throw new IllegalArgumentException("Invalid CourseTypeId: " + courseTypeId);
    }

    /**
     * Provides a longer, more descriptive string representation of the course type.
     * @return A descriptive string for the course type.
     */
	String toLongString() {
		switch(this.str) {
		case "25m":
			return "Kurzbahn";
		case "50m":
			return "Langbahn";
		case "25yd":
			return "Kurzbahn (yd)";
		case "50yd":
			return "Langbahn (yd)";
		case "Open-m":
			return "Freiwasser";
		case "Open-yd":
			return "Freiwasser (yd)";
		default:
			return "";
		}
	}
}