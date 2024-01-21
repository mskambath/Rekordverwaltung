package de.skambath.malte.swimrecords.model;

/**
 * Enum representing different sex categories in swimming events.
 * Includes categories for female, male, mixed (only for relays), and diverse.
 */
public enum Sex {
	Female("W"), Male("M"), Mixed("X"), Diverse("D");
	
	private final String sexId;

	/**
     * Constructor for the Sex enum.
     * @param sexId The string identifier for the sex category.
     */
    Sex(String sexId) {
        this.sexId = sexId;
    }

    /**
     * Gets the string identifier for the sex category.
     * @return The string identifier.
     */
    public String getSexId() {
        return sexId;
    }

    /**
     * Converts a string identifier to a Sex category.
     * @param sexId The string identifier for the sex category.
     * @return The corresponding Sex category.
     * @throws IllegalArgumentException If the string does not match any Sex category.
     */
    public static Sex fromSexId(String sexId) {
        for (Sex sex : values()) {
            if (sex.sexId.equals(sexId)) {
                return sex;
            }
        }
        throw new IllegalArgumentException("Invalid StrokeId: " + sexId);
    }
    
    /**
     * Returns the string representation of the sex category.
     * @return The string representation.
     */
    @Override
    public String toString() {
        return sexId;
    }
    
    /**
     * Provides a longer, more descriptive string representation of the sex category.
     * @return A descriptive string for the sex category.
     */ 
    public String toLongLabelString() {
    	switch(sexId) {
    	case "W" : 
    		return "weiblich";
    	case "M" : 
    		return "männlich";
    	case "X" : 
    		return "mixed";
    	case "D" : 
    		return "divers";
    	default :
    		return "";
    	}
    }
}
