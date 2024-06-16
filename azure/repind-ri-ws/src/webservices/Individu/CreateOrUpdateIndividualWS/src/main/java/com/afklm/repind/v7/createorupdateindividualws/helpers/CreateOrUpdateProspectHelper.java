package com.afklm.repind.v7.createorupdateindividualws.helpers;

import com.afklm.repind.v7.createorupdateindividualws.transformers.IndividuRequestTransformV7;
import com.afklm.repind.v7.createorupdateindividualws.transformers.IndividuTransformV7;
import com.afklm.soa.stubs.w000442.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v7.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000442.v7.response.InformationResponse;
import com.afklm.soa.stubs.w000442.v7.siccommontype.Information;
import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.UpdateModeEnum;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.myaccountcustomerdata.ReturnDetailsDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.util.CommunicationPreferencesUtils;
import com.airfrance.repind.util.LoggerUtils;
import com.airfrance.repind.util.SicDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("createOrUpdateProspectHelperV7")
@Slf4j
public class CreateOrUpdateProspectHelper {
	
	private static final int LIMIT_ML = 10;
	
	/**
	 * DS for account
	 */
	@Autowired
	protected MyAccountDS myAccountDS;
	
	@Autowired
	protected IndividuDS individuDS;
		
	@Autowired
	protected TelecomDS telecomDS;
	
	@Autowired
	private BusinessExceptionHelper businessExceptionHelperV7;
	
	@Autowired
	protected CommunicationPreferencesHelper communicationPreferencesHelper;
	
	@Autowired
	protected IndividuRepository individuRepository;
	
	public ProspectIndividuDTO findIndividual(CreateUpdateIndividualRequest request) throws BusinessErrorBlocBusinessException, JrafDomainException {
		/*** Recherche d'individu(s) existants pour cet email (reconciliation) ***/
		List<ProspectIndividuDTO> findIndividu = null;
		
		try {
			findIndividu = myAccountDS.findAnIndividualByEmail(request.getEmailRequest().get(0).getEmail().getEmail());
		} catch (JrafDomainException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
		} catch (Exception e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
		}
		ProspectIndividuDTO find = null;
		
		if (findIndividu != null && !findIndividu.isEmpty()) {     //test de multiplicité: reconciliation avec le CIN ou le GIN si plus d'un individu trouvé
			if (findIndividu.size() > 1) {
				for (ProspectIndividuDTO individu: findIndividu) {
					if (request.getRequestor().getLoggedGin() != null && individu.getGin() != null && IndividuTransformV7.clientNumberToString(individu.getGin()).equalsIgnoreCase(request.getRequestor().getLoggedGin())) {
						find = individu;
					}
					else if (request.getRequestor().getReconciliationDataCIN() != null && individu.getCin() != null && IndividuTransformV7.clientNumberToString(individu.getCin()).equalsIgnoreCase(request.getRequestor().getReconciliationDataCIN())) {
						find = individu;
					}
				}
				// En cas de contexte B2C_HOME_PAGE on peut demander plus d infos 
				// si on arrive pas a isoler un seul individu (erreur 550)
				if(find == null && !"".equalsIgnoreCase(request.getRequestor().getContext()) && "B2C_HOME_PAGE".equalsIgnoreCase(request.getRequestor().getContext())) { 
					throw new ReconciliationProcessException("New data required for reconciliation process");
				}
			}
			else{
				find = findIndividu.get(0);
			}
		}
		return find;
	}
	
	public CreateUpdateIndividualRequest updateIndividualProspect(ProspectIndividuDTO prospectIndivDto, CreateUpdateIndividualRequest request ) throws BusinessErrorBlocBusinessException, JrafDomainException {
		CreateUpdateIndividualRequest updateIndividu = null;
		
		if (prospectIndivDto != null) {
			try {
				log.debug("Call UpdateIndividualProspect for gin : {}",prospectIndivDto.getGin());
				
				updateIndividu = new CreateUpdateIndividualRequest();
				updateIndividu.setRequestor(IndividuRequestTransformV7.getRequestor(request));
	
				updateIndividu.setIndividualRequest(IndividuRequestTransformV7.getIndRequest(request, prospectIndivDto.getGin().toString()));
				
				// Communication preferences set to Update mode (UPD)
				if ((request.getComunicationPreferencesRequest() != null && !request.getComunicationPreferencesRequest().isEmpty()) && request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences() != null) {
					updateIndividu.getComunicationPreferencesRequest().add(IndividuTransformV7.dtoProspectToDtoIndividuCommPrefs(request));
					updateIndividu.setUpdateCommunicationPrefMode(UpdateModeEnum.UPDATE.toString());
				}
				if (request.getTelecomRequest() != null && !request.getTelecomRequest().isEmpty()) {
					//transform TELECOMS
					updateIndividu.getTelecomRequest().add(IndividuTransformV7.dtoProspectToDtoTelecom(request));
				}
				if (request.getPreferenceDataRequest() != null) {
					//transform PREFERENCES
					updateIndividu.setPreferenceDataRequest(IndividuTransformV7.dtoProspectToDtoPreference(request));
				}
	
				log.debug("Entering update...");
					
			} catch (Exception e){
				log.error(LoggerUtils.buidErrorMessage(e), e);
				throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905,e.getMessage());
			}
		}
		
