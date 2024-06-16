package com.airfrance.repind.scope;

import com.airfrance.repind.entity.firme.ScopeToProvideFirmEnum;

/**
 * @author Ghayth AYARI
 *
 */
public class FirmScope {

	private ScopeToProvideFirmEnum scopeToProvideFirmEnum;
	private Integer firstResultIndex;
	private Integer maxResults;
	
	
	public FirmScope(ScopeToProvideFirmEnum scopeToProvideFirmEnum, Integer firstResultIndex, Integer maxResults) {
		this.scopeToProvideFirmEnum = scopeToProvideFirmEnum;
		this.firstResultIndex = firstResultIndex;
		this.maxResults = maxResults;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((scopeToProvideFirmEnum == null) ? 0 : scopeToProvideFirmEnum.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FirmScope other = (FirmScope) obj;
		if (scopeToProvideFirmEnum != other.scopeToProvideFirmEnum)
			return false;
		return true;
	}


	/**
	 * @return the firmProvideScope
	 */
	public ScopeToProvideFirmEnum getFirmProvideScope() {
		return scopeToProvideFirmEnum;
	}
	/**
	 * @param firmProvideScope the firmProvideScope to set
	 */
	public void setFirmProvideScope(ScopeToProvideFirmEnum scopeToProvideFirmEnum) {
		this.scopeToProvideFirmEnum = scopeToProvideFirmEnum;
	}
	/**
	 * @return the startIndex
	 */
	public Integer getFirstResultIndex() {
		return firstResultIndex;
	}
	/**
	 * @param startIndex the startIndex to set
	 */
	public void setFirstResultIndex(Integer firstResultIndex) {
		this.firstResultIndex = firstResultIndex;
	}
	/**
	 * @return the endIndex
	 */
	public Integer getMaxResults() {
		return maxResults;
	}
	/**
	 * @param endIndex the endIndex to set
	 */
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

}
