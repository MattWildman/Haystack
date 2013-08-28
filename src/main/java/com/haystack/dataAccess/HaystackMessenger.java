package com.haystack.dataAccess;

import java.util.List;

import com.haystack.entities.Meeting;
import com.haystack.entities.Message;
import com.haystack.entities.MessageThread;
import com.haystack.entities.User;

public class HaystackMessenger extends MessageJDBCTemplate {
	
	private static HaystackMessenger instance = null;
	
	private Message setUpSystemMessage(Integer toId) {
		Message message = new Message();
		message.setToUser(toId);
		message.setFromUser(1);
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
	
	public List<MessageThread> getMessageThreads(Integer userId) {
		String SQL = "SELECT m.id, u.id, msgCount, unreadCount " +
					 "FROM messages m " +
					 "INNER JOIN " +
					 "    (SELECT threadId,  " +
					 "     MAX(date) AS newestDate,  " +
				 	 "     COUNT(*) AS msgCount, " +
					 "     COUNT(CASE WHEN isRead = 0 THEN 1 END) AS unreadCount " +
					 "     FROM messages  " +
					 "     GROUP BY threadId) m2 ON m.date = m2.newestDate " +
					 "AND m.threadId  = m2.threadId " +
					 "INNER JOIN users u ON m.threadId - ? = u.id " +
					 "WHERE isRead = 0 " +
					 "AND (m.toUser = ? OR m.fromUser = ?) " +
					 "ORDER BY date DESC";
		List<MessageThread> results = jdbcTemplateObject.query(SQL, 
								new Object[] {userId, userId, userId},
								new MessageThreadMapper());
		return results;
	}
	
	public List<Message> getMessagesInThread(Integer userId, Integer otherUserId) {
		String SQL = "SELECT * " +
					 "FROM messages m " +
					 "WHERE (m.toUser = ? " +
					 "OR m.fromUser = ?) " +
					 "AND m.threadId - ? = ? " +
					 "ORDER BY m.date DESC";
		List<Message> results = jdbcTemplateObject.query(SQL, 
								new Object[] {userId, userId, userId, otherUserId},
								this.getRowMapper());
		return results;
	}
	
	public static HaystackMessenger getInstance() {
		if(instance == null) {
			instance = new HaystackMessenger();
		}
		return instance;
	}
	
}
