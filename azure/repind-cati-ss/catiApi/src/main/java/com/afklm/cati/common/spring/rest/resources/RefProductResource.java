package com.afklm.cati.common.spring.rest.resources;

import java.io.Serializable;

public class RefProductResource extends CatiCommonResource implements Serializable {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = -5308303853546616771L;

	private int productId;
	private String productName;
    private String contractType;
    private String productType;
    private String subProductType;
    
    
    
	public RefProductResource() {
		super();
	}



	public RefProductResource(int productId, String productName, String contractType, String productType,
			String subProductType) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.contractType = contractType;
		this.productType = productType;
		this.subProductType = subProductType;
	}



	public int getProductId() {
		return productId;
	}



	public void setProductId(int productId) {
		this.productId = productId;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public String getContractType() {
		return contractType;
	}



	public void setContractType(String contractType) {
		this.contractType = contractType;
	}



	public String getProductType() {
		return productType;
	}



	public void setProductType(String productType) {
		this.productType = productType;
	}



	public String getSubProductType() {
		return subProductType;
	}



	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
