package com.afklm.repind.v4.provideindividualdata;

import com.afklm.repind.helpers.ProvideIndividualDataHelper;
import com.afklm.repind.v4.provideindividualdata.helpers.BusinessExceptionHelper;
import com.afklm.repind.v4.provideindividualdata.helpers.MarketingDataHelper;
import com.afklm.repind.v4.provideindividualdata.helpers.PaymentPreferencesDataHelper;
import com.afklm.repind.v4.provideindividualdata.transformers.ProvideIndividualDataTransformV4;
import com.afklm.repind.v4.provideindividualdata.type.MaskedPaymentPreferences;
import com.afklm.repind.v4.provideindividualdata.type.ScopeEnum;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v4.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v4.ProvideIndividualDataServiceV4;
import com.afklm.soa.stubs.w000418.v4.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v4.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v4.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000418.v4.response.DelegationDataResponse;
import com.afklm.soa.stubs.w000418.v4.siccommontype.Requestor;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.DelegationTypeEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.adh.individualinformation.IndividualInformationRequestDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.*;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.util.GaugeUtils;
import com.airfrance.repind.util.SicStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>Title : ProvideIndividualDataImpl.java</p>
 * 
 * <p>Copyright : Copyright (c) 2015</p>
 * <p>Company : AIRFRANCE</p>
 * 
 * @author t449753
 */
@Service("passenger_ProvideIndividualData-v04Bean")
@WebService(endpointInterface="com.afklm.soa.stubs.w000418.v4.ProvideIndividualDataServiceV4", targetNamespace = "http://www.af-klm.com/services/passenger/ProvideIndividualDataService-v4/wsdl", serviceName = "ProvideIndividualDataServiceService-v4", portName = "ProvideIndividualDataService-v4-soap11http")
@Slf4j
public class ProvideIndividualDataV4Impl implements ProvideIndividualDataServiceV4 {

	@Autowired
	protected MyAccountDS myAccountDS;

	@Autowired
	protected PrefilledNumbersDS prefilledNumbersDS;
	
	@Autowired
	protected AccountDataDS accountDataDS;
	
	@Autowired
	protected TelecomDS telecomDS;
	
	@Autowired
	protected DelegationDataDS delegationDataDS;
	
	@Autowired
	protected ExternalIdentifierDS externalIdentifierDS;
	
	@Autowired
	protected CommunicationPreferencesDS communicationPreferencesDS;
	
	@Autowired
	protected RoleDS roleDS;
	
	@Autowired
	protected EmailDS emailDS;
	
	@Autowired
	protected PostalAddressDS postalAddressDS;
	
	@Autowired
	protected BusinessExceptionHelper businessExceptionHelperV4;

	@Autowired
	protected MarketingDataHelper marketingDataHelperV4;

	@Autowired
	protected PaymentPreferencesDataHelper paymentPreferencesDataHelperV4;

	@Autowired
	protected PreferenceDS preferenceDS;
	
	@Autowired
	protected IndividuDS individuDS;
	
	@Resource
	private WebServiceContext context;


