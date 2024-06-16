package com.afklm.rigui.model.individual.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelSearchBySocialIDRequest {

    /**
     * Social ID : Social identifier
     */
    @NotBlank
    private String socialID;


    /**
     * Social Type | example: Facebook, Twitter, PNM, LinkedIn ...
     */
    private String socialType;
}
