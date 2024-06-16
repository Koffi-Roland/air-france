package com.afklm.cati.common.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefPermissionsQuestionDTO {


    private Integer id;

    private String name;

    private String question;

    private String questionEN;

    private Date dateCreation;

    private String siteCreation;

    private String signatureCreation;

    private Date dateModification;

    private String siteModification;

    private String signatureModification;
}
