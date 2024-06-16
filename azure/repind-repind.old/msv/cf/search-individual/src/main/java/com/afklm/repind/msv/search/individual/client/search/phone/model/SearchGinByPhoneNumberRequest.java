package com.afklm.repind.msv.search.individual.client.search.phone.model;

import com.afklm.repind.msv.search.individual.client.core.HttpRequestModel;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class SearchGinByPhoneNumberRequest extends HttpRequestModel {
    private String phone;
}
