package com.haystack.dataAccess;

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
	
	@Override
	public void save(Journey j, Integer ctxId) {
		String SQL = "INSERT INTO " + this.getTableName() + " (ctxId, startId, endId, type, company, service, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, ctxId, j.getStart().getId(), j.getStart().getId(), 
									   j.getType(), j.getCompany(), j.getService(), j.getSummary());
	}
	
}
