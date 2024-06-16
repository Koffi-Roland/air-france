package com.afklm.repind.provideindividualreferencetable.v1.transformers;

import com.afklm.soa.stubs.w001588.v1.data.ProvideIndividualReferenceTableResponse;
import com.afklm.soa.stubs.w001588.v1.response.RefCompref;
import com.afklm.soa.stubs.w001588.v1.response.RefComprefCountryMarket;
import com.afklm.soa.stubs.w001588.v1.response.RefGenericCodeLabelsType;
import com.airfrance.repind.dto.reference.RefComPrefCountryMarketDTO;
import com.airfrance.repind.dto.reference.RefComPrefDTO;
import com.airfrance.repind.dto.reference.RefGenericCodeLabelsTypeDTO;

import java.util.List;

public class ProvideIndividualReferenceTableTransformV1 {
	
	public static ProvideIndividualReferenceTableResponse transformRefComPrefToProvideIndividualReferenceTable(List<RefComPrefDTO> listRefComPrefDTO){
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		for(RefComPrefDTO element : listRefComPrefDTO){
			RefCompref elementResponse = new RefCompref();
			elementResponse.setRefComprefId(String.valueOf(element.getRefComprefId()));
			elementResponse.setDomain(element.getDomain().getCodeDomain());
			elementResponse.setGroupeType(element.getComGroupeType().getCodeGType());
			elementResponse.setType(element.getComType().getCodeType());
			elementResponse.setDescription(element.getDescription());
			elementResponse.setMandatoryOptin(element.getMandatoryOptin());
			elementResponse.setMarket(element.getMarket());
			elementResponse.setDefaultLanguage1(element.getDefaultLanguage1());
			elementResponse.setDefaultLanguage2(element.getDefaultLanguage2());
			elementResponse.setDefaultLanguage3(element.getDefaultLanguage3());
			elementResponse.setDefaultLanguage4(element.getDefaultLanguage4());
			elementResponse.setDefaultLanguage5(element.getDefaultLanguage5());
			elementResponse.setDefaultLanguage6(element.getDefaultLanguage6());
			elementResponse.setDefaultLanguage7(element.getDefaultLanguage7());
			elementResponse.setDefaultLanguage8(element.getDefaultLanguage8());
			elementResponse.setDefaultLanguage9(element.getDefaultLanguage9());
			elementResponse.setDefaultLanguage10(element.getDefaultLanguage10());
			elementResponse.setFieldA(element.getFieldA());
			elementResponse.setFieldN(element.getFieldN());
			elementResponse.setFieldT(element.getFieldT());
			elementResponse.setMedia(element.getMedia().getCodeMedia());
			response.getRefCompref().add(elementResponse);
		}
		return response;	
	}

	public static ProvideIndividualReferenceTableResponse transformRefComPrefCountryMarketToProvideIndividualReferenceTable(List<RefComPrefCountryMarketDTO> listRefComPrefCountryMarketDTO) {
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		for(RefComPrefCountryMarketDTO element : listRefComPrefCountryMarketDTO){
			RefComprefCountryMarket elementResponse = new RefComprefCountryMarket();
			elementResponse.setCodePays(element.getCodePays());
			elementResponse.setMarket(element.getMarket());
			response.getRefComprefMarketCountry().add(elementResponse);
		}
		return response;
	}
	
	// Catch response potentially filled with an other data to add Generic data structure
	public static ProvideIndividualReferenceTableResponse transformRefComPrefTypeToProvideIndividualReferenceTable(List<RefGenericCodeLabelsTypeDTO> listRefGenericCodeLabelsTypeDTO) {
		ProvideIndividualReferenceTableResponse response = new ProvideIndividualReferenceTableResponse();
		
		for(RefGenericCodeLabelsTypeDTO element : listRefGenericCodeLabelsTypeDTO){
			RefGenericCodeLabelsType elementResponse = new RefGenericCodeLabelsType();
			elementResponse.setCode(element.getCode());
			elementResponse.setLabelFr(element.getLabelFr());
			elementResponse.setLabelEn(element.getLabelEn());
			response.getRefGenericCodeLabelsType().add(elementResponse);
		}
		return response;	
	}
}
