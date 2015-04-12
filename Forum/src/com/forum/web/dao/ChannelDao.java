package com.forum.web.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.forum.web.rss.RssChannel;
import com.forum.web.rss.RssItem;

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
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<RssChannel> getAllChannels() {
		return session().createQuery("from RssChannel").list();
	}
	
	@Transactional
	public void createChannel(RssChannel channel) {
		session().save(channel);
	}
	
	public RssChannel getChannel(int channelId) {
		return (RssChannel) session().load(RssChannel.class, channelId);
	}
	
	@SuppressWarnings("unchecked")
	public List<RssItem> getItems(RssChannel channel) {
		return session()
				.createQuery("from RssItem as item where item.channel = ?")
				.setInteger(0, channel.getId())
				.list();
	}
	
	public RssChannel getChannelByTitle(String title) {
		return (RssChannel) session()
				.createQuery("from RssChannel as channel where channel.title = ?")
				.setString(0, title)
				.uniqueResult();
	}
	
	public boolean exists(RssChannel channel) {
		return false;
	}
	
}
