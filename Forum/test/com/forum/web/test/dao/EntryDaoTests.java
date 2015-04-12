package com.forum.web.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import com.forum.web.atom.AtomEntry;
import com.forum.web.atom.AtomFeed;
import com.forum.web.atom.Author;
import com.forum.web.atom.Contributor;
import com.forum.web.dao.ChannelDao;
import com.forum.web.dao.FeedDao;
import com.forum.web.parse.Parser;

// All tests based on xml data found in ~/Dan/Documents/forum_test_rss
// nyt.xml is the new york times rss feed, and tr.rss is the tech republic rss feed
// vox.xml is the atom feed

@ActiveProfiles("dev")
@ContextConfiguration(locations={"classpath:com/forum/web/config/dao-context.xml", 
		"classpath:com/forum/web/test/config/datasource.xml",
		"classpath:com/forum/web/config/dao-context.xml",
		"classpath:com/forum/web/config/service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class EntryDaoTests {
	
	private AtomFeed feed1;
	private AtomFeed feed2;
	private AtomFeed feed3;
	private List<AtomEntry> entries1;
	private List<AtomEntry> entries2;
	private long date1;
	private long date2;
	private Author author1;
	private Author author2;
	private Author author3;
	private Author author4;
	private Set<Author> authors1;
	private Set<Author> authors2;

	private Set<Contributor> contributors;
	private List<String> categories1;
	private List<String> categories2;

	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private FeedDao feedDao;
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbc;

	@Before
	public void init() {
		
		// create some data to be on hand for the tests
		date1 = Parser.parseDate("2015-04-09T12:10:02-04:00"); // 4/9/2015
		date2 = Parser.parseDate("2015-04-04T12:10:02-04:00"); // 4/4/2015
		author1 = new Author("george");
		author1.setEmail("george@gmail.com");
		author1.setUri("georgeswebsite.com");
		author2 = new Author("bill");
		author2.setEmail("bill@gmail.com");
		author2.setUri("billswebsite.com");
		authors1 = new HashSet<Author>();
		authors1.add(author1);
		authors1.add(author2);
		author3 = new Author("bill");
		author3.setEmail("bill@gmail.com");
		author4 = new Author("george");
		author4.setEmail("george@gmail.com");
		authors2 = new HashSet<Author>();
		authors2.add(author3);
		authors2.add(author4);

		categories1 = new ArrayList<String>();
		categories1.add("Sports");
		categories1.add("International");
		categories1.add("Politics");
		
		categories2 = new ArrayList<String>();
		categories2.add("Sports");
		categories2.add("Local");
		categories2.add("Politics");
		
		contributors = new HashSet<Contributor>();
		Contributor contributor1 = new Contributor("george");
		contributor1.setEmail("george.gmail.com");
		contributor1.setUri("georgeswebsite.com");
		Contributor contributor2 = new Contributor("Pete");
		contributor2.setEmail("pete.gmail.com");
		contributor2.setUri("peteswebsite.com");
		contributors.add(contributor1);
		contributors.add(contributor2);
		
		
		feed1 = new AtomFeed("Title of feed1", "yahoo.com/feed1a", date1);
		feed1.setAuthors(authors1);
		feed1.setCategories(categories1);
		feed1.setContributors(contributors);
		feed2 = new AtomFeed("Title of feed2", "google.com/feed2b", date2);
		feed3 = new AtomFeed("Title of feed3", "google.com/feed3", date2);
		feed3.setAuthors(authors2);
		feed3.setCategories(categories2);
		entries1 = new ArrayList<AtomEntry>();
		AtomEntry entry1 = new AtomEntry("Entry Title 1", "entry1111.com/entry1", date1);
		entry1.setAuthors(authors1);
		entry1.setCategories(categories1);
		entry1.setContributors(contributors);
		entry1.setContent("This is the content blah blah blah");
		entry1.setLink("somelinksomewhere.com");
		entry1.setRights("These are the rights!");
		entry1.setPublished(date1);
		entry1.setSource(new AtomFeed("source1 title", "source1.com/uniqueid", date1));
		entry1.setSummary("This is the summary of entry1");

		AtomEntry entry2 = new AtomEntry("Entry Title 2: The Second Coming", "entry2222.com/entry2", date2);
		entry2.setAuthors(authors2);
		entry2.setCategories(categories2);
		entry2.setContributors(contributors);
		entry2.setContent("This is the content of the second entry blah blah blah");
		entry2.setLink("somelinksomewhere.com");
		entry2.setRights("These are the rights!");
		entry2.setPublished(date2);
		entry2.setSource(new AtomFeed("source2 title", "source2.com/uniqueid", date2));
		entry2.setSummary("This is the summary of entry2");

		entries1.add(entry1);
		entries1.add(entry2);
		feed1.setEntries(entries1);
		
		// delete old data
		jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("delete from channels");
		jdbc.execute("delete from images");
		jdbc.execute("delete from items");
		jdbc.execute("delete from users");
		jdbc.execute("delete from images");
		jdbc.execute("delete from textinputs");
		jdbc.execute("delete from skipdays");

		jdbc.execute("delete from feeds");
		jdbc.execute("delete from authors");
		jdbc.execute("delete from entries");
		jdbc.execute("delete from categories");
		jdbc.execute("delete from contributors");


	}

	
	@Test
	public void testExists() {
		jdbc.execute("delete from feeds"); // clear table
		feedDao.createFeed(feed1);
		assertTrue("There should be feed1 in the db", feedDao.exists(feed1));
		feedDao.createFeed(feed2);
		assertTrue("There should be feed2 in the db", feedDao.exists(feed2));
		assertTrue("There should be feed1 in the db still", feedDao.exists(feed1));

	}

	@Test
	public void testgetAllEntries() {
		jdbc.execute("delete from feeds"); // clear table
		feedDao.createFeed(feed2);
		Set<AtomFeed> rFeeds = feedDao.getAllFeeds();
		assertEquals("There should be only be one feed", 1, rFeeds.size());
		feedDao.createFeed(feed2);
		rFeeds = feedDao.getAllFeeds();
		assertEquals("There should be two feeds, two regular", 2, rFeeds.size());
		feedDao.createFeed(feed1);
		rFeeds = feedDao.getAllFeeds();
		assertEquals("There should be four feeds, three regular, one source", 4, rFeeds.size());

	}

	@Test
	public void testGetEntryById() {
		jdbc.execute("delete from feeds"); // clear table
		AtomFeed rFeed = feedDao.getFeedById("google.com/feed2b");
		assertEquals("There are no db entries, should be 0 matches", null, rFeed);
		
		feedDao.createFeed(feed2);
		rFeed = feedDao.getFeedById("google.com/feed2b");
		assertEquals("Should be feed2's id in the database", "Title of feed2", rFeed.getTitle());
	}

	@Test
	public void testGetEntriesByAuthorName() {
		jdbc.execute("delete from feeds"); // clear table
		jdbc.execute("delete from authors"); // clear table
		jdbc.execute("delete from entries"); // clear table

		Set<AtomFeed> rFeeds = feedDao.getFeedsByAuthorName("george");
		assertEquals("there should be no feeds cited by george", 0, rFeeds.size());

		feedDao.createFeed(feed1); // has author1 and author 2 (george and bill)
		rFeeds = feedDao.getFeedsByAuthorName("george");
		assertEquals("Should only be one feed in the list", 1, rFeeds.size());
		rFeeds = feedDao.getFeedsByAuthorName("bill");
		assertEquals("Should only be one feed in the list", 1, rFeeds.size());
		
		feedDao.createFeed(feed3); // has author1 and author 2 (george and bill again)
		rFeeds = feedDao.getFeedsByAuthorName("george");
		assertEquals("two feeds now cite george as an author", 2, rFeeds.size());
		
		feedDao.createFeed(feed2); // add a new feed without authors
		rFeeds = feedDao.getFeedsByAuthorName("bill");
		assertEquals("two feeds cite bill as an author", 2, rFeeds.size());
		
		// should match partially too (to bill)
		rFeeds = feedDao.getFeedsByAuthorName("il"); 
		assertEquals("two feeds cite bill as an author", 2, rFeeds.size());

		// another partial match test
		rFeeds = feedDao.getFeedsByAuthorName("geo"); 
		assertEquals("two feeds cite bill as an author", 2, rFeeds.size());

		// a negative test
		rFeeds = feedDao.getFeedsByAuthorName("pete"); 
		assertEquals("no feeds cite pete as an author", 0, rFeeds.size());

	}

	@Test
	public void testGetEntriesByCategory() {
		jdbc.execute("delete from feeds"); // clear table
		jdbc.execute("delete from categories"); // clear table
		
		feedDao.createFeed(feed1); // feed1 has three categories, but none are claptrap
		Set<AtomFeed> rFeed = feedDao.getFeedsByCategory("claptrap");
		assertEquals("As there are no categories call claptrap", 0, rFeed.size());
		
		rFeed = feedDao.getFeedsByCategory("Sports");
		assertEquals("Should be one field with a sports category", 1, rFeed.size());

		// check for case insensitive match
		rFeed = feedDao.getFeedsByCategory("sportS");
		assertEquals("Should be one field with a sports category", 1, rFeed.size());
		
		// add another feed w/categories, check for two matches
		feedDao.createFeed(feed3); // feed3 has three categories
		rFeed = feedDao.getFeedsByCategory("sports");
		assertEquals("should be two feeds with Sports as a category", 2, rFeed.size());
		
		rFeed = feedDao.getFeedsByCategory("local");
		assertEquals("should be one feed with Local as a category", 1, rFeed.size());

	} 

	@Test
	public void testGetEntriesByTitle() {
		jdbc.execute("delete from feeds"); // clear table
		feedDao.createFeed(feed1);

		Set<AtomFeed> rFeed = feedDao.getFeedsByTitle("something");
		assertEquals("Should be feed1 with that word in it's title", 0, rFeed.size());

		rFeed = feedDao.getFeedsByTitle("title");
		assertEquals("Should be feed1 and its entry's source with 'title' in its title", 2, rFeed.size());
		
		rFeed = feedDao.getFeedsByTitle("feed1");
		assertEquals("Should be feed1 with 'feed1' in its title", 1, rFeed.size());
		feedDao.createFeed(feed2);
		feedDao.createFeed(feed3);
		
		rFeed = feedDao.getFeedsByTitle("title");
		assertEquals("Should be all three feeds and a source with 'title' in their titles", 4, rFeed.size());


	}


}
