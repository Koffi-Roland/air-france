package com.airfrance.repind.firme.searchfirmonmulticriteria.dto.builders;

import com.airfrance.repind.firme.searchfirmonmulticriteria.ds.BuildConditionsDS;
import com.airfrance.repind.firme.searchfirmonmulticriteria.ds.BuildQueryDS;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestDTOBuilder {

	/*===============================================*/
	/*             INJECTED BEANS                    */
	/*===============================================*/
	@Autowired
	private BuildQueryDS buildQueryDSBean = null;
	

	/*===============================================*/
	/*             PUBLIC METHODS                    */
	/*===============================================*/
	
	
	/**
	 * Builds a sub query for recursive search
	 *  Based on Name and phone
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public List<RequestDTO> build_NameCountrycodeAndPhone(RequestDTO requestDTO, String firmType) throws BusinessException
	{
		List<RequestDTO> subRequestsList = new ArrayList<RequestDTO>();
		
		if(BuildConditionsDS.isNameTypeAllConditionSet(requestDTO))
		{
			RequestDTO subRequestDTOLegalName = build_SubNameCountrycodeAndPhone(requestDTO);
			if(subRequestDTOLegalName != null)
			{
				subRequestDTOLegalName.getIdentity().setFirmType(firmType);
				subRequestDTOLegalName.getIdentity().setNameType("LEGAL");
				buildQueryDSBean.buildQuery(subRequestDTOLegalName);
				subRequestsList.add(subRequestDTOLegalName);
			}
			
			RequestDTO subRequestDTOTradeName = build_SubNameCountrycodeAndPhone(requestDTO);
			if(subRequestDTOTradeName != null)
			{
				subRequestDTOTradeName.getIdentity().setFirmType(firmType);
				subRequestDTOTradeName.getIdentity().setNameType("TRADE");
				buildQueryDSBean.buildQuery(subRequestDTOTradeName);
				subRequestsList.add(subRequestDTOTradeName);
			}
			
			RequestDTO subRequestDTOUsualName = build_SubNameCountrycodeAndPhone(requestDTO);
			if(subRequestDTOUsualName != null)
			{
				subRequestDTOUsualName.getIdentity().setFirmType(firmType);
				subRequestDTOUsualName.getIdentity().setNameType("USUAL");
				buildQueryDSBean.buildQuery(subRequestDTOUsualName);
				subRequestsList.add(subRequestDTOUsualName);
			}
		}
		else
		{
			RequestDTO subRequestDTO = build_SubNameCountrycodeAndPhone(requestDTO);
			if(subRequestDTO != null)
			{
				subRequestDTO.getIdentity().setFirmType(firmType);
				buildQueryDSBean.buildQuery(subRequestDTO);
				subRequestsList.add(subRequestDTO);
			}
		}
		
		
		return subRequestsList;
	}
	
	
	/**
	 * Creates a subquery from RequestDTO
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 */
	private RequestDTO build_SubNameCountrycodeAndPhone(RequestDTO requestDTO) throws BusinessException
	{
		RequestDTO subRequestDTO = null;

		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) ||	(BuildConditionsDS.isNameLikeConditionSet(requestDTO)))	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))	
				&& (BuildConditionsDS.isPhoneConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());		
			ContactsDTO contacts = new ContactsDTO();
			PhoneBlocDTO phoneBloc = new PhoneBlocDTO();
			phoneBloc.setPhoneNumber(requestDTO.getContacts().getPhoneBloc().getPhoneNumber());
			if(BuildConditionsDS.isNormalizedPhoneConditionSet(requestDTO))
			{
				phoneBloc.setNormalizedPhoneNumber(requestDTO.getContacts().getPhoneBloc().getNormalizedPhoneNumber());
			}
			if(BuildConditionsDS.isNormalizedPhoneCountryConditionSet(requestDTO))
			{
				phoneBloc.setNormalizedCountry(requestDTO.getContacts().getPhoneBloc().getNormalizedCountry());
			}
			contacts.setPhoneBloc(phoneBloc);
			PostalAddressBlocDTO postalAddressBloc = new PostalAddressBlocDTO();
			postalAddressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			contacts.setPostalAddressBloc(postalAddressBloc);
			subRequestDTO.setContacts(contacts);
		}
		return subRequestDTO;
	}
	
	

	
	/**
	 * Builds a sub query for recursive search
	 *  Based on Name and email
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public List<RequestDTO> build_NameCountrycodeAndEmail(RequestDTO requestDTO, String firmType) throws BusinessException {
		
		List<RequestDTO> subRequestsList = new ArrayList<RequestDTO>();
		
		if(BuildConditionsDS.isNameTypeAllConditionSet(requestDTO))
		{
			RequestDTO subRequestDTOLegalName = build_SubNameCountrycodeAndEmail(requestDTO);
			if(subRequestDTOLegalName != null)
			{
				subRequestDTOLegalName.getIdentity().setFirmType(firmType);
				subRequestDTOLegalName.getIdentity().setNameType("LEGAL");
				buildQueryDSBean.buildQuery(subRequestDTOLegalName);
				subRequestsList.add(subRequestDTOLegalName);
			}
			
			RequestDTO subRequestDTOTradeName = build_SubNameCountrycodeAndEmail(requestDTO);
			if(subRequestDTOTradeName != null)
			{
				subRequestDTOTradeName.getIdentity().setFirmType(firmType);
				subRequestDTOTradeName.getIdentity().setNameType("TRADE");
				buildQueryDSBean.buildQuery(subRequestDTOTradeName);
				subRequestsList.add(subRequestDTOTradeName);
			}
			
			RequestDTO subRequestDTOUsualName = build_SubNameCountrycodeAndEmail(requestDTO);
			if(subRequestDTOUsualName != null)
			{
				subRequestDTOUsualName.getIdentity().setFirmType(firmType);
				subRequestDTOUsualName.getIdentity().setNameType("USUAL");
				buildQueryDSBean.buildQuery(subRequestDTOUsualName);
				subRequestsList.add(subRequestDTOUsualName);
			}
		}
		else
		{
			RequestDTO subRequestDTO = build_SubNameCountrycodeAndEmail(requestDTO);
			if(subRequestDTO != null)
			{
				subRequestDTO.getIdentity().setFirmType(firmType);
				buildQueryDSBean.buildQuery(subRequestDTO);
				subRequestsList.add(subRequestDTO);
			}
		}
		
		return subRequestsList;
	}
	
	
	
	
	/**
	 * Creates a subquery from RequestDTO
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 */
	private RequestDTO build_SubNameCountrycodeAndEmail(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))	
				&& (BuildConditionsDS.isEmailConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
			
			ContactsDTO contacts = new ContactsDTO();
			
			EmailBlocDTO emailBloc = new EmailBlocDTO();
			emailBloc.setEmail(requestDTO.getContacts().getEmailBloc().getEmail());
			contacts.setEmailBloc(emailBloc);
			
			PostalAddressBlocDTO postalAddressBloc = new PostalAddressBlocDTO();
			postalAddressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			contacts.setPostalAddressBloc(postalAddressBloc);
			
			subRequestDTO.setContacts(contacts);
		}
		
		return subRequestDTO;
	}
	
	

	/**
	 * Builds a sub query for recursive search
	 *  Based on Name country code and address
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public List<RequestDTO> build_NameCountrycodeNbandstreetCityZipcode(RequestDTO requestDTO, String firmType) throws BusinessException {
		List<RequestDTO> subRequestsList = new ArrayList<RequestDTO>();
		
		if(BuildConditionsDS.isNameTypeAllConditionSet(requestDTO))
		{
			RequestDTO subRequestDTOLegalName = build_SubNameCountrycodeNbandstreetCityZipcode(requestDTO);
			if(subRequestDTOLegalName != null)
			{
				subRequestDTOLegalName.getIdentity().setFirmType(firmType);
				subRequestDTOLegalName.getIdentity().setNameType("LEGAL");
				buildQueryDSBean.buildQuery(subRequestDTOLegalName);
				subRequestsList.add(subRequestDTOLegalName);
			}
			
			RequestDTO subRequestDTOTradeName = build_SubNameCountrycodeNbandstreetCityZipcode(requestDTO);
			if(subRequestDTOTradeName != null)
			{
				subRequestDTOTradeName.getIdentity().setFirmType(firmType);
				subRequestDTOTradeName.getIdentity().setNameType("TRADE");
				buildQueryDSBean.buildQuery(subRequestDTOTradeName);
				subRequestsList.add(subRequestDTOTradeName);
			}
			
			RequestDTO subRequestDTOUsualName = build_SubNameCountrycodeNbandstreetCityZipcode(requestDTO);
			if(subRequestDTOUsualName != null)
			{
				subRequestDTOUsualName.getIdentity().setFirmType(firmType);
				subRequestDTOUsualName.getIdentity().setNameType("USUAL");
				buildQueryDSBean.buildQuery(subRequestDTOUsualName);
				subRequestsList.add(subRequestDTOUsualName);
			}
		}
		else
		{
			RequestDTO subRequestDTO = build_SubNameCountrycodeNbandstreetCityZipcode(requestDTO);
			if(subRequestDTO != null)
			{
				subRequestDTO.getIdentity().setFirmType(firmType);
				buildQueryDSBean.buildQuery(subRequestDTO);
				subRequestsList.add(subRequestDTO);
			}
		}
		
		return subRequestsList;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name country code and address
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	private RequestDTO build_SubNameCountrycodeNbandstreetCityZipcode(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))		
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))	
				&& (BuildConditionsDS.isCityStrictConditionSet(requestDTO))	
				&& (BuildConditionsDS.isZipStrictConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
			
			ContactsDTO contacts = new ContactsDTO();
			
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			postalAdressBloc.setNumberAndStreet(requestDTO.getContacts().getPostalAddressBloc().getNumberAndStreet());
			postalAdressBloc.setCity(requestDTO.getContacts().getPostalAddressBloc().getCity());
			postalAdressBloc.setZipCode(requestDTO.getContacts().getPostalAddressBloc().getZipCode());
			postalAdressBloc.setCitySearchType(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType());
			postalAdressBloc.setZipSearchType(requestDTO.getContacts().getPostalAddressBloc().getZipSearchType());
			contacts.setPostalAddressBloc(postalAdressBloc);
			
			subRequestDTO.setContacts(contacts);
		}
		return subRequestDTO;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name country code and address
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public List<RequestDTO> build_NameCountrycodeNbandstreetCity(RequestDTO requestDTO, String firmType) throws BusinessException {
		List<RequestDTO> subRequestsList = new ArrayList<RequestDTO>();
		
		if(BuildConditionsDS.isNameTypeAllConditionSet(requestDTO))
		{
			RequestDTO subRequestDTOLegalName = build_SubNameCountrycodeNbandstreetCity(requestDTO);
			if(subRequestDTOLegalName != null)
			{
				subRequestDTOLegalName.getIdentity().setFirmType(firmType);
				subRequestDTOLegalName.getIdentity().setNameType("LEGAL");
				buildQueryDSBean.buildQuery(subRequestDTOLegalName);
				subRequestsList.add(subRequestDTOLegalName);
			}
			
			RequestDTO subRequestDTOTradeName = build_SubNameCountrycodeNbandstreetCity(requestDTO);
			if(subRequestDTOTradeName != null)
			{
				subRequestDTOTradeName.getIdentity().setFirmType(firmType);
				subRequestDTOTradeName.getIdentity().setNameType("TRADE");
				buildQueryDSBean.buildQuery(subRequestDTOTradeName);
				subRequestsList.add(subRequestDTOTradeName);
			}
			
			RequestDTO subRequestDTOUsualName = build_SubNameCountrycodeNbandstreetCity(requestDTO);
			if(subRequestDTOUsualName != null)
			{
				subRequestDTOUsualName.getIdentity().setFirmType(firmType);
				subRequestDTOUsualName.getIdentity().setNameType("USUAL");
				buildQueryDSBean.buildQuery(subRequestDTOUsualName);
				subRequestsList.add(subRequestDTOUsualName);
			}
		}
		else
		{
			RequestDTO subRequestDTO = build_SubNameCountrycodeNbandstreetCity(requestDTO);
			if(subRequestDTO != null)
			{
				subRequestDTO.getIdentity().setFirmType(firmType);
				buildQueryDSBean.buildQuery(subRequestDTO);
				subRequestsList.add(subRequestDTO);
			}
		}
		
		return subRequestsList;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name country code and address
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	private  RequestDTO build_SubNameCountrycodeNbandstreetCity(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;

		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))	
				&& ((BuildConditionsDS.isCityLikeConditionSet(requestDTO)) || (BuildConditionsDS.isCityStrictConditionSet(requestDTO))))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
			
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			postalAdressBloc.setNumberAndStreet(requestDTO.getContacts().getPostalAddressBloc().getNumberAndStreet());
			postalAdressBloc.setCity(requestDTO.getContacts().getPostalAddressBloc().getCity());
			postalAdressBloc.setCitySearchType(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
		}

		return subRequestDTO;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name country code and address
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public List<RequestDTO> build_NameCountrycodeCityAndZip(RequestDTO requestDTO, String firmType) throws BusinessException {
		List<RequestDTO> subRequestsList = new ArrayList<RequestDTO>();
		
		if(BuildConditionsDS.isNameTypeAllConditionSet(requestDTO))
		{
			RequestDTO subRequestDTOLegalName = build_SubNameCountrycodeCityAndZip(requestDTO);
			if(subRequestDTOLegalName != null)
			{
				subRequestDTOLegalName.getIdentity().setFirmType(firmType);
				subRequestDTOLegalName.getIdentity().setNameType("LEGAL");
				buildQueryDSBean.buildQuery(subRequestDTOLegalName);
				subRequestsList.add(subRequestDTOLegalName);
			}
			
			RequestDTO subRequestDTOTradeName = build_SubNameCountrycodeCityAndZip(requestDTO);
			if(subRequestDTOTradeName != null)
			{
				subRequestDTOTradeName.getIdentity().setFirmType(firmType);
				subRequestDTOTradeName.getIdentity().setNameType("TRADE");
				buildQueryDSBean.buildQuery(subRequestDTOTradeName);
				subRequestsList.add(subRequestDTOTradeName);
			}
			
			RequestDTO subRequestDTOUsualName = build_SubNameCountrycodeCityAndZip(requestDTO);
			if(subRequestDTOUsualName != null)
			{
				subRequestDTOUsualName.getIdentity().setFirmType(firmType);
				subRequestDTOUsualName.getIdentity().setNameType("USUAL");
				buildQueryDSBean.buildQuery(subRequestDTOUsualName);
				subRequestsList.add(subRequestDTOUsualName);
			}
		}
		else
		{
			RequestDTO subRequestDTO = build_SubNameCountrycodeCityAndZip(requestDTO);
			if(subRequestDTO != null)
			{
				subRequestDTO.getIdentity().setFirmType(firmType);
				buildQueryDSBean.buildQuery(subRequestDTO);
				subRequestsList.add(subRequestDTO);
			}
		}
		
		return subRequestsList;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name country code and address
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	private RequestDTO build_SubNameCountrycodeCityAndZip(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipStrictConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
			
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			postalAdressBloc.setNumberAndStreet(requestDTO.getContacts().getPostalAddressBloc().getNumberAndStreet());
			postalAdressBloc.setCity(requestDTO.getContacts().getPostalAddressBloc().getCity());
			postalAdressBloc.setCitySearchType(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType());
			postalAdressBloc.setZipCode(requestDTO.getContacts().getPostalAddressBloc().getZipCode());
			postalAdressBloc.setZipSearchType(requestDTO.getContacts().getPostalAddressBloc().getZipSearchType());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
		}
		
		return subRequestDTO;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name country code and ZC1 to ZC4
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public List<RequestDTO>  build_NameCountrycodeZC14(RequestDTO requestDTO, String firmType) throws BusinessException {
		List<RequestDTO> subRequestsList = new ArrayList<RequestDTO>();
		
		if(BuildConditionsDS.isNameTypeAllConditionSet(requestDTO))
		{
			RequestDTO subRequestDTOLegalName = build_SubNameCountrycodeZC14(requestDTO);
			if(subRequestDTOLegalName != null)
			{
				subRequestDTOLegalName.getIdentity().setFirmType(firmType);
				subRequestDTOLegalName.getIdentity().setNameType("LEGAL");
				buildQueryDSBean.buildQuery(subRequestDTOLegalName);
				subRequestsList.add(subRequestDTOLegalName);
			}
			
			RequestDTO subRequestDTOTradeName = build_SubNameCountrycodeZC14(requestDTO);
			if(subRequestDTOTradeName != null)
			{
				subRequestDTOTradeName.getIdentity().setFirmType(firmType);
				subRequestDTOTradeName.getIdentity().setNameType("TRADE");
				buildQueryDSBean.buildQuery(subRequestDTOTradeName);
				subRequestsList.add(subRequestDTOTradeName);
			}
			
			RequestDTO subRequestDTOUsualName = build_SubNameCountrycodeZC14(requestDTO);
			if(subRequestDTOUsualName != null)
			{
				subRequestDTOUsualName.getIdentity().setFirmType(firmType);
				subRequestDTOUsualName.getIdentity().setNameType("USUAL");
				buildQueryDSBean.buildQuery(subRequestDTOUsualName);
				subRequestsList.add(subRequestDTOUsualName);
			}
		}
		else
		{
			RequestDTO subRequestDTO = build_SubNameCountrycodeZC14(requestDTO);
			if(subRequestDTO != null)
			{
				subRequestDTO.getIdentity().setFirmType(firmType);
				buildQueryDSBean.buildQuery(subRequestDTO);
				subRequestsList.add(subRequestDTO);
			}
		}
		
		return subRequestsList;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name country code and ZC1 to ZC4
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	private RequestDTO build_SubNameCountrycodeZC14(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
			
			ContactsDTO contacts = new ContactsDTO();
			
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			CommercialZonesDTO commercialZones = new CommercialZonesDTO();
			commercialZones.setZc1(requestDTO.getCommercialZones().getZc1());
			commercialZones.setZc2(requestDTO.getCommercialZones().getZc2());
			commercialZones.setZc3(requestDTO.getCommercialZones().getZc3());
			commercialZones.setZc4(requestDTO.getCommercialZones().getZc4());
			subRequestDTO.setCommercialZones(commercialZones);
		}
		
		return subRequestDTO;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name country code and ZC1 to ZC5
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public List<RequestDTO>  build_NameCountrycodeZC15(RequestDTO requestDTO, String firmType) throws BusinessException {
		List<RequestDTO> subRequestsList = new ArrayList<RequestDTO>();
		
		if(BuildConditionsDS.isNameTypeAllConditionSet(requestDTO))
		{
			RequestDTO subRequestDTOLegalName = build_SubNameCountrycodeZC15(requestDTO);
			if(subRequestDTOLegalName != null)
			{
				subRequestDTOLegalName.getIdentity().setFirmType(firmType);
				subRequestDTOLegalName.getIdentity().setNameType("LEGAL");
				buildQueryDSBean.buildQuery(subRequestDTOLegalName);
				subRequestsList.add(subRequestDTOLegalName);
			}
			
			RequestDTO subRequestDTOTradeName = build_SubNameCountrycodeZC15(requestDTO);
			if(subRequestDTOTradeName != null)
			{
				subRequestDTOTradeName.getIdentity().setFirmType(firmType);
				subRequestDTOTradeName.getIdentity().setNameType("TRADE");
				buildQueryDSBean.buildQuery(subRequestDTOTradeName);
				subRequestsList.add(subRequestDTOTradeName);
			}
			
			RequestDTO subRequestDTOUsualName = build_SubNameCountrycodeZC15(requestDTO);
			if(subRequestDTOUsualName != null)
			{
				subRequestDTOUsualName.getIdentity().setFirmType(firmType);
				subRequestDTOUsualName.getIdentity().setNameType("USUAL");
				buildQueryDSBean.buildQuery(subRequestDTOUsualName);
				subRequestsList.add(subRequestDTOUsualName);
			}
		}
		else
		{
			RequestDTO subRequestDTO = build_SubNameCountrycodeZC15(requestDTO);
			if(subRequestDTO != null)
			{
				subRequestDTO.getIdentity().setFirmType(firmType);
				buildQueryDSBean.buildQuery(subRequestDTO);
				subRequestsList.add(subRequestDTO);
			}
		}
		
		return subRequestsList;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name country code and ZC1 to ZC5
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	private RequestDTO build_SubNameCountrycodeZC15(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC5ConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
			
			ContactsDTO contacts = new ContactsDTO();
			
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			CommercialZonesDTO commercialZones = new CommercialZonesDTO();
			commercialZones.setZc1(requestDTO.getCommercialZones().getZc1());
			commercialZones.setZc2(requestDTO.getCommercialZones().getZc2());
			commercialZones.setZc3(requestDTO.getCommercialZones().getZc3());
			commercialZones.setZc4(requestDTO.getCommercialZones().getZc4());
			commercialZones.setZc5(requestDTO.getCommercialZones().getZc5());
			subRequestDTO.setCommercialZones(commercialZones);
		}
		
		return subRequestDTO;
	}
	
	
	
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name and ZC
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	private RequestDTO build_SubNameAndZC(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))	
				&& (BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
			
			CommercialZonesDTO commercialZones = new CommercialZonesDTO();
			commercialZones.setZc1(requestDTO.getCommercialZones().getZc1());
			commercialZones.setZc2(requestDTO.getCommercialZones().getZc2());
			commercialZones.setZc3(requestDTO.getCommercialZones().getZc3());
			commercialZones.setZc4(requestDTO.getCommercialZones().getZc4());
			subRequestDTO.setCommercialZones(commercialZones);
		}
		
		return subRequestDTO;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name, country code and ident
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public List<RequestDTO> build_NameCountryAndIdent(RequestDTO requestDTO, String firmType) throws BusinessException {
		List<RequestDTO> subRequestsList = new ArrayList<RequestDTO>();
		
		if(BuildConditionsDS.isNameTypeAllConditionSet(requestDTO))
		{
			RequestDTO subRequestDTOLegalName = build_SubNameCountryAndIdent(requestDTO);
			if(subRequestDTOLegalName != null)
			{
				subRequestDTOLegalName.getIdentity().setFirmType(firmType);
				subRequestDTOLegalName.getIdentity().setNameType("LEGAL");
				buildQueryDSBean.buildQuery(subRequestDTOLegalName);
				subRequestsList.add(subRequestDTOLegalName);
			}
			
			RequestDTO subRequestDTOTradeName = build_SubNameCountryAndIdent(requestDTO);
			if(subRequestDTOTradeName != null)
			{
				subRequestDTOTradeName.getIdentity().setFirmType(firmType);
				subRequestDTOTradeName.getIdentity().setNameType("TRADE");
				buildQueryDSBean.buildQuery(subRequestDTOTradeName);
				subRequestsList.add(subRequestDTOTradeName);
			}
			
			RequestDTO subRequestDTOUsualName = build_SubNameCountryAndIdent(requestDTO);
			if(subRequestDTOUsualName != null)
			{
				subRequestDTOUsualName.getIdentity().setFirmType(firmType);
				subRequestDTOUsualName.getIdentity().setNameType("USUAL");
				buildQueryDSBean.buildQuery(subRequestDTOUsualName);
				subRequestsList.add(subRequestDTOUsualName);
			}
		}
		else
		{
			RequestDTO subRequestDTO = build_SubNameCountryAndIdent(requestDTO);
			if(subRequestDTO != null)
			{
				subRequestDTO.getIdentity().setFirmType(firmType);
				buildQueryDSBean.buildQuery(subRequestDTO);
				subRequestsList.add(subRequestDTO);
			}
		}
		
		return subRequestsList;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name, country code and ident
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	private RequestDTO build_SubNameCountryAndIdent(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
			
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			IdentificationDTO identification = new IdentificationDTO();
			identification.setIdentificationType(requestDTO.getIdentification().getIdentificationType());
			identification.setIdentificationValue(requestDTO.getIdentification().getIdentificationValue());
			subRequestDTO.setIdentification(identification);
		}
		
		return subRequestDTO;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name and country code
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public List<RequestDTO> build_NameCountry(RequestDTO requestDTO, String firmType) throws BusinessException {
		List<RequestDTO> subRequestsList = new ArrayList<RequestDTO>();
		
		if(BuildConditionsDS.isNameTypeAllConditionSet(requestDTO))
		{
			RequestDTO subRequestDTOLegalName = build_SubNameCountry(requestDTO);
			if(subRequestDTOLegalName != null)
			{
				subRequestDTOLegalName.getIdentity().setFirmType(firmType);
				subRequestDTOLegalName.getIdentity().setNameType("LEGAL");
				buildQueryDSBean.buildQuery(subRequestDTOLegalName);
				subRequestsList.add(subRequestDTOLegalName);
			}
			
			RequestDTO subRequestDTOTradeName = build_SubNameCountry(requestDTO);
			if(subRequestDTOTradeName != null)
			{
				subRequestDTOTradeName.getIdentity().setFirmType(firmType);
				subRequestDTOTradeName.getIdentity().setNameType("TRADE");
				buildQueryDSBean.buildQuery(subRequestDTOTradeName);
				subRequestsList.add(subRequestDTOTradeName);
			}
			
			RequestDTO subRequestDTOUsualName = build_SubNameCountry(requestDTO);
			if(subRequestDTOUsualName != null)
			{
				subRequestDTOUsualName.getIdentity().setFirmType(firmType);
				subRequestDTOUsualName.getIdentity().setNameType("USUAL");
				buildQueryDSBean.buildQuery(subRequestDTOUsualName);
				subRequestsList.add(subRequestDTOUsualName);
			}
		}
		else
		{
			RequestDTO subRequestDTO = build_SubNameCountry(requestDTO);
			if(subRequestDTO != null)
			{
				subRequestDTO.getIdentity().setFirmType(firmType);
				buildQueryDSBean.buildQuery(subRequestDTO);
				subRequestsList.add(subRequestDTO);
			}
		}
		
		return subRequestsList;
	}
	

	/**
	 * Builds a sub query for recursive search - Based on Name and country code
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	private RequestDTO build_SubNameCountry(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
			
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
		}
		
		return subRequestDTO;
	}
	
	
	
	
	
	/**
	 * Builds a sub query for recursive search - Based on Name and country code
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	private RequestDTO build_SubNameAndCountry(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
			
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
		}
		
		return subRequestDTO;
	}
	
	
public RequestDTO build_NameLikeCityStrictZipLikeCountry(RequestDTO requestDTO, String string) throws BusinessException {
		
		RequestDTO subRequestDTO = null;
		
		if ( BuildConditionsDS.isNameLikeConditionSet(requestDTO)
				&& BuildConditionsDS.isCityStrictConditionSet(requestDTO)	
				&& BuildConditionsDS.isCountryConditionSet(requestDTO)
				&& BuildConditionsDS.isZipLikeConditionSet(requestDTO))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
			
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			postalAdressBloc.setCity(requestDTO.getContacts().getPostalAddressBloc().getCity());
			postalAdressBloc.setCitySearchType(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType());
			postalAdressBloc.setZipCode(requestDTO.getContacts().getPostalAddressBloc().getZipCode());
			postalAdressBloc.setZipSearchType(requestDTO.getContacts().getPostalAddressBloc().getZipSearchType());			
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			buildQueryDSBean.buildQueryV2(subRequestDTO);
		}
		
		return subRequestDTO;
	}

public RequestDTO build_NameLikeZipLikeCountry(RequestDTO requestDTO, String string) throws BusinessException {
	
	RequestDTO subRequestDTO = null;
	
	if ( BuildConditionsDS.isNameLikeConditionSet(requestDTO)		
			&& BuildConditionsDS.isCountryConditionSet(requestDTO)
			&& BuildConditionsDS.isZipLikeConditionSet(requestDTO)
			&& !BuildConditionsDS.isCityLikeConditionSet(requestDTO)
			&& !BuildConditionsDS.isCityStrictConditionSet(requestDTO))
	{
		subRequestDTO = new RequestDTO();
		subRequestDTO.setQueryIndex(1);
		subRequestDTO.setIdentity(new IdentityDTO());
		subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
		subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
		subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
		subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
		
		ContactsDTO contacts = new ContactsDTO();
		PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
		postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
		postalAdressBloc.setZipCode(requestDTO.getContacts().getPostalAddressBloc().getZipCode());
		postalAdressBloc.setZipSearchType(requestDTO.getContacts().getPostalAddressBloc().getZipSearchType());			
		contacts.setPostalAddressBloc(postalAdressBloc);
		subRequestDTO.setContacts(contacts);
		
		buildQueryDSBean.buildQueryV2(subRequestDTO);
	}
	
	return subRequestDTO;
}

public RequestDTO build_NameLikeCityLikeZipLikeCountry(RequestDTO requestDTO, String string) throws BusinessException {
	
	RequestDTO subRequestDTO = null;
	
	if ( BuildConditionsDS.isNameLikeConditionSet(requestDTO)
			&& BuildConditionsDS.isCityLikeConditionSet(requestDTO)	
			&& BuildConditionsDS.isCountryConditionSet(requestDTO)
			&& BuildConditionsDS.isZipLikeConditionSet(requestDTO))
	{
		subRequestDTO = new RequestDTO();
		subRequestDTO.setQueryIndex(1);
		subRequestDTO.setIdentity(new IdentityDTO());
		subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
		subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
		subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
		subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
		
		ContactsDTO contacts = new ContactsDTO();
		PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
		postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
		postalAdressBloc.setCity(requestDTO.getContacts().getPostalAddressBloc().getCity());
		postalAdressBloc.setCitySearchType(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType());
		postalAdressBloc.setZipCode(requestDTO.getContacts().getPostalAddressBloc().getZipCode());
		postalAdressBloc.setZipSearchType(requestDTO.getContacts().getPostalAddressBloc().getZipSearchType());			
		contacts.setPostalAddressBloc(postalAdressBloc);
		subRequestDTO.setContacts(contacts);
		
		buildQueryDSBean.buildQueryV2(subRequestDTO);
	}
	
	return subRequestDTO;
}

	public RequestDTO build_NameLikeCityStrictCountry(RequestDTO requestDTO, String string) throws BusinessException {
		
		RequestDTO subRequestDTO = null;
		
		if ( BuildConditionsDS.isNameLikeConditionSet(requestDTO)
				&& BuildConditionsDS.isCityStrictConditionSet(requestDTO)	
				&& BuildConditionsDS.isCountryConditionSet(requestDTO)
				&& !BuildConditionsDS.isZipLikeConditionSet(requestDTO)
				&& !BuildConditionsDS.isZipStrictConditionSet(requestDTO))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
			
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			postalAdressBloc.setCity(requestDTO.getContacts().getPostalAddressBloc().getCity());
			postalAdressBloc.setCitySearchType(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			buildQueryDSBean.buildQueryV2(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	/**
	 * Builds a sub query for recursive search - Based on Name
	 *  Based on Name
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public List<RequestDTO> build_Name(RequestDTO requestDTO, String firmType) throws BusinessException {
		List<RequestDTO> subRequestsList = new ArrayList<RequestDTO>();
		
		if(BuildConditionsDS.isNameTypeAllConditionSet(requestDTO))
		{
			RequestDTO subRequestDTOLegalName = build_SubName(requestDTO);
			if(subRequestDTOLegalName != null)
			{
				subRequestDTOLegalName.getIdentity().setFirmType(firmType);
				subRequestDTOLegalName.getIdentity().setNameType("LEGAL");
				buildQueryDSBean.buildQuery(subRequestDTOLegalName);
				subRequestsList.add(subRequestDTOLegalName);
			}
			
			RequestDTO subRequestDTOTradeName = build_SubName(requestDTO);
			if(subRequestDTOTradeName != null)
			{
				subRequestDTOTradeName.getIdentity().setFirmType(firmType);
				subRequestDTOTradeName.getIdentity().setNameType("TRADE");
				buildQueryDSBean.buildQuery(subRequestDTOTradeName);
				subRequestsList.add(subRequestDTOTradeName);
			}
			
			RequestDTO subRequestDTOUsualName = build_SubName(requestDTO);
			if(subRequestDTOUsualName != null)
			{
				subRequestDTOUsualName.getIdentity().setFirmType(firmType);
				subRequestDTOUsualName.getIdentity().setNameType("USUAL");
				buildQueryDSBean.buildQuery(subRequestDTOUsualName);
				subRequestsList.add(subRequestDTOUsualName);
			}
		}
		else
		{
			RequestDTO subRequestDTO = build_SubName(requestDTO);
			if(subRequestDTO != null)
			{
				subRequestDTO.getIdentity().setFirmType(firmType);
				buildQueryDSBean.buildQuery(subRequestDTO);
				subRequestsList.add(subRequestDTO);
			}
		}
		
		return subRequestsList;
	}
	
	
	/**
	 * Builds a sub query for recursive search Based on Name
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	private RequestDTO build_SubName(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isNameStrictConditionSet(requestDTO))
				||	(BuildConditionsDS.isNameLikeConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(new IdentityDTO());
			subRequestDTO.getIdentity().setFirmType(requestDTO.getIdentity().getFirmType());
			subRequestDTO.getIdentity().setName(requestDTO.getIdentity().getName());
			subRequestDTO.getIdentity().setNameType(requestDTO.getIdentity().getNameType());
			subRequestDTO.getIdentity().setNameSearchType(requestDTO.getIdentity().getNameSearchType());
		}
		
		return subRequestDTO;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Phone
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_Phone(RequestDTO requestDTO, String firmType) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(BuildConditionsDS.isPhoneConditionSet(requestDTO))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			
			ContactsDTO contacts = new ContactsDTO();
			PhoneBlocDTO phoneBloc = new PhoneBlocDTO();
			phoneBloc.setPhoneNumber(requestDTO.getContacts().getPhoneBloc().getPhoneNumber());
			if(BuildConditionsDS.isNormalizedPhoneConditionSet(requestDTO))
			{
				phoneBloc.setNormalizedPhoneNumber(requestDTO.getContacts().getPhoneBloc().getNormalizedPhoneNumber());
			}
			if(BuildConditionsDS.isNormalizedPhoneCountryConditionSet(requestDTO))
			{
				phoneBloc.setNormalizedCountry(requestDTO.getContacts().getPhoneBloc().getNormalizedCountry());
			}
			contacts.setPhoneBloc(phoneBloc);
			subRequestDTO.setContacts(contacts);
			
			if((requestDTO.getIdentity() != null)
					&& (requestDTO.getIdentity().getFirmType() != null))
			{
				IdentityDTO identity = new IdentityDTO();
				identity.setFirmType(firmType);
				subRequestDTO.setIdentity(identity);
			}
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			
		}
		
		return subRequestDTO;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Email
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_Email(RequestDTO requestDTO, String firmType) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(BuildConditionsDS.isEmailConditionSet(requestDTO))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			
			ContactsDTO contacts = new ContactsDTO();
			EmailBlocDTO emailBloc = new EmailBlocDTO();
			emailBloc.setEmail(requestDTO.getContacts().getEmailBloc().getEmail());
			contacts.setEmailBloc(emailBloc);
			subRequestDTO.setContacts(contacts);
			
			if((requestDTO.getIdentity() != null)
					&& (requestDTO.getIdentity().getFirmType() != null))
			{
				IdentityDTO identity = new IdentityDTO();
				identity.setFirmType(firmType);
				subRequestDTO.setIdentity(identity);
			}
			
			buildQueryDSBean.buildQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	
	/**
	 * Builds a sub query for recursive search - Based on Phone and ident
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_PhoneAndIdent(RequestDTO requestDTO, String firmType) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isPhoneConditionSet(requestDTO))	
				&& (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			ContactsDTO contacts = new ContactsDTO();
			PhoneBlocDTO phoneBloc = new PhoneBlocDTO();
			phoneBloc.setPhoneNumber(requestDTO.getContacts().getPhoneBloc().getPhoneNumber());
			if(BuildConditionsDS.isNormalizedPhoneConditionSet(requestDTO))
			{
				phoneBloc.setNormalizedPhoneNumber(requestDTO.getContacts().getPhoneBloc().getNormalizedPhoneNumber());
			}
			if(BuildConditionsDS.isNormalizedPhoneCountryConditionSet(requestDTO))
			{
				phoneBloc.setNormalizedCountry(requestDTO.getContacts().getPhoneBloc().getNormalizedCountry());
			}
			contacts.setPhoneBloc(phoneBloc);
			subRequestDTO.setContacts(contacts);
			IdentificationDTO identification = new IdentificationDTO();
			identification.setIdentificationType(requestDTO.getIdentification().getIdentificationType());
			identification.setIdentificationValue(requestDTO.getIdentification().getIdentificationValue());
			subRequestDTO.setIdentification(identification);
			if((requestDTO.getIdentity() != null)
					&& (requestDTO.getIdentity().getFirmType() != null))
			{
				IdentityDTO identity = new IdentityDTO();
				identity.setFirmType(firmType);
				subRequestDTO.setIdentity(identity);
			}
			
			buildQueryDSBean.buildQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	

	/**
	 * Builds a sub query for recursive search - Based on Email and ident
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_EmailAndIdent(RequestDTO requestDTO, String firmType) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isEmailConditionSet(requestDTO))	
				&& (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			ContactsDTO contacts = new ContactsDTO();
			EmailBlocDTO emailBloc = new EmailBlocDTO();
			emailBloc.setEmail(requestDTO.getContacts().getEmailBloc().getEmail());
			contacts.setEmailBloc(emailBloc);
			subRequestDTO.setContacts(contacts);
			IdentificationDTO identification = new IdentificationDTO();
			identification.setIdentificationType(requestDTO.getIdentification().getIdentificationType());
			identification.setIdentificationValue(requestDTO.getIdentification().getIdentificationValue());
			subRequestDTO.setIdentification(identification);
			
			if((requestDTO.getIdentity() != null)
					&& (requestDTO.getIdentity().getFirmType() != null))
			{
				IdentityDTO identity = new IdentityDTO();
				identity.setFirmType(firmType);
				subRequestDTO.setIdentity(identity);
			}
			
			buildQueryDSBean.buildQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	
	
	/**
	 * Builds a sub query for recursive search - Based on Ident
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_Ident(RequestDTO requestDTO, String firmType) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			IdentificationDTO identification = new IdentificationDTO();
			identification.setIdentificationType(requestDTO.getIdentification().getIdentificationType());
			identification.setIdentificationValue(requestDTO.getIdentification().getIdentificationValue());
			subRequestDTO.setIdentification(identification);
			
			if((requestDTO.getIdentity() != null)
					&& (requestDTO.getIdentity().getFirmType() != null))
			{
				IdentityDTO identity = new IdentityDTO();
				identity.setFirmType(firmType);
				subRequestDTO.setIdentity(identity);
			}
			
			buildQueryDSBean.buildQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	/**
	 * Builds a subquery - Based on country code
	 * @param requestDTO
	 * @param firmType
	 * @return
	 * @throws BusinessException
	 */
	public RequestDTO build_Countrycode(RequestDTO requestDTO, String firmType) throws BusinessException 
	{
		RequestDTO subRequestDTO = null;
		
		if(BuildConditionsDS.isCountryConditionSet(requestDTO))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			
			IdentityDTO identity = new IdentityDTO();
			identity.setFirmType(firmType);
			subRequestDTO.setIdentity(identity);

			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			buildQueryDSBean.buildQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	/**
	 * Builds a subquery - Based on ZC1 to ZC5
	 * @param requestDTO
	 * @param firmType
	 * @return
	 * @throws BusinessException
	 */
	public RequestDTO build_ZC5(RequestDTO requestDTO, String firmType) throws BusinessException 
	{
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&&	(BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&&	(BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&&	(BuildConditionsDS.isZC4ConditionSet(requestDTO))
				&&	(BuildConditionsDS.isZC5ConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			
			IdentityDTO identity = new IdentityDTO();
			identity.setFirmType(firmType);
			subRequestDTO.setIdentity(identity);

			CommercialZonesDTO commercialZonesDTO = new CommercialZonesDTO();
			commercialZonesDTO.setZc1(requestDTO.getCommercialZones().getZc1());
			commercialZonesDTO.setZc2(requestDTO.getCommercialZones().getZc2());
			commercialZonesDTO.setZc3(requestDTO.getCommercialZones().getZc3());
			commercialZonesDTO.setZc4(requestDTO.getCommercialZones().getZc4());
			commercialZonesDTO.setZc5(requestDTO.getCommercialZones().getZc5());
			subRequestDTO.setCommercialZones(commercialZonesDTO);
			
			buildQueryDSBean.buildQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	/**
	 * Builds a subquery - Based on ZC1 to ZC5
	 * @param requestDTO
	 * @param firmType
	 * @return
	 * @throws BusinessException
	 */
	public RequestDTO build_ZC1_2(RequestDTO requestDTO, String firmType) throws BusinessException 
	{
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&&	(BuildConditionsDS.isZC2ConditionSet(requestDTO)))				
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			
			subRequestDTO.setIdentity(requestDTO.getIdentity());

			CommercialZonesDTO commercialZonesDTO = new CommercialZonesDTO();
			commercialZonesDTO.setZc1(requestDTO.getCommercialZones().getZc1());
			commercialZonesDTO.setZc2(requestDTO.getCommercialZones().getZc2());
			subRequestDTO.setCommercialZones(commercialZonesDTO);
			
			buildQueryDSBean.buildQueryV2(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	
	/*===============================================*/
	/*             ACCESSORS                         */
	/*===============================================*/

	public BuildQueryDS getBuildQueryDSBean() {
		return buildQueryDSBean;
	}

	public void setBuildQueryDSBean(BuildQueryDS buildQueryDSBean) {
		this.buildQueryDSBean = buildQueryDSBean;
	}



	
}
