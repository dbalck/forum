package com.forum.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.forum.web.parse.Stream;
import com.forum.web.parse.WebCrawler;
import com.forum.web.service.StreamService;

@ActiveProfiles("dev")
@ContextConfiguration(locations={"classpath:com/forum/web/config/dao-context.xml", 
		"classpath:com/forum/web/test/config/datasource.xml",
		"classpath:com/forum/web/config/dao-context.xml"})
public class RssController {
	public static final String NYT_RSS = "http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml";
	public static final String TECH_REPUB_RSS = "http://techrepublic.com.feedsportal.com/c/35463/f/670841/index.rss";
	public static final String WSJ_RSS = "http://www.wsj.com/xml/rss/3_7041.xml";
	public static final String VOX_ATOM = "http://www.vox.com/rss/index.xml";
	public static final String GIZ_RSS = "http://gizmodo.com/index.xml";
	
	private static RssController singleton;

	
	private RssController() {}
	
	public static RssController getInstance() {
		if (singleton == null) {
			singleton = new RssController();
		} 
		return singleton;
		
	}
}
