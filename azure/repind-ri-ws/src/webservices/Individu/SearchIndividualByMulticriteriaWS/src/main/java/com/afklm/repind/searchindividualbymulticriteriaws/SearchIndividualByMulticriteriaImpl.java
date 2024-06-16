package com.afklm.repind.searchindividualbymulticriteriaws;

import com.afklm.soa.stubs.w001271.v2.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001271.v2.SearchIndividualByMulticriteriaServiceV2;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaRequest;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaResponse;
import com.afklm.soa.stubs.w001271.v2.request.Contact;
import com.afklm.soa.stubs.w001271.v2.request.Identification;
import com.afklm.soa.stubs.w001271.v2.request.Identity;
import com.afklm.soa.stubs.w001271.v2.request.PostalAddressContent;
import com.afklm.soa.stubs.w001271.v2.response.BusinessError;
import com.afklm.soa.stubs.w001271.v2.response.BusinessErrorBloc;
import com.afklm.soa.stubs.w001271.v2.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w001271.v2.siccommonenum.BusinessErrorTypeEnum;
import com.afklm.soa.stubs.w001271.v2.siccommontype.InternalBusinessError;
import com.afklm.soa.stubs.w001271.v2.siccommontype.Requestor;
import com.airfrance.ref.exception.AdhesionException;
import com.airfrance.ref.exception.MarketingErrorException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.external.InvalidExternalIdentifierTypeException;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaRequestDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaResponseDTO;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.reference.internal.ErrorDS;
import com.airfrance.repind.service.reference.internal.RefTypExtIDDS;
import com.airfrance.repind.util.ReadSoaHeaderHelper;
import com.airfrance.repind.util.SicStringUtils;
import com.sun.xml.ws.api.message.Header;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.soap.SOAPException;
import javax.xml.ws.WebServiceContext;

@Service("passenger_SearchIndividualByMulticriteriaService-v2Bean")
@WebService(endpointInterface="com.afklm.soa.stubs.w001271.v2.SearchIndividualByMulticriteriaServiceV2", targetNamespace = "http://www.af-klm.com/services/passenger/SearchIndividualByMulticriteriaService-v2/wsdl", serviceName = "SearchIndividualByMulticriteriaServiceService-v2", portName = "SearchIndividualByMulticriteriaService-v2-soap11http")
@Slf4j
public class SearchIndividualByMulticriteriaImpl implements SearchIndividualByMulticriteriaServiceV2 {

	/*
	 * individuDS reference which support the operation needed by the service
	 * implementation. This ref. is injected by spring.
	 */
	@Autowired
	private IndividuDS individuDS;
	
	@Autowired
	private TelecomDS telecomDS;
	
    @Autowired
    private RefTypExtIDDS refTypExtIDDS;
	
	@Autowired
	private ErrorDS errorDS;

	@Resource
	private WebServiceContext context;
	
