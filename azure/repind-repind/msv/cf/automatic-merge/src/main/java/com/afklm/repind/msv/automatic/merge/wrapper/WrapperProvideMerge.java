package com.afklm.repind.msv.automatic.merge.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WrapperProvideMerge {

    private WrapperIndividualMerge identification;
    private List<WrapperAccountDataMerge> accounts;
    private List<WrapperContractMerge> contracts;
    public List<WrapperTelecomMerge> telecoms;
    public List<WrapperEmailMerge> emails;
    public List<WrapperAddressMerge> addresses;
    public List<WrapperAddressMerge> addressesNotMergeable;
    public List<WrapperRoleGPMerge> rolesGP;
}
