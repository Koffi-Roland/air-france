package com.afklm.repind.msv.automatic.merge.model.individual;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * @author t528182
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelAccountData {

    private String identifiant;
    private String accountIdentifier;
    private String fbIdentifier;
    private String emailIdentifier;
    private Date lastConnexionDate;
    private ModelSignature signature;


    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getFbIdentifier() {
        return fbIdentifier;
    }

    public void setFbIdentifier(String fbIdentifier) {
        this.fbIdentifier = fbIdentifier;
    }

    public String getEmailIdentifier() {
        return emailIdentifier;
    }

    public void setEmailIdentifier(String emailIdentifier) {
        this.emailIdentifier = emailIdentifier;
    }

    public Date getLastConnexionDate() {
        return lastConnexionDate;
    }

    public void setLastConnexionDate(Date lastConnexionDate) {
        this.lastConnexionDate = lastConnexionDate;
    }

    public ModelSignature getSignature() {
        return signature;
    }

    public void setSignature(ModelSignature signature) {
        this.signature = signature;
    }

}
