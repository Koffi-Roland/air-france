package com.afklm.cati.common.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefPcsFactorDTO {

    private String code;

    private String libelle;

    private Integer factor;

    private Integer maxPoints;
}
