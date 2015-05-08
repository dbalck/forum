package com.forum.web.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@Column(name="user_id")
	private String username;
	private String name;
	private String authority;
	private boolean enabled;
	private String password;
	private String email;
		
	public User() {}
	
}
