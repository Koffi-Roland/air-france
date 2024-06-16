package com.afklm.repind.msv.search.individual.client.search.lastnameandfirstname.model;

import com.afklm.repind.msv.search.individual.client.core.HttpRequestModel;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class SearchGinByLastnameAndFirstnameRequest extends HttpRequestModel {
    private String lastname;
    private String firstname;
    private boolean merge;
}
