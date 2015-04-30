package com.forum.web.rss;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="enclosures")
public class Enclosure {
	private int length;
	private String type;
	private String url;
	
	@Id
	@Column(name="enclosure_id")
	@GeneratedValue
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "enclosure")
	private RssItem item;

	private int hash;
	
	public Enclosure() {}
	
	public Enclosure(int length, String type, String url) {
		this.length = length;
		this.type = type;
		this.url = url;

	}
		
	public void setLength(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return this.length;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
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

	public RssItem getItem() {
		return item;
	}

	public void setItem(RssItem item) {
		this.item = item;
	}

	@Override
	public int hashCode() {
		if (hash == 0) {
			final int prime = 31;
			int result = 1;
			result = prime * result + length;
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Enclosure other = (Enclosure) obj;
		if (length != other.getLength())
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.getType()))
			return false;
		if (url == null) {
			if (other.getUrl() != null)
				return false;
		} else if (!url.equals(other.getUrl()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Enclosure [length=" + length + ", type=" + type + ", url="
				+ url + "]";
	}
}
