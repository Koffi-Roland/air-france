package com.afklm.repind.msv.search.individual.client.search.socialMedia.model;

import com.afklm.repind.msv.search.individual.client.core.HttpRequestModel;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class SearchGinBySocialMediaRequest extends HttpRequestModel {
    private String externalIdentifierId;
    private String externalIdentifierType;
    private boolean merge;
}
