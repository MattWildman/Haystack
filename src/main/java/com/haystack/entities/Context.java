package com.haystack.entities;

import java.util.Date;

import org.joda.time.DateTime;

public class Context extends HaystackEntity{
	
	private String earliestString;
	private String latestString;
	private Date earliest;
	private Date latest;
	private String locationType;
	private Location location;
	private Journey journey;

	public String getEarliestString() {
		return earliestString;
	}

	public void setEarliestString(String earliestString) {
		this.earliestString = earliestString;
	}

	public String getLatestString() {
		return latestString;
	}

	public void setLatestString(String latestString) {
		this.latestString = latestString;
	}

	public Date getEarliest() {
		return earliest;
	}

	public void setEarliest(Date date) {
		this.earliest = date;
	}

	public Date getLatest() {
		return latest;
	}

	public void setLatest(Date latest) {
		this.latest = latest;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Journey getJourney() {
		return journey;
	}

	public void setJourney(Journey journey) {
		this.journey = journey;
	}
	
	public void setDateTimeStrings() {
		DateTime edt = new DateTime(this.getEarliest());
		DateTime ldt = new DateTime(this.getLatest());
		if (edt.getDayOfYear() == ldt.getDayOfYear() && 
			edt.getYear() == ldt.getYear()) {
			this.setEarliestString(edt.toString("'On' EEE d MMM yyyy 'between' HH:mm"));
			this.setLatestString(ldt.toString("HH:mm"));
		}
		else {
			this.setEarliestString(edt.toString("'Between' EEE d MMM yyyy 'at' HH:mm"));
			this.setLatestString(ldt.toString("EEE d MMM yyyy 'at' HH:mm"));
		}
	}
	
}
