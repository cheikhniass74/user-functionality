package com.user.spring.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.user.spring.dto.ForgotPasswordForm;
import com.user.spring.dto.ResetPasswordForm;
import com.user.spring.dto.SignupForm;
import com.user.spring.mail.MailSender;
import com.user.spring.services.UserService;
import com.user.spring.util.MyUtil;
import com.user.spring.validation.ForgotPasswordFormValidator;
import com.user.spring.validation.ResetPasswordFormValidator;
import com.user.spring.validation.SignUpFormValidator;

@Controller
public class RootController {

	private MailSender mailSender;
	private static final Log LOG = LogFactory.getLog(RootController.class);
	private UserService userService;
	private SignUpFormValidator signUpFormValidator;
	private ForgotPasswordFormValidator forgotPasswordFormValidator;
	private ResetPasswordFormValidator resetPasswordFormValiDator;

	@Autowired
	public RootController(MailSender mailSender, UserService userService,
			SignUpFormValidator signUpFormValidator,
			ForgotPasswordFormValidator forgotPasswordFormValidator,
			ResetPasswordFormValidator resetPasswordFormValiDator) {
		super();
		this.signUpFormValidator = signUpFormValidator;
		this.mailSender = mailSender;
		this.userService = userService;
		this.forgotPasswordFormValidator = forgotPasswordFormValidator;
		this.resetPasswordFormValiDator = resetPasswordFormValiDator;
	}

	@InitBinder("signupForm")
	protected void initSignUpBinder(WebDataBinder binder) {
		binder.setValidator(signUpFormValidator);
	}

	@InitBinder("forgotPasswordForm")
	protected void initForgotPasswordBinder(WebDataBinder binder) {
		binder.setValidator(forgotPasswordFormValidator);
	}

	@InitBinder("resetPasswordForm")
	protected void initResetPasswordBinder(WebDataBinder binder) {
		binder.setValidator(resetPasswordFormValiDator);
	}

	// home page
	@RequestMapping("/")
	public String send() throws MessagingException {
		// mailSender.send("cheikh.niass@outlook.com", "Lmfaaaaa", "content");
		return "home";
	}

	/*
	 * @RequestMapping("/login") public String loing() { //
	 * mailSender.send("cheikh.niass@outlook.com", "Lmfaaaaa", "content");
	 * return "login"; }
	 */

	// sign up page get
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute(new SignupForm());

		return "signup";
	}

	// sign up page post
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(
			@ModelAttribute("signupForm") @Valid SignupForm signupForm,
			BindingResult result, RedirectAttributes redirectAttributes) {

		// check whether there is an error on the form if there is return the
		// form
		if (result.hasErrors()) {
			return "signup";
		}

		LOG.info(signupForm.toString());

		// if there is no error then save the newly created user in the database
		userService.signup(signupForm);

		// display a message showing whether the user that he has successfully
		// sign up
		MyUtil.flah(redirectAttributes, "success", "signUpSuccesful");

		/*
		 * redirectAttributes.addFlashAttribute("flashKind", "success");
		 * redirectAttributes .addFlashAttribute("flashMessage",
		 * "Sign up succesful, Please check you mail box to verify youself");
		 */
		return "redirect:/";
	}

	@RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
	public String forgotPaswordform(Model model) {
		model.addAttribute(new ForgotPasswordForm());

		return "forgot-password";
	}

	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public String forgotPaswordform(
			@ModelAttribute("forgotPasswordForm") @Valid ForgotPasswordForm forgotPasswordForm,
			BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "forgot-password";

		}
		userService.forgotPassword(forgotPasswordForm);
		MyUtil.flah(redirectAttributes, "success", "forgotPasswordMessage");

		return "redirect:/";
	}

	@RequestMapping(value = "/reset-password/{forgotPasswordCode}", method = RequestMethod.GET)
	public String resetPaswordform(Model model) {
		model.addAttribute(new ResetPasswordForm());

		return "reset-password";
	}

	@RequestMapping(value = "/reset-password/{forgotPasswordCode}", method = RequestMethod.POST)
	public String resetPaswordform(
			@PathVariable("forgotPasswordCode") String forgotPasswordCode,
			@ModelAttribute("resetPasswordForm") @Valid ResetPasswordForm resetPasswordForm,
			BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "reset-password";

		}
		userService
				.resetPassword(forgotPasswordCode, resetPasswordForm, result);
		MyUtil.flah(redirectAttributes, "success", "resetPasswordMessage");

		return "redirect:/login";
	}

}
