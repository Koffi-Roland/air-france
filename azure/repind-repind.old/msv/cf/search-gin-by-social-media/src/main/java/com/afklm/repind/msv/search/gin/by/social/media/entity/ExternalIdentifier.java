package com.afklm.repind.msv.search.gin.by.social.media.entity;
import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="EXTERNAL_IDENTIFIER")
@Getter
@Setter
public class ExternalIdentifier implements Serializable {

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
     * identifierID
     */
    @Id
    @GeneratedValue()
    @Column(name="IDENTIFIER_ID", length=16, nullable=false, unique=true, updatable=false)
    private String identifierID;


    /**
     * sgin
     */
    @Column(name="SGIN", length=12, nullable=false, updatable=false)
    private String sgin;


    /**
     * identifier
     */
    @Column(name="IDENTIFIER", length=500, nullable=false)
    private String identifier;


    /**
     * type
     */
    @Column(name="TYPE", length=100, updatable=false, nullable=false)
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExternalIdentifier externalIdentifier = (ExternalIdentifier) o;
        return Objects.equals(sgin, externalIdentifier.sgin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sgin);
    }
}
