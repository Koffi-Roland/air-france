package com.afklm.repind.msv.inferred.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name="INFERRED_DATA")
public class InferredData implements Serializable {

    private static final long serialVersionUID = 1L;

    
    /**
     * preferenceDataId
     */
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ISEQ_INFERRED_DATA")
    @SequenceGenerator(name="ISEQ_INFERRED_DATA", sequenceName="ISEQ_INFERRED_DATA", allocationSize=1)
    @Column(name="INFERRED_DATA_ID")
    private Long inferredDataId;
        
            
    /**
     * key
     */
    @Column(name="SKEY", length=60, nullable=false)
    private String key;
        
            
    /**
     * value
     */
    @Column(name="SVALUE", length=256)
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
     * inferred
     */
    // * <-> 1
    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name="INFERRED_ID", referencedColumnName = "INFERRED_ID", nullable=false, foreignKey = @ForeignKey(name = "FK_INFERRED_ID"))
    private Inferred inferred;

	public Long getInferredDataId() {
		return inferredDataId;
	}

	public void setInferredDataId(Long inferredDataId) {
		this.inferredDataId = inferredDataId;
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

	public Inferred getInferred() {
		return inferred;
	}

	public void setInferred(Inferred inferred) {
		this.inferred = inferred;
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