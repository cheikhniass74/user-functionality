package com.user.spring.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.user.spring.entity.User;

public class ChangeEmailForm {
	@NotNull
	@Size(min =1, max = User.EMAILMAX, message = "{emailSizeError}")
	@Pattern(regexp = User.EMAILPATTERN, message = "{emaiPatternError}")
	private String email;
	@NotNull
	@Size(min =1, max = User.EMAILMAX, message = "{emailSizeError}")
	@Pattern(regexp = User.EMAILPATTERN,   message = "{emaiPatternError}")
	private String retypeEmail;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRetypeEmail() {
		return retypeEmail;
	}
	public void setRetypeEmail(String retypeEmail) {
		this.retypeEmail = retypeEmail;
	}

}
