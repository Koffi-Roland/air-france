package com.afklm.repind.common.entity.individual;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ACCOUNT_DATA")
@Getter
@Setter
@ToString
public class AccountData implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String gin;


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

}
