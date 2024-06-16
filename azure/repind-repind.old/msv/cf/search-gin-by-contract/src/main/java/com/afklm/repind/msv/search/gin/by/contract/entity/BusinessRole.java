package com.afklm.repind.msv.search.gin.by.contract.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BUSINESS_ROLE")
@Getter
@Setter
public class BusinessRole implements Serializable{

	private static final long serialVersionUID = 1L;
	/* PROTECTED REGION END */

	/**
	 * cleRole
	 */
	@Id
	@GeneratedValue
	@Column(name = "ICLE_ROLE", length = 10, nullable = false)
	private Integer cleRole;

	/**
	 * ginInd
	 */
	@Column(name = "SGIN_IND", length = 12)
	private String ginInd;

	/**
	 * ginPm
	 */
	@Column(name = "SGIN_PM", length = 12)
	private String ginPm;

	/**
	 * numeroContrat
	 */
	@Column(name = "SNUMERO_CONTRAT", length = 20)
	private String numeroContrat;

	/**
	 * type
	 */
	@Column(name = "STYPE", length = 1)
	private String type;

	/**
	 * dateCreation
	 */
	@Column(name = "DDATE_CREATION")
	private Date dateCreation;

	/**
	 * siteCreation
	 */
	@Column(name = "SSITE_CREATION", length = 12)
	private String siteCreation;

	/**
	 * signatureCreation
	 */
	@Column(name = "SSIGNATURE_CREATION", length = 16)
	private String signatureCreation;

	/**
	 * dateModification
	 */
	@Column(name = "DDATE_MODIFICATION")
	private Date dateModification;

	/**
	 * siteModification
	 */
	@Column(name = "SSITE_MODIFICATION", length = 10)
	private String siteModification;

	/**
	 * signatureModification
	 */
	@Column(name = "SSIGNATURE_MODIFICATION", length = 16)
	private String signatureModification;

}