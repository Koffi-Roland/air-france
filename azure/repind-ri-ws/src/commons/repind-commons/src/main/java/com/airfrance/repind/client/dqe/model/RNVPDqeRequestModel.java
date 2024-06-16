package com.airfrance.repind.client.dqe.model;

import com.airfrance.repind.client.core.HttpRequestModel;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class RNVPDqeRequestModel extends HttpRequestModel {
    private String adresse;
    private String pays;
    private String licence;
    private String modification;
}
