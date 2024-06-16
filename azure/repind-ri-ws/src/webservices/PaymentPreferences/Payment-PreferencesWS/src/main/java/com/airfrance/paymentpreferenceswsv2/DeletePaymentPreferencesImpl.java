package com.airfrance.paymentpreferenceswsv2;

import com.afklm.repindpp.paymentpreference.dto.PaymentsDetailsDTO;
import com.afklm.repindpp.paymentpreference.service.internal.PaymentPreferencesDS;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000471.v1.BusinessException;
import com.afklm.soa.stubs.w000471.v1.DeletePaymentPreferencesV1;
import com.afklm.soa.stubs.w000471.v1.deletepaymentpreferencesschema.BusinessError;
import com.afklm.soa.stubs.w000471.v1.deletepaymentpreferencesschema.BusinessErrorEnum;
import com.afklm.soa.stubs.w000471.v1.deletepaymentpreferencesschema.DeletePaymentPreferencesRequest;
import com.afklm.soa.stubs.w000471.v1.deletepaymentpreferencesschema.DeletePaymentPreferencesResponse;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface="com.afklm.soa.stubs.w000471.v1.DeletePaymentPreferencesV1", targetNamespace = "http://www.af-klm.com/services/passenger/DeletePaymentPreferences-v1/wsdl", serviceName = "DeletePaymentPreferencesService-v1", portName = "DeletePaymentPreferences-v1-soap11http")
public class DeletePaymentPreferencesImpl implements DeletePaymentPreferencesV1 {
	
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
		
		//		Check inputs :
		/************************/
		if(request.getGin().equalsIgnoreCase("")){
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_001.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_001, "EN"));
			errorCode.setMissingParameter("Gin");
			throw new BusinessException(errorCode.getFaultDescription(),errorCode);
		}
		if(request.getSignature().equalsIgnoreCase(""))
			missingSignature("Signature");
		if(request.getSignatureSite().equalsIgnoreCase(""))
			missingSignature("Signature Site");
		if(request.getIpAdress().equalsIgnoreCase(""))
			missingSignature("Ip Adresse");
		/************************/
		
		DeletePaymentPreferencesResponse response = new DeletePaymentPreferencesResponse();
		try {
			PaymentsDetailsDTO pDetails = null;
			List<PaymentsDetailsDTO> dtoList = null;
			try {
				dtoList = paymentPreferencesDS.findByGin(request.getGin());
				if(dtoList.size()>0){
					PaymentsDetailsDTO dtoToReturn = null;
					for(PaymentsDetailsDTO dto : dtoList) {
						if(dto.getPreferred() != null && dto.getPreferred().equals("Y")) {
							pDetails = dto;
						}
					}
					if(pDetails == null) {
						for(PaymentsDetailsDTO dto : dtoList) {
							if(dtoToReturn == null || dto.getPaymentId() > dtoToReturn.getPaymentId()) {
								pDetails = dto;
							}
						}
					}
				}
			}
			catch (JrafDomainException e) {
				BusinessError errorCode = new BusinessError();
				errorCode.setErrorCode(BusinessErrorEnum.ERR_001.value());
				errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_001, "EN"));
				errorCode.setMissingParameter("Gin");
				throw new BusinessException(errorCode.getFaultDescription(), errorCode);
			}
			if(pDetails != null && paymentPreferencesDS.deletePaymentPreferencesByPaymentId(pDetails.getPaymentId()+"")){
				response.setDeletionStatus("O");
			}else{
				BusinessError errorCode = new BusinessError();
				errorCode.setErrorCode(BusinessErrorEnum.ERR_001.value());
				errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_001, "EN"));
				errorCode.setMissingParameter("Gin");
				throw new BusinessException(errorCode.getFaultDescription(), errorCode);
				//response.setDeletionStatus("N");
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
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_133, "EN"));
			errorCode.setFaultDescription("Business Exception: "+e);
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
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
	public void missingSignature(String param) throws BusinessException{
		BusinessError errorCode = new BusinessError();
		errorCode.setErrorCode(BusinessErrorEnum.ERR_111.value());
		errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_111, "EN"));
		errorCode.setMissingParameter(param);
		throw new BusinessException(errorCode.getFaultDescription(), errorCode);
	}
}
