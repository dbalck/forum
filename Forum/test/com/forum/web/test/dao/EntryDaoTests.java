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
import com.forum.web.dao.EntryDao;
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
	private Set<AtomEntry> entries1;
	private Set<AtomEntry> entries2;
	private AtomEntry entry1;
	private AtomEntry entry2;
	private AtomEntry entry3;
	private long date1;
	private long date2;
	private Author author1;
	private Author author2;
	private Author author3;
	private Author author4;	
	private Author author5;
	private Set<Author> authors1;
	private Set<Author> authors2;
	private Set<Author> authors3;

	private Set<Contributor> contributors;
	private List<String> categories1;
	private List<String> categories2;

	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private EntryDao entryDao;

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
		author1.setEmail("george@example.com");
		author1.setUri("georgeswebsite.com");
		author2 = new Author("bill");
		author2.setEmail("bill@example.com");
		author2.setUri("billswebsite.com");
		authors1 = new HashSet<Author>();
		authors1.add(author1);
		authors1.add(author2);
		author3 = new Author("Dan");
		author3.setEmail("dan@example.com");
		author4 = new Author("Daniel");
		author4.setEmail("daniel@example.com");
		author5 = new Author("Danny");
		author5.setEmail("danny@example.com");
		authors2 = new HashSet<Author>();
		authors2.add(author3);
		authors2.add(author4);
		authors3 = new HashSet<Author>();
		authors3.add(author5);
		
		categories1 = new ArrayList<String>();
		categories1.add("Sports");
		categories1.add("International");
		categories1.add("Politics");
		
		categories2 = new ArrayList<String>();
		categories2.add("Sports");
		categories2.add("Local");
		categories2.add("Opinion");
		
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
		feed1.addAuthors(authors1);
		feed1.setCategories(categories1);
		feed1.setContributors(contributors);
		feed2 = new AtomFeed("Title of feed2", "google.com/feed2b", date2);
		feed3 = new AtomFeed("Title of feed3", "google.com/feed3", date2);
		feed3.addAuthors(authors2);
		feed3.setCategories(categories2);
		
		entries1 = new HashSet<AtomEntry>();
		entries2 = new HashSet<AtomEntry>();
		entry1 = new AtomEntry("Entry Title 1", "entry1111.com/entry1", date1);
		entry1.addAuthors(authors1);
		entry1.setCategories(categories1);
		entry1.setContributors(contributors);
		entry1.setContent("This is the content blah blah blah");
		entry1.setLink("somelinksomewhere.com");
		entry1.setRights("These are the rights!");
		entry1.setPublished(date1);
		entry1.setSource(new AtomFeed("source1 title", "source1.com/uniqueid", date1));
		entry1.setSummary("This is the summary of entry1");

		entry2 = new AtomEntry("Entry Title 2: The Second Coming", "entry2222.com/entry2", date2);
		entry2.addAuthors(authors2);
		entry2.setCategories(categories2);
		entry2.setContributors(contributors);
		entry2.setContent("This is the content of the second entry blah blah blah");
		entry2.setLink("somelinksomewhere.com");
		entry2.setRights("These are the rights!");
		entry2.setPublished(date2);
		entry2.setSource(new AtomFeed("source2 title", "source2.com/uniqueid", date2));
		entry2.setSummary("This is the summary of entry2");

		entry3 = new AtomEntry("Entry Title 3: The Treble Coming", "entry333.com/entry3", date2);
		entry3.addAuthors(authors3);
		entry3.setCategories(categories2);
		entry3.setContributors(contributors);
		entry3.setContent("This is the content of the third entry blah blah blah");
		entry3.setLink("somelinksomewhere.com");
		entry3.setRights("These are the rights!");
		entry3.setPublished(date2);
		entry3.setSource(new AtomFeed("source3 title", "source3.com/uniqueid", date2));
		entry3.setSummary("This is the summary of entry3");

		
		entries1.add(entry1);
		entries1.add(entry2);
		entries2.add(entry3);
		
		feed1.addEntries(entries1);
		feed2.addEntries(entries2);


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
		jdbc.execute("delete from entries"); // clear table

		feedDao.createFeed(feed1); // creates two entries
		assertTrue("There should be entry1 and entry2 in the db", entryDao.exists(entry1));
		assertTrue("There should be entry1 and entry2 in the db", entryDao.exists(entry2));
		feedDao.createFeed(feed2); // has one entry (entry3)
		assertTrue("There should be entry3 in the db", entryDao.exists(entry3));
		assertTrue("There should be entry1 in the db still", entryDao.exists(entry1));

	}

	@Test
	public void testgetAllEntries() {
		jdbc.execute("delete from feeds"); // clear table
		jdbc.execute("delete from entries"); // clear table
		
		// check empty set
		Set<AtomEntry> rEntries = entryDao.getAllEntries();
		assertEquals("There should be no entries yet", 0, rEntries.size());
		
		// check for one insert
		feedDao.createFeed(feed2); // one entry (entry3)
		rEntries = entryDao.getAllEntries();
		assertEquals("There should be only be one entry", 1, rEntries.size());
		
		// check for two more inserts
		feedDao.createFeed(feed1);
		rEntries = entryDao.getAllEntries();
		assertEquals("There should be three entries", 3, rEntries.size());
		
		// check for duplicate insert
		feedDao.createFeed(feed2);
		rEntries = entryDao.getAllEntries();
		assertEquals("There should still be three entries", 3, rEntries.size());

	}

	@Test
	public void testGetEntryById() {
		AtomEntry rEntry = entryDao.getEntryById(entry1.getGlobalId());
		assertEquals("There are no db entries, should be 0 matches", null, rEntry);
		
		feedDao.createFeed(feed1); // creates two entries (1, 2)
		
		// there should be entry1 in there (it was part of feed1)
		String entryId = entry1.getGlobalId();
		rEntry = entryDao.getEntryById(entryId);
		assertEquals("Should be entry1's id in the database", "Entry Title 1", rEntry.getTitle());
		
		// there should also be entry2 in there (it was part of feed1)
		entryId = entry2.getGlobalId();
		rEntry = entryDao.getEntryById(entryId);
		assertEquals("Should be entry2's id in the database", "Entry Title 2: The Second Coming", rEntry.getTitle());
	}

	@Test
	public void testGetEntriesByAuthorName() {
		Set<AtomEntry> rEntries = entryDao.getEntriesByAuthorName("george");
		assertEquals("there should be no entries cited by george", 0, rEntries.size());

		feedDao.createFeed(feed1); // has author1 and author 2 (george and bill)
		rEntries = entryDao.getEntriesByAuthorName("george");
		assertEquals("Should only be one entry in the list", 1, rEntries.size());
		rEntries = entryDao.getEntriesByAuthorName("bill");
		assertEquals("Should only be one feed in the list", 1, rEntries.size());
		
		// partial and multiple match test
		feedDao.createFeed(feed2); // feed2 has author5 (danny) in  entry3
		rEntries = entryDao.getEntriesByAuthorName("Dan");
		assertEquals("Entry2 cites both dan as an author (dan and daniel), and entry3 cites danny", 2, rEntries.size());
		
		// should match partially too (to bill)
		rEntries = entryDao.getEntriesByAuthorName("il"); 
		assertEquals("two feeds cite bill as an author", 1, rEntries.size());

		feedDao.createFeed(feed3); // has author1 and author 2  again (bill and george)
		// another partial match test
		rEntries = entryDao.getEntriesByAuthorName("geo"); 
		assertEquals("one entry (despite multiple inserts) cites geo as an author", 1, rEntries.size());

		// a negative test
		rEntries = entryDao.getEntriesByAuthorName("pete"); 
		assertEquals("no feeds cite pete as an author", 0, rEntries.size());

	}

	@Test
	public void testGetEntriesByCategory() {
		jdbc.execute("delete from feeds"); // clear table
		jdbc.execute("delete from categories"); // clear table
		jdbc.execute("delete from entries"); // clear table

		feedDao.createFeed(feed1); // entry1 and entry2 both have three categories, but none are 'claptrap'
		Set<AtomEntry> rEntries = entryDao.getEntriesByCategory("claptrap");
		assertEquals("As there are no categories called claptrap", 0, rEntries.size());
		
		// two entries have the same sports category
		rEntries = entryDao.getEntriesByCategory("Sports");
		assertEquals("Should be two entries with a sports category", 2, rEntries.size());

		// check for case insensitive match
		rEntries = entryDao.getEntriesByCategory("politICS");
		assertEquals("Should be one field with a politics category", 1, rEntries.size());
				
		rEntries = entryDao.getEntriesByCategory("local");
		assertEquals("should be one feed with Local as a category", 1, rEntries.size());

	} 

	@Test
	public void testGetEntriesByTitle() {		
		feedDao.createFeed(feed1);

		// no match test, 'something' doesn't exist in any entry title
		Set<AtomEntry> rEntries = entryDao.getEntriesByTitle("something");
		assertEquals("There should be no entries with 'something' in its title", 0, rEntries.size());

		rEntries = entryDao.getEntriesByTitle("title");
		assertEquals("Both entries 1 and 2 should have the word title in their title", 2, rEntries.size());
		
		rEntries = entryDao.getEntriesByTitle("Title 1");
		assertEquals("Should be entry1 with 'Title 1' in its title", 1, rEntries.size());
		feedDao.createFeed(feed2);
		
		rEntries = entryDao.getEntriesByTitle("title");
		assertEquals("Should be all three entries with 'title' in their titles", 3, rEntries.size());


	}
	
	@Test
	public void testCreateEntry() {
		// null out feed1's entries and check against null set
		feed1.setEntries(null);
		feedDao.createFeed(feed1);
		Set<AtomEntry> rEntries = entryDao.getEntriesFromFeed(feed1);
		assertEquals("Empty set test: Should have zero entries", 0, rEntries.size());
		
		// check against empty set
		feed1.setEntries(new HashSet<AtomEntry>());
		feedDao.createFeed(feed1);
		rEntries = entryDao.getEntriesFromFeed(feed1);
		assertEquals("Empty set test: Should have zero entries", 0, rEntries.size());
		
		// Add an entry to feed1 and check
		entryDao.createEntry(entry1, feed1);
		rEntries = entryDao.getEntriesFromFeed(feed1);
		assertEquals("Should have a single entry", 1, rEntries.size());
		
		// add entry1 again to check that it won't add a duplicate
		entryDao.createEntry(entry1, feed1);
		rEntries = entryDao.getEntriesFromFeed(feed1);
		assertEquals("Should have a single entry", 1, rEntries.size());

		//  add a couple more
		entryDao.createEntry(entry2, feed1);
		entryDao.createEntry(entry3, feed1);
		rEntries = entryDao.getEntriesFromFeed(feed1);
		assertEquals("Should have 3 entries", 3, rEntries.size());

	}

	
	@Test
	public void testAddEntriesToFeed() {
		
		// check that there are zero entries in testFeed
		feedDao.createFeed(feed1);
		Set<AtomEntry> rEntries = entryDao.getEntriesFromFeed(feed1);
		assertEquals("Should have two entries", 2, rEntries.size());
		
		//  add one entry (entries2 has a set of one) to feed1 and check it's entries have increased
		entryDao.addEntiresToFeed(entries2, feed1);
		rEntries = entryDao.getEntriesFromFeed(feed1);
		assertEquals("Should have 3 entries", 3, rEntries.size());

		// now check that it won't add duplicates (entries1 was a set inside feed1 when it was created)
		entryDao.addEntiresToFeed(entries1, feed1);
		rEntries = entryDao.getEntriesFromFeed(feed1);
		assertEquals("Should not have added duplicates to testFeed", 3, rEntries.size());

	}

	@Test
	public void testGetEntriesFromFeed() {		
		feedDao.createFeed(feed1);
		Set<AtomEntry> rEntries = entryDao.getAllEntries();
		assertEquals("Should have only two entries", 2, rEntries.size());
		
		rEntries = entryDao.getEntriesFromFeed(feed1);
		assertEquals("Should have retrieved two entries", 2, rEntries.size());
		
		// add a couple more entries via feed2, check again
		feedDao.createFeed(feed2);
		rEntries = entryDao.getAllEntries();
		assertEquals("Should have three entries total from two feeds", 3, rEntries.size());

		// but still only two from feed1
		rEntries = entryDao.getEntriesFromFeed(feed1);
		assertEquals("Should have retrieved two entries", 2, rEntries.size());
		
		// and only one from feed2
		rEntries = entryDao.getEntriesFromFeed(feed2);
		assertEquals("Should have retrieved one entry", 1, rEntries.size());


	}

	
}
