package com.afklm.rigui.model.individual.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelSearchByAccountRequest {
    private String cin;
    private String email;
}
