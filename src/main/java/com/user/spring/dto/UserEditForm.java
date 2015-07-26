package com.user.spring.dto;

import java.util.Set;

import javax.validation.constraints.Size;

import com.user.spring.entity.User;
import com.user.spring.entity.User.Role;

public class UserEditForm {
	@Size(min = 1, max = User.NAMEMAX)
	private String name;
     
	private Set<Role> roles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
