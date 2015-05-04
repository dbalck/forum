package com.forum.web.dao;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.forum.web.atom.AtomEntry;
import com.forum.web.atom.AtomFeed;

@Transactional
@Component("feedDao")
public class FeedDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	public FeedDao() {
		System.out.println("feedDao is live");
	}

	// creates and inserts the Feed objects into the database via hibernate
	public void createFeed(AtomFeed feed) {
		if (!exists(feed)) {
			session().save(feed);
		}
	}
	
	// queries based on global id; uses getFeedById for the heavy lifting
	public boolean exists(AtomFeed feed) {
		String id = feed.getGlobalId();
		Criteria crit = session().createCriteria(AtomFeed.class);
		crit.add(Restrictions.eq("globalId", id));
		AtomFeed ret = (AtomFeed) crit.uniqueResult();
		return ret != null ? true : false;
	}
	
	// gets the feed based on the feed's global identifier
	// returns null if it doesn't exist
	public AtomFeed getFeedById(String id) {
		Criteria crit = session().createCriteria(AtomFeed.class);
		crit.add(Restrictions.eq("globalId", id));
		AtomFeed feed = (AtomFeed) crit.uniqueResult();
		return feed;
	}
	
	@SuppressWarnings("unchecked")
	public Set<AtomFeed> getFeedsByTitle(String title) {
		Criteria crit = session().createCriteria(AtomFeed.class);
		crit.add(Restrictions.ilike("title", title, MatchMode.ANYWHERE));
		Set<AtomFeed> result =  new HashSet<AtomFeed>(crit.list());
		return result;

	}
	
	@SuppressWarnings("unchecked")
	public Set<AtomFeed> getFeedsByCategory(String category) {
		Query query = session().createQuery("from AtomFeed as feed right join feed.categories where category = lower(?) and feed is not null");
		query.setString(0, category);
		Set<AtomFeed> result =  new HashSet<AtomFeed>(query.list());
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public Set<AtomFeed> getFeedsByAuthorName(String name) {
		Criteria crit = session().createCriteria(AtomFeed.class);
		crit.createCriteria("authors").add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
		Set<AtomFeed> result =  new LinkedHashSet<AtomFeed>(crit.list());
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Set<AtomFeed> getFeedsByAuthorEmail(String email) {
		Criteria crit = session().createCriteria(AtomFeed.class);
		crit.createCriteria("authors").add(Restrictions.ilike("email", email, MatchMode.EXACT));
		Set<AtomFeed> result =  new LinkedHashSet<AtomFeed>(crit.list());
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Set<AtomFeed> getAllFeeds() {
		Query query = session().createQuery("from AtomFeed");
		Set<AtomFeed> result =  new LinkedHashSet<AtomFeed>(query.list());
		return result;

	}	
	
	// old feed is a managed object, new feed is a detached object
	public void mergeFeed(AtomFeed newFeed, AtomFeed persistedFeed) {
		newFeed.setId(persistedFeed.getId());
		session().merge(newFeed);
		
	}

	
}
