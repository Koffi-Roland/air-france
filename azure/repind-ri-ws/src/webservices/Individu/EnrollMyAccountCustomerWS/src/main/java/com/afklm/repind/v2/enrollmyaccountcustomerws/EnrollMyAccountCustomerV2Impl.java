/**
 *
 */
package com.afklm.repind.v2.enrollmyaccountcustomerws;

import com.afklm.repind.enrollmyaccountcustomerws.helpers.EnrollMyAccountCustomerHelperCommon;
import com.afklm.repind.utils.EnrollMyAccountCustomerMapperV2;
import com.afklm.repind.utils.EnrollMyAccountUtils;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000431.v2.BusinessException;
import com.afklm.soa.stubs.w000431.v2.EnrollMyAccountCustomerServiceV2;
import com.afklm.soa.stubs.w000431.v2.data.BusinessError;
import com.afklm.soa.stubs.w000431.v2.data.BusinessErrorEnum;
import com.afklm.soa.stubs.w000431.v2.data.EnrollMyAccountCustomerRequest;
import com.afklm.soa.stubs.w000431.v2.data.EnrollMyAccountCustomerResponse;
import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.AirlineCodeEnum;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerRequestDTO;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerResponseDTO;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.repind.service.consent.internal.ConsentDS;
import com.airfrance.repind.service.encryption.internal.EncryptionDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.marketing.HandleCommunication;
import com.airfrance.repind.util.EnrollConstant;
import com.airfrance.repind.util.LoggerUtils;
import com.airfrance.repind.util.SicStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.jws.WebService;


/**
 * <p>Title : EnrollMyAccountCustomerV2Impl.java</p>
 *
 * <p>Copyright : Copyright (c) 2016</p>
 * <p>Company : AIRFRANCE</p>
 *
 * @author t490195
 */
@Service("passenger_EnrollMyAccountCustomer-v02Bean")
@WebService(endpointInterface="com.afklm.soa.stubs.w000431.v2.EnrollMyAccountCustomerServiceV2", targetNamespace = "http://www.af-klm.com/services/passenger/EnrollMyAccountCustomerService-v2/wsdl", serviceName = "EnrollMyAccountCustomerServiceService-v2", portName = "EnrollMyAccountCustomerService-v2-soap11http")
@Slf4j
public class EnrollMyAccountCustomerV2Impl implements EnrollMyAccountCustomerServiceV2 {

	@Autowired
	private MyAccountDS myAccountDS = null;
	
	@Autowired
	private ConsentDS consentDS;

	@Autowired
	ApplicationContext appContext;

	@Autowired
	private EnrollMyAccountCustomerMapperV2 enrollMyAccountCustomerMapperV2;

