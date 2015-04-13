package com.forum.web.rss;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	
	// dependency
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="item_id")
	private Enclosure enclosure;

	// optional attributes
	private long pubDate;
	private String category;
	private String guid;
	private String source;
	private String author;
	private String comments;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="channel_id")
	private RssChannel channel;
		
	@Id
	@Column(name="item_id")
	private String id;
	private int hash;
	
	public RssItem() {}
	
	public RssItem(String title, String link, String description) {
		this.title = title;
		this.description = description;
		this.link = link;
		String id = "";
		if (title != null) {
			id += title;
		}
		if (link != null) {
			id += link;
		}
		this.id = generateId(id);
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

	public void setId(String id) {
		this.id = id;
	}

	private String generateId(String id) {
		UUID uuid = UUID.nameUUIDFromBytes(id.getBytes());
		return uuid.toString();
	}

	@Override
	public int hashCode() {
		if (this.hash == 0) {
			final int prime = 31;
			int result = 1;
			this.hash = prime * result + hash;
		}
		return this.hash;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
