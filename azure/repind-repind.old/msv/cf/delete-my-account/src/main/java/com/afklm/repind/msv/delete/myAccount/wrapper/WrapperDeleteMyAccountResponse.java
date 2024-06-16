package com.afklm.repind.msv.delete.myAccount.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class WrapperDeleteMyAccountResponse {

    private String gin;
}
