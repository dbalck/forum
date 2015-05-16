package com.forum.web.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.forum.web.service.UserService;
import com.forum.web.user.User;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private HomeController homeController;
	
	@Autowired
	public void setPasswordEncoder(PasswordEncoder encoder) {
		this.passwordEncoder = encoder;
	}

	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping("/logout")
	public String showLogout(HttpServletRequest http) {
		try {
			http.logout();
		} catch (ServletException e) {
			return "failedlogout";
		}
		return "successfullogout";
	}
	
	@RequestMapping("/failedlogout")
	public String failedLogout() {
		return "failedlogout";
	}
	
	@RequestMapping("/successfullogout")
	public String successfulLogout() {
		return "successfullogout";
	}
	
	@RequestMapping("/newaccount")
	public String newAccount(Model model) {
		model.addAttribute("user", new User());
		return "newaccount";
	}
	
	@RequestMapping(value="/createaccount", method=RequestMethod.POST)
	public String createAccount(Model model, User user) {
		System.out.println("createaccount invoked");
		if (!userService.uniqueData(user.getUsername(), user.getEmail())) {
			System.out.println("bad user data");
		}
		user.setAuthority("USER");
		user.setEnabled(true);
		String pass = passwordEncoder.encode(user.getPassword());
		user.setPassword(pass);
		System.out.println("creating user: " + user.getUsername());
		userService.createUser(user);
		
		return "accountcreated";
	}

	@RequestMapping(value="/accountcreated")
	public String accountCreated(Model model) {
		return "accountcreated";
	}

}
