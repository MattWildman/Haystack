package com.haystack.entities;

import java.sql.Date;

import lombok.Data;

@Data public class Context extends HaystackEntity{
	
	private Date earliest;
	private Date latest;
	private String locationType;
	
	private Where where;

	public Date getEarliest() {
		return earliest;
	}

	public void setEarliest(Date earliest) {
		this.earliest = earliest;
	}

	public Date getLatest() {
		return latest;
	}

	public void setLatest(Date latest) {
		this.latest = latest;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public Where getWhere() {
		return where;
	}

	public void setWhere(Where where) {
		this.where = where;
	}
	
}
