package com.afklm.rigui.wrapper.merge;

import java.util.List;

public class WrapperMergeResult {

	public boolean resultat;
	
	public WrapperIndividualMerge identification;
	public List<WrapperContractMerge> contracts;
	public List<WrapperTelecomMerge> telecoms;
	public List<WrapperEmailMerge> emails;
	public List<WrapperAddressMerge> addresses;
	
	
	
	public boolean isResultat() {
		return resultat;
	}
	public void setResultat(boolean resultat) {
		this.resultat = resultat;
	}
	public WrapperIndividualMerge getIdentification() {
		return identification;
	}
	public void setIdentification(WrapperIndividualMerge identification) {
		this.identification = identification;
	}
	public List<WrapperContractMerge> getContracts() {
		return contracts;
	}
	public void setContracts(List<WrapperContractMerge> contracts) {
		this.contracts = contracts;
	}
	public List<WrapperTelecomMerge> getTelecoms() {
		return telecoms;
	}
	public void setTelecoms(List<WrapperTelecomMerge> telecoms) {
		this.telecoms = telecoms;
	}
	public List<WrapperEmailMerge> getEmails() {
		return emails;
	}
	public void setEmails(List<WrapperEmailMerge> emails) {
		this.emails = emails;
	}
	public List<WrapperAddressMerge> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<WrapperAddressMerge> addresses) {
		this.addresses = addresses;
	}
	
}
