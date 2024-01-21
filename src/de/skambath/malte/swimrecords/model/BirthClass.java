package de.skambath.malte.swimrecords.model;

/**
 * Represents an age class for swimming competitions.
 * An age class is defined by two years: a starting year and an ending year.
 */
public class BirthClass {
    private int firstYear; // The starting year of the age class
    private int secondYear; // The ending year of the age class

    /**
     * Constructor for AgeClass.
     * @param firstYear The starting year of the age class.
     * @param secondYear The ending year of the age class.
     */
    public BirthClass(int firstYear, int secondYear) {
        this.firstYear = firstYear;
        this.secondYear = secondYear;
    }

    /**
     * Gets the starting year of the age class.
     * @return The starting year.
     */
    public int getFirstYear() {
        return firstYear;
    }

    /**
     * Sets the starting year of the age class.
     * @param firstYear The starting year to set.
     */
    public void setFirstYear(int firstYear) {
        this.firstYear = firstYear;
    }

    /**
     * Gets the ending year of the age class.
     * @return The ending year.
     */
    public int getSecondYear() {
        return secondYear;
    }

    /**
     * Sets the ending year of the age class.
     * @param secondYear The ending year to set.
     */
    public void setSecondYear(int secondYear) {
        this.secondYear = secondYear;
    }
}

