package com.afklm.rigui.services.builder.w002008;

import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelRoleGP;
import com.afklm.rigui.services.helper.DateHelper;
import com.afklm.soa.stubs.w002008.v1.data.CreateUpdateIndividualGPRequest;
import com.afklm.soa.stubs.w002008.v1.request.IndividualGPRequest;
import com.afklm.soa.stubs.w002008.v1.request.Requestor;
import com.afklm.soa.stubs.w002008.v1.request.RoleGPRequest;
import com.afklm.soa.stubs.w002008.v1.sicindividutype.IndividualInformations;
import com.afklm.soa.stubs.w002008.v1.sicindividutype.RoleGP;
import com.afklm.rigui.entity.individu.IndividuLight;

@Component
public class W002008RequestBuilder {
	
	private static final String DEFAULT_CHANNEL = "W";
	private static final String DEFAULT_SITE = "QVI";
	private static final String DEFAULT_SIGNATURE = "REPIND/IHM";
	
	private static final String UPDATE_ACTION_CODE = "M";
	private static final String DELETE_ACTION_CODE = "S";
	
	private static final String RIGHT_OWNER_OUVRANT_DROIT_LABEL = "O";
	private static final String RIGHT_OWNER_AYANT_DROIT_LABEL = " ";
	
	public CreateUpdateIndividualGPRequest buildUpdateRequest(ModelRoleGP roleGP, IndividuLight individu) {
		
		CreateUpdateIndividualGPRequest request = new CreateUpdateIndividualGPRequest();
		Requestor requestor = getRequestor();
		request.setRequestor(requestor);
		IndividualGPRequest individualGPRequest = getIndividualGPRequest(roleGP);
		individualGPRequest.setProcessMode(UPDATE_ACTION_CODE);
		IndividualInformations individualInformations = getIndividualInformations(individu);
		individualGPRequest.setIndividualInformations(individualInformations);
		request.getIndividualRequest().add(individualGPRequest);
		
		return request;
		
	}

	public CreateUpdateIndividualGPRequest buildDeleteRequest(ModelRoleGP roleGP, IndividuLight individu) {
		
		CreateUpdateIndividualGPRequest request = new CreateUpdateIndividualGPRequest();
		Requestor requestor = getRequestor();
		request.setRequestor(requestor);
		IndividualGPRequest individualGPRequest = getIndividualGPRequest(roleGP);
		individualGPRequest.setProcessMode(DELETE_ACTION_CODE);
		IndividualInformations individualInformations = getIndividualInformations(individu);
		individualGPRequest.setIndividualInformations(individualInformations);
		request.getIndividualRequest().add(individualGPRequest);
		
		return request;
		
	}

	private Requestor getRequestor() {
		Requestor requestor = new Requestor();
		requestor.setChannel(DEFAULT_CHANNEL);
		requestor.setSite(DEFAULT_SITE);
		requestor.setSignature(DEFAULT_SIGNATURE);
		return requestor;
	}
	
	private IndividualGPRequest getIndividualGPRequest(ModelRoleGP modelRoleGP) {
		IndividualGPRequest individualGPRequest = new IndividualGPRequest();
		RoleGPRequest GPRequest = new RoleGPRequest();
		RoleGP roleGP = new RoleGP();
		roleGP.setMatricule(modelRoleGP.getMatricule());
		String rightOwner = getRightOwner(modelRoleGP);
		roleGP.setRightOwner(rightOwner);
		roleGP.setOrganism(modelRoleGP.getCodeOrigin());
		roleGP.setTypology(modelRoleGP.getTypology());
		roleGP.setManagingCompany(modelRoleGP.getCodeCie());
		roleGP.setIdentifierOrder(modelRoleGP.getOrdreIdentifiant());
		roleGP.setExpiryCardDate(DateHelper.convertDateToUTC(modelRoleGP.getDateFinValidite()));
		roleGP.setEntryCompanyDate(DateHelper.convertDateToUTC(modelRoleGP.getDateDebValidite()));
		roleGP.setCountryCode(modelRoleGP.getCodePays());
		GPRequest.getRoleGP().add(roleGP );
		individualGPRequest.setRoleGPRequest(GPRequest);
		return individualGPRequest;
	}
	
	private IndividualInformations getIndividualInformations(IndividuLight individu) {
		IndividualInformations info = new IndividualInformations();
		info.setIdentifier(individu.getSgin());
		info.setBirthDate(DateHelper.convertDateToUTC(individu.getDateNaissance()));
		info.setCivility(individu.getCivilite());
		info.setLastNameSC(individu.getNom());
		info.setFirstNameSC(individu.getPrenom());
		return info;
	}
	
	private String getRightOwner(ModelRoleGP roleGP) {
		return ("001".equals(roleGP.getOrdreIdentifiant()) ? RIGHT_OWNER_OUVRANT_DROIT_LABEL : RIGHT_OWNER_AYANT_DROIT_LABEL);
	}
	
}
