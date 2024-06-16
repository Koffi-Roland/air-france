package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;

/**
 * IdentifyCustomerCrossReferential -  Request's context DTO
 * @author t950700
 *
 */
public class ContextDTO 
{

	/*==========================================*/
	/*                                          */
	/*           INSATNCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	
	private String typeOfSearch;
	private String typeOfFirm;
	private String responseType;
	private RequestorDTO requestor;
	  
	/*==========================================*/
	/*                                          */
	/*             CONSTRUCTORS                 */
	/*                                          */
	/*==========================================*/
	public ContextDTO() {
		super();
	}

	public ContextDTO(String typeOfSearch, String responseType,
			RequestorDTO requestor) {
		super();
		this.typeOfSearch = typeOfSearch;
		this.responseType = responseType;
		this.requestor = requestor;
	}

	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	public String getTypeOfSearch() {
		return typeOfSearch;
	}

	public void setTypeOfSearch(String typeOfSearch) {
		this.typeOfSearch = typeOfSearch;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public RequestorDTO getRequestor() {
		return requestor;
	}

	public void setRequestor(RequestorDTO requestor) {
		this.requestor = requestor;
	}

	public String getTypeOfFirm() {
		return typeOfFirm;
	}

	public void setTypeOfFirm(String typeOfFirm) {
		this.typeOfFirm = typeOfFirm;
	}
	  
}
