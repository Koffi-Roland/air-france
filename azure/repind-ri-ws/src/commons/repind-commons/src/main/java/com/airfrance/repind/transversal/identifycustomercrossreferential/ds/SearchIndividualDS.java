package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.airfrance.ref.exception.InvalidPhoneNumberException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.*;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.reference.internal.ErrorDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.BusinessExceptionDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ResponseDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders.RequestSearchIndividualDTOBuilder;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders.ResponseSearchIndividualDTOBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;



/**
 * Remarks:
 *  
 *  - This class is a copy of SearchIndividualByMulticriteriaImpl class (project SearchIndividualByMultiCriteria)
 *    No other code was added.
 *  
 *  - SearchIndividualByMultiCriteria for which this class is a consumer does not have a DTO layer.
 *    For this reason, this class manipulates instances from com.afklm.soa.stubs.w001271.v2 package
 *    instead of manipulating independent DTO instances.
 *  
 * @author t950700
 *
 */
@Service
public class SearchIndividualDS extends AbstractDS {

	/*==========================================*/
	/*                                          */
	/*             INJECTED BEANS               */
	/*                                          */
	/*==========================================*/
	
	/*
	 * Builds SearchndividualByMultiCriteria request from IdentifyCustomerCrossReferential requestDTO
	 */
	@Autowired
	private RequestSearchIndividualDTOBuilder requestDTOBuilder = null;
	
	
	/*
	 * Builds IdentifyCustomerCrossReferential response from SearchIndividualByMultiCriteria response
	 */
	@Autowired
	private ResponseSearchIndividualDTOBuilder responseBuilder = null;
	
	
	@Autowired
	private IndividuDS individuDS = null;
	
	@Autowired
	protected ErrorDS errorDS;
	
	@Autowired
	private TelecomDS telecomDS;
	
	/* Dozer reference for the data mapping. This ref. is injected by spring. */
	private Mapper mapper;
	
	/*==========================================*/
	/*                                          */
	/*                CONSTANTS                 */
	/*                                          */
	/*==========================================*/
	
	private static final String UNABLE_TO_GET_LABEL_ERROR = "UNABLE_TO_GET_LABEL";
	private static final int ERROR_DETAIL_MAX_LENGTH = 255;
	private static final int ERROR_LABEL_MAX_LENGTH = 70;
	private final String ERROR_PREFIX = "ERR_";
	
	/*==========================================*/
	/*                                          */
	/*               LOGGER                     */
	/*                                          */
	/*==========================================*/
	Log _log = LogFactory.getLog(SearchIndividualDS.class);
	
	
	
	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/

	public ResponseDTO searchIndividual(RequestDTO requestDTO) throws BusinessExceptionDTO, SystemException 
	{
		ResponseDTO responseDTO = searchIndividualByMultiCriteria(requestDTO);
		return responseDTO;
	}

