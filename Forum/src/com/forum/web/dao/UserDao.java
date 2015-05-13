package com.forum.web.dao;

import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.forum.web.user.User;

@Repository
@Transactional
@Component("userDao")
public class UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	public UserDao() {
		System.out.println("successfully loaded userDao");
	}
	
	@SuppressWarnings("unchecked")
	public Set<User> getAllUsers() {
		Query query = session().createQuery("from Users");
		return (Set<User>) query.list();
	}
	
	public void createUser(User user) {
		session().saveOrUpdate(user);
	}
	
	public User getUserByUsername(String username) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.eq("username", username));
		return (User) crit.uniqueResult();
		
	}
	
	public boolean usernameExists(String username) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.eq("username", username));
		if (crit.uniqueResult() != null) {
			return true;
		}
		return false;
	}
	
	public boolean emailExists(String email) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.eq("email", email));
		if (crit.uniqueResult() != null) {
			return true;
		}
		return false;

	}
}