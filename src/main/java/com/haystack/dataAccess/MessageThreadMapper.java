package com.haystack.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.haystack.entities.MessageThread;

@Repository
public class MessageThreadMapper implements RowMapper<MessageThread> {
	
	private UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
	private MessageJDBCTemplate msgJDBCTemplate = new MessageJDBCTemplate();
	
	public MessageThread mapRow(ResultSet rs, int rowNum) throws SQLException {
		MessageThread messageThread = new MessageThread();
		messageThread.setUser(userJDBCTemplate.getById(rs.getInt("u.id")));
		messageThread.setLastMessage(msgJDBCTemplate.getById(rs.getInt("m.id")));
		messageThread.setTotalCount(rs.getInt("msgCount"));
		messageThread.setUnreadCount(rs.getInt("unreadCount"));
		return messageThread;
	}

}
