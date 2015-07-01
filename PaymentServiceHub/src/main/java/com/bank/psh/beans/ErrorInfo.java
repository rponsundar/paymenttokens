package com.bank.psh.beans;

import java.io.Serializable;

public class ErrorInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7565593579911669047L;
	private String message;

	public ErrorInfo() {
		// TODO Auto-generated constructor stub
	}

	public ErrorInfo(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
