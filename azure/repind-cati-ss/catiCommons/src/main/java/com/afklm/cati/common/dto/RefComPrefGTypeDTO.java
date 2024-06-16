package com.afklm.cati.common.dto;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefComPrefGTypeDTO {


    private String codeGType;


    private String libelleGType;


    private String libelleGTypeEN;


    private String signatureModification;


    private String siteModification;


    private Date dateModification;


    private String signatureCreation;

    private String siteCreation;


    private Date dateCreation;

}
