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
	
}
