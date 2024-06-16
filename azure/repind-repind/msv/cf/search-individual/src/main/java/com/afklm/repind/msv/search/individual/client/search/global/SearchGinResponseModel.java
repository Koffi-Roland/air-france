package com.afklm.repind.msv.search.individual.client.search.global;

import com.afklm.repind.msv.search.individual.client.core.HttpResponseModel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class SearchGinResponseModel extends HttpResponseModel {

    private String gin;
    private String individualTypes;
    private String civility;
    private String lastName;
    private String firstName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private Integer matchScoreLastName;
    private Integer matchScoreFirstName;
}
