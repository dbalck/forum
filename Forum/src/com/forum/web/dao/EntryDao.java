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
	
	@SuppressWarnings("unchecked")
	public Set<AtomEntry> getAllEntries() {
		Query query = session().createQuery("from AtomEntry");
		Set<AtomEntry> result =  new HashSet<AtomEntry>(query.list());
		return result;
	}
		
	// queries based on id (a md5 UUID hash of the globalId)
	public boolean exists(AtomEntry entry) {
		String id = entry.getId();
		AtomEntry ret = (AtomEntry) session().get(AtomEntry.class, id);
		return ret != null ? true : false;
	}
	
	// gets the Entry based on the entry's UUID (id field)
	// returns null if it doesn't exist
	public AtomEntry getEntryById(String id) {
		AtomEntry entry = (AtomEntry) session().get(AtomEntry.class, id);
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


	
}
