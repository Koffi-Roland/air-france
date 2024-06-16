package com.airfrance.batch.purgemyaccount.model;

import lombok.*;

import java.util.Date;

/**
 * MyaLogicalToDelete
 * join entity for few fields between ACCOUNT_DATA and ROLE_CONTRATS
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MyaLogicalToDelete {
    /**
     * ad.ID
     */
    private Integer id;
    /**
     * ad.IVERSION
     */
    private Integer iversion;
    /**
     * ad.SGIN
     */
    private String sgin;
    /**
     * ad.STATUS
     */
    private String status;
    /**
     * ad.DDATE_MODIFICATION
     */
    private Date ddateModification;
    /**
     * rc.SRIN
     */
    private String srin;
    /**
     * rc.SETAT
     */
    private String setat;
    /**
     * rc.SNUMERO_CONTRAT
     */
    private String snumeroContrat;
    /**
     * rc.STYPE_CONTRAT
     */
    private String stypeContrat;
    /**
     * ad.DDATE_CREATION;
     */
    private Date ddateCreation;
}