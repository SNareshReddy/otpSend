package com.servpro.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OtpUtil {
	
	@Id
	private String mobilenumber;
	private String otp;
	private long ExpiryTime;
	
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public long getExpiryTime() {
		return ExpiryTime;
	}
	public void setExpiryTime(long expiryTime) {
		ExpiryTime = expiryTime;
	}
	
}