	/* (non-Javadoc)
	 * @see com.afklm.sic.ws.enrollmyaccountcustomer.EnrollMyAccountCustomerServiceV2#enrollMyAccountCustomer(com.afklm.sic.ws.enrollmyaccountcustomer.enrollmyaccountcustomertype.EnrollAccountCustomerRequest)
	 */
	@Override
	public EnrollMyAccountCustomerResponse enrollMyAccountCustomer(	EnrollMyAccountCustomerRequest request) throws BusinessException, SystemException {

		validateRequest(request);

		MyAccountCustomerRequestDTO enrollData = enrollMyAccountCustomerMapperV2.wsRequestToMyAccountCommon(request);

		log.info("EnrollMyAccountCustomerWS : begin");

		validateEnrollData(enrollData);

		log.info("Signature : site : {}",request.getSignatureLight().getSite());
		log.info("Signature : signature : {}",request.getSignatureLight().getSignature());
		log.info("Signature : ipAddress : {}",request.getSignatureLight().getIpAddress());

		EnrollMyAccountCustomerResponse response = null;

		// check Email identifier data
		String email = enrollData.getEmailIdentifier();
		validateEmail(email);

		try {
			// REPIND-2066: set default language to French
			if (enrollData.getLanguage() == null || EnrollConstant.EMPTY_STRING.equals(enrollData.getLanguage())) {
				enrollData.setLanguage(EnrollConstant.LANG_FRENCH);
			}

			MyAccountCustomerResponseDTO responseEnroll = myAccountDS.enrollMyAccountCustomer(enrollData, true, false);
			
			//REPIND-1647: Create Default Consent when creating new individual
			createDefaultConsent(responseEnroll.getGin(), enrollData.getSignature().getSignature());

			response = enrollMyAccountCustomerMapperV2.myAccountCommonToWsResponse(responseEnroll);

			// Send welcome email from CRMPush for AF and KL
			if (!AirlineCodeEnum.BB.getCode().equalsIgnoreCase(enrollData.getWebsite())) { // Website is not BB
				EnrollMyAccountUtils.callHandleCommunicationAccountEnrollService(response.getGin(), enrollData, appContext);
			}
		} catch (JrafDomainException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			BusinessError errorCode = new BusinessError();
			if (e instanceof ContractExistException) {
				// CONTRACT_EXIST ERROR
				errorCode.setErrorCode(BusinessErrorEnum.ERR_134.value());
				errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_134, "EN"));
			} else if (e instanceof SeveralIndividualException) {
				//SEVERAL_INDIVIDUAL
				errorCode.setErrorCode(BusinessErrorEnum.ERR_903.value());
				errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_903, "EN"));
			} else if (e instanceof AlreadyExistException) {
				//ACCOUNT_EXIST ERROR
				errorCode.setErrorCode(BusinessErrorEnum.ERR_384.value());
				errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_384, "EN"));
			} else if (e instanceof InvalidParameterException) {
				//INVALID PARAMETER ERROR
				errorCode.setErrorCode(BusinessErrorEnum.ERR_932.value());
				errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_932, "EN"));
				errorCode.setMissingParameter(e.getMessage());
			} else if (e instanceof BadPasswordException) {
				//BAD PASSWORD ERROR
				errorCode.setErrorCode(BusinessErrorEnum.ERR_530.value());
				errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_530, "EN"));
			} else {
				// ADHESION ERROR
				errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
				errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
			}
			if(log.isInfoEnabled()) {
				log.info("EnrollMyAccountCustomerWS : {} : {}",errorCode.getErrorCode(),errorCode.getFaultDescription());
			}

			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}
		if(log.isInfoEnabled()) {
			log.info("EnrollMyAccountCustomerWS : end");
		}
		return response;
	}

	private void validateEmail(String email) throws BusinessException {
		if (!SicStringUtils.isValidEmail(email)) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_932.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_932, "EN"));
			errorCode.setMissingParameter("emailidentifier");

			log.error("EnrollMyAccountCustomerWS : {} : {}",errorCode.getErrorCode(),errorCode.getFaultDescription());

			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}
	}

	private void validateEnrollData(MyAccountCustomerRequestDTO enrollData) throws BusinessException {
		if (enrollData.getSignature().getSignature() == null || enrollData.getSignature().getSignature().length() == 0) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_111.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_111, "EN"));

			log.error("{} : {}",errorCode.getErrorCode(),errorCode.getFaultDescription());

			throw new BusinessException(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_111, "EN"), errorCode);
		}
	}

	private void validateRequest(EnrollMyAccountCustomerRequest request) throws BusinessException {
		if(request == null){
			log.error("request is null");
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_932.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_932, "EN"));
			errorCode.setMissingParameter("request is null");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}

		log.info("parameters:");
		log.info("gin             : {}",request.getGin());
		log.info("civility        : {}",request.getCivility());
		log.info("firstName       : {}",request.getFirstName());
		log.info("lastName        : {}",request.getLastName());
		log.info("emailIdentifier : {}",request.getEmailIdentifier());
		log.info("languageCode    : {}",request.getLanguageCode());
		log.info("website         : {}",request.getWebsite());
		log.info("pointOfSale     : {}",request.getPointOfSale());
		log.info("optIn           : {}",request.isOptIn());
		log.info("directFBEnroll  : {}",request.isDirectFBEnroll());
	}

	private void createDefaultConsent(String gin, String signature) {
		try {
			consentDS.createDefaultConsents(gin, signature);
		} catch (Exception e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
		}
	}

	public void setConsentDS(ConsentDS consentDS) {
		this.consentDS = consentDS;
	}
}
