package com.user.spring.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.user.spring.entity.User;

public class SignupForm {
	@NotNull
	@Size(min = 1, max = User.NAMEMAX, message="{nameSizeError}")
	private String name;
	@NotNull
	@Size(min = 4, max = User.PASSWORDMAX, message="{passwordSizeError}")
	private String password;
	@NotNull
	@Size(min = 1, max = User.EMAILMAX, message="{emailSizeError}")
	@Pattern(regexp = User.EMAILPATTERN,message ="{emaiPatternError}")
	private String emailAddress;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public String toString() {
		return "SignupForm [name=" + name + ", password=" + password
				+ ", emailAddress=" + emailAddress + "]";
	}

}
