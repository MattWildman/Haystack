package com.haystack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.haystack.dataAccess.UserJDBCTemplate;
import com.haystack.entities.Registration;
import com.haystack.entities.User;
import com.haystack.validation.RegistrationValidation;

@Controller
@RequestMapping("/Register")
public class RegistrationController {

	@Autowired
	private RegistrationValidation registrationValidation;

	public void setRegistrationValidation(RegistrationValidation registrationValidation) {
		this.registrationValidation = registrationValidation;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showRegistration() {
		Registration registration = new Registration();
		ModelAndView modelAndView = GeneralNavigation.renderPage("Register",
																 "register");
		modelAndView.addObject("registration", registration);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processRegistration(Registration registration,
											BindingResult result) {
		registrationValidation.validate(registration, result);
		if (result.hasErrors()) {
			return GeneralNavigation.renderPage("Register", "register");
		}
		User user = new User();
		user.setUsername(registration.getUsername());
		user.setPassword(registration.getPassword());
		user.setEmail(registration.getEmail());
		UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
		userJDBCTemplate.save(user, 1);
		return GeneralNavigation.renderPage("Registration success!",
											"registration-success");
	}

}