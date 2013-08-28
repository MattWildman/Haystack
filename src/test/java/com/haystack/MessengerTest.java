package com.haystack;

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
	
	/*@Test
	public void sendSystemMessages() {
		HaystackMessenger.getInstance().sendMatchMessage(3, testMeeting);
		HaystackMessenger.getInstance().sendSharedConnectionMessage(3, testMeeting, testUser);
	}
	
	@Test
	public void sendUserMessages() {
		HaystackMessenger.getInstance().sendUserMessage(13, 14, "Hello! This is my message", "First message");
		HaystackMessenger.getInstance().sendUserMessage(14, 13, "Hello! This is my reply", "RE: First message");
		HaystackMessenger.getInstance().sendUserMessage(13, 14, "Message body message body blah blah", "Second message");
	}*/
	
	@Test
	public void getThreadData() {
		List<MessageThread> threads = HaystackMessenger.getInstance().getMessageThreads(6);
		for (MessageThread t : threads) {
			System.out.println(t.getUser().getUsername());
			System.out.println(t.getLastMessage().getTitle());
		}
		
	}

}