	/*==========================================*/
	/*                                          */
	/*             PRIVATE METHODS              */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Perform search individuals - through SearchIndividualByMultiCriteriaDS
	 *
	 * @param request
	 * @return responseDTO
	 * @throws BusinessErrorBlocBusinessException
	 */
	private ResponseDTO searchIndividualByMultiCriteria(RequestDTO requestDTO) throws BusinessExceptionDTO
	{
		

		ResponseDTO responseDTO = null;
		
		/*
		 * Building SearchIndividual request
		 */
		SearchIndividualByMulticriteriaRequestDTO request = requestDTOBuilder.buildSearchIndividualByMultiCriteriaRequest(requestDTO);
		
		
		/*
		 * Calling SearchIndividualByMutiCriteria domain service
		 */
		SearchIndividualByMulticriteriaResponseDTO result = null;
		
		try 
		{
			// Recuperation des infos dans la request
			RequestorDTO requestor = request.getRequestor();
			IdentityDTO identity = request.getIdentity();
			ContactDTO contact = request.getContact();
			String populationTarget = request.getPopulationTargeted();
			String processType = request.getProcessType();
			
			if (processType==null || "".equals(processType)){
				processType = "A";
				request.setProcessType(processType);
			}
			
			if (populationTarget==null || "".equals(populationTarget)){
				populationTarget = "A";
				request.setPopulationTargeted(populationTarget);
			}
			
			// application code is missing
			if(requestor.getApplicationCode()==null || (requestor.getApplicationCode()!=null && ("").equalsIgnoreCase(requestor.getApplicationCode()))){
				throw new MissingParameterException("Application Code is missing");
			}
			
			// identity bloc is missing
			if(identity==null){
				identity = new IdentityDTO();
				//throw new MissingParameterException("Identity block is missing");
			}
			
			//104 first name is missing
			if((identity.getLastName()!=null && !("").equalsIgnoreCase(identity.getLastName())
				&& (identity.getFirstName()==null || (identity.getFirstName()!=null && ("").equalsIgnoreCase(identity.getFirstName()))))){
				throw new MissingParameterException("Firstname is missing");
			}
			
			//103 last name mandatory
			if((identity.getFirstName()!=null && !("").equalsIgnoreCase(identity.getFirstName())
					&& (identity.getLastName()==null || (identity.getLastName()!=null && ("").equalsIgnoreCase(identity.getLastName()))))){
						throw new MissingParameterException("Lastname is missing");
			}
			else{ //si nom / prenom existe on controle les longueurs
				if(!("").equalsIgnoreCase(identity.getFirstName()) && !("").equalsIgnoreCase(identity.getLastName())){
					if(identity.getLastName()!=null && identity.getLastName().length()<2 ){
						throw new MissingParameterException("LastName is too short (2 chars minimum)");
					}
				}
			}
			
			//searchTypes missing
			if(
					(identity.getFirstNameSearchType()==null) 
					|| 
					(("").equalsIgnoreCase(identity.getFirstNameSearchType()))
					||
					(!("L").equalsIgnoreCase(identity.getFirstNameSearchType()))
			)
			{
				identity.setFirstNameSearchType("S");
			}
			
			if(
					(identity.getLastNameSearchType()==null) 
					|| 
					(("").equalsIgnoreCase(identity.getLastNameSearchType()))
					||
					(!("L").equalsIgnoreCase(identity.getLastNameSearchType()))
			)
			{
				identity.setLastNameSearchType("S");
			}
			
			//Email adress/Phone is missing (si pas nom/prenom l'email ou le telephone doit etre renseigne)
			if( (identity.getFirstName()==null || (identity.getFirstName()!=null && ("").equalsIgnoreCase(identity.getFirstName())))
			&&(identity.getLastName()==null || (identity.getLastName()!=null && ("").equalsIgnoreCase(identity.getLastName())))
			&& ((contact.getEmail()==null || (contact.getEmail()!=null && ("").equalsIgnoreCase(contact.getEmail()))) && 
			(contact.getPhoneNumber()==null || (contact.getPhoneNumber()!=null && ("").equalsIgnoreCase(contact.getPhoneNumber()))))){
					throw new MissingParameterException("LastName/FirstName or Email is missing");
			}
			
			if (contact!=null){
				if (contact.getCountryCode() != null && contact.getPhoneNumber() != null){
					TelecomsDTO phoneNumberDTO = new TelecomsDTO();
					phoneNumberDTO.setCountryCode(contact.getCountryCode().toUpperCase());
					phoneNumberDTO.setSnumero(contact.getPhoneNumber());
					TelecomsDTO normalizedPN = telecomDS.normalizePhoneNumber(phoneNumberDTO);
					
					request.getContact().setCountryCode(normalizedPN.getSnorm_inter_country_code());
					request.getContact().setPhoneNumber(normalizedPN.getSnorm_inter_phone_number());
				}
				
				if (contact.getPostalAddressBloc() != null){
					PostalAddressBlocDTO pab = contact.getPostalAddressBloc();
					if (pab.getCity()!=null){
						pab.setCity(pab.getCity().toUpperCase());
					}
					if (pab.getCitySearchType()==null){
						pab.setCitySearchType("L");
					}
					if (pab.getZipCodeSearchType()==null){
						pab.setZipCodeSearchType("L");
					}
					if (pab.getCountryCode()!=null){
						pab.setCountryCode(pab.getCountryCode().toUpperCase());
					}
					if (pab.getNumberAndStreet()!=null){
						pab.setNumberAndStreet(pab.getNumberAndStreet().toUpperCase());
					}
				}
			}

			//TRAITEMENT: retourne une liste de resultats:
			_log.info("SearchIndividualByMulticriteriaV2::Input :"+request);
			result = getIndividuDS().searchIndividual(request);
			_log.info(result.getIndividuals().size()+" individual(s) found !");
			
			
			//246 TOO MANY RESULTS FOUND
			if(result.getIndividuals().size()>255){
				_log.info("TOO MANY RESULTS FOUND");
				throw new JrafApplicativeException("Too many results matching");
			}
			
			//REPIND-651: Business Rules in order to keep only one result if responseType=U (Unique result)
			if (result.getIndividuals().size() > 1 && requestDTO.getContext().getResponseType().equalsIgnoreCase("U")) {

				//REPIND-2299: Return UNIQUE phone number matches only in IdentifyCustomerCrossReferential
				if (requestDTO.getContext().getTypeOfSearch().equalsIgnoreCase("I")){
					throwBusinessException(getMultipleResultsCode(), getMultipleResultsMessage());
				}
				Set<IndividualMulticriteriaDTO> individus = result.getIndividuals();
				_log.info("SearchIndividualByMulticriteriaV2:: Unique response is expecting but several results - " + individus.size());

				try {
					filteringIndividuByFBTierMANoTraveler(individus);
					result.setIndividuals(individus);
				} catch (ParseException e) {
					_log.error("SearchIndividualByMulticriteriaV2: ERROR - Unable to filter out individus");
				}
			}
		} 
		catch (MissingParameterException e){
			//throwBusinessException(BusinessErrorCodeEnum.ERR_133, e.getMessage());
			throwBusinessException(getNoIndividualsMissingParameterReturnCode(), getNoIndividualsMissingParameterReturnMessage());
		}
		catch (JrafApplicativeException e) {
			//throwBusinessException(BusinessErrorCodeEnum.ERR_246, e.getMessage());
			throwBusinessException(getOtherExceptionReturnCode(), getOtherExceptionReturnMessage());
		}
		catch (com.airfrance.ref.exception.NotFoundException e)
		{
			//throwBusinessException(BusinessErrorCodeEnum.ERR_001, e.getMessage());
			throwBusinessException(getNoIndividualsReturnCode(), getNoIndividualsReturnMessage());
		}
		catch(InvalidPhoneNumberException e) {
			// throwBusinessException("240", "INVALID PHONE NUMBER DATA");	// 240 Not compatible with IdentifyCustomerCrossRef ErrorCode - ReturnCode
			throwBusinessException("932", "INVALID PHONE NUMBER DATA");
		}
		catch (JrafDomainException e) 
		{
			// REPIND-1012 : Change OTHER exception for NOT FOUND exception
			if (e != null && e.getMessage() != null && "No individus found".equals(e.getMessage())) {
				throwBusinessException(getNoIndividualsReturnCode(), getNoIndividualsReturnMessage());					
			}
			//throwBusinessException(BusinessErrorCodeEnum.ERR_905, e.getMessage());
			throwBusinessException(getOtherExceptionReturnCode(), getOtherExceptionReturnMessage());
		}

		responseDTO = responseBuilder.build(requestDTO, result);
 		
 		if(responseDTO.getCustomers().isEmpty()) {
 			throwBusinessException(getNoIndividualsReturnCode(), getNoIndividualsReturnMessage());
 		}

		return responseDTO;
	}
	
