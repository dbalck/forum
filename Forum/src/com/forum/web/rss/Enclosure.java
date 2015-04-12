package com.forum.web.rss;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
	@GeneratedValue
	private int id;
	
	public Enclosure() {}
	
	public Enclosure(int length, String type, String url) {
		this.length = length;
		this.type = type;
		this.url = url;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public int getId() {
		return id;
	}

	public int getLength() {
		return length;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "Enclosure [length=" + length + ", type=" + type + ", url="
				+ url + "]";
	}
}
