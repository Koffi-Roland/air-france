package com.airfrance.repind.dto.tracking;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : TrackingDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class TrackingDTO  {
        
    /**
     * IdTracking
     */
    private Long IdTracking;
        
        
    /**
     * identificationNumber
     */
    private String identificationNumber;
        
        
    /**
     * Ip_Adresse
     */
    private String Ip_Adresse;
        
        
    /**
     * Matricule
     */
    private String Matricule;
        
        
    /**
     * AFR
     */
    private String AFR;
        
        
    /**
     * initialValue
     */
    private String initialValue;
        
        
    /**
     * newValue
     */
    private String newValue;
        
        
    /**
     * sdateModification
     */
    private Date sdateModification;
        
        
    /**
     * action
     */
    private String action;
        
        
    /**
     * Type
     */
    private String Type;
        

    /*PROTECTED REGION ID(_IogLkBXpEeGRl-H7-h2ImA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public TrackingDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pIdTracking IdTracking
     * @param pIdentificationNumber identificationNumber
     * @param pIp_Adresse Ip_Adresse
     * @param pMatricule Matricule
     * @param pAFR AFR
     * @param pInitialValue initialValue
     * @param pNewValue newValue
     * @param pSdateModification sdateModification
     * @param pAction action
     * @param pType Type
     */
    public TrackingDTO(Long pIdTracking, String pIdentificationNumber, String pIp_Adresse, String pMatricule, String pAFR, String pInitialValue, String pNewValue, Date pSdateModification, String pAction, String pType) {
        this.IdTracking = pIdTracking;
        this.identificationNumber = pIdentificationNumber;
        this.Ip_Adresse = pIp_Adresse;
        this.Matricule = pMatricule;
        this.AFR = pAFR;
        this.initialValue = pInitialValue;
        this.newValue = pNewValue;
        this.sdateModification = pSdateModification;
        this.action = pAction;
        this.Type = pType;
    }

    /**
     *
     * @return AFR
     */
    public String getAFR() {
        return this.AFR;
    }

    /**
     *
     * @param pAFR AFR value
     */
    public void setAFR(String pAFR) {
        this.AFR = pAFR;
    }

    /**
     *
     * @return IdTracking
     */
    public Long getIdTracking() {
        return this.IdTracking;
    }

    /**
     *
     * @param pIdTracking IdTracking value
     */
    public void setIdTracking(Long pIdTracking) {
        this.IdTracking = pIdTracking;
    }

    /**
     *
     * @return Ip_Adresse
     */
    public String getIp_Adresse() {
        return this.Ip_Adresse;
    }

    /**
     *
     * @param pIp_Adresse Ip_Adresse value
     */
    public void setIp_Adresse(String pIp_Adresse) {
        this.Ip_Adresse = pIp_Adresse;
    }

    /**
     *
     * @return Matricule
     */
    public String getMatricule() {
        return this.Matricule;
    }

    /**
     *
     * @param pMatricule Matricule value
     */
    public void setMatricule(String pMatricule) {
        this.Matricule = pMatricule;
    }

    /**
     *
     * @return Type
     */
    public String getType() {
        return this.Type;
    }

    /**
     *
     * @param pType Type value
     */
    public void setType(String pType) {
        this.Type = pType;
    }

    /**
     *
     * @return action
     */
    public String getAction() {
        return this.action;
    }

    /**
     *
     * @param pAction action value
     */
    public void setAction(String pAction) {
        this.action = pAction;
    }

    /**
     *
     * @return identificationNumber
     */
    public String getIdentificationNumber() {
        return this.identificationNumber;
    }

    /**
     *
     * @param pIdentificationNumber identificationNumber value
     */
    public void setIdentificationNumber(String pIdentificationNumber) {
        this.identificationNumber = pIdentificationNumber;
    }

    /**
     *
     * @return initialValue
     */
    public String getInitialValue() {
        return this.initialValue;
    }

    /**
     *
     * @param pInitialValue initialValue value
     */
    public void setInitialValue(String pInitialValue) {
        this.initialValue = pInitialValue;
    }

    /**
     *
     * @return newValue
     */
    public String getNewValue() {
        return this.newValue;
    }

    /**
     *
     * @param pNewValue newValue value
     */
    public void setNewValue(String pNewValue) {
        this.newValue = pNewValue;
    }

    /**
     *
     * @return sdateModification
     */
    public Date getSdateModification() {
        return this.sdateModification;
    }

    /**
     *
     * @param pSdateModification sdateModification value
     */
    public void setSdateModification(Date pSdateModification) {
        this.sdateModification = pSdateModification;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_IogLkBXpEeGRl-H7-h2ImA) ENABLED START*/
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
            .append("IdTracking", getIdTracking())
            .append("identificationNumber", getIdentificationNumber())
            .append("Ip_Adresse", getIp_Adresse())
            .append("Matricule", getMatricule())
            .append("AFR", getAFR())
            .append("initialValue", getInitialValue())
            .append("newValue", getNewValue())
            .append("sdateModification", getSdateModification())
            .append("action", getAction())
            .append("Type", getType())
            .toString();
    }

    /*PROTECTED REGION ID(_IogLkBXpEeGRl-H7-h2ImA u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
