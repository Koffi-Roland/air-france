package com.afklm.repind.msv.inferred.filter;

import java.util.ArrayList;
import java.util.List;

import com.klm.opex.correlation.CorrelationData;

public class HapiLog {

	private CorrelationData correlationData;

	private String method;

	private String URI;

	private String contentType;

	private List<HapiLogHeader> headers = new ArrayList<>();

	private Object request;
	private Object response;

	private int status;

	private long executionTime;

	public String getContentType() {
		return contentType;
	}

	public CorrelationData getCorrelationData() {
		return correlationData;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public List<HapiLogHeader> getHeaders() {
		return headers;
	}

	public String getMethod() {
		return method;
	}

	public Object getRequest() {
		return request;
	}

	public Object getResponse() {
		return response;
	}

	public int getStatus() {
		return status;
	}

	public String getURI() {
		return URI;
	}

	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	public void setCorrelationData(final CorrelationData correlationData) {
		this.correlationData = correlationData;
	}

	public void setExecutionTime(final long executionTime) {
		this.executionTime = executionTime;
	}

	public void setHeaders(final List<HapiLogHeader> headers) {
		this.headers = headers;
	}

	public void setMethod(final String method) {
		this.method = method;
	}

	public void setRequest(final Object request) {
		this.request = request;
	}

	public void setResponse(final Object response) {
		this.response = response;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	public void setURI(final String uRI) {
		URI = uRI;
	}

}
