package com.haystack.dataAccess;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.haystack.entities.Journey;

@Repository
public class JourneyJDBCTemplate extends HaystackDAO<Journey> {
	
	public JourneyJDBCTemplate() {
		this.setTableName("journeys");
		this.setRowMapper(new JourneyMapper());
	}
	
	public void updateType(Integer id, String type) {
		this.update(id, "type", type);
	}
	
	public void updateCompany(Integer id, String company) {
		this.update(id, "company", company);
	}
	
	public void updateService(Integer id, String service) {
		this.update(id, "service", service);
	}
	
	public void updateStartId(Integer id, Integer startId) {
		this.update(id, "startId", startId);
	}
	
	public void updateEndId(Integer id, Integer endId) {
		this.update(id, "endId", endId);
	}
	
	@Override
	public void save(Journey j, Integer ctxId) {
		String SQL = "INSERT INTO " + this.getTableName() + " (ctxId, type, company, service, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, ctxId, j.getType(), j.getCompany(), j.getService(), j.getSummary());
	}
	
	public Integer saveAndReturnKey(Journey j, Integer ctxId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ctxId", ctxId);
		parameters.addValue("type", j.getType());
		parameters.addValue("company", j.getCompany());
		parameters.addValue("service", j.getService());
		parameters.addValue("description", j.getSummary());
		Number longKey = this.jdbcInsert.executeAndReturnKey(parameters);
		return longKey.intValue();
	}
	
}
