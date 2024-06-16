package com.airfrance.repind.util.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class RestTemplateExtended extends RestTemplate {
	
	private String apiKey;
	
	private String apiSecret;
	
	public RestTemplateExtended() {
		super();
	}
	
	public RestTemplateExtended(String apiKey, String apiSecret) {
		super();
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	/**
	 * For Mashery call, a sig request param must be provided
	 * 
	 * @return String
	 */
	public String getSig() {
		return DigestUtils.sha256Hex(this.apiKey + this.apiSecret + System.currentTimeMillis() / 1000);
	}

	/**
	 * Create header required with api_key
	 * 
	 * @param apiKey
	 * @return
	 */
	public HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accept);
		headers.add("api_key", this.apiKey);
		return headers;
	}
}
