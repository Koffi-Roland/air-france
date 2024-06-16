package com.afklm.repind.common.entity.profile.compliance.score;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


/**
 * Title : RefPcsScore.java
 * It's an entity designed to store Profile Compliance Scores
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
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

}
