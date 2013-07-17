package com.haystack.entities;

import lombok.Data;

@Data public class Participant extends HaystackEntity {
	
	private String gender;
	private Integer minAge;
	private Integer maxAge;
	private Integer height;

}