	/**
	 * Lever une {@link BusinessErrorBlocBusinessException} sans {@link InternalBusinessError}.
	 * @param numError : numero de l'erreur.
	 * @param label : label de l'erreur.
	 * @param detail : detail de l'erreur.
	 * @throws BusinessErrorBlocBusinessException
	 */
//	private void throwBusinessException(BusinessError numError, String detail) throws BusinessException {
//		
//		BusinessError businessError = buildBusinessException(numError, null, detail);
//		
//		throwBusinessException(businessError, null);
//	}
	

//	private void throwBusinessException(BusinessError businessError, InternalBusinessError internalBusinessError) throws BusinessErrorBlocBusinessException {
//		
//		com.afklm.soa.stubs.w001271.v2.response.BusinessErrorBloc businessErrorBloc = new com.afklm.soa.stubs.w001271.v2.response.BusinessErrorBloc();
//		businessErrorBloc.setBusinessError(businessError);
//		businessErrorBloc.setInternalBusinessError(internalBusinessError);
//		
//		throw new BusinessErrorBlocBusinessException("", businessErrorBloc);
//	}
	
//	private BusinessError buildBusinessException(BusinessErrorCodeEnum errorCode, String otherErrorCode, String detail) {
//		
//		String label;
//		
//		try {
//			String errorCodeStr = (errorCode==BusinessError.ERR_001) ? otherErrorCode : errorCode.toString();
//			String errorNum = errorCodeStr.replace(ERROR_PREFIX,"");
//			label = errorDS.getErrorDetails(errorNum).getLabelUK();
//		} catch (Exception e) {
//			label = UNABLE_TO_GET_LABEL_ERROR;
//		}
//		
//		BusinessError businessError = new BusinessError();
//		businessError.setErrorCode(errorCode);
//		businessError.setOtherErrorCode("");
//		businessError.setErrorLabel(SicStringUtils.truncate(label,ERROR_LABEL_MAX_LENGTH));
//		businessError.setErrorDetail(SicStringUtils.truncate(detail,ERROR_DETAIL_MAX_LENGTH));
//
//		return businessError;
//	}
	

