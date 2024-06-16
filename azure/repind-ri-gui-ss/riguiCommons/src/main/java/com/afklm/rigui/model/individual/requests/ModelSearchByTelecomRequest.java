package com.afklm.rigui.model.individual.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelSearchByTelecomRequest {
    private String countryCode;
    private String phoneNumber;
}
