/**
 *
 */
package com.afklm.repind.v3.enrollmyaccountcustomerws;

import com.afklm.repind.enrollmyaccountcustomerws.helpers.EnrollMyAccountCustomerHelperCommon;
import com.afklm.repind.utils.EnrollMyAccountCustomerMapperV3;
import com.afklm.repind.utils.EnrollMyAccountUtils;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000431.v3.BusinessException;
import com.afklm.soa.stubs.w000431.v3.data.BusinessError;
import com.afklm.soa.stubs.w000431.v3.data.BusinessErrorEnum;
import com.afklm.soa.stubs.w000431.v3.data.EnrollMyAccountCustomerRequest;
import com.afklm.soa.stubs.w000431.v3.data.EnrollMyAccountCustomerResponse;
import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.AirlineCodeEnum;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerRequestDTO;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerResponseDTO;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.repind.service.consent.internal.ConsentDS;
import com.airfrance.repind.service.encryption.internal.EncryptionDS;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.marketing.HandleCommunication;
import com.airfrance.repind.util.LoggerUtils;
import com.airfrance.repind.util.PwdContainer;
import com.airfrance.repind.util.SicStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.Calendar;
import java.util.List;


/**
 * <p>Title : EnrollMyAccountCustomerV3Impl.java</p>
 *
 * <p>Copyright : Copyright (c) 2018</p>
 * <p>Company : AIRFRANCE</p>
 *
 * @author t838155
 */
@Service("passenger_EnrollMyAccountCustomer-v03Bean")
@WebService(endpointInterface="com.afklm.soa.stubs.w000431.v3.EnrollMyAccountCustomerServiceV3", targetNamespace = "http://www.af-klm.com/services/passenger/EnrollMyAccountCustomerService-v3/wsdl", serviceName = "EnrollMyAccountCustomerServiceService-v3", portName = "EnrollMyAccountCustomerService-v3-soap11http")
@Slf4j
public class EnrollMyAccountCustomerV3Impl implements com.afklm.soa.stubs.w000431.v3.EnrollMyAccountCustomerServiceV3 {

	@Autowired
	private MyAccountDS myAccountDS = null;

	@Autowired
	private ConsentDS consentDS;

	@Autowired
	ApplicationContext appContext;

	@Autowired
	private EnrollMyAccountCustomerMapperV3 enrollMyAccountCustomerMapperV3;

	public void setConsentDS(ConsentDS consentDS) {
		this.consentDS = consentDS;
	}

	@Override
	public EnrollMyAccountCustomerResponse enrollMyAccountCustomer(EnrollMyAccountCustomerRequest request)
			throws BusinessException, SystemException {

		validateRequest(request);

		MyAccountCustomerRequestDTO enrollData = enrollMyAccountCustomerMapperV3.wsRequestToMyAccountCommon(request);
		validateEnrollData(enrollData);

		log.info("Signature : site : {}",request.getRequestor().getSite());
		log.info("Signature : signature : {}",request.getRequestor().getSignature());
		log.info("Signature : ipAddress : {}",request.getRequestor().getIpAddress());

		EnrollMyAccountCustomerResponse response = null;

		// check Email identifier data
		String email = enrollData.getEmailIdentifier();
		validateEmail(email);

		try {

			MyAccountCustomerResponseDTO responseEnroll = myAccountDS.enrollMyAccountCustomer(enrollData, false, true);
			
			//REPIND-1647: Create Default Consent when creating new individual
			createDefaultConsent(responseEnroll.getGin(), enrollData.getSignature().getSignature());

			response = enrollMyAccountCustomerMapperV3.myAccountCommonToWsResponse(responseEnroll);

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

			log.error("EnrollMyAccountCustomerWS : {} : {}",errorCode.getErrorCode(),errorCode.getFaultDescription());

			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}

		log.info("EnrollMyAccountCustomerWS : end");

		return response;

	}

	private void createDefaultConsent(String gin, String signature) {
		try {
			consentDS.createDefaultConsents(gin, signature);
		} catch (Exception e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
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

		log.info("EnrollMyAccountCustomerWS : begin");
		log.info("parameters:");
		log.info("gin             : {}",request.getGin());
		log.info("civility        : {}",request.getCivility());
		log.info("firstName       : {}",request.getFirstName());
		log.info("lastName        : {}",request.getLastName());
		log.info("emailIdentifier : {}",request.getEmailIdentifier());
		log.info("languageCode    : {}",request.getLanguageCode());
		log.info("website         : {}",request.getWebsite());
	}

	private void validateEnrollData(MyAccountCustomerRequestDTO enrollData) throws BusinessException {
		if (enrollData.getSignature().getSignature() == null || enrollData.getSignature().getSignature().length() == 0) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_111.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_111, "EN"));

			if(log.isInfoEnabled()) {
				log.info("{} : {}",errorCode.getErrorCode(),errorCode.getFaultDescription());
			}
			throw new BusinessException(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_111, "EN"), errorCode);
		}
	}

	private void validateEmail(String email) throws BusinessException {
		if (!SicStringUtils.isValidEmail(email)) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_932.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_932, "EN"));
			errorCode.setMissingParameter("emailidentifier");

			log.info("EnrollMyAccountCustomerWS : {} : {}",errorCode.getErrorCode(),errorCode.getFaultDescription());

			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}
	}
}
