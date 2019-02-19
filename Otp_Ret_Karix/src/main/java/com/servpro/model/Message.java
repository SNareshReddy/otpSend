package com.servpro.model;

public class Message {

	
	private String message;
	private String type;
	
	
	
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Message(String message, String type) {
		super();
		this.message = message;
		this.type = type;
	}


	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "Message [message=" + message + ", type=" + type + "]";
	}
	
	
	
	
}
