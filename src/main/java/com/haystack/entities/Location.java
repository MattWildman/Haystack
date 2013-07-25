package com.haystack.entities;

public class Location extends HaystackEntity implements Where {
	
	private String area;
	private String postcode;
	private String country;
	private Float lat;
	private Float longd;
	private Integer rad;
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public Float getLat() {
		return lat;
	}
	
	public void setLat(Float lat) {
		this.lat = lat;
	}
	
	public Float getLongd() {
		return longd;
	}
	
	public void setLongd(Float longd) {
		this.longd = longd;
	}
	
	public Integer getRad() {
		return rad;
	}
	
	public void setRad(Integer rad) {
		this.rad = rad;
	}

}
