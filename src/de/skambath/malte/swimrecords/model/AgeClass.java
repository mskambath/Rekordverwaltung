package de.skambath.malte.swimrecords.model;

/**
 * Represents an age group in a swimming competition.
 * An age group can be defined by a single age or a range of ages.
 */
public class AgeClass {
    private int minAge; // Represents the starting age or a single age
    private int maxAge; // Represents the ending age in a range; -1 if it's a single age

    /**
     * Constructor for AgeClass representing a single age.
     * @param year The age represented by this class.
     */
    public AgeClass(int age) {
        this.minAge = age;
        this.maxAge = age; // -1 indicates a single age
    }

    /**
     * Constructor for AgeClass representing a range of ages.
     * @param startYear The starting age of the range.
     * @param endYear The ending age of the range.
     */
    public AgeClass(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    /**
     * Gets the starting age or the single age represented by this class.
     * @return The starting age or single age.
     */
    public int getMinAge() {
        return minAge;
    }

    /**
     * Sets the starting age or the single age for this class.
     * @param firstYear The starting age or single age to be set.
     */
    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    /**
     * Gets the ending age of the range. Returns -1 if this class represents a single age.
     * @return The ending age of the range, or -1 if it's a single age.
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * Sets the ending age of the range. Use minAge==maxAge if this class represents a single age.
     * @param secondYear The ending age of the range to be set, or -1 for a single age.
     */
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
    
    /**
     * Gets the birthclass that is in a given year part of this ageClass.
     * @param year a given year in which this AgeClass should be considered.
     * @return A BirthClass.
     */
    public BirthClass getBirthClass(int year) {
    	if(minAge < 0)
    		return new BirthClass(-1, -1);
    	return new BirthClass(year - maxAge, year - minAge);
    }
    
    public String toString() {
    	if(minAge > 0) {
    		return "AK" + minAge;
    	}
    	return "Offene Klasse";
    }
}

