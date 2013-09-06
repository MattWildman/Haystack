package com.haystack.dataAccess;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

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
	
	public static HaystackMessenger getInstance() {
		if(instance == null) {
			instance = new HaystackMessenger();
		}
		return instance;
	}
	
	public void markThreadAsRead(Integer userId, Integer otherUserId) {
		
		String SQL = "UPDATE messages " +
				 	 "SET isRead = 1 " +
					 "WHERE isRead = 0 " +
					 "AND threadId - ? = ? " +
					 "AND toUser = ?";
		
		jdbcTemplateObject.update(SQL, otherUserId, userId, userId);
	}
	
	public Boolean hasPermission(Integer userId, Integer permittedId) {
		
		if (permittedId == 1) {
			return true;
		}
		
		String SQL = "SELECT userId " +
					 "FROM messagepermissionsview " +
					 "WHERE userId = ? " +
					 "AND permittedId = ?";
		
		SqlRowSet srs = jdbcTemplateObject.queryForRowSet(SQL, 
										  new Object[] {userId, permittedId});
		return srs.first();
	}
	
	public void sendUserMessage(Message message) {
		if (this.hasPermission(message.getFromUser(), message.getToUser())) {
			message.setMessageType("user message");
			this.saveAndReturnKey(message, message.getToUser());
		}
	}
	
	public void sendMatchMessage(Integer toId, Meeting meeting) {
		Message message = this.setUpSystemMessage(toId);
		String meetingName = meeting.getTitle();
		Integer meetingId = meeting.getId();
		String body = "We've found some matches for your meeting: " + meetingName + "<br>" +
					  "<a href=\"../Matches/Pending/" + meetingId + "\">View them now.</a>";
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
		String body = "User '" + username + "' has accepted your meeting - '" + 
					  meetingName + "' - as a match.<br>" +
					  "You can start messaging them! " +
					  "<a href=\"../Inbox/" + userId + "\">Send them a message.</a>";
		message.setTitle("New shared connection!");
		message.setSummary(body);
		message.setMessageType("shared connection");
		this.saveAndReturnKey(message, toId);
	}
	
	public List<MessageThread> getMessageThreads(Integer userId) {
		String SQL = "SELECT m.id, m.date, u.id, msgCount, unreadCount " +
					 "FROM messages m " +
					 "INNER JOIN " +
					 "    (SELECT threadId,  " +
					 "     MAX(date) AS newestDate,  " +
				 	 "     COUNT(*) AS msgCount, " +
					 "     COUNT(CASE WHEN isRead = 0 AND toUser = ? THEN 1 END) AS unreadCount " +
					 "     FROM messages  " +
					 "     GROUP BY threadId) m2 ON m.date = m2.newestDate " +
					 "AND m.threadId  = m2.threadId " +
					 "INNER JOIN users u ON m.threadId - ? = u.id " +
					 "WHERE (m.toUser = ? OR m.fromUser = ?) " +
					 "AND u.id IN (1 OR (SELECT permittedId FROM messagepermissionsview " +
					 "		   			 WHERE userId = ?)) " +
					 "ORDER BY m.date DESC";
		List<MessageThread> results = jdbcTemplateObject.query(SQL, 
								new Object[] {userId, userId, userId, userId, userId},
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

	public Integer getUnreadCount(Integer userId) {
		
		String SQL = "SELECT COUNT(*) " +
					 "FROM messages " +
					 "WHERE toUser = ? " +
					 "AND isRead = 0";
		
		Integer unreadCount = jdbcTemplateObject.queryForInt(SQL, userId);
		return unreadCount;
	}
	
}
