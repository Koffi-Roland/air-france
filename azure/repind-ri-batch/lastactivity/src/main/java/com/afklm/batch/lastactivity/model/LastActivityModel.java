package com.afklm.batch.lastactivity.model;


import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Last activity model
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LastActivityModel {

    /**
     * Description: Individual GIN number, example: '110000038701'
     */
    @NotBlank
    private String gin;
    /**
     * Description: last modification date, example: "04/10/22"
     */
    @NotBlank
    private Date dateModification;

    /**
     * Description: Site where modification originated, example: "Individuals_all"
     */
    private String sourceModification;
    /**
     * Description: Signature for individual modification, example: "REPIND/IHM"
     */
    private String signatureModification;

    /**
     * Description: Site where modification originated, example: "QUALIF2009"
     */
    private String siteModification;

}
