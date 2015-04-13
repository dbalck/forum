package com.forum.web.rss;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="images")
public class Image {
	
	@Id
	@Column(name="image_id")
	private String id;
	private int hash;
	
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
		this.id = generateId(title + link + url);

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
		Image other = (Image) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Image [title=" + title + ", link=" + link + ", url=" + url
				+ ", description=" + description + ", height=" + height
				+ ", width=" + width + "]";
	}
	
}
