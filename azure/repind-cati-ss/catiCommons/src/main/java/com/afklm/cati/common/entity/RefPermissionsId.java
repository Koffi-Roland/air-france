package com.afklm.cati.common.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;


@Embeddable
public class RefPermissionsId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * questionId
     */
    @OneToOne()
    @JoinColumn(name="REF_PERMISSIONS_QUESTION_ID", nullable=false)
    @ForeignKey(name = "FK_REF_PERMISSIONS_QUESTION")
    private RefPermissionsQuestion questionId;


    /**
     * refComPrefDgt
     */
    @OneToOne()
    @JoinColumn(name = "REF_COMPREF_DGT_ID", nullable=false)
    @ForeignKey(name = "FK_REF_COMPREF_DGT_PERM")
    private RefComPrefDgt refComPrefDgt;

    public RefPermissionsId() {
        super();
    }


    public RefPermissionsId(RefPermissionsQuestion questionId, RefComPrefDgt refComPrefDgt) {
        super();
        this.questionId = questionId;
        this.refComPrefDgt = refComPrefDgt;
    }


    public RefPermissionsQuestion getQuestionId() {
        return questionId;
    }




    public void setQuestionId(RefPermissionsQuestion questionId) {
        this.questionId = questionId;
    }




    public RefComPrefDgt getRefComPrefDgt() {
        return refComPrefDgt;
    }




    public void setRefComPrefDgt(RefComPrefDgt refComPrefDgt) {
        this.refComPrefDgt = refComPrefDgt;
    }


    @Override
    public String toString() {
        return "RefPermissionsId [questionId=" + questionId + ", refComPrefDgt=" + refComPrefDgt + "]";
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
        result = prime * result + ((refComPrefDgt == null) ? 0 : refComPrefDgt.hashCode());
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
        RefPermissionsId other = (RefPermissionsId) obj;
        if (questionId == null) {
            if (other.questionId != null)
                return false;
        } else if (!questionId.equals(other.questionId))
            return false;
        if (refComPrefDgt == null) {
            if (other.refComPrefDgt != null)
                return false;
        } else if (!refComPrefDgt.equals(other.refComPrefDgt))
            return false;
        return true;
    }
}
