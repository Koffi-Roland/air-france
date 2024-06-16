package com.afklm.repind.common.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
/*
  An abstract class used to prevent code duplication among entity who share the same data about signature
 */
public abstract class AbstractGenericField implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * signatureModification
     */
    @Column(name = "MODIFICATION_SIGNATURE", length = 16)
    private String signatureModification;

    /**
     * siteModification
     */
    @Column(name = "MODIFICATION_SITE", length = 10)
    private String siteModification;

    /**
     * dateModification
     */
    @Column(name = "MODIFICATION_DATE")
    private Date dateModification;

    /**
     * signatureCreation
     */
    @Column(name = "CREATION_SIGNATURE", length = 16)
    private String signatureCreation;

    /**
     * siteCreation
     */
    @Column(name = "CREATION_SITE", length = 10)
    private String siteCreation;

    /**
     * dateCreation
     */
    @Column(name = "CREATION_DATE")
    private Date dateCreation;
}
