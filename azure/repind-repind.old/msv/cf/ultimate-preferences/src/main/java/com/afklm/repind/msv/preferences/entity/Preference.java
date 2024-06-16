package com.afklm.repind.msv.preferences.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="PREFERENCE")
public class Preference implements Serializable {
	
    /**
     * id
     */
    private static final long serialVersionUID = 4L;
            
    /**
     * preferenceId

     */
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_PREFERENCE")
    @SequenceGenerator(name="SEQ_PREFERENCE", sequenceName="SEQ_PREFERENCE", allocationSize=1)
    @Column(name="PREFERENCE_ID")
    private Long preferenceId;

    /**
     * gin
     */
    @Column(name="SGIN", length=12, nullable=false)
    private String gin;
        
    /**
     * type
     */
    @Column(name="STYPE", length=3, nullable=false)
    private String type;

    /**
     * link
     */
    @Column(name="ILINK", nullable=true)
    private Long link;
    
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
    @JsonManagedReference
    @OneToMany(mappedBy="preference", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    private List<PreferenceData> preferenceData;

	public Long getPreferenceId() {
		return preferenceId;
	}

	public void setPreferenceId(Long preferenceId) {
		this.preferenceId = preferenceId;
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

	public Long getlink() {
		return link;
	}

	public void setLink(Long link) {
		this.link = link;
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

	public List<PreferenceData> getPreferenceData() {
		return preferenceData;
	}

	public void setPreferenceData(List<PreferenceData> preferenceData) {
		this.preferenceData = preferenceData;
	}
	
}
