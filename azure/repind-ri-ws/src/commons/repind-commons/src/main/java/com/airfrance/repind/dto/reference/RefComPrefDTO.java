package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_Y7gEIDSeEeaR_YJoHRGtPg i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefComPrefDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class RefComPrefDTO implements Serializable {


        
    /**
     * refComprefId
     */
    private Integer refComprefId;
        
        
    /**
     * description
     */
    private String description;
        
        
    /**
     * mandatoryOptin
     */
    private String mandatoryOptin;
        
        
    /**
     * market
     */
    private String market;
        
        
    /**
     * defaultLanguage1
     */
    private String defaultLanguage1;
        
        
    /**
     * defaultLanguage2
     */
    private String defaultLanguage2;
        
        
    /**
     * defaultLanguage3
     */
    private String defaultLanguage3;
        
        
    /**
     * defaultLanguage4
     */
    private String defaultLanguage4;
        
        
    /**
     * defaultLanguage5
     */
    private String defaultLanguage5;
        
        
    /**
     * defaultLanguage6
     */
    private String defaultLanguage6;
        
        
    /**
     * defaultLanguage7
     */
    private String defaultLanguage7;
        
        
    /**
     * defaultLanguage8
     */
    private String defaultLanguage8;
        
        
    /**
     * defaultLanguage9
     */
    private String defaultLanguage9;
        
        
    /**
     * defaultLanguage10
     */
    private String defaultLanguage10;
        
        
    /**
     * fieldA
     */
    private String fieldA;
        
        
    /**
     * fieldN
     */
    private String fieldN;
        
        
    /**
     * fieldT
     */
    private String fieldT;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * signatureCreation
     */
    private String signatureCreation;
        
        
    /**
     * siteCreation
     */
    private String siteCreation;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * comGroupeType
     */
    private RefComPrefGTypeDTO comGroupeType;
        
        
    /**
     * comType
     */
    private RefComPrefTypeDTO comType;
        
        
    /**
     * domain
     */
    private RefComPrefDomainDTO domain;
        
        
    /**
     * media
     */
    private RefComPrefMediaDTO media;
        

    /*PROTECTED REGION ID(_Y7gEIDSeEeaR_YJoHRGtPg u var) ENABLED START*/
    // add your custom variables here if necessary
    
     /**
   * Determines if a de-serialized file is compatible with this class.
   *
   * Maintainers must change this value if and only if the new version
   * of this class is not compatible with old versions. See Sun docs
   * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
   * /serialization/spec/class.html#4100> details. </a>
   *
   * Not necessary to include in first version of the class, but
   * included here as a reminder of its importance.
   */
    private static final long serialVersionUID = 1L;
    
    /*PROTECTED REGION END*/

    
	    
	    /** 
	     * default constructor 
	     */
	    public RefComPrefDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return comGroupeType
     */
    public RefComPrefGTypeDTO getComGroupeType() {
        return this.comGroupeType;
    }

    /**
     *
     * @param pComGroupeType comGroupeType value
     */
    public void setComGroupeType(RefComPrefGTypeDTO pComGroupeType) {
        this.comGroupeType = pComGroupeType;
    }

    /**
     *
     * @return comType
     */
    public RefComPrefTypeDTO getComType() {
        return this.comType;
    }

    /**
     *
     * @param pComType comType value
     */
    public void setComType(RefComPrefTypeDTO pComType) {
        this.comType = pComType;
    }

    /**
     *
     * @return dateCreation
     */
    public Date getDateCreation() {
        return this.dateCreation;
    }

    /**
     *
     * @param pDateCreation dateCreation value
     */
    public void setDateCreation(Date pDateCreation) {
        this.dateCreation = pDateCreation;
    }

    /**
     *
     * @return dateModification
     */
    public Date getDateModification() {
        return this.dateModification;
    }

    /**
     *
     * @param pDateModification dateModification value
     */
    public void setDateModification(Date pDateModification) {
        this.dateModification = pDateModification;
    }

    /**
     *
     * @return defaultLanguage1
     */
    public String getDefaultLanguage1() {
        return this.defaultLanguage1;
    }

    /**
     *
     * @param pDefaultLanguage1 defaultLanguage1 value
     */
    public void setDefaultLanguage1(String pDefaultLanguage1) {
        this.defaultLanguage1 = pDefaultLanguage1;
    }

    /**
     *
     * @return defaultLanguage10
     */
    public String getDefaultLanguage10() {
        return this.defaultLanguage10;
    }

    /**
     *
     * @param pDefaultLanguage10 defaultLanguage10 value
     */
    public void setDefaultLanguage10(String pDefaultLanguage10) {
        this.defaultLanguage10 = pDefaultLanguage10;
    }

    /**
     *
     * @return defaultLanguage2
     */
    public String getDefaultLanguage2() {
        return this.defaultLanguage2;
    }

    /**
     *
     * @param pDefaultLanguage2 defaultLanguage2 value
     */
    public void setDefaultLanguage2(String pDefaultLanguage2) {
        this.defaultLanguage2 = pDefaultLanguage2;
    }

    /**
     *
     * @return defaultLanguage3
     */
    public String getDefaultLanguage3() {
        return this.defaultLanguage3;
    }

    /**
     *
     * @param pDefaultLanguage3 defaultLanguage3 value
     */
    public void setDefaultLanguage3(String pDefaultLanguage3) {
        this.defaultLanguage3 = pDefaultLanguage3;
    }

    /**
     *
     * @return defaultLanguage4
     */
    public String getDefaultLanguage4() {
        return this.defaultLanguage4;
    }

    /**
     *
     * @param pDefaultLanguage4 defaultLanguage4 value
     */
    public void setDefaultLanguage4(String pDefaultLanguage4) {
        this.defaultLanguage4 = pDefaultLanguage4;
    }

    /**
     *
     * @return defaultLanguage5
     */
    public String getDefaultLanguage5() {
        return this.defaultLanguage5;
    }

    /**
     *
     * @param pDefaultLanguage5 defaultLanguage5 value
     */
    public void setDefaultLanguage5(String pDefaultLanguage5) {
        this.defaultLanguage5 = pDefaultLanguage5;
    }

    /**
     *
     * @return defaultLanguage6
     */
    public String getDefaultLanguage6() {
        return this.defaultLanguage6;
    }

    /**
     *
     * @param pDefaultLanguage6 defaultLanguage6 value
     */
    public void setDefaultLanguage6(String pDefaultLanguage6) {
        this.defaultLanguage6 = pDefaultLanguage6;
    }

    /**
     *
     * @return defaultLanguage7
     */
    public String getDefaultLanguage7() {
        return this.defaultLanguage7;
    }

    /**
     *
     * @param pDefaultLanguage7 defaultLanguage7 value
     */
    public void setDefaultLanguage7(String pDefaultLanguage7) {
        this.defaultLanguage7 = pDefaultLanguage7;
    }

    /**
     *
     * @return defaultLanguage8
     */
    public String getDefaultLanguage8() {
        return this.defaultLanguage8;
    }

    /**
     *
     * @param pDefaultLanguage8 defaultLanguage8 value
     */
    public void setDefaultLanguage8(String pDefaultLanguage8) {
        this.defaultLanguage8 = pDefaultLanguage8;
    }

    /**
     *
     * @return defaultLanguage9
     */
    public String getDefaultLanguage9() {
        return this.defaultLanguage9;
    }

    /**
     *
     * @param pDefaultLanguage9 defaultLanguage9 value
     */
    public void setDefaultLanguage9(String pDefaultLanguage9) {
        this.defaultLanguage9 = pDefaultLanguage9;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     *
     * @param pDescription description value
     */
    public void setDescription(String pDescription) {
        this.description = pDescription;
    }

    /**
     *
     * @return domain
     */
    public RefComPrefDomainDTO getDomain() {
        return this.domain;
    }

    /**
     *
     * @param pDomain domain value
     */
    public void setDomain(RefComPrefDomainDTO pDomain) {
        this.domain = pDomain;
    }

    /**
     *
     * @return fieldA
     */
    public String getFieldA() {
        return this.fieldA;
    }

    /**
     *
     * @param pFieldA fieldA value
     */
    public void setFieldA(String pFieldA) {
        this.fieldA = pFieldA;
    }

    /**
     *
     * @return fieldN
     */
    public String getFieldN() {
        return this.fieldN;
    }

    /**
     *
     * @param pFieldN fieldN value
     */
    public void setFieldN(String pFieldN) {
        this.fieldN = pFieldN;
    }

    /**
     *
     * @return fieldT
     */
    public String getFieldT() {
        return this.fieldT;
    }

    /**
     *
     * @param pFieldT fieldT value
     */
    public void setFieldT(String pFieldT) {
        this.fieldT = pFieldT;
    }

    /**
     *
     * @return mandatoryOptin
     */
    public String getMandatoryOptin() {
        return this.mandatoryOptin;
    }

    /**
     *
     * @param pMandatoryOptin mandatoryOptin value
     */
    public void setMandatoryOptin(String pMandatoryOptin) {
        this.mandatoryOptin = pMandatoryOptin;
    }

    /**
     *
     * @return market
     */
    public String getMarket() {
        return this.market;
    }

    /**
     *
     * @param pMarket market value
     */
    public void setMarket(String pMarket) {
        this.market = pMarket;
    }

    /**
     *
     * @return media
     */
    public RefComPrefMediaDTO getMedia() {
        return this.media;
    }

    /**
     *
     * @param pMedia media value
     */
    public void setMedia(RefComPrefMediaDTO pMedia) {
        this.media = pMedia;
    }

    /**
     *
     * @return refComprefId
     */
    public Integer getRefComprefId() {
        return this.refComprefId;
    }

    /**
     *
     * @param pRefComprefId refComprefId value
     */
    public void setRefComprefId(Integer pRefComprefId) {
        this.refComprefId = pRefComprefId;
    }

    /**
     *
     * @return signatureCreation
     */
    public String getSignatureCreation() {
        return this.signatureCreation;
    }

    /**
     *
     * @param pSignatureCreation signatureCreation value
     */
    public void setSignatureCreation(String pSignatureCreation) {
        this.signatureCreation = pSignatureCreation;
    }

    /**
     *
     * @return signatureModification
     */
    public String getSignatureModification() {
        return this.signatureModification;
    }

    /**
     *
     * @param pSignatureModification signatureModification value
     */
    public void setSignatureModification(String pSignatureModification) {
        this.signatureModification = pSignatureModification;
    }

    /**
     *
     * @return siteCreation
     */
    public String getSiteCreation() {
        return this.siteCreation;
    }

    /**
     *
     * @param pSiteCreation siteCreation value
     */
    public void setSiteCreation(String pSiteCreation) {
        this.siteCreation = pSiteCreation;
    }

    /**
     *
     * @return siteModification
     */
    public String getSiteModification() {
        return this.siteModification;
    }

    /**
     *
     * @param pSiteModification siteModification value
     */
    public void setSiteModification(String pSiteModification) {
        this.siteModification = pSiteModification;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_Y7gEIDSeEeaR_YJoHRGtPg) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
        buffer.append("[");
        buffer.append("refComprefId=").append(getRefComprefId());
        buffer.append(",");
        buffer.append("description=").append(getDescription());
        buffer.append(",");
        buffer.append("mandatoryOptin=").append(getMandatoryOptin());
        buffer.append(",");
        buffer.append("market=").append(getMarket());
        buffer.append(",");
        buffer.append("defaultLanguage1=").append(getDefaultLanguage1());
        buffer.append(",");
        buffer.append("defaultLanguage2=").append(getDefaultLanguage2());
        buffer.append(",");
        buffer.append("defaultLanguage3=").append(getDefaultLanguage3());
        buffer.append(",");
        buffer.append("defaultLanguage4=").append(getDefaultLanguage4());
        buffer.append(",");
        buffer.append("defaultLanguage5=").append(getDefaultLanguage5());
        buffer.append(",");
        buffer.append("defaultLanguage6=").append(getDefaultLanguage6());
        buffer.append(",");
        buffer.append("defaultLanguage7=").append(getDefaultLanguage7());
        buffer.append(",");
        buffer.append("defaultLanguage8=").append(getDefaultLanguage8());
        buffer.append(",");
        buffer.append("defaultLanguage9=").append(getDefaultLanguage9());
        buffer.append(",");
        buffer.append("defaultLanguage10=").append(getDefaultLanguage10());
        buffer.append(",");
        buffer.append("fieldA=").append(getFieldA());
        buffer.append(",");
        buffer.append("fieldN=").append(getFieldN());
        buffer.append(",");
        buffer.append("fieldT=").append(getFieldT());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_Y7gEIDSeEeaR_YJoHRGtPg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
