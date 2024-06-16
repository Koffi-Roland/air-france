package com.afklm.cati.common.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefComPrefMediaDTO {

    private String codeMedia;

    private String libelleMedia;

    private String libelleMediaEN;

    private String signatureModification;

    private String siteModification;

    private Date dateModification;

    private String signatureCreation;

    private String siteCreation;

    private Date dateCreation;
}
