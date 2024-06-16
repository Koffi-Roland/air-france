package com.afklm.rigui.model.individual;

import java.util.Date;

public class ModelBasicIndividualMergeStatistiquesData extends ModelBasicIndividualData {

	private String ginMerged;
	private String signatureModification;
	private Date dateModification;
	private String siteModification;

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
}
