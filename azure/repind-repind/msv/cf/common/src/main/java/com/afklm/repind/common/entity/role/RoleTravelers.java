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
@Table(name = "ROLE_TRAVELERS")
@Getter
@Setter
@ToString
public class RoleTravelers implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * cleRole
     */
    @Id
    @Column(name = "ICLE_ROLE")
    private Integer cleRole;

    /**
     * gin
     */
    @Column(name = "SGIN")
    private String gin;

    /**
     * lastRecognitionDate
     */
    @Column(name = "DLAST_RECOGNITION_DATE")
    private Date lastRecognitionDate;

    /**
     * matchingRecognitionCode
     */
    @Column(name = "SMATCHING_RECOGNITION_CODE")
    private String matchingRecognitionCode;

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
