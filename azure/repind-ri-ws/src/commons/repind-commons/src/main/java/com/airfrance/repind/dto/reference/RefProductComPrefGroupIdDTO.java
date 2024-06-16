package com.airfrance.repind.dto.reference;

import java.io.Serializable;


public class RefProductComPrefGroupIdDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /**
     * refProductDTO
     */
    private RefProductDTO refProduct;
    
    
    /**
     * refComPrefGroupInfo
     */
    private RefComPrefGroupInfoDTO refComPrefGroupInfo;


	public RefProductComPrefGroupIdDTO() {
		super();
	}


	public RefProductComPrefGroupIdDTO(RefProductDTO refProduct, RefComPrefGroupInfoDTO refComPrefGroupInfo) {
		super();
		this.refProduct = refProduct;
		this.refComPrefGroupInfo = refComPrefGroupInfo;
	}


	public RefProductDTO getRefProduct() {
		return refProduct;
	}


	public void setRefProduct(RefProductDTO refProduct) {
		this.refProduct = refProduct;
	}


	public RefComPrefGroupInfoDTO getRefComPrefGroupInfo() {
		return refComPrefGroupInfo;
	}


	public void setRefComPrefGroupInfo(RefComPrefGroupInfoDTO refComPrefGroupInfo) {
		this.refComPrefGroupInfo = refComPrefGroupInfo;
	}


	@Override
	public String toString() {
		return "RefProductComPrefGroupIdDTO [refProduct=" + refProduct + ", refComPrefGroupInfo=" + refComPrefGroupInfo
				+ "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((refComPrefGroupInfo == null) ? 0 : refComPrefGroupInfo.hashCode());
		result = prime * result + ((refProduct == null) ? 0 : refProduct.hashCode());
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
		RefProductComPrefGroupIdDTO other = (RefProductComPrefGroupIdDTO) obj;
		if (refComPrefGroupInfo == null) {
			if (other.refComPrefGroupInfo != null)
				return false;
		} else if (!refComPrefGroupInfo.equals(other.refComPrefGroupInfo))
			return false;
		if (refProduct == null) {
			if (other.refProduct != null)
				return false;
		} else if (!refProduct.equals(other.refProduct))
			return false;
		return true;
	}
}


