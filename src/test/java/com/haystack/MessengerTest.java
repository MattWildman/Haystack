package com.haystack;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.haystack.dataAccess.HaystackDBFacade;
import com.haystack.dataAccess.HaystackMessenger;
import com.haystack.dataAccess.UserJDBCTemplate;
import com.haystack.entities.Meeting;
import com.haystack.entities.MessageThread;
import com.haystack.entities.User;

public class MessengerTest {

	Meeting testMeeting;
	User testUser;
	HaystackDBFacade hdbf = new HaystackDBFacade();
	UserJDBCTemplate userDAO = new UserJDBCTemplate();
	
	@Before
	public void setUp() {
		testMeeting = hdbf.getMeeting(22);
		testUser = userDAO.getById(4);
	}
	
	@Test
	public void sendSystemMessages() throws InterruptedException {
		HaystackMessenger.getInstance().sendMatchMessage(3, testMeeting);
		HaystackMessenger.getInstance().sendSharedConnectionMessage(3, testMeeting, testUser);
	}
	
	@Test
	public void getThreadData() {
		List<MessageThread> threads = HaystackMessenger.getInstance().getMessageThreads(3);
		assertEquals("Thread number is incorrect", 1, threads.size());
	}
	
	@Test
	public void testPermissions() {
		assertTrue(HaystackMessenger.getInstance().hasPermission(4, 6));
		assertFalse(HaystackMessenger.getInstance().hasPermission(4, 5));
	}

}
