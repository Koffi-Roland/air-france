package com.afklm.rigui.dto.lastactivity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
/**
 * Last activity data transfer object
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LastActivityDTO {
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
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String sourceModification;
    /**
     * Description: Signature for individual modification, example: "REPIND/IHM"
     */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String signatureModification;
    /**
     * Description: Site where modification originated: "QUALIF2009"
     */
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String siteModification;
}
