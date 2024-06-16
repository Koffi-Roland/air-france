package com.afklm.cati.common.model;

public class ModelRefPcsFactor {

    private String code;
    private String libelle;
    private Integer factor;
    private Integer maxPoints;

    public ModelRefPcsFactor() {}

    public ModelRefPcsFactor(String code, String libelle, Integer factor, Integer maxPoints) {
        this.code = code;
        this.libelle = libelle;
        this.factor = factor;
        this.maxPoints = maxPoints;
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

    public Integer getFactor() {
        return factor;
    }

    public void setFactor(Integer factor) {
        this.factor = factor;
    }

    public Integer getMaxPoints() { return maxPoints; }
    public void setMaxPoints(Integer maxPoints) { this.maxPoints = maxPoints; }
}
