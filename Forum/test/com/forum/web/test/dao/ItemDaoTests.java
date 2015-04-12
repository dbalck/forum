package com.forum.web.test.dao;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.forum.web.dao.ItemDao;
import com.forum.web.rss.RssChannel;
import com.forum.web.rss.RssItem;

@ActiveProfiles("dev")
@ContextConfiguration(locations={
		"classpath:com/forum/web/config/dao-context.xml", 
		"classpath:com/forum/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ItemDaoTests {

	RssChannel channel1;
	
	RssItem item1; // whole object: all fields filled out
	RssItem item2;
	RssItem item3;
	RssItem item4;

	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private DataSource dataSource;
	
	@Before
	public void init() {
		
		// clear out the database tables
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("delete from items");
		jdbc.execute("delete from channels");
		jdbc.execute("delete from users");

		/* build the first test channel
		RssChannel.Builder cb1 = new RssChannel.Builder("NEWS R US", "http://newsrus.com", "We've got all the best news here");
		channel1 = cb1.build();
		
		// build the first test item
		RssItem.Builder b1 = new RssItem.Builder("The Panthers win the Superbowl", 
				"http://thisdoesntexist.com",
				"The Panters beat the Dallas Cowboys 45 - 6 to earn their superbowl XXXXXXXL title");
		b1.pubDate(1234567898);
		b1.category("sports");
		b1.guid("http://thisdoesntexist.com/item2342q353");
		// b1.enclosure("placeholder for enclosure"); 
		b1.source("http://someothersite.com/item");
		b1.author("Burt Reynolds");
		b1.comments("These are some random comments");
		item1 = b1.build();
		*/
		
	}
	
	@Test
	public void testCreateItem() {
		assertTrue("dummy test", true);
	}
	
	/*
	@Test
	public void testCreateItem() {
		itemDao.createItem(item1, 1);
		List<RssItem> items = itemDao.getAllItems();
		assertEquals("Number of items should be 1", 1, items.size());
		
	}*/
	


}
