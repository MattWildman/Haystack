package com.haystack.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haystack.dataAccess.UserJDBCTemplate;
import com.haystack.entities.Registration;
import com.haystack.entities.User;
import com.haystack.validation.RegistrationValidation;

@Controller
@RequestMapping("/register")
public class RegistrationController {
        
	@Autowired
    private RegistrationValidation registrationValidation;

    public void setRegistrationValidation(RegistrationValidation registrationValidation) {
            this.registrationValidation = registrationValidation;
    }

    // Display the form on the get request
    @RequestMapping(method = RequestMethod.GET)
    public String showRegistration(Map<String, Registration> model) {
            Registration registration = new Registration();
            model.put("registration", registration);
            return "register";
    }

    // Process the form.
    @RequestMapping(method = RequestMethod.POST)
    public String processRegistration(Registration registration,
                    BindingResult result) {
            // set custom Validation by user
            registrationValidation.validate(registration, result);
            if (result.hasErrors()) {
                    return "register";
            }
            User user = new User();
            user.setUsername(registration.getUsername());
            user.setPassword(registration.getPassword());
            user.setEmail(registration.getEmail()); 
            UserJDBCTemplate userJDBCTemplate = new UserJDBCTemplate();
            userJDBCTemplate.save(user, 1);
            return "registration-success";
    }
        
}