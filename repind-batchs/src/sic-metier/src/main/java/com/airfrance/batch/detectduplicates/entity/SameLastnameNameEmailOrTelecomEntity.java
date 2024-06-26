package com.airfrance.batch.detectduplicates.entity;

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
@Table(name = "DQ_NOMPRENOM_EMAIL_OR_TEL")
public class SameLastnameNameEmailOrTelecomEntity implements Serializable {

    @Id
    @Column(name="NOMPRENOM", nullable = false)
    private String lastnameName;

    @Column(name="ELEMENT_DUPLICATE", nullable = false)
    private String elementDuplicate;

    @Column(name="SGINS", nullable = false)
    private String gins;

    @Column(name="NB_CONTRACT", nullable = false)
    private int nbContract;

    @Column(name="NB_GINS", nullable = false)
    private int nbGINs;

    @Column(name="IS_DUPLICATE", nullable = false)
    private boolean duplicate;
}
