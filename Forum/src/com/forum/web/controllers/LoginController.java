package com.forum.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	public void setPasswordEncoder(PasswordEncoder encoder) {
		this.passwordEncoder = encoder;
	}

	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping("/newaccount")
	public String newAccount(Model model) {
		model.addAttribute("user", new User());
		return "newaccount";
	}
	
	@RequestMapping(value="/createaccount", method=RequestMethod.POST)
	public String createAccount(User user) {
		
		if (!userService.uniqueData(user.getUsername(), user.getEmail())) {
			System.out.println("bad user data");
			return "home";
		}
		user.setAuthority("USER");
		user.setEnabled(true);
		String pass = passwordEncoder.encode(user.getPassword());
		user.setPassword(pass);
		userService.createUser(user);
		
		return "home";
	}

	@RequestMapping(value="/accountcreated")
	public String accountCreated(Model model) {
		return "accountcreated";
	}

}
