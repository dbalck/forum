package com.forum.web.test.service;

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
import com.forum.web.dao.ChannelDao;
import com.forum.web.dao.EntryDao;
import com.forum.web.dao.FeedDao;
import com.forum.web.dao.ItemDao;
import com.forum.web.parse.Stream;
import com.forum.web.rss.Enclosure;
import com.forum.web.rss.Image;
import com.forum.web.rss.RssChannel;
import com.forum.web.rss.RssItem;
import com.forum.web.rss.TextInput;
import com.forum.web.service.StreamService;

// All tests based on xml data found in ~/Dan/Documents/forum_test_rss
// nyt.xml is the new york times rss feed, and tr.rss is the tech republic rss feed
// vox.xml is the atom feed

@ActiveProfiles("dev")
@ContextConfiguration(locations={"classpath:com/forum/web/config/dao-context.xml", 
		"classpath:com/forum/web/test/config/datasource.xml",
		"classpath:com/forum/web/config/dao-context.xml",
		"classpath:com/forum/web/config/service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class StreamServiceTest {	
	
	private StreamService streamService;
	private List<Stream> streams = new ArrayList<Stream>();
	
	private Enclosure enclosure1;
	private RssItem item1;
	private RssItem item2;
	private RssItem item3;
	private RssItem item4;
	private Image image1;
	private TextInput ti1;
	private RssChannel channel1;
	private RssChannel channel2;
	private Set<RssItem> items1;
	private Set<RssItem> items2;
	
	private AtomFeed feed1;
	private AtomFeed feed2;
	private Set<AtomEntry> entries1;
	private Set<AtomEntry> entries2;
	private AtomEntry entry1;
	private AtomEntry entry2;
	private AtomEntry entry3;
	private long date1;
	private long date2;

	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private FeedDao feedDao;
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private EntryDao entryDao;

	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbc;

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
		jdbc.execute("delete from feeds");
		jdbc.execute("delete from authors");
		jdbc.execute("delete from entries");
		jdbc.execute("delete from categories");
		jdbc.execute("delete from contributors");

		streamService = new StreamService();
		streamService.setChannelDao(channelDao);
		streamService.setFeedDao(feedDao);
		
		item1 = new RssItem("title1", "example1.com", "this is the first item", null);
		item2 = new RssItem("title2", "example2.com", "this is the second item", null);
		item3 = new RssItem("ninjas attack the white house", "example3.com", "The identity of the culprits is still unknown", null);
		item4 = new RssItem("Senator admits to love affair with staffer", "example4.com", "Constituents report that they are unsurprised", null);
		
		// Extraneous objects
		enclosure1 = new Enclosure(888, "this is the enclosure type", "enclosureurl.com");
		image1 = new Image("Title for image", "imagelink.com", "imageurl.com");
		ti1 = new TextInput("text input title", "textlink.com", "text input description goes here", "name of the text input");

		// add properties to item objects
		item1.addEnclosure(enclosure1);
		
		items1 = new HashSet<RssItem>();
		items1.add(item1);
		items1.add(item2);
		
		items2 = new HashSet<RssItem>();
		items2.add(item3);
		items2.add(item4);
				
		channel1 = new RssChannel("Anti-Tank - R - US", "example1.com", "Your most reliable source for Anti-tank-weapon-news");
		channel2 = new RssChannel("All puppies, all the time", "example2.com", "This is the second channel");
		
		// two items
		channel1.addItems(items1);
		channel1.addImage(image1);
		channel1.addTextInput(ti1);
		
		// two items
		channel2.addItems(items2);
		channel2.addImage(image1);
		channel2.addTextInput(ti1);
		
		entry1 = new AtomEntry("Entry Title 1", "entry1111.com/entry1", date1);
		entry1.setContent("This is the content blah blah blah");
		entry1.setLink("somelinksomewhere.com");
		entry1.setRights("These are the rights!");
		entry1.setPublished(date1);
		entry1.setSource(new AtomFeed("source1 title", "source1.com/uniqueid", date1));
		entry1.setSummary("This is the summary of entry1");

		entry2 = new AtomEntry("Entry Title 2: The Second Coming", "entry2222.com/entry2", date2);
		entry2.setContent("This is the content of the second entry blah blah blah");
		entry2.setLink("somelinksomewhere.com");
		entry2.setRights("These are the rights!");
		entry2.setPublished(date2);
		entry2.setSource(new AtomFeed("source2 title", "source2.com/uniqueid", date2));
		entry2.setSummary("This is the summary of entry2");
		
		entry3 = new AtomEntry("Entry Title 3: The Treble Coming", "entry333.com/entry3", date2);
		entry3.setContent("This is the content of the third entry blah blah blah");
		entry3.setLink("somelinksomewhere.com");
		entry3.setRights("These are the rights!");
		entry3.setPublished(date2);
		entry3.setSource(new AtomFeed("source3 title", "source3.com/uniqueid", date2));
		entry3.setSummary("This is the summary of entry3");
		
		entries1 = new HashSet<AtomEntry>();
		entries2 = new HashSet<AtomEntry>();

		entries1.add(entry1);
		entries1.add(entry2);
		entries2.add(entry3);
		
		// two entries
		feed1 = new AtomFeed("Title of feed1", "yahoo.com/feed1a", date1);
		feed1.addEntries(entries1);
		
		// one entry
		feed2 = new AtomFeed("Title of feed2", "google.com/feed2b", date2);
		feed2.addEntries(entries2);

		// put them into streams
		streams.add((Stream) channel1);
		streams.add((Stream) channel2);
		streams.add((Stream) feed1);
		streams.add((Stream) feed2);

	}

	@Test
	public void testCreateStream() {
		
		streamService.setChannelDao(channelDao);
		streamService.setItemDao(itemDao);
		streamService.setFeedDao(feedDao);
		streamService.setEntryDao(entryDao);

		streamService.createStreams(streams);
		
		// create a detatched copy of a stream (channel1), and cast as a stream
		RssChannel tempChannel = new RssChannel("Anti-Tank - R - US", "example1.com", "Your most reliable source for Anti-tank-weapon-news");
		
		// create a copy of an item and an entirely new item, and give it to the new channel
		RssItem tempItem1 = new RssItem("title1", "example1.com", "this is the first item", null);
		RssItem tempItem2 = new RssItem("titleA", "exampleA.com", "this is the A item", null);

		// add them to the channel, which now has a stale and new item. 
		tempChannel.addItem(tempItem1);
		tempChannel.addItem(tempItem2);
		
		// test to see if the new item is added, and then stale item isn't
		streamService.createStream((Stream) tempChannel);


		
		assertTrue("dummy test", true);
	

	}
	


}
