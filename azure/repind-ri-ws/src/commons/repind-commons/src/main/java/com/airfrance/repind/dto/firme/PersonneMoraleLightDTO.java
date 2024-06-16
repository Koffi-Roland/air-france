package com.airfrance.repind.dto.firme;


import com.airfrance.repind.entity.firme.enums.PersonneMoraleTypeEnum;

import java.io.Serializable;

/**
 * The type Personne morale light dto.
 */
public class PersonneMoraleLightDTO implements Serializable {

    /**
     * The S gin.
     */
    public String sGin;
    /**
     * The Personne morale type.
     */
    public PersonneMoraleTypeEnum personneMoraleType;

    /**
     * Instantiates a new Personne morale light dto.
     *
     * @param sGin               the s gin
     * @param personneMoraleType the personne morale type
     */
    public PersonneMoraleLightDTO(String sGin, PersonneMoraleTypeEnum personneMoraleType) {
        this.sGin = sGin;
        this.personneMoraleType = personneMoraleType;
    }

    /**
     * Gets gin.
     *
     * @return the gin
     */
    public String getsGin() {
        return sGin;
    }

    /**
     * Sets gin.
     *
     * @param sGin the s gin
     */
    public void setsGin(String sGin) {
        this.sGin = sGin;
    }

    /**
     * Gets personne morale type.
     *
     * @return the personne morale type
     */
    public PersonneMoraleTypeEnum getPersonneMoraleType() {
        return personneMoraleType;
    }

    /**
     * Sets personne morale type.
     *
     * @param personneMoraleType the personne morale type
     */
    public void setPersonneMoraleType(PersonneMoraleTypeEnum personneMoraleType) {
        this.personneMoraleType = personneMoraleType;
    }
}
