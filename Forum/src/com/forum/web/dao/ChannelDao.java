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

@Transactional
@Component("channelDao")
public class ChannelDao {

	@Autowired
	private SessionFactory sessionFactory;

	public ChannelDao() {
		System.out.println("successfully loaded channelDao");
		
	}
	
	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	// queries based on title and link;
	public boolean exists(RssChannel channel) {
		String id = channel.getLink();
		Criteria crit = session().createCriteria(RssChannel.class);
		crit.add(Restrictions.eq("link", id));
		RssChannel ret = (RssChannel) crit.uniqueResult();
		if (ret != null) {
			System.out.println("channel exists, forgoing channel save");
			return true;
		} else {
			System.out.println("channel doesn't exist, saving channel");
			return false;
		}
	}
	
	public void createChannel(RssChannel channel) {
		if (!exists(channel)) {
			session().save(channel);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Set<RssChannel> getAllChannels() {
		Query query = session().createQuery("from RssChannel");
		Set<RssChannel> result = new HashSet<RssChannel>(query.list());
		return result;
	}
	
	
	public RssChannel getChannelById(String id) {
		Criteria crit = session().createCriteria(RssChannel.class);
		crit.add(Restrictions.eq("link", id));
		return (RssChannel) crit.uniqueResult();
	}
			
	
	// Returns all the channels whose title strings at lease partially match (case insensitive) the argument string
	@SuppressWarnings("unchecked")
	public Set<RssChannel> getChannelsByTitle(String title) {
		Criteria crit = session().createCriteria(RssChannel.class);
		crit.add(Restrictions.ilike("title", title, MatchMode.ANYWHERE));
		Set<RssChannel> result =  new HashSet<RssChannel>(crit.list());
		return result;

	}
	
	@SuppressWarnings("unchecked")
	public Set<RssChannel> getChannelsByCategory(String category) {
		Criteria crit = session().createCriteria(RssChannel.class);
		crit.add(Restrictions.ilike("category", category));
		Set<RssChannel> result =  new HashSet<RssChannel>(crit.list());
		return result;
		
	}

	@SuppressWarnings("unchecked")
	public Set<RssChannel> getFeedsByPersonName(String name) {
		Criteria crit = session().createCriteria(RssChannel.class);
		crit.add(Restrictions.or(
				Restrictions.ilike("managingEditor", name, MatchMode.ANYWHERE), 
				Restrictions.ilike("webMaster", name, MatchMode.ANYWHERE) 
				));
		Set<RssChannel> result =  new LinkedHashSet<RssChannel>(crit.list());
		
		return result;
	}
	
	public void deleteChannel(RssChannel channel) {
		session().delete(channel);
	}

	
}
