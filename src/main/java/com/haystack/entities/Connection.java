package com.haystack.entities;

import lombok.Data;

@Data public abstract class Connection extends HaystackEntity {
	
	private String connectionType;
	private String status;
	
	private Context[] context;
	
}
