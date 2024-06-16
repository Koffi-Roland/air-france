package com.afklm.repind.msv.inferred.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity


@Table(name="REF_INFERRED_KEY_TYPE")
public class RefInfrdKeyType implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private RefInfrdKeyTypeId refInfrdKeyTypeId;

	public RefInfrdKeyTypeId getRefInfrdKeyTypeId() {
		return refInfrdKeyTypeId;
	}

	public void setRefInfrdKeyTypeId(RefInfrdKeyTypeId refInfrdKeyTypeId) {
		this.refInfrdKeyTypeId = refInfrdKeyTypeId;
	}
    
}
