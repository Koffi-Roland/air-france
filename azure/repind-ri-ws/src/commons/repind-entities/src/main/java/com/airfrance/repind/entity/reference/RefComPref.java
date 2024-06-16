package com.airfrance.repind.entity.reference;

/*PROTECTED REGION ID(_XarNwDSiEeaR_YJoHRGtPg i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : RefComPref.java</p>
 * BO RefComPref
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity
@Immutable

@Table(name="VIEW_REF_COMPREF")
public class RefComPref implements Serializable {

/*PROTECTED REGION ID(serialUID _XarNwDSiEeaR_YJoHRGtPg) ENABLED START*/
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
     * refComprefId
     */
    @Id
    @Column(name="REF_COMPREF_ID")
    private Integer refComprefId;
        
            
    /**
     * description
     */
    @Column(name="DESCRIPTION", length=50)
    private String description;
        
            
    /**
     * mandatoryOptin
     */
    @Column(name="MANDATORY_OPTIN", length=30)
    private String mandatoryOptin;
        
            
    /**
     * market
     */
    @Column(name="MARKET", length=3)
    private String market;
        
            
    /**
     * defaultLanguage1
     */
    @Column(name="DEFAULT_LANGUAGE_1", length=2)
    private String defaultLanguage1;
        
            
    /**
     * defaultLanguage2
     */
    @Column(name="DEFAULT_LANGUAGE_2", length=2)
    private String defaultLanguage2;
        
            
    /**
     * defaultLanguage3
     */
    @Column(name="DEFAULT_LANGUAGE_3", length=2)
    private String defaultLanguage3;
        
            
    /**
     * defaultLanguage4
     */
    @Column(name="DEFAULT_LANGUAGE_4", length=2)
    private String defaultLanguage4;
        
            
    /**
     * defaultLanguage5
     */
    @Column(name="DEFAULT_LANGUAGE_5", length=2)
    private String defaultLanguage5;
        
            
    /**
     * defaultLanguage6
     */
    @Column(name="DEFAULT_LANGUAGE_6", length=2)
    private String defaultLanguage6;
        
            
    /**
     * defaultLanguage7
     */
    @Column(name="DEFAULT_LANGUAGE_7", length=2)
    private String defaultLanguage7;
        
            
    /**
     * defaultLanguage8
     */
    @Column(name="DEFAULT_LANGUAGE_8", length=2)
    private String defaultLanguage8;
        
            
    /**
     * defaultLanguage9
     */
    @Column(name="DEFAULT_LANGUAGE_9", length=2)
    private String defaultLanguage9;
        
            
    /**
     * defaultLanguage10
     */
    @Column(name="DEFAULT_LANGUAGE_10", length=2)
    private String defaultLanguage10;
        
            
    /**
     * fieldA
     */
    @Column(name="A", length=1)
    private String fieldA;
        
            
    /**
     * fieldN
     */
    @Column(name="N", length=1)
    private String fieldN;
        
            
    /**
     * fieldT
     */
    @Column(name="T", length=1)
    private String fieldT;
        
            
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
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;
        
            
    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16, nullable=false)
    private String signatureCreation;
        
            
    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10, nullable=false)
    private String siteCreation;
        
            
    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;
        
            
    /**
     * comGroupeType
     */
    // 1 -> 1
    @OneToOne()
    @JoinColumn(name="COM_GROUP_TYPE", nullable=false)
    @ForeignKey(name = "FK_REF_COMPREF_COM_GROUPE_TYPE")
    private RefComPrefGType comGroupeType;
        
            
    /**
     * comType
     */
    // 1 -> 1
    @OneToOne()
    @JoinColumn(name="COM_TYPE", nullable=false)
    @ForeignKey(name = "FK_REF_COMPREF_COM_TYPE")
    private RefComPrefType comType;
        
            
    /**
     * domain
     */
    // 1 -> 1
    @OneToOne()
    @JoinColumn(name="DOMAIN", nullable=false)
    @ForeignKey(name = "FK_REF_COMPREF_DOMAIN")
    private RefComPrefDomain domain;
        
            
    /**
     * media
     */
    // 1 -> 1
    @OneToOne()
    @JoinColumn(name="MEDIA", nullable=true)
    @ForeignKey(name = "FK_REF_COMPREF_MEDIA")
    private RefComPrefMedia media;
        
    /*PROTECTED REGION ID(_XarNwDSiEeaR_YJoHRGtPg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** 
     * default constructor 
     */
    public RefComPref() {
    	//empty constructor
    }
        
    /** 
     * full constructor
     * @param pRefComprefId refComprefId
     * @param pDescription description
     * @param pMandatoryOptin mandatoryOptin
     * @param pMarket market
     * @param pDefaultLanguage1 defaultLanguage1
     * @param pDefaultLanguage2 defaultLanguage2
     * @param pDefaultLanguage3 defaultLanguage3
     * @param pDefaultLanguage4 defaultLanguage4
     * @param pDefaultLanguage5 defaultLanguage5
     * @param pDefaultLanguage6 defaultLanguage6
     * @param pDefaultLanguage7 defaultLanguage7
     * @param pDefaultLanguage8 defaultLanguage8
     * @param pDefaultLanguage9 defaultLanguage9
     * @param pDefaultLanguage10 defaultLanguage10
     * @param pFieldA fieldA
     * @param pFieldN fieldN
     * @param pFieldT fieldT
     * @param pSignatureModification signatureModification
     * @param pSiteModification siteModification
     * @param pDateModification dateModification
     * @param pSignatureCreation signatureCreation
     * @param pSiteCreation siteCreation
     * @param pDateCreation dateCreation
     */
    public RefComPref(Integer pRefComprefId, String pDescription, String pMandatoryOptin, String pMarket, String pDefaultLanguage1, String pDefaultLanguage2, String pDefaultLanguage3, String pDefaultLanguage4, String pDefaultLanguage5, String pDefaultLanguage6, String pDefaultLanguage7, String pDefaultLanguage8, String pDefaultLanguage9, String pDefaultLanguage10, String pFieldA, String pFieldN, String pFieldT, String pSignatureModification, String pSiteModification, Date pDateModification, String pSignatureCreation, String pSiteCreation, Date pDateCreation) {
        this.refComprefId = pRefComprefId;
        this.description = pDescription;
        this.mandatoryOptin = pMandatoryOptin;
        this.market = pMarket;
        this.defaultLanguage1 = pDefaultLanguage1;
        this.defaultLanguage2 = pDefaultLanguage2;
        this.defaultLanguage3 = pDefaultLanguage3;
        this.defaultLanguage4 = pDefaultLanguage4;
        this.defaultLanguage5 = pDefaultLanguage5;
        this.defaultLanguage6 = pDefaultLanguage6;
        this.defaultLanguage7 = pDefaultLanguage7;
        this.defaultLanguage8 = pDefaultLanguage8;
        this.defaultLanguage9 = pDefaultLanguage9;
        this.defaultLanguage10 = pDefaultLanguage10;
        this.fieldA = pFieldA;
        this.fieldN = pFieldN;
        this.fieldT = pFieldT;
        this.signatureModification = pSignatureModification;
        this.siteModification = pSiteModification;
        this.dateModification = pDateModification;
        this.signatureCreation = pSignatureCreation;
        this.siteCreation = pSiteCreation;
        this.dateCreation = pDateCreation;
    }

    /**
     *
     * @return comGroupeType
     */
    public RefComPrefGType getComGroupeType() {
        return this.comGroupeType;
    }

    /**
     *
     * @param pComGroupeType comGroupeType value
     */
    public void setComGroupeType(RefComPrefGType pComGroupeType) {
        this.comGroupeType = pComGroupeType;
    }

    /**
     *
     * @return comType
     */
    public RefComPrefType getComType() {
        return this.comType;
    }

    /**
     *
     * @param pComType comType value
     */
    public void setComType(RefComPrefType pComType) {
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
    public RefComPrefDomain getDomain() {
        return this.domain;
    }

    /**
     *
     * @param pDomain domain value
     */
    public void setDomain(RefComPrefDomain pDomain) {
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
    public RefComPrefMedia getMedia() {
        return this.media;
    }

    /**
     *
     * @param pMedia media value
     */
    public void setMedia(RefComPrefMedia pMedia) {
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
        /*PROTECTED REGION ID(toString_XarNwDSiEeaR_YJoHRGtPg) ENABLED START*/
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

     
    
    /*PROTECTED REGION ID(equals hash _XarNwDSiEeaR_YJoHRGtPg) ENABLED START*/
    
    /**
     * {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        //TODO Customize here if necessary
        return hashCodeImpl();
    }

    /**
     * {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        //TODO Customize here if necessary
        return equalsImpl(obj);
    }
    
    /*PROTECTED REGION END*/
    
    /**
     * Generated implementation method for hashCode
     * You can stop calling it in the hashCode() generated in protected region if necessary
     * @return hashcode
     */
    private int hashCodeImpl() {
        return super.hashCode();
    }

    /**
     * Generated implementation method for equals
     * You can stop calling it in the equals() generated in protected region if necessary
     * @return if param equals the current object
     * @param obj Object to compare with current
     */
    private boolean equalsImpl(Object obj) {
        return super.equals(obj);
    }

    /*PROTECTED REGION ID(_XarNwDSiEeaR_YJoHRGtPg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
