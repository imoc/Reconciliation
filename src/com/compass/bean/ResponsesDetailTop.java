package com.compass.bean;

public class ResponsesDetailTop {

	private String account;
	private String agency;
	private String subject;
	private String currency;
	private String subAccount;
	private String name;
	
	
	public ResponsesDetailTop(String account, String agency, String subject,
			String currency, String subAccount, String name) {
		this.account = account;
		this.agency = agency;
		this.subject = subject;
		this.currency = currency;
		this.subAccount = subAccount;
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getSubAccount() {
		return subAccount;
	}
	public void setSubAccount(String subAccount) {
		this.subAccount = subAccount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "ResponsesDetailTop [account=" + account + ", agency=" + agency
				+ ", subject=" + subject + ", currency=" + currency
				+ ", subAccount=" + subAccount + ", name=" + name + "]";
	}
	
	
}
