package com.afklm.cati.common.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "TRACKING_REF_PERMISSIONS")
public class TrackingRefPermissions implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRACKING_REF_PERMISSIONS")
    @SequenceGenerator(name = "SEQ_TRACKING_REF_PERMISSIONS", sequenceName = "SEQ_TRACKING_REF_PERMISSIONS",
            allocationSize = 1)
    @Column(name = "TRACKING_ID", length = 12, nullable = false)
    private Integer id;


    /**
     * tableName
     */
    @Column(name = "TABLE_NAME", nullable = false)
    private String tableName;


    /**
     * permissionId
     */
    @OneToOne()
    @JoinColumn(name = "PERMISSION_ID", nullable = false)
    @ForeignKey(name = "FK_REF_PERMISSIONS_TRACK")
    private RefPermissionsQuestion permissionId;


    /**
     * beforeValue
     */
    @Column(name = "BEFORE_VALUE")
    private String beforeValue;


    /**
     * afterValue
     */
    @Column(name = "AFTER_VALUE")
    private String afterValue;


    /**
     * actionType
     */
    @Column(name = "ACTION_TYPE", nullable = false)
    private String actionType;


    /**
     * dateModification
     */
    @Column(name = "DDATE_MODIFICATION", nullable = false)
    private Date dateModification;


    /**
     * siteModification
     */
    @Column(name = "SSITE_MODIFICATION", nullable = false)
    private String siteModification;


    /**
     * signatureModification
     */
    @Column(name = "SSIGNATURE_MODIFICATION", nullable = false)
    private String signatureModification;


    public TrackingRefPermissions() {
        super();
    }


    public TrackingRefPermissions(Integer id, String tableName, RefPermissionsQuestion permissionId, String beforeValue,
                                  String afterValue, String actionType, Date dateModification, String siteModification,
                                  String signatureModification) {
        super();
        this.id = id;
        this.tableName = tableName;
        this.permissionId = permissionId;
        this.beforeValue = beforeValue;
        this.afterValue = afterValue;
        this.actionType = actionType;
        this.dateModification = dateModification;
        this.siteModification = siteModification;
        this.signatureModification = signatureModification;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getTableName() {
        return tableName;
    }


    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public RefPermissionsQuestion getPermissionId() {
        return permissionId;
    }


    public void setPermissionId(RefPermissionsQuestion permissionId) {
        this.permissionId = permissionId;
    }


    public String getBeforeValue() {
        return beforeValue;
    }


    public void setBeforeValue(String beforeValue) {
        this.beforeValue = beforeValue;
    }


    public String getAfterValue() {
        return afterValue;
    }


    public void setAfterValue(String afterValue) {
        this.afterValue = afterValue;
    }


    public String getActionType() {
        return actionType;
    }


    public void setActionType(String actionType) {
        this.actionType = actionType;
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


    @Override
    public String toString() {
        return "TrackingRefPermissions [id=" + id + ", tableName=" + tableName + ", permissionId=" + permissionId
                + ", beforeValue=" + beforeValue + ", afterValue=" + afterValue + ", actionType=" + actionType
                + ", dateModification=" + dateModification + ", siteModification=" + siteModification
                + ", signatureModification=" + signatureModification + "]";
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((actionType == null) ? 0 : actionType.hashCode());
        result = prime * result + ((afterValue == null) ? 0 : afterValue.hashCode());
        result = prime * result + ((beforeValue == null) ? 0 : beforeValue.hashCode());
        result = prime * result + ((dateModification == null) ? 0 : dateModification.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((permissionId == null) ? 0 : permissionId.hashCode());
        result = prime * result + ((signatureModification == null) ? 0 : signatureModification.hashCode());
        result = prime * result + ((siteModification == null) ? 0 : siteModification.hashCode());
        result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TrackingRefPermissions other = (TrackingRefPermissions) obj;
        if (actionType == null) {
            if (other.actionType != null)
                return false;
        } else if (!actionType.equals(other.actionType))
            return false;
        if (afterValue == null) {
            if (other.afterValue != null)
                return false;
        } else if (!afterValue.equals(other.afterValue))
            return false;
        if (beforeValue == null) {
            if (other.beforeValue != null)
                return false;
        } else if (!beforeValue.equals(other.beforeValue))
            return false;
        if (dateModification == null) {
            if (other.dateModification != null)
                return false;
        } else if (!dateModification.equals(other.dateModification))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (permissionId == null) {
            if (other.permissionId != null)
                return false;
        } else if (!permissionId.equals(other.permissionId))
            return false;
        if (signatureModification == null) {
            if (other.signatureModification != null)
                return false;
        } else if (!signatureModification.equals(other.signatureModification))
            return false;
        if (siteModification == null) {
            if (other.siteModification != null)
                return false;
        } else if (!siteModification.equals(other.siteModification))
            return false;
        if (tableName == null) {
            if (other.tableName != null)
                return false;
        } else if (!tableName.equals(other.tableName))
            return false;
        return true;
    }
}
