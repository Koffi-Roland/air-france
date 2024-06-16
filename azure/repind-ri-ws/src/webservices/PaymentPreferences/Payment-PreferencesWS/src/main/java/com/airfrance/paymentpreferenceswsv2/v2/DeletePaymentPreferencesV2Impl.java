package com.airfrance.paymentpreferenceswsv2.v2;

import com.afklm.repindpp.paymentpreference.service.internal.PaymentPreferencesDS;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000471.v2.BusinessException;
import com.afklm.soa.stubs.w000471.v2.DeletePaymentPreferencesV2;
import com.afklm.soa.stubs.w000471.v2.deletepaymentpreferencesschema.BusinessError;
import com.afklm.soa.stubs.w000471.v2.deletepaymentpreferencesschema.BusinessErrorEnum;
import com.afklm.soa.stubs.w000471.v2.deletepaymentpreferencesschema.DeletePaymentPreferencesRequest;
import com.afklm.soa.stubs.w000471.v2.deletepaymentpreferencesschema.DeletePaymentPreferencesResponse;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;

@WebService(endpointInterface="com.afklm.soa.stubs.w000471.v2.DeletePaymentPreferencesV2", targetNamespace = "http://www.af-klm.com/services/passenger/DeletePaymentPreferences-v2/wsdl", serviceName = "DeletePaymentPreferencesService-v2", portName = "DeletePaymentPreferences-v2-soap11http")
public class DeletePaymentPreferencesV2Impl implements DeletePaymentPreferencesV2 {
	
	/*
	 * IPaymentPreferencesDS reference which support the operation needed by the service
	 * implementation. This ref. is injected by spring.
	 */
	@Autowired
	private PaymentPreferencesDS paymentPreferencesDS;
	
	public PaymentPreferencesDS getPaymentPreferencesDS() {
		return paymentPreferencesDS;
	}

	public void setPaymentPreferencesDS(PaymentPreferencesDS paymentPreferencesDS) {
		this.paymentPreferencesDS = paymentPreferencesDS;
	}

	public DeletePaymentPreferencesResponse deletePaymentPreferences(DeletePaymentPreferencesRequest request) throws BusinessException, SystemException {
		
		if(isEmptyInput(request.getPaymentId())) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_002.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_001, "EN"));
			errorCode.setMissingParameter("PaymentId");
			throw new BusinessException(errorCode.getFaultDescription(),errorCode);
		}
		/*if(isEmptyInput(request.getSignature())) {
			missingSignature("Signature");
		}
		if(isEmptyInput(request.getSignatureSite())) {
			missingSignature("Signature Site");
		}
		if(isEmptyInput(request.getIpAdress())) {
			missingSignature("Ip Adresse");
		}*/
		
		DeletePaymentPreferencesResponse response = new DeletePaymentPreferencesResponse();
		
		try {
			if(paymentPreferencesDS.deletePaymentPreferencesByPaymentId(request.getPaymentId())) {
				response.setDeletionStatus("O");
			} else {
				BusinessError errorCode = new BusinessError();
				errorCode.setErrorCode(BusinessErrorEnum.ERR_002.value());
				errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_002, "EN"));
				errorCode.setMissingParameter("PaymentId");
				throw new BusinessException(errorCode.getFaultDescription(), errorCode);
			}
		} catch (JrafDomainException e) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
			errorCode.setMissingParameter("Technical Error : "+e);
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
			
		}
		catch (BusinessException e) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_002.value());
			errorCode.setFaultDescription("No payment found with this paymentId");
			errorCode.setMissingParameter("PaymentId");
			throw new BusinessException("No payment found with this paymentId", errorCode);
		}
		catch(org.hibernate.exception.GenericJDBCException e){
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_133, "EN"));
			errorCode.setFaultDescription("Technical Error, JDBC Exception : "+e);
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}
		catch(javax.persistence.PersistenceException e){
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_133, "EN"));
			errorCode.setFaultDescription("Technical Error, JDBC Exception : "+e);
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}
		catch(Exception e){
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_133, "EN"));
			errorCode.setFaultDescription("Technical Error, JDBC Exception : "+e);
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}
		return response;
	}

	public boolean isEmptyInput(String input) {
		if(input == null || input.equalsIgnoreCase("?") || input.equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}
	
	public void missingSignature(String param) throws BusinessException{
		BusinessError errorCode = new BusinessError();
		errorCode.setErrorCode(BusinessErrorEnum.ERR_111.value());
		errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_111, "EN"));
		errorCode.setMissingParameter(param);
		throw new BusinessException(errorCode.getFaultDescription(), errorCode);
	}
}
