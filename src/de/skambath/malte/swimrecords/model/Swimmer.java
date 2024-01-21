package de.skambath.malte.swimrecords.model;

/**
 * Represents a swimmer with personal details and an identifier.
 */
public class Swimmer {
	private String lastName; // The swimmer's last name
	private String firstName; // The swimmer's first name
	private String dsvId; // The swimmer's DSV (Deutscher Schwimm-Verband) ID or similar unique identifier

	/**
     * Gets the last name of the swimmer.
     * 
     * @return The swimmer's last name.
     */
	public String getLastName() {
		return lastName;
	}

	/**
     * Sets the last name of the swimmer.
     * 
     * @param lastName The last name to be set for the swimmer.
     */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
     * Gets the first name of the swimmer.
     * 
     * @return The swimmer's first name.
     */
	public String getFirstName() {
		return firstName;
	}

	/**
     * Sets the first name of the swimmer.
     * 
     * @param firstName The first name to be set for the swimmer.
     */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
     * Gets the DSV ID of the swimmer.
     * 
     * @return The swimmer's DSV ID.
     */
	public String getDsvId() {
		return dsvId;
	}

	/**
     * Sets the DSV ID for the swimmer.
     * 
     * @param dsvId The DSV ID to be set for the swimmer.
     */
	public void setDsvId(String dsvId) {
		this.dsvId = dsvId;
	}
}
