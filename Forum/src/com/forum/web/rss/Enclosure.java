package com.forum.web.rss;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="enclosures")
public class Enclosure {
	private int length;
	private String type;
	private String url;
	
	@Id
	@Column(name="enclosure_id")
	private String id;
	private int hash;
	
	public Enclosure() {}
	
	public Enclosure(int length, String type, String url) {
		this.length = length;
		this.type = type;
		this.url = url;
		this.id = generateId(type + url);

	}
		
	public void setLength(int length) {
		this.length = length;
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
		Enclosure other = (Enclosure) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Enclosure [length=" + length + ", type=" + type + ", url="
				+ url + "]";
	}
}
