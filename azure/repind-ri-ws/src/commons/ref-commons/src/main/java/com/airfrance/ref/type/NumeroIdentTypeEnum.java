package com.airfrance.ref.type;

public enum NumeroIdentTypeEnum {

    ARC("AR", "ARC AGENCY"),
    ATAF("AT", "ATAF AGENCY"),
    IATA("IA", "IATA AGENCY"),
    TID("TI", "TID AGENCY"),
    NON_AGREE("AG", "AGENCY WITHOUT AGREEMENT");

    private String code;

    private String libelle;

    NumeroIdentTypeEnum(String code, String libelle) {
        this.setCode(code);
        this.setLibelle(libelle);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
