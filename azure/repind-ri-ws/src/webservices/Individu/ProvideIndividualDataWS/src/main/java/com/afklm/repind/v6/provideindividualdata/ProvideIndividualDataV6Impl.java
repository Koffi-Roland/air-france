package com.afklm.repind.v6.provideindividualdata;

import com.afklm.repind.helpers.ProvideIndividualDataHelper;
import com.afklm.repind.v6.provideindividualdata.helpers.BusinessExceptionHelper;
import com.afklm.repind.v6.provideindividualdata.helpers.MarketingDataHelper;
import com.afklm.repind.v6.provideindividualdata.helpers.PaymentPreferencesDataHelper;
import com.afklm.repind.v6.provideindividualdata.transformers.ProvideIndividualDataTransformV6;
import com.afklm.repind.v6.provideindividualdata.type.MaskedPaymentPreferences;
import com.afklm.repind.v6.provideindividualdata.type.PopulationTargetedEnum;
import com.afklm.repind.v6.provideindividualdata.type.ScopesToProvideEnum;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v6.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v6.ProvideIndividualDataServiceV6;
import com.afklm.soa.stubs.w000418.v6.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v6.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v6.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000418.v6.response.DelegationDataResponse;
import com.afklm.soa.stubs.w000418.v6.response.PostalAddressResponse;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.BussinessRoleTypeEnum;
import com.airfrance.ref.type.DelegationTypeEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.channel.ChannelToCheckDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.adh.individualinformation.IndividualInformationRequestDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.role.RoleTravelersDTO;
import com.airfrance.repind.dto.role.RoleUCCRDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.channel.internal.ChannelToCheckDS;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.*;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.service.role.internal.RoleTravelersDS;
import com.airfrance.repind.service.role.internal.RoleUCCRDS;
import com.airfrance.repind.util.GaugeUtils;
import com.airfrance.repind.util.SicStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
 * @author T412211
 */
@Service("passenger_ProvideIndividualData-v06Bean")
@WebService(endpointInterface = "com.afklm.soa.stubs.w000418.v6.ProvideIndividualDataServiceV6", targetNamespace = "http://www.af-klm.com/services/passenger/ProvideIndividualDataService-v6/wsdl", serviceName = "ProvideIndividualDataServiceService-v6", portName = "ProvideIndividualDataService-v6-soap11http")
@Slf4j
public class ProvideIndividualDataV6Impl implements ProvideIndividualDataServiceV6 {

    @Autowired
    protected MyAccountDS myAccountDS;

    // REPIND-555 : Deprecated
    // @Autowired
    // protected IProspectDS prospectDS;

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
    protected RoleUCCRDS roleUCCR;

    @Autowired
    protected RoleTravelersDS roleTravelersDS;

    @Autowired
    protected EmailDS emailDS;

    @Autowired
    protected PostalAddressDS postalAddressDS;

    @Autowired
    protected ChannelToCheckDS channelToCheckDS;

    @Autowired
    protected PreferenceDS preferenceDS;

    @Autowired
    protected AlertDS alertDS;

    @Autowired
    protected IndividuDS individuDS;

    @Autowired
    protected BusinessRoleDS businessRoleDS;

    @Autowired
    protected BusinessExceptionHelper businessExceptionHelperV5;

    @Autowired
    protected MarketingDataHelper marketingDataHelperV6;

    @Autowired
    protected PaymentPreferencesDataHelper paymentPreferencesDataHelperV5;

    @Resource
    private WebServiceContext context;

    public final static String NOT_COLLECTED = "NOT COLLECTED";
    public final static String POSTAL_CODE_9999 = "99999";

