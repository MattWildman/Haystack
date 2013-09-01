package com.haystack.dataAccess;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public final class HaystackDataSource extends DriverManagerDataSource {
	
	public HaystackDataSource() {
		super();
		this.setDriverClassName("com.mysql.jdbc.Driver");
		this.setUrl("jdbc:mysql://localhost:3306/haystack");
		this.setUsername("admin");
		this.setPassword("test");
	}
}
