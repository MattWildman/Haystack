package com.haystack.dataAccess;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.haystack.entities.Meeting;

@Repository
public class MeetingJDBCTemplate extends HaystackDAO<Meeting> {
	
	public MeetingJDBCTemplate() {
		this.setTableName("meetings");
		this.setRowMapper(new MeetingMapper());
	}
	
	public void updateUserId(Integer id, Integer userId) {
		this.update(id, "userDesc", userId);
	}
	
	@Override
	public void save(Meeting m, Integer conId) {
		String SQL = "INSERT INTO " + this.getTableName() + " (conId, userDesc) " +
					 "VALUES (?, ?)";
		this.jdbcTemplateObject.update(SQL, conId, m.getUser().getId());
	}
	
	public Integer saveAndReturnKey(Meeting m, Integer conId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("conId", conId);
		Number longKey = this.jdbcInsert.executeAndReturnKey(parameters);
		return longKey.intValue();
	}
	
}
