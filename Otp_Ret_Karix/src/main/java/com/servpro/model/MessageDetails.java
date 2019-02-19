package com.servpro.model;

import java.util.Date;

public class MessageDetails {
		
	private Long ID;
	private Long Statuscode;
	private String Info;
	private Date Time;
	
	

	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public Long getStatuscode() {
		return Statuscode;
	}
	public void setStatuscode(Long statuscode) {
		Statuscode = statuscode;
	}
	public String getInfo() {
		return Info;
	}
	public void setInfo(String info) {
		Info = info;
	}
	public Date getTime() {
		return Time;
	}
	public void setTime(Date time) {
		Time = time;
	}
	
	@Override
	public String toString() {
		return "MessageDetails [ID=" + ID + ", Statuscode=" + Statuscode + ", Info=" + Info + ", Time=" + Time + "]";
	}
	
	
	
}
