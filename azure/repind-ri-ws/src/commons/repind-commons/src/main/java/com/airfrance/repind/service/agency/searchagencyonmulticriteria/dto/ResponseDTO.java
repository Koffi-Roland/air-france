package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

import java.util.List;


/**
 * DTO for the webservice response
 * @author t950700
 *
 */
public class ResponseDTO {
	/*==========================================*/
	/*           INSTANCE VARIABLES             */
	/*==========================================*/
	private long totalNumber;
	private int firstIndex;
	private int maxResult;
	private String returnCode;
	private String returnMessage;
	private List<AgencyDTO> agencies;
	private String token;

	/*==========================================*/
	/*           CONSTRUCTORS                   */
	/*==========================================*/
	public ResponseDTO() {
		super();
	}
	
	/*==========================================*/
	/*                 ACCESSORS                */
	/*==========================================*/
	
	public long getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(long totalNumber) {
		this.totalNumber = totalNumber;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
	
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	
	public List<AgencyDTO> getAgencies() {
		return agencies;
	}

	public void setAgencies(List<AgencyDTO> agencies) {
		this.agencies = agencies;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}