package phd.Nabsim.Models;

import java.util.Date;

public class Notification {
	
	private User sendingUser;
	
	private String sender;
	private Subject subject;
	private String app;
	private String body;
	private String date;
	
	private int senderRank;
	private int subjectRank;
	private int appRank;
	private int bodyRank;
	private int dateRank;
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getSenderRank() {
		return senderRank;
	}
	public void setSenderRank(int senderRank) {
		this.senderRank = senderRank;
	}
	public int getSubjectRank() {
		return subjectRank;
	}
	public void setSubjectRank(int subjectRank) {
		this.subjectRank = subjectRank;
	}
	public int getAppRank() {
		return appRank;
	}
	public void setAppRank(int appRank) {
		this.appRank = appRank;
	}
	public int getBodyRank() {
		return bodyRank;
	}
	public void setBodyRank(int bodyRank) {
		this.bodyRank = bodyRank;
	}
	public int getDateRank() {
		return dateRank;
	}
	public void setDateRank(int dateRank) {
		this.dateRank = dateRank;
	}
	public User getSendingUser() {
		return sendingUser;
	}
	public void setSendingUser(User sendingUser) {
		this.sendingUser = sendingUser;
	}	
	
}
