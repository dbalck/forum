package com.forum.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forum.web.atom.AtomFeed;
import com.forum.web.dao.ChannelDao;
import com.forum.web.dao.EntryDao;
import com.forum.web.dao.FeedDao;
import com.forum.web.dao.ItemDao;
import com.forum.web.parse.Article;
import com.forum.web.parse.Stream;
import com.forum.web.parse.StreamType;
import com.forum.web.rss.RssChannel;
import com.forum.web.rss.RssItem;

@Service("streamService")
public class StreamService {
	
	private ItemDao itemDao;
	private ChannelDao channelDao;
	private EntryDao entryDao;
	private FeedDao feedDao;

	@Autowired
	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}
	
	@Autowired
	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}
	
	@Autowired
	public void setEntryDao(EntryDao entryDao) {
		this.entryDao = entryDao;
	}
	
	@Autowired
	public void setFeedDao(FeedDao feedDao) {
		this.feedDao = feedDao;
	}

	// create feed, i.e. create channel and child objects (including items)
	public void createStreams(List<Stream> streams) {
		for (Stream s: streams) {
			// the stream is RSS
			if (s.type() == StreamType.RSS) {
				if (channelDao == null) {
					System.out.println("channelDao is null");
				} else {
					channelDao.createChannel((RssChannel) s); 
				}
				
			// the Stream is Atom
			} else {
				if (feedDao == null) {
					System.out.println("feedDao is null");
				} else {
					feedDao.createFeed((AtomFeed) s); 
				}
			}
		} 
	}
	
	// read channel
	public RssChannel getStream(int channelId) {
		return null;
	}
	
	// update channel
	public void updateStream(RssChannel channel, int channelId) {
		
	}
	
	// delete channel and child objects (including items)
	public void deleteStream(int channelId) {
		
	}
	
	// create item in channel
	public void createArticle(RssItem item, int ChannelId) {
		
	}
	
	// read item in channel
	public List<RssItem> getArticles(int ChannelId) {
		return null;
	}
	
	// delete item in channel
	public void deleteArticle(int itemId, int channelId) {
		
	}
	
	// check whether a Stream exists 
	public boolean exists(Stream stream) {
		return false;
	}
	
	// check whether an article exists
	public boolean exists(Article article) {
		return false;
	}
	

}
