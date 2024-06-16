package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.builders;

import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.BusinessException;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.RequestDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds.BuildConditionsDS;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds.BuildQueryDS;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("AgencyRequestDTOBuilder")
public class RequestDTOBuilder {

	/*===============================================*/
	/*             INJECTED BEANS                    */
	/*===============================================*/
	@Autowired
	@Qualifier("AgenceBuildQueryDS")
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
	public RequestDTO build_NameAndPhone(RequestDTO requestDTO) throws BusinessException
	{
		RequestDTO subRequestDTO = null;

		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))		
				&& (BuildConditionsDS.isPhoneConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
			ContactsDTO contacts = new ContactsDTO();
			PhoneBlocDTO phoneBloc = new PhoneBlocDTO();
			phoneBloc.setPhoneNumber(requestDTO.getContacts().getPhoneBloc().getPhoneNumber());
			contacts.setPhoneBloc(phoneBloc);

			subRequestDTO.setContacts(contacts);
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
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
	public RequestDTO build_NameAndEmail(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))	
				&& (BuildConditionsDS.isEmailConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
			ContactsDTO contacts = new ContactsDTO();
			EmailBlocDTO emailBloc = new EmailBlocDTO();
			emailBloc.setEmail(requestDTO.getContacts().getEmailBloc().getEmail());
			contacts.setEmailBloc(emailBloc);

			subRequestDTO.setContacts(contacts);
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
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
	public RequestDTO build_NameCountrycodeNbandstreetCityZipcode(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))	
				&& (BuildConditionsDS.isCityStrictConditionSet(requestDTO))	
				&& (BuildConditionsDS.isZipStrictConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
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
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
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
	public RequestDTO build_NameCountrycodeNbandstreetCity(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;

		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isNumberAndStreetConditionSet(requestDTO))	
				&& ((BuildConditionsDS.isCityLikeConditionSet(requestDTO)) || (BuildConditionsDS.isCityStrictConditionSet(requestDTO))))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			postalAdressBloc.setNumberAndStreet(requestDTO.getContacts().getPostalAddressBloc().getNumberAndStreet());
			postalAdressBloc.setCity(requestDTO.getContacts().getPostalAddressBloc().getCity());
			postalAdressBloc.setCitySearchType(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);

		}

		return subRequestDTO;
	}

	/**
	 * Builds a sub query for recursive search
	 *  Based on Name country code and city
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 */
	public RequestDTO build_NameCountrycodeCityLike(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;

		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& ((BuildConditionsDS.isCityLikeConditionSet(requestDTO))))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());

			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			postalAdressBloc.setCity(requestDTO.getContacts().getPostalAddressBloc().getCity());
			postalAdressBloc.setCitySearchType(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);

			buildQueryDSBean.buildQueryV2(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);

		}

		return subRequestDTO;
	}
	
	public RequestDTO build_NameCountrycodeZipLike(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;

		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& ((BuildConditionsDS.isZipLikeConditionSet(requestDTO))))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());

			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			postalAdressBloc.setZipCode(requestDTO.getContacts().getPostalAddressBloc().getZipCode());
			postalAdressBloc.setZipSearchType(requestDTO.getContacts().getPostalAddressBloc().getZipSearchType());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);

			buildQueryDSBean.buildQueryV2(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);

		}

		return subRequestDTO;
	}
	
	/**
	 * Builds a sub query for recursive search
	 *  Based on Name country code and city
	 * @param requestDTO
	 * @return
	 * @throws BusinessException
	 */
	public RequestDTO build_NameCountrycodeCityStrict(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;

		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& ((BuildConditionsDS.isCityStrictConditionSet(requestDTO))))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());

			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			postalAdressBloc.setCity(requestDTO.getContacts().getPostalAddressBloc().getCity());
			postalAdressBloc.setCitySearchType(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);

			buildQueryDSBean.buildQueryV2(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);

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
	public RequestDTO build_NameCountrycodeCityStrictZipStrict(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityStrictConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipStrictConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
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
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
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
	public RequestDTO build_NameLikeCountryCityLikeZipLike(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(BuildConditionsDS.isNameLikeConditionSet(requestDTO)	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isCityLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipLikeConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
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
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	/**
	 * Builds a sub query for recursive search
	 *  Based on Name country code and ZC1 to ZC4
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_NameZC15(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))	
				&& (BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
				
			CommercialZonesDTO commercialZones = new CommercialZonesDTO();
			commercialZones.setZc1(requestDTO.getCommercialZones().getZc1());
			commercialZones.setZc2(requestDTO.getCommercialZones().getZc2());
			commercialZones.setZc3(requestDTO.getCommercialZones().getZc3());
			commercialZones.setZc4(requestDTO.getCommercialZones().getZc4());
			commercialZones.setZc5(requestDTO.getCommercialZones().getZc5());
			subRequestDTO.setCommercialZones(commercialZones);
			
			buildQueryDSBean.buildQueryV2(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	public RequestDTO build_NameZipZC(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))
				&& (BuildConditionsDS.isZipLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
				
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setZipCode(requestDTO.getContacts().getPostalAddressBloc().getZipCode());
			postalAdressBloc.setZipSearchType(requestDTO.getContacts().getPostalAddressBloc().getZipSearchType());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			CommercialZonesDTO commercialZones = new CommercialZonesDTO();
			commercialZones.setZc1(requestDTO.getCommercialZones().getZc1());
			commercialZones.setZc2(requestDTO.getCommercialZones().getZc2());
			commercialZones.setZc3(requestDTO.getCommercialZones().getZc3());
			commercialZones.setZc4(requestDTO.getCommercialZones().getZc4());
			commercialZones.setZc5(requestDTO.getCommercialZones().getZc5());
			subRequestDTO.setCommercialZones(commercialZones);
			
			buildQueryDSBean.buildQueryV2(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	public RequestDTO build_NameCityZC(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))
				&& (BuildConditionsDS.isCityLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
				
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCity(requestDTO.getContacts().getPostalAddressBloc().getCity());
			postalAdressBloc.setCitySearchType(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			CommercialZonesDTO commercialZones = new CommercialZonesDTO();
			commercialZones.setZc1(requestDTO.getCommercialZones().getZc1());
			commercialZones.setZc2(requestDTO.getCommercialZones().getZc2());
			commercialZones.setZc3(requestDTO.getCommercialZones().getZc3());
			commercialZones.setZc4(requestDTO.getCommercialZones().getZc4());
			commercialZones.setZc5(requestDTO.getCommercialZones().getZc5());
			subRequestDTO.setCommercialZones(commercialZones);
			
			buildQueryDSBean.buildQueryV2(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	public RequestDTO build_NameCityZipZC(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))
				&& (BuildConditionsDS.isCityLikeConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipLikeConditionSet(requestDTO))		
				&& (BuildConditionsDS.isZC1ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC2ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC3ConditionSet(requestDTO))
				&& (BuildConditionsDS.isZC4ConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
				
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCity(requestDTO.getContacts().getPostalAddressBloc().getCity());
			postalAdressBloc.setCitySearchType(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType());
			postalAdressBloc.setZipCode(requestDTO.getContacts().getPostalAddressBloc().getZipCode());
			postalAdressBloc.setZipSearchType(requestDTO.getContacts().getPostalAddressBloc().getZipSearchType());
			
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			CommercialZonesDTO commercialZones = new CommercialZonesDTO();
			commercialZones.setZc1(requestDTO.getCommercialZones().getZc1());
			commercialZones.setZc2(requestDTO.getCommercialZones().getZc2());
			commercialZones.setZc3(requestDTO.getCommercialZones().getZc3());
			commercialZones.setZc4(requestDTO.getCommercialZones().getZc4());
			commercialZones.setZc5(requestDTO.getCommercialZones().getZc5());
			subRequestDTO.setCommercialZones(commercialZones);
			
			buildQueryDSBean.buildQueryV2(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
		
	/**
	 * Builds a sub query for recursive search
	 *  Based on Name, country code and ident
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_NameCountrycodeAndIdent(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			IdentificationDTO identification = new IdentificationDTO();
			identification.setIdentificationType(requestDTO.getIdentification().getIdentificationType());
			identification.setIdentificationValue(requestDTO.getIdentification().getIdentificationValue());
			subRequestDTO.setIdentification(identification);
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	/**
	 * Builds a sub query for recursive search
	 *  Based on Name and country code
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_NameAndCountrycode(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	/**
	 * Builds a sub query for recursive search
	 *  Based on Name and country code
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_NameStrictCountrycode(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if( BuildConditionsDS.isNameStrictConditionSet(requestDTO) 	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	/**
	 * Builds a sub query for recursive search
	 *  Based on Name and country code
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_NameLikeCountrycode(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if (BuildConditionsDS.isNameLikeConditionSet(requestDTO)	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			buildQueryDSBean.buildQueryV2(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	/**
	 * Builds a sub query for recursive search
	 *  Based on Name and country code
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_NameZipCountrycode(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if(((BuildConditionsDS.isNameStrictConditionSet(requestDTO)) || BuildConditionsDS.isNameLikeConditionSet(requestDTO)) 	
				&& (BuildConditionsDS.isCountryConditionSet(requestDTO))
				&& (BuildConditionsDS.isZipLikeConditionSet(requestDTO)|| BuildConditionsDS.isZipStrictConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
			ContactsDTO contacts = new ContactsDTO();
			PostalAddressBlocDTO postalAdressBloc = new PostalAddressBlocDTO();
			postalAdressBloc.setCountryCode(requestDTO.getContacts().getPostalAddressBloc().getCountryCode());
			postalAdressBloc.setZipCode(requestDTO.getContacts().getPostalAddressBloc().getZipCode());
			postalAdressBloc.setZipSearchType(requestDTO.getContacts().getPostalAddressBloc().getZipSearchType());
			contacts.setPostalAddressBloc(postalAdressBloc);
			subRequestDTO.setContacts(contacts);
			
			buildQueryDSBean.buildQueryV2(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}

	/**
	 * Builds a sub query for recursive search
	 *  Based on Name
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_Name(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if (BuildConditionsDS.isNameStrictConditionSet(requestDTO)) //|| (BuildConditionsDS.isNameLikeConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	/**
	 * Builds a sub query for recursive search
	 *  Based on Phone
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_Phone(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isPhoneConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			
			ContactsDTO contacts = new ContactsDTO();
			PhoneBlocDTO phoneBloc = new PhoneBlocDTO();
			phoneBloc.setPhoneNumber(requestDTO.getContacts().getPhoneBloc().getPhoneNumber());
			contacts.setPhoneBloc(phoneBloc);
			subRequestDTO.setContacts(contacts);
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
			
		}
		
		return subRequestDTO;
	}

	/**
	 * Builds a sub query for recursive search
	 *  Based on Email
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_Email(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isEmailConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			
			ContactsDTO contacts = new ContactsDTO();
			EmailBlocDTO emailBloc = new EmailBlocDTO();
			emailBloc.setEmail(requestDTO.getContacts().getEmailBloc().getEmail());
			contacts.setEmailBloc(emailBloc);
			subRequestDTO.setContacts(contacts);
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}

	/**
	 * Builds a sub query for recursive search
	 *  Based on Phone and ident
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_PhoneAndIdent(RequestDTO requestDTO) throws BusinessException {
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
			contacts.setPhoneBloc(phoneBloc);
			subRequestDTO.setContacts(contacts);
			IdentificationDTO identification = new IdentificationDTO();
			identification.setIdentificationType(requestDTO.getIdentification().getIdentificationType());
			identification.setIdentificationValue(requestDTO.getIdentification().getIdentificationValue());
			subRequestDTO.setIdentification(identification);
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}

	/**
	 * Builds a sub query for recursive search
	 *  Based on Email and ident
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_EmailAndIdent(RequestDTO requestDTO) throws BusinessException {
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
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	/**
	 * Builds a sub query for recursive search
	 *  Based on Ident
	 * @param requestDTO
	 * @return
	 * @throws BusinessException 
	 */
	public RequestDTO build_Ident(RequestDTO requestDTO) throws BusinessException {
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
			
			buildQueryDSBean.buildQuery(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	public RequestDTO build_NameLikeZipLike(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isNameLikeConditionSet(requestDTO)) && (BuildConditionsDS.isZipLikeConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
			subRequestDTO.setContacts(requestDTO.getContacts());
			
			buildQueryDSBean.buildQueryV2(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	public RequestDTO build_NameLikeCityLike(RequestDTO requestDTO) throws BusinessException {
		RequestDTO subRequestDTO = null;
		
		if((BuildConditionsDS.isNameLikeConditionSet(requestDTO)) && (BuildConditionsDS.isCityLikeConditionSet(requestDTO)))
		{
			subRequestDTO = new RequestDTO();
			subRequestDTO.setQueryIndex(1);
			subRequestDTO.setIdentity(requestDTO.getIdentity());
			
			subRequestDTO.setContacts(requestDTO.getContacts());
			
			buildQueryDSBean.buildQueryV2(subRequestDTO);
			buildQueryDSBean.buildCountQuery(subRequestDTO);
		}
		
		return subRequestDTO;
	}
	
	

	/*===============================================*/
	/*                ACCESSORS                      */
	/*===============================================*/
	public BuildQueryDS getBuildQueryDSBean() {
		return buildQueryDSBean;
	}

	public void setBuildQueryDSBean(BuildQueryDS buildQueryDSBean) {
		this.buildQueryDSBean = buildQueryDSBean;
	}

	
}
