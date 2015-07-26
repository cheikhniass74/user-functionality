package com.user.spring.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;


import com.user.spring.util.MyUtil;

@Entity
@Table(name = "usr", indexes={@Index(columnList = "email", unique = true),
		@Index(columnList = "forgotPasswordVerificationCode", unique = true),
		@Index(columnList = "verificationCode", unique = true)})
public class User {

	public static final int EMAILMAX = 255;
	public static final int NAMEMAX = 100;
	public static final int VERIFICATIONCODE= 16;
	public static final String EMAILPATTERN =  "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
	public static final int PASSWORDMAX = 30;
	
	public static enum Role {
		UNVERIFIED, BLOCKED,ADMIN
		
	}
	@ElementCollection(fetch= FetchType.EAGER)
	private Set<Role>roles = new HashSet<Role>();
 
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, length = EMAILMAX)
	private String email;

	@Column(nullable = false, length = NAMEMAX)
	private String name;
	
	@Column(nullable = false)
	private String pasword;
	
	@Column(length = VERIFICATIONCODE)
	private  String verificationCode;
    
	public String getForgotPasswordVerificationCode() {
		return forgotPasswordVerificationCode;
	}

	public void setForgotPasswordVerificationCode(
			String forgotPasswordVerificationCode) {
		this.forgotPasswordVerificationCode = forgotPasswordVerificationCode;
	}

	@Column(length = VERIFICATIONCODE)
	private  String forgotPasswordVerificationCode;
	
	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasword() {
		return pasword;
	}

	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

	public boolean isAdmin() {
		if (roles.contains(Role.ADMIN)) {
			return true;
		}
		return false;
	}
	
	public boolean isEditable(){
		User loggedInUser = MyUtil.getSessionUser();
		if(loggedInUser == null){
			return false;
		}
		return loggedInUser.getId() == id || isAdmin();
		
	}
			
}
