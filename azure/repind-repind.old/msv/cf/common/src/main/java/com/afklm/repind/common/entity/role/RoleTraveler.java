package com.afklm.repind.common.entity.role;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "ROLE_TRAVELERS")
@Getter
@Setter
@ToString
public class RoleTraveler {

    private static final long serialVersionUID = -1685512493317850763L;


    /**
     * cleRole
     */
    @Id
    @Column(name="ICLE_ROLE", length=10, nullable=false, unique=true)
    private Integer cleRole;


    /**
     * gin
     */
    @Column(name="SGIN", length=12, updatable=false, insertable=true)
    private String gin;


    /**
     * lastRecognitionDate
     */
    @Column(name="DLAST_RECOGNITION_DATE", nullable=false)
    private Date lastRecognitionDate;


    /**
     * matchingRecognitionCode
     */
    @Column(name="SMATCHING_RECOGNITION_CODE", length=4)
    private String matchingRecognitionCode;


    /**
     * dateCreation
     */
    @Column(name="DDATE_CREATION")
    private Date dateCreation;


    /**
     * signatureCreation
     */
    @Column(name="SSIGNATURE_CREATION", length=16)
    private String signatureCreation;


    /**
     * siteCreation
     */
    @Column(name="SSITE_CREATION", length=10)
    private String siteCreation;


    /**
     * dateModification
     */
    @Column(name="DDATE_MODIFICATION")
    private Date dateModification;


    /**
     * signatureModification
     */
    @Column(name="SSIGNATURE_MODIFICATION", length=16)
    private String signatureModification;


    /**
     * siteModification
     */
    @Column(name="SSITE_MODIFICATION", length=10)
    private String siteModification;

}
