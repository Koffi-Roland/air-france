package com.afklm.cati.common.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;


@Embeddable
public class RefComPrefGroupId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * refComPrefDgt
     */
    @OneToOne()
    @JoinColumn(name="REF_COMPREF_DGT_ID", nullable=false)
    @ForeignKey(name = "REF_REF_COMPREF_ID_FK")
    private RefComPrefDgt refComPrefDgt;


    /**
     * refComPrefGroupInfo
     */
    @OneToOne()
    @JoinColumn(name = "REF_COMPREF_GROUP_INFO_ID", nullable=false)
    @ForeignKey(name = "REF_COMPREF_GROUP_INFO_ID_FK")
    private RefComPrefGroupInfo refComPrefGroupInfo;


    public RefComPrefGroupId() {
        super();
    }


    public RefComPrefGroupId(RefComPrefDgt refComPrefDgt, RefComPrefGroupInfo refComPrefGroupInfo) {
        super();
        this.refComPrefDgt = refComPrefDgt;
        this.refComPrefGroupInfo = refComPrefGroupInfo;
    }


    public RefComPrefDgt getRefComPrefDgt() {
        return refComPrefDgt;
    }


    public void setRefComPrefDgt(RefComPrefDgt refComPrefDgt) {
        this.refComPrefDgt = refComPrefDgt;
    }


    public RefComPrefGroupInfo getRefComPrefGroupInfo() {
        return refComPrefGroupInfo;
    }


    public void setRefComPrefGroupInfo(RefComPrefGroupInfo refComPrefGroupInfo) {
        this.refComPrefGroupInfo = refComPrefGroupInfo;
    }


    @Override
    public String toString() {
        return "RefComPrefGroupId [refComPrefDgt=" + refComPrefDgt + ", refComPrefGroupInfo=" + refComPrefGroupInfo
                + "]";
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((refComPrefDgt == null) ? 0 : refComPrefDgt.hashCode());
        result = prime * result + ((refComPrefGroupInfo == null) ? 0 : refComPrefGroupInfo.hashCode());
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
        RefComPrefGroupId other = (RefComPrefGroupId) obj;
        if (refComPrefDgt == null) {
            if (other.refComPrefDgt != null)
                return false;
        } else if (!refComPrefDgt.equals(other.refComPrefDgt))
            return false;
        if (refComPrefGroupInfo == null) {
            if (other.refComPrefGroupInfo != null)
                return false;
        } else if (!refComPrefGroupInfo.equals(other.refComPrefGroupInfo))
            return false;
        return true;
    }
}
