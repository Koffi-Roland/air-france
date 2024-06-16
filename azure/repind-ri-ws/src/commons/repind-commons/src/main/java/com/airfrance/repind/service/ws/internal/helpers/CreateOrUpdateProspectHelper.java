package com.airfrance.repind.service.ws.internal.helpers;

import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.MediumCodeEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.ref.type.NATFieldsEnum;
import com.airfrance.ref.type.UpdateModeEnum;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.RequestorDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.*;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.InformationDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.InformationResponseDTO;
import com.airfrance.repind.dto.individu.myaccountcustomerdata.ReturnDetailsDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.ws.EmailDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.role.RoleContrats;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.Utils;
import com.airfrance.repind.util.CommunicationPreferencesUtils;
import com.airfrance.repind.util.transformer.IndividuTransform;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CreateOrUpdateProspectHelper {
	
	private Log LOGGER = LogFactory.getLog(CreateOrUpdateProspectHelper.class);
	
	private static final int LIMIT_ML = 10;
	
	/**
	 * DS for account
	 */
	@Autowired
	protected MyAccountDS myAccountDS;
	
	@Autowired
	protected IndividuDS individuDS;
	
	@Autowired
	protected PreferenceDS preferenceDS;
		
	@Autowired
	protected TelecomDS telecomDS;
	
	@Autowired
	protected IndividuRepository individuRepository;

	@Autowired
	protected RoleContratsRepository roleContratsRepository;

	@Autowired
	protected CommunicationPreferencesHelper communicationPreferencesHelper;
	
	public ProspectIndividuDTO findIndividual(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {
		/*** Recherche d'individu(s) existants pour cet email (reconciliation) ***/
		List<ProspectIndividuDTO> findIndividu = null;
		
		findIndividu = myAccountDS.findAnIndividualByEmail(request.getEmailRequestDTO().get(0).getEmailDTO().getEmail());
		
		ProspectIndividuDTO find = null;
		
		if (findIndividu != null && !findIndividu.isEmpty()) {     //test de multiplicité: reconciliation avec le CIN ou le GIN si plus d'un individu trouvé
			if (findIndividu.size() > 1) {
				for (ProspectIndividuDTO individu: findIndividu) {
					if (request.getRequestorDTO().getLoggedGin() != null && StringUtils.isNotEmpty(individu.getGin()) && individu.getGin().equalsIgnoreCase(request.getRequestorDTO().getLoggedGin())) {
						find = individu;
					}
					else if (request.getRequestorDTO().getReconciliationDataCIN() != null && StringUtils.isNotEmpty(individu.getCin()) && individu.getCin().equalsIgnoreCase(request.getRequestorDTO().getReconciliationDataCIN())) {
						find = individu;
					}
				}
				// En cas de contexte B2C_HOME_PAGE on peut demander plus d infos 
				// si on arrive pas a isoler un seul individu (erreur 550)
				if(find == null && !"".equalsIgnoreCase(request.getRequestorDTO().getContext()) && "B2C_HOME_PAGE".equalsIgnoreCase(request.getRequestorDTO().getContext())) { 
					throw new ReconciliationProcessException("New data required for reconciliation process");
				}
			}
			else{
				find = findIndividu.get(0);
			}
		}
		return find; 
	}
	
	public CreateUpdateIndividualRequestDTO updateIndividualProspect(ProspectIndividuDTO prospectIndivDto, CreateUpdateIndividualRequestDTO request ) throws InvalidParameterException, MissingParameterException {
		
		CreateUpdateIndividualRequestDTO updateIndividu = null;
		
		if (prospectIndivDto != null) {
			LOGGER.debug("Call UpdateIndividualProspect for gin : " + prospectIndivDto.getGin());
			
			updateIndividu = new CreateUpdateIndividualRequestDTO();
			updateIndividu.setRequestorDTO(IndividuTransform.getRequestorProspectToIndiv(request));
	
			updateIndividu.setIndividualRequestDTO(IndividuTransform.getIndRequestProspectToIndiv(request, prospectIndivDto.getGin()));
			
			// Communication preferences set to Update mode (UPD)
			if ((request.getCommunicationPreferencesRequestDTO() != null && !request.getCommunicationPreferencesRequestDTO().isEmpty()) && request.getCommunicationPreferencesRequestDTO().get(0).getCommunicationPreferencesDTO() != null) {
				updateIndividu.setCommunicationPreferencesRequestDTO(IndividuTransform.getCommPrefRequestProspectToIndiv(request));
				updateIndividu.setUpdateCommunicationPrefMode(UpdateModeEnum.UPDATE.toString());
			}
			if (request.getTelecomRequestDTO() != null && !request.getTelecomRequestDTO().isEmpty()) {
				//transform TELECOMS
				updateIndividu.setTelecomRequestDTO(IndividuTransform.getTelecomRequestProspectToIndiv(request));
			}
			
			// TODO add pref bloc and control persist
			if (request.getPreferenceRequestDTO() != null && request.getPreferenceRequestDTO().getPreferenceDTO() != null) {
				updateIndividu.setPreferenceRequestDTO(IndividuTransform.getPreferenceRequestProspectToIndiv(request));
			}
			
			// Transform email
			if (request.getEmailRequestDTO() != null) {
				List<EmailRequestDTO> updatedEmailList = new ArrayList<EmailRequestDTO>();
				for (EmailRequestDTO emailWS : request.getEmailRequestDTO()) {
					EmailRequestDTO updatedEmail = null;
					
					
					if (emailWS.getEmailDTO() != null) {
						updatedEmail = new EmailRequestDTO();
						EmailDTO mail = new EmailDTO();
						mail.setEmail(emailWS.getEmailDTO().getEmail());
						
						if (emailWS.getEmailDTO().getEmailOptin() != null) {
							mail.setEmailOptin(emailWS.getEmailDTO().getEmailOptin());
						}
						else {
							mail.setEmailOptin(NATFieldsEnum.NONE.getValue());
						}
						
						if (emailWS.getEmailDTO().getMediumCode() != null) {
							mail.setMediumCode(emailWS.getEmailDTO().getMediumCode());
						}
						else {
							mail.setMediumCode(MediumCodeEnum.HOME.toString());
						}
						
						if (emailWS.getEmailDTO().getMediumStatus() != null) {
							mail.setMediumStatus(emailWS.getEmailDTO().getMediumStatus());
						}
						else {
							mail.setMediumStatus(MediumStatusEnum.VALID.toString());
						}
						
						updatedEmail.setEmailDTO(mail);
					}
					
					updatedEmailList.add(updatedEmail);
				}
				updateIndividu.setEmailRequestDTO(updatedEmailList);
			}
	
			LOGGER.debug("Entering update...");
		}
		
		return updateIndividu;
	}

	public IndividuDTO searchProspectByEmail(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {
		List<IndividuDTO> findProspect = null;
		
		findProspect = individuDS.findProspectByEmail(request.getEmailRequestDTO().get(0).getEmailDTO().getEmail());
		
		if(findProspect != null) {
			if(findProspect.size() == 1) {
				return findProspect.get(0);
			} else if(findProspect.size() > 1) {
				return filterIndividualByName(request, findProspect);
			}
		}
		
		return null;
	}
	
	private IndividuDTO filterIndividualByName(CreateUpdateIndividualRequestDTO request, List<IndividuDTO> findProspect) {

		if(request.getIndividualRequestDTO() != null && request.getIndividualRequestDTO().getIndividualInformationsDTO() != null) {

			// Compare all lastname/firstname with request
			for (IndividuDTO indDTO : findProspect) {
				if(request.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC() != null && request.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC() != null) {
					if(request.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC().equalsIgnoreCase(indDTO.getPrenom())
							&& request.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC().equalsIgnoreCase(indDTO.getNom())) {
						return indDTO;
					}
				} else if(request.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC() != null || request.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC() != null) {
					if(request.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC() != null) {
						if(request.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC().equalsIgnoreCase(indDTO.getPrenom()) && indDTO.getNom() == null) {
							return indDTO;
						}
					} else if (request.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC() != null) {
						if(request.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC().equalsIgnoreCase(indDTO.getNom()) && indDTO.getPrenom() == null) {
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
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String updateProspect(IndividuDTO updateProspectDTO, IndividuDTO findProspectDTO, CreateModifyIndividualResponseDTO responseDTO, CreateUpdateIndividualRequestDTO request) throws JrafDomainException {
		
		List<CommunicationPreferencesDTO> prospectComPrefSet = updateProspectDTO.getCommunicationpreferencesdto();
		communicationPreferencesHelper.crmOptinComPrefs(prospectComPrefSet, responseDTO);
				
		String response = null;
		
		Individu individuBO = individuRepository.findBySgin(findProspectDTO.getSgin());
		
		SignatureDTO signatureAPP = IndividuTransform.transformToSignatureAPP(request.getRequestorDTO());
		
		String comprefMode = "U";
		if (StringUtils.isNotEmpty(request.getUpdateCommunicationPrefMode())) {
			comprefMode = request.getUpdateCommunicationPrefMode();
		}
				
		ReturnDetailsDTO newResponseForWS = myAccountDS.updateCommunicationPreferences(individuBO, prospectComPrefSet, comprefMode, signatureAPP, false, false);
		
		if (StringUtils.isNotEmpty(newResponseForWS.getDetailedCode())) {
			responseDTO.getInformationResponse().add(setReturnDetailsOnAProspectCommon(Integer.parseInt(newResponseForWS.getDetailedCode())));	
		}
			
		findProspectDTO = com.airfrance.repind.dto.individu.IndividuTransform.bo2DtoForUpdate(individuBO);
		
		response = individuDS.updateAnIndividualProspect(updateProspectDTO, findProspectDTO);			
		
		return response;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String updateProspectSharepoint(IndividuDTO updateProspectDTO, IndividuDTO findProspectDTO, CreateModifyIndividualResponseDTO responseDTO, CreateUpdateIndividualRequestDTO request) throws JrafDomainException {

		List<CommunicationPreferencesDTO> prospectComPrefSet = updateProspectDTO.getCommunicationpreferencesdto();
		communicationPreferencesHelper.crmOptinComPrefs(prospectComPrefSet, responseDTO);

		String response = null;

		Individu individuBO = individuRepository.findBySgin(findProspectDTO.getSgin());

		SignatureDTO signatureAPP = IndividuTransform.transformToSignatureAPP(request.getRequestorDTO());

		String comprefMode = "U";
		if (StringUtils.isNotEmpty(request.getUpdateCommunicationPrefMode())) {
			comprefMode = request.getUpdateCommunicationPrefMode();
		}

		ReturnDetailsDTO newResponseForWS = myAccountDS.updateCommunicationPreferencesSharepoint(individuBO, prospectComPrefSet, comprefMode, signatureAPP, false, false);

		if (StringUtils.isNotEmpty(newResponseForWS.getDetailedCode())) {
			responseDTO.getInformationResponse().add(setReturnDetailsOnAProspectCommon(Integer.parseInt(newResponseForWS.getDetailedCode())));
		}

		findProspectDTO = com.airfrance.repind.dto.individu.IndividuTransform.bo2DtoForUpdate(individuBO);

		response = individuDS.updateAnIndividualProspectSharepoint(updateProspectDTO, findProspectDTO);

		return response;
	}

	/**
	 * This method create a new prospect in the DB
	 * @param createProspect
	 * @return createAProspectResponseDTO
	 * @throws JrafDomainException
	 */
	public CreateModifyIndividualResponseDTO createProspect(IndividuDTO createProspect, CreateModifyIndividualResponseDTO responseDTO) throws JrafDomainException {
		
		List<CommunicationPreferencesDTO> prospectComPrefSet = createProspect.getCommunicationpreferencesdto();
		// FIXME : C'est bizarre de retourner un responseDTO et au final de ne pas s en servir car on ne retourne que response
		// TODO : Verifier que la correction est bonne renvoi GIN + Code Erreur
		responseDTO = communicationPreferencesHelper.crmOptinComPrefs(prospectComPrefSet, responseDTO);

		try {
			individuDS.setGenderAndCivility(createProspect);

			if(StringUtils.isBlank(createProspect.getNonFusionnable())) {
				createProspect.setNonFusionnable("N");
			}
			createProspect.setType("W");
			
			preferenceDS.checkProspectPreferencesToCreate(createProspect);
			// REPIND-555 : PROSPECT's migration from SICUTF8 to SIC
			// create prospect
			responseDTO.setGin(individuDS.createAnIndividualProspect(createProspect));
			
		} catch (JrafDomainException e) {
			LOGGER.fatal(e);
			throw new JrafDomainException("Unable to create prospect");
		}
		return responseDTO;
	}
	
	/**
	 * Prospect telecom's update
	 * 
	 * <p>
	 * Telecom's update is not a blocking process. That is the reason why there are no exceptions thrown.
	 * When normalization fails, telecom is not recorded and the web service returns a success response.
	 * </p>
	 */
	public void updateProspectTelecoms(CreateUpdateIndividualRequestDTO request, String sgin) throws JrafDomainException {
		
		final String WEBSERVICE_IDENTIFIER = "W000442";
		final String SITE = "VLB";
		
		if (request != null && request.getTelecomRequestDTO() != null && !request.getTelecomRequestDTO().isEmpty()) {
			try {
				Long gin = Long.valueOf(sgin);
				
				List<TelecomsDTO> prospectTelecomsFromWS = IndividuTransform.transformToProspectTelecomsDTO(request.getTelecomRequestDTO());
				SignatureDTO signatureAPP = IndividuTransform.transformToProspectSignatureAPP(request.getRequestorDTO());
				SignatureDTO signatureWS = IndividuTransform.transformToProspectSignatureWS(WEBSERVICE_IDENTIFIER, SITE);
				
				telecomDS.checkProspectTelecom(prospectTelecomsFromWS);
				List<TelecomsDTO> normalizedProspectTelecomsFromWS = telecomDS.normalizeProspectPhoneNumber(prospectTelecomsFromWS);
				telecomDS.updateProspectNormalizedTelecom(gin, normalizedProspectTelecomsFromWS, signatureAPP, signatureWS);
			} 
			// 133 : MISSING PARAMETER
			catch (MissingParameterException e) {
				LOGGER.error("MISSING PARAMETER: "+e.getMessage());
			}
			// 701 : PHONE NUMBER TOO LONG
			catch (TooLongPhoneNumberException e) {
				LOGGER.error("TELECOM NORMALIZATION ERROR: "+e.getMessage());
			}
			// 702 : PHONE NUMBER TOO SHORT
			catch (TooShortPhoneNumberException e) {
				LOGGER.error("TELECOM NORMALIZATION ERROR: "+e.getMessage());
			}
			// 703 : UNKNOWN
			catch (InvalidPhoneNumberException e) {
				LOGGER.error("TELECOM NORMALIZATION ERROR: "+e.getMessage());
			}
			// 711 : INVALID PHONE COUNTRY CODE
			catch (InvalidCountryCodeException e) {
				LOGGER.error("TELECOM NORMALIZATION ERROR: "+e.getMessage());
			}
			// 705 : NO NORMALIZED
			catch (NormalizedPhoneNumberException e) {
				LOGGER.error("TELECOM NORMALIZATION ERROR: "+e.getMessage());
			}
			// 932 : INVALID PARAMETER
			catch (InvalidParameterException e) {
				LOGGER.error("INVALID PARAMETER: "+e.getMessage());
			}
			// 905 : TECHNICAL ERROR
			catch (Exception e) {
				LOGGER.error("UNEXPECTED ERROR: ",e);
			}
		}
	}
	
	// REPIND-555: Prospect migration to sic from sic-utf8 -> Changes ProspectDTO by IndividuDTO 
	private void updateComPref(IndividuDTO updateProspectDTO, IndividuDTO findProspectDTO, CreateModifyIndividualResponseDTO responseDTO) throws JrafDomainException {
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
    											responseDTO.getInformationResponse().add(setReturnDetailsOnAProspect(7));
    										}
    										if (mlRequest.getOptIn().equalsIgnoreCase("Y")) {
    											// ML deja à "Y"
    											responseDTO.getInformationResponse().add(setReturnDetailsOnAProspect(4));
    										}
    									}
    									else {
    										//mise à jour du marche/langue avec l'input
    										responseDTO.getInformationResponse().add(setReturnDetailsOnAProspect(1));
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
					responseDTO.getInformationResponse().add(setReturnDetailsOnAProspect(7));
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
			}
		}
	}
	
	/**
     * Fonction permettant d'affecter les paramètres de l'objet ReturnDetails en fonction du numéro d'erreur
     */
    private InformationResponseDTO setReturnDetailsOnAProspect(int number){
    	InformationResponseDTO response = new InformationResponseDTO();
    	InformationDTO info = new InformationDTO();
    	
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
    
    /**
     * REPIND-1656: Some codes have been put in common
     * 
     * Fonction permettant d'affecter les paramètres de l'objet ReturnDetails en fonction du numéro d'erreur
     */
    private InformationResponseDTO setReturnDetailsOnAProspectCommon(int number){
    	InformationResponseDTO response = new InformationResponseDTO();
    	InformationDTO info = new InformationDTO();
    	
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

	// REPIND-701 : For delete logically a prospect
	public void deleteProspect(IndividuDTO deleteProspectDTO) throws JrafDomainException {
		
		individuDS.deleteAProspect(deleteProspectDTO);
		
	}
	
	// REPIND-765 : 
	public void updateUtfIndividu(UtfHelper utfHelper, UtfRequestDTO ur, String gin, String signature, String site) throws JrafDomainException {
		
		// Le GIN ne doit pas etre Null ou vide sinon, cela ne sert a rien
		if (gin != null && !"".equals(gin) && ur != null && ur.getUtfDTO() != null && !ur.getUtfDTO().isEmpty()) {
				// Faire un select en base pour retrouver l'ID ! Car nous ne sommes pas en Delete/replace
				List<com.airfrance.repindutf8.dto.utf.UtfDTO> lu = utfHelper.findIndividuByGin(gin);
							
				for (com.airfrance.repindutf8.dto.utf.UtfDTO u : lu) {
					
					// Parcours de la Request
					List <UtfDTO> lutf = ur.getUtfDTO();
					for (UtfDTO utf : lutf) {	
						
						// REPIND-1003 : Repair critical
						// Dans le cas ou on a un UTF du même type et que l'ID est vide dans la Request, on met a jour celle là. 
						if (u.getType().equals(utf.getType()) && (utf.getId() == null || utf.getId() == 0) && u.getUtfId() == null) {
							
							LOGGER.info("Reconcil Update UTF " + u.getUtfId());

							// On affecte l ID du DTO vers la Request pour forcer la mise à jour (Replace)
							utf.setId(u.getUtfId());
						}
					}
				}
		}
	}
	
	// REPIND-765 : Detect first and last name too long in order to store the value on UTF field
	public void checkFirstNameLastNameOverFlow(IndividuDTO updateProspect, CreateUpdateIndividualRequestDTO request) {
		if (updateProspect.getNom() != null && updateProspect.getNom().length() > 35 || 
				updateProspect.getPrenom() != null && updateProspect.getPrenom().length() > 25) {
				
				// We store the full name / first name on UTF8
				UtfRequestDTO ur = new UtfRequestDTO();
				
				UtfDTO u = new UtfDTO();
				u.setType("IND");
				
				utfDataDTO udf = new utfDataDTO();
				udf.setKey("FIRST_NAME");
				udf.setValue(updateProspect.getNom());

				utfDataDTO udl = new utfDataDTO();
				udl.setKey("LAST_NAME");
				udl.setValue(updateProspect.getPrenom());
				
				UtfDatasDTO uds = new UtfDatasDTO();
				uds.setUtfDataDTO(new ArrayList <utfDataDTO>());
				
				uds.getUtfDataDTO().add(udf);
				uds.getUtfDataDTO().add(udl);
				
				u.setUtfDatasDTO(uds);
				
				ur.setUtfDTO(new ArrayList <UtfDTO>());
				ur.getUtfDTO().add(u);

				request.setUtfRequestDTO(ur);
				
				// We reduce the lenght to store as Normalized
				updateProspect.setNom(updateProspect.getNom().replaceAll(" ", ""));	// We delete Space
				updateProspect.setNom(updateProspect.getNom().replaceAll("'", ""));	// We delete cote
				updateProspect.setNom(updateProspect.getNom().replaceAll("-", ""));	// We delete tiret
				
				if (updateProspect.getNom() != null && updateProspect.getNom().length() > 35) {
					updateProspect.setNom(updateProspect.getNom().substring(0, 34));	// We truncate
				}

				updateProspect.setPrenom(updateProspect.getPrenom().replaceAll(" ", ""));
				updateProspect.setPrenom(updateProspect.getPrenom().replaceAll("'", ""));
				updateProspect.setPrenom(updateProspect.getPrenom().replaceAll("-", ""));
				
				if (updateProspect.getPrenom() != null && updateProspect.getPrenom().length() > 25) {
					updateProspect.setPrenom(updateProspect.getPrenom().substring(0, 24));	
				}    						
			}
	}
	
	public IndividuDTO searchByMulticriteria(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {
		
		IndividuDTO indDTO = null;
		SearchIndividualByMulticriteriaResponseDTO responseDTO = null;
		try {
			responseDTO = individuDS.searchIndividual(buildSearchIndividualByMultiCriteriaRequest(request));
		} catch (NotFoundException e) {
			return null;
		}
		
		if(responseDTO != null) {
			indDTO = filterSearchList(request, responseDTO);
		}
		
		if(indDTO != null) {
			return individuDS.getIndividualOrProspectByGin(indDTO.getSgin());
		}
		
		return null;
	}
	
	public IndividuDTO filterSearchList(CreateUpdateIndividualRequestDTO request, SearchIndividualByMulticriteriaResponseDTO response) {
		
		List<IndividuDTO> listIndFoundRelevance100 = new ArrayList<IndividuDTO>();
		List<IndividuDTO> listIndFoundRelevance70 = new ArrayList<IndividuDTO>();
		
		boolean nameEmpty = false;
		
		// Check if lastname or firstname in request is null 
		if(request.getIndividualRequestDTO() != null && request.getIndividualRequestDTO().getIndividualInformationsDTO() != null) {
			if(request.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC() == null || request.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC() == null) {
				nameEmpty = true;
			}
		}

		// Take only relevance 100 or relevance 70 if lastname or firstname in request is null
		for (IndividualMulticriteriaDTO indMultiDTO : response.getIndividuals()) {
			if(indMultiDTO.getRelevance().equals("100")) {
				listIndFoundRelevance100.add(indMultiDTO.getIndividu());
			} else if (indMultiDTO.getRelevance().equals("70") && nameEmpty == true) {
				listIndFoundRelevance70.add(indMultiDTO.getIndividu());
			}
		}
		
		if(listIndFoundRelevance100.size() > 0) {
			if(listIndFoundRelevance100.size() == 1) {
				return listIndFoundRelevance100.get(0);
			} else {
				return filterIndividuals(listIndFoundRelevance100);
			}
		}
		
		if(listIndFoundRelevance70.size() > 0) {
			return filterIndividualByName(request, listIndFoundRelevance70);
		}
		

		return null;
	}
	
	public IndividuDTO filterIndividuals(List<IndividuDTO> listIndFound) {
		
		List<IndividuDTO> newList = new ArrayList<IndividuDTO>();
		
		// Get only FB individuals
		newList = filterIndividualsByContracts(listIndFound, true);
		
		// If there is no FB individuals
		// Get only MA individuals
		if(newList.size() == 0) {
			newList = filterIndividualsByContracts(listIndFound, false);
		}

		// If there is no MA individuals
		// Get only prospect
		if(newList.size() == 0) {
			newList = filterIndividualsByType(listIndFound, true);
		}

		// If there is no prospect
		// Get only traveler
		if(newList.size() == 0) {
			newList = filterIndividualsByType(listIndFound, false);
		}
		
		// If there are more than 1 individuals found
		// We take the most recent
		if(newList.size() > 1) {
			Collections.sort(newList, new Comparator<IndividuDTO>() {
		        @Override
		        public int compare(IndividuDTO i1, IndividuDTO i2) {
		        	if(i1.getDateModification() != null && i2.getDateModification() != null) {
			            return i2.getDateModification().compareTo(i1.getDateModification());
		        	} else {
		        		if(i1.getDateModification() != null && i2.getDateModification() == null) {
		        			return i2.getDateCreation().compareTo(i1.getDateModification());
		        		} else if (i1.getDateModification() == null && i2.getDateModification() != null) {
		        			return i2.getDateModification().compareTo(i1.getDateCreation());
		        		} else {
		        			return i2.getDateCreation().compareTo(i1.getDateCreation());
		        		}
		        	}
		        }
		    });
			
			return newList.get(0);
			
		} else if (newList.size() == 1) {
			
			return newList.get(0);
			
		} else {
			
			return null;
			
		}
		
	}
	
	private boolean hasValidContract(IndividuDTO individu, String contractName) {
		
		if(individu.getRolecontratsdto() != null && individu.getRolecontratsdto().size() > 0) {
			Iterator<RoleContratsDTO> contractIterator = individu.getRolecontratsdto().iterator();
			
			while (contractIterator.hasNext()) {
				RoleContratsDTO contract = contractIterator.next();
				if (contract.getTypeContrat().equalsIgnoreCase(contractName) && (contract.getEtat().equalsIgnoreCase("C") || contract.getEtat().equalsIgnoreCase("P"))) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public List<IndividuDTO> filterIndividualsByType(List<IndividuDTO> listIndFound, boolean isProspect) {
				
		List<IndividuDTO> indList = new ArrayList<IndividuDTO>();
		
		for (IndividuDTO individuDTO : listIndFound) {
			if(isProspect) {
				if(individuDTO.getType().equals("W")) {
					indList.add(individuDTO);
				}
			} else {

				if(individuDTO.getType().equals("T")) {
					indList.add(individuDTO);
				}
			}
		}
		
		return indList;
		
	}
	
	public List<IndividuDTO> filterIndividualsByContracts(List<IndividuDTO> listIndFound, boolean fbContract) {
				
		List<IndividuDTO> indList = new ArrayList<IndividuDTO>();
		
		for (IndividuDTO individuDTO : listIndFound) {
			if(fbContract) {
				if(hasValidContract(individuDTO, "FP")) {
					indList.add(individuDTO);
				}
			} else {

				if(hasValidContract(individuDTO, "MA")) {
					indList.add(individuDTO);
				}
			}
		}
		
		return indList;
		
	}

	public SearchIndividualByMulticriteriaRequestDTO buildSearchIndividualByMultiCriteriaRequest(CreateUpdateIndividualRequestDTO request){
		
		SearchIndividualByMulticriteriaRequestDTO requestIndividual = null;
		
		if(request != null)
		{
			// Recuperation des infos dans la request
			
			/*
			 * Request initialization
			 */
			requestIndividual = new SearchIndividualByMulticriteriaRequestDTO();
			requestIndividual.setPopulationTargeted("A");
			
			/*
			 * Process type
			 */
			requestIndividual.setProcessType("A");

			/*
			 * Requestor
			 */
			RequestorDTO requestor = new RequestorDTO();
			
			if(request.getRequestorDTO() != null && StringUtils.isNotBlank(request.getRequestorDTO().getApplicationCode())) {
				requestor.setApplicationCode(request.getRequestorDTO().getApplicationCode());
			} else {
				requestor.setApplicationCode("REPIND");
			}
			
			requestIndividual.setRequestor(requestor);
			
			/*
			 * Contact
			 */
			ContactDTO contact = new ContactDTO();
			if((request.getEmailRequestDTO() != null)
					&&	(request.getEmailRequestDTO().get(0) != null)
					&&	(request.getEmailRequestDTO().get(0).getEmailDTO() != null)
					&&	(request.getEmailRequestDTO().get(0).getEmailDTO().getEmail().replace(" ", "").length() > 0)
					&&	(Utils.isEmailCompliant(request.getEmailRequestDTO().get(0).getEmailDTO().getEmail())))
			{
				contact.setEmail(request.getEmailRequestDTO().get(0).getEmailDTO().getEmail());
			}
			
			requestIndividual.setContact(contact);
			
			/*
			 * Identity
			 */
			IdentityDTO identity = new IdentityDTO();
			if((request.getIndividualRequestDTO() != null)
					&&	(request.getIndividualRequestDTO().getIndividualInformationsDTO() != null)
					&&	(request.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC() != null)
					&&	(request.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC().replace(" ", "").length() > 0)
					&&	(Utils.isAlphaNumeric(request.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC())))
			{
				identity.setFirstName(request.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC());
				identity.setFirstNameSearchType("S");
			}

			if((request.getIndividualRequestDTO() != null)
					&&	(request.getIndividualRequestDTO().getIndividualInformationsDTO() != null)
					&&	(request.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC() != null)
					&&	(request.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC().replace(" ", "").length() > 0)
					&&	(Utils.isAlphaNumeric(request.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC())))
			{
				identity.setLastName(request.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC());
				identity.setLastNameSearchType("S");
			}
			
			if(identity.getFirstName() == null || identity.getLastName() == null) {
				identity.setFirstName(null);
				identity.setFirstNameSearchType(null);
				identity.setLastName(null);
				identity.setLastNameSearchType(null);
			}
			
			if((request.getIndividualRequestDTO() != null)
					&&	(request.getIndividualRequestDTO().getIndividualInformationsDTO() != null)
					&&	(request.getIndividualRequestDTO().getIndividualInformationsDTO().getBirthDate() != null))
			{
				identity.setBirthday(request.getIndividualRequestDTO().getIndividualInformationsDTO().getBirthDate());
			}
			requestIndividual.setIdentity(identity);
			
		}
		
		return requestIndividual;
		
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public Individu individuBeforeUpdate(String gin) {
		return individuRepository.findBySgin(gin);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void individuAfterUpdate(String gin, Date birthDate, String civility) {
		int inc = 0;
		Date now = new Date();

		List<RoleContrats> roleContratsList = roleContratsRepository.findByGin(gin);

		//Retrieve valid contracts and increment if found
		for (RoleContrats role : roleContratsList){
			if (role.getEtat().equals("C") && (role.getDateFinValidite() == null || role.getDateFinValidite().after(now)))
				inc++;
		}

		//Set original data stored in RI if valid contracts found
		if(inc != 0){
			Individu individu = individuRepository.findBySgin(gin);
			individu.setDateNaissance(birthDate);
			individu.setCivilite(civility);
			individuRepository.saveAndFlush(individu);
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void individuAfterUpdateSharepoint(String gin, Date birthDate, String civility, String nom, String prenom, String gender,
											  Date modificationDate, String modificationSignature, String modificationSite) {
		int inc = 0;
		Date now = new Date();

		List<RoleContrats> roleContratsList = roleContratsRepository.findByGin(gin);

		//Retrieve valid contracts and increment if found
		for (RoleContrats role : roleContratsList){
			if (role.getEtat().equals("C") && (role.getDateFinValidite() == null || role.getDateFinValidite().after(now)))
				inc++;
		}

		//Set original data stored in RI if valid contracts found
		if(inc != 0){
			Individu individu = individuRepository.findBySgin(gin);
			individu.setDateNaissance(birthDate);
			individu.setCivilite(civility);
			individu.setSexe(gender);
			individu.setNom(nom);
			individu.setPrenom(prenom);
			individu.setDateModification(modificationDate);
			individu.setSignatureModification(modificationSignature);
			individu.setSiteModification(modificationSite);
			individuRepository.saveAndFlush(individu);
		}
	}
}
