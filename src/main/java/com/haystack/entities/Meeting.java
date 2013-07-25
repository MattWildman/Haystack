package com.haystack.entities;

import java.util.List;

public class Meeting extends Connection {

	private Participant user;
	private List<Participant> participants;
	
	public Participant getUser() {
		return user;
	}
	
	public void setUser(Participant user) {
		this.user = user;
	}
	
	public List<Participant> getParticipants() {
		return participants;
	}
	
	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}
	
}
