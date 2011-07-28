package com.objects;

import java.util.Arrays;
import java.util.List;

public class UserObject {
	/* store user info on get auth to display*/
	private String fname;
	
	private String lname;
	private String phone;
	private String email;
	private List<String> history;
	
	public UserObject(){}
	
	public UserObject(String result){
		List<String> fields = Arrays.asList(result.split("|"));
		this.fname=fields.get(0);
		this.lname=fields.get(1);
		this.email=fields.get(2);
		this.phone = fields.get(3);
		String historyString = fields.get(4);
		this.history= Arrays.asList(historyString.split("&"));
	}
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getHistory() {
		return history;
	}
	public void setHistory(List<String> history) {
		this.history = history;
	}
	
}
