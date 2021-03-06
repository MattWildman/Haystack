package com.haystack.dataAccess;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.haystack.entities.Connection;

@Repository
public class ConnectionJDBCTemplate extends HaystackDAO<Connection> {

	public ConnectionJDBCTemplate() {
		this.setTableName("connections");
		this.setRowMapper(new ConnectionMapper());
	}
	
	public void updateConType(Integer id, String conType) {
		this.update(id, "conType", conType);
	}

	
	public void updateStatus(Integer id, String status) {
		this.update(id, "status", status);
	}
	
	public List<Connection> getUserConnections(Integer userId) {
		return this.getByOwnerId("userId", userId);
	}
	
	public Connection getByMeetingId(Integer meetingId) {
		String SQL = "SELECT * FROM connections WHERE id IN (SELECT m.conId FROM meetings m WHERE m.id = ?)";
		Connection result = jdbcTemplateObject.queryForObject(SQL, new Object[]{meetingId}, this.getRowMapper());
		return result;
	}
	
	public List<Connection> getUserMeetings(Integer userId) {
		String SQL = "SELECT * FROM connections WHERE conType = 'meeting' AND userId = ?";
 		List <Connection> results = jdbcTemplateObject.query(SQL, new Object[]{userId}, this.getRowMapper());
 		return results;
	}
	
	@Override
	public void save(Connection c, Integer userId) {
		String SQL = "INSERT INTO " + this.getTableName() + " (userId, title, summary, conType, status)" +
					 "VALUES (?, ?, ?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, userId, c.getTitle(), c.getSummary(), c.getConType(), c.getStatus());
	}
	
	public Integer saveAndReturnKey(Connection c, Integer userId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("userId", userId);
		parameters.addValue("title", c.getTitle());
		parameters.addValue("summary", c.getSummary());
		parameters.addValue("conType", c.getConType());
		parameters.addValue("status", c.getStatus());
		Number longKey = this.jdbcInsert.executeAndReturnKey(parameters);
		return longKey.intValue();
	}
	
}
