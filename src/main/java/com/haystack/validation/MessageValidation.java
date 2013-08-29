package com.haystack.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.haystack.entities.Message;

@Component("messageValidator")
public class MessageValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Message.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		@SuppressWarnings("unused")
		Message message = (Message) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "summary",
				  "NotEmpty.message.summary", "You can't send a blank message!");
		
	}

}
