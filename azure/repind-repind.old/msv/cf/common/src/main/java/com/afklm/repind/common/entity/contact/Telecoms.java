package com.afklm.repind.common.entity.contact;

/*PROTECTED REGION ID(_o6dz0DOcEeCokvyNKVv2PQ i) ENABLED START*/

// add not generated imports here

import jakarta.persistence.*;
import com.afklm.repind.common.entity.individual.Individu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;

/*PROTECTED REGION END*/


/**
 * <p>Title : Telecoms.java</p>
 * BO Telecoms
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity
@Table(name="TELECOMS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Telecoms implements Serializable {

    /*PROTECTED REGION ID(serialUID _o6dz0DOcEeCokvyNKVv2PQ) ENABLED START*/
    /**
     * Determines if a de-serialized file is compatible with this class.
     *
     * Maintainers must change this value if and only if the new version
     * of this class is not compatible with old versions. See Sun docs
     * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
     * /serialization/spec/class.html#4100> details. </a>
     *
     * Not necessary to include in first version of the class, but
     * included here as a reminder of its importance.
     */
    private static final long serialVersionUID = 1L;


    /**
     * country code avant normalisation, non present en BD.
     */
    @Transient
    private String countryCode;

    /*PROTECTED REGION END*/


    /**
     * sain
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_TELECOMS")
    @GenericGenerator(name="ISEQ_TELECOMS", strategy = "com.afklm.repind.common.sequence.StringSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "ISEQ_TELECOMS")
            })
    @Column(name="SAIN", length=16, nullable=false, unique=true, updatable=false)
    private String ain;


    /**
     * version
     */
    @Version
    @Column(name="IVERSION", length=12, nullable=false)
    private Integer version;


    /**
     * scode_medium
     */
    @Column(name="SCODE_MEDIUM", length=1)
    private String codeMedium;


    /**
     * sstatut_medium
     */
    @Column(name="SSTATUT_MEDIUM", length=1)
    private String statutMedium;


    /**
     * snumero
     */
    @Column(name="SNUMERO", length=15)
    private String numero;


    /**
     * sdescriptif_complementaire
     */
    @Column(name="SDESCRIPTIF_COMPLEMENTAIRE", length=60)
    private String descriptifComplementaire;


    /**
     * sterminal
     */
    @Column(name="STERMINAL", length=1)
    private String terminal;


    /**
     * scode_region
     */
    @Column(name="SCODE_REGION", length=4)
    private String codeRegion;


    /**
     * sindicatif
     */
    @Column(name="SINDICATIF", length=4)
    private String indicatif;


    /**
     * ssignature_modification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String signatureModification;


    /**
     * ssite_modification
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    private String siteModification;


    /**
     * ddate_modification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;


    /**
     * ssignature_creation
     */
    @Column(name="SSIGNATURE_CREATION", length=16)
    private String signatureCreation;


    /**
     * ssite_creation
     */
    @Column(name="SSITE_CREATION", length=10)
    private String siteCreation;


    /**
     * ddate_creation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;


    /**
     * icle_role
     */
    @Column(name="ICLE_ROLE", length=10)
    private Integer cleRole;


    /**
     * ikey_temp
     */
    @Column(name="IKEY_TEMP", length=10)
    private Integer keyTemp;


    /**
     * snormalized_country
     */
    @Column(name="SNORMALIZED_COUNTRY", length=4)
    private String normalizedCountry;


    /**
     * snormalized_numero
     */
    @Column(name="SNORMALIZED_NUMERO", length=15)
    private String normalizedNumero;


    /**
     * sforcage
     */
    @Column(name="SFORCAGE", length=1)
    private String forcage;


    /**
     * svalidation
     */
    @Column(name="SVALIDATION", length=1)
    private String validation;


    /**
     * snorm_nat_phone_number
     */
    @Column(name="SNORM_NAT_PHONE_NUMBER")
    private String normNatPhoneNumber;


    /**
     * snorm_nat_phone_number_clean
     */
    @Column(name="SNORM_NAT_PHONE_NUMBER_CLEAN")
    private String normNatPhoneNumberClean;


    /**
     * snorm_inter_country_code
     */
    @Column(name="SNORM_INTER_COUNTRY_CODE")
    private String normInterCountryCode;


    /**
     * snorm_inter_phone_number
     */
    @Column(name="SNORM_INTER_PHONE_NUMBER")
    private String normInterPhoneNumber;


    /**
     * snorm_terminal_type_detail
     */
    @Column(name="SNORM_TERMINAL_TYPE_DETAIL")
    private String normTerminalTypeDetail;


    /**
     * isnormalized
     */
    @Column(name="ISNORMALIZED")
    private String isNormalized;


    /**
     * ddate_invalidation
     */
    @Column(name="DDATE_INVALIDATION")
    private Date dateInvalidation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SGIN")
    private Individu individu;
}
