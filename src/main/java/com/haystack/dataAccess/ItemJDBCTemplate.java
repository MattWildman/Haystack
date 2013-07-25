package com.haystack.dataAccess;

import java.awt.Image;

import org.springframework.stereotype.Repository;

import com.haystack.entities.Item;

@Repository
public class ItemJDBCTemplate extends HaystackDAO<Item> {
	
	public ItemJDBCTemplate() {
		this.setTableName("items");
		this.setRowMapper(new ItemMapper());
	}
	
	public void updateStatus(Integer id, Boolean lost) {
		String status = lost ? "lost" : "found";
		this.update(id, "status", status);
	}
	
	public void updateManufacturer(Integer id, String manufacturer) {
		this.update(id, "manufacturer", manufacturer);
	}
	
	public void updateModel(Integer id, String model) {
		this.update(id, "model", model);
	}
	
	public void updateSize(Integer id, Integer size) {
		this.update(id, "size", size);
	}
	
	public void updateWeight(Integer id, Integer weight) {
		this.update(id, "weight", weight);
	}
	
	public void updateImageType(Integer id, String imageType) {
		this.update(id, "imageType", imageType);
	}
	
	public void uploadImage(Integer id, Image image) {
		//TODO convert image to longblob
	}
	
	@Override
	public void save(Item i, Integer conId) {
		String status = i.getLost() ? "lost" : "found";
		String SQL = "INSERT INTO " + this.getTableName() + " (conId, status, name, description, manufacturer, model, size, weight) " +
					 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		this.jdbcTemplateObject.update(SQL, conId, status, i.getTitle(), i.getSummary(), i.getManufacturer(), i.getModel(), 
									   i.getSize(), i.getWeight());
	}
	
}
