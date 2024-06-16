package com.afklm.repind.common.entity.role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ROLE_UCCR")
@Getter
@Setter
@ToString
public class RoleUCCR implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * uccrId
     */
    @Id
    @Column(name = "UCCR_ID")
    private String uccrId;

    /**
     * corporateEnvironmentID
     */
    @Column(name = "CE_ID")
    private String corporateEnvironmentID;

    /**
     * cleRole
     */
    @Column(name = "ICLE_ROLE")
    private Integer cleRole;

    /**
     * gin
     */
    @Column(name = "SGIN")
    private String gin;

    /**
     * type
     */
    @Column(name = "STYPE")
    private String type;

    /**
     * etat
     */
    @Column(name = "SETAT")
    private String etat;

    /**
     * debutValidite
     */
    @Column(name = "DDEBUT_VALIDITE")
    private Date debutValidite;

    /**
     * finValidite
     */
    @Column(name = "DFIN_VALIDITE")
    private Date finValidite;

    /**
     * dateCreation
     */
    @Column(name = "DDATE_CREATION")
    private Date dateCreation;

    /**
     * siteCreation
     */
    @Column(name = "SSITE_CREATION")
    private String siteCreation;

    /**
     * signatureCreation
     */
    @Column(name = "SSIGNATURE_CREATION")
    private String signatureCreation;

    /**
     * dateModification
     */
    @Column(name = "DDATE_MODIFICATION")
    private Date dateModification;

    /**
     * siteModification
     */
    @Column(name = "SSITE_MODIFICATION")
    private String siteModification;

    /**
     * signatureCreation
     */
    @Column(name = "SSIGNATURE_MODIFICATION")
    private String signatureModification;
}
