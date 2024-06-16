package com.afklm.repind.v8.createorupdateindividualws;

import com.afklm.repind.v8.createorupdateindividualws.helpers.AlertCheckHelper;
import com.afklm.repind.v8.createorupdateindividualws.helpers.BusinessExceptionHelper;
import com.afklm.repind.v8.createorupdateindividualws.helpers.PreferenceCheckHelper;
import com.afklm.repind.v8.createorupdateindividualws.transformers.*;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000413.v2.MarketingDataBusinessException;
import com.afklm.soa.stubs.w000413.v2.StoreMarketingPreferencesCustomerV2;
import com.afklm.soa.stubs.w000413.v2.common.MarketingDataV2;
import com.afklm.soa.stubs.w000413.v2.store.StoreMDRequestV2;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.CreateUpdateIndividualServiceV8;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.EmailRequest;
import com.afklm.soa.stubs.w000442.v8.request.ExternalIdentifierRequest;
import com.afklm.soa.stubs.w000442.v8.request.PostalAddressRequest;
import com.afklm.soa.stubs.w000442.v8.request.TelecomRequest;
import com.afklm.soa.stubs.w000442.v8.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.Civilian;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.ExternalIdentifierData;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.MarketLanguage;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.PreferenceV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.PreferenceDataV2;
import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.contract.NotConsistentSaphirNumberException;
import com.airfrance.ref.exception.contract.SaphirContractNotFoundException;
import com.airfrance.ref.exception.email.SharedEmailException;
import com.airfrance.ref.exception.external.*;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.ref.type.ProcessEnum;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.refTable.RefTableREF_CIVILITE;
import com.airfrance.repind.entity.refTable.RefTableREF_CODE_TITRE;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.reference.internal.ReferenceDS;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import com.airfrance.repind.service.ws.internal.helpers.UltimateException;
import com.airfrance.repind.util.ForgetMeUtils;
import com.airfrance.repind.util.LoggerUtils;
import com.airfrance.repind.util.ReadSoaHeaderHelper;
import com.airfrance.repind.util.SicStringUtils;
import com.airfrance.repindutf8.util.SicUtf8StringUtils;
import com.sun.xml.ws.api.message.Header;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.soap.SOAPException;
import javax.xml.ws.WebServiceContext;
import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;


@Service("passenger_CreateUpdateIndividualService-v8Bean")
@WebService(endpointInterface = "com.afklm.soa.stubs.w000442.v8.CreateUpdateIndividualServiceV8", targetNamespace = "http://www.af-klm.com/services/passenger/CreateUpdateIndividualService-v8/wsdl", serviceName = "CreateUpdateIndividualServiceService-v8", portName = "CreateUpdateIndividualService-v8-soap11http")
@Slf4j
public class CreateOrUpdateIndividualImplV8 implements CreateUpdateIndividualServiceV8 {

    @Autowired
    protected AccountDataDS accountDataDS;

    @Autowired
    protected ReferenceDS referencesDS;

    @Autowired
    protected CreateOrUpdateIndividualDS createOrUpdateIndividualDS;

    @Autowired
    protected BusinessExceptionHelper businessExceptionHelper;

    @Autowired
    protected AlertCheckHelper alertHelper;

    @Autowired
    protected PreferenceCheckHelper preferencHelper;

    @Autowired
    private ForgetMeUtils forgetMeUtils;

    @Resource
    protected WebServiceContext context;

    @Autowired
    @Qualifier("consumerW000413V02")
    private StoreMarketingPreferencesCustomerV2 storeMarketingPreferenceCustomer;

    private final String WEBSERVICE_IDENTIFIER = "W000442";
    private final String SITE = "VLB";

    @Override
    public CreateUpdateIndividualResponse createIndividual(CreateUpdateIndividualRequest request) throws BusinessErrorBlocBusinessException {

        log.info("##### START {} ###############################",this.getClass().getSimpleName());
        if (request == null) {
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_932, "Request must not be null");
        }
        CreateUpdateIndividualResponse response = null;

