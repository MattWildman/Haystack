package com.haystack.dataAccess;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.haystack.entities.Message;

@Repository
public class MessageJDBCTemplate extends HaystackDAO<Message> {
	
	public MessageJDBCTemplate() {
		this.setTableName("messages");
		this.setRowMapper(new MessageMapper());
	}
	
	public List<Message> getReceivedMessages(Integer userId) {
		return this.getByOwnerId("toUser", userId);
	}
	
	public List<Message> getSentMessages(Integer userId) {
		return this.getByOwnerId("fromUser", userId);
	}
	
	@Override
	public void save(Message m, Integer toUserId) {
		String SQL = "INSERT INTO " + this.getTableName() + " (toUser, fromUser, msgType, subject, content) " +
					 "VALUES (?, ?, ?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, toUserId, m.getFromUser(), m.getMessageType(), m.getTitle(), m.getSummary());
	}
	
}
