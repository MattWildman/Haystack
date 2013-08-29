package com.haystack.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.haystack.dataAccess.HaystackMessenger;
import com.haystack.dataAccess.UserJDBCTemplate;
import com.haystack.entities.Message;
import com.haystack.entities.MessageThread;
import com.haystack.entities.User;
import com.haystack.validation.MessageValidation;

@Controller
public class MessagesController {
	
	@Autowired
    private MessageValidation messageValidation;
	
	public void setMeetingSearchValidation(MessageValidation messageValidation) {
		this.messageValidation = messageValidation;
	}
	
	@RequestMapping(value="/Inbox", method=RequestMethod.GET)
	public ModelAndView showInbox() {
		
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		
		List<MessageThread> messageThreads = HaystackMessenger.getInstance()
											 .getMessageThreads(loggedInId);
		
		ModelAndView modelAndView = GeneralNavigation.renderPage("Inbox", "inbox");
		modelAndView.addObject("messageThreads", messageThreads);
		return modelAndView;
	}
	
	@RequestMapping(value="/Inbox/{id}", method=RequestMethod.GET)
	public ModelAndView showThread(@PathVariable Integer id) {
		
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		
		if (!HaystackMessenger.getInstance().hasPermission(loggedInId, id)) {
			return GeneralNavigation.renderPage("Not authorised", "not-authorised");
		}
		
		HaystackMessenger.getInstance().markThreadAsRead(loggedInId, id);
		
		UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
		User user = userJDBCTemplate.getById(id);
		List<Message> messages = HaystackMessenger.getInstance()
								 .getMessagesInThread(loggedInId, id);
		ModelAndView modelAndView = GeneralNavigation.renderPage("Messages from " + user.getUsername(), 
																 "message-thread");
		if (id != 1) {
			Message newMessage = new Message();
			modelAndView.addObject("newMessage", newMessage);
		}
		
		modelAndView.addObject("senderId", loggedInId);
		modelAndView.addObject("user", user);
		modelAndView.addObject("messages", messages);
		return modelAndView;
		
	}
	
	@RequestMapping(value="/Inbox/{id}", method=RequestMethod.POST)
	public ModelAndView sendAndDisplayMessage(@PathVariable Integer id, 
											  Message message, BindingResult result) {
		
		messageValidation.validate(message, result);
		if (result.hasErrors()) {
			return this.showThread(id);
		}
		
		Integer loggedInId = SecurityNavigation.getLoggedInUserId();
		
		if (!HaystackMessenger.getInstance().hasPermission(loggedInId, id)) {
			return GeneralNavigation.renderPage("Not authorised", "not-authorised");
		}
		
		HaystackMessenger.getInstance().sendUserMessage(message);
		
		return this.showThread(id);
		
	}

}
