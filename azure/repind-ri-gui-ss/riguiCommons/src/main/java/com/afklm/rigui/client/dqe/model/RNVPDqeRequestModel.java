package com.afklm.rigui.client.dqe.model;

import com.afklm.rigui.client.core.HttpRequestModel;
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
