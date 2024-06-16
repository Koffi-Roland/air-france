package com.afklm.repind.msv.automatic.merge.model.individual;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author t528182
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ModelAddress {

    private String identifiant;
    private String version;
    private String type;
    private String status;
    private String corporateName;
    private String addressComplement;
    private String numberAndStreet;
    private String locality;
    private String zipCode;
    private String city;
    private String state;
    private String country;
    private String forced;
    private ModelSignature signature;
    private Set<ModelUsageMedium> usages;
}
