package com.haystack.entities;

import lombok.Data;

@Data public abstract class HaystackEntity {
	
	private Integer id;
	private String title;
	private String summary;

}
