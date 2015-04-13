package com.forum.web.rss;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	private String id;
	private int hash;
	
	public TextInput() {}
	
	public TextInput(String title, String link, String description, String name) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.name = name;
		this.id = generateId(title + link + description + name);
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

	public String getId() {
		return id;
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
		TextInput other = (TextInput) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "TextInput: [title=" + title + ", link=" + link
				+ ", description=" + description + ", name=" + name + "]";
	}
	
	
	
	
}
