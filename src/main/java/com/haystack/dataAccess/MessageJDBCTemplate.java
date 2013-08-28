package com.haystack.dataAccess;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
		String SQL = "INSERT INTO " + this.getTableName() + " (toUser, fromUser, msgType, subject, content, threadIs) " +
					 "VALUES (?, ?, ?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, toUserId, m.getFromUser(), m.getMessageType(), m.getTitle(), m.getSummary(),
									   toUserId + m.getFromUser());
	}
	
	@Override
	public Integer saveAndReturnKey(Message m, Integer toUserId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("toUser", toUserId);
		parameters.addValue("fromUser", m.getFromUser());
		parameters.addValue("msgType", m.getMessageType());
		parameters.addValue("isRead", 0);
		parameters.addValue("subject", m.getTitle());
		parameters.addValue("content", m.getSummary());
		parameters.addValue("threadId", toUserId + m.getFromUser());
		Number longKey = this.jdbcInsert.executeAndReturnKey(parameters);
		return longKey.intValue();
	}
	
}
