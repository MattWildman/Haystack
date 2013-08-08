package com.haystack;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

public class DateTimeTest {

	DateTimeFormatter format;
	DateTime dt;
	
	@Before
	public void setUp() {
		format = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");	
	}
	
	@Test
	public void test() {
		dt = format.parseDateTime("01/01/1900 17:20");
		assertEquals("Date strings don't match", "01/01/1900 17:20", dt.toString("dd/MM/yyyy HH:mm"));
	}

}
