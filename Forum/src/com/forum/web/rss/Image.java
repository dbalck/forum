package com.forum.web.rss;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="images")
public class Image {
	
	@Id
	@Column(name="image_id")
	@GeneratedValue
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "image")
	private RssChannel channel;
	
	// required
	private String title;
	private String link;
	private String url;

	// optional
	private String description;
	private int height;
	private int width;
		
	private int hash;

	public Image() {}
	
	public Image(String title, String link, String url) {
		this.title = title;
		this.link = link;
		this.url = url;
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
			result = prime * result + height;
			result = prime * result + ((link == null) ? 0 : link.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
			result = prime * result + ((url == null) ? 0 : url.hashCode());
			result = prime * result + width;
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
		Image other = (Image) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (height != other.height)
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (width != other.width)
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
