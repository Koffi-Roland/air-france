package com.afklm.repind.common.entity.contact;

import jakarta.persistence.*;
import com.afklm.repind.common.entity.individual.Individu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(
        name = "ADR_POST"
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostalAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(PostalAddress.class);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_ADR_POSTS")
    @GenericGenerator(name="ISEQ_ADR_POSTS", strategy = "com.afklm.repind.common.sequence.StringSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "ISEQ_ADR_POSTS")
            })
    @Column(name = "SAIN",length = 16, nullable = false, unique = true, updatable = false)
    private String ain;

    @Version
    @Column(name = "IVERSION", length = 12, nullable = false)
    private Integer version;

    @Column(
            name = "SRAISON_SOCIALE",
            length = 42
    )
    private String raisonSociale;
    @Column(
            name = "SCOMPLEMENT_ADRESSE",
            length = 42
    )
    private String complementAdresse;
    @Column(
            name = "SNO_ET_RUE",
            length = 42
    )
    private String noEtRue;
    @Column(
            name = "SLOCALITE",
            length = 42
    )
    private String localite;
    @Column(
            name = "SCODE_POSTAL",
            length = 10
    )
    private String codePostal;
    @Column(
            name = "SVILLE",
            length = 32
    )
    private String ville;
    @Column(
            name = "SCODE_PAYS",
            length = 2
    )
    private String codePays;
    @Column(
            name = "SCODE_PROVINCE",
            length = 2
    )
    private String codeProvince;
    @Column(
            name = "SCODE_MEDIUM",
            length = 1,
            nullable = false
    )
    private String codeMedium;
    @Column(
            name = "SSTATUT_MEDIUM",
            length = 1,
            nullable = false
    )
    private String statutMedium;
    @Column(
            name = "SSITE_MODIFICATION",
            length = 10
    )
    private String siteModification;
    @Column(
            name = "SSIGNATURE_MODIFICATION",
            length = 16
    )
    private String signatureModification;
    @Column(
            name = "DDATE_MODIFICATION"
    )
    private Date dateModification;
    @Column(
            name = "SSITE_CREATION",
            length = 10,
            nullable = false
    )
    private String siteCreation;
    @Column(
            name = "SSIGNATURE_CREATION",
            length = 16,
            nullable = false
    )
    private String signatureCreation;
    @Column(
            name = "DDATE_CREATION",
            nullable = false
    )
    private Date dateCreation;
    @Column(
            name = "SFORCAGE",
            length = 1
    )
    private String forcage;
    @Column(
            name = "SDESCRIPTIF_COMPLEMENTAIRE",
            length = 60
    )
    private String descriptifComplementaire;
    @Column(
            name = "SINDADR",
            length = 30
    )
    private String indAdr;
    @Column(
            name = "ICOD_ERR",
            nullable = false
    )
    private Integer codErr;
    @Column(
            name = "ICOD_WARNING"
    )
    private Integer codWarning;
    @Column(
            name = "ICLE_ROLE",
            length = 10
    )
    private Integer cleRole;
    @Column(
            name = "IKEY_TEMP",
            length = 10
    )
    private Integer keyTemp;
    @Column(
            name = "DDATE_FONCTIONNEL"
    )
    private Date dateFonctionnel;
    @Column(
            name = "SSITE_FONCTIONNEL",
            length = 10
    )
    private String siteFonctionnel;
    @Column(
            name = "SSIGNATURE_FONCTIONNEL",
            length = 16
    )
    private String signatureFonctionnel;
    @Column(
            name = "SCOD_ERR_SIMPLE",
            length = 2
    )
    private String codErrSimple;
    @Column(
            name = "SCOD_ERR_DETAILLE",
            length = 16
    )
    private String codErrDetaille;
    @Column(
            name = "STYPE_INVALIDITE",
            length = 1
    )
    private String typeInvalidite;
    @Column(
            name = "SENVOI_POSTAL",
            length = 1
    )
    private String senvoiPostal;
    @Column(
            name = "DENVOI_POSTAL"
    )
    private Date denvoiPostal;
    @Column(
            name = "SCODE_APP_SEND",
            length = 4
    )
    private String scodeAppSend;
    @Transient
    private String reprocessError;
    @Transient
    private Date reprocessTime;
    @Transient
    private String reprocessAppliId;
    @Transient
    private boolean toBeReprocessed;
    @Transient
    private String codeAppliSending;
    @Transient
    private Set<String> formalizedAdrList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SGIN")
    private Individu individu;

    @OneToMany
    @JoinColumn(
            name = "SAIN_ADR",
            nullable = true
    )
    private Set<UsageMedium> usageMedium;
}
