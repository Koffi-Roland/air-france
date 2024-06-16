package com.afklm.rigui.entity.individu;

/*PROTECTED REGION ID(_erg6IDBHEeCUXsOnQ__xYw i) ENABLED START*/

// add not generated imports here

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/*PROTECTED REGION END*/


/**
 * <p>Title : AccountData.java</p>
 * BO AccountData
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
/*PROTECTED REGION ID(@Entity _erg6IDBHEeCUXsOnQ__xYw) ENABLED START*/
@Entity


@Table(name="ACCOUNT_DATA")
public class AccountData implements Serializable {
    /*PROTECTED REGION END*/

    /*PROTECTED REGION ID(serialUID _erg6IDBHEeCUXsOnQ__xYw) ENABLED START*/
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
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_ID_ACCOUNTDATA")
    @SequenceGenerator(name="ISEQ_ID_ACCOUNTDATA", sequenceName = "ISEQ_ID_ACCOUNTDATA",
            allocationSize = 1)
    @Column(name="ID", length=12, nullable=false, unique=true, updatable=false)
    private Integer id;


    /**
     * version
     */
    @Version
    @Column(name="IVERSION")
    private Integer version;


    /**
     * accountIdentifier
     */
    @Column(name="ACCOUNT_IDENTIFIER", length=8)
    private String accountIdentifier;


    /**
     * sgin
     */
    @Column(name="SGIN", length=12, updatable=false, insertable=false)
    private String sgin;


    /*PROTECTED REGION ID(_8F9JcDBIEeCUXsOnQ__xYw p) ENABLED START*/
    /**
     * fbIdentifier
     */
    @Column(name="FB_IDENTIFIER", length=16, columnDefinition="nvarchar2")
    private String fbIdentifier;
    /*PROTECTED REGION END*/


    /*PROTECTED REGION ID(_IJMuUDBIEeCUXsOnQ__xYw p) ENABLED START*/
    /**
     * emailIdentifier
     */
    @Column(name="EMAIL_IDENTIFIER", length=60, columnDefinition="nvarchar2")
    private String emailIdentifier;
    /*PROTECTED REGION END*/


    /*PROTECTED REGION ID(_AB7PwDBJEeCUXsOnQ__xYw p) ENABLED START*/
    /**
     * personnalizedIdentifier
     */
    @Column(name="PERSONALIZED_IDENTIFIER", length=16, columnDefinition="nvarchar2")
    private String personnalizedIdentifier;
    /*PROTECTED REGION END*/


    /**
     * status
     */
    @Column(name="STATUS", length=1)
    private String status;


    /**
     * password
     */
    @Column(name="PASSWORD", length=255)
    private String password;


    /**
     * passwordToChange
     */
    @Column(name="PASSWORD_TO_CHANGE", length=1)
    private Integer passwordToChange;


    /**
     * temporaryPwd
     */
    @Column(name="TEMPORARY_PWD", length=255)
    private String temporaryPwd;


    /**
     * temporaryPwdEndDate
     */
    @Column(name="TEMPORARY_PWD_END_DATE")
    private Date temporaryPwdEndDate;


    /**
     * secretQuestion
     */
    @Column(name="SECRET_QUESTION", length=1020)
    private byte[] secretQuestion;


    /*PROTECTED REGION ID(_jcdOsDBJEeCUXsOnQ__xYw p) ENABLED START*/
    /**
     * secretQuestionAnswer
     */
    @Column(name="SECRET_QUESTION_ANSWER", length=260, columnDefinition="nvarchar2")
    private String secretQuestionAnswer;
    /*PROTECTED REGION END*/


    /**
     * nbFailureAuthentification
     */
    @Column(name="NB_FAILURE_AUTHENTIFICATION", length=1)
    private Integer nbFailureAuthentification;


    /**
     * nbFailureSecretQuestionAns
     */
    @Column(name="NB_FAILURE_SECRET_QUESTION_ANS", length=1)
    private Integer nbFailureSecretQuestionAns;


    /**
     * enrolmentPointOfSell
     */
    @Column(name="ENROLEMENT_POINT_OF_SELL", length=3)
    private String enrolmentPointOfSell;


