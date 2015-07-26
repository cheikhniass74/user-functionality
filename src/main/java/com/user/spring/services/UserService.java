package com.user.spring.services;

import org.springframework.validation.BindingResult;

import com.user.spring.dto.ChangeEmailForm;
import com.user.spring.dto.ChangePasswordForm;
import com.user.spring.dto.ForgotPasswordForm;
import com.user.spring.dto.ResetPasswordForm;
import com.user.spring.dto.SignupForm;
import com.user.spring.dto.UserEditForm;
import com.user.spring.entity.User;

public interface UserService {
	public abstract void signup(SignupForm signupForm);

	public abstract void verify(String verifcationCode);

	public abstract void resendVerifcationEmail();

	public abstract void forgotPassword(ForgotPasswordForm forgotPasswordForm);

	public abstract void resetPassword(String forgotPasswordCode,
			ResetPasswordForm resetPasswordForm, BindingResult result);

	public abstract User findOne(long id);

	public abstract void updateProfle(long id, UserEditForm userEditForm);

	public abstract void changePassword(long id,
			ChangePasswordForm changePasswordForm);

	public abstract void changeEmail(long id, ChangeEmailForm changeEmailForm);

	public abstract void deleteProfile(long userId);

}
