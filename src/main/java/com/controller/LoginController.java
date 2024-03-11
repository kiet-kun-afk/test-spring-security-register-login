package com.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@ResponseBody
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		return "Denied";
	}

	@ResponseBody
	@RequestMapping("/success")
	public String handleLoginSuccess() {
		return "Successfully";
	}

	@ResponseBody
	@RequestMapping("/failure")
	public String handleLoginFailure() {
		return "Email or password is not true";
	}

	@ResponseBody
	@GetMapping("staff")
	@PreAuthorize("hasAuthority('STAFF')")
	public String getStaff() {
		return "YOU ARE THE STAFF";
	}

	@ResponseBody
	@GetMapping("manager")
	@PreAuthorize("hasAuthority('MANAGER')")
	public String getManager() {
		return "YOU ARE THE MANAGER";
	}
}
