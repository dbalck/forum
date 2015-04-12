package com.forum.web.rss;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
	
	@GeneratedValue
	@Id
	@Column(name="textinput_id")
	private int id;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "TextInput: [title=" + title + ", link=" + link
				+ ", description=" + description + ", name=" + name + "]";
	}
	
	
	
	
}
