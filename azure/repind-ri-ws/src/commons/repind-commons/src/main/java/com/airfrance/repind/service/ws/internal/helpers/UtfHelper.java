package com.airfrance.repind.service.ws.internal.helpers;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.util.SicStringUtils;
import com.airfrance.repindutf8.dto.utf.UtfDTO;
import com.airfrance.repindutf8.dto.utf.UtfDataDTO;
import com.airfrance.repindutf8.service.utf.internal.UtfDS;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UtfHelper {

	@Autowired
	private UtfDS utfDS;

	private Set<UtfDataDTO> _fillUtfDataDto(final UtfDatasDTO utfDatas, final boolean isCreate)
			throws InvalidParameterException {
		if (utfDatas == null || utfDatas.getUtfDataDTO() == null) {
			return null;
		}
		final Set<UtfDataDTO> setUtfDataDto = new HashSet<>();
		for (final utfDataDTO utfData : utfDatas.getUtfDataDTO()) {
			final UtfDataDTO utfDataDTO = new UtfDataDTO();
			utfDataDTO.setKey(utfData.getKey());
			utfDataDTO.setValue(utfData.getValue());
			setUtfDataDto.add(utfDataDTO);
		}
		return setUtfDataDto;
	}

	public static void NormalizeSCNames(IndividualInformationsDTO individual) {
		if (individual == null) {
			return;
		}
		individual.setFirstNameSC(SicStringUtils.replaceAccentAndRemoveSpecialChars(individual.getFirstNameSC()));
		individual.setLastNameSC(SicStringUtils.replaceAccentAndRemoveSpecialChars(individual.getLastNameSC()));
		individual.setMiddleNameSC(SicStringUtils.replaceAccentAndRemoveSpecialChars(individual.getMiddleNameSC()));
	}


	public UtfRequestDTO fillPREF(PreferenceDTO preferenceDTO){

		final UtfDTO utfDto = new UtfDTO();
		utfDto.setType("PRF");
		utfDto.setGin(preferenceDTO.getGin());

		Set<UtfDataDTO> utfDataDTOs = new HashSet<UtfDataDTO>();
		for(PreferenceDataDTO preferenceDataDTO : preferenceDTO.getPreferenceDataDTO()){
			UtfDataDTO utfDataDTO = new UtfDataDTO();
			utfDataDTO.setKey(preferenceDataDTO.getKey());
			utfDataDTO.setValue(preferenceDataDTO.getValue());

			utfDataDTOs.add(utfDataDTO);
		}

		utfDto.setUtfDataDTO(utfDataDTOs);

		return null;
	}

	// Business rule, if firstNameSC or lastNameSC is filled in the original
	// request, autofill a new IND utf
	// if a utf type IND already exist in the request, do nothing
	private UtfRequestDTO _fillUtfINDbySC(UtfRequestDTO utfRequest, final String firstnameSC, final String lastnameSC,
			final String gin) throws JrafDomainException {
		if (StringUtils.isBlank(firstnameSC) && StringUtils.isBlank(lastnameSC)) {
			return utfRequest;
		}
		if (utfRequest != null && utfRequest.getUtfDTO() != null) {
			for (final com.airfrance.repind.dto.ws.UtfDTO utf : utfRequest.getUtfDTO()) {
				if ("IND".equals(utf.getType())) {
					// if IND type already exist in the request, do nothing
					return utfRequest;
				}
			}
		}

		final UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		utfDto.setGin(gin);

		if (utfRequest == null) {
			utfRequest = new UtfRequestDTO();
		}

		if(utfRequest.getUtfDTO() == null) {
			final List<com.airfrance.repind.dto.ws.UtfDTO> utfDTO = new ArrayList<com.airfrance.repind.dto.ws.UtfDTO>();
			utfRequest.setUtfDTO(utfDTO);
		}

		final com.airfrance.repind.dto.ws.UtfDTO utf = new com.airfrance.repind.dto.ws.UtfDTO();
		utf.setType("IND");

		final List<UtfDTO> listUtfDto = utfDS.findByExample(utfDto);
		if(listUtfDto != null && listUtfDto.size() == 1) {
			// If the UTF type IND already exist, update it with SC values
			utf.setId(listUtfDto.get(0).getUtfId());
		}

		final UtfDatasDTO utfDatas = new UtfDatasDTO();

		if(utfDatas.getUtfDataDTO() == null) {
			final List<utfDataDTO> utfDataDTO = new ArrayList<utfDataDTO>();
			utfDatas.setUtfDataDTO(utfDataDTO);
		}

		if (StringUtils.isNotBlank(firstnameSC)) {
			final utfDataDTO firstname = new utfDataDTO();
			firstname.setKey("FIRST_NAME");
			firstname.setValue(firstnameSC);
			utfDatas.getUtfDataDTO().add(firstname);
		}

		if (StringUtils.isNotBlank(lastnameSC)) {
			final utfDataDTO lastname = new utfDataDTO();
			lastname.setKey("LAST_NAME");
			lastname.setValue(lastnameSC);
			utfDatas.getUtfDataDTO().add(lastname);
		}
		utf.setUtfDatasDTO(utfDatas);
		utfRequest.getUtfDTO().add(utf);
		return utfRequest;
	}

	// Business rule, if emailSC is filled in the original
	// request, autofill a new MEL utf
	// if a utf type MEL already exist in the request, do nothing
	private UtfRequestDTO _fillUtfMELbySC(UtfRequestDTO utfRequest, final String emailSC, final String gin) throws JrafDomainException {
		if (StringUtils.isBlank(emailSC)) {
			return utfRequest;
		}
		if (utfRequest != null && utfRequest.getUtfDTO() != null) {
			for (final com.airfrance.repind.dto.ws.UtfDTO utf : utfRequest.getUtfDTO()) {
				if ("MEL".equals(utf.getType())) {
					// if MEL type already exist in the request, do nothing
					return utfRequest;
				}
			}
		}

		final UtfDTO utfDto = new UtfDTO();
		utfDto.setType("MEL");
		utfDto.setGin(gin);

		if (utfRequest == null) {
			utfRequest = new UtfRequestDTO();
		}

		if(utfRequest.getUtfDTO() == null) {
			final List<com.airfrance.repind.dto.ws.UtfDTO> utfDTO = new ArrayList<com.airfrance.repind.dto.ws.UtfDTO>();
			utfRequest.setUtfDTO(utfDTO);
		}

		final com.airfrance.repind.dto.ws.UtfDTO utf = new com.airfrance.repind.dto.ws.UtfDTO();
		utf.setType("MEL");

		final List<UtfDTO> listUtfDto = utfDS.findByExample(utfDto);
		if(listUtfDto != null && listUtfDto.size() == 1) {
			// If the UTF type MEL already exist, update it with SC values
			utf.setId(listUtfDto.get(0).getUtfId());
		}

		final UtfDatasDTO utfDatas = new UtfDatasDTO();

		if(utfDatas.getUtfDataDTO() == null) {
			final List<utfDataDTO> utfDataDTO = new ArrayList<utfDataDTO>();
			utfDatas.setUtfDataDTO(utfDataDTO);
		}

		if (StringUtils.isNotBlank(emailSC)) {
			final utfDataDTO firstname = new utfDataDTO();
			firstname.setKey("EMAIL");
			firstname.setValue(emailSC);
			utfDatas.getUtfDataDTO().add(firstname);
		}

		utf.setUtfDatasDTO(utfDatas);
		utfRequest.getUtfDTO().add(utf);
		return utfRequest;
	}

	// Business rule, if emailSC is filled in the original
	// request, autofill a new PAC utf
	// if a utf type PAC already exist in the request, do nothing
	private UtfRequestDTO _fillUtfPACbySC(UtfRequestDTO utfRequest, final String gin,  
			// raisonSociale, numEtNomRue, complementAdresse, ville, codePostal, state, district
			final String postalAddressCorpo, final String postalAddressNumAndStreet, final String postalAddressAddInfo, 
			final String postalAddressCity, final String postalAddressZip, final String postalAddressState, final String postalAddressDistrict) throws JrafDomainException {
		if (StringUtils.isBlank(postalAddressCorpo) && 
				StringUtils.isBlank(postalAddressNumAndStreet) &&
				StringUtils.isBlank(postalAddressAddInfo) &&
				StringUtils.isBlank(postalAddressCity) &&
				StringUtils.isBlank(postalAddressZip) &&
				StringUtils.isBlank(postalAddressState) &&
				StringUtils.isBlank(postalAddressDistrict)
				) {
			return utfRequest;
		}
		if (utfRequest != null && utfRequest.getUtfDTO() != null) {
			for (final com.airfrance.repind.dto.ws.UtfDTO utf : utfRequest.getUtfDTO()) {
				if ("PAC".equals(utf.getType())) {
					// if MEL type already exist in the request, do nothing
					return utfRequest;
				}
			}
		}

		final UtfDTO utfDto = new UtfDTO();
		utfDto.setType("PAC");
		utfDto.setGin(gin);

		if (utfRequest == null) {
			utfRequest = new UtfRequestDTO();
		}

		if(utfRequest.getUtfDTO() == null) {
			final List<com.airfrance.repind.dto.ws.UtfDTO> utfDTO = new ArrayList<com.airfrance.repind.dto.ws.UtfDTO>();
			utfRequest.setUtfDTO(utfDTO);
		}

		final com.airfrance.repind.dto.ws.UtfDTO utf = new com.airfrance.repind.dto.ws.UtfDTO();
		utf.setType("PAC");

		final List<UtfDTO> listUtfDto = utfDS.findByExample(utfDto);
		if(listUtfDto != null && listUtfDto.size() == 1) {
			// If the UTF type PAC already exist, update it with SC values
			utf.setId(listUtfDto.get(0).getUtfId());
		}

		final UtfDatasDTO utfDatas = new UtfDatasDTO();

		if(utfDatas.getUtfDataDTO() == null) {
			final List<utfDataDTO> utfDataDTO = new ArrayList<utfDataDTO>();
			utfDatas.setUtfDataDTO(utfDataDTO);
		}

		_AddUtfData("CORPORATE_NAME", postalAddressCorpo, utfDatas);
		_AddUtfData("NUMBER_AND_STREET", postalAddressNumAndStreet, utfDatas);
		_AddUtfData("ADDITIONAL_INFORMATION", postalAddressAddInfo, utfDatas);
		_AddUtfData("CITY", postalAddressCity, utfDatas);
		_AddUtfData("ZIP_CODE", postalAddressZip, utfDatas);
		_AddUtfData("STATE", postalAddressState, utfDatas);
		_AddUtfData("DISTRICT", postalAddressDistrict, utfDatas);

		utf.setUtfDatasDTO(utfDatas);
		utfRequest.getUtfDTO().add(utf);
		return utfRequest;
	}
	
	private void _AddUtfData(String utfKey, String utfValue, UtfDatasDTO utfDatas) {
		
		if (StringUtils.isNotBlank(utfValue)) {
			final utfDataDTO data = new utfDataDTO();
			data.setKey(utfKey);
			data.setValue(utfValue);
			utfDatas.getUtfDataDTO().add(data);
		}
	}
	
	private UtfDTO _generateDTOupdateOrDelete(final com.airfrance.repind.dto.ws.UtfDTO utf) throws InvalidParameterException {
		String id = null;
		if(utf != null && utf.getId() != null) {
			id = String.valueOf(utf.getId());
		}
		if (utf == null || StringUtils.isBlank(id)) {
			throw new InvalidParameterException("utf & utf id must not be null");
		}
		if (!SicStringUtils.isNumeric(id)) {
			throw new InvalidParameterException("ID must be an int");
		}
		final UtfDTO utfDto = new UtfDTO();
		utfDto.setUtfId(Long.parseLong(id));
		return utfDto;
	}

	
	public void processIndividualOnUTF8(UtfRequestDTO utfRequest, final String gin, final RequestorDTO requestor, final String firstnameSC, final String lastnameSC) throws JrafDomainException, UtfException {
		// Business rule, if firstNameSC or lastNameSC is filled in the original request, autofill a new IND utf
		utfRequest = _fillUtfINDbySC(utfRequest, firstnameSC, lastnameSC, gin);

		if (utfRequest == null || utfRequest.getUtfDTO() == null) {
			return;
		}
		if (StringUtils.isBlank(gin) || requestor == null) {
			throw new InvalidParameterException("GIN or Signature must not be null");
		}
		process(utfRequest, gin, requestor);
	}	

	public void processEmailOnUTF8(UtfRequestDTO utfRequest, final String gin, final RequestorDTO requestor, final String emailSC) throws JrafDomainException, UtfException {
		// Business rule, if emailSC is filled in the original request, autofill a new MEL utf
		utfRequest = _fillUtfMELbySC(utfRequest, emailSC, gin);

		if (utfRequest == null || utfRequest.getUtfDTO() == null) {
			return;
		}
		if (StringUtils.isBlank(gin) || requestor == null) {
			throw new InvalidParameterException("GIN or Signature must not be null");
		}
		process(utfRequest, gin, requestor);
	}	

	public void processPostalAddressOnUTF8(UtfRequestDTO utfRequest, final String gin, final RequestorDTO requestor,
		// CORPO           NUM STREET  ADD INFO      CITY    ZIPCODE STATE DISTRICT 
		final String raisonSociale, final String numEtNomRue, final String complementAdresse, final String ville, final String codePostal, final String state, final String district) throws JrafDomainException, UtfException {
	
		// business rule, if one of the postal address is filled in the original
		// request, autofill a new PAC utf
		utfRequest = _fillUtfPACbySC(utfRequest, gin, raisonSociale, numEtNomRue, complementAdresse, ville, codePostal, state, district);
		
		// raisonSociale, numEtNomRue, complementAdresse, ville, codePostal, state, district
		if (utfRequest == null || utfRequest.getUtfDTO() == null) {
			return;
		}
		if (StringUtils.isBlank(gin) || requestor == null) {
			throw new InvalidParameterException("GIN or Signature must not be null");
		}
		process(utfRequest, gin, requestor);
	}	
		
	private void process(UtfRequestDTO utfRequest, final String gin, final RequestorDTO requestor) throws JrafDomainException, UtfException {
		for (final com.airfrance.repind.dto.ws.UtfDTO utf : utfRequest.getUtfDTO()) {
			String id = null;
			if(utf.getId() != null) { 
				id = String.valueOf(utf.getId());
			}
			final UtfDTO utfDto = StringUtils.isBlank(id) ? new UtfDTO() : _generateDTOupdateOrDelete(utf);
			utfDto.setGin(gin);
			utfDto.setType(utf.getType());
			utfDto.setUtfDataDTO(_fillUtfDataDto(utf.getUtfDatasDTO(), utfDto.getUtfId() == null));
			utfDS.process(utfDto, requestor.getSignature(), requestor.getSite());
		}
	}

	public List<UtfDTO> findIndividuByGin(String gin) throws JrafDomainException {
		final UtfDTO utfDto = new UtfDTO();
		utfDto.setType("IND");
		utfDto.setGin(gin);

		return utfDS.findByExample(utfDto);
	}

	public List<UtfDTO> findEmailByGin(String gin) throws JrafDomainException {
		final UtfDTO utfDto = new UtfDTO();
		utfDto.setType("MEL");
		utfDto.setGin(gin);

		return utfDS.findByExample(utfDto);
	}
	
	public List<PreferenceDataDTO> utfValues(List<PreferenceDTO> prefDtoFromWs) {

		List<PreferenceDataDTO> preferencesDatatUTF = new ArrayList<>();

		for (PreferenceDTO prefDto : prefDtoFromWs) {
			for (PreferenceDataDTO prefDataDto : prefDto.getPreferenceDataDTO()) {
				if(SicStringUtils.isISOLatinString(prefDataDto.getValue()));
				preferencesDatatUTF.add(prefDataDto);
			}
		}

		return preferencesDatatUTF;
	}

	public void process(String sgin, List<PreferenceDataDTO> preferencesDataUTF,
			com.airfrance.repind.dto.individu.SignatureDTO signFromWs) throws JrafDomainException, UtfException {
		final Set<UtfDataDTO> setUtfDataDto = new HashSet<>();

		for (PreferenceDataDTO prefDataDto : preferencesDataUTF) {
			final UtfDataDTO utfDataDTO = new UtfDataDTO();
			utfDataDTO.setKey(prefDataDto.getKey());
			utfDataDTO.setValue(prefDataDto.getValue());
			setUtfDataDto.add(utfDataDTO);
		}
		
		final UtfDTO utfDto = new UtfDTO();
		utfDto.setType("PRF");
		utfDto.setGin(sgin);
		utfDto.setUtfDataDTO(setUtfDataDto);

		utfDS.process(utfDto, signFromWs.getSignature(), signFromWs.getSite());
	}

	/////////////////////
	// POSTAL ADDRESSE //
	/////////////////////
	
/*	public void processPostalAddressOnUTF8(UtfRequestDTO utfRequest, final String gin, final RequestorDTO requestor,
			// CORPO           NUM STREET  ADD INFO      CITY    ZIPCODE STATE DISTRICT 
			final String raisonSociale, final String numEtNomRue, final String complementAdresse, final String ville, final String codePostal, final String state, final String district) throws JrafDomainException, UtfException {
		
		// business rule, if one of the postal address is filled in the original
		// request, autofill a new PAC utf
		utfRequest = _fillUtfPACbySC(utfRequest, gin, raisonSociale, numEtNomRue, complementAdresse, ville, codePostal, state, district);

		if (utfRequest == null || utfRequest.getUtfDTO() == null) {
			return;
		}
		if (StringUtils.isBlank(gin) || requestor == null) {
			throw new InvalidParameterException("GIN or Signature must not be null");
		}
		for (final com.airfrance.repind.dto.ws.UtfDTO utf : utfRequest.getUtfDTO()) {
			String id = null;
			if(utf.getId() != null) { 
				id = String.valueOf(utf.getId());
			}
			final UtfDTO utfDto = StringUtils.isBlank(id) ? new UtfDTO()
					: _generateDTOupdateOrDelete(utf);
			utfDto.setGin(gin);
			utfDto.setType(utf.getType());
			utfDto.setUtfDataDTO(_fillUtfDataDto(utf.getUtfDatasDTO(), utfDto.getUtfId() == null));
			utfDS.process(utfDto, requestor.getSignature(), requestor.getSite());
		}
		
		
	}	
*/
	// Business rule, if raisonSocial or complementAdresse or numEtNomRue or localite or ville is filled in the original
	// request, autofill a new ADR utf
	// if a utf type ADR already exist in the request, do nothing
/*
	private UtfRequestDTO _fillUtfADRbySC(UtfRequestDTO utfRequest, final String gin, 
			final String raisonSociale, final String numEtNomRue, final String complementAdresse, final String ville, final String codePostal, final String state, final String district
			) throws JrafDomainException {
		if (
				StringUtils.isBlank(raisonSociale) && StringUtils.isBlank(numEtNomRue) && StringUtils.isBlank(complementAdresse) && 
				StringUtils.isBlank(ville) && StringUtils.isBlank(codePostal) &&  StringUtils.isBlank(state) &&  StringUtils.isBlank(district) 
			) {
			return utfRequest;
		}
		if (utfRequest != null && utfRequest.getUtfDTO() != null) {
			for (final com.airfrance.repind.dto.ws.UtfDTO utf : utfRequest.getUtfDTO()) {
				if ("ADR".equals(utf.getType())) {
					// if ADR type already exist in the request, do nothing
					return utfRequest;
				}
			}
		}

		final UtfDTO utfDto = new UtfDTO();
		utfDto.setType("ADR");
		utfDto.setGin(gin);

		if (utfRequest == null) {
			utfRequest = new UtfRequestDTO();
		}

		if(utfRequest.getUtfDTO() == null) {
			final List<com.airfrance.repind.dto.ws.UtfDTO> utfDTO = new ArrayList<com.airfrance.repind.dto.ws.UtfDTO>();
			utfRequest.setUtfDTO(utfDTO);
		}

		final com.airfrance.repind.dto.ws.UtfDTO utf = new com.airfrance.repind.dto.ws.UtfDTO();
		utf.setType("ADR");

		final List<UtfDTO> listUtfDto = utfDS.findByExample(utfDto);
		if(listUtfDto != null && listUtfDto.size() == 1) {
			// If the UTF type ADR already exist, update it with SC values
			utf.setId(listUtfDto.get(0).getUtfId());
		}

		final UtfDatasDTO utfDatas = new UtfDatasDTO();

		if(utfDatas.getUtfDataDTO() == null) {
			final List<utfDataDTO> utfDataDTO = new ArrayList<utfDataDTO>();
			utfDatas.setUtfDataDTO(utfDataDTO);
		}

		if (StringUtils.isNotBlank(raisonSociale)) {
			final utfDataDTO rs = new utfDataDTO();
			rs.setKey("RAISON_SOCIALE");
			rs.setValue(raisonSociale);
			utfDatas.getUtfDataDTO().add(rs);
		}

		if (StringUtils.isNotBlank(complementAdresse)) {
			final utfDataDTO ca = new utfDataDTO();
			ca.setKey("COMPLEMENT_ADRESSE");
			ca.setValue(complementAdresse);
			utfDatas.getUtfDataDTO().add(ca);
		}

		if (StringUtils.isNotBlank(numEtNomRue)) {
			final utfDataDTO nnr = new utfDataDTO();
			nnr.setKey("NUM_NOM_RUE");
			nnr.setValue(numEtNomRue);
			utfDatas.getUtfDataDTO().add(nnr);
		}

		if (StringUtils.isNotBlank(localite)) {
			final utfDataDTO l = new utfDataDTO();
			l.setKey("LOCALITE");
			l.setValue(localite);
			utfDatas.getUtfDataDTO().add(l);
		}
		
		if (StringUtils.isNotBlank(ville)) {
			final utfDataDTO v = new utfDataDTO();
			v.setKey("VILLE");
			v.setValue(ville);
			utfDatas.getUtfDataDTO().add(v);
		}

		utf.setUtfDatasDTO(utfDatas);
		utfRequest.getUtfDTO().add(utf);
		return utfRequest;
	}
*/	
}
