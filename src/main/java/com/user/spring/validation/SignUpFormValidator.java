package com.user.spring.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.user.spring.dto.SignupForm;
import com.user.spring.entity.User;
import com.user.spring.repo.UserRepository;

@Component
public class SignUpFormValidator extends LocalValidatorFactoryBean {

	private UserRepository userRepository;

	@Autowired
	public SignUpFormValidator(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(SignupForm.class);
	}

	public void validate(Object obj, Errors errors,
			final Object... validationHint) {
		super.validate(obj, errors, validationHint);

		if (!errors.hasErrors()) {

			SignupForm signupForm = (SignupForm) obj;
			User user = userRepository
					.findByEmail(signupForm.getEmailAddress());
			if (user != null) {
				errors.rejectValue("emailAddress", "emailNotUnique");

			}
		}

	}

}
