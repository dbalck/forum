package com.forum.web.atom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.forum.web.parse.Article;
import com.forum.web.parse.Parser;
import com.forum.web.parse.Stream;
import com.forum.web.parse.StreamType;

@Entity
@Table(name="feeds")
public class AtomFeed implements Stream {
	
	// required fields
	private String title; 
	
	@Column(name="global_feed_id")
	private String globalId; 
	private long updated;
	
	// recommended fields
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="feeds_authors", 
		joinColumns = { @JoinColumn(name = "feed_id")}, 
		inverseJoinColumns= { @JoinColumn(name = "author_id")})
	private Set<Author> authors = new HashSet<Author>();
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="feeds_contributors", 
		joinColumns = { @JoinColumn(name = "feed_id")}, 
		inverseJoinColumns= { @JoinColumn(name = "contributor_id")})
	private Set<Contributor> contributors = new HashSet<Contributor>();

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
	
	// Not CascadeType.MERGE
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "feed")
	private Set<AtomEntry> entries = new HashSet<AtomEntry>();
	
	// id for persistence/hibernate
	@Id
	@Column(name="feed_id")
	@GeneratedValue
	private int id;
	
	private int hash;
		
	public AtomFeed() {}

	public AtomFeed(String title, String globalId, long updated) {
		this.title = title;
		this.globalId = globalId;
		this.updated = updated;
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

	public Set<AtomEntry> getEntries() {
		return entries;
	}

	public void setEntries(Set<AtomEntry> entries) {
		this.entries = entries;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
	
	public void addEntry(AtomEntry entry) {
		entry.setFeed(this);
		getEntries().add(entry);
	}
	
	public void addEntries(Set<AtomEntry> entries) {
		if (entries != null) {
			for (AtomEntry e: entries) {
				addEntry(e);
			}			
		}
	}
	
	public void removeEntry(AtomEntry entry) {
		getEntries().remove(entry);
		entry.setFeed(null);
	}
	
	public void addAuthors(Set<Author> authors) {
		if (authors != null) {
			for (Author a: authors) {
				a.addFeed(this);
			}
			this.authors.addAll(authors);
		}
	}
	
	public void addContributors(Set<Contributor> contributors) {
		for (Contributor c: contributors) {
			c.addFeed(this);
		}
		this.contributors.addAll(contributors);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((generator == null) ? 0 : generator.hashCode());
		result = prime * result
				+ ((globalId == null) ? 0 : globalId.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((logo == null) ? 0 : logo.hashCode());
		result = prime * result + ((rights == null) ? 0 : rights.hashCode());
		result = prime * result
				+ ((subtitle == null) ? 0 : subtitle.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + (int) (updated ^ (updated >>> 32));
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
		AtomFeed other = (AtomFeed) obj;
		if (generator == null) {
			if (other.generator != null)
				return false;
		} else if (!generator.equals(other.generator))
			return false;
		if (globalId == null) {
			if (other.globalId != null)
				return false;
		} else if (!globalId.equals(other.globalId))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (logo == null) {
			if (other.logo != null)
				return false;
		} else if (!logo.equals(other.logo))
			return false;
		if (rights == null) {
			if (other.rights != null)
				return false;
		} else if (!rights.equals(other.rights))
			return false;
		if (subtitle == null) {
			if (other.subtitle != null)
				return false;
		} else if (!subtitle.equals(other.subtitle))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (updated != other.updated)
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
