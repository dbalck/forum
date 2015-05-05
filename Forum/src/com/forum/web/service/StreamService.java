package com.forum.web.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forum.web.atom.AtomEntry;
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

	// create stream, i.e. create channel and child objects (including items)
	public void createStream(Stream s) { 
		// the stream is ATOM
		if (s.type() == StreamType.ATOM) {
			AtomFeed feed = (AtomFeed) s;
			AtomFeed pFeed = feedDao.getFeedById(feed.getGlobalId());
			if (pFeed == null) {
				// new feed, we can simple push the entire atom hierarchy into the db
				feedDao.createFeed(feed);
			} else {
				// existing feed, check if it has the same fields (logically equal) as our persistent one
				if (!feed.equals(pFeed)) {
					
					// save the new entries to a separate place
					Set<AtomEntry> entries = feed.getEntries();
					
					// add the new entries to the old feed
					entryDao.addEntiresToFeed(entries, pFeed);

					// now get a lazy reference to these combined entries and set it to the new feed
					entries = pFeed.getEntries();
					feed.setContributors(pFeed.getContributors());
					feed.setCategories(pFeed.getCategories());
					feed.setEntries(entries);
					feed.setAuthors(pFeed.getAuthors());
					
					// merges (ie replaces) all fields in the old feed
					feedDao.mergeFeed(feed, pFeed);
					
					// then add (rather than replace) the entries here, which persists them
					// entryDao.addEntiresToFeed(entries, pFeed);
	
				}
				// otherwise we don't need to update, it's exactly the same as the last time we pulled it
			}
		// the Stream is Atom
		} else {
			RssChannel channel = (RssChannel) s;
			RssChannel pChannel = channelDao.getChannelById(channel.getLink());
			if (pChannel == null) {
				// there was no preexisting channel with that link, create a new one
				channelDao.createChannel(channel);
			} else {
				// since RSS channels don't require a field that specifies when they were last
				// updated, we have to just update each time
				
				// save the new items separately for adding later
				Set<RssItem> items = channel.getItems();

				// add the new items to the old channel
				itemDao.addItemsToChannel(items, pChannel);

				// now get a lazy reference to these combined items and set it to the new feed
				items = pChannel.getItems();
				channel.setItems(items);
				channel.setImage(pChannel.getImage());
				channel.setTextInput(pChannel.getTextInput());

				// merges (ie replaces) all fields in the old feed
				channelDao.mergeChannel(channel, pChannel);
				
				// then add (rather than replace) the entries here, which persists them
				// itemDao.addItemsToChannel(items, pChannel);
				
			}
		}

	}

	
	public void createStreams(List<Stream> streams) {
		for (Stream s: streams) {
			createStream(s);
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
