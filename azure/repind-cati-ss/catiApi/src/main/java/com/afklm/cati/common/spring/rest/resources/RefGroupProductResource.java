package com.afklm.cati.common.spring.rest.resources;

import java.io.Serializable;
import java.util.Date;

public class RefGroupProductResource extends CatiCommonResource implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4634609266319780882L;
    
    private int productId;
    private String productType;
    private String subProductType;
    private String productName;
    private int idGroup;
    private String code;
    private String libelleFR;
    private String libelleEN;
    private String mandatoryOption;
    private String defaultOption;
    private Date dateCreation;
    private String siteCreation;
    private String signatureCreation;
    private Date dateModification;
    private String siteModification;
    private String signatureModification;
    
	public RefGroupProductResource() {
		super();
	}

	public RefGroupProductResource(int productId, String productType, String subProductType, String productName,
			int idGroup, String code, String libelleFR, String libelleEN, String mandatoryOption, String defaultOption,
			Date dateCreation, String siteCreation, String signatureCreation, Date dateModification,
			String siteModification, String signatureModification) {
		super();
		this.productId = productId;
		this.productType = productType;
		this.subProductType = subProductType;
		this.productName = productName;
		this.idGroup = idGroup;
		this.code = code;
		this.libelleFR = libelleFR;
		this.libelleEN = libelleEN;
		this.mandatoryOption = mandatoryOption;
		this.defaultOption = defaultOption;
		this.dateCreation = dateCreation;
		this.siteCreation = siteCreation;
		this.signatureCreation = signatureCreation;
		this.dateModification = dateModification;
		this.siteModification = siteModification;
		this.signatureModification = signatureModification;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelleFR() {
		return libelleFR;
	}

	public void setLibelleFR(String libelleFR) {
		this.libelleFR = libelleFR;
	}

	public String getLibelleEN() {
		return libelleEN;
	}

	public void setLibelleEN(String libelleEN) {
		this.libelleEN = libelleEN;
	}

	public String getMandatoryOption() {
		return mandatoryOption;
	}

	public void setMandatoryOption(String mandatoryOption) {
		this.mandatoryOption = mandatoryOption;
	}

	public String getDefaultOption() {
		return defaultOption;
	}

	public void setDefaultOption(String defaultOption) {
		this.defaultOption = defaultOption;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getSiteCreation() {
		return siteCreation;
	}

	public void setSiteCreation(String siteCreation) {
		this.siteCreation = siteCreation;
	}

	public String getSignatureCreation() {
		return signatureCreation;
	}

	public void setSignatureCreation(String signatureCreation) {
		this.signatureCreation = signatureCreation;
	}

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	public String getSiteModification() {
		return siteModification;
	}

	public void setSiteModification(String siteModification) {
		this.siteModification = siteModification;
	}

	public String getSignatureModification() {
		return signatureModification;
	}

	public void setSignatureModification(String signatureModification) {
		this.signatureModification = signatureModification;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
