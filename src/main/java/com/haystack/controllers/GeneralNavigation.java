package com.haystack.controllers;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GeneralNavigation {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView homePage() {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		String formattedDate = dateFormat.format(date);
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("serverTime", formattedDate );
		return modelAndView;
	}
	
	@RequestMapping(value="/findSomething", method=RequestMethod.GET)
	public ModelAndView findItemPage() {
		return new ModelAndView("item-form");
	}
	
	@RequestMapping(value="/Searches/Post*", method=RequestMethod.GET)
	public ModelAndView postsPage() {
		return new ModelAndView("post");
	}
	
	@RequestMapping(value="/account", method=RequestMethod.GET)
	public ModelAndView accountPage() {
		return new ModelAndView("account");
	}
	
	@RequestMapping(value="/Inbox", method=RequestMethod.GET)
	public ModelAndView inboxPage() {
		return new ModelAndView("inbox");
	}
	
	@RequestMapping(value="/inbox/message*", method=RequestMethod.GET)
	public ModelAndView messagePage() {
		return new ModelAndView("message");
	}

}
