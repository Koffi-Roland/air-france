package com.afklm.repind.msv.inferred.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="INFERRED")
public class Inferred implements Serializable {
	
    /**
     * id
     */
    private static final long serialVersionUID = 1L;
            
    /**
     * inferredId

     */
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ISEQ_INFERRED")
    @SequenceGenerator(name="ISEQ_INFERRED", sequenceName="ISEQ_INFERRED", allocationSize=1)
    @Column(name="INFERRED_ID")
    private Long inferredId;

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
     * status
     */
    @Column(name="SSTATUS", length=1, nullable=false)
    private String status;
    
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
    @OneToMany(mappedBy="inferred", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
    private List<InferredData> inferredData;

	public Long getInferredId() {
		return inferredId;
	}

	public void setInferredId(Long inferredId) {
		this.inferredId = inferredId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public List<InferredData> getInferredData() {
		return inferredData;
	}

	public void setInferredData(List<InferredData> inferredData) {
		this.inferredData = inferredData;
	}
	
}