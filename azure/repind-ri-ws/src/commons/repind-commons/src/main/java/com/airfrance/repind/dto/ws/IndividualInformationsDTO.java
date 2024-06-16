package com.airfrance.repind.dto.ws;

/*PROTECTED REGION ID(_DihtkNUrEeef5oRB6XPNlw i) ENABLED START*/

// add not generated imports here

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : IndividualInformationsDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
	public  class IndividualInformationsDTO implements Serializable {


        
    /**
     * identifier
     */
    private String identifier;
        
        
    /**
     * version
     */
    private String version;
        
        
    /**
     * gender
     */
    private String gender;
        
        
    /**
     * personalIdentifier
     */
    private String personalIdentifier;
        
        
    /**
     * birthDate
     */
    private Date birthDate;
        
        
    /**
     * nationality
     */
    private String nationality;
        
        
    /**
     * secondNationality
     */
    private String secondNationality;
        
        
    /**
     * secondFirstName
     */
    private String secondFirstName;
        
        
    /**
     * flagNoFusion
     */
    private Boolean flagNoFusion;
        
        
    /**
     * status
     */
    private String status;
        
        
    /**
     * flagThirdTrap
     */
    private Boolean flagThirdTrap;
        
        
    /**
     * civility
     */
    private String civility;
        
        
    /**
     * lastNamePseudonym
     */
    private String lastNamePseudonym;
        
        
    /**
     * firstNamePseudonym
     */
    private String firstNamePseudonym;
        
        
    /**
     * lastNameSC
     */
    private String lastNameSC;
        
        
    /**
     * firstNameSC
     */
    private String firstNameSC;
        
        
    /**
     * middleNameSC
     */
    private String middleNameSC;
        
        
    /**
     * languageCode
     */
    private String languageCode;
        
        
    /**
     * populationType
     */
    private String populationType;
        

    /*PROTECTED REGION ID(_DihtkNUrEeef5oRB6XPNlw u var) ENABLED START*/
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
	    public IndividualInformationsDTO() {
	    //empty constructor
	    }
	    
    /**
     *
     * @return birthDate
     */
    public Date getBirthDate() {
        return this.birthDate;
    }

    /**
     *
     * @param pBirthDate birthDate value
     */
    public void setBirthDate(Date pBirthDate) {
        this.birthDate = pBirthDate;
    }

    /**
     *
     * @return civility
     */
    public String getCivility() {
        return this.civility;
    }

    /**
     *
     * @param pCivility civility value
     */
    public void setCivility(String pCivility) {
        this.civility = pCivility;
    }

    /**
     *
     * @return firstNamePseudonym
     */
    public String getFirstNamePseudonym() {
        return this.firstNamePseudonym;
    }

    /**
     *
     * @param pFirstNamePseudonym firstNamePseudonym value
     */
    public void setFirstNamePseudonym(String pFirstNamePseudonym) {
        this.firstNamePseudonym = pFirstNamePseudonym;
    }

    /**
     *
     * @return firstNameSC
     */
    public String getFirstNameSC() {
        return this.firstNameSC;
    }

    /**
     *
     * @param pFirstNameSC firstNameSC value
     */
    public void setFirstNameSC(String pFirstNameSC) {
        this.firstNameSC = pFirstNameSC;
    }

    /**
     *
     * @return flagNoFusion
     */
    public Boolean getFlagNoFusion() {
        return this.flagNoFusion;
    }

    /**
     *
     * @param pFlagNoFusion flagNoFusion value
     */
    public void setFlagNoFusion(Boolean pFlagNoFusion) {
        this.flagNoFusion = pFlagNoFusion;
    }

    /**
     *
     * @return flagThirdTrap
     */
    public Boolean getFlagThirdTrap() {
        return this.flagThirdTrap;
    }

    /**
     *
     * @param pFlagThirdTrap flagThirdTrap value
     */
    public void setFlagThirdTrap(Boolean pFlagThirdTrap) {
        this.flagThirdTrap = pFlagThirdTrap;
    }

    /**
     *
     * @return gender
     */
    public String getGender() {
        return this.gender;
    }

    /**
     *
     * @param pGender gender value
     */
    public void setGender(String pGender) {
        this.gender = pGender;
    }

    /**
     *
     * @return identifier
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     *
     * @param pIdentifier identifier value
     */
    public void setIdentifier(String pIdentifier) {
        this.identifier = pIdentifier;
    }

    /**
     *
     * @return languageCode
     */
    public String getLanguageCode() {
        return this.languageCode;
    }

    /**
     *
     * @param pLanguageCode languageCode value
     */
    public void setLanguageCode(String pLanguageCode) {
        this.languageCode = pLanguageCode;
    }

    /**
     *
     * @return lastNamePseudonym
     */
    public String getLastNamePseudonym() {
        return this.lastNamePseudonym;
    }

    /**
     *
     * @param pLastNamePseudonym lastNamePseudonym value
     */
    public void setLastNamePseudonym(String pLastNamePseudonym) {
        this.lastNamePseudonym = pLastNamePseudonym;
    }

    /**
     *
     * @return lastNameSC
     */
    public String getLastNameSC() {
        return this.lastNameSC;
    }

    /**
     *
     * @param pLastNameSC lastNameSC value
     */
    public void setLastNameSC(String pLastNameSC) {
        this.lastNameSC = pLastNameSC;
    }

    /**
     *
     * @return middleNameSC
     */
    public String getMiddleNameSC() {
        return this.middleNameSC;
    }

    /**
     *
     * @param pMiddleNameSC middleNameSC value
     */
    public void setMiddleNameSC(String pMiddleNameSC) {
        this.middleNameSC = pMiddleNameSC;
    }

    /**
     *
     * @return nationality
     */
    public String getNationality() {
        return this.nationality;
    }

    /**
     *
     * @param pNationality nationality value
     */
    public void setNationality(String pNationality) {
        this.nationality = pNationality;
    }

    /**
     *
     * @return personalIdentifier
     */
    public String getPersonalIdentifier() {
        return this.personalIdentifier;
    }

    /**
     *
     * @param pPersonalIdentifier personalIdentifier value
     */
    public void setPersonalIdentifier(String pPersonalIdentifier) {
        this.personalIdentifier = pPersonalIdentifier;
    }

    /**
     *
     * @return populationType
     */
    public String getPopulationType() {
        return this.populationType;
    }

    /**
     *
     * @param pPopulationType populationType value
     */
    public void setPopulationType(String pPopulationType) {
        this.populationType = pPopulationType;
    }

    /**
     *
     * @return secondFirstName
     */
    public String getSecondFirstName() {
        return this.secondFirstName;
    }

    /**
     *
     * @param pSecondFirstName secondFirstName value
     */
    public void setSecondFirstName(String pSecondFirstName) {
        this.secondFirstName = pSecondFirstName;
    }

    /**
     *
     * @return secondNationality
     */
    public String getSecondNationality() {
        return this.secondNationality;
    }

    /**
     *
     * @param pSecondNationality secondNationality value
     */
    public void setSecondNationality(String pSecondNationality) {
        this.secondNationality = pSecondNationality;
    }

    /**
     *
     * @return status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     *
     * @param pStatus status value
     */
    public void setStatus(String pStatus) {
        this.status = pStatus;
    }

    /**
     *
     * @return version
     */
    public String getVersion() {
        return this.version;
    }

    /**
     *
     * @param pVersion version value
     */
    public void setVersion(String pVersion) {
        this.version = pVersion;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_DihtkNUrEeef5oRB6XPNlw) ENABLED START*/
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
        buffer.append("identifier=").append(getIdentifier());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("gender=").append(getGender());
        buffer.append(",");
        buffer.append("personalIdentifier=").append(getPersonalIdentifier());
        buffer.append(",");
        buffer.append("birthDate=").append(getBirthDate());
        buffer.append(",");
        buffer.append("nationality=").append(getNationality());
        buffer.append(",");
        buffer.append("secondNationality=").append(getSecondNationality());
        buffer.append(",");
        buffer.append("secondFirstName=").append(getSecondFirstName());
        buffer.append(",");
        buffer.append("flagNoFusion=").append(getFlagNoFusion());
        buffer.append(",");
        buffer.append("status=").append(getStatus());
        buffer.append(",");
        buffer.append("flagThirdTrap=").append(getFlagThirdTrap());
        buffer.append(",");
        buffer.append("civility=").append(getCivility());
        buffer.append(",");
        buffer.append("lastNamePseudonym=").append(getLastNamePseudonym());
        buffer.append(",");
        buffer.append("firstNamePseudonym=").append(getFirstNamePseudonym());
        buffer.append(",");
        buffer.append("lastNameSC=").append(getLastNameSC());
        buffer.append(",");
        buffer.append("firstNameSC=").append(getFirstNameSC());
        buffer.append(",");
        buffer.append("middleNameSC=").append(getMiddleNameSC());
        buffer.append(",");
        buffer.append("languageCode=").append(getLanguageCode());
        buffer.append(",");
        buffer.append("populationType=").append(getPopulationType());
        buffer.append("]");
        return buffer.toString();
    }

    /*PROTECTED REGION ID(_DihtkNUrEeef5oRB6XPNlw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
