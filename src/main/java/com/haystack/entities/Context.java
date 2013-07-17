package com.haystack.entities;

import java.sql.Date;

import lombok.Data;

@Data public class Context extends HaystackEntity{
	
	private Date earliest;
	private Date latest;
	private String locationType;
	
	private Where where;
	
}
