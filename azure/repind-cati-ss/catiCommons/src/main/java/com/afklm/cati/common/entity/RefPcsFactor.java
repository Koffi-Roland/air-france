package com.afklm.cati.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>Title : RefPcsFactor.java</p>
 * BO Pays
 * <p>Copyright : Copyright (c) 2022</p>
 * <p>Company : AIRFRANCE-KLM</p>
 */
@Entity
@Table(name = "REF_PCS_FACTOR")
public class RefPcsFactor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Code PCS Factor
     */
    @Id
    @Column(name = "SCODE", length = 10, nullable = false)
    private String code;


    /**
     * Libelle PCS Factor
     */
    @Column(name = "SLIBELLE", length = 25)
    private String libelle;


    /**
     * FACTOR PCS Factor
     */
    @Column(name = "SFACTOR")
    private Integer factor;

    /**
     * MAX POINTS PCS Factor
     */
    @Column(name = "SMAX_POINTS")
    private Integer maxPoints;


    public RefPcsFactor() {
    }

    public RefPcsFactor(String code, String libelle, Integer factor) {
        this.code = code;
        this.libelle = libelle;
        this.factor = factor;
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

    public Integer getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

    @Override
    public String toString() {
        return "RefPcsFactor{" +
                "code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                ", factor=" + factor + '\'' +
                ", maxPoints=" + maxPoints +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefPcsFactor that = (RefPcsFactor) o;
        return Objects.equals(code, that.code) && Objects.equals(libelle, that.libelle) && Objects.equals(factor, that.factor) && Objects.equals(maxPoints, that.maxPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, libelle, factor, maxPoints);
    }
}
