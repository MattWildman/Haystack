package com.haystack.entities;

import lombok.Data;

@Data public class Location extends HaystackEntity implements Where {
	
	private String area;
	private String postcode;
	private String country;
	private Float lat;
	private Float longd;
	private Integer rad;

}
