package com.afklm.repind.common.entity.role;

import com.afklm.repind.common.entity.individual.Individu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BUSINESS_ROLE")
@Getter
@Setter
@ToString
public class BusinessRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * cleRole
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_BUSINESS_ROLE")
    @SequenceGenerator(name = "ISEQ_BUSINESS_ROLE", sequenceName = "ISEQ_BUSINESS_ROLE", allocationSize = 1)
    @Column(name = "ICLE_ROLE", length = 10, nullable = false)
    private Integer cleRole;

    /**
     * ginInd
     */
    @Column(name = "SGIN_IND", length = 12, insertable=false, updatable=false)
    private String ginInd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SGIN_IND", referencedColumnName = "SGIN")
    private Individu individu;

    /**
     * numeroContrat
     */
    @Column(name = "SNUMERO_CONTRAT", length = 20)
    private String numeroContrat;

    /**
     * type
     */
    @Column(name = "STYPE", length = 1)
    private String type;

    /**
     * dateCreation
     */
    @Column(name = "DDATE_CREATION")
    private Date dateCreation;

    /**
     * siteCreation
     */
    @Column(name = "SSITE_CREATION", length = 12)
    private String siteCreation;

    /**
     * signatureCreation
     */
    @Column(name = "SSIGNATURE_CREATION", length = 16)
    private String signatureCreation;

    /**
     * dateModification
     */
    @Column(name = "DDATE_MODIFICATION")
    private Date dateModification;

    /**
     * siteModification
     */
    @Column(name = "SSITE_MODIFICATION", length = 10)
    private String siteModification;

    /**
     * signatureModification
     */
    @Column(name = "SSIGNATURE_MODIFICATION", length = 16)
    private String signatureModification;

    /**
     * roleTravelers
     */
    // 1 <-> 1
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(referencedColumnName = "ICLE_ROLE")
    private RoleTraveler roleTraveler;

    /**
     * roleGP
     */
    // 1 <-> 1
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(referencedColumnName = "ICLE_ROLE")
    private RoleGP roleGP;

    // 1 <-> 1
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(referencedColumnName = "ICLE_ROLE")
    private RoleContract roleContract;
}
