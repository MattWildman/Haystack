package com.haystack.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.haystack.entities.Registration;
import com.haystack.entities.User;

@Component("accountValidator")
public class AccountValidation implements Validator {

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Registration.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Registration registration = (Registration) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
				"NotEmpty.account.username",
				"User name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"NotEmpty.account.password",
				"Password is required.");
		
		String password = registration.getPassword();
		
		if(!password.equals(user.getPassword())) {
			errors.rejectValue("password",
					"validPassword.account.password",
					"This is not your current password.");
		}
		
		String newPassword = registration.getNewPassword();
		String confirmPassword = registration.getConfirmPassword();
		
		if(!newPassword.isEmpty()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
					"NotEmpty.account.confirmPassword",
					"Please confirm your new password.");
			if (!(registration.getNewPassword()).equals(registration.getConfirmPassword())) {
				errors.rejectValue("newPassword",
						"matchingPassword.account.newPassword",
						"New password and confirm password do not match.");
			}
		}
		else if (!confirmPassword.isEmpty()) {
			errors.rejectValue("confirmPassword",
					"Empty.account.confirmPassword",
					"This field should be blank.");
		}
		
		String username = registration.getUsername();
		
		if ((username.length()) > 50) {
			errors.rejectValue("username",
					"lengthOfUser.account.username",
					"User name must not more than 50 characters.");
		}
		
	}

}
