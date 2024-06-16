package com.airfrance.batch.purgemyaccount.helper;

public class Constant {
    public static final String SIGNATURE_MODIFICATION_PURGE_MYA = "PURGE_MYA";
    public static final String SITE_MODIFICATION_QVI = "QVI";
    public static final String MYA_BACKUP_HEADER = "ID;IVERSION;ACCOUNT_IDENTIFIER;SGIN;FB_IDENTIFIER;EMAIL_IDENTIFIER;PERSONALIZED_IDENTIFIER;STATUS;PASSWORD;PASSWORD_TO_CHANGE;TEMPORARY_PWD;TEMPORARY_PWD_END_DATE;SECRET_QUESTION_OLD;SECRET_QUESTION_ANSWER;NB_FAILURE_AUTHENTIFICATION;NB_FAILURE_SECRET_QUESTION_ANS;ENROLEMENT_POINT_OF_SELL;CARRIER;LAST_CONNECTION_DATE;LAST_PWD_RESET_DATE;ACCOUNT_DELETION_DATE;SSITE_CREATION;SSIGNATURE_CREATION;DDATE_CREATION;SSITE_MODIFICATION;SSIGNATURE_MODIFICATION;DDATE_MODIFICATION;IP_ADDRESS;ACCOUNT_UPGRADE_DATE;SECRET_QUESTION;ACCOUNT_LOCKED_DATE;SECRET_QUESTION_ANSWER_UPPER;LAST_SECRET_ANSW_MODIFICATION;SOCIAL_NETWORK_ID;LAST_SOCIAL_NETWORK_LOGON_DATE;LAST_SOCIAL_NETWORK_USED;LAST_SOCIAL_NETWORK_ID;SRIN;IVERSION1;SNUMERO_CONTRAT;SGIN1;SETAT;STYPE_CONTRAT;SSOUS_TYPE;STIER;SCODE_COMPAGNIE;IVERSION_PRODUIT;DFIN_VALIDITE;DDEBUT_VALIDITE;SFAMILLE_TRAITEMENT;SFAMILLE_PRODUIT;ICLE_ROLE;DDATE_CREATION1;SSIGNATURE_CREATION1;DDATE_MODIFICATION1;SSIGNATURE_MODIFICATION1;SSITE_CREATION1;SSITE_MODIFICATION1;SAGENCE_IATA;IATA;SSOURCE_ADHESION;SPERMISSION_PRIME;ISOLDE_MILES;IMILES_QUALIF;IMILES_QUALIF_PREC;ISEGMENTS_QUALIF;ISEGMENTS_QUALIF_PREC;CUSCO_CREATED;SMEMBER_TYPE";

    public static final String MYA_SUCCESS_LOGICAL_DELETE_HEADER = "ID;IVERSION;SGIN;STATUS;DDATE_MODIFICATION;SRIN;SETAT;SNUMERO_CONTRAT;STYPE_CONTRAT;DDATE_CREATION";




    public static final String[] MYA_SUCCESS_LOGICAL_DELETE_WRAPPER_FIELDS = new String[] {
            "id", "iversion", "sgin", "status", "ddateModification", "srin", "setat", "snumeroContrat", "stypeContrat", "ddateCreation"
    };

    public static final String[] MYA_BACKUP_WRAPPER_FIELDS = new String[] {
            "acId", "acIversion", "acAccountIdentifier", "acSgin", "acFbIdentifier", "acEmailIdentifier",
            "acPersonalizedIdentifier", "acStatus", "acPassword", "acPasswordToChange", "acTmpPwd",
            "acTmpPwdEndDate", "acSecretQuestionOld", "acSecretQuestionAnswer", "acNbFailureAuthentification",
            "acNbFailureSecretQuestionAns", "acEnrolementPointOfSell", "acCarrier", "acLastConnectionDate",
            "acLastPwdResetDate", "acCountDeletionDate", "acSiteCreation", "acSignatureCreation", "acDateCreation",
            "acSiteModification", "acSignatureModification", "acDateModification", "acIpAddress",
            "acAccountUpgradeDate", "acSecretQuestion", "acAccountLockedDDate", "acSecretQuestionAnswerUpper",
            "acLastSecretAnswModification", "acSocialNetworkId", "acLastSocialNetworkLogonDate",
            "acLastSocialNetworkUsed", "acLastSocialNetworkId",
            "rcRin", "rcIversion", "rcNumeroContrat", "rcSgin", "rcSetat", "rcStypeContrat", "rcSsoustype",
            "rcStier", "rcScodeCompagnie", "rcIversionProduit", "rcDfinValidite", "rcDdebutValidite",
            "rcSfamilleTraitement", "rcSfamilleProduit", "rcIcleRole", "rcDdateCreation", "rcSsignatureCreation",
            "rcDdateModification", "rcSsignatureModification", "rcSsiteCreation", "rcSsiteModification",
            "rcSagenceIata", "rcIata", "rcSsourceAdhesion", "rcSpermissionPrime", "rcIsoldeMiles",
            "rcImilesQualif", "rcImilesQualifPrec", "rcIsegmentsQualif", "rcIsegmentsQualifPrec",
            "rcCuscoCreated", "rcSmemberType"};




    private Constant() {
        throw new IllegalStateException("Utility class");
    }
}
