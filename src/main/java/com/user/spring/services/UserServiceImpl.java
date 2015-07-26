package com.user.spring.services;

import javax.mail.MessagingException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.BindingResult;

import com.user.spring.dto.ChangeEmailForm;
import com.user.spring.dto.ChangePasswordForm;
import com.user.spring.dto.ForgotPasswordForm;
import com.user.spring.dto.ResetPasswordForm;
import com.user.spring.dto.SignupForm;
import com.user.spring.dto.UserDetailsImpl;
import com.user.spring.dto.UserEditForm;
import com.user.spring.entity.User;
import com.user.spring.entity.User.Role;
import com.user.spring.mail.MailSender;
import com.user.spring.mail.MockMailSender;
import com.user.spring.repo.UserRepository;
import com.user.spring.util.MyUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final Log LOG = LogFactory.getLog(MockMailSender.class);

	private UserRepository repository;
	private PasswordEncoder passwordEncoder;
	private MailSender sender;

	@Autowired
	public UserServiceImpl(UserRepository repository,
			PasswordEncoder passwordEncoder, MailSender sender) {
		super();
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.sender = sender;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void signup(SignupForm signupForm) {
		final User user = new User();
		user.setEmail(signupForm.getEmailAddress());
		user.setName(signupForm.getName());
		user.setPasword(passwordEncoder.encode(signupForm.getPassword()));
		user.getRoles().add(Role.UNVERIFIED);
		user.setVerificationCode(RandomStringUtils
				.randomAlphabetic(User.VERIFICATIONCODE));
		repository.save(user);

		TransactionSynchronizationManager
				.registerSynchronization(new TransactionSynchronizationAdapter() {

					@Override
					public void afterCommit() {
						String link = MyUtil.getHosAndPort() + "/users/"
								+ user.getVerificationCode() + "/verify";
						try {
							LOG.info("Sending email to" + user.getEmail());
							sender.send(user.getEmail(),
									MyUtil.getMessage("verifySubject"),
									MyUtil.getMessage("verifyBody", link));
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							LOG.error(ExceptionUtils.getStackTrace(e));

						}
					}
				});
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// check whether the user is in our database
		User user = repository.findByEmail(username);

		// if you fail to find it throw an exception
		if (user == null) {
			throw new UsernameNotFoundException(username);

		} else {
			return new UserDetailsImpl(user);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	//@Secured("ROLE_UNVERIFIED")
	//@Secured({"ROLE_UNVERIFIED", "ROLE_ADMIN}") for list of unverified role put tags
	public void verify(String verificationCode) {
		long userId = MyUtil.getSessionUser().getId();
		User user = repository.findOne(userId);
		MyUtil.validate(user.getRoles().contains(Role.UNVERIFIED),
				"alreadyVerified");
		MyUtil.validate(user.getVerificationCode().equals(verificationCode),
				"invalid", "verification code");

		user.getRoles().remove(Role.UNVERIFIED);
		user.setVerificationCode(null);
		repository.save(user);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void resendVerifcationEmail() {
		// get the logged in user email
		String loggedInUserEmail = MyUtil.getSessionUser().getEmail();
		final User user = repository.findByEmail(loggedInUserEmail);
		TransactionSynchronizationManager
				.registerSynchronization(new TransactionSynchronizationAdapter() {

					@Override
					public void afterCommit() {
						String link = MyUtil.getHosAndPort() + "/users/"
								+ user.getVerificationCode() + "/verify";
						try {
							LOG.info("Sending email to" + user.getEmail());
							sender.send(user.getEmail(),
									MyUtil.getMessage("verifySubject"),
									MyUtil.getMessage("verifyBody", link));
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							LOG.error(ExceptionUtils.getStackTrace(e));

						}
					}
				});

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void forgotPassword(ForgotPasswordForm forgotPasswordForm) {
		final User user = repository.findByEmail(forgotPasswordForm.getEmail());
		user.setForgotPasswordVerificationCode(RandomStringUtils
				.randomAlphabetic(User.VERIFICATIONCODE));
		repository.save(user);

		TransactionSynchronizationManager
				.registerSynchronization(new TransactionSynchronizationAdapter() {

					@Override
					public void afterCommit() {
						String link = MyUtil.getHosAndPort()
								+ "/reset-password/"
								+ user.getForgotPasswordVerificationCode();
						try {
							LOG.info("Sending email to" + user.getEmail());
							sender.send(user.getEmail(), MyUtil
									.getMessage("restPaswordSubject"), MyUtil
									.getMessage("resetPasswordBody", link));
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							LOG.error(ExceptionUtils.getStackTrace(e));

						}
					}
				});

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void resetPassword(String forgotPasswordCode,
			ResetPasswordForm resetPasswordForm, BindingResult result) {

		User user = repository
				.findByforgotPasswordVerificationCode(forgotPasswordCode);
		/*
		 * if (user == null){ result.reject("invalidforgotPasswordCode"); }
		 */

		MyUtil.validate(
				user.getForgotPasswordVerificationCode().equals(
						forgotPasswordCode), "invalidforgotPasswordCode",
				"forgot Password Code");

		/*
		 * if (result.hasErrors()) { return;
		 * 
		 * }
		 */
		user.setForgotPasswordVerificationCode(null);
		user.setPasword(passwordEncoder.encode(resetPasswordForm.getPassword()
				.trim()));
		repository.save(user);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public User findOne(long id) {
		User loggedInUser = MyUtil.getSessionUser();
		User user = repository.findOne(id);
		if (loggedInUser == null || loggedInUser.getId() != user.getId()
				&& !loggedInUser.isAdmin()) {
			user.setEmail("Confidential");

		}
		return user;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateProfle(long id, UserEditForm userEditForm) {
		User loggeInUser = MyUtil.getSessionUser();
		// MyUtil.validate(loggeInUser.isEditable(),"noPermission");
		MyUtil.validate(loggeInUser.isAdmin() || loggeInUser.getId() == id,
				"noPermission");
		User userbeingEdited = repository.findOne(id);
		userbeingEdited.setName(userEditForm.getName().trim());
		if (loggeInUser.isAdmin()) {
			userbeingEdited.setRoles(userEditForm.getRoles());

		}
		repository.save(userbeingEdited);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void changePassword(long id, ChangePasswordForm changePasswordForm) {
		User loggUser = MyUtil.getSessionUser();
		MyUtil.validate(!loggUser.equals(null) && loggUser.getId() == id || loggUser.isAdmin(),
				"noPermission");
		
		User user = repository.findOne(id);
		
		user.setPasword(passwordEncoder.encode(changePasswordForm.getPassword().trim()));
		repository.save(user);

	}

	@Override
	public void changeEmail(long id, ChangeEmailForm changeEmailForm) {
		User logInUser = MyUtil.getSessionUser();
		User user = repository.findOne(id);
		
		
		MyUtil.validate(logInUser.getId() == id || logInUser.isAdmin(),
				"noPermission");
		user.setEmail(null);
		user.setEmail(changeEmailForm.getEmail());
		repository.save(user);

	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED, readOnly = false)
	public void deleteProfile(long userId) {
		User loggedInUser= MyUtil.getSessionUser();
		MyUtil.validate(loggedInUser.getId() == userId || loggedInUser.isAdmin(), "noPermission");
		User userBeingDeletedUser = repository.findOne(userId);
		final String email = userBeingDeletedUser.getEmail();
		userBeingDeletedUser.setEmail(null);//set the email to nulll so he can sign up next time around
		repository.delete(userBeingDeletedUser);
		
		TransactionSynchronizationManager
		.registerSynchronization(new TransactionSynchronizationAdapter() {

			@Override
			public void afterCommit() {
				String link = MyUtil.getHosAndPort();
				try {
					LOG.info("Sending email to" + email);
					sender.send(email, MyUtil
							.getMessage("deleteUserSubject"), MyUtil
							.getMessage("deltedUserBody", link));
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					LOG.error(ExceptionUtils.getStackTrace(e));

				}
			}
		});

		
		
		
	}

}
