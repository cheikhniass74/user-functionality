package com.user.spring.util;

import java.util.Locale;

import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.user.spring.dto.UserDetailsImpl;
import com.user.spring.entity.User;

@Component
public class MyUtil {

	private static MessageSource messageSource;

	@Autowired
	public MyUtil(MessageSource messageSource) {
		MyUtil.messageSource = messageSource;

	}

	public static void flah(RedirectAttributes redirectAttributes, String kind,
			String messagekey) {
		redirectAttributes.addFlashAttribute("flashKind", kind);
		redirectAttributes.addFlashAttribute("flashMessage",
				MyUtil.getMessage(messagekey));
	}

	public static String getMessage(String messageKey, Object... args) {
		return messageSource.getMessage(messageKey, args, Locale.getDefault());
	}

	private static String hosAndPort;

	public static String getHosAndPort() {
		return (isDev() ? "http://" : "https://") + hosAndPort;
	}

	@Value("${hosAndPort}")
	public void setHosAndPort(String hosAndPort) {
		MyUtil.hosAndPort = hosAndPort;
	}

	private static String activeProfiles;
	

	@Value("${spring.profiles.active}")
	public void setActiveProfiles(String activeProfiles) {
		MyUtil.activeProfiles = activeProfiles;
	}
	
	public static boolean isDev() {
		return activeProfiles.equals("dev");
	}
	
	public static User getSessionUser(){
		UserDetailsImpl curDetails = getAuthenticationUserDetails();
		return  curDetails == null ? null: curDetails.getUser();
	
	}

	public static UserDetailsImpl getAuthenticationUserDetails() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return (UserDetailsImpl) principal;
		} else {
			return null;
		}
	}
	
	public static void validate(boolean valid, String messageContent,Object... args){
		if (!valid){
		 throw new RuntimeException(MyUtil.getMessage(messageContent, args));
		}
	}

}
