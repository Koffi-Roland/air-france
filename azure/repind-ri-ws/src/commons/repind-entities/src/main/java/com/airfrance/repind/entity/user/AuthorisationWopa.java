package com.airfrance.repind.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "AUTHORISATION_WOPA")
@Data
public class AuthorisationWopa {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_AUTHORISATION_WOPA")
	@SequenceGenerator(name = "ISEQ_AUTHORISATION_WOPA", sequenceName = "ISEQ_AUTHORISATION_WOPA", allocationSize = 1)
	@Column(name = "ID_AUTH")
	private Integer id;

	/**
	 * Registration (matricule) of the user
	 */
	@Column(name = "SUSER_ID")
	private String userId;

	/**
	 * Authorised country codes
	 */
	@Column(name = "SCOUNTRY_CODES")
	private String countryCodes;

	/**
	 * Creation date
	 */
	@Column(name = "DDATE_CREATION")
	private LocalDate dateCreation;

	/**
	 * Creation site
	 */
	@Column(name = "SSITE_CREATION")
	private String siteCreation;

	/**
	 * Creation signature
	 */
	@Column(name = "SSIGNATURE_CREATION")
	private String signatureCreation;

	/**
	 * Modification date
	 */
	@Column(name = "DDATE_MODIFICATION")
	private LocalDate dateModification;

	/**
	 * Modification site
	 */
	@Column(name = "SSITE_MODIFICATION")
	private String siteModification;

	/**
	 * Modification signature
	 */
	@Column(name = "SSIGNATURE_MODIFICATION")
	private String signatureModification;
	
	/**
	 * Role of the user
	 */
	@Column(name = "SROLE")
	private String role;

}