	public ProvideIndividualInformationResponse provideIndividualDataByIdentifier(ProvideIndividualInformationRequest request) throws BusinessErrorBlocBusinessException, SystemException {
		
		// REPIND-1441 : Trace who are consuming us and for what 
		TraceInput(request);
		
		ProvideIndividualInformationResponse response = null;
		boolean isFBRecognitionActivate = false;
				
		try {

			String gin = null;
			IndividuDTO individuDTO = null;
			AccountDataDTO accountDataDTO = null;
			List<TelecomsDTO> telecomsDTOList = null;
			
			checkRequest(request);
			
			IndividualInformationRequestDTO requestDTO = new IndividualInformationRequestDTO();
			
			// REPIND-1546 : NPE on SONAR
			if (request != null) {
				requestDTO = new IndividualInformationRequestDTO(request.getIdentificationNumber(), request.getOption());
			}
			
			// get scope
			ScopeEnum scope = ScopeEnum.getEnum(getScope(request));
			
			int percentageMarketingData = 0;
			int percentagePaymentPreferences = 0;
			
			/* ==================================================================================== *
			 * INDIVIDUAL DATA																		*
			 * ==================================================================================== */
			
			if(scope==ScopeEnum.ALL_DATA || scope==ScopeEnum.INDIVIDUAL_DATA || scope==ScopeEnum.INDIVIDUAL_AND_FB_DATA || scope==ScopeEnum.INDIVIDUAL_AND_MARKETING_DATA) {
				
				individuDTO = myAccountDS.searchIndividualInformation(requestDTO);
				if(individuDS.isIndividualNotProvide(individuDTO))
					throw new NotFoundException("");
				
				response = ProvideIndividualDataTransformV4.transformToProvideIndividualInformationResponse(individuDTO);
		
				checkResponse(response);
				
				// get gin
				gin = individuDTO.getSgin();

				// add postal address
				List<PostalAddressDTO> postalAddressDTOList = postalAddressDS.findPostalAddress(gin);
				ProvideIndividualDataTransformV4.transformToPostalAddressResponse(postalAddressDTOList, response.getPostalAddressResponse());
				
				// add mails
				List<EmailDTO> emailDTOList = emailDS.findEmail(gin);
				ProvideIndividualDataTransformV4.transformToEmailResponse(emailDTOList, response.getEmailResponse());
								
				// add role contracts
				List<RoleContratsDTO> roleContratsDTOList = roleDS.findRoleContrats(gin, isFBRecognitionActivate);
				ProvideIndividualDataTransformV4.transformToContractResponse(roleContratsDTOList, response.getContractResponse(), isFBRecognitionActivate);
				
				// add account data
				accountDataDTO = accountDataDS.getByGin(gin);
				response.setAccountDataResponse(ProvideIndividualDataTransformV4.transformToAccountDataResponse(accountDataDTO));

				// add telecom data with usage code management 
				telecomsDTOList = telecomDS.findLatestByUsageCode(gin);
				ProvideIndividualDataTransformV4.transformToTelecomResponse(telecomsDTOList, response.getTelecomResponse());
			
				// add delegation data
				List<DelegationDataDTO> delegatorList = delegationDataDS.findDelegator(gin);
				List<DelegationDataDTO> delegateList = delegationDataDS.findDelegate(gin);
				
				/*
				 * Specific code for KID SOLO
				 * We must not provide kid solo delegations for this version
				 * To be removed once we want those delegations
				 * */
				List<DelegationTypeEnum> delegeationTypesToRemove = new ArrayList<DelegationTypeEnum>(Arrays.asList(DelegationTypeEnum.UNACOMPAGNED_MINOR, DelegationTypeEnum.UNACOMPAGNED_MINOR_ATTENDANT));
				ProvideIndividualDataHelper.deleteDelegationFromReponseByTypesDelegation(delegateList, delegeationTypesToRemove);
				ProvideIndividualDataHelper.deleteDelegationFromReponseByTypesDelegation(delegatorList, delegeationTypesToRemove);
				
				DelegationDataResponse delegationDataResponse = ProvideIndividualDataTransformV4.dtoTOws(delegatorList, delegateList);
				response.setDelegationDataResponse(delegationDataResponse);
				
				// add prefilled numbers
				List<PrefilledNumbersDTO> prefilledNumbersDTOList = prefilledNumbersDS.findPrefilledNumbers(gin);
				ProvideIndividualDataTransformV4.transformToPrefilledNumbers(prefilledNumbersDTOList, response.getPrefilledNumbersResponse());
				
				// add external identifiers
				List<ExternalIdentifierDTO> externalIdentifierDTOList = externalIdentifierDS.findExternalIdentifier(gin);
				ProvideIndividualDataTransformV4.transformToExternalIdentifierResponse(externalIdentifierDTOList, response.getExternalIdentifierResponse());
			
				// add communication preferences
				List<CommunicationPreferencesDTO> communicationPreferencesDTOList = communicationPreferencesDS.findCommunicationPreferences(gin);
				ProvideIndividualDataTransformV4.transformToCommunicationPreferencesResponse(communicationPreferencesDTOList, response.getCommunicationPreferencesResponse());
				
				// add warnings
				Set<WarningDTO> warningDTOSet = myAccountDS.checkValidAccount(gin);
				ProvideIndividualDataTransformV4.transformToWarningResponse(warningDTOSet, response.getWarningResponse());
				
			}
			
			/* ==================================================================================== *
			 * MARKETING DATA																		*
			 * ==================================================================================== */
			
			if(scope==ScopeEnum.ALL_DATA || scope==ScopeEnum.INDIVIDUAL_AND_MARKETING_DATA) {
				PreferenceDTO preferenceDTO = new PreferenceDTO();
				preferenceDTO.setGin(gin);
				List<PreferenceDTO> preferenceDTOList = preferenceDS.findByExample(preferenceDTO);
				
				// Normalize all preference data key
				List<PreferenceDTO> normalizedPrefDtoList = new ArrayList<>();
				if (preferenceDTOList != null) {
					for (PreferenceDTO prefDtoFromDB : preferenceDTOList) {
						PreferenceDTO normalizedPrefDto = marketingDataHelperV4.normalizePreferenceDataKey(prefDtoFromDB);
						normalizedPrefDtoList.add(normalizedPrefDto);
					}
				}
				
				//Transform TDC and APC to MarketingResponse
				ProvideIndividualDataTransformV4.transformPreferenceDTOListToResponse(normalizedPrefDtoList, response);
				
				percentageMarketingData = marketingDataHelperV4.calculateMarketingInformationGauge(response);

			}
			
			/* ==================================================================================== *
			 * PAYMENT PREFERENCES																	*
			 * ==================================================================================== */
			//REPIND-1230: In case of exception, we catch it in order to provide a valid answer
			if (scope==ScopeEnum.ALL_DATA) {
				try {
					MaskedPaymentPreferences paymentPreferences = paymentPreferencesDataHelperV4.provideMaskedPaymentPreferences(gin);
					percentagePaymentPreferences = paymentPreferencesDataHelperV4.computePaymentPreferencesGauge(paymentPreferences);
				} catch (JrafDomainException e) {
					ProvideIndividualDataV4Impl.log.warn("Unable to get PaymentPreferences for the GIN: " + gin);
				}
			}
			
			// compute gauge
			if(response.getAccountDataResponse()!=null && response.getAccountDataResponse().getAccountData()!=null) {
				int percentageFullProfil = GaugeUtils.calculPercentageFullProfil(individuDTO, accountDataDTO, individuDTO.getPostaladdressdto(), telecomsDTOList, percentageMarketingData, percentagePaymentPreferences);
				response.getAccountDataResponse().getAccountData().setPercentageFullProfil(percentageFullProfil);
			}
			
		}
		// ERROR 001 : NOT FOUND
		catch(NotFoundException e) {
			// REPIND-255 : Suppression du log ERROR car c'est un cas normal
			log.info("provideIndividualDataByIdentifier : {}",e.getMessage());
			businessExceptionHelperV4.throwBusinessException(BusinessErrorCodeEnum.ERROR_001, e.getMessage());
		}
		// ERROR 133 : MISSING PARAMETER
		catch(MissingParameterException e) {
			log.error("provideIndividualDataByIdentifier ERROR", e);
			businessExceptionHelperV4.throwBusinessException(BusinessErrorCodeEnum.ERROR_133, e.getMessage());
		}
		// ERROR 932 : INVALID PARAMETER
		catch(InvalidParameterException e) {
			log.error("provideIndividualDataByIdentifier ERROR", e);
			businessExceptionHelperV4.throwBusinessException(BusinessErrorCodeEnum.ERROR_932, e.getMessage());
		}
		// ERROR 905 : TECHNICAL ERROR
		catch (JrafDomainException e) {
			log.error("provideIndividualDataByIdentifier ERROR", e);
			businessExceptionHelperV4.throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
		}

		return response;
	}
	
