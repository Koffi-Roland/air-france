package com.afklm.repind.msv.preferences.entity;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity


@Table(name="REF_PREFERENCE_KEY_TYPE")
public class RefPreferenceKeyType implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private RefPreferenceKeyTypeId refPreferenceKeyTypeId;

	public RefPreferenceKeyTypeId getRefInfrdKeyTypeId() {
		return refPreferenceKeyTypeId;
	}

	public void setRefInfrdKeyTypeId(RefPreferenceKeyTypeId refPreferenceKeyTypeId) {
		this.refPreferenceKeyTypeId = refPreferenceKeyTypeId;
	}
    
}
