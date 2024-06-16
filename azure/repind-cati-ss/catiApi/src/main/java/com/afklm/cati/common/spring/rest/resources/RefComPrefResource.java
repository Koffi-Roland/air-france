package com.afklm.cati.common.spring.rest.resources;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class RefComPrefResource extends CatiCommonResource implements Serializable {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = -5308303853546616771L;


	/**
     * refComprefId
     */
    private Integer refComprefId;
        
            
    /**
     * description
     */
    @Length(max = 50)
    private String description;
        
            
    /**
     * mandatoryOptin
     */
    @NotNull
    @Length(max = 30)
    private String mandatoryOptin;
        
            
    /**
     * market
     */
    @NotNull
    @Length(max = 3)
    private String market;
        
            
    /**
     * defaultLanguage1
     */
    @NotNull
    @Length(max = 2)
    private String defaultLanguage1;
        
            
    /**
     * defaultLanguage2
     */
    @Length(max = 2)
    private String defaultLanguage2;
        
            
    /**
     * defaultLanguage3
     */
    @Length(max = 2)
    private String defaultLanguage3;
        
            
    /**
     * defaultLanguage4
     */
    @Length(max = 2)
    private String defaultLanguage4;
        
            
    /**
     * defaultLanguage5
     */
    @Length(max = 2)
    private String defaultLanguage5;
        
            
    /**
     * defaultLanguage6
     */
    @Length(max = 2)
    private String defaultLanguage6;
        
            
    /**
     * defaultLanguage7
     */
    @Length(max = 2)
    private String defaultLanguage7;
        
            
    /**
     * defaultLanguage8
     */
    @Length(max = 2)
    private String defaultLanguage8;
        
            
    /**
     * defaultLanguage9
     */
    @Length(max = 2)
    private String defaultLanguage9;
        
            
    /**
     * defaultLanguage10
     */
    @Length(max = 2)
    private String defaultLanguage10;
        
            
    /**
     * fieldA
     */
    @NotNull
    @Length(max = 1)
    private String fieldA;
        
            
    /**
     * fieldN
     */
    @NotNull
    @Length(max = 1)
    private String fieldN;
        
            
    /**
     * fieldT
     */
    @NotNull
    @Length(max = 1)
    private String fieldT;

    /**
     * comGroupeType
     */
    @NotNull
    @Length(max = 7)
    private String comGroupeType;
        
            
    /**
     * comType
     */
    @NotNull
    @Length(max = 7)
    private String comType;
        
            
    /**
     * domain
     */
    @NotNull
    @Length(max = 7)
    private String domain;
        
            
    /**
     * media
     */
    @Length(max = 1)
    private String media;
    
    private Date dateCreation;
    
    private String siteCreation;
    
    private String signatureCreation;
    
    private Date dateModification;
    
    private String siteModification;
    
    private String signatureModification;


	/**
	 * @return the refComprefId
	 */
	public Integer getRefComprefId() {
		return refComprefId;
	}


	/**
	 * @param refComprefId the refComprefId to set
	 */
	public void setRefComprefId(Integer refComprefId) {
		this.refComprefId = refComprefId;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the mandatoryOptin
	 */
	public String getMandatoryOptin() {
		return mandatoryOptin;
	}


	/**
	 * @param mandatoryOptin the mandatoryOptin to set
	 */
	public void setMandatoryOptin(String mandatoryOptin) {
		this.mandatoryOptin = mandatoryOptin;
	}


	/**
	 * @return the market
	 */
	public String getMarket() {
		return market;
	}


	/**
	 * @param market the market to set
	 */
	public void setMarket(String market) {
		this.market = market;
	}


	/**
	 * @return the defaultLanguage1
	 */
	public String getDefaultLanguage1() {
		return defaultLanguage1;
	}


	/**
	 * @param defaultLanguage1 the defaultLanguage1 to set
	 */
	public void setDefaultLanguage1(String defaultLanguage1) {
		this.defaultLanguage1 = defaultLanguage1;
	}


	/**
	 * @return the defaultLanguage2
	 */
	public String getDefaultLanguage2() {
		return defaultLanguage2;
	}


	/**
	 * @param defaultLanguage2 the defaultLanguage2 to set
	 */
	public void setDefaultLanguage2(String defaultLanguage2) {
		this.defaultLanguage2 = defaultLanguage2;
	}


	/**
	 * @return the defaultLanguage3
	 */
	public String getDefaultLanguage3() {
		return defaultLanguage3;
	}


	/**
	 * @param defaultLanguage3 the defaultLanguage3 to set
	 */
	public void setDefaultLanguage3(String defaultLanguage3) {
		this.defaultLanguage3 = defaultLanguage3;
	}


	/**
	 * @return the defaultLanguage4
	 */
	public String getDefaultLanguage4() {
		return defaultLanguage4;
	}


	/**
	 * @param defaultLanguage4 the defaultLanguage4 to set
	 */
	public void setDefaultLanguage4(String defaultLanguage4) {
		this.defaultLanguage4 = defaultLanguage4;
	}


	/**
	 * @return the defaultLanguage5
	 */
	public String getDefaultLanguage5() {
		return defaultLanguage5;
	}


	/**
	 * @param defaultLanguage5 the defaultLanguage5 to set
	 */
	public void setDefaultLanguage5(String defaultLanguage5) {
		this.defaultLanguage5 = defaultLanguage5;
	}


	/**
	 * @return the defaultLanguage6
	 */
	public String getDefaultLanguage6() {
		return defaultLanguage6;
	}


	/**
	 * @param defaultLanguage6 the defaultLanguage6 to set
	 */
	public void setDefaultLanguage6(String defaultLanguage6) {
		this.defaultLanguage6 = defaultLanguage6;
	}


	/**
	 * @return the defaultLanguage7
	 */
	public String getDefaultLanguage7() {
		return defaultLanguage7;
	}


	/**
	 * @param defaultLanguage7 the defaultLanguage7 to set
	 */
	public void setDefaultLanguage7(String defaultLanguage7) {
		this.defaultLanguage7 = defaultLanguage7;
	}


	/**
	 * @return the defaultLanguage8
	 */
	public String getDefaultLanguage8() {
		return defaultLanguage8;
	}


	/**
	 * @param defaultLanguage8 the defaultLanguage8 to set
	 */
	public void setDefaultLanguage8(String defaultLanguage8) {
		this.defaultLanguage8 = defaultLanguage8;
	}


	/**
	 * @return the defaultLanguage9
	 */
	public String getDefaultLanguage9() {
		return defaultLanguage9;
	}


	/**
	 * @param defaultLanguage9 the defaultLanguage9 to set
	 */
	public void setDefaultLanguage9(String defaultLanguage9) {
		this.defaultLanguage9 = defaultLanguage9;
	}


	/**
	 * @return the defaultLanguage10
	 */
	public String getDefaultLanguage10() {
		return defaultLanguage10;
	}


	/**
	 * @param defaultLanguage10 the defaultLanguage10 to set
	 */
	public void setDefaultLanguage10(String defaultLanguage10) {
		this.defaultLanguage10 = defaultLanguage10;
	}


	/**
	 * @return the fieldA
	 */
	public String getFieldA() {
		return fieldA;
	}


	/**
	 * @param fieldA the fieldA to set
	 */
	public void setFieldA(String fieldA) {
		this.fieldA = fieldA;
	}


	/**
	 * @return the fieldN
	 */
	public String getFieldN() {
		return fieldN;
	}


	/**
	 * @param fieldN the fieldN to set
	 */
	public void setFieldN(String fieldN) {
		this.fieldN = fieldN;
	}


	/**
	 * @return the fieldT
	 */
	public String getFieldT() {
		return fieldT;
	}


	/**
	 * @param fieldT the fieldT to set
	 */
	public void setFieldT(String fieldT) {
		this.fieldT = fieldT;
	}


	public String getComGroupeType() {
		return comGroupeType;
	}


	public void setComGroupeType(String comGroupeType) {
		this.comGroupeType = comGroupeType;
	}


	public String getComType() {
		return comType;
	}


	public void setComType(String comType) {
		this.comType = comType;
	}


	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}


	public String getMedia() {
		return media;
	}


	public void setMedia(String media) {
		this.media = media;
	}


	public Date getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	public String getSiteCreation() {
		return siteCreation;
	}


	public void setSiteCreation(String siteCreation) {
		this.siteCreation = siteCreation;
	}


	public String getSignatureCreation() {
		return signatureCreation;
	}


	public void setSignatureCreation(String signatureCreation) {
		this.signatureCreation = signatureCreation;
	}


	public Date getDateModification() {
		return dateModification;
	}


	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}


	public String getSiteModification() {
		return siteModification;
	}


	public void setSiteModification(String siteModification) {
		this.siteModification = siteModification;
	}


	public String getSignatureModification() {
		return signatureModification;
	}


	public void setSignatureModification(String signatureModification) {
		this.signatureModification = signatureModification;
	}

}
