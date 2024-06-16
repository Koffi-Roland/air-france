package com.afklm.repind.msv.automatic.merge.model.individual;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelEmail {

    private String identifiant;
    private String type;
    private String status;
    private String email;
    private String description;
    private String authorizationMailing;
    private ModelSignature signature;
    private Integer version;

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModelSignature getSignature() {
        return signature;
    }

    public void setSignature(ModelSignature signature) {
        this.signature = signature;
    }

    public String getAuthorizationMailing() {
        return authorizationMailing;
    }

    public void setAuthorizationMailing(String authorizationMailing) {
        this.authorizationMailing = authorizationMailing;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
