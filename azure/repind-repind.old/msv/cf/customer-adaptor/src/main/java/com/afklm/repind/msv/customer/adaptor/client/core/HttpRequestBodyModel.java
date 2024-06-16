package com.afklm.repind.msv.customer.adaptor.client.core;

import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequestBodyModel extends HttpRequestModel {
    private Object body;
}