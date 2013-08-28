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
	
	@Test
	public void sendSystemMessages() throws InterruptedException {
		HaystackMessenger.getInstance().sendMatchMessage(3, testMeeting);
		Thread.sleep(2000);
		HaystackMessenger.getInstance().sendSharedConnectionMessage(3, testMeeting, testUser);
		Thread.sleep(2000);
	}
	
	@Test
	public void sendUserMessages() throws InterruptedException {
		HaystackMessenger.getInstance().sendUserMessage(13, 14, "Hello! This is my message", "First message");
		Thread.sleep(2000);
		HaystackMessenger.getInstance().sendUserMessage(14, 13, "Hello! This is my reply", "RE: First message");
		Thread.sleep(2000);
		HaystackMessenger.getInstance().sendUserMessage(13, 14, "Message body message body blah blah", "Second message");
		Thread.sleep(2000);
	}
	
	@Test
	public void getThreadData() {
		List<MessageThread> threads = HaystackMessenger.getInstance().getMessageThreads(3);
		for (MessageThread t : threads) {
			System.out.println(t.getUser().getUsername());
			System.out.println(t.getLastMessage().getTitle());
		}
		
	}

}
