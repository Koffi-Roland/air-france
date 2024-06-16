package com.airfrance.repind.transversal.identifycustomercrossreferential.dto;


/**
 * IdentifyCutomerCrossReferential RequestDTO
 * @author t950700
 *
 */
public class RequestDTO {
	
	/*==========================================*/
	/*                                          */
	/*           INSTANCE VARIABLES             */
	/*                                          */
	/*==========================================*/
	
	private String processType;
	private int index;
	private int queryMaxResults;
	private int queryPageSize;
	private String queryMembers;
	private String queryMembersEmails;
	private String queryMembersTelecoms;
	private ContextDTO context;
	private SearchIdentifierDTO searchIdentifier;
	private ProvideIdentifierDTO provideIdentifier;
	
	/*==========================================*/
	/*                                          */
	/*              CONSTRUCTORS                */
	/*                                          */
	/*==========================================*/
	
	public RequestDTO() {
		super();
	}

	public RequestDTO(String processType, int index, int queryMaxResults,
			int queryPageSize, ContextDTO context,
			SearchIdentifierDTO searchIdentifier,
			ProvideIdentifierDTO provideIdentifier) {
		super();
		this.processType = processType;
		this.index = index;
		this.queryMaxResults = queryMaxResults;
		this.queryPageSize = queryPageSize;
		this.context = context;
		this.searchIdentifier = searchIdentifier;
		this.provideIdentifier = provideIdentifier;
	}

	
	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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

	public String getQueryMembers() {
		return queryMembers;
	}

	public void setQueryMembers(String queryMembers) {
		this.queryMembers = queryMembers;
	}

	public String getQueryMembersEmails() {
		return queryMembersEmails;
	}

	public void setQueryMembersEmails(String queryMembersEmails) {
		this.queryMembersEmails = queryMembersEmails;
	}

	public String getQueryMembersTelecoms() {
		return queryMembersTelecoms;
	}

	public void setQueryMembersTelecoms(String queryMembersTelecoms) {
		this.queryMembersTelecoms = queryMembersTelecoms;
	}

	public ContextDTO getContext() {
		return context;
	}

	public void setContext(ContextDTO context) {
		this.context = context;
	}

	public SearchIdentifierDTO getSearchIdentifier() {
		return searchIdentifier;
	}

	public void setSearchIdentifier(SearchIdentifierDTO searchIdentifier) {
		this.searchIdentifier = searchIdentifier;
	}

	public ProvideIdentifierDTO getProvideIdentifier() {
		return provideIdentifier;
	}

	public void setProvideIdentifier(ProvideIdentifierDTO provideIdentifier) {
		this.provideIdentifier = provideIdentifier;
	}
	
}
