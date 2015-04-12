package com.forum.web.atom;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.forum.web.parse.Article;
import com.forum.web.parse.Parser;
import com.forum.web.parse.Stream;
import com.forum.web.parse.StreamType;

@Entity
@Immutable
@Table(name="feeds")
public class AtomFeed implements Stream {
	
	// required fields
	private String title; 
	
	@Column(name="global_feed_id")
	private String globalId; 
	private long updated;
	
	// recommended fields
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="feed_id")
	private Set<Author> authors;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="feed_id")
	private Set<Contributor> contributors; 

	private String link;
	
	// optional fields
	@ElementCollection
	@CollectionTable(name="categories", joinColumns = @JoinColumn(name="feed_id"))
	@Column(name="category")	
	private List<String> categories;
	
	
	private String generator;
	private String icon;
	private String logo;
	private String rights;
	private String subtitle;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="feed_id")
	private List<AtomEntry> entries;
	
	// id for persistence/hibernate
	@Id
	@Column(name="feed_id")
	private String id;
	private int hash;
		
	public AtomFeed() {}

	public AtomFeed(String title, String globalId, long updated) {
		this.title = title;
		this.globalId = globalId;
		this.updated = updated;
		this.id = generateId(globalId);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGlobalId() {
		return globalId;
	}

	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}

	public long getUpdated() {
		return updated;
	}

	public void setUpdated(long updated) {
		this.updated = updated;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Set<Contributor> getContributors() {
		return contributors;
	}

	public void setContributors(Set<Contributor> contributors) {
		this.contributors = contributors;
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public List<AtomEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<AtomEntry> entries) {
		this.entries = entries;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	// unimplemented Stream interface methods
	public List<String> people() {
		List<String> names = new ArrayList<String>();
		for (Author author: authors) {
			names.add(author.getName());
		}
		return names;
	}
	
	public List<Article> articles() {
		List<Article> articles = new ArrayList<Article>();
		for (AtomEntry entry: entries) {
			articles.add(entry);
		}
		return articles;
	}	
	
	public String date() {
		return Parser.buildDate(updated);
	}
	
	public String title() {
		return this.getTitle();
	}
	
	public String link() {
		return this.getLink();
	}
	
	public StreamType type() {
		return StreamType.ATOM;
	}
	
	public int getHash() {
		return hash;
	}

	public void setHash(int hash) {
		this.hash = hash;
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
			this.hash = prime * result + ((id == null) ? 0 : id.hashCode());
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
		AtomFeed other = (AtomFeed) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AtomFeed [title=" + title + ", globalId=" + globalId + ", updated="
				+ updated + ", authors=" + authors + ", link=" + link
				+ ", categories=" + categories + ", contributors="
				+ contributors + ", generator=" + generator + ", icon=" + icon
				+ ", logo=" + logo + ", rights=" + rights + ", subtitle="
				+ subtitle + ", id=" + id + "]";
	}
	
	

}
