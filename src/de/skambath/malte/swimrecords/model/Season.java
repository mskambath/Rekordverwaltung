package de.skambath.malte.swimrecords.model;

/**
 * Represents a competitive season in swimming.
 * A season can be either a whole year or a competition year 
 * (from June 1st of a year to May 31st of the following year).
 */
public class Season {
    private String season; // Represents the season as a string

    /**
     * Constructor for Season.
     * @param year The year representing the season.
     * @param isCompetitionYear Flag indicating whether the season is a competition year.
     *                          If true, the season is from June 1st of the given year
     *                          to May 31st of the following year.
     *                          If false, it represents the whole year.
     */
    public Season(int year, boolean isCompetitionYear) {
        if (isCompetitionYear) {
            int endYear = year + 1;
            this.season = "01.06." + year + "|31.05." + endYear;
        } else {
            this.season = "01.01." + String.valueOf(year) + "|31.12." + String.valueOf(year);
        }
    }

    /**
     * Gets the representation of the season.
     * @return The season as a string.
     */
    public String getSeason() {
        return season;
    }
}


