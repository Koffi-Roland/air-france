package com.afklm.repind.msv.customer.adaptor.model.criteria;

import com.afklm.repind.msv.customer.adaptor.model.DataModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpsertIndividusRequestCriteria {
    private boolean eligible;
    private String message; //message to explain why idv is not eligible
    private String gin;
    private List<DataModel> individusList;
}
