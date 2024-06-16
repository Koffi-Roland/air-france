package com.afklm.repind.msv.customer.adaptor.model.repind;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IndividusDataModel {
    private String gin;
    private String tableName;
    private String action;


    private TmpProfile tmpProfile;

    private Individus individus;
    private Emails emails;
    private Contracts contracts;
    private PostalAddress postalAddress;
    private MarketLanguages marketLanguages;
    private Preference preference;

    private boolean eligible;
    private String message;
}
