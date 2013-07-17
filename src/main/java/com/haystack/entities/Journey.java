package com.haystack.entities;

import lombok.Data;

@Data public class Journey extends HaystackEntity implements Where {

	private String type;
	private String company;
	private String service;
	
	private Location start;
	private Location end;
	
}
