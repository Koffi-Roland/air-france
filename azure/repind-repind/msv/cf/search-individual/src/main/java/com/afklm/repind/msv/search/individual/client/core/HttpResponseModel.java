package com.afklm.repind.msv.search.individual.client.core;

import com.afklm.repind.common.model.error.RestError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponseModel {

    private RestError restError;
}
