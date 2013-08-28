package com.haystack.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.haystack.dataAccess.HaystackMessenger;
import com.haystack.dataAccess.UserJDBCTemplate;
import com.haystack.entities.Message;
import com.haystack.entities.MessageThread;
import com.haystack.entities.User;

@Controller
public class MessagesController {
	
	@RequestMapping(value="/Inbox", method=RequestMethod.GET)
	public ModelAndView showInbox() {
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		List<MessageThread> messageThreads = HaystackMessenger.getInstance().getMessageThreads(loggedInId);
		ModelAndView modelAndView = GeneralNavigation.renderPage("Inbox", "inbox");
		modelAndView.addObject("messageThreads", messageThreads);
		return modelAndView;
	}
	
	@RequestMapping(value="/Inbox/{id}", method=RequestMethod.GET)
	public ModelAndView showThread(@PathVariable Integer id) {
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
		User user = userJDBCTemplate.getById(id);
		List<Message> messages = HaystackMessenger.getInstance().getMessagesInThread(loggedInId, id);
		ModelAndView modelAndView = GeneralNavigation.renderPage("Messages from " + user.getUsername(), 
																 "message-thread");
		modelAndView.addObject("user", user);
		modelAndView.addObject("messages", messages);
		return modelAndView;
	}

}
