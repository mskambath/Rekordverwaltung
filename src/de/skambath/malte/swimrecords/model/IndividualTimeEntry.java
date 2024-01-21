package de.skambath.malte.swimrecords.model;

import java.time.Duration;
import java.util.Date;

public class IndividualTimeEntry {
	private String Name;
	private String Surname;
	private String SwimmerId;
	private int YearAge;
	private int Birthyear;
	private String Club;
	private String ClubId;
	private Date Date;
	private String MeetName;
	private Duration Time;
	private Event Discipline;
	//private AgeGroup Agegroup;
	private CourseType CourseType;
	private Sex sex;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSurname() {
		return Surname;
	}
	public void setSurname(String surname) {
		Surname = surname;
	}
	public int getBirthyear() {
		return Birthyear;
	}
	public void setBirthyear(int birthyear) {
		Birthyear = birthyear;
	}
	public int getYearAge() {
		return YearAge;
	}
	public void setYearAge(int yearAge) {
		YearAge = yearAge;
	}
	public String getSwimmerId() {
		return SwimmerId;
	}
	public void setSwimmerId(String swimmerId) {
		SwimmerId = swimmerId;
	}
	public String getClub() {
		return Club;
	}
	public void setClub(String club) {
		Club = club;
	}
	public String getClubId() {
		return ClubId;
	}
	public void setClubId(String clubId) {
		ClubId = clubId;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	public String getMeetName() {
		return MeetName;
	}
	public void setMeetName(String meetName) {
		MeetName = meetName;
	}
	public Event getDiscipline() {
		return Discipline;
	}
	public void setDiscipline(Event discipline) {
		Discipline = discipline;
	}
	public Duration getTime() {
		return Time;
	}
	public void setTime(Duration time) {
		Time = time;
	}
	public CourseType getCourseType() {
		return CourseType;
	}
	public void setCourseType(CourseType courseType) {
		CourseType = courseType;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}  
}
