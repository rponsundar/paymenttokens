/**
 * 
 */
package com.bank.psh.beans;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author user
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseBean extends ErrorInfo implements Serializable{

	private boolean fatalErr;
	private MessageBean[] msgs;
	
	/**
	 * @return the fatalErr
	 */
	public boolean isFatalErr() {
		return fatalErr;
	}
	/**
	 * @param fatalErr the fatalErr to set
	 */
	public void setFatalErr(boolean fatalErr) {
		this.fatalErr = fatalErr;
	}
	/**
	 * @return the msgs
	 */
	public MessageBean[] getMsgs() {
		return msgs;
	}
	/**
	 * @param msgs the msgs to set
	 */
	public void setMsgs(MessageBean[] msgs) {
		this.msgs = msgs;
	}
}