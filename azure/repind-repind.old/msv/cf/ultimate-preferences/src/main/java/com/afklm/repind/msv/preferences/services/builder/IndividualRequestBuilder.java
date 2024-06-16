package com.afklm.repind.msv.preferences.services.builder;


import com.afklm.soa.stubs.w000442.v8_0_1.xsd0.Civilian;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd0.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd0.IndividualProfilV3;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd6.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd7.CreateUpdateIndividualRequest;
import org.springframework.stereotype.Component;

@Component
public class IndividualRequestBuilder extends W000442RequestBuilder {

	@Override
	public CreateUpdateIndividualRequest buildCreateRequest(String id, Object model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateUpdateIndividualRequest buildUpdateRequest(String id, Object model) {
		CreateUpdateIndividualRequest request = null;
		
		request = mergeCommonRequestElements(id);
		IndividualRequest individualRequest = getIndividualRequest(RequestType.UPDATE, (ModelIndividualRequest) model);
		request.setIndividualRequest(individualRequest);
		
		return request;
	
	}

	@Override
	public CreateUpdateIndividualRequest buildDeleteRequest(String id, Object model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private IndividualRequest getIndividualRequest(RequestType type, ModelIndividualRequest modelIndividual) {
		
		IndividualRequest request = new IndividualRequest();
		
		ModelIndividualIdentificationRequest identification = modelIndividual.getIdentification();
		ModelIndividualProfileRequest profile = modelIndividual.getProfile();
		
		IndividualInformationsV3 informations = new IndividualInformationsV3();
		informations.setIdentifier(identification.getGin());
		informations.setFirstNameSC(identification.getFirstname());
		informations.setLastNameSC(identification.getLastname());
		informations.setBirthDate(DateHelper.convertDateToUTC(identification.getBirthdate()));
		informations.setCivility(identification.getCivility());
		informations.setSecondFirstName(identification.getMiddlename());
		informations.setFirstNamePseudonym(identification.getAliasFirstname());
		informations.setLastNamePseudonym(identification.getAliasLastname());
		informations.setStatus(identification.getStatus());
		informations.setGender(identification.getGender());
		request.setIndividualInformations(informations);
		
		Civilian civilian = new Civilian();
		civilian.setTitleCode(identification.getTitle());
		request.setCivilian(civilian);
		
		IndividualProfilV3 individualProfile = new IndividualProfilV3();
		individualProfile.setEmailOptin(profile.getNat());
		individualProfile.setLanguageCode(profile.getMotherLanguage());
		individualProfile.setProAreaCode(profile.getBranch());
		request.setIndividualProfil(individualProfile);
		
		return request;
		
	}

}
