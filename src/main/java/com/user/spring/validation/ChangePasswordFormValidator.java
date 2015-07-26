package com.user.spring.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.user.spring.dto.ChangePasswordForm;

@Component
public class ChangePasswordFormValidator extends LocalValidatorFactoryBean {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(ChangePasswordForm.class);
	}

	@Override
	public void validate(Object obj, Errors errors,
			final Object... validationHint) {
		super.validate(obj, errors, validationHint);

		if (!errors.hasErrors()) {
			ChangePasswordForm changePasswordForm = (ChangePasswordForm) obj;
			if (!changePasswordForm.getPassword().equals(changePasswordForm.getRetypePassword())) {
				errors.reject("passwordonotmactch");
				
			}
			
		}

	}

}
