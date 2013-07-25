package com.haystack.dataAccess;

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
	
	@Override
	public void save(Location l, Integer ctxId) {
		String SQL = "INSERT INTO " + this.getTableName() + " (ctxId, name, area, postcode, country, description, lat, longd, rad)" +
					 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, ctxId, l.getTitle(), l.getArea(), l.getPostcode(), l.getCountry(), 
									   l.getSummary(), l.getLat(), l.getLongd(), l.getRad());
	}
	
}