	private static final String UNABLE_TO_GET_LABEL_ERROR = "UNABLE_TO_GET_LABEL";
	private static final int ERROR_DETAIL_MAX_LENGTH = 255;
	private static final int ERROR_LABEL_MAX_LENGTH = 70;
	private final String ERROR_PREFIX = "ERR_";

	
	public SearchIndividualByMulticriteriaResponse searchIndividual(SearchIndividualByMulticriteriaRequest request) throws BusinessErrorBlocBusinessException{
		
		// REPIND-1441 : Trace who are consuming us and for what 
		TraceInput(request);
		
		SearchIndividualByMulticriteriaResponse response = null;
		SearchIndividualByMulticriteriaRequestDTO requeteDTO = null;
		SearchIndividualByMulticriteriaResponseDTO result = null;
		try 
		{
			
			// REPIND-1288 : Normalize Email
			if (request != null && request.getContact() != null && request.getContact().getEmail() != null) {
				request.getContact().setEmail(SicStringUtils.normalizeEmail(request.getContact().getEmail()));
			}
			
			// Recuperation des infos dans la request
			Requestor requestor = request.getRequestor();
			Identity identity = request.getIdentity();
			Contact contact = request.getContact();
			
			// REPIND-1264 : Add Search for Social by Type and Identifier
			Identification identification = request.getIdentification();
			
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
				identity = new Identity();
				//throw new MissingParameterException("Identity block is missing");
			} 
			
			//Email adress/Phone/identification is missing (si pas nom/prenom l'email ou le telephone ou l'identification doit etre renseigne)
			if (identity == null || (identity.getFirstName()==null || (identity.getFirstName()!=null && ("").equalsIgnoreCase(identity.getFirstName())))
			&&(identity.getLastName()==null || (identity.getLastName()!=null && ("").equalsIgnoreCase(identity.getLastName())))
			&& (contact == null || (contact.getEmail()==null || (contact.getEmail()!=null && ("").equalsIgnoreCase(contact.getEmail()))) && 
			(contact.getPhoneNumber()==null || (contact.getPhoneNumber()!=null && ("").equalsIgnoreCase(contact.getPhoneNumber()))))
			// REPIND-1264 : Add Search for Social by Type and Identifier
			&& (identification == null || (identification.getIdentificationType()==null || identification.getIdentificationValue() == null))
			){
					throw new MissingParameterException("LastName/FirstName or Email or Phone or identification is missing");
			}

			if(contact == null || (contact.getEmail()==null || (contact.getEmail()!=null && ("").equalsIgnoreCase(contact.getEmail()))) && 
					(contact.getPhoneNumber()==null || (contact.getPhoneNumber()!=null && ("").equalsIgnoreCase(contact.getPhoneNumber())))) {
				//104 first name is missing
				if((identity.getLastName()!=null && !("").equalsIgnoreCase(identity.getLastName())
						&& (identity.getFirstName()==null || (identity.getFirstName()!=null && ("").equalsIgnoreCase(identity.getFirstName()))))){
					throwBusinessException(BusinessErrorCodeEnum.ERR_104, "Firstname is missing");
				}

				//103 last name mandatory
				if((identity.getFirstName()!=null && !("").equalsIgnoreCase(identity.getFirstName())
						&& (identity.getLastName()==null || (identity.getLastName()!=null && ("").equalsIgnoreCase(identity.getLastName()))))){
					throwBusinessException(BusinessErrorCodeEnum.ERR_103, "Lastname is missing");
				}
				
				if (identification != null && (!"".equals(identification.getIdentificationType()) && !"".equals(identification.getIdentificationValue()))) {
					
				}
				else{ //si nom / prenom existe on controle les longueurs
					if(identity.getLastName().length()<2 && identity.getFirstName().length()<1 ){
						throw new MissingParameterException("LastName/FirstName are too short (2/1 char(s) minimum)");
					} else if(identity.getLastName().length()<2 ){
						throw new MissingParameterException("LastName is too short (2 chars minimum)");
					} else if(identity.getFirstName().length()<1 ){
						throw new MissingParameterException("FirstName is too short (1 chars minimum)");
					}
				}
			}
			
			//searchTypes missing
			if(identity.getFirstNameSearchType()==null || ("").equalsIgnoreCase(identity.getFirstNameSearchType())){
				identity.setFirstNameSearchType("S");
			}
			if(identity.getLastNameSearchType()==null || ("").equalsIgnoreCase(identity.getLastNameSearchType())){
				identity.setLastNameSearchType("S");
			}
			
			if (contact!=null){
				if (contact.getCountryCode() != null && contact.getPhoneNumber() != null){
					TelecomsDTO phoneNumberDTO = new TelecomsDTO();
					phoneNumberDTO.setCountryCode(contact.getCountryCode());
					phoneNumberDTO.setSnumero(contact.getPhoneNumber());
					TelecomsDTO normalizedPN = telecomDS.normalizePhoneNumber(phoneNumberDTO);
					
					request.getContact().setCountryCode(normalizedPN.getSnorm_inter_country_code());
					request.getContact().setPhoneNumber(normalizedPN.getSnorm_nat_phone_number_clean());
				}
				
				if (contact.getPostalAddressBloc() != null){
					if (contact.getPostalAddressBloc().getPostalAddressContent()!=null){
						PostalAddressContent pac = contact.getPostalAddressBloc().getPostalAddressContent();
						if (pac.getCity()!=null){
							pac.setCity(pac.getCity().toUpperCase());
						}
						if (pac.getCitySearchType()==null){
							pac.setCitySearchType("L");
						}
						if (pac.getZipCodeSearchType()==null){
							pac.setZipCodeSearchType("L");
						}
						if (pac.getCountryCode()!=null){
							pac.setCountryCode(pac.getCountryCode().toUpperCase());
						}
						if (pac.getNumberAndStreet()!=null){
							pac.setNumberAndStreet(pac.getNumberAndStreet().toUpperCase());
						}
					}
				}
			}

			// REPIND-1264 : Add Search for Social by Type and Identifier
			if (!SearchIndividualByMulticriteriaTransform.isNullOrEmpty(identification)) {
			// if (identification != null && identification.getIdentificationType() != null && identification.getIdentificationValue() != null) {
				
				// REPIND-1264 : Add a trim function in search
				if (identification.getIdentificationType() != null) {
					identification.setIdentificationType(identification.getIdentificationType().trim());
				}
				if (identification.getIdentificationValue() != null) {
					identification.setIdentificationValue(identification.getIdentificationValue().trim());
				}
				
				// REPIND-1286 : The two fields must be mandatory 
				if (identification.getIdentificationType() == null || "".equals(identification.getIdentificationType()) 
						|| identification.getIdentificationValue() == null  || "".equals(identification.getIdentificationValue())) {					
					// REPIND-1286 : Change Code from 113 to 133
					throwBusinessException(BusinessErrorCodeEnum.ERR_133, "type or identification could not be empty");
				} else {
					String type = identification.getIdentificationType();

					// Type must exist in database - if not... NULL
					String dbType = refTypExtIDDS.getExtIdByOption(type);
					if (dbType == null || type.length() > 3) {
						throw new InvalidExternalIdentifierTypeException(type);
					} else {
						// Remplir le PNM par PNM_ID equivalent en base de données
						request.getIdentification().setIdentificationType(dbType);
					}
				}
			}
			
			//TRAITEMENT: retourne une liste de resultats:
			log.debug("SearchIndividualByMulticriteriaV2::Input :{}",request);
			requeteDTO = SearchIndividualByMulticriteriaTransform.wsdlTOdto(request);
			
			//REPIND-1808: Add ConsumerId in Requestor
			requeteDTO.getRequestor().setConsumerId(getConsumerId(context));
			
			if(!"W".equals(request.getPopulationTargeted()))
			{
				result = getIndividuDS().searchIndividual(requeteDTO);
				log.debug("{} individual(s) found !",result.getIndividuals().size());
				
				//246 TOO MANY RESULTS FOUND
				if(result.getIndividuals().size()>255){
					log.info("TOO MANY RESULTS FOUND IN INDIVIDUAL");
					throw new JrafApplicativeException("Too many results matching");
				}else if(result.getIndividuals().size()==0){
					log.info("NO INDIVIDUAL FOUND (search for prospect)");
				}
			}
			
			response = SearchIndividualByMulticriteriaTransform.dtoTOwsdl(result);
		} 
		catch (MissingParameterException e){
			throwBusinessException(BusinessErrorCodeEnum.ERR_133, e.getMessage());
		}
		catch (JrafApplicativeException e) {
			throwBusinessException(BusinessErrorCodeEnum.ERR_246, e.getMessage());
		}
		catch (NotFoundException e)
		{
			// REPIND-555 : We do not need to call PROSPECT because data is on INDIVIDUAL 
			// if( request.getPopulationTargeted().equals("A"))
			// {
			// try {
			// 	response = searchProspect(request.getRequestor().getApplicationCode(), requeteDTO, e);
			// 	} catch (JrafDomainException e1) {
			// 		// e1.printStackTrace();
			// 		log.fatal(e1);
			// 	}
			// }
			// else {
				throwBusinessException(BusinessErrorCodeEnum.ERR_001, BusinessErrorCodeEnum.ERR_001.value(), e.getMessage());
			// }
		}
		catch (JrafDomainException e) 
		{
			// On recupere le JRafException depuis "creerListeIndividusSearchEmailOnly" et on le formate en 001 NOT FOUND
			if (e.getMessage() != null && "No individus found".equals(e.getMessage())) {
				throwBusinessException(BusinessErrorCodeEnum.ERR_001, BusinessErrorCodeEnum.ERR_001.value(), "Individual not found: No individual found");
			} else {
				throwBusinessException(BusinessErrorCodeEnum.ERR_905, e.getMessage());
			}
		}

