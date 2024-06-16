package com.airfrance.repind.client.core;

import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public abstract class HttpRequestBodyModel extends HttpRequestModel {
    private Object body;
}
