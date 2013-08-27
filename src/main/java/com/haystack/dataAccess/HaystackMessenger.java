package com.haystack.dataAccess;

import com.haystack.entities.Meeting;
import com.haystack.entities.Message;
import com.haystack.entities.User;

public class HaystackMessenger extends MessageJDBCTemplate {
	
	private static HaystackMessenger instance = null;
	
	private Message setUpSystemMessage(Integer toId) {
		Message message = new Message();
		message.setToUser(toId);
		message.setFromUser(null);
		return message;
	}
	
	protected HaystackMessenger() {}
	
	public void sendUserMessage(Integer toId, Integer fromId, 
								String body, String subject) {
		Message message = new Message();
		message.setToUser(toId);
		message.setFromUser(fromId);
		message.setSummary(body);
		message.setTitle(subject);
		message.setMessageType("user message");
		this.saveAndReturnKey(message, toId);
	}
	
	public void sendMatchMessage(Integer toId, Meeting meeting) {
		Message message = this.setUpSystemMessage(toId);
		String meetingName = meeting.getTitle();
		Integer meetingId = meeting.getId();
		String body = "<p>We've found some matches for your meeting: " + meetingName + "</p>" +
					  "<p><a href=\"/Matches/" + meetingId + "\">View them now.</a></p>";
		message.setTitle("New matches found");
		message.setSummary(body);
		message.setMessageType("new matches");
		this.saveAndReturnKey(message, toId);
	}
	
	public void sendSharedConnectionMessage(Integer toId, Meeting meeting, User user) {
		Message message = this.setUpSystemMessage(toId);
		String meetingName = meeting.getTitle();
		String username = user.getUsername();
		Integer userId = user.getId();
		String body = "<p>User '" + username + "' has accepted your meeting - '" + 
					  meetingName + "' - as a match. " +
					  "You can start messaging them!</p>" +
					  "<p><a href=\"/Inbox/" + userId + "\">Send them a message.</a></p>";
		message.setTitle("New shared connection!");
		message.setSummary(body);
		message.setMessageType("shared connection");
		this.saveAndReturnKey(message, toId);
	}
	
	public static HaystackMessenger getInstance() {
		if(instance == null) {
			instance = new HaystackMessenger();
		}
		return instance;
	}
	
}
