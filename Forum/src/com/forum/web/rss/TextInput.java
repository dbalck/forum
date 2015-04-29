package com.forum.web.rss;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="textinputs")
public class TextInput {
	
	// all fields required (except id)
	private String title;
	private String link;
	private String description;
	private String name;
	
	@Id
	@Column(name="textinput_id")
	@GeneratedValue
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "textInput")
	private RssChannel channel;
	
	private int hash;
	
	public TextInput() {}
	
	public TextInput(String title, String link, String description, String name) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getHash() {
		return hash;
	}

	public void setHash(int hash) {
		this.hash = hash;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
		
	public RssChannel getChannel() {
		return channel;
	}

	public void setChannel(RssChannel channel) {
		this.channel = channel;
	}

	@Override
	public int hashCode() {
		if (hash == 0) {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((description == null) ? 0 : description.hashCode());
			result = prime * result + ((link == null) ? 0 : link.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		TextInput other = (TextInput) obj;
		if (description == null) {
			if (other.getDescription() != null)
				return false;
		} else if (!description.equals(other.getDescription()))
			return false;
		if (link == null) {
			if (other.getLink() != null)
				return false;
		} else if (!link.equals(other.getLink()))
			return false;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		if (title == null) {
			if (other.getTitle() != null)
				return false;
		} else if (!title.equals(other.getTitle()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TextInput: [title=" + title + ", link=" + link
				+ ", description=" + description + ", name=" + name + "]";
	}
	
	
	
	
}
