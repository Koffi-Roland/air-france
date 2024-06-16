package com.afklm.repind.msv.graphql.bff.example.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestTemplateExtended extends RestTemplate {

	/**
	 * Mashery api key
	 */
	private String apiKey;
	/**
	 * Mashery api secret
	 */
	private String apiSecret;

	/**
	 * For Mashery call, a sig request param must be provided
	 * 
	 * @return String
	 */
	public String getSig()
	{
		return DigestUtils.sha256Hex(this.apiKey + this.apiSecret + System.currentTimeMillis() / 1000);
	}

	/**
	 * Create header required with api_key
	 * 
	 * @return http headers
	 */
	public HttpHeaders createHeaders()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.APPLICATION_JSON);
		headers.setAccept(accept);
		headers.add("api_key", this.apiKey);
		return headers;
	}
}
