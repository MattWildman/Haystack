package com.haystack.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.haystack.entities.Registration;


@Component("registrationValidator")
public class RegistrationValidation {
	
	public boolean supports(Class<?> clazz) {
		
		return Registration.class.isAssignableFrom(clazz);
		
	}

	public void validate(Object target, Errors errors) {
		
		Registration registration = (Registration) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
				"NotEmpty.registration.username",
				"User name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"NotEmpty.registration.password",
				"Password is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"NotEmpty.registration.confirmPassword",
				"Please confirm your password.");
		
		String username = registration.getUsername();
		
		if ((username.length()) > 50) {
			errors.rejectValue("username",
					"lengthOfUser.registration.username",
					"User name must not more than 50 characters.");
		}
		
		if (!(registration.getPassword()).equals(registration.getConfirmPassword())) {
			errors.rejectValue("password",
					"matchingPassword.registration.password",
					"Password and confirm password do not match.");
		}
		
	}
	
}