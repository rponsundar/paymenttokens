/**
 * 
 */
package com.bank.psh.beans;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shivakumar
 *
 */
public class ErrorsInfo implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ErrorInfo> errors;

	public List<ErrorInfo> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorInfo> errors) {
		this.errors = errors;
	}
	
	

}
