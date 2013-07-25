package com.haystack.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.haystack.entities.Item;

@Service
public class ItemMapper implements RowMapper<Item> {
	
	private Boolean getItemStatus(String status) {
		return status == "lost";
	}
	
	public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setTitle(rs.getString("title"));
		item.setSummary(rs.getString("description"));
		item.setLost(getItemStatus(rs.getString("status")));
		item.setManufacturer(rs.getString("manufacturer"));
		item.setModel(rs.getString("model"));
		item.setSize(rs.getInt("size"));
		item.setWeight(rs.getInt("weight"));
		item.setImageType(rs.getString("imageType"));
		//TODO: item.setImage(rs.getBlob("picture"));
		return item;
	}

}
