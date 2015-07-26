package com.user.spring.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.user.spring.dto.ForgotPasswordForm;
import com.user.spring.entity.User;
import com.user.spring.repo.UserRepository;

@Component
public class ForgotPasswordFormValidator extends LocalValidatorFactoryBean {

	private UserRepository userRepository;

	@Autowired
	public ForgotPasswordFormValidator(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(ForgotPasswordForm.class);
	}

	public void validate(Object obj, Errors errors,
			final Object... validationHint) {
		super.validate(obj, errors, validationHint);

		if (!errors.hasErrors()) {

			ForgotPasswordForm forgotPasswordForm = (ForgotPasswordForm) obj;
			User user = userRepository
					.findByEmail(forgotPasswordForm.getEmail());
			if (user == null) {
				errors.rejectValue("email", "notfound");

			}
		}

	}

}
