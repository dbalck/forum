package com.forum.web.parse;

import java.util.List;

public interface Stream {
	
	// returns whether the stream is RSS or Atom
	public StreamType type();
	
	// returns the title of the Feed
	public String title();
	
	// returns the pubDate (rss) or the last update (atom)
	public String date(); 
	
	// returns the managing editor (rss) or the author (atom)
	public List<String> people();
	
	// returns the link of the channel/feed
	public String link();
	
	// returns the items/entries that constitute this feed/channel
	public List<Article> articles();
}