	/*==========================================*/
	/*                                          */
	/*                ACCESSORS                 */
	/*                                          */
	/*==========================================*/
	
	public IndividuDS getIndividuDS() {
		return individuDS;
	}

	public void setIndividuDS(IndividuDS individuDS) {
		this.individuDS = individuDS;
	}

	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * 
	 * @param Set<IndividualMulticriteriaDTO> individus (Full list of individus)
	 * @throws ParseException
	 * List of individus filtered by FB, MA, Traveler then NoTraveler
	 */
	private void filteringIndividuByFBTierMANoTraveler (Set<IndividualMulticriteriaDTO> individus) throws ParseException {
		//First filter, by FB Contract (Contract, then Tier, then Creation date)
		filteringOnFBContract(individus);
		
		//Second filter, by MA Contract (Contract, then Creation date)
		if (individus.size() > 1) {
			filteringOnMAContract(individus);
		}
		
		//Third filter, by noTraveler (Type other than T, then Creation date)
		if (individus.size() > 1) {
			filteringOnNoTraveler(individus);
		}
		
		//Last filter, by Traveler (Type T, then Creation date)
		if (individus.size() > 1) {
			filteringOnTraveler(individus);
		}
	}
	
	/**
	 * 
	 * @param Set<IndividualMulticriteriaDTO> individus (Full list of individus)
	 * @throws ParseException
	 * This method will get only individus who have a FB Contract
	 * Then filter by the most important Tier
	 * and then by the date of validity of the contract
	 * 
	 */
	private void filteringOnFBContract (Set<IndividualMulticriteriaDTO> individus) throws ParseException {
		_log.info("SearchIndividualByMulticriteriaV2:: " + individus.size() + " before FB Filter");

		Map<String, HashMap<IndividualMulticriteriaDTO, Date>> individuToKeepByTier = new HashMap<String, HashMap<IndividualMulticriteriaDTO, Date>>();
		Iterator<IndividualMulticriteriaDTO> individuMulticriteriaIterator = individus.iterator();
		while(individuMulticriteriaIterator.hasNext()) {
			IndividualMulticriteriaDTO individu = individuMulticriteriaIterator.next();
			if (individu.getIndividu().getRolecontratsdto() != null && individu.getIndividu().getRolecontratsdto().size() > 0) {
				Map<String, Object> isFBContract = hasFBContract(individu);
				if (isFBContract != null) {
					if (!individuToKeepByTier.containsKey(isFBContract.get("TIER"))) {
						individuToKeepByTier.put(isFBContract.get("TIER").toString(), new HashMap<IndividualMulticriteriaDTO, Date>());
					}
					individuToKeepByTier.get(isFBContract.get("TIER")).put(individu, (Date)isFBContract.get("VALIDITY_DATE"));
				}
			}
		 }
		
		//FOR DEBUG
		/*int nbFBFound = 0;
		for (String tierFB : individuToKeepByTier.keySet()) {
			nbFBFound += individuToKeepByTier.get(tierFB).size();
		}
		_log.info("SearchIndividualByMulticriteriaV2:: " + nbFBFound + " after FB Contract Filter");*/
		//_log.info("SearchIndividualByMulticriteriaV2:: " + individuToKeepByTier.size() + " after FB Contract Filter");
		
		//Else, there are FB Contract, we keep the most important
		if (individuToKeepByTier.size() > 0) {
			Map<IndividualMulticriteriaDTO, Date> fbToKeep = new HashMap<IndividualMulticriteriaDTO, Date>();
			
			if (individuToKeepByTier.get("P") != null) fbToKeep = individuToKeepByTier.get("P");
			if (individuToKeepByTier.get("J") != null) fbToKeep = individuToKeepByTier.get("J");
			if (individuToKeepByTier.get("A") != null) fbToKeep = individuToKeepByTier.get("A");
			if (individuToKeepByTier.get("B") != null) fbToKeep = individuToKeepByTier.get("B");
			if (individuToKeepByTier.get("R") != null) fbToKeep = individuToKeepByTier.get("R");
			if (individuToKeepByTier.get("M") != null) fbToKeep = individuToKeepByTier.get("M");
			if (individuToKeepByTier.get("C") != null) fbToKeep = individuToKeepByTier.get("C");
			
			_log.info("SearchIndividualByMulticriteriaV2:: " + fbToKeep.size() + " after FB Tier Filter");

			//If there are still more than one FB Contract then we filtering by creation date
			if (fbToKeep.size() > 1) {
				filteringOnContractDate(fbToKeep);
				
				_log.info("SearchIndividualByMulticriteriaV2:: " + fbToKeep.size() + " after FB Date Filter");
			}
			
			individus.clear();
			individus.addAll(fbToKeep.keySet());
		}
	}
	