    /**
     * carrier
     */
    @Column(name="CARRIER", length=2)
    private String carrier;


    /**
     * lastConnexionDate
     */
    @Column(name="LAST_CONNECTION_DATE")
    private Date lastConnexionDate;


    /**
     * lastPwdResetDate
     */
    @Column(name="LAST_PWD_RESET_DATE")
    private Date lastPwdResetDate;


    /**
     * accountDeletionDate
     */
    @Column(name="ACCOUNT_DELETION_DATE")
    private Date accountDeletionDate;


    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10)
    private String siteCreation;


    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16)
    private String signatureCreation;


    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;


    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    private String siteModification;


    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String signatureModification;


    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;


    /**
     * ipAddress
     */
    @Column(name="IP_ADDRESS")
    private String ipAddress;


    /**
     * accountUpgradeDate
     */
    @Column(name="ACCOUNT_UPGRADE_DATE")
    private Date accountUpgradeDate;


    /**
     * accountLockedDate
     */
    @Column(name="ACCOUNT_LOCKED_DATE")
    private Date accountLockedDate;


    /**
     * secretQuestionAnswerUpper
     */
    @Column(name="SECRET_QUESTION_ANSWER_UPPER")
    private String secretQuestionAnswerUpper;


    /**
     * lastSecretAnswModification
     */
    @Column(name="LAST_SECRET_ANSW_MODIFICATION")
    private Date lastSecretAnswModification;


    /**
     * socialNetworkId
     */
    @Column(name="SOCIAL_NETWORK_ID")
    private String socialNetworkId;


    /**
     * lastSocialNetworkLogonDate
     */
    @Column(name="LAST_SOCIAL_NETWORK_LOGON_DATE")
    private Date lastSocialNetworkLogonDate;


    /**
     * lastSocialNetworkUsed
     */
    @Column(name="LAST_SOCIAL_NETWORK_USED")
    private String lastSocialNetworkUsed;


    /**
     * lastSocialNetworkId
     */
    @Column(name="LAST_SOCIAL_NETWORK_ID")
    private String lastSocialNetworkId;


    /**
     * individu
     */
    // 1 <-> 1
    @OneToOne()
    @JoinColumn(name="SGIN", nullable=false, foreignKey = @javax.persistence.ForeignKey(name = "FK_ACCOUNT_DATA2_INDIVIDUS"))
    private Individu individu;

    /*PROTECTED REGION ID(_erg6IDBHEeCUXsOnQ__xYw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    /**
     * default constructor 
     */
    public AccountData() {
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
    public AccountData(Integer pId, Integer pVersion, String pAccountIdentifier, String pSgin, String pFbIdentifier, String pEmailIdentifier, String pPersonnalizedIdentifier, String pStatus, String pPassword, Integer pPasswordToChange, String pTemporaryPwd, Date pTemporaryPwdEndDate, byte[] pSecretQuestion, String pSecretQuestionAnswer, Integer pNbFailureAuthentification, Integer pNbFailureSecretQuestionAns, String pEnrolmentPointOfSell, String pCarrier, Date pLastConnexionDate, Date pLastPwdResetDate, Date pAccountDeletionDate, String pSiteCreation, String pSignatureCreation, Date pDateCreation, String pSiteModification, String pSignatureModification, Date pDateModification, String pIpAddress, Date pAccountUpgradeDate, Date pAccountLockedDate, String pSecretQuestionAnswerUpper, Date pLastSecretAnswModification, String pSocialNetworkId, Date pLastSocialNetworkLogonDate, String pLastSocialNetworkUsed, String pLastSocialNetworkId) {
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
     * @return individu
     */
    public Individu getIndividu() {
        return this.individu;
    }

    /**
     *
     * @param pIndividu individu value
     */
    public void setIndividu(Individu pIndividu) {
        this.individu = pIndividu;
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
    public byte[] getSecretQuestion() {
        return this.secretQuestion;
    }

    /**
     *
     * @param pSecretQuestion secretQuestion value
     */
    public void setSecretQuestion(byte[] pSecretQuestion) {
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
        /*PROTECTED REGION ID(toString_erg6IDBHEeCUXsOnQ__xYw) ENABLED START*/
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
        buffer.append("id=").append(getId());
        buffer.append(",");
        buffer.append("version=").append(getVersion());
        buffer.append(",");
        buffer.append("accountIdentifier=").append(getAccountIdentifier());
        buffer.append(",");
        buffer.append("sgin=").append(getSgin());
        buffer.append(",");
        buffer.append("fbIdentifier=").append(getFbIdentifier());
        buffer.append(",");
        buffer.append("emailIdentifier=").append(getEmailIdentifier());
        buffer.append(",");
        buffer.append("personnalizedIdentifier=").append(getPersonnalizedIdentifier());
        buffer.append(",");
        buffer.append("status=").append(getStatus());
        buffer.append(",");
        buffer.append("password=").append(getPassword());
        buffer.append(",");
        buffer.append("passwordToChange=").append(getPasswordToChange());
        buffer.append(",");
        buffer.append("temporaryPwd=").append(getTemporaryPwd());
        buffer.append(",");
        buffer.append("temporaryPwdEndDate=").append(getTemporaryPwdEndDate());
        buffer.append(",");
        buffer.append("secretQuestion=").append(getSecretQuestion());
        buffer.append(",");
        buffer.append("secretQuestionAnswer=").append(getSecretQuestionAnswer());
        buffer.append(",");
        buffer.append("nbFailureAuthentification=").append(getNbFailureAuthentification());
        buffer.append(",");
        buffer.append("nbFailureSecretQuestionAns=").append(getNbFailureSecretQuestionAns());
        buffer.append(",");
        buffer.append("enrolmentPointOfSell=").append(getEnrolmentPointOfSell());
        buffer.append(",");
        buffer.append("carrier=").append(getCarrier());
        buffer.append(",");
        buffer.append("lastConnexionDate=").append(getLastConnexionDate());
        buffer.append(",");
        buffer.append("lastPwdResetDate=").append(getLastPwdResetDate());
        buffer.append(",");
        buffer.append("accountDeletionDate=").append(getAccountDeletionDate());
        buffer.append(",");
        buffer.append("siteCreation=").append(getSiteCreation());
        buffer.append(",");
        buffer.append("signatureCreation=").append(getSignatureCreation());
        buffer.append(",");
        buffer.append("dateCreation=").append(getDateCreation());
        buffer.append(",");
        buffer.append("siteModification=").append(getSiteModification());
        buffer.append(",");
        buffer.append("signatureModification=").append(getSignatureModification());
        buffer.append(",");
        buffer.append("dateModification=").append(getDateModification());
        buffer.append(",");
        buffer.append("ipAddress=").append(getIpAddress());
        buffer.append(",");
        buffer.append("accountUpgradeDate=").append(getAccountUpgradeDate());
        buffer.append(",");
        buffer.append("accountLockedDate=").append(getAccountLockedDate());
        buffer.append(",");
        buffer.append("secretQuestionAnswerUpper=").append(getSecretQuestionAnswerUpper());
        buffer.append(",");
        buffer.append("lastSecretAnswModification=").append(getLastSecretAnswModification());
        buffer.append(",");
        buffer.append("socialNetworkId=").append(getSocialNetworkId());
        buffer.append(",");
        buffer.append("lastSocialNetworkLogonDate=").append(getLastSocialNetworkLogonDate());
        buffer.append(",");
        buffer.append("lastSocialNetworkUsed=").append(getLastSocialNetworkUsed());
        buffer.append(",");
        buffer.append("lastSocialNetworkId=").append(getLastSocialNetworkId());
        buffer.append("]");
        return buffer.toString();
    }



    /*PROTECTED REGION ID(equals hash _erg6IDBHEeCUXsOnQ__xYw) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountData other = (AccountData) obj;

        // TODO: writes or generates equals method

        return super.equals(other);
    }

    /**
     * {@inheritDoc}
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        // TODO: writes or generates hashcode method

        return result;
    }

}
