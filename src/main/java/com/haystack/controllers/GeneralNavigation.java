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
	
	@RequestMapping(value="/post", method=RequestMethod.GET)
	public ModelAndView postPage() {
		return new ModelAndView("post");
	}

}
