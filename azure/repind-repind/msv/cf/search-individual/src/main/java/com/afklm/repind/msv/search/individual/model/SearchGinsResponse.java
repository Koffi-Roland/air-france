package com.afklm.repind.msv.search.individual.model;

import java.util.ArrayList;
import java.util.List;

import com.afklm.repind.msv.search.individual.client.core.HttpResponseModel;
import lombok.Data;

@Data
public class SearchGinsResponse extends HttpResponseModel {
    
    private List<String> gins = new ArrayList<>();

}
