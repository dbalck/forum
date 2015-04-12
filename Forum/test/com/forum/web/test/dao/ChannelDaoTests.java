package com.forum.web.test.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forum.web.dao.ChannelDao;
import com.forum.web.rss.Enclosure;
import com.forum.web.rss.Image;
import com.forum.web.rss.RssChannel;
import com.forum.web.rss.RssItem;
import com.forum.web.rss.SkipDays;
import com.forum.web.rss.TextInput;

@ActiveProfiles("dev")
@ContextConfiguration(locations={"classpath:com/forum/web/config/dao-context.xml", 
		"classpath:com/forum/web/test/config/datasource.xml",
		"classpath:com/forum/web/config/dao-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ChannelDaoTests {
	private Enclosure enclosure1;
	private RssItem item1;
	private RssItem item2;
	private Image image1;
	private TextInput ti1;
	private SkipDays sd1;
	private RssChannel channel1;
	private Set<RssItem> items;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private DataSource dataSource;
	
	@Before
	public void initializeDB() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("delete from channels");
		jdbc.execute("delete from images");
		jdbc.execute("delete from items");
		jdbc.execute("delete from users");
		jdbc.execute("delete from images");
		jdbc.execute("delete from textinputs");
		jdbc.execute("delete from skipdays");
		
		item1 = new RssItem("title1", "example1.com", "this is the first item");
		enclosure1 = new Enclosure(888, "this is the enclosure type", "enclosureurl.com");
		
		item1.setEnclosure(enclosure1);
		
		item2 = new RssItem("title2", "example2.com", "this is the second item");
		
		items = new HashSet<RssItem>();
		items.add(item1);
		items.add(item2);
		
		image1 = new Image("Title for image", "imagelink.com", "imageurl.com");
		
		ti1 = new TextInput("text input title", "textlink.com", "text input description goes here", "name of the text input");
		
		List<String> days = new ArrayList<String>();
		days.add("monday");
		days.add("tuesday");
		sd1 = new SkipDays(days);
		
		channel1 = new RssChannel("Channel Title1", "example1.com", "This is the first channel");

	}
	
	@Test
	public void testCreateChannel() {
		channel1.setItems(items);
		channel1.setImage(image1);
		channel1.setTextInput(ti1);
		channel1.setSkipDays(sd1);

		channelDao.createChannel(channel1);
		
		RssChannel channel = channelDao.getAllChannels().get(0);
		String title = channel.getTitle();
		assertEquals("Titles should be the same", "Channel Title1", title);	
		
		List<RssItem> items = channelDao.getItems(channel);
		assertEquals("There should be two items", 2, items.size());

	}
	


}