		return updateIndividu;
	}

	public IndividuDTO searchProspectByEmail(CreateUpdateIndividualRequest request) throws BusinessErrorBlocBusinessException, JrafDomainException {
		IndividuDTO findProspect = null;
		
		List<IndividuDTO> listFindProspect = individuDS.findProspectByEmail(request.getEmailRequest().get(0).getEmail().getEmail());

		if(listFindProspect != null) {
			if(listFindProspect.size() == 1) {
				return listFindProspect.get(0);
			} else if(listFindProspect.size() > 1) {
				return filterIndividualByName(request, listFindProspect);
			}
		}
		
		return findProspect;
	}

	private IndividuDTO filterIndividualByName(CreateUpdateIndividualRequest request, List<IndividuDTO> findProspect) {

		if(request.getIndividualRequest() != null && request.getIndividualRequest().getIndividualInformations() != null) {

			// Compare all lastname/firstname with request
			for (IndividuDTO indDTO : findProspect) {
				if(request.getIndividualRequest().getIndividualInformations().getFirstNameSC() != null && request.getIndividualRequest().getIndividualInformations().getLastNameSC() != null) {
					if(request.getIndividualRequest().getIndividualInformations().getFirstNameSC().equalsIgnoreCase(indDTO.getPrenom())
							&& request.getIndividualRequest().getIndividualInformations().getLastNameSC().equalsIgnoreCase(indDTO.getNom())) {
						return indDTO;
					}
				} else if(request.getIndividualRequest().getIndividualInformations().getFirstNameSC() != null || request.getIndividualRequest().getIndividualInformations().getLastNameSC() != null) {
					if(request.getIndividualRequest().getIndividualInformations().getFirstNameSC() != null) {
						if(request.getIndividualRequest().getIndividualInformations().getFirstNameSC().equalsIgnoreCase(indDTO.getPrenom()) && indDTO.getNom() == null) {
							return indDTO;
						}
					} else if (request.getIndividualRequest().getIndividualInformations().getLastNameSC() != null) {
						if(request.getIndividualRequest().getIndividualInformations().getLastNameSC().equalsIgnoreCase(indDTO.getNom()) && indDTO.getPrenom() == null) {
							return indDTO;
						}
					}
				} else {
					if(indDTO.getPrenom() == null && indDTO.getNom() == null) {
						return indDTO;
					}
				}
			}
			
		}
		
		return null;
	}
		
//	public ProspectDTO searchProspectByEmail(String email) throws BusinessErrorBlocBusinessException, JrafDomainException {
//		ProspectDTO searchProspect = new ProspectDTO();
//		List<ProspectDTO> listFindProspect = null;
//		ProspectDTO findProspect = null;
//		searchProspect.setEmail(email);
//		
//		try {
//			listFindProspect = individuDS.findByExample(searchProspect);
//		} catch (JrafDomainException e) {
//			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905,e.getMessage());
//		}catch (Exception e){
//			LOGGER.fatal(e);
//			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905,e.getMessage());
//		}
//		
//		if (listFindProspect != null && !listFindProspect.isEmpty()) {
//			findProspect = listFindProspect.get(0);
//		}
//		return findProspect;
//	}
	
