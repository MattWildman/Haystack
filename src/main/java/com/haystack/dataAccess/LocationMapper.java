package com.haystack.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.haystack.entities.Location;

@Service
public class LocationMapper implements RowMapper<Location> {
	
   public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
	  Location location = new Location();
      location.setId(rs.getInt("id"));
      location.setTitle(rs.getString("title"));
      location.setSummary(rs.getString("description"));
      location.setArea(rs.getString("area"));
      location.setPostcode(rs.getString("postcode"));
      location.setCountry(rs.getString("country"));
      location.setLat(rs.getFloat("lat"));
      location.setLongd(rs.getFloat("longd"));
      location.setRad(rs.getInt("rad"));
      return location;
   }

}
