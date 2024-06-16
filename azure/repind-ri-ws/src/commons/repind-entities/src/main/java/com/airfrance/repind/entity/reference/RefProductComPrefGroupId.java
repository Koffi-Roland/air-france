package com.airfrance.repind.entity.reference;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;


@Embeddable
public class RefProductComPrefGroupId implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /**
     * refProduct
     */
    @OneToOne()
    @JoinColumn(name="PRODUCT_ID", nullable=false)
    @ForeignKey(name = "FK_REF_PRODUCT_ID")
    private RefProduct refProduct;
    
    
    /**
     * refComPrefGroupInfo
     */
    @OneToOne()
	@JoinColumn(name = "REF_COMPREF_GROUP_INFO_ID", nullable=false)
    @ForeignKey(name = "FK_REF_COMPREF_GROUP_INFO_ID")
    private RefComPrefGroupInfo refComPrefGroupInfo;


	public RefProductComPrefGroupId() {
		super();
	}


	public RefProductComPrefGroupId(RefProduct refProduct, RefComPrefGroupInfo refComPrefGroupInfo) {
		super();
		this.refProduct = refProduct;
		this.refComPrefGroupInfo = refComPrefGroupInfo;
	}


	public RefProduct getRefProduct() {
		return refProduct;
	}


	public void setRefProduct(RefProduct refProduct) {
		this.refProduct = refProduct;
	}


	public RefComPrefGroupInfo getRefComPrefGroupInfo() {
		return refComPrefGroupInfo;
	}


	public void setRefComPrefGroupInfo(RefComPrefGroupInfo refComPrefGroupInfo) {
		this.refComPrefGroupInfo = refComPrefGroupInfo;
	}


	@Override
	public String toString() {
		return "RefProductComPrefGroupId [refProduct=" + refProduct + ", refComPrefGroupInfo=" + refComPrefGroupInfo
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
		RefProductComPrefGroupId other = (RefProductComPrefGroupId) obj;
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


