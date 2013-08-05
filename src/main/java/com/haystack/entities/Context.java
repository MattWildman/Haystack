package com.haystack.entities;

import java.util.Date;

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
	
}
