package com.afklm.rigui.model.individual.requests;

import java.util.Date;

public class ModelMergeStatistiques {

	private String ginMerged;
	private String signatureModification;
	private Date dateModification;
	private String siteModification;
	private String ginValid;
	private String firstname;
	private String lastname;
	private String statusGinValid;
	private String civility;

	public ModelMergeStatistiques(String ginMerged, String signatureModification, Date dateModification,
			String siteModification, String ginValid, String firstname, String lastname, String statusGinValid,
			String civility) {
		super();
		this.ginMerged = ginMerged;
		this.signatureModification = signatureModification;
		this.dateModification = dateModification;
		this.siteModification = siteModification;
		this.ginValid = ginValid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.statusGinValid = statusGinValid;
		this.civility = civility;
	}

	/**
	 * @return the civility
	 */
	public String getCivility() {
		return civility;
	}

	/**
	 * @param civility
	 *            the civility to set
	 */
	public void setCivility(String civility) {
		this.civility = civility;
	}

	/**
	 * @return the ginMerged
	 */
	public String getGinMerged() {
		return ginMerged;
	}

	/**
	 * @param ginMerged
	 *            the ginMerged to set
	 */
	public void setGinMerged(String ginMerged) {
		this.ginMerged = ginMerged;
	}

	/**
	 * @return the signatureModification
	 */
	public String getSignatureModification() {
		return signatureModification;
	}

	/**
	 * @param signatureModification
	 *            the signatureModification to set
	 */
	public void setSignatureModification(String signatureModification) {
		this.signatureModification = signatureModification;
	}

	/**
	 * @return the dateModification
	 */
	public Date getDateModification() {
		return dateModification;
	}

	/**
	 * @param dateModification
	 *            the dateModification to set
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
	 * @param siteModification
	 *            the siteModification to set
	 */
	public void setSiteModification(String siteModification) {
		this.siteModification = siteModification;
	}

	/**
	 * @return the ginValid
	 */
	public String getGinValid() {
		return ginValid;
	}

	/**
	 * @param ginValid
	 *            the ginValid to set
	 */
	public void setGinValid(String ginValid) {
		this.ginValid = ginValid;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *            the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *            the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the statusGinValid
	 */
	public String getStatusGinValid() {
		return statusGinValid;
	}

	/**
	 * @param statusGinValid
	 *            the statusGinValid to set
	 */
	public void setStatusGinValid(String statusGinValid) {
		this.statusGinValid = statusGinValid;
	}

}
