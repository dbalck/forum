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

import com.forum.web.rss.RssChannel;
import com.forum.web.rss.RssItem;

@Transactional
@Component("itemDao")
public class ItemDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	public ItemDao() {
		System.out.println("successfully loaded itemDao");
	}
		
	// gets all the RssItems in the database, regardless of channel
	@SuppressWarnings("unchecked")
	public Set<RssItem> getAllItems() {
		Query query = session().createQuery("from RssItem");
		Set<RssItem> result =  new HashSet<RssItem>(query.list());
		return result;
	}
	
	public void saveItemToChannel(RssItem item, RssChannel channel) {
		// check to see if the channel already exists
		String id = channel.getLink();
		Criteria crit = session().createCriteria(RssChannel.class);
		crit.add(Restrictions.eq("link", id));
		RssChannel c = (RssChannel) crit.uniqueResult();
		
		// if it does exist, see if the same item already exists
		if (c != null) {
			id = item.getGlobalId();
			crit = session().createCriteria(RssItem.class);
			crit.add(Restrictions.eq("globalId", id));
			RssItem i = (RssItem) crit.uniqueResult();
			// so if the item does not exist already exist, save it to said channel, 
			// otherwise no need to add the item
			if (i == null) {
				item.setChannel(c);
				session().save(item);
			}
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public Set<RssItem> getItemsFromChannel(RssChannel channel) {
		Criteria crit = session().createCriteria(RssItem.class);
		crit.createAlias("channel", "c");
		crit.add(Restrictions.eq("c.id", channel.getId()));
		Set<RssItem> result =  new LinkedHashSet<RssItem>(crit.list());
		return result;

	}
	
	@SuppressWarnings("unchecked")
	public Set<RssItem> getItemsByTitle(String title) {
		Criteria crit = session().createCriteria(RssItem.class);
		crit.add(Restrictions.ilike("title", title, MatchMode.ANYWHERE));
		Set<RssItem> result =  new HashSet<RssItem>(crit.list());
		return result;
	}

			
	public boolean exists(RssItem item) {
		String id = item.getGlobalId();
		Criteria crit = session().createCriteria(RssItem.class);
		crit.add(Restrictions.eq("globalId", id));
		RssItem ret = (RssItem) crit.uniqueResult();
		return ret != null ? true : false;
	}
	
	@SuppressWarnings("unchecked")
	public Set<RssItem> getItemsByAuthor(String author) {
		Criteria crit = session().createCriteria(RssItem.class);
		crit.add(Restrictions.ilike("author", author, MatchMode.ANYWHERE));
		Set<RssItem> result =  new HashSet<RssItem>(crit.list());
		return result;

	}
		
	public void addItemsToChannel(Set<RssItem> items, RssChannel channel) {		
		for (RssItem item: items) {
			this.saveItemToChannel(item, channel);
		}
	}



}
