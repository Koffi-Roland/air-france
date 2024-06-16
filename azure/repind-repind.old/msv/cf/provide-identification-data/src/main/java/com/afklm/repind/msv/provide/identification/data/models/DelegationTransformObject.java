package com.afklm.repind.msv.provide.identification.data.models;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.soa.stubs.r000378.v1.model.DelegationIndividualData;
import com.afklm.soa.stubs.r000378.v1.model.DelegationStatusData;
import com.afklm.soa.stubs.r000378.v1.model.Signature;
import com.afklm.soa.stubs.r000378.v1.model.Telecom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/*
 * A class used to refactor some code and make the data manipulation easier
 */
public class DelegationTransformObject {
    private Individu individu;
    private AccountIdentifier accountIdentifier;
    private List<Telecom> telecomList;
    private DelegationStatusData delegationStatusData;
    private DelegationIndividualData delegationIndividualData;
    private Signature signature;
}
