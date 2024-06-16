package com.afklm.rigui.wrapper.merge;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WrapperProvideMerge {
	
	public WrapperIndividualMerge identification;
	public List<WrapperAccountDataMerge> accounts;
	public List<WrapperContractMerge> contracts;
	public List<WrapperTelecomMerge> telecoms;
	public List<WrapperEmailMerge> emails;
	public List<WrapperAddressMerge> addresses;
	public List<WrapperAddressMerge> addressesNotMergeable;
	public List<WrapperRoleGPMerge> rolesGP;
}
