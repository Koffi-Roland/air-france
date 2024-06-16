package com.afklm.cati.common.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefComPrefDomainDTO {


    private String codeDomain;


    private String libelleDomain;

    private String libelleDomainEN;


    private String signatureModification;


    private String siteModification;


    private Date dateModification;


    private String signatureCreation;


    private String siteCreation;


    private Date dateCreation;

}
