package com.afklm.repind.common.entity.role;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.Date;

@Entity
@Table(name = "ROLE_GP")
@Getter
@Setter
@ToString
public class RoleGP {

    private static final long serialVersionUID = 1L;


    /**
     * cleRole
     */
    @Id
    @Column(name="ICLE_ROLE", length=10, nullable=false, unique=true)
    private Integer cleRole;


    /**
     * matricule
     */
    @Column(name="SMATRICULE", length=12)
    private String matricule;


    /**
     * version
     */
    @Column(name="SVERSION", length=2)
    private String version;


    /**
     * etat
     */
    @Column(name="SETAT", length=1)
    private String etat;


    /**
     * dateDebValidite
     */
    @Column(name="DDEBUT_VALIDITE")
    private Date dateDebValidite;

    /**
     * dateFinValidite
     */
    @Column(name="DFIN_VALIDITE")
    private Date dateFinValidite;

    /**
     * type
     */
    @Column(name="STYPE", length=2)
    private String type;


    /**
     * codeOrigin
     */
    @Column(name="SCODE_ORIGINE", length=6)
    private String codeOrigin;


    /**
     * codeCie
     */
    @Column(name="SCODE_CIE", length=6)
    private String codeCie;


    /**
     * codePays
     */
    @Column(name="SCODE_PAYS", length=2)
    private String codePays;


    /**
     * typology
     */
    @Column(name="STRUNC_TYPO", length=4)
    private String typology;


    /**
     * ordreIdentifiant
     */
    @Column(name="SORDRE_IDENTIFIANT", length=3)
    private String ordreIdentifiant;

}
