package com.haystack.entities;

import java.sql.Date;

public class Context extends HaystackEntity{
	
	private Date earliest;
	private Date latest;
	private String locationType;
	private Location location;
	private Journey journey;

	public Date getEarliest() {
		return earliest;
	}

	public void setEarliest(Date earliest) {
		this.earliest = earliest;
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
