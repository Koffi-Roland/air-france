package com.afklm.cati.common.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefPreferenceDataKeyDTO {

    private String code;

    private String libelleFr;

    private String libelleEn;

    private String normalizedKey;
}