	/**
	 * 
	 * @param Set<IndividualMulticriteriaDTO> individus (Full list of individus)
	 * @throws ParseException
	 * This method will get only individus who have a MyAccount Contract
	 * Then filter by the creation date of the contract
	 * 
	 */
	private void filteringOnMAContract (Set<IndividualMulticriteriaDTO> individus) throws ParseException {
		Map<IndividualMulticriteriaDTO, Date> maToKeep = new HashMap<IndividualMulticriteriaDTO, Date>();
				
		_log.info("SearchIndividualByMulticriteriaV2:: " + individus.size() + " before MA Contract Filter");
		
		Iterator<IndividualMulticriteriaDTO> individuMulticriteriaIterator = individus.iterator();
		while(individuMulticriteriaIterator.hasNext()) {
			IndividualMulticriteriaDTO individu = individuMulticriteriaIterator.next();
			if (individu.getIndividu().getRolecontratsdto() != null && individu.getIndividu().getRolecontratsdto().size() > 0) {
				Map<IndividualMulticriteriaDTO, Date> isMAContract = hasMAContract(individu);
				if (isMAContract != null) {
					maToKeep.putAll(isMAContract);
				}
			}
		}
		
		_log.info("SearchIndividualByMulticriteriaV2:: " + maToKeep.size() + " after MA Contract Filter");
		
		if (maToKeep.size() > 0) {
			//If there are more than one MA contract then we filtering by creation date
			if (maToKeep.size() > 1) {
				filteringOnContractDate(maToKeep);
				
				_log.info("SearchIndividualByMulticriteriaV2:: " + maToKeep.size() + " after MA Date Filter");
			}
			
			individus.clear();
			individus.addAll(maToKeep.keySet());			
		}
	}
	
