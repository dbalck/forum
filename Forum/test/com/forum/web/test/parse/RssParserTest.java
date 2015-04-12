package com.forum.web.test.parse;

import java.util.ArrayList;
import java.util.List;

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
import com.forum.web.dao.FeedDao;
import com.forum.web.parse.Article;
import com.forum.web.parse.Stream;
import com.forum.web.parse.WebCrawler;
import com.forum.web.service.StreamService;

// All tests based on xml data found in ~/Dan/Documents/forum_test_rss
// nyt.xml is the new york times rss feed, and tr.rss is the tech republic rss feed

@ActiveProfiles("dev")
@ContextConfiguration(locations={"classpath:com/forum/web/config/dao-context.xml", 
		"classpath:com/forum/web/test/config/datasource.xml",
		"classpath:com/forum/web/config/dao-context.xml",
		"classpath:com/forum/web/config/service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RssParserTest {
	
	private static final String NYT_LOCAL = "http://localhost:8080/nyt.xml";
	private static final String TECH_REPUB_LOCAL = "http://localhost:8080/tr.rss";
	private static final String VOX_LOCAL = "http://localhost:8080/vox.xml";

	private List<String> rssLinks;
	private List<String> atomLinks;
	private StreamService streamService;
	private WebCrawler crawler;
	
	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private FeedDao feedDao;
	
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

		rssLinks = new ArrayList<String>();
		rssLinks.add(NYT_LOCAL);
		rssLinks.add(TECH_REPUB_LOCAL);
		atomLinks = new ArrayList<String>();
		atomLinks.add(VOX_LOCAL);
		
		streamService = new StreamService();
		crawler = new WebCrawler(rssLinks, atomLinks);
		
	}

	
	@Test
	public void testParseLinks() {
		List<Stream> streams = crawler.parseLinks();
		for (Stream s: streams) {
			System.out.println("++++++++++++++++++++++++++++++++++++ " + s.title() + " ++++++++++++++++++++++++++++++++++++");
			List<Article> articles = s.articles();
			for (Article a: articles) {
				System.out.println(a.title());
			}
		}
		streamService.setChannelDao(channelDao);
		streamService.setFeedDao(feedDao);
		streamService.createStreams(streams);
				
	}

}