		return response;
	}
		
	/**
	 * Lever une {@link BusinessErrorBlocBusinessException} sans {@link InternalBusinessError}.
	 * @param numError : numero de l'erreur.
	 * @param label : label de l'erreur.
	 * @param detail : detail de l'erreur.
	 * @throws BusinessErrorBlocBusinessException
	 */
	public void throwBusinessException(BusinessErrorCodeEnum numError, String detail) throws BusinessErrorBlocBusinessException {
		BusinessError businessError = buildBusinessException(numError, null, detail);
		throwBusinessException(businessError, null);
	}
	
	/**
	 * Lever une {@link BusinessErrorBlocBusinessException} sans {@link InternalBusinessError}.
	 * @param numError : numero de l'erreur.
	 * @param otherErrorCode : cas de l'erreur OTHER
	 * @param label : label de l'erreur.
	 * @param detail : detail de l'erreur.
	 * @throws BusinessErrorBlocBusinessException
	 */
	public void throwBusinessException(BusinessErrorCodeEnum numError, String otherErrorCode, String detail) throws BusinessErrorBlocBusinessException {
		
		BusinessError businessError = buildBusinessException(numError, otherErrorCode, detail);
		
		throwBusinessException(businessError, null);
	}
	
	/**
	 * Exception de {@link BusinessErrorTypeEnum} Adhesion.
	 * @param e : {@link AdhesionException} concernée.
	 * @throws BusinessErrorBlocBusinessException : {@link BusinessErrorBlocBusinessException} levé.
	 */
	public void throwAdhesionBusinessException(BusinessErrorCodeEnum errorCode, AdhesionException e) throws BusinessErrorBlocBusinessException {
		
		BusinessError businessError = buildBusinessException(errorCode, null, "Adhesion error");	
		InternalBusinessError internalBusinessError = buildInternalBusinessException(e.getCodeErreur(), e.getMessage(), BusinessErrorTypeEnum.ERROR_ADHESION);
		
		throwBusinessException(businessError, internalBusinessError);
	}
	
	
	/**
	 * Exception de {@link BusinessErrorTypeEnum} Marketing.
	 * @param numError : TODO remplacer par BusinessEnumCodeError.
	 * @param label : label de l'erreur.
	 * @param detail : detail de l'erreur.
	 * @throws BusinessErrorBlocBusinessException
	 */
	public void throwMarketingBusinessException(BusinessErrorCodeEnum errorCode, MarketingErrorException e) throws BusinessErrorBlocBusinessException {
		
		BusinessError businessError = buildBusinessException(errorCode, null, "Marketing error");
		InternalBusinessError internalBusinessError = buildInternalBusinessException(e.getFaultInfo(), null, BusinessErrorTypeEnum.ERROR_MARKETING);

		throwBusinessException(businessError, internalBusinessError);
	}

	protected void throwBusinessException(BusinessError businessError, InternalBusinessError internalBusinessError) throws BusinessErrorBlocBusinessException {
		
		BusinessErrorBloc businessErrorBloc = new BusinessErrorBloc();
		businessErrorBloc.setBusinessError(businessError);
		businessErrorBloc.setInternalBusinessError(internalBusinessError);
		
		throw new BusinessErrorBlocBusinessException("", businessErrorBloc);
	}
	
	@SuppressWarnings("deprecation")
	protected BusinessError buildBusinessException(BusinessErrorCodeEnum errorCode, String otherErrorCode, String detail) {
		
		String label;
		
		try {
			String errorCodeStr = (errorCode==BusinessErrorCodeEnum.ERR_001) ? otherErrorCode : errorCode.toString();
			String errorNum = errorCodeStr.replace(ERROR_PREFIX,"");
			label = errorDS.getErrorDetails(errorNum).getLabelUK();
		} catch (Exception e) {
			label = UNABLE_TO_GET_LABEL_ERROR;
		}
		
		BusinessError businessError = new BusinessError();
		businessError.setErrorCode(errorCode);
		businessError.setOtherErrorCode("");
		businessError.setErrorLabel(SicStringUtils.truncate(label,ERROR_LABEL_MAX_LENGTH));
		businessError.setErrorDetail(SicStringUtils.truncate(detail,ERROR_DETAIL_MAX_LENGTH));

		return businessError;
	}
	
	protected InternalBusinessError buildInternalBusinessException(String errorCode, String label,BusinessErrorTypeEnum errorType) {
		
		InternalBusinessError internalBusinessError = new InternalBusinessError();
		internalBusinessError.setErrorCode(errorCode);
		internalBusinessError.setErrorLabel(SicStringUtils.truncate(label,ERROR_LABEL_MAX_LENGTH));
		internalBusinessError.setErrorType(errorType);
		
		return internalBusinessError;
		
	}

