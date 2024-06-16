package com.afklm.repind.msv.automatic.merge.model.individual;

import java.util.Date;

public class ModelRoleUCCR {

    private Integer cleRole;
    private String uccrID;
    private String gin;
    private String ceID;
    private String type;
    private String etat;
    private Date debutValidite;
    private Date finValidite;
    private ModelSignature signature;

    public ModelSignature getSignature() {
        return signature;
    }

    public void setSignature(ModelSignature signature) {
        this.signature = signature;
    }

    public Integer getCleRole() {
        return cleRole;
    }

    public void setCleRole(Integer cleRole) {
        this.cleRole = cleRole;
    }

    public String getUccrID() {
        return uccrID;
    }

    public void setUccrID(String uccrID) {
        this.uccrID = uccrID;
    }

    public String getGin() {
        return gin;
    }

    public void setGin(String gin) {
        this.gin = gin;
    }

    public String getCeID() {
        return ceID;
    }

    public void setCeID(String ceID) {
        this.ceID = ceID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDebutValidite() {
        return debutValidite;
    }

    public void setDebutValidite(Date debutValidite) {
        this.debutValidite = debutValidite;
    }

    public Date getFinValidite() {
        return finValidite;
    }

    public void setFinValidite(Date finValidite) {
        this.finValidite = finValidite;
    }

}
