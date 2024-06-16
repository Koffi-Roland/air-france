package com.afklm.repind.common.entity.contact;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "USAGE_MEDIUMS"
)
public class UsageMedium implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISEQ_USAGE_MEDIUMS")
    @GenericGenerator(name="ISEQ_USAGE_MEDIUMS", strategy = "com.afklm.repind.common.sequence.StringSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "ISEQ_USAGE_MEDIUMS")
            })
    @Column(name = "SRIN", length = 16)
    private String rin;

    @Column(
            name = "SAIN_TEL",
            length = 16
    )
    private String ainTel;

    @Column(
            name = "SAIN_EMAIL",
            length = 16
    )
    private String ainEmail;

    @Column(
            name = "SCODE_APPLICATION",
            length = 4
    )
    private String codeApplication;

    @Column(
            name = "SAIN_ADR",
            length = 16
    )
    private String ainAdr;

    @Column(
            name = "INUM",
            nullable = false,
            updatable = false
    )
    private Integer num;

    @Column(
            name = "SCON",
            length = 1
    )
    private String con;

    @Column(
            name = "SROLE1",
            length = 1
    )
    private String role1;

    @Column(
            name = "SROLE2",
            length = 1
    )
    private String role2;

    @Column(
            name = "SROLE3",
            length = 1
    )
    private String role3;

    @Column(
            name = "SROLE4",
            length = 1
    )
    private String role4;

    @Column(
            name = "SROLE5",
            length = 1
    )
    private String role5;
}
