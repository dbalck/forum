package com.forum.web.atom;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="contributors")
public class Contributor {

	private String name;
	private String email;
	private String uri;
	
	@Id
	@GeneratedValue
	@Column(name="contributor_id")
	private String id;
	
	@ManyToMany(mappedBy = "contributors")
	private Set<AtomFeed> feeds = new HashSet<AtomFeed>();
	
	@ManyToMany(mappedBy = "contributors")
	private Set<AtomEntry> entries = new HashSet<AtomEntry>();
	
	public Contributor() {}
	
	public Contributor(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Set<AtomFeed> getFeeds() {
		return feeds;
	}

	public void setFeeds(Set<AtomFeed> feeds) {
		this.feeds = feeds;
	}

	public Set<AtomEntry> getEntries() {
		return entries;
	}

	public void setEntries(Set<AtomEntry> entries) {
		this.entries = entries;
	}

	public void addFeed(AtomFeed feed) {
		this.feeds.add(feed);
	}

	public void addEntry(AtomEntry entry) {
		this.entries.add(entry);
		
	}

	
	
}
