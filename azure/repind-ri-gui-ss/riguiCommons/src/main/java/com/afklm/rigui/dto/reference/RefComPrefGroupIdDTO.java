package com.afklm.rigui.dto.reference;

import java.io.Serializable;


public class RefComPrefGroupIdDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /**
     * refComPrefDgt
     */
    private RefComPrefDgtDTO refComPrefDgt;
    
    
    /**
     * refComPrefGroupInfo
     */
    private RefComPrefGroupInfoDTO refComPrefGroupInfo;


	public RefComPrefGroupIdDTO() {
		super();
	}


	public RefComPrefGroupIdDTO(RefComPrefDgtDTO refComPrefDgt, RefComPrefGroupInfoDTO refComPrefGroupInfo) {
		super();
		this.refComPrefDgt = refComPrefDgt;
		this.refComPrefGroupInfo = refComPrefGroupInfo;
	}


	public RefComPrefDgtDTO getRefComPrefDgt() {
		return refComPrefDgt;
	}


	public void setRefComPrefDgt(RefComPrefDgtDTO refComPrefDgt) {
		this.refComPrefDgt = refComPrefDgt;
	}


	public RefComPrefGroupInfoDTO getRefComPrefGroupInfo() {
		return refComPrefGroupInfo;
	}


	public void setRefComPrefGroupInfo(RefComPrefGroupInfoDTO refComPrefGroupInfo) {
		this.refComPrefGroupInfo = refComPrefGroupInfo;
	}


	@Override
	public String toString() {
		return "RefComPrefGroupId [refComPrefDgt=" + refComPrefDgt + ", refComPrefGroupInfo=" + refComPrefGroupInfo
				+ "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((refComPrefDgt == null) ? 0 : refComPrefDgt.hashCode());
		result = prime * result + ((refComPrefGroupInfo == null) ? 0 : refComPrefGroupInfo.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefComPrefGroupIdDTO other = (RefComPrefGroupIdDTO) obj;
		if (refComPrefDgt == null) {
			if (other.refComPrefDgt != null)
				return false;
		} else if (!refComPrefDgt.equals(other.refComPrefDgt))
			return false;
		if (refComPrefGroupInfo == null) {
			if (other.refComPrefGroupInfo != null)
				return false;
		} else if (!refComPrefGroupInfo.equals(other.refComPrefGroupInfo))
			return false;
		return true;
	}
}


