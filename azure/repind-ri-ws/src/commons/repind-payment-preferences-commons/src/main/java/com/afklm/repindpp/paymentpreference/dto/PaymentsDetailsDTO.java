package com.afklm.repindpp.paymentpreference.dto;

/*PROTECTED REGION ID(_RzGAEFriEeCAX-eiAOZ-9g i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : PaymentsDetailsDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class PaymentsDetailsDTO  {
        
    /**
     * paymentId
     */
    private Integer paymentId;
        
        
    /**
     * gin
     */
    private String gin;
        
        
    /**
     * version
     */
    private Integer version;
        
        
    /**
     * paymentType
     */
    private String paymentType;
        
        
    /**
     * pointOfSell
     */
    private String pointOfSell;
        
        
    /**
     * carrier
     */
    private String carrier;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * signatureCreation
     */
    private String signatureCreation;
        
        
    /**
     * siteCreation
     */
    private String siteCreation;
        
        
    /**
     * changingDate
     */
    private Date changingDate;
        
        
    /**
     * changingSignature
     */
    private String changingSignature;
        
        
    /**
     * changingSite
     */
    private String changingSite;
        
        
    /**
     * paymentGroup
     */
    private String paymentGroup;
        
        
    /**
     * paymentMethod
     */
    private String paymentMethod;
    
    /**
     * preferred
     */
    private String preferred;
    
    /**
     * corporate
     */
    private String corporate;
    
    /**
     * cardName
     */
    private String cardName;
    
    /**
     * isTokenized
     */
    private String isTokenized;
        
        
    /**
     * ipAdresse
     */
    private String ipAdresse;
        
        
    /**
     * fieldsdto
     */
    private Set<FieldsDTO> fieldsdto;
        

    /*PROTECTED REGION ID(_RzGAEFriEeCAX-eiAOZ-9g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public PaymentsDetailsDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pPaymentId paymentId
     * @param pGin gin
     * @param pVersion version
     * @param pPaymentType paymentType
     * @param pPointOfSell pointOfSell
     * @param pCarrier carrier
     * @param pDateCreation dateCreation
     * @param pSignatureCreation signatureCreation
     * @param pSiteCreation siteCreation
     * @param pChangingDate changingDate
     * @param pChangingSignature changingSignature
     * @param pChangingSite changingSite
     * @param pPaymentGroup paymentGroup
     * @param pPaymentMethod paymentMethod
     * @param pIpAdresse ipAdresse
     * @param pIsTokenized isTokenized
     */
    public PaymentsDetailsDTO(Integer pPaymentId, String pGin, Integer pVersion, String pPaymentType, String pPointOfSell, String pCarrier, Date pDateCreation, String pSignatureCreation, String pSiteCreation, Date pChangingDate, String pChangingSignature, String pChangingSite, String pPaymentGroup, String pPaymentMethod, String pIpAdresse,  String pIsTokenized) {
        this.paymentId = pPaymentId;
        this.gin = pGin;
        this.version = pVersion;
        this.paymentType = pPaymentType;
        this.pointOfSell = pPointOfSell;
        this.carrier = pCarrier;
        this.dateCreation = pDateCreation;
        this.signatureCreation = pSignatureCreation;
        this.siteCreation = pSiteCreation;
        this.changingDate = pChangingDate;
        this.changingSignature = pChangingSignature;
        this.changingSite = pChangingSite;
        this.paymentGroup = pPaymentGroup;
        this.paymentMethod = pPaymentMethod;
        this.ipAdresse = pIpAdresse;
        this.isTokenized = pIsTokenized;
    }
    
    /** 
     * full constructor for V2 (with preferred and corporate fields added)
     * @param pPaymentId paymentId
     * @param pGin gin
     * @param pVersion version
     * @param pPaymentType paymentType
     * @param pPointOfSell pointOfSell
     * @param pCarrier carrier
     * @param pDateCreation dateCreation
     * @param pSignatureCreation signatureCreation
     * @param pSiteCreation siteCreation
     * @param pChangingDate changingDate
     * @param pChangingSignature changingSignature
     * @param pChangingSite changingSite
     * @param pPaymentGroup paymentGroup
     * @param pPaymentMethod paymentMethod
     * @param pIpAdresse ipAdresse
     * @param pIsTokenized isTokenized
     */
    public PaymentsDetailsDTO(Integer pPaymentId, String pGin, Integer pVersion, String pPaymentType, String pPointOfSell, String pCarrier, Date pDateCreation, String pSignatureCreation, String pSiteCreation, Date pChangingDate, String pChangingSignature, String pChangingSite, String pPaymentGroup, String pPaymentMethod, String pPreferred, String pCorporate, String pCardName, String pIpAdresse,  String pIsTokenized) {
        this.paymentId = pPaymentId;
        this.gin = pGin;
        this.version = pVersion;
        this.paymentType = pPaymentType;
        this.pointOfSell = pPointOfSell;
        this.carrier = pCarrier;
        this.dateCreation = pDateCreation;
        this.signatureCreation = pSignatureCreation;
        this.siteCreation = pSiteCreation;
        this.changingDate = pChangingDate;
        this.changingSignature = pChangingSignature;
        this.changingSite = pChangingSite;
        this.paymentGroup = pPaymentGroup;
        this.paymentMethod = pPaymentMethod;
        this.preferred = pPreferred;
        this.corporate = pCorporate;
        this.ipAdresse = pIpAdresse;
        this.isTokenized = pIsTokenized;
        this.cardName = pCardName;
    }
    
    /**
     *
     * @return carrier
     */
    public String getCarrier() {
        return this.carrier;
    }

    /**
     *
     * @param pCarrier carrier value
     */
    public void setCarrier(String pCarrier) {
        this.carrier = pCarrier;
    }

    /**
     *
     * @return changingDate
     */
    public Date getChangingDate() {
        return this.changingDate;
    }

    /**
     *
     * @param pChangingDate changingDate value
     */
    public void setChangingDate(Date pChangingDate) {
        this.changingDate = pChangingDate;
    }

    /**
     *
     * @return changingSignature
     */
    public String getChangingSignature() {
        return this.changingSignature;
    }

    /**
     *
     * @param pChangingSignature changingSignature value
     */
    public void setChangingSignature(String pChangingSignature) {
        this.changingSignature = pChangingSignature;
    }

    /**
     *
     * @return changingSite
     */
    public String getChangingSite() {
        return this.changingSite;
    }

    /**
     *
     * @param pChangingSite changingSite value
     */
    public void setChangingSite(String pChangingSite) {
        this.changingSite = pChangingSite;
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
     * @return fieldsdto
     */
    public Set<FieldsDTO> getFieldsdto() {
        return this.fieldsdto;
    }

    /**
     *
     * @param pFieldsdto fieldsdto value
     */
    public void setFieldsdto(Set<FieldsDTO> pFieldsdto) {
        this.fieldsdto = pFieldsdto;
    }

    /**
     *
     * @return gin
     */
    public String getGin() {
        return this.gin;
    }

    /**
     *
     * @param pGin gin value
     */
    public void setGin(String pGin) {
        this.gin = pGin;
    }

    /**
     *
     * @return ipAdresse
     */
    public String getIpAdresse() {
        return this.ipAdresse;
    }

    /**
     *
     * @param pIpAdresse ipAdresse value
     */
    public void setIpAdresse(String pIpAdresse) {
        this.ipAdresse = pIpAdresse;
    }

    /**
     *
     * @return paymentGroup
     */
    public String getPaymentGroup() {
        return this.paymentGroup;
    }

    /**
     *
     * @param pPaymentGroup paymentGroup value
     */
    public void setPaymentGroup(String pPaymentGroup) {
        this.paymentGroup = pPaymentGroup;
    }

    /**
     *
     * @return paymentId
     */
    public Integer getPaymentId() {
        return this.paymentId;
    }

    /**
     *
     * @param pPaymentId paymentId value
     */
    public void setPaymentId(Integer pPaymentId) {
        this.paymentId = pPaymentId;
    }

    /**
     *
     * @return paymentMethod
     */
    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    /**
     *
     * @param pPaymentMethod paymentMethod value
     */
    public void setPaymentMethod(String pPaymentMethod) {
        this.paymentMethod = pPaymentMethod;
    }
    
    /**
    *
    * @return preferred
    */
   public String getPreferred() {
       return this.preferred;
   }

   /**
    *
    * @param pPreferred preferred value
    */
   public void setPreferred(String pPreferred) {
       this.preferred = pPreferred;
   }
    
    /**
    *
    * @return corporate
    */
   public String getCorporate() {
       return this.corporate;
   }

   /**
    *
    * @param pCorporate corporate value
    */
   public void setCorporate(String pCorporate) {
       this.corporate = pCorporate;
   }
   
    /**
    *
    * @return isTokenized
    */
   public String getIsTokenized() {
       return this.isTokenized;
   }

   /**
    *
    * @param pIsTokenized isTokenized value
    */
   public void setIsTokenized(String pIsTokenized) {
       this.isTokenized = pIsTokenized;
   }

    /**
     *
     * @return paymentType
     */
    public String getPaymentType() {
        return this.paymentType;
    }

    /**
     *
     * @param pPaymentType paymentType value
     */
    public void setPaymentType(String pPaymentType) {
        this.paymentType = pPaymentType;
    }

    /**
     *
     * @return pointOfSell
     */
    public String getPointOfSell() {
        return this.pointOfSell;
    }

    /**
     *
     * @param pPointOfSell pointOfSell value
     */
    public void setPointOfSell(String pPointOfSell) {
        this.pointOfSell = pPointOfSell;
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
    * @return cardName
    */
   public String getCardName() {
       return this.cardName;
   }

   /**
    *
    * @param pCardName cardName value
    */
   public void setCardName(String pCardName) {
       this.cardName = pCardName;
   }   
   
    /**
     *
     * @return version
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     *
     * @param pVersion version value
     */
    public void setVersion(Integer pVersion) {
        this.version = pVersion;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_RzGAEFriEeCAX-eiAOZ-9g) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
            .append("paymentId", getPaymentId())
            .append("gin", getGin())
            .append("version", getVersion())
            .append("paymentType", getPaymentType())
            .append("pointOfSell", getPointOfSell())
            .append("carrier", getCarrier())
            .append("dateCreation", getDateCreation())
            .append("signatureCreation", getSignatureCreation())
            .append("siteCreation", getSiteCreation())
            .append("changingDate", getChangingDate())
            .append("changingSignature", getChangingSignature())
            .append("changingSite", getChangingSite())
            .append("paymentGroup", getPaymentGroup())
            .append("paymentMethod", getPaymentMethod())
            .append("preferred", getPreferred())
            .append("corporate", getCorporate())
            .append("cardName", getCardName())
            .append("ipAdresse", getIpAdresse())
            .append("isTokenized", getIsTokenized())
            .toString();
    }

    /*PROTECTED REGION ID(_RzGAEFriEeCAX-eiAOZ-9g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
    
}
