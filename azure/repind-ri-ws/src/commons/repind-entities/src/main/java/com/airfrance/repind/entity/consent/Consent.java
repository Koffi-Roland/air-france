package com.airfrance.repind.entity.consent;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity


@Table(name="CONSENT")
public class Consent implements Serializable {

    private static final long serialVersionUID = 1L;

            
    /**
     * preferenceId

     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_CONSENT")
    @SequenceGenerator(name="ISEQ_CONSENT", sequenceName = "ISEQ_CONSENT",
			allocationSize = 1)
    @Column(name="CONSENT_ID")
    private Long consentId;

    /**
     * gin
     */
    @Column(name="SGIN", length=12, nullable=false)
    private String gin;
        
    /**
     * type
     */
    @Column(name="STYPE", length=4, nullable=false)
    private String type;
    

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
     * preferenceData
     */
    // 1 <-> * 
    @OneToMany(mappedBy="consent", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private Set<ConsentData> consentData;

	public Long getConsentId() {
		return consentId;
	}

	public void setConsentId(Long consentId) {
		this.consentId = consentId;
	}

	public String getGin() {
		return gin;
	}

	public void setGin(String gin) {
		this.gin = gin;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Set<ConsentData> getConsentData() {
		return consentData;
	}

	public void setConsentData(Set<ConsentData> consentData) {
		this.consentData = consentData;
	}
    
}
