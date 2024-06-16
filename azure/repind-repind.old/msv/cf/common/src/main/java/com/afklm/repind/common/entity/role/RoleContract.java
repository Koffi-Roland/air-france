package com.afklm.repind.common.entity.role;

// add not generated imports here

import jakarta.persistence.*;
import com.afklm.repind.common.entity.individual.Individu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>Title : RoleContrats.java</p>
 * BO RoleContrats
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity
@Table(name = "ROLE_CONTRATS")
@Getter
@Setter
@ToString
public class RoleContract implements Serializable {
    /**
     * Determines if a de-serialized file is compatible with this class.
     * <p>
     * Maintainers must change this value if and only if the new version
     * of this class is not compatible with old versions. See Sun docs
     * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
     * /serialization/spec/class.html#4100> details. </a>
     * <p>
     * Not necessary to include in first version of the class, but
     * included here as a reminder of its importance.
     */
    private static final long serialVersionUID = 1L;

    /**
     * srin
     */
    @Id
    @GenericGenerator(name = "ISEQ_ROLE_CONTRATS", strategy = "com.afklm.repind.common.sequence.StringSequenceGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "ISEQ_ROLE_CONTRATS")
            })
    @GeneratedValue(generator = "ISEQ_ROLE_CONTRATS")
    @Column(name = "SRIN", nullable = false, updatable = false)
    private String srin;


    /**
     * version
     */
    @Version
    @Column(name = "IVERSION", nullable = false)
    private Integer version;


    /**
     * numeroContrat
     */
    @Column(name = "SNUMERO_CONTRAT", length = 20, nullable = false)
    private String numeroContrat;



    /**
     * etat
     */
    @Column(name = "SETAT", length = 1, nullable = false)
    private String etat;


    /**
     * typeContrat
     */
    @Column(name = "STYPE_CONTRAT", length = 10, nullable = false)
    private String typeContrat;


    /**
     * sousType
     */
    @Column(name = "SSOUS_TYPE", length = 10)
    private String sousType;


    /**
     * tier
     */
    @Column(name = "STIER", length = 1)
    private String tier;


    /**
     * codeCompagnie
     */
    @Column(name = "SCODE_COMPAGNIE", length = 3)
    private String codeCompagnie;


    /**
     * versionProduit
     */
    @Column(name = "IVERSION_PRODUIT")
    private Integer versionProduit;


    /**
     * dateFinValidite
     */
    @Column(name = "DFIN_VALIDITE")
    private Date dateFinValidite;


    /**
     * dateDebutValidite
     */
    @Column(name = "DDEBUT_VALIDITE")
    private Date dateDebutValidite;


    /**
     * familleTraitement
     */
    @Column(name = "SFAMILLE_TRAITEMENT", length = 1)
    private String familleTraitement;


    /**
     * familleProduit
     */
    @Column(name = "SFAMILLE_PRODUIT", length = 3)
    private String familleProduit;


    /**
     * cleRole
     */
    @Column(name = "ICLE_ROLE", updatable = false, insertable = false)
    private Integer cleRole;


    /**
     * dateCreation
     */
    @Column(name = "DDATE_CREATION")
    private Date dateCreation;


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
     * signatureModification
     */
    @Column(name = "SSIGNATURE_MODIFICATION", length = 16)
    private String signatureModification;


    /**
     * siteCreation
     */
    @Column(name = "SSITE_CREATION", length = 10)
    private String siteCreation;


    /**
     * siteModification
     */
    @Column(name = "SSITE_MODIFICATION", length = 10)
    private String siteModification;


    /**
     * agenceIATA
     */
    @Column(name = "SAGENCE_IATA", length = 8)
    private String agenceIATA;


    /**
     * iata
     */
    @Column(name = "IATA", length = 8)
    private String iata;


    /**
     * sourceAdhesion
     */
    @Column(name = "SSOURCE_ADHESION", length = 4)
    private String sourceAdhesion;


    /**
     * permissionPrime
     */
    @Column(name = "SPERMISSION_PRIME", length = 1)
    private String permissionPrime;


    /**
     * soldeMiles
     */
    @Column(name = "ISOLDE_MILES")
    private Integer soldeMiles;


    /**
     * milesQualif
     */
    @Column(name = "IMILES_QUALIF")
    private Integer milesQualif;


    /**
     * milesQualifPrec
     */
    @Column(name = "IMILES_QUALIF_PREC")
    private Integer milesQualifPrec;


    /**
     * segmentsQualif
     */
    @Column(name = "ISEGMENTS_QUALIF")
    private Integer segmentsQualif;


    /**
     * segmentsQualifPrec
     */
    @Column(name = "ISEGMENTS_QUALIF_PREC")
    private Integer segmentsQualifPrec;


    /**
     * cuscoCreated
     */
    @Column(name = "CUSCO_CREATED")
    private String cuscoCreated;

    /**
     * memberType
     */
    @Column(name = "SMEMBER_TYPE")
    private String memberType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SGIN")
    private Individu individu;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ICLE_ROLE")
    private BusinessRole businessRole;

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final RoleContract other = (RoleContract) obj;
        return Objects.equals(srin, other.srin);
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
