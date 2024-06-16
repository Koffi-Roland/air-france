package com.afklm.repind.msv.customer.adaptor.wrapper.individus;

import com.afklm.repind.msv.customer.adaptor.model.criteria.UpsertIndividusRequestCriteria;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.http.HttpStatusCode;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@With
public class WrapperUpsertIndividusResponse {
    private UpsertIndividusRequestCriteria upsertIndividusRequestCriteria;
    private HttpStatusCode httpStatusCode;
}
