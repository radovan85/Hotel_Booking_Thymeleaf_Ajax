package com.radovan.spring.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.radovan.spring.exceptions.InvalidUserException;
import com.radovan.spring.model.RegistrationForm;
import com.radovan.spring.service.GuestService;

@Controller
public class MainController {
	
	@Autowired
	private GuestService guestService;

	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String indexPage() {
		return "index";
	}
	
	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public String homePage() {
		return "fragments/homePage :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, ModelMap map) {
		if (error != null) {
			map.put("error", "Invalid username and Password");
		}

		if (logout != null) {
			map.put("logout", "You have logged out successfully");
		}
		return "fragments/login :: ajaxLoadedContent";
	}
	
	
	@RequestMapping(value = "/loginPassConfirm", method = RequestMethod.POST)
	public String confirmLoginPass(Principal principal) {
		Optional<Principal> authPrincipal = Optional.ofNullable(principal);
		if (!authPrincipal.isPresent()) {
			Error error = new Error("Invalid user");
			throw new InvalidUserException(error);
		}

		return "fragments/homePage :: ajaxLoadedContent";
	}
	
	@RequestMapping(value="/loginErrorPage",method = RequestMethod.GET)
	public String logError(ModelMap map) {
		map.put("alert", "Invalid username or password!");
		return "fragments/login :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/loggedout", method = RequestMethod.POST)
	public String logout() {
		SecurityContextHolder.clearContext();
		return "fragments/homePage :: ajaxLoadedContent";
	}
	
	@RequestMapping(value="/contactInfo",method = RequestMethod.GET)
	public String getContactInfo() {
		return "fragments/contact :: ajaxLoadedContent";
	}
	
	@RequestMapping(value="/register",method = RequestMethod.GET)
	public String userRegistration(ModelMap map) {
		RegistrationForm registerForm = new RegistrationForm();
		map.put("registerForm", registerForm);
		return "fragments/registration :: ajaxLoadedContent";
	}
	
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public String registerUser(@ModelAttribute ("registerForm") RegistrationForm registerForm) {
		guestService.storeGuest(registerForm);
		return "fragments/homePage :: ajaxLoadedContent";
	}
	
	@RequestMapping(value = "/registerComplete", method = RequestMethod.GET)
	public String registrationCompleted() {
		return "fragments/registration_completed :: ajaxLoadedContent";
	}

	@RequestMapping(value = "/registerFail", method = RequestMethod.GET)
	public String registrationFailed() {
		return "fragments/registration_failed :: ajaxLoadedContent";
	}
}
