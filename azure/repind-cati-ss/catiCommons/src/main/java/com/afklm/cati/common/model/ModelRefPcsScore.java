package com.afklm.cati.common.model;

public class ModelRefPcsScore {

    private String code;
    private String codeFactor;
    private String libelle;
    private Integer score;

    public ModelRefPcsScore() {}

    public ModelRefPcsScore(String code, String codeFactor, String libelle, Integer score) {
        this.code = code;
        this.codeFactor = codeFactor;
        this.libelle = libelle;
        this.score = score;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeFactor() { return codeFactor; }

    public void setCodeFactor(String codeFactor) { this.codeFactor = codeFactor; }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
