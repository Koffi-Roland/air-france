package com.airfrance.repind.entity.reference;


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
@Table(name="REF_PCS_SCORE")
public class RefPcsScore implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Code PCS Factor
     */
    @Id
    @Column(name="SCODE", length=10, nullable=false)
    private String code;

    /**
     * Code PCS Score
     */
    @Column(name="SCODE_FACTOR", length=10, nullable=false)
    private String codeFactor;

    /**
     * Libelle PCS Factor
     */
    @Column(name="SLIBELLE", length=25)
    private String libelle;


    /**
     * FACTOR PCS Factor
     */
    @Column(name="SSCORE")
    private Integer score;

    public RefPcsScore() {
    }

    public RefPcsScore(String code, String codeFactor, String libelle, Integer score) {
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

    public String getCodeFactor() {
        return codeFactor;
    }

    public void setCodeFactor(String codeFactor) {
        this.codeFactor = codeFactor;
    }

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

    @Override
    public String toString() {
        return "RefPcsScore{" +
                "code='" + code + '\'' +
                ", codeFactor='" + codeFactor + '\'' +
                ", libelle='" + libelle + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefPcsScore that = (RefPcsScore) o;
        return Objects.equals(code, that.code) && Objects.equals(codeFactor, that.codeFactor) && Objects.equals(libelle, that.libelle) && Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, codeFactor, libelle, score);
    }
}
