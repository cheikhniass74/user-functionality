package com.user.spring.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.user.spring.entity.User;

public class ForgotPasswordForm {
	@NotNull()
	@Size(min = 4, max = User.EMAILMAX)
	@Pattern(regexp = User.EMAILPATTERN,message ="{emaiPatternError}")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