//	public void setProspectDS(IProspectDS prospectDS) {
//		this.prospectDS = prospectDS;
//	}
//
//	public IProspectDS getProspectDS() {
//		return prospectDS;
//	}
	

	public IndividuDS getIndividuDS() {
		return individuDS;
	}

	public void setIndividuDS(IndividuDS individuDS) {
		this.individuDS = individuDS;
	}

	public TelecomDS getTelecomDS() {
		return telecomDS;
	}

	public void setTelecomDS(TelecomDS telecomDS) {
		this.telecomDS = telecomDS;
	}
	
	// REPIND-1441 : Trace who are consuming us and for what 
	private void TraceInput(SearchIndividualByMulticriteriaRequest request) {
		
		String retour = "W001271V2; ";

		// CONSUMER ID
		retour += SicStringUtils.TraceInputConsumer(context);

		// SITE + SIGNATURE + APPLICATION CODE
		if (request != null) {
			
			if (request.getRequestor() != null) {
				
				retour += SicStringUtils.TraceInputRequestor(
						request.getRequestor().getChannel(), request.getRequestor().getMatricule(), request.getRequestor().getOfficeId(),
						request.getRequestor().getSite(), request.getRequestor().getSignature(), request.getRequestor().getApplicationCode());
			}
			
			// SEARCH : MANUEL/AUTO DRIVE POPULATION TARGET
			retour += "PRO:" + SicStringUtils.WriteNull(request.getProcessType()) + "; ";
			retour += "DRI:" + SicStringUtils.WriteNull(request.getSearchDriving()) + "; ";
			retour += "SCO:" + SicStringUtils.WriteNull(request.getPopulationTargeted()) + "; ";
			
			// INPUT SOCIAUX
			if (request.getIdentification() != null) {
				retour += "SOC:" + request.getIdentification().getIdentificationType() + "; ";
			}

			// INPUT INDIVIDUAL
			if (request.getIdentity() != null) {
				retour += "IND:";
				if (request.getIdentity().getCivility() != null) {
					retour += "CV|";
				}
				if (request.getIdentity().getBirthday() != null) {
					retour += "BD|";
				}
				if (request.getIdentity().getLastName() != null) {
					retour += "LN" + SicStringUtils.WriteNull(request.getIdentity().getLastNameSearchType()) + "|";
				}
				if (request.getIdentity().getFirstName() != null) {
					retour += "FN" + SicStringUtils.WriteNull(request.getIdentity().getFirstNameSearchType()) + "|";
				}
				retour = retour.substring(0, retour.length()-1);
				retour += "; ";
			}

			// INPUT CONTACT
			if (request.getContact() != null) {
				retour += "CON:";
				if (request.getContact().getEmail() != null) {
					retour += "EM|";	
				}
				if (request.getContact().getPhoneNumber() != null) {
					retour += "PN|";	
				}
				if (request.getContact().getPostalAddressBloc() != null) {
					retour += "PA|";	
				}
				retour = retour.substring(0, retour.length()-1);
				retour += "; ";
			}
		}
		log.info(retour);
	}
	
	//REPIND-1808: ConsumerId must be retrieved for CCP Calls
	public String getConsumerId(WebServiceContext context) throws JrafDomainException {
		Header header = ReadSoaHeaderHelper.getHeaderFromContext(context, "trackingMessageHeader");
		String consumerId = null;
		
		try {
			consumerId = ReadSoaHeaderHelper.getHeaderChildren(header, "consumerID");
		} catch (SOAPException e) {
			throw new JrafDomainException("Error during retrieve of ConsumerId");
		}

		return consumerId;
	}
}
