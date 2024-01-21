package de.skambath.malte.swimrecords.model;

public class ClubRef {
	private String dsvId;
	private String name;
	private String dsvLinkId;
	private String stateGoverningBody;
	private String region;
	private String address;
	private String website;
	
	public ClubRef(String name, String dsvId, String dsvClubId, String zip, String city, String region) {
		this.dsvId = dsvId;
		this.name = name;
		this.dsvLinkId = dsvClubId;
		this.stateGoverningBody = "";
		this.region = region;
	}
	public String getDsvId() {
		return dsvId;
	}
	public void setDsvId(String dsvId) {
		this.dsvId = dsvId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDsvLinkId() {
		return dsvLinkId;
	}
	public void setDsvLinkId(String dsvLinkId) {
		this.dsvLinkId = dsvLinkId;
	}
	public String getStateGoverningBody() {
		return stateGoverningBody;
	}
	public void setStateGoverningBody(String stateGoverningBody) {
		this.stateGoverningBody = stateGoverningBody;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
}
