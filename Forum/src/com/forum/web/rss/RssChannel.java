package com.forum.web.rss;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.forum.web.parse.Article;
import com.forum.web.parse.Parser;
import com.forum.web.parse.Stream;
import com.forum.web.parse.StreamType;

@Entity
@Immutable
@Table(name="channels")
public class RssChannel implements Stream {
	
	// required fields
	private String title; 
	private String link; 
	private String description;
		
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "channel")
	private Set<RssItem> items = new HashSet<RssItem>();
	
	// private List<Integer> skipHours;
	@OneToOne
	@JoinColumn(name="channel_id")
	private Image image;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private SkipDays skipDays;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private TextInput textInput;


	// id for persistence/hibernate
	@Id
	@Column(name="channel_id")
	@GeneratedValue
	private int id;
	private int hash;
	
	private long pubDate;
	private long lastBuildDate;
	private String language;
	private String category;
	private String cloud;
	private String copyright;
	private String docs;
	private String generator;
	private String managingEditor;
	private String rating;
	private int ttl;
	private String webMaster;
	
	public RssChannel() {}

	public RssChannel(String title, String link, String description) {
		this.title = title;
		this.link = link;
		this.description = description;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<RssItem> getItems() {
		return items;
	}

	public void setItems(Set<RssItem> items) {
		this.items = items;
	}

//	public List<Integer> getSkipHours() {
//		return skipHours;
//	}
//
//	public void setSkipHours(List<Integer> skipHours) {
//		this.skipHours = skipHours;
//	}

	public SkipDays getSkipDays() {
		return skipDays;
	}

	public void setSkipDays(SkipDays skipDays) {
		this.skipDays = skipDays;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public TextInput getTextInput() {
		return textInput;
	}

	public void setTextInput(TextInput textInput) {
		this.textInput = textInput;
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

	public long getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(long lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCloud() {
		return cloud;
	}

	public void setCloud(String cloud) {
		this.cloud = cloud;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getDocs() {
		return docs;
	}

	public void setDocs(String docs) {
		this.docs = docs;
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public String getManagingEditor() {
		return managingEditor;
	}

	public void setManagingEditor(String managingEditor) {
		this.managingEditor = managingEditor;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Integer getTtl() {
		return ttl;
	}

	public void setTtl(Integer ttl) {
		this.ttl = ttl;
	}

	public String getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(String webMaster) {
		this.webMaster = webMaster;
	}
	
	public void addItem(RssItem item) {
		item.setChannel(this);
		getItems().add(item);
	}
	
	public void addItems(Set<RssItem> items) {
		if (items != null) {
			for (RssItem item: items) {
				addItem(item);
			}			
		}
	}

	
	// unimplemented Article interface methods
	public String date() {
		return Parser.buildDate(getPubDate());
	}
	
	public List<String> people() {
		List<String> authors1 = new ArrayList<String>();
		authors1.add(managingEditor);
		authors1.add(webMaster);
		return authors1;
	}
	
	public List<Article> articles() {
		List<Article> articles = new ArrayList<Article>();
		for (RssItem item: items) {
			articles.add(item);
		}
		return articles;
	}
	
	public String link() {
		return this.getLink();
	}
	
	public String title() {
		return this.getTitle();
	}
	
	public StreamType type() {
		return StreamType.RSS;
	}
	
	public int getHash() {
		return hash;
	}

	public void setHash(int hash) {
		this.hash = hash;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	
	public void addTextInput(TextInput ti) {
		ti.setChannel(this);
		this.textInput = ti;
	}
	
	public void addImage(Image image) {
		image.setChannel(this);
		this.image = image;
	}

	public void addSkipDays(SkipDays days) {
		days.setChannel(this);
		this.skipDays = days;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RssChannel other = (RssChannel) obj;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.getLink()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RssChannel [title=" + title + ", link=" + link
				+ ", description=" + description + ", items=" + items
				+ ", skipDays=" + skipDays + ", image=" + image
				+ ", textInput=" + textInput + ", id=" + id + ", pubDate="
				+ pubDate + ", lastBuildDate=" + lastBuildDate + ", language="
				+ language + ", category=" + category + ", cloud=" + cloud
				+ ", copyright=" + copyright + ", docs=" + docs
				+ ", generator=" + generator + ", managingEditor="
				+ managingEditor + ", rating=" + rating + ", ttl=" + ttl
				+ ", webMaster=" + webMaster + "]";
	}	
	

}
