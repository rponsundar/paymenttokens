package com.bank.psh.exception;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

public class PTResponseErrorHandler implements ResponseErrorHandler {

	private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

	public boolean hasError(ClientHttpResponse response) throws IOException {
		return errorHandler.hasError(response);
	}

	public void handleError(ClientHttpResponse response) throws IOException {
		String theString = IOUtils.toString(response.getBody());
		System.out.println("Error occured " + theString);
		
	}
}
