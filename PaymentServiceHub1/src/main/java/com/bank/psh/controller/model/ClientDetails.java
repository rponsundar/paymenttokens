package com.bank.psh.controller.model;

public class ClientDetails {
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	private String clientId;
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

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAuthorization_grants() {
		return authorization_grants;
	}

	public void setAuthorization_grants(String authorization_grants) {
		this.authorization_grants = authorization_grants;
	}

	public String getAutoApproval() {
		return autoApproval;
	}

	public void setAutoApproval(String autoApproval) {
		this.autoApproval = autoApproval;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	private String redirectURI;
	private String domain;
	private String scope;
	private String authorization_grants;
	private String autoApproval;
	private String resourceId;
	private String clientName;
	private String authorities;
	private int accessValidity;
	public int getAccessValidity() {
		return accessValidity;
	}

	public void setAccessValidity(int accessValidity) {
		this.accessValidity = accessValidity;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	

}
