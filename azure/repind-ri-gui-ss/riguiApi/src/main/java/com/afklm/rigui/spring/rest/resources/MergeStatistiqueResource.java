package com.afklm.rigui.spring.rest.resources;

import java.io.Serializable;
import java.util.Date;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Common Resource
 * @author m405991
 *
 */
@JsonInclude(Include.NON_NULL)
public class MergeStatistiqueResource extends RepresentationModel<TrackingResource> implements Serializable {

	private String ginMerged;
	private String signatureModification;
	private Date dateModification;
	private String siteModification;
	private String ginValid;
	private String firstname;
	private String lastname;
	private String statusGinValid;

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
