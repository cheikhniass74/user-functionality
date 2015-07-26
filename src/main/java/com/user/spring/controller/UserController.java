package com.user.spring.controller;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
import com.user.spring.dto.ChangeEmailForm;
import com.user.spring.dto.ChangePasswordForm;
import com.user.spring.dto.UserEditForm;
import com.user.spring.entity.User;
import com.user.spring.services.UserService;
import com.user.spring.util.MyUtil;
import com.user.spring.validation.ChangeEmailFormValidator;
import com.user.spring.validation.ChangePasswordFormValidator;

@Controller
@RequestMapping("/users")
public class UserController {
	private UserService userService;
	private ChangePasswordFormValidator changePasswordFormValidator;
	private ChangeEmailFormValidator changeEmailFormValidator;

	@Autowired
	public UserController(UserService userService,
			ChangePasswordFormValidator changePasswordFormValidator,
			ChangeEmailFormValidator changeEmailFormValidator) {
		super();
		this.userService = userService;
		this.changePasswordFormValidator = changePasswordFormValidator;
		this.changeEmailFormValidator = changeEmailFormValidator;
	}

	@InitBinder("changePasswordForm")
	protected void initChangePasswordBinder(WebDataBinder binder) {
		binder.setValidator(changePasswordFormValidator);
	}

	@InitBinder("changeEmailForm")
	protected void initChangeEmailBinder(WebDataBinder binder) {
		binder.setValidator(changeEmailFormValidator);
	}

	@RequestMapping("/{verficationCode}/verify")
	public String verify(
			@PathVariable("verficationCode") String verificationCode,
			RedirectAttributes redirectAttributes, HttpServletRequest request)
			throws ServletException {

		userService.verify(verificationCode);

		MyUtil.flah(redirectAttributes, "success", "verificationSuccess");

		request.logout();

		return "redirect:/";

	}

	@RequestMapping("/resend-verication-mail")
	public String resendVericcationEmail(RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws ServletException {
		userService.resendVerifcationEmail();
		MyUtil.flah(redirectAttributes, "success", "resendVericationMessage");
		request.logout();
		return "redirect:/";

	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public String findOne(@PathVariable("userId") long id, Model model) {
		model.addAttribute(userService.findOne(id));
		return "user";

	}

	@RequestMapping(value = "/{userId}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable("userId") long id, Model model) {
		User user = userService.findOne(id);
		UserEditForm userEditForm = new UserEditForm();
		userEditForm.setName(user.getName());
		userEditForm.setRoles(user.getRoles());
		model.addAttribute(userEditForm);
		return "user-edit";

	}

	@RequestMapping(value = "/{userId}/edit", method = RequestMethod.POST)
	public String edit(@PathVariable("userId") long id,
			@ModelAttribute("userEditForm") @Valid UserEditForm userEditForm,
			BindingResult result, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {

		if (result.hasErrors()) {
			return "user-edit";
		}
		userService.updateProfle(id, userEditForm);
		MyUtil.flah(redirectAttributes, "success", "editsuccess");
		return "redirect:/login";

		// request.logout();

	}

	@RequestMapping(value = "/{userId}/change-password", method = RequestMethod.GET)
	public String changePassword(Model model) {
		model.addAttribute(new ChangePasswordForm());
		return "change-password";

	}

	@RequestMapping(value = "/{userId}/change-password", method = RequestMethod.POST)
	public String changePassword(
			@PathVariable("userId") long id,
			@ModelAttribute("changePasswordForm") @Valid ChangePasswordForm changePasswordForm,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws ServletException {

		if (result.hasErrors()) {
			return "change-password";

		}

		userService.changePassword(id, changePasswordForm);

		MyUtil.flah(redirectAttributes, "success", "passwordChanged");

		request.logout();
		return "redirect:/login";

	}

	@RequestMapping(value = "/{userId}/change-email", method = RequestMethod.GET)
	public String changeEmail(@PathVariable("userId") long id, Model model) {

		User userId = userService.findOne(id);
		ChangeEmailForm changeEmailForm = new ChangeEmailForm();
		changeEmailForm.setEmail(userId.getEmail());

		model.addAttribute(changeEmailForm);
		return "change-email";

	}

	@RequestMapping(value = "/{userId}/change-email", method = RequestMethod.POST)
	public String changeEmail(
			@PathVariable("userId") long id,
			@ModelAttribute("changeEmailForm") @Valid ChangeEmailForm changeEmailForm,
			BindingResult result, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			return "change-email";
		}

		userService.changeEmail(id, changeEmailForm);

		MyUtil.flah(redirectAttributes, "success", "emailchangesuccess");

		return "redirect:/login";

	}

	@RequestMapping(value ="/{userId}/delete-profile", method = RequestMethod.GET)
	public String deleteProfilel(@PathVariable("userId")  long userId,
			RedirectAttributes redirectAttributes, HttpServletRequest request) throws ServletException {
		
		
		userService.deleteProfile(userId);
		//MyUtil.flah(redirectAttributes, "success", "deleteProfileSucesss");
		request.logout();

		return "redirect:/";

	}
}
