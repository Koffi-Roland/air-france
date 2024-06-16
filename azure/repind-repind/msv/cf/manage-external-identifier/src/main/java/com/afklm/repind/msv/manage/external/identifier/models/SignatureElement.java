package com.afklm.repind.msv.manage.external.identifier.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class SignatureElement {
    private String signatureCreation;
    private String siteCreation;
    private Date dateCreation;
    private String signatureModification;
    private String siteModification;
    private Date dateModification;
}
