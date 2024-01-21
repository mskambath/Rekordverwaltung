package de.skambath.malte.swimrecords.model;

import java.util.Date;

/**
 * @class DateRange
 * @brief Represents a range between two dates.
 *
 * This class encapsulates a range defined by two Date objects, representing
 * the start and end of the range.
 */
public class DateRange {
	//  The first date in the range.
	private Date firstDate;
	
	// The last date in the range.
	private Date lastDate;
	
	/**
     * @brief Constructor for creating a date range.
     * 
     * Initializes the date range with a start (first) and end (last) date.
     * @param first The starting date of the range.
     * @param last The ending date of the range.
     */
	DateRange(Date first, Date last) {
		this.firstDate = first;
		this.lastDate = last;
	}
	
	/**
     * @brief Retrieves the first date of the range.
     * @return The starting date.
     */
	public Date getFirstDate() {
		return firstDate;
	}
	
	/**
     * @brief Sets the first date of the range.
     * @param firstDate The date to set as the first date.
     */
	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}
	
	/**
     * @brief Retrieves the last date of the range.
     * @return The ending date.
     */
	public Date getLastDate() {
		return lastDate;
	}
	
	/**
     * @brief Sets the last date of the range.
     * @param lastDate The date to set as the last date.
     */
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
}
