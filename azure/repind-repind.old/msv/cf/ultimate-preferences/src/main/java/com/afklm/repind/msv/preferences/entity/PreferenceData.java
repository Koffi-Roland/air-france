package com.afklm.repind.msv.preferences.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="PREFERENCE_DATA")
public class PreferenceData implements Serializable {

    private static final long serialVersionUID = 6L;

    
    /**
     * preferenceDataId
     */
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_PREFERENCE_DATA")
    @SequenceGenerator(name="SEQ_PREFERENCE_DATA", sequenceName="SEQ_PREFERENCE_DATA", allocationSize=1)
    @Column(name="PREFERENCE_DATA_ID")
    private Long preferenceDataId;
        
            
    /**
     * key
     */
    @Column(name="SKEY", length=60, nullable=false)
    private String key;
        
            
    /**
     * value
     */
    @Column(name="SVALUE", length=1500)
    private String value;

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
    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name="PREFERENCE_ID", referencedColumnName = "PREFERENCE_ID", nullable=true, foreignKey = @ForeignKey(name = "FK_PREFERENCE_ID"))
    private Preference preference;

	public Long getPreferenceDataId() {
		return preferenceDataId;
	}

	public void setPreferenceDataId(Long preferenceDataId) {
		this.preferenceDataId = preferenceDataId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Preference getPreference() {
		return preference;
	}

	public void setPreference(Preference preference) {
		this.preference = preference;
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
    
}
