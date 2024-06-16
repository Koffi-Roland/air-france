package com.afklm.rigui.dto.individu;

/*PROTECTED REGION ID(_couCUDLAEeCru6uc_4I2vA i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/*PROTECTED REGION END*/

/**
 * <p>Title : AccountDataDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class AccountDataDTO  {
        
    /**
     * id
     */
    private Integer id;
        
        
    /**
     * version
     */
    private Integer version;
        
        
    /**
     * accountIdentifier
     */
    private String accountIdentifier;
        
        
    /**
     * sgin
     */
    private String sgin;
        
        
    /**
     * fbIdentifier
     */
    private String fbIdentifier;
        
        
    /**
     * emailIdentifier
     */
    private String emailIdentifier;
        
        
    /**
     * personnalizedIdentifier
     */
    private String personnalizedIdentifier;
        
        
    /**
     * status
     */
    private String status;
        
        
    /**
     * password
     */
    private String password;
        
        
    /**
     * passwordToChange
     */
    private Integer passwordToChange;
        
        
    /**
     * temporaryPwd
     */
    private String temporaryPwd;
        
        
    /**
     * temporaryPwdEndDate
     */
    private Date temporaryPwdEndDate;
        
        
    /**
     * secretQuestion
     */
    private String secretQuestion;
        
        
    /**
     * secretQuestionAnswer
     */
    private String secretQuestionAnswer;
        
        
    /**
     * nbFailureAuthentification
     */
    private Integer nbFailureAuthentification;
        
        
    /**
     * nbFailureSecretQuestionAns
     */
    private Integer nbFailureSecretQuestionAns;
        
        
    /**
     * enrolmentPointOfSell
     */
    private String enrolmentPointOfSell;
        
        
    /**
     * carrier
     */
    private String carrier;
        
        
    /**
     * lastConnexionDate
     */
    private Date lastConnexionDate;
        
        
    /**
     * lastPwdResetDate
     */
    private Date lastPwdResetDate;
        
        
    /**
     * accountDeletionDate
     */
    private Date accountDeletionDate;
        
        
    /**
     * siteCreation
     */
    private String siteCreation;
        
        
    /**
     * signatureCreation
     */
    private String signatureCreation;
        
        
    /**
     * dateCreation
     */
    private Date dateCreation;
        
        
    /**
     * siteModification
     */
    private String siteModification;
        
        
    /**
     * signatureModification
     */
    private String signatureModification;
        
        
    /**
     * dateModification
     */
    private Date dateModification;
        
        
    /**
     * ipAddress
     */
    private String ipAddress;
        
        
    /**
     * accountUpgradeDate
     */
    private Date accountUpgradeDate;
        
        
    /**
     * accountLockedDate
     */
    private Date accountLockedDate;
        
        
    /**
     * secretQuestionAnswerUpper
     */
    private String secretQuestionAnswerUpper;
        
        
    /**
     * lastSecretAnswModification
     */
    private Date lastSecretAnswModification;
        
        
    /**
     * socialNetworkId
     */
    private String socialNetworkId;
        
        
    /**
     * lastSocialNetworkLogonDate
     */
    private Date lastSocialNetworkLogonDate;
        
        
    /**
     * lastSocialNetworkUsed
     */
    private String lastSocialNetworkUsed;
        
        
    /**
     * lastSocialNetworkId
     */
    private String lastSocialNetworkId;
        
        
    /**
     * individudto
     */
    private IndividuDTO individudto;

    /*PROTECTED REGION ID(_couCUDLAEeCru6uc_4I2vA u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public AccountDataDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pId id
     * @param pVersion version
     * @param pAccountIdentifier accountIdentifier
     * @param pSgin sgin
     * @param pFbIdentifier fbIdentifier
     * @param pEmailIdentifier emailIdentifier
     * @param pPersonnalizedIdentifier personnalizedIdentifier
     * @param pStatus status
     * @param pPassword password
     * @param pPasswordToChange passwordToChange
     * @param pTemporaryPwd temporaryPwd
     * @param pTemporaryPwdEndDate temporaryPwdEndDate
     * @param pSecretQuestion secretQuestion
     * @param pSecretQuestionAnswer secretQuestionAnswer
     * @param pNbFailureAuthentification nbFailureAuthentification
     * @param pNbFailureSecretQuestionAns nbFailureSecretQuestionAns
     * @param pEnrolmentPointOfSell enrolmentPointOfSell
     * @param pCarrier carrier
     * @param pLastConnexionDate lastConnexionDate
     * @param pLastPwdResetDate lastPwdResetDate
     * @param pAccountDeletionDate accountDeletionDate
     * @param pSiteCreation siteCreation
     * @param pSignatureCreation signatureCreation
     * @param pDateCreation dateCreation
     * @param pSiteModification siteModification
     * @param pSignatureModification signatureModification
     * @param pDateModification dateModification
     * @param pIpAddress ipAddress
     * @param pAccountUpgradeDate accountUpgradeDate
     * @param pAccountLockedDate accountLockedDate
     * @param pSecretQuestionAnswerUpper secretQuestionAnswerUpper
     * @param pLastSecretAnswModification lastSecretAnswModification
     * @param pSocialNetworkId socialNetworkId
     * @param pLastSocialNetworkLogonDate lastSocialNetworkLogonDate
     * @param pLastSocialNetworkUsed lastSocialNetworkUsed
     * @param pLastSocialNetworkId lastSocialNetworkId
     */
    public AccountDataDTO(Integer pId, Integer pVersion, String pAccountIdentifier, String pSgin, String pFbIdentifier, String pEmailIdentifier, String pPersonnalizedIdentifier, String pStatus, String pPassword, Integer pPasswordToChange, String pTemporaryPwd, Date pTemporaryPwdEndDate, String pSecretQuestion, String pSecretQuestionAnswer, Integer pNbFailureAuthentification, Integer pNbFailureSecretQuestionAns, String pEnrolmentPointOfSell, String pCarrier, Date pLastConnexionDate, Date pLastPwdResetDate, Date pAccountDeletionDate, String pSiteCreation, String pSignatureCreation, Date pDateCreation, String pSiteModification, String pSignatureModification, Date pDateModification, String pIpAddress, Date pAccountUpgradeDate, Date pAccountLockedDate, String pSecretQuestionAnswerUpper, Date pLastSecretAnswModification, String pSocialNetworkId, Date pLastSocialNetworkLogonDate, String pLastSocialNetworkUsed, String pLastSocialNetworkId) {
        this.id = pId;
        this.version = pVersion;
        this.accountIdentifier = pAccountIdentifier;
        this.sgin = pSgin;
        this.fbIdentifier = pFbIdentifier;
        this.emailIdentifier = pEmailIdentifier;
        this.personnalizedIdentifier = pPersonnalizedIdentifier;
        this.status = pStatus;
        this.password = pPassword;
        this.passwordToChange = pPasswordToChange;
        this.temporaryPwd = pTemporaryPwd;
        this.temporaryPwdEndDate = pTemporaryPwdEndDate;
        this.secretQuestion = pSecretQuestion;
        this.secretQuestionAnswer = pSecretQuestionAnswer;
        this.nbFailureAuthentification = pNbFailureAuthentification;
        this.nbFailureSecretQuestionAns = pNbFailureSecretQuestionAns;
        this.enrolmentPointOfSell = pEnrolmentPointOfSell;
        this.carrier = pCarrier;
        this.lastConnexionDate = pLastConnexionDate;
        this.lastPwdResetDate = pLastPwdResetDate;
        this.accountDeletionDate = pAccountDeletionDate;
        this.siteCreation = pSiteCreation;
        this.signatureCreation = pSignatureCreation;
        this.dateCreation = pDateCreation;
        this.siteModification = pSiteModification;
        this.signatureModification = pSignatureModification;
        this.dateModification = pDateModification;
        this.ipAddress = pIpAddress;
        this.accountUpgradeDate = pAccountUpgradeDate;
        this.accountLockedDate = pAccountLockedDate;
        this.secretQuestionAnswerUpper = pSecretQuestionAnswerUpper;
        this.lastSecretAnswModification = pLastSecretAnswModification;
        this.socialNetworkId = pSocialNetworkId;
        this.lastSocialNetworkLogonDate = pLastSocialNetworkLogonDate;
        this.lastSocialNetworkUsed = pLastSocialNetworkUsed;
        this.lastSocialNetworkId = pLastSocialNetworkId;
    }

    /**
     *
     * @return accountDeletionDate
     */
    public Date getAccountDeletionDate() {
        return this.accountDeletionDate;
    }

    /**
     *
     * @param pAccountDeletionDate accountDeletionDate value
     */
    public void setAccountDeletionDate(Date pAccountDeletionDate) {
        this.accountDeletionDate = pAccountDeletionDate;
    }

    /**
     *
     * @return accountIdentifier
     */
    public String getAccountIdentifier() {
        return this.accountIdentifier;
    }

    /**
     *
     * @param pAccountIdentifier accountIdentifier value
     */
    public void setAccountIdentifier(String pAccountIdentifier) {
        this.accountIdentifier = pAccountIdentifier;
    }

    /**
     *
     * @return accountLockedDate
     */
    public Date getAccountLockedDate() {
        return this.accountLockedDate;
    }

    /**
     *
     * @param pAccountLockedDate accountLockedDate value
     */
    public void setAccountLockedDate(Date pAccountLockedDate) {
        this.accountLockedDate = pAccountLockedDate;
    }

    /**
     *
     * @return accountUpgradeDate
     */
    public Date getAccountUpgradeDate() {
        return this.accountUpgradeDate;
    }

    /**
     *
     * @param pAccountUpgradeDate accountUpgradeDate value
     */
    public void setAccountUpgradeDate(Date pAccountUpgradeDate) {
        this.accountUpgradeDate = pAccountUpgradeDate;
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
     * @return emailIdentifier
     */
    public String getEmailIdentifier() {
        return this.emailIdentifier;
    }

    /**
     *
     * @param pEmailIdentifier emailIdentifier value
     */
    public void setEmailIdentifier(String pEmailIdentifier) {
        this.emailIdentifier = pEmailIdentifier;
    }

    /**
     *
     * @return enrolmentPointOfSell
     */
    public String getEnrolmentPointOfSell() {
        return this.enrolmentPointOfSell;
    }

    /**
     *
     * @param pEnrolmentPointOfSell enrolmentPointOfSell value
     */
    public void setEnrolmentPointOfSell(String pEnrolmentPointOfSell) {
        this.enrolmentPointOfSell = pEnrolmentPointOfSell;
    }

    /**
     *
     * @return fbIdentifier
     */
    public String getFbIdentifier() {
        return this.fbIdentifier;
    }

    /**
     *
     * @param pFbIdentifier fbIdentifier value
     */
    public void setFbIdentifier(String pFbIdentifier) {
        this.fbIdentifier = pFbIdentifier;
    }

    /**
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     *
     * @param pId id value
     */
    public void setId(Integer pId) {
        this.id = pId;
    }

    /**
     *
     * @return individudto
     */
    public IndividuDTO getIndividudto() {
        return this.individudto;
    }

    /**
     *
     * @param pIndividudto individudto value
     */
    public void setIndividudto(IndividuDTO pIndividudto) {
        this.individudto = pIndividudto;
    }

    /**
     *
     * @return ipAddress
     */
    public String getIpAddress() {
        return this.ipAddress;
    }

    /**
     *
     * @param pIpAddress ipAddress value
     */
    public void setIpAddress(String pIpAddress) {
        this.ipAddress = pIpAddress;
    }

    /**
     *
     * @return lastConnexionDate
     */
    public Date getLastConnexionDate() {
        return this.lastConnexionDate;
    }

    /**
     *
     * @param pLastConnexionDate lastConnexionDate value
     */
    public void setLastConnexionDate(Date pLastConnexionDate) {
        this.lastConnexionDate = pLastConnexionDate;
    }

    /**
     *
     * @return lastPwdResetDate
     */
    public Date getLastPwdResetDate() {
        return this.lastPwdResetDate;
    }

    /**
     *
     * @param pLastPwdResetDate lastPwdResetDate value
     */
    public void setLastPwdResetDate(Date pLastPwdResetDate) {
        this.lastPwdResetDate = pLastPwdResetDate;
    }

    /**
     *
     * @return lastSecretAnswModification
     */
    public Date getLastSecretAnswModification() {
        return this.lastSecretAnswModification;
    }

    /**
     *
     * @param pLastSecretAnswModification lastSecretAnswModification value
     */
    public void setLastSecretAnswModification(Date pLastSecretAnswModification) {
        this.lastSecretAnswModification = pLastSecretAnswModification;
    }

    /**
     *
     * @return lastSocialNetworkId
     */
    public String getLastSocialNetworkId() {
        return this.lastSocialNetworkId;
    }

    /**
     *
     * @param pLastSocialNetworkId lastSocialNetworkId value
     */
    public void setLastSocialNetworkId(String pLastSocialNetworkId) {
        this.lastSocialNetworkId = pLastSocialNetworkId;
    }

    /**
     *
     * @return lastSocialNetworkLogonDate
     */
    public Date getLastSocialNetworkLogonDate() {
        return this.lastSocialNetworkLogonDate;
    }

    /**
     *
     * @param pLastSocialNetworkLogonDate lastSocialNetworkLogonDate value
     */
    public void setLastSocialNetworkLogonDate(Date pLastSocialNetworkLogonDate) {
        this.lastSocialNetworkLogonDate = pLastSocialNetworkLogonDate;
    }

    /**
     *
     * @return lastSocialNetworkUsed
     */
    public String getLastSocialNetworkUsed() {
        return this.lastSocialNetworkUsed;
    }

    /**
     *
     * @param pLastSocialNetworkUsed lastSocialNetworkUsed value
     */
    public void setLastSocialNetworkUsed(String pLastSocialNetworkUsed) {
        this.lastSocialNetworkUsed = pLastSocialNetworkUsed;
    }

    /**
     *
     * @return nbFailureAuthentification
     */
    public Integer getNbFailureAuthentification() {
        return this.nbFailureAuthentification;
    }

    /**
     *
     * @param pNbFailureAuthentification nbFailureAuthentification value
     */
    public void setNbFailureAuthentification(Integer pNbFailureAuthentification) {
        this.nbFailureAuthentification = pNbFailureAuthentification;
    }

    /**
     *
     * @return nbFailureSecretQuestionAns
     */
    public Integer getNbFailureSecretQuestionAns() {
        return this.nbFailureSecretQuestionAns;
    }

    /**
     *
     * @param pNbFailureSecretQuestionAns nbFailureSecretQuestionAns value
     */
    public void setNbFailureSecretQuestionAns(Integer pNbFailureSecretQuestionAns) {
        this.nbFailureSecretQuestionAns = pNbFailureSecretQuestionAns;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     *
     * @param pPassword password value
     */
    public void setPassword(String pPassword) {
        this.password = pPassword;
    }

    /**
     *
     * @return passwordToChange
     */
    public Integer getPasswordToChange() {
        return this.passwordToChange;
    }

    /**
     *
     * @param pPasswordToChange passwordToChange value
     */
    public void setPasswordToChange(Integer pPasswordToChange) {
        this.passwordToChange = pPasswordToChange;
    }

    /**
     *
     * @return personnalizedIdentifier
     */
    public String getPersonnalizedIdentifier() {
        return this.personnalizedIdentifier;
    }

    /**
     *
     * @param pPersonnalizedIdentifier personnalizedIdentifier value
     */
    public void setPersonnalizedIdentifier(String pPersonnalizedIdentifier) {
        this.personnalizedIdentifier = pPersonnalizedIdentifier;
    }

    /**
     *
     * @return secretQuestion
     */
    public String getSecretQuestion() {
        return this.secretQuestion;
    }

    /**
     *
     * @param pSecretQuestion secretQuestion value
     */
    public void setSecretQuestion(String pSecretQuestion) {
        this.secretQuestion = pSecretQuestion;
    }

    /**
     *
     * @return secretQuestionAnswer
     */
    public String getSecretQuestionAnswer() {
        return this.secretQuestionAnswer;
    }

    /**
     *
     * @param pSecretQuestionAnswer secretQuestionAnswer value
     */
    public void setSecretQuestionAnswer(String pSecretQuestionAnswer) {
        this.secretQuestionAnswer = pSecretQuestionAnswer;
    }

    /**
     *
     * @return secretQuestionAnswerUpper
     */
    public String getSecretQuestionAnswerUpper() {
        return this.secretQuestionAnswerUpper;
    }

    /**
     *
     * @param pSecretQuestionAnswerUpper secretQuestionAnswerUpper value
     */
    public void setSecretQuestionAnswerUpper(String pSecretQuestionAnswerUpper) {
        this.secretQuestionAnswerUpper = pSecretQuestionAnswerUpper;
    }

    /**
     *
     * @return sgin
     */
    public String getSgin() {
        return this.sgin;
    }

    /**
     *
     * @param pSgin sgin value
     */
    public void setSgin(String pSgin) {
        this.sgin = pSgin;
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
     * @return socialNetworkId
     */
    public String getSocialNetworkId() {
        return this.socialNetworkId;
    }

    /**
     *
     * @param pSocialNetworkId socialNetworkId value
     */
    public void setSocialNetworkId(String pSocialNetworkId) {
        this.socialNetworkId = pSocialNetworkId;
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
     * @return temporaryPwd
     */
    public String getTemporaryPwd() {
        return this.temporaryPwd;
    }

    /**
     *
     * @param pTemporaryPwd temporaryPwd value
     */
    public void setTemporaryPwd(String pTemporaryPwd) {
        this.temporaryPwd = pTemporaryPwd;
    }

    /**
     *
     * @return temporaryPwdEndDate
     */
    public Date getTemporaryPwdEndDate() {
        return this.temporaryPwdEndDate;
    }

    /**
     *
     * @param pTemporaryPwdEndDate temporaryPwdEndDate value
     */
    public void setTemporaryPwdEndDate(Date pTemporaryPwdEndDate) {
        this.temporaryPwdEndDate = pTemporaryPwdEndDate;
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
        /*PROTECTED REGION ID(toString_couCUDLAEeCru6uc_4I2vA) ENABLED START*/
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
            .append("id", getId())
            .append("version", getVersion())
            .append("accountIdentifier", getAccountIdentifier())
            .append("sgin", getSgin())
            .append("fbIdentifier", getFbIdentifier())
            .append("emailIdentifier", getEmailIdentifier())
            .append("personnalizedIdentifier", getPersonnalizedIdentifier())
            .append("status", getStatus())
            .append("password", getPassword())
            .append("passwordToChange", getPasswordToChange())
            .append("temporaryPwd", getTemporaryPwd())
            .append("temporaryPwdEndDate", getTemporaryPwdEndDate())
            .append("secretQuestion", getSecretQuestion())
            .append("secretQuestionAnswer", getSecretQuestionAnswer())
            .append("nbFailureAuthentification", getNbFailureAuthentification())
            .append("nbFailureSecretQuestionAns", getNbFailureSecretQuestionAns())
            .append("enrolmentPointOfSell", getEnrolmentPointOfSell())
            .append("carrier", getCarrier())
            .append("lastConnexionDate", getLastConnexionDate())
            .append("lastPwdResetDate", getLastPwdResetDate())
            .append("accountDeletionDate", getAccountDeletionDate())
            .append("siteCreation", getSiteCreation())
            .append("signatureCreation", getSignatureCreation())
            .append("dateCreation", getDateCreation())
            .append("siteModification", getSiteModification())
            .append("signatureModification", getSignatureModification())
            .append("dateModification", getDateModification())
            .append("ipAddress", getIpAddress())
            .append("accountUpgradeDate", getAccountUpgradeDate())
            .append("accountLockedDate", getAccountLockedDate())
            .append("secretQuestionAnswerUpper", getSecretQuestionAnswerUpper())
            .append("lastSecretAnswModification", getLastSecretAnswModification())
            .append("socialNetworkId", getSocialNetworkId())
            .append("lastSocialNetworkLogonDate", getLastSocialNetworkLogonDate())
            .append("lastSocialNetworkUsed", getLastSocialNetworkUsed())
            .append("lastSocialNetworkId", getLastSocialNetworkId())
            .toString();
    }
}
