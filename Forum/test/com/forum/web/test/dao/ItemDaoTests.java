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
import com.forum.web.dao.ItemDao;
import com.forum.web.rss.Enclosure;
import com.forum.web.rss.Image;
import com.forum.web.rss.RssChannel;
import com.forum.web.rss.RssItem;
import com.forum.web.rss.TextInput;

@ActiveProfiles("dev")
@ContextConfiguration(locations={"classpath:com/forum/web/test/config/dao-context.xml", 
		"classpath:com/forum/web/test/config/datasource.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ItemDaoTests {

	private JdbcTemplate jdbc;
	private Enclosure enclosure1;
	private RssItem item1;
	private RssItem item2;
	private RssItem item3;
	private RssItem item4;
	private RssItem item5;
	private RssItem item6;
	private Image image1;
	private TextInput ti1;
	private RssChannel channel1;
	private RssChannel channel2;
	private RssChannel channel3;
	private Set<RssItem> items1;
	private Set<RssItem> items2;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ItemDao itemDao;
		
	@Before
	public void init() {
		jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("delete from channels");
		jdbc.execute("delete from images");
		jdbc.execute("delete from items");
		jdbc.execute("delete from users");
		jdbc.execute("delete from images");
		jdbc.execute("delete from textinputs");
		jdbc.execute("delete from skipdays");
		
		item1 = new RssItem("title1", "example1.com", "this is the first item", null);
		enclosure1 = new Enclosure(888, "this is the enclosure type", "enclosureurl.com");
		item1.addEnclosure(enclosure1);
		
		item2 = new RssItem("title2", "example2.com", "this is the second item", null);
		
		items1 = new HashSet<RssItem>();
		items1.add(item1);
		items1.add(item2);
		
		item3 = new RssItem("ninjas attack the white house", "example3.com", "The identity of the culprits is still unknown", null);
		item4 = new RssItem("Senator admits to love affair with staffer", "example4.com", "Constituents report that they are unsurprised", null);
		items2 = new HashSet<RssItem>();
		items2.add(item3);
		items2.add(item4);
		
		image1 = new Image("Title for image", "imagelink.com", "imageurl.com");
		
		ti1 = new TextInput("text input title", "textlink.com", "text input description goes here", "name of the text input");
		
		List<String> days = new ArrayList<String>();
		days.add("monday");
		days.add("tuesday");
		
		channel1 = new RssChannel("Anti-Tank - R - US", "example1.com", "Your most reliable source for Anti-tank-weapon-news");
		channel1.addItems(items1);
		channel1.addImage(image1);
		channel1.addTextInput(ti1);
		
		channel2 = new RssChannel("All puppies, all the time", "example2.com", "This is the second channel");
		channel2.addItems(items2);
		channel2.addImage(image1);
		channel2.addTextInput(ti1);
		
		item5 = new RssItem("ninjas attack the white house", "example5.com", "The identity of the culprits is still unknown", null);
		item6 = new RssItem("Senator admits to love affair with staffer", "example6.com", "Constituents report that they are unsurprised", null);
		channel3 = new RssChannel("this is channel 3", "example3.com", "This is the third channel");
	}
	
	@Test
	public void testGetAllItems() {
		
		// empty set
		Set<RssItem> items = itemDao.getAllItems();
		assertEquals("there should be no items saved yet", 0, items.size());
		
		channelDao.createChannel(channel1);
		items = itemDao.getAllItems();
		assertEquals("there should be a couple items saved in channel1", 2, items.size());

		channelDao.createChannel(channel2);
		items = itemDao.getAllItems();
		assertEquals("there should be a four items saved between channel1 and channel2", 4, items.size());
		
		// add a channel without items, double check no change
		channelDao.createChannel(channel3);
		items = itemDao.getAllItems();
		assertEquals("there should be a four items saved between channel1 and channel2", 4, items.size());
		
		
		}


	@Test
	public void testSaveItemToChannel() {

		channelDao.createChannel(channel1);
		Set<RssItem> items = itemDao.getAllItems();
		assertEquals("there should be a couple items saved in channel1", 2, items.size());

		// add another item to channel1 and update		
		itemDao.saveItemToChannel(new RssItem("AA", "BB", "CC", "DD"), channel1);
		items = itemDao.getAllItems();
		assertEquals("there should be three items saved in channel1", 3, items.size());

		// add another item
		itemDao.saveItemToChannel(item4, channel1);
		items = itemDao.getAllItems();
		assertEquals("there should be four items saved in channel1", 4, items.size());
		
		channelDao.createChannel(channel2);
		items = itemDao.getAllItems();
		assertEquals("there should be four items saved in channel1 and another unique one in channel2", 5, items.size());

		
	}
	
	@Test
	public void testGetItemsByTitle() {
		channel3.addItem(item5);
		channel3.addItem(item6);

		channelDao.createChannel(channel1);
		channelDao.createChannel(channel2);
		channelDao.createChannel(channel3);

		Set<RssItem> items = itemDao.getItemsByTitle("random nonexistent title");
		assertEquals("there should be no items with the title 'random nonexistent title'", 0, items.size());

		items = itemDao.getItemsByTitle("title1");
		assertEquals("item1 should have that title", 1, items.size());
		
		items = itemDao.getItemsByTitle("title0");
		assertEquals("no items should have that title", 0, items.size());
		
		items = itemDao.getItemsByTitle("ninjas attack");
		assertEquals("items 3 & 5 should have that title", 2, items.size());
		
		items = itemDao.getItemsByTitle("");
		assertEquals("empty string, all items should match", 6, items.size());


		
	}
	
	@Test
	public void testGetItemsByAuthor() {
		item1.setAuthor("burt");
		item2.setAuthor("bob");
		item3.setAuthor("bill");
		item4.setAuthor("gary");
		channelDao.createChannel(channel1);
		channelDao.createChannel(channel2);
		
		Set<RssItem> items = itemDao.getItemsByAuthor("Dan");
		assertEquals("there should be no items with the author 'Dan'", 0, items.size());
		
		items = itemDao.getItemsByAuthor("burt");
		assertEquals("there should be one item with the author 'burt'", 1, items.size());
		
		
		// check case insensitive
		items = itemDao.getItemsByAuthor("Burt");
		assertEquals("there should be one item with the author 'burt'", 1, items.size());

		items = itemDao.getItemsByAuthor("bill");
		assertEquals("there should be no items with the author 'bill'", 1, items.size());

		// check b
		items = itemDao.getItemsByAuthor("B");
		assertEquals("there should be three items with the author starting with 'B'", 3, items.size());
		
		// check empty string -- should return all
		items = itemDao.getItemsByAuthor("");
		assertEquals("Empty string should match all four authors", 4, items.size());

		

	}


	

}
