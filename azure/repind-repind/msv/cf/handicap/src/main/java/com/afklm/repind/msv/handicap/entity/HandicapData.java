package com.afklm.repind.msv.handicap.entity;


import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
@Table(name="HANDICAP_DATA")
public class HandicapData implements Serializable {

    private static final long serialVersionUID = 1L;

    
    /**
     * preferenceDataId
     */
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="ISEQ_HANDICAP_DATA")
    @SequenceGenerator(name="ISEQ_HANDICAP_DATA", sequenceName="ISEQ_HANDICAP_DATA", allocationSize=1)
    @Column(name="HANDICAP_DATA_ID")
    private Long handicapDataId;
        
            
    /**
     * key
     */
    @Column(name="SKEY", length=60, nullable=false)
    private String key;
        
            
    /**
     * value
     */
    @Column(name="SVALUE", length=500)
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
     * handicap
     */
    // * <-> 1
    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name="HANDICAP_ID", referencedColumnName = "HANDICAP_ID", nullable=false, foreignKey = @ForeignKey(name = "FK_HANDICAP_ID"))
    private Handicap handicap;

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

	public Long getHandicapDataId() {
		return handicapDataId;
	}

	public void setHandicapDataId(Long handicapDataId) {
		this.handicapDataId = handicapDataId;
	}

	public Handicap getHandicap() {
		return handicap;
	}

	public void setHandicap(Handicap handicap) {
		this.handicap = handicap;
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
