package com.forum.web.test.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

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
	
	private JdbcTemplate jdbc;
	private Enclosure enclosure1;
	private RssItem item1;
	private RssItem item2;
	private RssItem item3;
	private RssItem item4;
	private Image image1;
	private TextInput ti1;
	private SkipDays sd1;
	private RssChannel channel1;
	private RssChannel channel2;
	private Set<RssItem> items1;
	private Set<RssItem> items2;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private DataSource dataSource;
	
	@Before
	public void initializeDB() {
		jdbc = new JdbcTemplate(dataSource);
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
		
		items1 = new HashSet<RssItem>();
		items1.add(item1);
		items1.add(item2);
		
		item3 = new RssItem("ninjas attack the white house", "example3.com", "The identity of the culprits is still unknown");
		item4 = new RssItem("Senator admits to love affair with staffer", "example4.com", "Constituents report that they are unsurprised");
		items2 = new HashSet<RssItem>();
		items2.add(item3);
		items2.add(item4);
		
		image1 = new Image("Title for image", "imagelink.com", "imageurl.com");
		
		ti1 = new TextInput("text input title", "textlink.com", "text input description goes here", "name of the text input");
		
		List<String> days = new ArrayList<String>();
		days.add("monday");
		days.add("tuesday");
		sd1 = new SkipDays(days);
		
		channel1 = new RssChannel("Anti-Tank - R - US", "example1.com", "Your most reliable source for Anti-tank-weapon-news");
		channel1.setItems(items1);
		channel1.setImage(image1);
		channel1.setTextInput(ti1);
		channel1.setSkipDays(sd1);
		
		channel2 = new RssChannel("All puppies, all the time", "example1.com", "This is the first channel");
		channel2.setItems(items2);
		channel2.setImage(image1);
		channel2.setTextInput(ti1);
		channel2.setSkipDays(sd1);
	}
	
	@Test
	public void testCreateChannel() {		
		// empty set (no channels created)
		Set<RssChannel> channels = channelDao.getAllChannels();
		assertEquals("There should no channels created", 0, channels.size());	

		// one channel created
		channelDao.createChannel(channel1);
		channels = channelDao.getAllChannels();
		assertEquals("There should only be one channel", 1, channels.size());
		
		// duplicate channel should not be created
		channelDao.createChannel(channel1);
		channels = channelDao.getAllChannels();
		assertEquals("There should still be only be one channel (no duplicate)", 1, channels.size());	

		channelDao.createChannel(channel2);
		channels = channelDao.getAllChannels();
		assertEquals("There should now be two channels", 2, channels.size());	

	}
	
	@Test
	public void testGetAllChannels() {

		
	}
	
	@Test
	public void testGetChannelsByTitle() {

		
	}

	


}
