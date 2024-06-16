package com.afklm.batch.detectduplicates.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DQ_NOMPRENOM_TELECOM_EMAIL")
public class SameLastnameNameEmailAndTelecomEntity implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name="NOMPRENOM", nullable = false)
    private String lastnameName;

    @Column(name="SNORM_INTER_PHONE_NUMBER", nullable = false)
    private String telecom;

    @Column(name="TEL_SGINS", nullable = false)
    private String telecomGINs;

    @Column(name="TEL_NB_CONTRACT", nullable = false)
    private int telecomNbContract;

    @Column(name="TEL_NB_GINS", nullable = false)
    private int telecomNbGINs;

    @Column(name="EMAIL", nullable = false)
    private String email;

    @Column(name="EMAIL_SGINS", nullable = false)
    private String emailGINs;

    @Column(name="EMAIL_NB_CONTRACT", nullable = false)
    private int emailNbContract;

    @Column(name="EMAIL_NB_GINS", nullable = false)
    private int emailNbGINs;

    @Column(name="IS_DUPLICATE", nullable = false)
    private boolean duplicate;
}
