package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

import javax.persistence.Query;

/**
 * DTO for the web service request
 * @author t950700
 *
 */
public class RequestDTO {
	/*==========================================*/
	/*           INSTANCE VARIABLES             */
	/*==========================================*/
	private RequestorDTO requestor;
	private String processType;
	private IdentityDTO identity;
	private ContactsDTO contacts;
	private IdentificationDTO identification;
	private CommercialZonesDTO commercialZones;
	private Query countHqlQuery;
	private Query searchHqlQuery;
	private String loadAgencyHqlQuery;
	private int queryIndex;
	private int queryMaxResults;
	private int queryPageSize;
	
	private boolean queryAutoSearchHasLimit;
	private int queryAutoSearchLimit;

	/*==========================================*/
	/*           CONSTRUCTORS                   */
	/*==========================================*/
	public RequestDTO() {
		super();
	}
	
	/*==========================================*/
	/*                 ACCESSORS                */
	/*==========================================*/

	public RequestorDTO getRequestor() {
		return requestor;
	}

	public void setRequestor(RequestorDTO requestor) {
		this.requestor = requestor;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public IdentityDTO getIdentity() {
		return identity;
	}

	public void setIdentity(IdentityDTO identity) {
		this.identity = identity;
	}

	public ContactsDTO getContacts() {
		return contacts;
	}

	public void setContacts(ContactsDTO contacts) {
		this.contacts = contacts;
	}

	public IdentificationDTO getIdentification() {
		return identification;
	}

	public void setIdentification(IdentificationDTO identification) {
		this.identification = identification;
	}

	public CommercialZonesDTO getCommercialZones() {
		return commercialZones;
	}

	public void setCommercialZones(CommercialZonesDTO commercialZones) {
		this.commercialZones = commercialZones;
	}

	public Query getCountHqlQuery() {
		return countHqlQuery;
	}

	public void setCountHqlQuery(Query countHqlQuery) {
		this.countHqlQuery = countHqlQuery;
	}

	public Query getSearchHqlQuery() {
		return searchHqlQuery;
	}

	public void setSearchHqlQuery(Query searchHqlQuery) {
		this.searchHqlQuery = searchHqlQuery;
	}

	public String getLoadAgencyHqlQuery() {
		return loadAgencyHqlQuery;
	}

	public void setLoadAgencyHqlQuery(String loadAgencyHqlQuery) {
		this.loadAgencyHqlQuery = loadAgencyHqlQuery;
	}

	public int getQueryIndex() {
		return queryIndex;
	}

	public void setQueryIndex(int queryIndex) {
		this.queryIndex = queryIndex;
	}

	public int getQueryMaxResults() {
		return queryMaxResults;
	}

	public void setQueryMaxResults(int queryMaxResults) {
		this.queryMaxResults = queryMaxResults;
	}

	public int getQueryPageSize() {
		return queryPageSize;
	}

	public void setQueryPageSize(int queryPageSize) {
		this.queryPageSize = queryPageSize;
	}

	public boolean isQueryAutoSearchHasLimit() {
		return queryAutoSearchHasLimit;
	}

	public void setQueryAutoSearchHasLimit(boolean queryAutoSearchHasLimit) {
		this.queryAutoSearchHasLimit = queryAutoSearchHasLimit;
	}

	public int getQueryAutoSearchLimit() {
		return queryAutoSearchLimit;
	}

	public void setQueryAutoSearchLimit(int queryAutoSearchLimit) {
		this.queryAutoSearchLimit = queryAutoSearchLimit;
	}
	
}