	protected void checkRequest(ProvideIndividualInformationRequest request) throws MissingParameterException {
		
		if(StringUtils.isEmpty(request.getIdentificationNumber())) {
			throw new MissingParameterException("Identification number is mandatory");
		}
	
		if(StringUtils.isEmpty(request.getOption())) {
			throw new MissingParameterException("Option is mandatory");
		}
		
	}
	
	protected void checkResponse(ProvideIndividualInformationResponse response) throws NotFoundException {
		
		if(response==null || response.getIndividualResponse()==null || response.getIndividualResponse().getIndividualInformations()==null) {
			throw new NotFoundException("Unable to find individual");
		}
		
	}
	
	private String getScope(ProvideIndividualInformationRequest request) {
		
		if(request==null) {
			return null;
		}
		
		Requestor requestor = request.getRequestor();
		
		if(requestor==null) {
			return null;
		}
		
		return requestor.getScope();
		
	}	
	
	public PaymentPreferencesDataHelper getPaymentPreferencesDataHelperV4() {
		return paymentPreferencesDataHelperV4;
	}


	public void setPaymentPreferencesDataHelperV4(PaymentPreferencesDataHelper paymentPreferencesDataHelperV4) {
		this.paymentPreferencesDataHelperV4 = paymentPreferencesDataHelperV4;
	}

	// REPIND-1441 : Trace who are consuming us and for what 
	private void TraceInput(ProvideIndividualInformationRequest request) {
		
		String retour = "W000418V4; ";

		// CONSUMER ID
		retour += SicStringUtils.TraceInputConsumer(context);

		// SITE + SIGNATURE + APPLICATION CODE
		if (request != null) {
			
			if (request.getRequestor() != null) {
				
				retour += SicStringUtils.TraceInputRequestor(
						request.getRequestor().getChannel(), request.getRequestor().getMatricule(), request.getRequestor().getOfficeId(),
						request.getRequestor().getSite(), request.getRequestor().getSignature(), request.getRequestor().getApplicationCode());
			}
			
			// SCOPE TO PROVIDE
			retour += SicStringUtils.TraceInputScope(request.getRequestor().getScope());
			
			// INPUT
			retour += SicStringUtils.TraceInputOptionIdentificationNumber(request.getOption(), request.getIdentificationNumber());
		}
		
		log.info(retour);
	}
}
