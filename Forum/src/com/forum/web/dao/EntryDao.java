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

import com.forum.web.atom.AtomEntry;


public class EntryDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	public EntryDao() {
		System.out.println("entryDao is live");
	}

	public Set<AtomEntry> getAllEntries() {
		return null;
	}
		
	// queries based on global id; uses getEntryById for the heavy lifting
	public boolean exists(AtomEntry entry) {
		String id = entry.getGlobalId();
		AtomEntry ret = getEntryById(id);
		return ret != null ? true : false;
	}
	
	// gets the Entry based on the entry's global identifier
	// returns null if it doesn't exist
	public AtomEntry getEntryById(String globalId) {
		Criteria crit = session().createCriteria(AtomEntry.class);
		crit.add(Restrictions.eq("globalId", globalId));
		return (AtomEntry) crit.uniqueResult();
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


	
}
