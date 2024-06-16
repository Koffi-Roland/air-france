package com.afklm.repind.msv.customer.adaptor.client.sfmc.token.model;

import com.afklm.repind.msv.customer.adaptor.client.core.HttpRequestBodyModel;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveTokenRequest extends HttpRequestBodyModel {
    private String grant_type;
    private String client_id;
    private String client_secret;
}
