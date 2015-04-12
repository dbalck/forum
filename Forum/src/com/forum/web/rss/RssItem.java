package com.forum.web.rss;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
	@GeneratedValue
	@Column(name="item_id")
	private int id;
	
	public RssItem() {}
	
	public RssItem(String title, String link, String description) {
		this.title = title;
		this.description = description;
		this.link = link;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	@Override
	public String toString() {
		return "RssItem [title=" + title + ", description=" + description
				+ ", link=" + link + ", enclosure=" + enclosure + ", pubDate="
				+ pubDate + ", category=" + category + ", guid=" + guid
				+ ", source=" + source + ", author=" + author + ", comments="
				+ comments + ", channel=" + channel + ", id=" + id + "]";
	}
	
	

}
