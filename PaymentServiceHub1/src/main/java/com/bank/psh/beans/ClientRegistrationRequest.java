package com.bank.psh.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ClientRegistrationRequest {
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getRedirectURI() {
		return redirectURI;
	}
	public void setRedirectURI(String redirectURI) {
		this.redirectURI = redirectURI;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	@NotNull
	@Size(min=2, max=20, message="Client Name should be 16 chars in length")
	private String clientName;
	
	@NotNull
	@Size(min=5, max=100, message="Redirect URI is not valid")
	private String redirectURI;
	
	private String domain;
	

}
