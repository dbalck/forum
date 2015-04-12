package com.forum.web.rss;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="images")
public class Image {
	
	@Id
	@Column(name="image_id")
	@GeneratedValue
	private int id;
	
	// required
	private String title;
	private String link;
	private String url;

	// optional
	private String description;
	private int height;
	private int width;
		
	public Image() {}
	
	public Image(String title, String link, String url) {
		this.title = title;
		this.link = link;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public String toString() {
		return "Image [title=" + title + ", link=" + link + ", url=" + url
				+ ", description=" + description + ", height=" + height
				+ ", width=" + width + "]";
	}
	
}
