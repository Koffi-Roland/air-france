package com.afklm.cati.common.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefComPrefTypeDTO {


    private String codeType;

    private String libelleType;

    private String libelleTypeEN;

    private String signatureModification;

    private String siteModification;

    private Date dateModification;

    private String signatureCreation;

    private String siteCreation;

    private Date dateCreation;
}
