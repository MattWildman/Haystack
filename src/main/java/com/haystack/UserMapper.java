package com.haystack;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper implements RowMapper<User> {
	
   public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User user = new User();
      user.setUid(rs.getInt("id"));
      user.setFirstName(rs.getString("name"));
      user.setLastName(rs.getString("name"));
      user.setEmail(rs.getString("name"));
      return user;
   }
   
}
