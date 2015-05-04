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
@Component("entryDao")
public class EntryDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	public EntryDao() {
		System.out.println("entryDao is live");
	}
	
	public void createEntry(AtomEntry entry, AtomFeed feed) throws NullPointerException {
		if (entry == null || feed == null) {
			throw new NullPointerException(" null entry or feed");
		}
		
		feed.addEntry(entry);
		session().saveOrUpdate(feed);
		
	}
	
	public void addEntiresToFeed(Set<AtomEntry> entries, AtomFeed feed) {		
		for (AtomEntry entry: entries) {
			this.createEntry(entry, feed);
		}
	}

	
	@SuppressWarnings("unchecked")
	public Set<AtomEntry> getAllEntries() {
		Query query = session().createQuery("from AtomEntry");
		Set<AtomEntry> result =  new HashSet<AtomEntry>(query.list());
		return result;
	}
		
	public boolean exists(AtomEntry entry) {
		String id = entry.getGlobalId();
		Criteria crit = session().createCriteria(AtomEntry.class);
		crit.add(Restrictions.eq("globalId", id));
		AtomEntry ret = (AtomEntry) crit.uniqueResult();
		return ret != null ? true : false;

	}
	
	// queries by the Entry's globalId
	// returns null if it doesn't exist
	public AtomEntry getEntryById(String id) {
		Criteria crit = session().createCriteria(AtomEntry.class);
		crit.add(Restrictions.eq("globalId", id));
		AtomEntry entry = (AtomEntry) crit.uniqueResult();
		return entry;
	}
	
	@SuppressWarnings("unchecked")
	public Set<AtomEntry> getEntriesByTitle(String title) {
		Criteria crit = session().createCriteria(AtomEntry.class);
		crit.add(Restrictions.ilike("title", title, MatchMode.ANYWHERE));
		Set<AtomEntry> result =  new HashSet<AtomEntry>(crit.list());
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Set<AtomEntry> getEntriesByCategory(String category) {
		Query query = session().createQuery("from AtomEntry as entry right join entry.categories where category = lower(?) and entry is not null");
		query.setString(0, category);
		Set<AtomEntry> result =  new HashSet<AtomEntry>(query.list());
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public Set<AtomEntry> getEntriesByAuthorName(String name) {
		Criteria crit = session().createCriteria(AtomEntry.class);
		crit.createCriteria("authors").add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
		Set<AtomEntry> result =  new LinkedHashSet<AtomEntry>(crit.list());
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Set<AtomEntry> getEntriesByAuthorEmail(String email) {
		Criteria crit = session().createCriteria(AtomEntry.class);
		crit.createCriteria("authors").add(Restrictions.ilike("email", email, MatchMode.EXACT));
		Set<AtomEntry> result =  new LinkedHashSet<AtomEntry>(crit.list());
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public Set<AtomEntry> getEntriesFromFeed(AtomFeed feed) {
		Criteria crit = session().createCriteria(AtomEntry.class);
		crit.createAlias("feed", "f");
		crit.add(Restrictions.eq("f.id", feed.getId()));
		Set<AtomEntry> result =  new LinkedHashSet<AtomEntry>(crit.list());
		return result;
	}


	
}
