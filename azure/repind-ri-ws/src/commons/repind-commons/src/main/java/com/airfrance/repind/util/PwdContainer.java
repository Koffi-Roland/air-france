package com.airfrance.repind.util;


public class PwdContainer {
	
	private String CryptedPwd;
	private String notCryptedPwd;
	
	public PwdContainer() {
		super();
		CryptedPwd = "";
		this.notCryptedPwd = "";
	}

	public PwdContainer(String cryptedPwd, String notCryptedPwd) {
		super();
		CryptedPwd = cryptedPwd;
		this.notCryptedPwd = notCryptedPwd;
	}
	
	public String getCryptedPwd() {
		return CryptedPwd;
	}
	public void setCryptedPwd(String cryptedPwd) {
		CryptedPwd = cryptedPwd;
	}
	public String getNotCryptedPwd() {
		return notCryptedPwd;
	}
	public void setNotCryptedPwd(String notCryptedPwd) {
		this.notCryptedPwd = notCryptedPwd;
	}
	
}
