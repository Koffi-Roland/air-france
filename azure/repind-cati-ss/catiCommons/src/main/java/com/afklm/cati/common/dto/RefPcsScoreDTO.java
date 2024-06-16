package com.afklm.cati.common.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefPcsScoreDTO {

    private String code;

    private String codeFactor;

    private String libelle;

    private Integer score;
}
