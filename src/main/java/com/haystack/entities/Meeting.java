package com.haystack.entities;

import java.util.List;

public class Meeting extends Connection {

	private Participant user;
	private List<Participant> participants;
	
	public Meeting() {
		this.setConType("meeting");
	}
	
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
	
	public void addParticipant(Participant participant) {
		this.participants.add(participant);
	}
	
	public void removeParticipant(Integer pId) {
		for (Participant p : participants) {
			if (p.getId() == pId)
				participants.remove(p);
		}
	}
	
}
