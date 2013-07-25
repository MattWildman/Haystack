package com.haystack.dataAccess;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.haystack.entities.Message;

@Service
public class MessageMapper implements RowMapper<Message> {
	
	private Boolean getReadStatus(Integer isRead) {
		return isRead >= 1;
	}
	
	private Date convertTimestamp(Timestamp t) {
		Date date = t;
		return date;
	}
	
	public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
		Message message = new Message();
		message.setId(rs.getInt("id"));
		message.setTitle(rs.getString("subject"));
		message.setSummary(rs.getString("content"));
		message.setToUser(rs.getInt("toUser"));
		message.setFromUser(rs.getInt("fromUser"));
		message.setMessageType(rs.getString("msgType"));
		message.setRead(this.getReadStatus(rs.getInt("isRead")));
		message.setDate(this.convertTimestamp(rs.getTimestamp("date")));
		return message;
	}

}
