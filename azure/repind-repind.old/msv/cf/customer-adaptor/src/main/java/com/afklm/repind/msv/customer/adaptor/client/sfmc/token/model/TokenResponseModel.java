package com.afklm.repind.msv.customer.adaptor.client.sfmc.token.model;

import com.afklm.repind.msv.customer.adaptor.client.core.HttpResponseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseModel extends HttpResponseModel {

    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String rest_instance_url;

}
