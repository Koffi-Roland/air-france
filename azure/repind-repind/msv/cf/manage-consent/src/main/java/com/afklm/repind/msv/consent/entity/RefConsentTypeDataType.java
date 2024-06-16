package com.afklm.repind.msv.consent.entity;

import jakarta.persistence.*;

import java.io.Serializable;



@Entity


@Table(name="REF_CONSENT_TYPE_DATA_TYPE")
public class RefConsentTypeDataType implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private RefConsentTypeDataTypeId refConsentTypeDataTypeId;
	
}