	/**
	 * 
	 * @param Set<IndividualMulticriteriaDTO> individus (Full list of individus)
	 * @throws ParseException
	 * This method will get only individus who have Status not equals to 'T'
	 * Then filter by the creation date of the individu
	 * 
	 */
	private void filteringOnNoTraveler (Set<IndividualMulticriteriaDTO> individus) throws ParseException {
		Map<IndividualMulticriteriaDTO, Date> noTravelerToKeep = new HashMap<IndividualMulticriteriaDTO, Date>();
		
		_log.info("SearchIndividualByMulticriteriaV2:: " + individus.size() + " before NoTraveler Filter");
		
		for (IndividualMulticriteriaDTO individu : individus) {
			if (!individu.getIndividu().getType().equalsIgnoreCase("T")) {
				noTravelerToKeep.put(individu, individu.getIndividu().getDateCreation());
			}
		}
		
		_log.info("SearchIndividualByMulticriteriaV2:: " + noTravelerToKeep.size() + " after NoTraveler Filter");
		
		if (noTravelerToKeep.size() > 0) {
			//If there are more than one No Traveler then we filtering by creation date
			if (noTravelerToKeep.size() > 1) {
				filteringOnContractDate(noTravelerToKeep);
				
				_log.info("SearchIndividualByMulticriteriaV2:: " + noTravelerToKeep.size() + " after NoTraveler Date Filter");
				
				//If there is one individu, we got it !
				if (noTravelerToKeep.size() == 1) {
					individus.clear();
					individus.addAll(noTravelerToKeep.keySet());	
				}
				//If not, we don't touch the initial list (individus), in order to filter on Traveler status
			
			//If there is one individu, we got it !
			} else if (noTravelerToKeep.size() == 1) {
				individus.clear();
				individus.addAll(noTravelerToKeep.keySet());
			}
		}
	}
	
	/**
	 * 
	 * @param Set<IndividualMulticriteriaDTO> individus (Full list of individus)
	 * @throws ParseException
	 * This method will get only individus who have Status equals to 'T'
	 * Then filter by the creation date of the individu
	 * 
	 */
	private void filteringOnTraveler (Set<IndividualMulticriteriaDTO> individus) throws ParseException {
		Map<IndividualMulticriteriaDTO, Date> travelerToKeep = new HashMap<IndividualMulticriteriaDTO, Date>();
		
		_log.info("SearchIndividualByMulticriteriaV2:: " + individus.size() + " before Traveler Filter");
		
		for (IndividualMulticriteriaDTO individu : individus) {
			if (individu.getIndividu().getType().equalsIgnoreCase("T")) {
				travelerToKeep.put(individu, individu.getIndividu().getDateCreation());
			}
		}
		
		_log.info("SearchIndividualByMulticriteriaV2:: " + travelerToKeep.size() + " after Traveler Filter");
		
		if (travelerToKeep.size() > 0) {
			//If there are more than one Traveler then we filtering by creation date
			if (travelerToKeep.size() > 1) {
				filteringOnContractDate(travelerToKeep);
				
				_log.info("SearchIndividualByMulticriteriaV2:: " + travelerToKeep.size() + " after Traveler Date Filter");
			}
			
			individus.clear();
			individus.addAll(travelerToKeep.keySet());
		}
	}
	
