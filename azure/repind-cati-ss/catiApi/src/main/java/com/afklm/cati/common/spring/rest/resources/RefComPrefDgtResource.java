package com.afklm.cati.common.spring.rest.resources;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.afklm.cati.common.entity.RefComPrefMl;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class RefComPrefDgtResource extends CatiCommonResource implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4634609266319780882L;
        
    private Integer refComPrefDgtId;
    
    private String domain;
    
    private String groupType;
    
    private String type;    
    
    private String description;
    
    @JsonIgnore
    private List<RefComPrefMl> refComPrefMls;
    
    private Date dateCreation;
    
    private String siteCreation;
    
    private String signatureCreation;
    
    private Date dateModification;
    
    private String siteModification;
    
    private String signatureModification;

	/**
	 * @return the refComPrefDgtId
	 */
	public Integer getRefComPrefDgtId() {
		return refComPrefDgtId;
	}

	/**
	 * @param refComPrefDgtId the refComPrefDgtId to set
	 */
	public void setRefComPrefDgtId(Integer refComPrefDgtId) {
		this.refComPrefDgtId = refComPrefDgtId;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the groupType
	 */
	public String getGroupType() {
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the refComPrefMls
	 */
	public List<RefComPrefMl> getRefComPrefMls() {
		return refComPrefMls;
	}

	/**
	 * @param refComPrefMls the refComPrefMls to set
	 */
	public void setRefComPrefMls(List<RefComPrefMl> refComPrefMls) {
		this.refComPrefMls = refComPrefMls;
	}

	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * @return the siteCreation
	 */
	public String getSiteCreation() {
		return siteCreation;
	}

	/**
	 * @param siteCreation the siteCreation to set
	 */
	public void setSiteCreation(String siteCreation) {
		this.siteCreation = siteCreation;
	}

	/**
	 * @return the signatureCreation
	 */
	public String getSignatureCreation() {
		return signatureCreation;
	}

	/**
	 * @param signatureCreation the signatureCreation to set
	 */
	public void setSignatureCreation(String signatureCreation) {
		this.signatureCreation = signatureCreation;
	}

	/**
	 * @return the dateModification
	 */
	public Date getDateModification() {
		return dateModification;
	}

	/**
	 * @param dateModification the dateModification to set
	 */
	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	/**
	 * @return the siteModification
	 */
	public String getSiteModification() {
		return siteModification;
	}

	/**
	 * @param siteModification the siteModification to set
	 */
	public void setSiteModification(String siteModification) {
		this.siteModification = siteModification;
	}

	/**
	 * @return the signatureModification
	 */
	public String getSignatureModification() {
		return signatureModification;
	}

	/**
	 * @param signatureModification the signatureModification to set
	 */
	public void setSignatureModification(String signatureModification) {
		this.signatureModification = signatureModification;
	}
    
}
