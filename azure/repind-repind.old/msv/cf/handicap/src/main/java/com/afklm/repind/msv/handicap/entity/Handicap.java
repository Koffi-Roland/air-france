package com.afklm.repind.msv.handicap.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name="HANDICAP")
public class Handicap implements Serializable {
	
    /**
     * id
     */
    private static final long serialVersionUID = 1L;
            
    /**
     * handicapId

     */
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ISEQ_HANDICAP")
    @SequenceGenerator(name="ISEQ_HANDICAP", sequenceName="ISEQ_HANDICAP", allocationSize=1)
    @Column(name="HANDICAP_ID")
    private Long handicapId;

    /**
     * gin
     */
    @Column(name="SGIN", length=12, nullable=false)
    private String gin;
        
    /**
     * type
     */
    @Column(name="STYPE", length=10, nullable=false)
    private String type;

    /**
     * code
     */
    @Column(name="SCODE", length=10, nullable=false)
    private String code;
    
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
     * inferredData
     */
    // 1 <-> * 
    @JsonManagedReference
    @OneToMany(mappedBy="handicap", cascade= CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    private List<HandicapData> handicapData;


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

	public Long getHandicapId() {
		return handicapId;
	}

	public void setHandicapId(Long handicapId) {
		this.handicapId = handicapId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<HandicapData> getHandicapData() {
		return handicapData;
	}

	public void setHandicapData(List<HandicapData> handicapData) {
		this.handicapData = handicapData;
	}

}
