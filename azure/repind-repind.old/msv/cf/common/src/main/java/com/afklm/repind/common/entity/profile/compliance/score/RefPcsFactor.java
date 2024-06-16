package com.afklm.repind.common.entity.profile.compliance.score;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Title : RefPcsFactor.java
 * It's an entity designed to store Profile Compliance Score Factors
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="REF_PCS_FACTOR")
public class RefPcsFactor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Code PCS Factor
     */
    @Id
    @Column(name="SCODE", length=10, nullable=false)
    private String code;


    /**
     * Libelle PCS Factor
     */
    @Column(name="SLIBELLE", length=25)
    private String libelle;


    /**
     * FACTOR PCS Factor
     */
    @Column(name="SFACTOR")
    private Integer factor;

    /**
     * MAX POINTS PCS Factor
     */
    @Column(name="SMAX_POINTS")
    private Integer maxPoints;

}

