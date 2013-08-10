package com.haystack;

import static org.junit.Assert.*;

import org.junit.Test;

import com.haystack.dataAccess.HaystackDBFacade;
import com.haystack.entities.Meeting;

public class MeetingTest {

	@Test
	public void test() {
		HaystackDBFacade haystackDAO = new HaystackDBFacade();
		Meeting meeting = haystackDAO.getMeeting(16);
		assertEquals("Title mismatch", meeting.getTitle(), "test meeting");
	}

}