	/**
	 * 
	 * @param IndividualMulticriteriaDTO individu
	 * @return Map<String, Object> fbInfos
	 * @throws ParseException
	 * This method will check if the individu has a FB Contract
	 * and then return a Map that contains the Tier level, Individu and the date creation of the contract
	 * [A:2016-01-01]
	 * 
	 */
	private Map<String, Object> hasFBContract(IndividualMulticriteriaDTO individu) {
		Iterator<RoleContratsDTO> contractIterator = individu.getIndividu().getRolecontratsdto().iterator();
		
		while (contractIterator.hasNext()) {
			RoleContratsDTO contract = contractIterator.next();
			if (contract.getTypeContrat().equalsIgnoreCase("FP") && (contract.getEtat().equalsIgnoreCase("C") || contract.getEtat().equalsIgnoreCase("P"))) {
				Map<String, Object> fbInfos = new HashMap<String, Object>();
				fbInfos.put("TIER", contract.getTier() == null ? "P" : contract.getTier());
				fbInfos.put("VALIDITY_DATE", contract.getDateDebutValidite());
				return fbInfos;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param IndividualMulticriteriaDTO individu
	 * @return Map<IndividualMulticriteriaDTO, Date> maInfos
	 * @throws ParseException
	 * This method will check if the individu has a MA Contract
	 * and then return a Map that contains the Individu and the date creation of the contract
	 * [Individu:2016-01-01]
	 * 
	 */
	private Map<IndividualMulticriteriaDTO, Date> hasMAContract(IndividualMulticriteriaDTO individu) {
		Iterator<RoleContratsDTO> contractIterator = individu.getIndividu().getRolecontratsdto().iterator();
		
		while (contractIterator.hasNext()) {
			RoleContratsDTO contract = contractIterator.next();
			if (contract.getTypeContrat().equalsIgnoreCase("MA") && (contract.getEtat().equalsIgnoreCase("C") || contract.getEtat().equalsIgnoreCase("P"))) {
				Map<IndividualMulticriteriaDTO, Date> maInfos = new HashMap<IndividualMulticriteriaDTO, Date>();
				maInfos.put(individu, contract.getDateCreation());
				return maInfos;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param Map<IndividualMulticriteriaDTO, Date> contracts
	 * @throws ParseException
	 * This method will each contracts to each others
	 * and then return the most recent of them
	 * 
	 */
	private void filteringOnContractDate(Map<IndividualMulticriteriaDTO, Date> contracts) throws ParseException {
		Map<IndividualMulticriteriaDTO, Date> contractToKeep = new HashMap<IndividualMulticriteriaDTO, Date>();
		
		Iterator<Map.Entry<IndividualMulticriteriaDTO, Date>> contractsIterator = contracts.entrySet().iterator();
		Date mostRecentDate = null;
		while (contractsIterator.hasNext()) {
			Map.Entry<IndividualMulticriteriaDTO, Date> contract = contractsIterator.next();
			if (contract.getValue() != null) {
				if (mostRecentDate != null) {
					if (contract.getValue().after(mostRecentDate)) {
						contractToKeep.clear();
						contractToKeep.put(contract.getKey(), contract.getValue());
						mostRecentDate = contract.getValue();
					} else if (contract.getValue().equals(mostRecentDate)) {
						contractToKeep.put(contract.getKey(), contract.getValue());
					}
				} else {
					mostRecentDate = contract.getValue();
					contractToKeep.put(contract.getKey(), contract.getValue());
				}
			}
		}
		
		contracts.clear();
		contracts.putAll(contractToKeep);
	}
	
}
