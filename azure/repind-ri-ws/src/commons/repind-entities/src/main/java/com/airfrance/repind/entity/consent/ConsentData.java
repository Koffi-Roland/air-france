package com.airfrance.repind.entity.consent;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity


@Table(name="CONSENT_DATA")
public class ConsentData implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * consentDataId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_CONSENT_DATA")
    @SequenceGenerator(name="ISEQ_CONSENT_DATA", sequenceName = "ISEQ_CONSENT_DATA",
			allocationSize = 1)
    @Column(name="CONSENT_DATA_ID")
    private Long consentDataId;
        
            
    /**
     * key
     */
    @Column(name="STYPE", length=100, nullable=false)
    private String type;
        
            
    /**
     * value
     */
    @Column(name="SCONSENT", length=1)
    private String isConsent;

    /**
     * dateCreation
     */
    @Column(name="DDATE_CONSENT")
    private Date dateConsent;
    
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;
        
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16)
    private String signatureCreation;
        
    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10)
    private String siteCreation;
        
    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;
        
    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String signatureModification;
        
    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    private String siteModification;
            
    /**
     * preference
     */
    // * <-> 1
    @ManyToOne()
    @JoinColumn(name="CONSENT_ID", referencedColumnName = "CONSENT_ID", nullable=false)
    @ForeignKey(name = "FK_CONSENT_REF_CONSENT")
    private Consent consent;

	public Long getConsentDataId() {
		return consentDataId;
	}

	public void setConsentDataId(Long consentDataId) {
		this.consentDataId = consentDataId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsConsent() {
		return isConsent;
	}

	public void setIsConsent(String isConsent) {
		this.isConsent = isConsent;
	}

	public Date getDateConsent() {
		return dateConsent;
	}

	public void setDateConsent(Date dateConsent) {
		this.dateConsent = dateConsent;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getSignatureCreation() {
		return signatureCreation;
	}

	public void setSignatureCreation(String signatureCreation) {
		this.signatureCreation = signatureCreation;
	}

	public String getSiteCreation() {
		return siteCreation;
	}

	public void setSiteCreation(String siteCreation) {
		this.siteCreation = siteCreation;
	}

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	public String getSignatureModification() {
		return signatureModification;
	}

	public void setSignatureModification(String signatureModification) {
		this.signatureModification = signatureModification;
	}

	public String getSiteModification() {
		return siteModification;
	}

	public void setSiteModification(String siteModification) {
		this.siteModification = siteModification;
	}

	public Consent getConsent() {
		return consent;
	}

	public void setConsent(Consent consent) {
		this.consent = consent;
	}
    
}
