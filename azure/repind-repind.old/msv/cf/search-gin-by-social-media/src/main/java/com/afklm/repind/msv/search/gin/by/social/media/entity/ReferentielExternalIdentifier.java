package com.afklm.repind.msv.search.gin.by.social.media.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="REF_TYP_EXT_ID")
@Getter
@Setter
public class ReferentielExternalIdentifier implements Serializable {

    /*PROTECTED REGION ID(serialUID _2rProMWJEd-Z0rWwhyE6jg) ENABLED START*/
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
    /*PROTECTED REGION END*/


    /**
     * External Id
     */
    @Id
    @GeneratedValue()
    @Column(name="EXT_ID", length=15, nullable=false, unique=true)
    private String id;


    /**
     * Option
     */
    @Column(name="SOPTION", length=3)
    private String option;


    /**
     * Libellé
     */
    @Column(name="SLIBELLE", length=25)
    private String libelle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferentielExternalIdentifier referentielExternalIdentifier = (ReferentielExternalIdentifier) o;
        return Objects.equals(id, referentielExternalIdentifier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