        try {
            // INDIVIDUAL CREATION (GIN not filled) -> S08924
            boolean isCreated = false;

            // Normalize SC input & save UTF data REPIND-579
            // TODO : Mettre en commentaire puis deplacer el Business plus bas en utilisant les DTO
            IndividualInformationsV3 individualInformation = new IndividualInformationsV3();
            if (request.getIndividualRequest() != null) {
                individualInformation = request.getIndividualRequest().getIndividualInformations();
                if (individualInformation.getStatus() != null) {
                    // REPIND-946 : It's impossible to create or update an individual with the statut 'F' (via WS)
                    if (individualInformation.getStatus().equals("F")) {
                        throw new InvalidParameterException("statut F is unknown");
                    }
                }
            }

            // 1. Identify a GDPR request for deletion
            if (request != null && request.getRequestor() != null && request.getRequestor().getContext() != null) {
                String context = request.getRequestor().getContext();
                if (forgetMeUtils.isRequestForDeletion(context)) {
                    response = new CreateUpdateIndividualResponse();
                    response.setSuccess(forgetMeUtils.processRequestToBeForgotten(CreateUpdateIndividualRequestTransform.transformCreateUpdateIndividualRequest(request)));
                    return response;
                }
            }

            // 2. CHECKING INPUTS => EITHER CREATION OR UPDATE
            checkInput(request);

            CreateUpdateIndividualRequestDTO requestDTO = IndividuRequestTransform.transformRequestWSToDTO(request);

            //REPIND-1573 - We want to limit this feature to some consumers
            requestDTO.setConsumerId(getConsumerId());

            CreateModifyIndividualResponseDTO responseDTO = IndividuResponseTransform.transformToCreateUpdateIndividualResponseDTO(response);

            responseDTO = createOrUpdateIndividualDS.createOrUpdateV8(requestDTO, responseDTO);

            response = IndividuResponseTransform.transformToCreateUpdateIndividualResponse(responseDTO);

            //REPIND-1225: Preferences are now stored in RI DB instead of BDM
			/*if (request.getProcess() == null || !ProcessEnum.W.getCode().equalsIgnoreCase(request.getProcess())) {
				callMarketingData(request);
			}*/

        }
        // ERROR ? (need to receive a number code) : ULTIMATE ERROR
        catch (UltimateException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.OTHER, e.getMessage());
        }
        // ERROR 001 : INDIVIDUAL NOT FOUND
        catch (NotFoundException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_001, e.getMessage());
        }
        // ERROR 003 : SIMULTANEOUS UPDATE
        catch (SimultaneousUpdateException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_003, e.getMessage());
        }
        // ERROR 100: MAILING DATA MANDATORY
        catch (MissingMailingDataException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createCustomBusinessException("ERROR_100", e.getMessage());
        }
        // ERROR 117: MEDIUM CODE MANDATORY
        catch (MissingMediumCodeException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createCustomBusinessException("ERROR_117", e.getMessage());
        }
        // ERROR 132 : MAXIMUM NUMBER OF ADDRESS REACHED
        catch (MaximumAddressNumberException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createCustomBusinessException("ERROR_132", e.getMessage());
        }
        // ERROR 133 : MISSING PARAMETER
        catch (MissingParameterException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_133, e.getMessage());
        }
        // ERROR 135 : INVALID LANGUAGE CODE
        catch (InvalidLanguageCodeException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_135, e.getMessage());
        }
        // ERROR 165: INVALID MAILING CODE (A,T OR N)
        catch (InvalidMailingCodeException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createCustomBusinessException("ERROR_165", e.getMessage());
        }
        // ERROR 220 : INVALID MAILING
        catch (InvalidMailingException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createCustomBusinessException("ERROR_220", e.getMessage());
        }
        // ERROR 247 : OPEN OR VALID CONTRACT FOUND
        catch (ValidContractFoundException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createCustomBusinessException("ERROR_247", e.getMessage());
        }
        // ERROR 248 : REQUEST ALREADY DONE
        catch (RequestAlreadyDoneException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createCustomBusinessException("ERROR_248", e.getMessage());
        }
        // 382 : SHARED EMAIL
        catch (SharedEmailException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_382, e.getMessage() + ": " + e.getEmail());
        }
        // 384 : ALREADY EXISTS
        catch (AlreadyExistException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_384, e.getMessage());
        }
        // 385 : Account not found
        catch (AccountNotFoundException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_385, e.getMessage());
        }
        // 387 : MY ACCOUNT ONLY
        catch (OnlyMyAccountException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_387, e.getMessage());
        }
        // 538 : SAPHIR NUMBER NOT MATCHING WITH INDIVIDUAL DATAS
        catch (NotConsistentSaphirNumberException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_538, e.getMessage());
        }
        // 550 : NEW DATA REQUIRED FOR RECONCILIATION PROCESS
        catch (ReconciliationProcessException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_550, e.getMessage());
        }
        // 551 : MAXIMUM NUMBER OF SUBSCRIBED NEWLETTER SALES REACHED
        catch (MaximumSubscriptionsException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_551, e.getMessage());
        }
        // ERROR 701 : PHONE NUMBER TOO LONG
        catch (TooLongPhoneNumberException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_701, e.getMessage());
        }
        // ERROR 702 : PHONE NUMBER TOO SHORT
        catch (TooShortPhoneNumberException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_702, e.getMessage());
        }
        // ERROR 703 : INVALID PHONE NUMBER
        catch (InvalidPhoneNumberException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_703, e.getMessage());
        }
        // 706 : DELEGATION GIN NOT FOUND
        catch (DelegationGinNotFoundException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_706, e.getMessage());
        }
        // 707 : DELEGATION GINS IDENTICAL
        catch (DelegationGinsIdenticalException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_707, e.getMessage());
        }
        // 708 : DELEGATION GIN WITHOUT ACCOUNT
        catch (DelegationGinWithoutAccountException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_708, e.getMessage());
        }
        // 709 : DELEGATION STATUS NOT AUTHORIZED
        catch (DelegationActionException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_709, e.getMessage());
        }
        // 710 : DELEGATION ERROR
        catch (DelegationException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_710, e.getMessage());
        }
        // ERROR 711 : INVALID PHONE COUNTRY CODE
        catch (InvalidCountryCodeException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_711, e.getMessage());
        }
        // ERROR 705 : NO NORMALIZED
        catch (NormalizedPhoneNumberException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_705, e.getMessage());
        }
        // ERROR 712 : SAPHIR CONTRACT NOT FOUND
        catch (SaphirContractNotFoundException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_712, e.getMessage());
        }
        // ERROR 713 : INVALID EXTERNAL IDENTIFIER
        catch (ExternalIdentifierAlreadyUsedException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_713, e.getMessage());
        }
        // ERROR 713 : INVALID EXTERNAL IDENTIFIER
        catch (InvalidExternalIdentifierException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_713, e.getMessage());
        }
        // ERROR 714 : INVALID EXTERNAL IDENTIFIER TYPE
        catch (InvalidExternalIdentifierTypeException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_714, e.getMessage());
        }
        // ERROR 715 : INVALID EXTERNAL IDENTIFIER DATA KEY
        catch (InvalidExternalIdentifierDataKeyException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_715, e.getMessage());
        }
        // ERROR 716 : INVALID EXTERNAL IDENTIFIER DATA VALUE
        catch (InvalidExternalIdentifierDataValueException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_716, e.getMessage());
        }
        // ERROR 717 : INVALID PNM ID
        catch (InvalidPnmIdException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_717, e.getMessage());
        }
        // ERROR 718 : MAXIMUM NUMBER OF PNM ID REACHED
        catch (MaxNbOfPnmIdReachedException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_718, e.getMessage());
        }
        // ERROR 720 : MAXIMUM NUMBER OF PREFERENCES REACHED
        catch (MultiplePreferencesException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_720, e.getMessage());
        }
        // ERROR 932 : INVALID PARAMETER
        catch (InvalidParameterException | com.airfrance.ref.exception.InvalidParameterException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_932, e.getMessage());
        }
        catch (AddressNormalizationException e) {
            log.error("Problem during postal address normalization : ", e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
        }
        // 905 : MARKETING ERROR
        catch (MarketingErrorException e) {
            log.error("Problem during updateMyAccountCustomer : ", e);
            throw businessExceptionHelper.createMarketingBusinessException(BusinessErrorCodeEnum.ERROR_905, e);
        }
        // ERROR 905 : TECHNICAL ERROR
        catch (JrafDomainException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
        }

        // ERROR 905 : TECHNICAL ERROR
        catch (JrafApplicativeException e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
        }
        //RequestAlreadyDoneException
        // ERROR 905 : TECHNICAL ERROR
        catch (Exception e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
            throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
        }

        log.info("##### END CreateOrUpdateAnIndividual-V8 #################################");
        return response;
    }

    /**
     * CHECKING INPUT : checking Individual Request information
     *
     * @param request
     * @throws BusinessErrorBlocBusinessException
     * @throws JrafApplicativeException
     * @throws JrafDomainException
     */
    protected void checkInput(CreateUpdateIndividualRequest request) throws BusinessErrorBlocBusinessException, JrafApplicativeException, JrafDomainException {

        if (request != null) {

            if (request.getPreferenceRequest() != null){
                checkPreferencesDataCharacters(request);
            }

            //check if medium status is valid for Postal Address
            for (PostalAddressRequest postalAddressRequest : request.getPostalAddressRequest()) {
                String mediumStatus = postalAddressRequest.getPostalAddressProperties().getMediumStatus();
                if (!MediumStatusEnum.checkIfStatusExists(mediumStatus)) {
                    throw new InvalidParameterException("Invalid medium status " + mediumStatus);
                }

            }
            if (ProcessEnum.W.getCode().equals(request.getProcess())) {
                // Traitement pour les prospects
                if (request.getRequestor() != null && request.getRequestor().getContext() != null) {
                    if (!request.getRequestor().getContext().equalsIgnoreCase("B2C_HOME_PAGE") &&
                            !request.getRequestor().getContext().equalsIgnoreCase("B2C_HOME_PAGE_RECONCILIATION") &&
                            !request.getRequestor().getContext().equalsIgnoreCase("B2C_BOOKING_PROCESS") &&
                            // REPIND-1786 : FBSPush want to update PROSPECT with Birthdate/ComPref/Pref/Alert etc...
                            !request.getRequestor().getContext().equalsIgnoreCase("FBSPUSH") &&
                            !request.getRequestor().getContext().equalsIgnoreCase("KL_PROSPECT")) {
                        throw new InvalidParameterException("Unknown context parameter for prospect");
                    }
                } else {
                    throw new MissingParameterException("Missing context information");
                }

                // REPIND-579 deleted latin1 char check (DONE)

                // Email mandatory for creation
                if (request.getEmailRequest() != null && !request.getEmailRequest().isEmpty()) {
                    if (request.getEmailRequest().size() > 1) {
                        throw new InvalidParameterException("Only 1 email usage for prospect");
                    } else {
                        if (request.getEmailRequest().get(0).getEmail() != null
                                && request.getEmailRequest().get(0).getEmail().getEmail() != null) {
                            String email = request.getEmailRequest().get(0).getEmail().getEmail();

                            // REPIND-1767 : Detect UTF8 in Email
                            if (SicUtf8StringUtils.isNonASCII(email)) {
                                // We will store that later with an other detecting...
                            } else {
                                if (!SicStringUtils.isValidEmail(email)) {
                                    throw new InvalidParameterException("Invalid email");
                                }
                            }
                            request.getEmailRequest().get(0).getEmail().setEmail(email.toLowerCase());
                        }
                    }
                } else if (request.getIndividualRequest() == null || request.getIndividualRequest().getIndividualInformations() == null || request.getIndividualRequest().getIndividualInformations().getIdentifier() == null) {
                    throw new MissingParameterException("Email mandatory for prospect creation");
                }

                if (request.getRequestor() != null && request.getRequestor().getLoggedGin() != null) {
                    try {
                        request.getRequestor().setLoggedGin(IndividuTransform.clientNumberToString(request.getRequestor().getLoggedGin()));
                        if (!SicStringUtils.isValidFbIdentifier(request.getRequestor().getLoggedGin())) {
                            throw new InvalidParameterException("Invalid FB identifier");
                        }
                    } catch (JrafApplicativeException e) {
                        log.error(LoggerUtils.buidErrorMessage(e), e);
                        throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
                    } catch (NumberFormatException e) {
                        log.error(LoggerUtils.buidErrorMessage(e), e);
                        throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
                    }
                }

                if (request.getRequestor() != null && request.getRequestor().getReconciliationDataCIN() != null) {
                    try {
                        request.getRequestor().setReconciliationDataCIN(IndividuTransform.clientNumberToString(request.getRequestor().getReconciliationDataCIN()));
                        if (!SicStringUtils.isValidFbIdentifier(request.getRequestor().getReconciliationDataCIN())) {
                            throw new InvalidParameterException("Invalid FB identifier");
                        }
                    } catch (JrafApplicativeException e) {
                        log.error(LoggerUtils.buidErrorMessage(e), e);
                        throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
                    } catch (NumberFormatException e) {
                        log.error(LoggerUtils.buidErrorMessage(e), e);
                        throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
                    }
                }

                // Compref mandatory for creation
                if (request.getComunicationPreferencesRequest() != null && !request.getComunicationPreferencesRequest().isEmpty()) {
                    if (request.getComunicationPreferencesRequest().size() == 1) {
                        if (request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences() != null) {
                            try {
                                if ((!referencesDS.refComPrefExist(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getDomain(), request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getCommunicationGroupeType(), request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getCommunicationType())
                                ) && !ProcessEnum.A.getCode().equals(request.getProcess())) {
                                    throw new InvalidParameterException("Domain, group type or type unknown");
                                }
                            } catch (JrafDomainException e) {
                                log.error(LoggerUtils.buidErrorMessage(e), e);
                                throw businessExceptionHelper.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
                            }

                            if (!"P".equals(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getDomain()) && (request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage() == null || request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage().isEmpty())) {
                                throw new MissingParameterException("Market language mandatory for prospects");
                            } else {   // Verification de doublons sur les marchés/langues dans l'input
                                List<MarketLanguage> tempsML = request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage();
                                boolean twiceML = false;
                                for (int indexReq = 0; indexReq < request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage().size(); indexReq++) {
                                    for (int indexTemp = indexReq + 1; indexTemp < tempsML.size(); indexTemp++) {
                                        if (tempsML.get(indexTemp).getLanguage() == request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage().get(indexReq).getLanguage()
                                                && tempsML.get(indexTemp).getMarket().equalsIgnoreCase(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage().get(indexReq).getMarket())) {
                                            twiceML = true;
                                        }
                                    }
                                }
                                if (twiceML) {
                                    throw new InvalidParameterException("Duplicate market/language set");
                                }
                            }

                            // REPIND-1571 : No check
//							if (request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getOptIn() != null && request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage() != null && !request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage().isEmpty()) {
//								for (MarketLanguage ml : request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage()) {
//									if ((!request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getOptIn().equalsIgnoreCase("Y") || !request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getOptIn().equalsIgnoreCase("N"))
//											&& (!ml.getOptIn().equalsIgnoreCase("Y") || !ml.getOptIn().equalsIgnoreCase("N"))
//											&& (!request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getOptIn().equalsIgnoreCase(ml.getOptIn()))) {
//										throw new InvalidParameterException("Optin compref and optin market language are different");
//									}
//								}
//							}

                            if (StringUtils.isEmpty(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getDomain())) {
                                throw new MissingParameterException("The field domain is mandatory");
                            }

                            if (StringUtils.isEmpty(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getCommunicationGroupeType())) {
                                throw new MissingParameterException("The field group type is mandatory");
                            }

                            if (StringUtils.isEmpty(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getCommunicationType())) {
                                throw new MissingParameterException("The field type is mandatory");
                            }

                            if (StringUtils.isEmpty(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getOptIn())) {
                                throw new MissingParameterException("The field optin is mandatory");
                            }

                            if (StringUtils.isEmpty(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getSubscriptionChannel())) {
                                throw new MissingParameterException("The field subscription channel is mandatory");
                            }

                            for (MarketLanguage ml : request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage()) {
                                if (StringUtils.isEmpty(ml.getMarket())) {
                                    throw new MissingParameterException("The field market is mandatory");
                                }
                                if (ml.getLanguage() == null) {
                                    throw new MissingParameterException("The field language is mandatory");
                                }
                                if (StringUtils.isEmpty(ml.getOptIn())) {
                                    throw new MissingParameterException("The field optin for market language is mandatory");
                                }
                                if (ml.getDateOfConsent() == null) {
                                    throw new MissingParameterException("The field date of consent for market language is mandatory");
                                }
                            }
                        }
                    } else {
                        throw new InvalidParameterException("Only 1 communication preference allowed for prospect");
                    }
                } else if ((request.getIndividualRequest() == null || request.getIndividualRequest().getIndividualInformations() == null || request.getIndividualRequest().getIndividualInformations().getIdentifier() == null)
                    /* && request.getAlertRequest() == null */) { //We can create a prospect with an alert and without ComPref
                    throw new MissingParameterException("Communication preference mandatory for prospect creation");
                }

                if (request.getTelecomRequest() != null && !request.getTelecomRequest().isEmpty()) {
                    if (request.getTelecomRequest().size() > 1) {
                        throw new InvalidParameterException("Only 1 telecom allowed for prospect");
                    }
                }

                // Only preferred airport field is allowed for a prospect preference
                preferencHelper.controlProspectPreference(request);

            } else if (ProcessEnum.A.getCode().equals(request.getProcess())) {   // Traitement par defaut pour les alert
                if (request.getAlertRequest() != null) {
                    alertHelper.checkInputAlert(IndividuRequestTransform.transformToAlertDTO(request.getAlertRequest()),
                            (request.getComunicationPreferencesRequest() != null && !request.getComunicationPreferencesRequest().isEmpty()));
                    alertHelper.checkComprefAndAlert(request.getAlertRequest(), request.getComunicationPreferencesRequest());
                }
            }// Dans le cas ou on essaye de creer des alertes sans le process A (Hors process)
            else if (request.getAlertRequest() != null && !request.getProcess().equals(ProcessEnum.A.getCode())) {
                throw new InvalidParameterException("Create or update Alert not allowed withless Process A");

                // REPIND-1808 : Create Caller for CCP
            } else if (ProcessEnum.C.getCode().equals(request.getProcess())) {   // Traitement pour les Caller
                boolean shouldThrow = false;
                if (request.getTelecomRequest() != null && !request.getTelecomRequest().isEmpty()) {
                    for (TelecomRequest tr : request.getTelecomRequest()) {
                        if (tr.getTelecom() == null || StringUtils.isEmpty(tr.getTelecom().getCountryCode()) || StringUtils.isEmpty(tr.getTelecom().getPhoneNumber())) {
                            shouldThrow = true;
                        }
                    }
                } else {
                    shouldThrow = true;
                }
                if (shouldThrow) {
                    throw new InvalidParameterException("Create a Caller is not allowed without a Telecom");
                }
            } else {    // Traitement par defaut pour les non prospects

                // On differencie ici si il s'agit d'une creation ou d'un update
                if (request.getProcess() == null || (request.getProcess() != null && !ProcessEnum.E.getCode().equals(request.getProcess()))) {
                    String identifier = checkIndividualInput(request);
                    // IF IDENTIFIER IS FILLED IN => OK
                    if (StringUtils.isNotEmpty(identifier)) {
                        return;
                    }
                }
                //		// WE WANT TO CREATE A NEW INDIVIDUAL => MANDATORY FIELDS ARE BIRTH DATE (EXCEPT FOR FIDELIO)
                //		// STATUS, CIVILITY,LAST NAME SC, FIRST NAME SC
                //		if(!applicationCode.equals(FIDELIO) && individualInformations.getBirthDate()==null){
                //			throw new MissingParameterException("The field birth date is mandatory");
                //		}

                // INDVIDUAL CREATION => CAN NOT CREATE EXTRA DATA WHILE CREATING AN INDIVIDUAL. ONLY EMAILS,TELECOM AND POSTAL ADDRESSES CAN BE CREATED
                if (request.getComunicationPreferencesRequest().isEmpty() && request.getPrefilledNumbersRequest().isEmpty() &&
                        request.getExternalIdentifierRequest().isEmpty() && request.getAccountDelegationDataRequest() == null &&
                        request.getPreferenceRequest() == null) {
                    return;
                }

                if (request.getProcess() != null && ProcessEnum.E.getCode().equals(request.getProcess())) {
                    checkExternalIdentifierDataKey(request);
                }

                // TODO : Remplacer SOCIAL par TypeEmunContext
                if (!request.getExternalIdentifierRequest().isEmpty() && (request.getProcess() != null && !ProcessEnum.E.getCode().equals(request.getProcess()))) {
                    throw new InvalidParameterException("Unable to create an individual with external identifiers (account data mandatory)");
                }

                if (!request.getComunicationPreferencesRequest().isEmpty()) {
                    throw new InvalidParameterException("Unable to create an individual with communication preferences");
                }

                if (!request.getPrefilledNumbersRequest().isEmpty()) {
                    throw new InvalidParameterException("Unable to create an individual with prefilled numbers");
                }


                if (request.getPreferenceRequest() != null) {
                    throw new InvalidParameterException("Unable to create an individual with preferences and marketing data");
                }

                if (request.getAccountDelegationDataRequest() != null) {
                    throw new InvalidParameterException("Unable to create an individual with account delegation data");
                }

            }
        }
    }

    /**
     * Check fields about an individual
     *
     * @param request
     * @return
     * @throws MissingParameterException
     */
    protected String checkIndividualInput(CreateUpdateIndividualRequest request) throws MissingParameterException {

        if (request == null || request.getIndividualRequest() == null || request.getIndividualRequest().getIndividualInformations() == null) {
            throw new MissingParameterException("The individual informations are mandatory");
        }

        IndividualInformationsV3 individualInformations = request.getIndividualRequest().getIndividualInformations();

        // REPIND-1671 : Check the Constraint : scivilite in ('M.','MISS','MR','MRS','MS', 'MX')
        if (!StringUtils.isEmpty(individualInformations.getCivility())) {                            // Check if CIVILITY have been filled

            individualInformations.setCivility(individualInformations.getCivility().trim().toUpperCase());

            RefTableREF_CIVILITE iRefTableREF_CIVILITE = RefTableREF_CIVILITE.instance();
            if (!iRefTableREF_CIVILITE.estValide(individualInformations.getCivility(), "")) {        // Check if this value is correct or not

                log.warn("The field civility not valid '{}'",individualInformations.getCivility());
                throw new MissingParameterException("The field civility not valid");                // We must raised an error because of ORACLE violated constraint
            }
        }


        // REPIND-1671 : Check the Constraint : scode_title in REF_CODE_TITRE
        Civilian civilian = request.getIndividualRequest().getCivilian();
        if (civilian != null && !StringUtils.isEmpty(civilian.getTitleCode())) {                                            // Check if TITLE have been filled

            civilian.setTitleCode(civilian.getTitleCode().trim().toUpperCase());
            request.getIndividualRequest().setCivilian(civilian);

            RefTableREF_CODE_TITRE iRefTableREF_CODE_TITRE = RefTableREF_CODE_TITRE.instance();
            if (!iRefTableREF_CODE_TITRE.estValide(civilian.getTitleCode(), "")) {                    // Check if this value is correct or not

                log.warn("The field title not valid '{}'",civilian.getTitleCode());
                throw new MissingParameterException("The field title not valid");                    // We must raised an error because of ORACLE violated constraint
            }
        }

        // REPIND-1671 : In case of UPDATE we skipped all the Mandatory check made after that
        if (StringUtils.isNotEmpty(individualInformations.getIdentifier())) {
            return individualInformations.getIdentifier();
        }

        // CHECK MANDATORY IN CREATE ONLY

        // REPIND-984
        // KidSolo context : not check the status (no mandatory for B2C).
        if (StringUtils.isEmpty(individualInformations.getStatus()) && !ProcessEnum.K.getCode().equals(request.getProcess())) {
            throw new MissingParameterException("The field status is mandatory");
        }

        if (StringUtils.isEmpty(individualInformations.getCivility())) {
            throw new MissingParameterException("The field civility is mandatory");
        }

        if (StringUtils.isEmpty(individualInformations.getLastNameSC())) {
            throw new MissingParameterException("The field lastname is mandatory");
        }

        if (StringUtils.isEmpty(individualInformations.getFirstNameSC())) {
            throw new MissingParameterException("The field firstname is mandatory");
        }

        if (request.getProcess() != null && ProcessEnum.T.equals(ProcessEnum.valueOf(request.getProcess()))) {
            if ((request.getTelecomRequest() == null || request.getTelecomRequest().isEmpty())
                    && (request.getEmailRequest() == null || request.getEmailRequest().isEmpty())) {
                throw new MissingParameterException("A bloc \"contact\" (Telecom and/or email) is mandatory for traveler process");
            }

            if (request.getTelecomRequest() != null && !request.getTelecomRequest().isEmpty()) {
                for (TelecomRequest telRequest : request.getTelecomRequest()) {
                    if (telRequest.getTelecom() != null) {
                        boolean exc = telRequest.getTelecom().getCountryCode() != null ? true : false;
                        exc &= telRequest.getTelecom().getPhoneNumber() != null ? true : false;
                        if (!exc) {
                            throw new MissingParameterException("Phone number and country code are mandatory in a Telecom bloc");
                        }
                    }
                }
            }
            if (request.getEmailRequest() != null && !request.getEmailRequest().isEmpty()) {
                for (EmailRequest emRequest : request.getEmailRequest()) {
                    if (emRequest.getEmail() != null && emRequest.getEmail().getEmail() == null) {
                        throw new MissingParameterException("Email is mandatory in an Email bloc");
                    }
                }
            }

        }

        return "";
    }

    protected void checkPreferencesDataCharacters(CreateUpdateIndividualRequest request) throws InvalidParameterException{
        List<PreferenceV2> preferenceList = request.getPreferenceRequest().getPreference();

        // Check for TCC (Travel Companions) preferences data
        for (PreferenceV2 preference : preferenceList){
            if (preference.getType() != null && "TCC".equalsIgnoreCase(preference.getType())
                    && preference.getPreferenceDatas() != null
                    && preference.getPreferenceDatas().getPreferenceData() != null
                    && !preference.getPreferenceDatas().getPreferenceData().isEmpty()) {

                for (PreferenceDataV2 prefData : preference.getPreferenceDatas().getPreferenceData()) {
                    if (prefData.getKey() != null && "lastName".equalsIgnoreCase(prefData.getKey())) {
                        boolean hasSpecialChars = !prefData.getValue().matches("^[A-Za-z\\s]+$");
                        if (hasSpecialChars){
                            throw new InvalidParameterException("The Travel Companion lastname must not contain any special characters (only letters and spaces allowed)");
                        }
                    }
                    if (prefData.getKey() != null && "firstName".equalsIgnoreCase(prefData.getKey())) {
                        boolean hasSpecialChars = !prefData.getValue().matches("^[A-Za-z\\s]+$");
                        if (hasSpecialChars){
                            throw new InvalidParameterException("The Travel Companion firstname must not contain any special characters (only letters and spaces allowed)");
                        }
                    }
                }
            }
        }
    }

    protected void checkExternalIdentifierDataKey(CreateUpdateIndividualRequest request) throws InvalidParameterException {
        for (ExternalIdentifierRequest eir : request.getExternalIdentifierRequest()) {
            List<String> keys = new LinkedList<>();
            for (ExternalIdentifierData eid : eir.getExternalIdentifierData()) {
                if (keys.contains(eid.getKey())) {
                    throw new InvalidParameterException("The external identifier data key must be unique for a given external identifier");
                }
                keys.add(eid.getKey());
            }
        }
    }

    private String getGin(CreateUpdateIndividualRequest request) {

        if (request == null || request.getIndividualRequest() == null || request.getIndividualRequest().getIndividualInformations() == null) {
            return null;
        }

        return request.getIndividualRequest().getIndividualInformations().getIdentifier();
    }

    private String getGin(CreateModifyIndividualResponseDTO responseDTO) {

        if (responseDTO == null || responseDTO.getIndividu() == null) {
            return null;
        }

        responseDTO.setGin(responseDTO.getIndividu().getNumeroClient());
        return responseDTO.getIndividu().getNumeroClient();
    }

    /**
     * PROCESS MARKETING DATA TO UPDATE
     *
     * @param request
     * @throws BusinessException
     */
    protected void callMarketingData(CreateUpdateIndividualRequest request) throws JrafDomainException {

        if (storeMarketingPreferenceCustomer == null) {
            log.error("The StoreMarketingPreferenceCustomer is null, verify the problem.");
            throw new MarketingErrorException("The StoreMarketingPreferenceCustomer is null, check the problem.");
        }

        // REPIND-555 : On appel pas la BDM si pas de GIN
        if (request.getPreferenceRequest() != null && request.getIndividualRequest().getIndividualInformations() != null && request.getIndividualRequest().getIndividualInformations().getIdentifier() != null) {

            StoreMDRequestV2 mdRequest = new StoreMDRequestV2();
            MarketingDataV2 marketingData = PreferenceTransform.transformToBDM(request.getPreferenceRequest());
            marketingData.setGIN(request.getIndividualRequest().getIndividualInformations().getIdentifier());
            AccountDataDTO accountDataDTO = new AccountDataDTO();
            accountDataDTO.setSgin(request.getIndividualRequest().getIndividualInformations().getIdentifier());

            // Call BDM only if other blocks expect TravelDoc and Apis are filled
            if ((marketingData.getEmergencyContactList() == null || marketingData.getEmergencyContactList().isEmpty()) &&
                    (marketingData.getHandicap() == null) &&
                    (marketingData.getPersonalInformation() == null) &&
                    (marketingData.getTravelCompanionV2List() == null || marketingData.getTravelCompanionV2List().isEmpty()) &&
                    (marketingData.getTravelPreferences() == null) &&
                    (marketingData.getTutorUMList() == null || marketingData.getTutorUMList().isEmpty())) {

                log.info("No BDM data to process");
                return;
            }

            log.info("GIN enabled : {}",request.getIndividualRequest().getIndividualInformations().getIdentifier());

            List<AccountDataDTO> listAccountData = accountDataDS.findByExample(accountDataDTO);
            if (listAccountData.size() == 1) {
                accountDataDTO = listAccountData.get(0);
            } else {
                if (log.isWarnEnabled()) {
                    log.warn("We found {} accountDataDTO but we should have found 1, verify the problem.",listAccountData.size());
                }
                accountDataDTO = null;
            }

            // MyAccount DOES NOT EXIST FOR BDM AS THERE IS NO ACCOUNT ID
            if (accountDataDTO == null || accountDataDTO.getAccountIdentifier() == null) {
                throw new NotFoundException("No account ID -> does not exist in the BDM");
            }
            marketingData.setMyAccountId(accountDataDTO.getAccountIdentifier());
            mdRequest.setMarketingDataV2(marketingData);

            try {

                storeMarketingPreferenceCustomer.storeMarketingDataV2(mdRequest);

            } catch (MarketingDataBusinessException e) {
                log.error("Error when calling storeMarketingDataV2 from webservice", e);
                throw new MarketingErrorException(e.getFaultInfo().getErrorCode().toString(), e);
            } catch (SystemException e) {
                log.error("Error when calling storeMarketingDataV2 webservice", e);
                throw new MarketingErrorException(e.getMessage());
            } catch (Exception e) {
                log.error("Error when calling storeMarketingDataV2 webservice", e);
                throw new MarketingErrorException(e.getMessage().toString());
            }
        }
    }

    public String getConsumerId() throws SOAPException {
        Header header = ReadSoaHeaderHelper.getHeaderFromContext(context, "trackingMessageHeader");
        String consumerID = ReadSoaHeaderHelper.getHeaderChildren(header, "consumerID");

        return consumerID;
    }


}
