package com.forum.web.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forum.web.dao.UserDao;
import com.forum.web.user.User;

@Service("userService")
public class UserService {
	
	public UserService() {
		System.out.println("userService created");
	}
	
	private UserDao userDao;
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		User user = userDao.getUserByUsername(username);
		return buildUserForAuthentication(user);
	}
		
	private org.springframework.security.core.userdetails.User buildUserForAuthentication(com.forum.web.user.User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		boolean enabled = user.isEnabled();
		List<GrantedAuthority> authority = getAuthority(user);
		return new org.springframework.security.core.userdetails.User(
				username, password, 
				enabled, true, 
				true, true, 
				authority);
	}
	
	private List<GrantedAuthority> getAuthority(User user) {
		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
		auths.add(new SimpleGrantedAuthority(user.getAuthority()));
		return new ArrayList<GrantedAuthority>(auths);
	}
	
	public boolean uniqueData(String username, String email) {
		if (userDao.usernameExists(username) || userDao.emailExists(email)) {
			return false;
		}
		return true;
	}	
	
	@Transactional
	public void createUser(User user) {
		userDao.createUser(user);
	}

}
