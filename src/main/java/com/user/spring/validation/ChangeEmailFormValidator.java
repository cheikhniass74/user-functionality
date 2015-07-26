package com.user.spring.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.user.spring.dto.ChangeEmailForm;
import com.user.spring.entity.User;
import com.user.spring.repo.UserRepository;

@Component
public class ChangeEmailFormValidator extends LocalValidatorFactoryBean {
	@Autowired
	private UserRepository repository;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(ChangeEmailForm.class);
	}

	@Override
	public void validate(Object obj, Errors errors,
			final Object... validationHint) {
		super.validate(obj, errors, validationHint);

		if (!errors.hasErrors()) {
			ChangeEmailForm changeEmailForm = (ChangeEmailForm) obj;

			if (!changeEmailForm.getEmail().equals(
					changeEmailForm.getRetypeEmail())) {
				errors.reject("emaildonotmatch");

			}
			User userEmail = repository.findByEmail(changeEmailForm.getEmail());
			if (userEmail != null) {
				errors.reject("emailNotUnique");
				
			}
			

		}

	}

}
