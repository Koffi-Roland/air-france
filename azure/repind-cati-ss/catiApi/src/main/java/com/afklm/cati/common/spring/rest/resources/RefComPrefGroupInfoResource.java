package com.afklm.cati.common.spring.rest.resources;

import java.io.Serializable;
import java.util.Date;

import org.dozer.Mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefComPrefGroupInfoResource extends CatiCommonResource implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4634609266319780882L;
        
    @Mapping("id")
    @JsonProperty("id")
    private Integer groupInfoId;
    
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

	private Long nbCompref;
	
	private Long nbProduct;

	public Integer getgroupInfoId() {
		return groupInfoId;
	}

	public void setId(Integer id) {
		this.groupInfoId = id;
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

	public Long getNbCompref() {
		return nbCompref;
	}

	public void setNbCompref(Long nbCompref) {
		this.nbCompref = nbCompref;
	}

	public Long getNbProduct() {
		return nbProduct;
	}

	public void setNbProduct(Long nbProduct) {
		this.nbProduct = nbProduct;
	}
    
}
