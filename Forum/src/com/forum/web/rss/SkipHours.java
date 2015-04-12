package com.forum.web.rss;

import java.util.List;

public class SkipHours {
	private List<Integer> hours;
	private int id;
	
	public SkipHours() {}
	
	public SkipHours(List<Integer> hours) {
		this.hours = hours;
	}	
	
	public List<Integer> getHours() {
		return hours;
	}

	public void setHours(List<Integer> hours) {
		this.hours = hours;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return hours.toString();
	}
	
	
	
}
