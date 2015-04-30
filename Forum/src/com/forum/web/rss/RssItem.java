package com.forum.web.rss;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.forum.web.parse.Article;
import com.forum.web.parse.Parser;

@Entity
@Table(name="items")
@Immutable
public class RssItem implements Article {

	//required attributes
	private String title;
	private String description; 
	private String link; 
	
	// optional attributes
	private long pubDate;
	private String category;
	private String guid;
	private String source;
	private String author;
	private String comments;
			
	@Id
	@GeneratedValue
	@Column(name="item_id")
	private int id;
	
	@Column(name="global_item_id")
	private String globalId;
	
	@ManyToOne
	@JoinColumn(name="channel_id")
	private RssChannel channel;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private Enclosure enclosure;
	
	private int hash;
	
	public RssItem() {}
	
	public RssItem(String title, String link, String description, String guid) {
		this.title = title;
		this.description = description;
		this.link = link;
		
		if (guid != null) {
			globalId = guid;
		} else if (link != null) {
			globalId = link;
		} else if (title != null) {
			globalId = title;
		} else {
			throw new IllegalArgumentException("illegal nulls in RssItem's constructor");
		}
	}
	

	public RssChannel getChannel() {
		return channel;
	}

	public void setChannel(RssChannel channel) {
		this.channel = channel;
	}

	public Enclosure getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(Enclosure enclosure) {
		this.enclosure = enclosure;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public long getPubDate() {
		return pubDate;
	}

	public void setPubDate(long pubDate) {
		this.pubDate = pubDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public void addEnclosure(Enclosure enclosure) {
		enclosure.setItem(this);
		this.enclosure = enclosure;

	}
	
	// unimplemented Article interface methods
	public String date() {
		return Parser.buildDate(pubDate);
	}
	
	public List<String> people() {
		List<String> authors = new ArrayList<String>();
		authors.add(author);
		return authors;
	}
	
	public String description() {
		return getDescription();
	}
	
	public String link() {
		return getLink();
	}
	
	public String title() {
		return getTitle();
	}
	
	public int getHash() {
		return hash;
	}

	public void setHash(int hash) {
		this.hash = hash;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}

	public String getGlobalId() {
		return globalId;
	}

	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}

	@Override
	public int hashCode() {
		int result = 1;
		if (hash == 0) {
			final int prime = 31;
			result = prime * result + ((author == null) ? 0 : author.hashCode());
			result = prime * result
					+ ((category == null) ? 0 : category.hashCode());
			result = prime * result + ((channel == null) ? 0 : channel.hashCode());
			result = prime * result
					+ ((comments == null) ? 0 : comments.hashCode());
			result = prime * result
					+ ((description == null) ? 0 : description.hashCode());
			result = prime * result
					+ ((enclosure == null) ? 0 : enclosure.hashCode());
			result = prime * result
					+ ((globalId == null) ? 0 : globalId.hashCode());
			result = prime * result + ((guid == null) ? 0 : guid.hashCode());
			result = prime * result + hash;
			result = prime * result + ((link == null) ? 0 : link.hashCode());
			result = prime * result + (int) (pubDate ^ (pubDate >>> 32));
			result = prime * result + ((source == null) ? 0 : source.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
			hash = result;
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RssItem other = (RssItem) obj;
		if (globalId == null) {
			if (other.getGlobalId() != null)
				return false;
		} else if (!globalId.equals(other.getGlobalId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RssItem [title=" + title + ", description=" + description
				+ ", link=" + link + ", enclosure=" + enclosure + ", pubDate="
				+ pubDate + ", category=" + category + ", guid=" + guid
				+ ", source=" + source + ", author=" + author + ", comments="
				+ comments + ", channel=" + channel + ", id=" + id + "]";
	}
	
	

}
