package com.haystack.dataAccess;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.haystack.entities.Location;

@Repository
public class LocationJDBCTemplate extends HaystackDAO<Location> {

	public LocationJDBCTemplate() {
		this.setTableName("locations");
		this.setRowMapper(new LocationMapper());
	}
	
	public void updateArea(Integer id, String area) {
		this.update(id, "area", area);
	}
	
	public void updatePostcode(Integer id, String postcode) {
		this.update(id, "postcode", postcode);
	}
	
	public void updateCountry(Integer id, String country) {
		this.update(id, "country", country);
	}
	
	public void updateLat(Integer id, Float lat) {
		this.update(id, "lat", lat);
	}
	
	public void updateLongd(Integer id, Float longd) {
		this.update(id, "longd", longd);
	}
	
	public void updateRad(Integer id, Integer rad) {
		this.update(id, "rad", rad);
	}
	
	public void updateJourneyId(Integer id, Integer journeyId) {
		this.update(id, "journeyId", journeyId);
	}
	
	@Override
	public void save(Location l, Integer ctxId) {
		String SQL = "INSERT INTO " + this.getTableName() + " (ctxId, name, area, postcode, country, description, lat, longd, rad)" +
					 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, ctxId, l.getTitle(), l.getArea(), l.getPostcode(), l.getCountry(), 
									   l.getSummary(), l.getLat(), l.getLongd(), l.getRad());
	}
	
	public Integer saveAndReturnKey(Location l, Integer ctxId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ctxId", ctxId);
		parameters.addValue("name", l.getTitle());
		parameters.addValue("area", l.getArea());
		parameters.addValue("postcode", l.getPostcode());
		parameters.addValue("country", l.getCountry());
		parameters.addValue("description", l.getSummary());
		parameters.addValue("lat", l.getLat());
		parameters.addValue("longd", l.getLongd());
		parameters.addValue("rad", l.getRad());
		Number longKey = this.jdbcInsert.executeAndReturnKey(parameters);
		return longKey.intValue();
	}
	
}
