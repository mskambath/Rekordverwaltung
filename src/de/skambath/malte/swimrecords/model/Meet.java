package de.skambath.malte.swimrecords.model;

import java.util.Date;

public class Meet {
	private String meetName;
	private CourseType courseType;
	private String dsvMeetId;
	private DateRange dateRange;
	

	public CourseType getCourseType() {
		return courseType;
	}

	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}

	public String getMeetName() {
		return meetName;
	}

	public void setMeetName(String meetName) {
		this.meetName = meetName;
	}

	public String getDsvMeetId() {
		return dsvMeetId;
	}

	public void setDsvMeetId(String dsvMeetId) {
		this.dsvMeetId = dsvMeetId;
	}
		
}
