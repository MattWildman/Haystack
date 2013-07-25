package com.haystack.entities;

import java.util.Date;

public class Message extends HaystackEntity {

	private Integer toUser;
	private Integer fromUser;
	private String messageType;
	private Boolean read;
	private Date date;
	
	public Integer getToUser() {
		return toUser;
	}
	
	public void setToUser(Integer toUser) {
		this.toUser = toUser;
	}
	
	public Integer getFromUser() {
		return fromUser;
	}
	
	public void setFromUser(Integer fromUser) {
		this.fromUser = fromUser;
	}
	
	public String getMessageType() {
		return messageType;
	}
	
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	public Boolean getRead() {
		return read;
	}
	
	public void setRead(Boolean read) {
		this.read = read;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
