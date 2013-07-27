package com.haystack.entities;

public class MeetingSearch extends Meeting {
	
	private Location location;
	private Journey journey;

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
