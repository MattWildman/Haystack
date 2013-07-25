package com.haystack.entities;

import java.awt.Image;

import lombok.Data;

@Data public class Item extends HaystackEntity {

	private Boolean lost;
	private String manufacturer;
	private String model;
	private Integer size;
	private Integer weight;
	private String imageType;
	
	private Image image;

	public Boolean getLost() {
		return lost;
	}

	public void setLost(Boolean lost) {
		this.lost = lost;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
}
