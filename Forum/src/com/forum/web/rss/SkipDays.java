package com.forum.web.rss;

import java.util.List;

public class SkipDays {

	private int id;
	
	private RssChannel channel;
	
	private int hash;
	
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;
	
	public SkipDays() {}
	
	public SkipDays(List<String> days) {
		for (String str: days) {
			switch (str) {
			case "monday":
				monday = true;
				break;
			case "tuesday":
				tuesday = true;
				break;
			case "wednesday":
				wednesday = true;
				break;
			case "thursday":
				thursday = true;
				break;
			case "friday":
				friday = true;
				break;
			case "saturday":
				saturday = true;
				break;
			case "sunday":
				sunday = true;
				break;
			}
		}
	}

	public boolean isMonday() {
		return monday;
	}

	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	public boolean isTuesday() {
		return tuesday;
	}

	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	public boolean isWednesday() {
		return wednesday;
	}

	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	public boolean isThursday() {
		return thursday;
	}

	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	public boolean isFriday() {
		return friday;
	}

	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	public boolean isSaturday() {
		return saturday;
	}

	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}

	public boolean isSunday() {
		return sunday;
	}

	public void setSunday(boolean sunday) {
		this.sunday = sunday;
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
	

	
}
