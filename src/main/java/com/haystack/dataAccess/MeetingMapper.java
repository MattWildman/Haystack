package com.haystack.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.haystack.entities.Meeting;

@Service
public class MeetingMapper implements RowMapper<Meeting> {
	
	private ParticipantJDBCTemplate participantJDBCTemplate = new ParticipantJDBCTemplate();
	
	public Meeting mapRow(ResultSet rs, int rowNum) throws SQLException {
		Meeting meeting = new Meeting();
		meeting.setId(rs.getInt("id"));
		meeting.setUser(participantJDBCTemplate.getById(rs.getInt("userDesc")));
		return meeting;
	}

}
