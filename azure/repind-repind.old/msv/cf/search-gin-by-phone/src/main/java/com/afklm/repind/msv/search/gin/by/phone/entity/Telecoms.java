package com.afklm.repind.msv.search.gin.by.phone.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name="TELECOMS")
@Getter
@Setter
public class Telecoms {

    /**
     * sain
     */
    @Id
    @GeneratedValue
    @Column(name="SAIN", length=16, nullable=false, unique=true, updatable=false)
    private String sain;

    /**
     * sgin
     */
    @Column(name="SGIN", length=12, updatable=false, insertable=false)
    private String sgin;

    /**
     * scode_medium
     */
    @Column(name="SCODE_MEDIUM", length=1)
    private String scodeMedium;

    /**
     * sstatut_medium
     */
    @Column(name="SSTATUT_MEDIUM", length=1)
    private String sstatutMedium;

    /**
     * snorm_inter_phone_number
     */
    @Column(name="SNORM_INTER_PHONE_NUMBER")
    private String snormInterPhoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telecoms telecoms = (Telecoms) o;
        return Objects.equals(sgin, telecoms.sgin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sgin);
    }
}
