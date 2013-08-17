package com.haystack;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import com.haystack.dataAccess.HaystackDBFacade;
import com.haystack.entities.Context;
import com.haystack.entities.Meeting;

public class DateTimeTest {

	DateTimeFormatter format;
	DateTime dt;
	HaystackDBFacade hdbf;
	Context ctx;
	
	@Before
	public void setUp() {
		format = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		hdbf = new HaystackDBFacade();
		ctx = new Context();
	}
	
	@Test
	public void dateTimeTest() {
		dt = format.parseDateTime("01/01/1900 17:20");
		assertEquals("Date strings don't match", "01/01/1900 17:20", dt.toString("dd/MM/yyyy HH:mm"));
	}
	
	@Test
	public void formattingTest() {
		Meeting meeting = hdbf.getMeeting(21);
		ctx = meeting.getContexts().get(0);
		Date e = ctx.getEarliest();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy 'at' HH:mm");
		String estring = sdf.format(e);
		assertEquals("Date strings don't match", "Sun 2 Jun 2013 at 10:30", estring);
	}
	
	@Test
	public void jspDisplayTest() {
		DateTime edt = new DateTime();
		DateTime ldt = new DateTime();
		ctx.setEarliest(edt.toDate());
		ctx.setLatest(ldt.toDate());
		if (edt.equals(ldt)) {
			ctx.setEarliestString(edt.toString("'On' EEE, d MMM yyyy 'between' HH:mm"));
			ctx.setLatestString(ldt.toString("HH:mm"));
		}
		else {
			ctx.setEarliestString(edt.toString("'Between' EEE, d MMM yyyy 'at' HH:mm"));
			ctx.setLatestString(ldt.toString("EEE, d MMM yyyy 'at' HH:mm"));
		}
		System.out.println(ctx.getEarliestString() + " and " + ctx.getLatestString());
	}

}
