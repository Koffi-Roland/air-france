package com.afklm.cati.common.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "REF_PERMISSIONS")
public class RefPermissions implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    /**
     * refPermissionsId
     */
    @EmbeddedId
    private RefPermissionsId refPermissionsId;


    /**
     * dateCreation
     */
    @Column(name = "DDATE_CREATION", nullable = false)
    private Date dateCreation;


    /**
     * siteCreation
     */
    @Column(name = "SSITE_CREATION", nullable = false)
    private String siteCreation;


    /**
     * signatureCreation
     */
    @Column(name = "SSIGNATURE_CREATION", nullable = false)
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
     * signatureModification
     */
    @Column(name = "SSIGNATURE_MODIFICATION")
    private String signatureModification;


    public RefPermissions() {
        super();
    }


    public RefPermissionsId getRefPermissionsId() {
        return refPermissionsId;
    }


    public void setRefPermissionsId(RefPermissionsId refPermissionsId) {
        this.refPermissionsId = refPermissionsId;
    }


    public Date getDateCreation() {
        return dateCreation;
    }


    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }


    public String getSiteCreation() {
        return siteCreation;
    }


    public void setSiteCreation(String siteCreation) {
        this.siteCreation = siteCreation;
    }


    public String getSignatureCreation() {
        return signatureCreation;
    }


    public void setSignatureCreation(String signatureCreation) {
        this.signatureCreation = signatureCreation;
    }


    public Date getDateModification() {
        return dateModification;
    }


    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }


    public String getSiteModification() {
        return siteModification;
    }


    public void setSiteModification(String siteModification) {
        this.siteModification = siteModification;
    }


    public String getSignatureModification() {
        return signatureModification;
    }


    public void setSignatureModification(String signatureModification) {
        this.signatureModification = signatureModification;
    }
}
