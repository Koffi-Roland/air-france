package com.afklm.repind.msv.search.individual.client.search.email.model;

import com.afklm.repind.msv.search.individual.client.core.HttpRequestModel;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class SearchGinByEmailRequest extends HttpRequestModel {
    private String email;
}