    @Override
    public ProvideIndividualInformationResponse provideIndividualDataByIdentifier(ProvideIndividualInformationRequest request) throws BusinessErrorBlocBusinessException, SystemException {

        // REPIND-1441 : Trace who are consumming and what
        TraceInput(request);

        ProvideIndividualInformationResponse response = null;

        try {

            String gin = null;
            IndividuDTO individuDTO = null;
            AccountDataDTO accountDataDTO = null;
            List<TelecomsDTO> telecomsDTOList = null;
            Boolean isIndividu = true;
            Boolean isProspect = false;

            boolean haveCommunicationPreferences = false;
            boolean haveRoleTravelers = false;
            boolean haveExternalIdentifier = false;

            checkRequest(request);

            IndividualInformationRequestDTO requestDTO = new IndividualInformationRequestDTO();

            // REPIND-1546 : NPE on SONAR
            if (request != null) {
                requestDTO = new IndividualInformationRequestDTO(request.getIdentificationNumber(), request.getOption()); // , request.getPopulationTargeted());
            }

            // get scopeToProvide
            Set<ScopesToProvideEnum> scopesToProvideSet = ScopesToProvideEnum.getEnumSet(request.getScopeToProvide());

			/*			if(scopesToProvideSet == null && getScope(request) != null) {
				throw new InvalidParameterException("scope is not used anymore, please use scopeToProvide");
			}
			 */
            // avtivate scopes for Individual if requested
            activateIndividualScopes(scopesToProvideSet);

            int percentageMarketingData = 0;
            int percentagePaymentPreferences = 0;

            /* ==================================================================================== *
             * INDIVIDUAL DATA																		*
             * ==================================================================================== */
            // Search an Individual in SIC
            individuDTO = myAccountDS.searchIndividualInformationAll(requestDTO);
            if (individuDS.isIndividualNotProvide(individuDTO))
                throw new NotFoundException("");

            // REPIND-555 : Prospect will be find on INDIVIDUS_ALL
/*			// If not found, search in prospect SIC_UTF8
			if(individuDTO == null) {
				// Check the population targeted to search in Prospect (A for ALL or W for WhiteWinger)
				if ("A".equals(requestDTO.getPopulationTargeted()) || "W".equals(requestDTO.getPopulationTargeted())) {
					prospectDTO = getProspectDTO(requestDTO);

					// Do not return a deleted Prospect
					if (!"X".equalsIgnoreCase(prospectDTO.getStatus())) {
						individuDTO = ProvideIndividualDataTransformV6.transformToIndividuDTO(prospectDTO);
						isIndividu = false;
					}
				}
			}
*/
            response = ProvideIndividualDataTransformV6.transformToProvideIndividualInformationResponse(individuDTO, scopesToProvideSet, isIndividu);

            checkResponse(response);

            // get gin
            gin = individuDTO.getSgin();

            // isProspect only if individual is WhiteWinger
            isProspect = individuDTO == null ? false : "W".equals(individuDTO.getType());
            ////////////////////////////////////////////////////////////////
            // We have to get this attributes to determine populationType //
            ////////////////////////////////////////////////////////////////

            // Traveler
            List<RoleTravelersDTO> roleTravelersDTOList = roleTravelersDS.findRoleTravelers(gin);
            haveRoleTravelers = roleTravelersDTOList != null && roleTravelersDTOList.size() > 0;

            // Communication Preferences
            List<CommunicationPreferencesDTO> communicationPreferencesDTOList = communicationPreferencesDS.findCommunicationPreferences(gin);
            if (individuDTO.getCommunicationpreferencesdto() != null) {
                communicationPreferencesDTOList = new ArrayList<>(individuDTO.getCommunicationpreferencesdto());
            }
            haveCommunicationPreferences = communicationPreferencesDTOList != null && communicationPreferencesDTOList.size() > 0;

            // External Identifier : Request PNM_ID and GIGYA or FACEBOOK or TWITTER
            List<ExternalIdentifierDTO> externalIdentifierDTOList = externalIdentifierDS.findExternalIdentifierALL(gin);
            haveExternalIdentifier = externalIdentifierDTOList != null && externalIdentifierDTOList.size() > 0;

            ////////////////////////////////////////////////////////////////


            if (isScopesToProvideRequested(scopesToProvideSet)) {
                // add postal address
                if (!isProspect && isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.POSTAL_ADDRESS))) {
                    List<PostalAddressDTO> postalAddressDTOList = postalAddressDS.findPostalAddress(gin);
                    ProvideIndividualDataTransformV6.transformToPostalAddressResponse(postalAddressDTOList, response.getPostalAddressResponse());
                    whiteningPostalAddressContent(request, response);
                }

                // REPIND-555: Prospect migration
//				// add localization data
                if (isProspect && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.LOCALIZATION))) {
                    List<PostalAddressDTO> postalAddressDTOList = postalAddressDS.findPostalAddress(gin);
                    ProvideIndividualDataTransformV6.transformToLocalizationResponse(postalAddressDTOList, response.getLocalizationResponse());
                    // ProvideIndividualDataTransformV6.transformToLocalizationResponse(prospectDTO.getProspectLocalizationDTO(), response.getLocalizationResponse());
                }
                // add preference data and marketing
                if (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.PREFERENCE) || scopesToProvideSet.contains(ScopesToProvideEnum.MARKETING)) {
                    PreferenceDTO preferenceDTO = new PreferenceDTO();
                    preferenceDTO.setGin(gin);
                    List<PreferenceDTO> preferenceDTOList = preferenceDS.findByExample(preferenceDTO);
                    preferenceDTOList = marketingDataHelperV6.cleanNonPrintableChars(preferenceDTOList);

                    /*
                     * TODO Check if is necessary
                     */
                    // REPIND-555: Prospect migration
//					if(isProspect)  {
//						ProvideIndividualDataTransformV6.transformToPreferencesResponse(individuDTO.getPreferenceDTO(), response);

                    // Normalize all preference data key
                    List<PreferenceDTO> normalizedPrefDtoList = new ArrayList<>();
                    if (preferenceDTOList != null) {
                        for (PreferenceDTO prefDtoFromDB : preferenceDTOList) {
                            PreferenceDTO normalizedPrefDto = marketingDataHelperV6.normalizePreferenceDataKey(prefDtoFromDB);
                            normalizedPrefDtoList.add(normalizedPrefDto);
                        }
                    }

                    //Transform TDC and APC to MarketingResponse
                    //Transform TPC (WWP) to PreferenceResponse
                    ProvideIndividualDataTransformV6.transformPreferenceDTOListToResponse(normalizedPrefDtoList, response);

                    // REPIND-854 : In case of Prospect, we do not want marketingData
                    // if("W".equals(individuDTO.getType()))  {
                    if (isProspect) {
                        response.setMarketingDataResponse(null);
                    }

                    //Should be not used anymore after migrating of Prospects from UTF8 to SIC
//					if(!isIndividu)  {
//						ProvideIndividualDataTransformV6.transformToPreferencesResponse(prospectDTO.getProspectLocalizationDTO(), response);
//					}
                }
                // add mails
                if (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.EMAIL)) {
                    List<EmailDTO> emailDTOList = null;
                    if (isIndividu) {
                        emailDTOList = emailDS.findEmail(gin);
                    } else if (individuDTO.getEmaildto() != null) {
                        emailDTOList = new ArrayList<>(individuDTO.getEmaildto());
                    }
                    ProvideIndividualDataTransformV6.transformToEmailResponse(emailDTOList, response.getEmailResponse());
                }
                // add role contracts
                if (isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.CONTRACT))) {
                    List<RoleContratsDTO> roleContratsDTOList = roleDS.findRoleContrats(gin, true);
                    ProvideIndividualDataTransformV6.transformToContractResponse(roleContratsDTOList, response.getContractResponse(), true);
                }
                // add UCCR contracts
                if (isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.UCCR_CONTRACT))) {
                    List<BusinessRoleDTO> businessRoleUccrList = businessRoleDS.findByGinAndType(gin, BussinessRoleTypeEnum.ROLE_UCCR.code());
                    if (CollectionUtils.isNotEmpty(businessRoleUccrList)) {
                        List<RoleUCCRDTO> roleUCCRDTOList = roleUCCR.getRoleUccrFromBusinessRole(businessRoleUccrList);
                        ProvideIndividualDataTransformV6.transformRoleUCCRToContractResponse(roleUCCRDTOList, response.getContractResponse());
                    }
                }
                // add account data
                if (isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.ACCOUNT))) {
                    accountDataDTO = accountDataDS.getOnlyAccountByGin(gin);
                    response.setAccountDataResponse(ProvideIndividualDataTransformV6.transformToAccountDataResponse(accountDataDTO));
                }
                // add social data
                // Do we need to query this fields (Scope or Target)
                if (isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) ||
                        scopesToProvideSet.contains(ScopesToProvideEnum.SOCIAL_NETWORK) ||
                        PopulationTargetedEnum.E.toString().equals(requestDTO.getPopulationTargeted()))) {
                    // List<SocialNetworkDataTypeDTO> socialNetworkDataTypeDTO = socialNetworkDS.findSocialNetworkData(gin);
                    // haveSocialNetworkData = socialNetworkDataTypeDTO != null && socialNetworkDataTypeDTO.size() > 0;
                    // Do we need to return the result
                }
                // add telecom data with usage code management
                if (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.TELECOM)) {
                    if (isIndividu) {
                        telecomsDTOList = telecomDS.findLatestByUsageCode(gin);
                    } else if (individuDTO.getTelecoms() != null) {
                        telecomsDTOList = new ArrayList<>(individuDTO.getTelecoms());
                    }
                    ProvideIndividualDataTransformV6.transformToTelecomResponse(telecomsDTOList, response.getTelecomResponse());
                }
                // add delegation data
                if (isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.DELEGATION))) {
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

                    DelegationDataResponse delegationDataResponse = ProvideIndividualDataTransformV6.dtoTOws(delegatorList, delegateList);
                    response.setDelegationDataResponse(delegationDataResponse);
                }
                // add prefilled numbers
                if (isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.PREFILLED_NUMBER))) {
                    List<PrefilledNumbersDTO> prefilledNumbersDTOList = prefilledNumbersDS.findPrefilledNumbers(gin);
                    ProvideIndividualDataTransformV6.transformToPrefilledNumbers(prefilledNumbersDTOList, response.getPrefilledNumbersResponse());
                }
                // add external identifiers
                if (isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) ||
                        scopesToProvideSet.contains(ScopesToProvideEnum.EXTERNAL_IDENTIFIER) ||
                        PopulationTargetedEnum.E.toString().equals(requestDTO.getPopulationTargeted()))) {
                    // List<ExternalIdentifierDTO> externalIdentifierDTOList = externalIdentifierDS.findExternalIdentifier(gin);
                    // haveExternalIdentifier = externalIdentifierDTOList != null && externalIdentifierDTOList.size() > 0;
                    // Do we need to return the result
                    if (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) ||
                            scopesToProvideSet.contains(ScopesToProvideEnum.EXTERNAL_IDENTIFIER)) {
                        ProvideIndividualDataTransformV6.transformToExternalIdentifierResponse(externalIdentifierDTOList, response.getExternalIdentifierResponse());
                    }
                }
                // add communication preferences
                if (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.COMMUNICATIION_PREFERENCE)) {
                    // List<CommunicationPreferencesDTO> communicationPreferencesDTOList = null;
                    if (isIndividu) {
                        // communicationPreferencesDTOList = communicationPreferencesDS.findCommunicationPreferences(gin);
                    } else if (individuDTO.getCommunicationpreferencesdto() != null) {
                        // communicationPreferencesDTOList = new ArrayList<CommunicationPreferencesDTO>(individuDTO.getCommunicationpreferencesdto());
                    }
                    ProvideIndividualDataTransformV6.transformToCommunicationPreferencesResponse(communicationPreferencesDTOList, response.getCommunicationPreferencesResponse());
                }

                // add traveler data
                // Do we need to query this fields (Scope or Target)
                if ((scopesToProvideSet.contains(ScopesToProvideEnum.ALL) ||
                        scopesToProvideSet.contains(ScopesToProvideEnum.TRAVELER)) &&
                        ("A".equals(requestDTO.getPopulationTargeted()) ||
                                (PopulationTargetedEnum.T.toString()).equals(requestDTO.getPopulationTargeted()))) {
                    // List<RoleTravelersDTO> roleTravelersDTOList = roleTravelersDS.findRoleTravelers(gin);
                    // haveRoleTravelers = roleTravelersDTOList != null && roleTravelersDTOList.size() > 0;
                    // Do we need to return the result
                    ProvideIndividualDataTransformV6.transformToTravelerResponse(roleTravelersDTOList, response.getContractResponse());
                }

                // add alert data informations
                if (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.ALERT)) {
                    AlertDTO search = new AlertDTO();
                    search.setSgin(gin);
                    search.setOptIn("Y");
                    List<AlertDTO> alertDTOList = alertDS.findByExample(search);
                    ProvideIndividualDataTransformV6.transformToAlert(alertDTOList, response);
                }

                // add warnings
                Set<WarningDTO> warningDTOSet = myAccountDS.checkValidAccount(gin);
                ProvideIndividualDataTransformV6.transformToWarningResponse(warningDTOSet, response.getWarningResponse());
            }


            /* ==================================================================================== *
             * PAYMENT PREFERENCES																	*
             * ==================================================================================== */
            //REPIND-1230: In case of exception, we catch it in order to provide a valid answer
            if (isIndividu) {
                try {
                    MaskedPaymentPreferences paymentPreferencesResponse = paymentPreferencesDataHelperV5.provideMaskedPaymentPreferences(gin);
                    percentagePaymentPreferences = paymentPreferencesDataHelperV5.computePaymentPreferencesGauge(paymentPreferencesResponse);
                } catch (JrafDomainException e) {
                    log.warn("Unable to get PaymentPreferences for the GIN: {} cause {}",gin,e.getMessage());
                }
                // compute gauge
                if (response.getAccountDataResponse() != null && response.getAccountDataResponse().getAccountData() != null) {
                    int percentageFullProfil = GaugeUtils.calculPercentageFullProfil(individuDTO, accountDataDTO, individuDTO.getPostaladdressdto(), telecomsDTOList, percentageMarketingData, percentagePaymentPreferences);
                    response.getAccountDataResponse().getAccountData().setPercentageFullProfil(percentageFullProfil);
                }
            }

            if (response != null && response.getIndividualResponse() != null && response.getIndividualResponse().getIndividualInformations() != null) {

                String populationType = response.getIndividualResponse().getIndividualInformations().getPopulationType();

                // In case of value of populationType is not current Individual of type <I> with contract
                // if (!"I".equals(populationType) && !"W".equals(populationType)) {

                // populationType ="";

                // We search if individual has attributes
                if (haveRoleTravelers && !populationType.contains("T")) {
                    populationType += "T";
                }
                if (haveExternalIdentifier && !populationType.contains("E")) {
                    populationType += "E";
                }
                if (haveCommunicationPreferences && !populationType.contains("W")) {
                    populationType += "W";
                }

                if ("".equals(populationType)) {
                    populationType = "I";
                }
                // }

                // IndividualInformations indInfo =
                response.getIndividualResponse().getIndividualInformations().setPopulationType(populationType);
                // indInfo.setPopulationType(populationType);
            }
			/*
			// If we target a Traveler and we do not have travelers datas
			if (PopulationTargetedEnum.T.toString().equals(requestDTO.getPopulationTargeted()) && !haveRoleTravelers) {
				throw new NotFoundException("No traveler data found");
			}
			// If we target an Social network data and we get something else
			if (PopulationTargetedEnum.S.toString().equals(requestDTO.getPopulationTargeted()) && !response.getIndividualResponse().getIndividualInformations().getPopulationType().contains(PopulationTargetedEnum.S.toString())) {
				throw new NotFoundException("No social data found");
			}
			// If we target an External ID data and we get something else
			if (PopulationTargetedEnum.E.toString().equals(requestDTO.getPopulationTargeted()) && !response.getIndividualResponse().getIndividualInformations().getPopulationType().contains(PopulationTargetedEnum.E.toString())) {
				throw new NotFoundException("No external data found");
			}
			// If we target an Individual and we get something else
			if (PopulationTargetedEnum.I.toString().equals(requestDTO.getPopulationTargeted()) && !response.getIndividualResponse().getIndividualInformations().getPopulationType().contains(PopulationTargetedEnum.I.toString())) {
				throw new NotFoundException("No individual found");
			}
			// If we target an External Identifier and we do not have external indentifier or social network datas
			if (PopulationTargetedEnum.E.toString().equals(requestDTO.getPopulationTargeted()) && !(haveExternalIdentifier || haveSocialNetworkData)) {
				throw new NotFoundException("No social network or external identifier data found");
			}
			 */
        }
        // ERROR 001 : NOT FOUND
        catch (NotFoundException e) {
            // REPIND-255 : Suppression du log ERROR car c'est un cas normal
            log.info("provideIndividualDataByIdentifier : {}",e.getMessage());
            businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_001, e.getMessage());
        }
        // ERROR 133 : MISSING PARAMETER
        catch (MissingParameterException e) {
            log.error("provideIndividualDataByIdentifier ERROR", e);
            businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_133, e.getMessage());
        }
        // ERROR 932 : INVALID PARAMETER
        catch (InvalidParameterException e) {
            log.error("provideIndividualDataByIdentifier ERROR", e);
            businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_932, e.getMessage());
        }
        // ERROR 905 : TECHNICAL ERROR
        catch (JrafDomainException e) {
            log.error("provideIndividualDataByIdentifier ERROR", e);
            businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
        }

        return response;
    }

    protected void whiteningPostalAddressContent(ProvideIndividualInformationRequest request, ProvideIndividualInformationResponse response) throws JrafDomainException {
        if (request.getRequestor() != null && response.getPostalAddressResponse() != null && !response.getPostalAddressResponse().isEmpty()) {
            // we search channels to check in Data Base
            List<ChannelToCheckDTO> channelToCheckDTOList = channelToCheckDS.findAll();
            Boolean isChannelToCheck = channelToCheckDTOContains(request.getRequestor().getChannel(), channelToCheckDTOList);
            if (isChannelToCheck) {
                for (PostalAddressResponse postalAddressResponseLoop : response.getPostalAddressResponse()) {
                    if (postalAddressResponseLoop.getPostalAddressContent() != null) {
                        if (isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getAdditionalInformation(), ProvideIndividualDataV6Impl.NOT_COLLECTED)) {
                            postalAddressResponseLoop.getPostalAddressContent().setAdditionalInformation(null);
                        }
                        if (isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getNumberAndStreet(), ProvideIndividualDataV6Impl.NOT_COLLECTED)) {
                            postalAddressResponseLoop.getPostalAddressContent().setNumberAndStreet(null);
                        }
                        if (isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getDistrict(), ProvideIndividualDataV6Impl.NOT_COLLECTED)) {
                            postalAddressResponseLoop.getPostalAddressContent().setDistrict(null);
                        }
                        if (isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getCity(), ProvideIndividualDataV6Impl.NOT_COLLECTED)) {
                            postalAddressResponseLoop.getPostalAddressContent().setCity(null);
                            if (isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getZipCode(), ProvideIndividualDataV6Impl.POSTAL_CODE_9999)) {
                                postalAddressResponseLoop.getPostalAddressContent().setZipCode(null);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isEqualStrings(String stringToCompare, String string) {
        if (stringToCompare != null && stringToCompare.trim().equals(string)) {
            return true;
        }
        return false;
    }

    private Boolean channelToCheckDTOContains(String channel, List<ChannelToCheckDTO> channelToCheckDTOList) {
        if (channel != null && channelToCheckDTOList != null && !channelToCheckDTOList.isEmpty()) {
            for (ChannelToCheckDTO channelToCheckDTOLoop : channelToCheckDTOList) {
                if (channel.trim().equals(channelToCheckDTOLoop.getChannel().trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
        @Deprecated		// REPIND-555
        private ProspectDTO getProspectDTO(IndividualInformationRequestDTO requestDTO) throws BusinessErrorBlocBusinessException {
            ProspectDTO searchProspect = new ProspectDTO();
            List<ProspectDTO> findProspect = null;
            if("GIN".equals(requestDTO.getOption())){
                try {
                    if(SicUtf8StringUtils.isValidGIN(requestDTO.getIdentificationNumber())) {

                        searchProspect.setGin(Long.valueOf(requestDTO.getIdentificationNumber()));
                        findProspect = prospectDS.findByExample(searchProspect);
                    } else{
                        businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_932, "INVALID IDENTIFICATION NUMBER");
                    }
                } catch (JrafApplicativeException e) {
                    log.error("provideIndividualDataByIdentifier ERROR", e);
                    businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
                } catch (JrafDomainException e) {
                    log.error("provideIndividualDataByIdentifier ERROR", e);
                    businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
                }
            } else {
                log.error("Parameter Exception : Option need to be a GIN");
                businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_001, "NO MATCH FOUND");
            }

            if(findProspect!=null && !findProspect.isEmpty()) {
                // severals results -> take first and log a warning
                if(findProspect.size()>1){
                    log.warn("Prospect search returned several results: "+searchProspect.getGin()+" | "+searchProspect.getEmail());
                }
                return findProspect.get(0);
            } else {
                log.error("provideIndividualDataByIdentifier ERROR : NOT FOUND");
                businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_001, "NO MATCH FOUND");
            }

            return null;
        }
    */
    protected void checkRequest(ProvideIndividualInformationRequest request) throws MissingParameterException {

        if (StringUtils.isEmpty(request.getIdentificationNumber())) {
            throw new MissingParameterException("Identification number is mandatory");
        }

        if (StringUtils.isEmpty(request.getOption())) {
            throw new MissingParameterException("Option is mandatory");
        }

    }

    protected void checkResponse(ProvideIndividualInformationResponse response) throws NotFoundException {

        if (response == null || response.getIndividualResponse() == null || response.getIndividualResponse().getIndividualInformations() == null) {
            throw new NotFoundException("Unable to find individual");
        }

    }

    /*	private String getScope(ProvideIndividualInformationRequest request) {

        if(request==null) {
            return null;
        }
        RequestorV2 requestor = request.getRequestor();

        if(requestor==null) {
            return null;
        }

        return requestor.getScope();

    }

    private String getPopulationTargeted(ProvideIndividualInformationRequest request) {

        if(request==null) {
            return null;
        }
        return request.getPopulationTargeted();
    }
     */
    private boolean isScopesToProvideRequested(Set<ScopesToProvideEnum> scopesToProvideSet) {
        if (scopesToProvideSet != null && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.IDENTIFICATION) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.POSTAL_ADDRESS) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.EMAIL) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.CONTRACT) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.ACCOUNT) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.TRAVELER) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.SOCIAL_NETWORK) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.TELECOM) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.DELEGATION) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.PREFILLED_NUMBER) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.EXTERNAL_IDENTIFIER) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.COMMUNICATIION_PREFERENCE) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.LOCALIZATION) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.PREFERENCE) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.UCCR_CONTRACT) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.ALERT) ||
                scopesToProvideSet.contains(ScopesToProvideEnum.MARKETING))) {
            return true;
        }
        return false;
    }

    private void activateIndividualScopes(Set<ScopesToProvideEnum> scopesToProvideSet) {
        if (scopesToProvideSet != null && scopesToProvideSet.contains(ScopesToProvideEnum.INDIVIDUAL)) {
            scopesToProvideSet.add(ScopesToProvideEnum.IDENTIFICATION);
            scopesToProvideSet.add(ScopesToProvideEnum.POSTAL_ADDRESS);
            scopesToProvideSet.add(ScopesToProvideEnum.EMAIL);
            scopesToProvideSet.add(ScopesToProvideEnum.CONTRACT);
            scopesToProvideSet.add(ScopesToProvideEnum.ACCOUNT);
            scopesToProvideSet.add(ScopesToProvideEnum.SOCIAL_NETWORK);
            scopesToProvideSet.add(ScopesToProvideEnum.TELECOM);
            scopesToProvideSet.add(ScopesToProvideEnum.DELEGATION);
            scopesToProvideSet.add(ScopesToProvideEnum.PREFILLED_NUMBER);
            scopesToProvideSet.add(ScopesToProvideEnum.EXTERNAL_IDENTIFIER);
            scopesToProvideSet.add(ScopesToProvideEnum.COMMUNICATIION_PREFERENCE);
            scopesToProvideSet.add(ScopesToProvideEnum.LOCALIZATION);
            scopesToProvideSet.add(ScopesToProvideEnum.PREFERENCE);
            scopesToProvideSet.add(ScopesToProvideEnum.UCCR_CONTRACT);
            scopesToProvideSet.add(ScopesToProvideEnum.ALERT);
            scopesToProvideSet.add(ScopesToProvideEnum.MARKETING);
        }
    }

    public DelegationDataDS getDelegationDataDS() {
        return delegationDataDS;
    }

    public void setDelegationDataDS(DelegationDataDS delegationDataDS) {
        this.delegationDataDS = delegationDataDS;
    }

    public PaymentPreferencesDataHelper getPaymentPreferencesDataHelperV5() {
        return paymentPreferencesDataHelperV5;
    }


    public void setPaymentPreferencesDataHelperV5(PaymentPreferencesDataHelper paymentPreferencesDataHelperV5) {
        this.paymentPreferencesDataHelperV5 = paymentPreferencesDataHelperV5;
    }

    // REPIND-1441 : Trace who are consuming us and for what
    private void TraceInput(ProvideIndividualInformationRequest request) {

        String retour = "W000418V6; ";

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
            retour += SicStringUtils.TraceInputScopeToProvide(request.getScopeToProvide());

            // INPUT
            retour += SicStringUtils.TraceInputOptionIdentificationNumber(request.getOption(), request.getIdentificationNumber());
        }

        log.info(retour);
    }

	/*
	public static final String TraceInputProvide(String consumerID, String site, String signature, String appicationCode, List<String> scopeToProvide, String option, String identificationNumber) {
		
		return TraceInputRequestor(consumerID, site, signature, appicationCode) + 
				TraceInputScopeToProvide(scopeToProvide) + 
				TraceInputOptionIdentificationNumber(option, identificationNumber);
	}
	*/
}
