package com.airfrance.batch.purgemyaccount.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MyaPursToPurge
 * join entity between ACCOUNT_DATA and ROLE_CONTRATS
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyaPursBackup {
    //ACCOUNT_DATA fields
    private String acId;
    private String acIversion;
    private String acAccountIdentifier;
    private String acSgin;
    private String acFbIdentifier;
    private String acEmailIdentifier;
    private String acPersonalizedIdentifier;
    private String acStatus;
    private String acPassword;
    private String acPasswordToChange;
    private String acTmpPwd;
    private String acTmpPwdEndDate;
    private String acSecretQuestionOld;
    private String acSecretQuestionAnswer;
    private String acNbFailureAuthentification;
    private String acNbFailureSecretQuestionAns;
    private String acEnrolementPointOfSell;
    private String acCarrier;
    private String acLastConnectionDate;
    private String acLastPwdResetDate;
    private String acCountDeletionDate;
    private String acSiteCreation;
    private String acSignatureCreation;
    private String acDateCreation;
    private String acSiteModification;
    private String acSignatureModification;
    private String acDateModification;
    private String acIpAddress;
    private String acAccountUpgradeDate;
    private String acSecretQuestion;
    private String acAccountLockedDDate;
    private String acSecretQuestionAnswerUpper;
    private String acLastSecretAnswModification;
    private String acSocialNetworkId;
    private String acLastSocialNetworkLogonDate;
    private String acLastSocialNetworkUsed;
    private String acLastSocialNetworkId;

    //ROLE_CONTRATS fields
    private String rcRin;
    private String rcIversion;
    private String rcNumeroContrat;
    private String rcSgin;
    private String rcSetat;
    private String rcStypeContrat;
    private String rcSsoustype;
    private String rcStier;
    private String rcScodeCompagnie;
    private String rcIversionProduit;
    private String rcDfinValidite;
    private String rcDdebutValidite;
    private String rcSfamilleTraitement;
    private String rcSfamilleProduit;
    private String rcIcleRole;
    private String rcDdateCreation;
    private String rcSsignatureCreation;
    private String rcDdateModification;
    private String rcSsignatureModification;
    private String rcSsiteCreation;
    private String rcSsiteModification;
    private String rcSagenceIata;
    private String rcIata;
    private String rcSsourceAdhesion;
    private String rcSpermissionPrime;
    private String rcIsoldeMiles;
    private String rcImilesQualif;
    private String rcImilesQualifPrec;
    private String rcIsegmentsQualif;
    private String rcIsegmentsQualifPrec;
    private String rcCuscoCreated;
    private String rcSmemberType;
}
