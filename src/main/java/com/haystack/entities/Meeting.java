package com.haystack.entities;

import lombok.Data;

@Data public class Meeting extends Connection {

	private Participant user;
	private Participant[] participants;
	
}
