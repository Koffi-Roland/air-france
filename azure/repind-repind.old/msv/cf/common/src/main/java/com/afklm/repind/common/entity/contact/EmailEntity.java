package com.afklm.repind.common.entity.contact;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import com.afklm.repind.common.entity.individual.Individu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "EMAILS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * An entity designed to store data about email and mailing
 */
public class EmailEntity implements Serializable {

    /*PROTECTED REGION ID(serialUID _2rProMWJEd-Z0rWwhyE6jg) ENABLED START*/
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
    /*PROTECTED REGION END*/


    /**
     * sain
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_EMAILS")
    @GenericGenerator(name="ISEQ_EMAILS", strategy = "com.afklm.repind.common.sequence.StringSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "ISEQ_EMAILS")
            })
    @Column(name = "SAIN", length = 16, nullable = false, unique = true, updatable = false)
    private String ain;

    /**
     * version
     */
    @Version
    @Column(name = "IVERSION", length = 12, nullable = false)
    private Integer version;

    @Column(name = "SCODE_MEDIUM", length = 1, nullable = false)
    private String codeMedium;

    /**
     * statutMedium
     */
    @Column(name = "SSTATUT_MEDIUM", length = 1, nullable = false)
    private String statutMedium;

    /**
     * email
     */
    @Column(name = "SEMAIL", length = 60, nullable = false)
    private String email;

    /**
     * descriptifComplementaire
     */
    @Column(name = "SDESCRIPTIF_COMPLEMENTAIRE", length = 30)
    private String descriptifComplementaire;

    /**
     * autorisationMailing
     */
    @Column(name = "SAUTORISATION_MAILING", length = 1, nullable = false)
    private String autorisationMailing;

    /**
     * cleRole
     */
    @Column(name = "ICLE_ROLE")
    private Integer cleRole;

    /**
     * cleTemp
     */
    @Column(name = "IKEY_TEMP")
    private Integer cleTemp;

    /**
     * signatureModification
     */
    @Column(name = "SSIGNATURE_MODIFICATION", length = 16)
    private String signatureModification;

    /**
     * siteModification
     */
    @Column(name = "SSITE_MODIFICATION", length = 10)
    private String siteModification;

    /**
     * dateModification
     */
    @Column(name = "DDATE_MODIFICATION")
    private Date dateModification;

    /**
     * signatureCreation
     */
    @Column(name = "SSIGNATURE_CREATION", length = 16, nullable = false)
    private String signatureCreation;

    /**
     * siteCreation
     */
    @Column(name = "SSITE_CREATION", length = 10, nullable = false)
    private String siteCreation;

    /**
     * dateCreation
     */
    @Column(name = "DDATE_CREATION")
    private Date dateCreation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SGIN")
    private Individu individu;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailEntity emailTarget = (EmailEntity) o;
        return Objects.equals(ain, emailTarget.ain);
    }

}
