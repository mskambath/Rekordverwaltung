package de.skambath.malte.swimrecords.model;

/**
 * Represents a single ranking record.
 */
public class Ranking {
    /** Ranking number. */
    private int rank;

    /** Name of the individual or team. */
    private String name;

    /** Birth year of the individual. */
    private int birthYear;

    /** Name of the club. */
    private String club;

    /** Performance time. */
    private String time;

    /** Points scored. */
    private int points;

    /** Location of the event. */
    private String location;

    /** Date of the event. */
    private String date;

    /**
     * Constructor for Ranking.
     * 
     * @param rank Ranking number.
     * @param name Name of the individual or team.
     * @param birthYear Birth year of the individual.
     * @param club Name of the club.
     * @param time Performance time.
     * @param points Points scored.
     * @param location Location of the event.
     * @param date Date of the event.
     */
    public Ranking(int rank, String name, int birthYear, String club, String time, int points, String location, String date) {
        this.rank = rank;
        this.name = name;
        this.birthYear = birthYear;
        this.club = club;
        this.time = time;
        this.points = points;
        this.location = location;
        this.date = date;
    }

    // Getters and Setters

    /**
     * Gets the ranking number.
     * @return The ranking number.
     */
    public int getRank() { return rank; }

    /**
     * Sets the ranking number.
     * @param rank The ranking number.
     */
    public void setRank(int rank) { this.rank = rank; }

    /**
     * Returns a string representation of the Ranking.
     * @return String representing the ranking.
     */
    @Override
    public String toString() {
        return "Ranking{" +
                "rank=" + rank +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", club='" + club + '\'' +
                ", time='" + time + '\'' +
                ", points=" + points +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                '}';
    }


    /**
     * Gets the name of the individual or team.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the individual or team.
     * @param name The name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the birth year of the individual.
     * @return The birth year.
     */
    public int getBirthYear() {
        return birthYear;
    }

    /**
     * Sets the birth year of the individual.
     * @param birthYear The birth year.
     */
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    /**
     * Gets the name of the club.
     * @return The club name.
     */
    public String getClub() {
        return club;
    }

    /**
     * Sets the name of the club.
     * @param club The club name.
     */
    public void setClub(String club) {
        this.club = club;
    }

    /**
     * Gets the performance time.
     * @return The performance time.
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the performance time.
     * @param time The performance time.
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Gets the location of the event.
     * @return The location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the event.
     * @param location The location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the date of the event.
     * @return The date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the event.
     * @param date The date.
     */
    public void setDate(String date) {
        this.date = date;
    }

}