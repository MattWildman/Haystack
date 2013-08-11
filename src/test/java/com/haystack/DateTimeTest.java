package com.haystack;

import static org.junit.Assert.*;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import com.haystack.entities.Context;

public class DateTimeTest {

	DateTimeFormatter format;
	DateTime dt;
	Context ctx = new Context();
	
	@Before
	public void setUp() {
		format = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
	}
	
	@Test
	public void dateTimeTest() {
		dt = format.parseDateTime("01/01/1900 17:20");
		assertEquals("Date strings don't match", "01/01/1900 17:20", dt.toString("dd/MM/yyyy HH:mm"));
	}
	
	@Test
	public void dateTest() {
		ctx.setEarliestString("01/03/1900 01:00");
		String estring = ctx.getEarliestString();
		DateTime edt = format.parseDateTime(estring);
		ctx.setEarliest(edt.toDate());
		Date d = ctx.getEarliest();
		System.out.println(d.toString());
	}

}
