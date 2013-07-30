package com.haystack.dataAccess;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.haystack.entities.Participant;

@Repository
public class ParticipantJDBCTemplate extends HaystackDAO<Participant> {
	
	public ParticipantJDBCTemplate() {
		this.setTableName("participants");
		this.setRowMapper(new ParticipantMapper());
	}
	
	public void updateGender(Integer id, String gender) {
		this.update(id, "gender", gender);
	}
	
	public void updateManufacturer(Integer id, Integer minAge) {
		this.update(id, "minAge", minAge);
	}
	
	public void updateModel(Integer id, Integer maxAge) {
		this.update(id, "maxAge", maxAge);
	}
	
	public void updateSize(Integer id, Integer height) {
		this.update(id, "height", height);
	}
	
	public List<Participant> getByMeetingId(Integer meetingId) {
		return this.getByOwnerId("meetingId", meetingId);
	}
	
	@Override
	public void save(Participant p, Integer meetingId) {
		String SQL = "INSERT INTO " + this.getTableName() + " (meetingId, name, gender, minAge, maxAge, height, description) " +
					 "VALUES (?, ?, ?, ?, ?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, meetingId, p.getTitle(), p.getGender(), p.getMinAge(), p.getMaxAge(), 
									   p.getHeight(), p.getSummary());
	}
	
	public Integer saveAndReturnKey(Participant p, Integer meetingId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("meetingId", meetingId);
		parameters.addValue("name", p.getTitle());
		parameters.addValue("gender", p.getGender());
		parameters.addValue("minAge", p.getMinAge());
		parameters.addValue("maxAge", p.getMaxAge());
		parameters.addValue("height", p.getHeight());
		parameters.addValue("description", p.getSummary());
		Number longKey = this.jdbcInsert.executeAndReturnKey(parameters);
		return longKey.intValue();
	}
	
}
