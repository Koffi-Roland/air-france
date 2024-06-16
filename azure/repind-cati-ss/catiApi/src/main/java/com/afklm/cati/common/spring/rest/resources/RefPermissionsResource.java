package com.afklm.cati.common.spring.rest.resources;

import java.io.Serializable;
import java.util.Date;

public class RefPermissionsResource extends CatiCommonResource implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4634609266319780882L;
    
    private RefPermissionsIdResource refPermissionsId;
    
    private Date dateCreation;
    
    private String siteCreation;
    
    private String signatureCreation;
    
    private Date dateModification;
    
    private String siteModification;
    
    private String signatureModification;

	/**
	 * @return the refPermissionsId
	 */
	public RefPermissionsIdResource getRefPermissionsId() {
		return refPermissionsId;
	}

	/**
	 * @param refPermissionsId the refPermissionsId to set
	 */
	public void setRefPermissionsId(RefPermissionsIdResource refPermissionsId) {
		this.refPermissionsId = refPermissionsId;
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
