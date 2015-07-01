/**
 * 
 */
package com.bank.psh.beans;

/**
 * @author user
 *
 */
public class UserBean {
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String userId;
	private String password;

}
