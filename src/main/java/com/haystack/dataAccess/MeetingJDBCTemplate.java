package com.haystack.dataAccess;

import org.springframework.stereotype.Repository;

import com.haystack.entities.Meeting;

@Repository
public class MeetingJDBCTemplate extends HaystackDAO<Meeting> {
	
	public MeetingJDBCTemplate() {
		this.setTableName("meetings");
		this.setRowMapper(new MeetingMapper());
	}
	
	@Override
	public void save(Meeting m, Integer conId) {
		String SQL = "INSERT INTO " + this.getTableName() + " (conId, userDesc, description) " +
					 "VALUES (?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, conId, m.getUser().getId(), m.getSummary());
	}
	
}
