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
		String id = channel.getLink();
		Criteria crit = session().createCriteria(RssChannel.class);
		crit.add(Restrictions.eq("link", id));
		RssChannel c = (RssChannel) crit.uniqueResult();
		item.setChannel(c);
		session().save(item);
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


}
