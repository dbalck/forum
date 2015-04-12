package com.forum.web.parse;

import java.util.List;

public interface Article {
	
	// returns the identifying line of the item/entry
	public String title();
	
	// returns the brief description or summary that goes under the title
	public String description();
	
	// returns when it was last updated or published (rss/atom)
	public String date();
	
	// returns the link to the actual article/content the item/entry is describing
	public String link();
	
	// byline of whoever wrote the original article
	public List<String> people();
}