//	public List<ProspectDTO> searchProspectByGIN(Long gin) throws BusinessErrorBlocBusinessException, JrafDomainException {
//		ProspectDTO searchProspect = new ProspectDTO();
//		List<ProspectDTO> findProspect = null;
//		searchProspect.setGin(gin);
//		
//		try {
//			findProspect = individuDS.findByExample(searchProspect);
//		} catch (JrafDomainException e) {
//			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905,e.getMessage());
//		}catch (Exception e){
//			LOGGER.fatal(e);
//			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905,e.getMessage());
//		}
//		return findProspect;
//	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String updateProspect(IndividuDTO updateProspectDTO, IndividuDTO findProspectDTO, CreateUpdateIndividualResponse createUpdateIndividualResponse, CreateUpdateIndividualRequest request) throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		List<CommunicationPreferencesDTO> prospectComPrefSet = updateProspectDTO.getCommunicationpreferencesdto();
		communicationPreferencesHelper.crmOptinComPrefs(prospectComPrefSet, createUpdateIndividualResponse);
				
		String response = null;
		
		//try {
			// update prospect
			// verification des dates d'optin pour ne pas les ecraser (REPIND-610)
			//individuDS.reuseProspectInitialComPrefData(findProspectDTO.getCommunicationpreferencesdto(), updateProspectDTO.getCommunicationpreferencesdto());
			
			// maj des marchés/langues (ajout si nouveau ou modif si existant)
			
			//updateComPref(updateProspectDTO, findProspectDTO, createUpdateIndividualResponse);
			//response = individuDS.updateAnIndividualProspect(updateProspectDTO, findProspectDTO);	
			
		/*} catch (MaximumSubscriptionsException e){
			throw new MaximumSubscriptionsException(e.getMessage());
		}
		catch (Exception e){
			LOGGER.fatal(e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
		}*/
		
		
		Individu individuBO = individuRepository.findBySgin(findProspectDTO.getSgin());
		
		
		//TODO Make A Transform
		Date today = new Date();
		SignatureDTO signatureAPP = new SignatureDTO();
		signatureAPP.setDate(new Date());
		signatureAPP.setHeure(SicDateUtils.computeHour(today));
		signatureAPP.setIpAddress(request.getRequestor().getIpAddress());
		signatureAPP.setSignature(request.getRequestor().getSignature());
		signatureAPP.setSite(request.getRequestor().getSite());
		signatureAPP.setTypeSignature(null);
		signatureAPP.setApplicationCode(request.getRequestor().getApplicationCode());
		
		String comprefMode = "U";
		if (StringUtils.isNotEmpty(request.getUpdateCommunicationPrefMode())) {
			comprefMode = request.getUpdateCommunicationPrefMode();
		}
				
		ReturnDetailsDTO newResponseForWS = myAccountDS.updateCommunicationPreferences(individuBO, prospectComPrefSet, comprefMode, signatureAPP, false, false);
		
		if (StringUtils.isNotEmpty(newResponseForWS.getDetailedCode())) {
			createUpdateIndividualResponse.getInformationResponse().add(setReturnDetailsOnAProspectCommon(Integer.parseInt(newResponseForWS.getDetailedCode())));
		}
			
		findProspectDTO = individuDS.refresh(findProspectDTO);
		
		response = individuDS.updateAnIndividualProspect(updateProspectDTO, findProspectDTO);	
		
		return response;
	}
	
	 /**
     * REPIND-1656: Some codes have been put in common
     * 
     * Fonction permettant d'affecter les paramètres de l'objet ReturnDetails en fonction du numéro d'erreur
     */
    private InformationResponse setReturnDetailsOnAProspectCommon(int number){
    	InformationResponse response = new InformationResponse();
    	Information info = new Information();
    	
    	if(number==3){
    		info.setInformationCode("1");
    		info.setInformationDetails("Existing Prospect Found, update of this market/language ok.");
    	}
    	if(number==6){
    		info.setInformationCode("4");
    		info.setInformationDetails("Existing Prospect Found, optin already set on this market/language.");
    	}
    	if(number==9){
    		info.setInformationCode("7");
    		info.setInformationDetails("Existing Prospect Found, Market/language not found or already optout.");
    	}
    	
    	response.setInformation(info);
		return response;
    }

	/**
	 * This method create a new prospect in the DB
	 * @param createProspect
	 * @return createAProspectResponseDTO
	 * @throws JrafDomainException
	 */
	public String createProspect(IndividuDTO createProspect, CreateUpdateIndividualResponse createUpdateIndividualResponse) throws JrafDomainException {
		
		List<CommunicationPreferencesDTO> prospectComPrefSet = createProspect.getCommunicationpreferencesdto();
		communicationPreferencesHelper.crmOptinComPrefs(prospectComPrefSet, createUpdateIndividualResponse);
		
		String response = null;
		try {

			if(StringUtils.isBlank(createProspect.getCivilite())) {
				createProspect.setCivilite("M.");
			}
			if(StringUtils.isBlank(createProspect.getSexe())) {
				createProspect.setSexe("U");
			}
			if(StringUtils.isBlank(createProspect.getNonFusionnable())) {
				createProspect.setNonFusionnable("N");
			}
			createProspect.setType("W");
			
			// REPIND-555 : PROSPECT's migration from SICUTF8 to SIC
			// create prospect
			response = individuDS.createAnIndividualProspect(createProspect);
			
		} catch (JrafDomainException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw new JrafDomainException("Unable to create prospect");
		}
		return response;
	}
	
	/**
	 * Prospect telecom's update
	 * 
	 * <p>
	 * Telecom's update is not a blocking process. That is the reason why there are no exceptions thrown.
	 * When normalization fails, telecom is not recorded and the web service returns a success response.
	 * </p>
	 */
	public void updateProspectTelecoms(CreateUpdateIndividualRequest request, String sgin) throws JrafDomainException {
		
		final String WEBSERVICE_IDENTIFIER = "W000442";
		final String SITE = "VLB";
		if (request != null && request.getTelecomRequest() != null && !request.getTelecomRequest().isEmpty()) {
			try {
				Long gin = Long.valueOf(sgin);
				
				List<TelecomsDTO> prospectTelecomsFromWS = IndividuTransformV7.transformToProspectTelecomsDTO(request.getTelecomRequest());
				SignatureDTO signatureAPP = IndividuRequestTransformV7.transformToProspectSignatureAPP(request.getRequestor());
				SignatureDTO signatureWS = IndividuRequestTransformV7.transformToProspectSignatureWS(WEBSERVICE_IDENTIFIER, SITE);
				
				telecomDS.checkProspectTelecom(prospectTelecomsFromWS);
				List<TelecomsDTO> normalizedProspectTelecomsFromWS = telecomDS.normalizeProspectPhoneNumber(prospectTelecomsFromWS);
				telecomDS.updateProspectNormalizedTelecom(gin, normalizedProspectTelecomsFromWS, signatureAPP, signatureWS);
				
			} 
			// 133 : MISSING PARAMETER
			catch (MissingParameterException e) {
				log.error("MISSING PARAMETER: " ,e);
			}
			// 701 : PHONE NUMBER TOO LONG
			catch (TooLongPhoneNumberException e) {
				log.error("TELECOM NORMALIZATION ERROR: ",e);
			}
			// 702 : PHONE NUMBER TOO SHORT
			catch (TooShortPhoneNumberException e) {
				log.error("TELECOM NORMALIZATION ERROR: ",e);
			}
			// 703 : UNKNOWN
			catch (InvalidPhoneNumberException e) {
				log.error("TELECOM NORMALIZATION ERROR: ",e);
			}
			// 711 : INVALID PHONE COUNTRY CODE
			catch (InvalidCountryCodeException e) {
				log.error("TELECOM NORMALIZATION ERROR: ",e);
			}
			// 705 : NO NORMALIZED
			catch (NormalizedPhoneNumberException e) {
				log.error("TELECOM NORMALIZATION ERROR: ",e);
			}
			// 932 : INVALID PARAMETER
			catch (InvalidParameterException e) {
				log.error("INVALID PARAMETER: ",e);
			}
			// 905 : TECHNICAL ERROR
			catch (Exception e) {
				log.error("UNEXPECTED ERROR: ",e);
			}
		}
	}
	
	
	// REPIND-555: Prospect migration to sic from sic-utf8 -> Changes ProspectDTO by IndividuDTO 
	private void updateComPref(IndividuDTO updateProspectDTO, IndividuDTO findProspectDTO, CreateUpdateIndividualResponse response) throws JrafDomainException {
		// Si des comprefs ont été saisis et si il existe des comprefs pour l'individu en base	
		if (findProspectDTO.getCommunicationpreferencesdto() != null && !findProspectDTO.getCommunicationpreferencesdto().isEmpty()	&& updateProspectDTO.getCommunicationpreferencesdto() !=null && !updateProspectDTO.getCommunicationpreferencesdto().isEmpty()) {
			//recherche si M/L en input existe déjà
			for (CommunicationPreferencesDTO cpRequest : updateProspectDTO.getCommunicationpreferencesdto()) {
				boolean findCp=false;

				for (CommunicationPreferencesDTO cpExist : findProspectDTO.getCommunicationpreferencesdto()) {
    				//recherche de concordance sur les COMP_REF : ici si la compref saisie est identique a la compref en BDD
					if (cpExist.getDomain().equalsIgnoreCase(cpRequest.getDomain())	&& cpExist.getComGroupType().equalsIgnoreCase(cpRequest.getComGroupType()) && cpExist.getComType().equalsIgnoreCase(cpRequest.getComType())) {
						findCp=true;
						
    					//recherche de concordance des M/L
						if (cpExist.getMarketLanguageDTO() != null && !cpExist.getMarketLanguageDTO().isEmpty()	&& cpRequest.getMarketLanguageDTO() != null && !cpRequest.getMarketLanguageDTO().isEmpty()) {
							for (MarketLanguageDTO mlRequest : cpRequest.getMarketLanguageDTO()) {
								boolean findMl = false;
								for (MarketLanguageDTO mlExist : cpExist.getMarketLanguageDTO()) {
									// Si marché/langue existe deja et identique à celui saisi en entree pour ce prospect en BDD
    								if (mlExist.getMarket().equalsIgnoreCase(mlRequest.getMarket()) && mlExist.getLanguage().equalsIgnoreCase(mlRequest.getLanguage())) {
    									findMl = true;
    									if (mlExist.getOptIn().equalsIgnoreCase(mlRequest.getOptIn())) {
    										if (mlRequest.getOptIn().equalsIgnoreCase("N")) {
    											// ML deja à "N"
    											response.getInformationResponse().add(setReturnDetailsOnAProspect(7));
    										}
    										if (mlRequest.getOptIn().equalsIgnoreCase("Y")) {
    											// ML deja à "Y"
    											response.getInformationResponse().add(setReturnDetailsOnAProspect(4));
    										}
    									}
    									else {
    										//mise à jour du marche/langue avec l'input
    										response.getInformationResponse().add(setReturnDetailsOnAProspect(1));
    										mlExist.setOptIn(mlRequest.getOptIn()); 
    									
    										if (mlRequest.getDateOfConsent()!=null) {
    											mlExist.setDateOfConsent(mlRequest.getDateOfConsent());
    										}
    										else {
    											mlExist.setDateOfConsent(new Date());
    										}
    										if (mlRequest.getMedia1() != null) {
    											mlExist.setMedia1(mlRequest.getMedia1());
    										}
    										else {
    											mlExist.setMedia1(null);
    										}
    										if (mlRequest.getMedia2() != null) {
    											mlExist.setMedia2(mlRequest.getMedia2());
    										}
    										else {
    											mlExist.setMedia2(null);
    										}
    										if (mlRequest.getMedia3() != null) {
    											mlExist.setMedia3(mlRequest.getMedia3());
    										}
    										else{
    											mlExist.setMedia3(null);
    										}
    										if(mlRequest.getMedia4() != null)
    											mlExist.setMedia4(mlRequest.getMedia4());
    										else {
    											mlExist.setMedia4(null);
    										}
    										if (mlRequest.getMedia5() != null) {
    											mlExist.setMedia5(mlRequest.getMedia5());
    										}
    										else {
    											mlExist.setMedia5(null);
    										}
    										if (mlRequest.getModificationSignature() != null) {
    											mlExist.setModificationSignature(mlRequest.getModificationSignature());
    										}
    										if (mlRequest.getModificationSite() != null) {
    											mlExist.setModificationSite(mlRequest.getModificationSite());
    										}
    										if (mlRequest.getModificationDate()!=null) {
    											mlExist.setModificationDate(mlRequest.getModificationDate());
    										}
    									}
    								}
    				
    							}
								// Si le ML saisi en entrée n'existe pas en BDD on le cree
								if(!findMl){
									// Max de ML pour une compref = 10
									if (cpExist.getMarketLanguageDTO().size() < LIMIT_ML) {
										cpExist.getMarketLanguageDTO().add(mlRequest);
//										response.getInformationResponse().add(setReturnDetailsOnAProspect(7));
									} else {
										throw new MaximumSubscriptionsException("MAXIMUM NUMBER OF SUBSCRIBED NEWLETTER SALES REACHED");
									}
								}
							}
						}
	    						
    					if(cpRequest.getDateOptin()!=null)
    						cpExist.setDateOptin(cpRequest.getDateOptin());
    					else
    						cpExist.setDateOptin(new Date());
    					if(cpRequest.getChannel()!=null)
    						cpExist.setChannel(cpRequest.getChannel());

    					if(cpRequest.getDateOfEntry()!=null)
    						cpExist.setDateOfEntry(cpRequest.getDateOfEntry());

						if(cpRequest.getOptinPartners()!=null)
    						cpExist.setOptinPartners(cpRequest.getOptinPartners());
						
						if(cpRequest.getDateOptinPartners()!=null)
    						cpExist.setDateOptinPartners(cpRequest.getDateOptinPartners());

						if(cpRequest.getMedia1()!=null)
							cpExist.setMedia1(cpRequest.getMedia1());
						else
							cpExist.setMedia1(null);
						
						if(cpRequest.getMedia2()!=null)
							cpExist.setMedia2(cpRequest.getMedia2());
						else
							cpExist.setMedia2(null);
						
						if(cpRequest.getMedia3()!=null)
							cpExist.setMedia3(cpRequest.getMedia3());
						else
							cpExist.setMedia3(null);
						
						if(cpRequest.getMedia4()!=null)
							cpExist.setMedia4(cpRequest.getMedia4());
						else
							cpExist.setMedia4(null);
						
						if(cpRequest.getMedia5()!=null)
							cpExist.setMedia5(cpRequest.getMedia5());
						else
							cpExist.setMedia5(null);
						
						if(cpRequest.getModificationSignature()!=null)
							cpExist.setModificationSignature(cpRequest.getModificationSignature());
		
						if(cpRequest.getModificationSite()!=null)
							cpExist.setModificationSite(cpRequest.getModificationSite());

						if(cpRequest.getModificationDate()!=null)
							cpExist.setModificationDate(cpRequest.getModificationDate());

						// maj de l'optin
						if(cpRequest.getSubscribe() !=null) {
							cpExist.setSubscribe(cpRequest.getSubscribe());
						}
						
						//vérification de la cohérance de l'optin/out global
						CommunicationPreferencesUtils.validateGlobalOptin(cpExist);
    				}
    			}
				if(!findCp)  {// si on a pas trouvé la COM_PREF:
					response.getInformationResponse().add(setReturnDetailsOnAProspect(7));
					if (findProspectDTO.getCommunicationpreferencesdto().size() < 10) {
						findProspectDTO.getCommunicationpreferencesdto().add(cpRequest);
					}
				}
			}
		}
		// si aucune COM_PREF n'existe pour cet individu en BDD: on crée une nouvelle
		else if (findProspectDTO.getCommunicationpreferencesdto() == null || findProspectDTO.getCommunicationpreferencesdto().isEmpty()){ 
			if (updateProspectDTO.getCommunicationpreferencesdto()!=null) {
				for (CommunicationPreferencesDTO reqComPref : updateProspectDTO.getCommunicationpreferencesdto()) {
					if (findProspectDTO.getCommunicationpreferencesdto() == null || findProspectDTO.getCommunicationpreferencesdto().size() < 10) {
						findProspectDTO.getCommunicationpreferencesdto().add(reqComPref);
					}
				}
//				response.getInformationResponse().add(setReturnDetailsOnAProspect(7));
			}
		}
	}
	
	/**
     * Fonction permettant d'affecter les paramètres de l'objet ReturnDetails en fonction du numéro d'erreur
     */
    private InformationResponse setReturnDetailsOnAProspect(int number){
    	InformationResponse response = new InformationResponse();
    	Information info = new Information();
    	
    	if(number==1){
    		info.setInformationCode(""+number);
    		info.setInformationDetails("Existing Prospect Found, update of this market/language ok.");
    	}
    	if(number==4){
    		info.setInformationCode(""+number);
    		info.setInformationDetails("Existing Prospect Found, optin already set on this market/language.");
    	}
    	if(number==7){
    		info.setInformationCode(""+number);
    		info.setInformationDetails("Existing Prospect Found, Market/language not found or already optout.");
    	}
    	
    	response.setInformation(info);
		return response;
    }

	// REPIND-701 : For delete logically a prospect
	public void deleteProspect(IndividuDTO deleteProspectDTO) throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		try {
			individuDS.deleteAProspect(deleteProspectDTO);
		}
		catch (Exception e){
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
		}
	}
}
