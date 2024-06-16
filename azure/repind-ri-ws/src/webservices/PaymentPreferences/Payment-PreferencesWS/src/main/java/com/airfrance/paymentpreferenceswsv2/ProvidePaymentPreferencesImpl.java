package com.airfrance.paymentpreferenceswsv2;

import com.afklm.repindpp.paymentpreference.dto.FieldsDTO;
import com.afklm.repindpp.paymentpreference.dto.PaymentsDetailsDTO;
import com.afklm.repindpp.paymentpreference.encoding.AES;
import com.afklm.repindpp.paymentpreference.service.internal.PaymentPreferencesDS;
import com.afklm.repindpp.paymentpreference.service.internal.VariablesPPDS;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000469.v1.BusinessException;
import com.afklm.soa.stubs.w000469.v1.ProvidePaymentPreferencesV1;
import com.afklm.soa.stubs.w000469.v1.providedecryptedpaymentpreferencesschema.ProvideFields;
import com.afklm.soa.stubs.w000469.v1.providedecryptedpaymentpreferencesschema.ProvidePaymentPreferencesRequest;
import com.afklm.soa.stubs.w000469.v1.providedecryptedpaymentpreferencesschema.ProvidePaymentPreferencesResponse;
import com.afklm.soa.stubs.w000469.v1.providepaymentpreferencesschema.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.jws.WebService;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@WebService(endpointInterface="com.afklm.soa.stubs.w000469.v1.ProvidePaymentPreferencesV1", targetNamespace = "http://www.af-klm.com/services/passenger/ProvidePaymentPreferences-v1/wsdl", serviceName = "ProvidePaymentPreferencesService-v1", portName = "ProvidePaymentPreferences-v1-soap11http")
@Slf4j
public class ProvidePaymentPreferencesImpl implements ProvidePaymentPreferencesV1{

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

	public VariablesPPDS getVariablesPPDS() {
		return variablesPPDS;
	}

	public void setVariablesPPDS(VariablesPPDS variablesPPDS) {
		this.variablesPPDS = variablesPPDS;
	}

	@Autowired
	private VariablesPPDS variablesPPDS;

	/* We don't need to call amadeus service - REPIND-270 */

	public int doWeMaske(String pFieldCode){
		if(pFieldCode.equalsIgnoreCase("CreditCardNumber")
				||	pFieldCode.equalsIgnoreCase("TransfertAccountNumber")
				||	pFieldCode.equalsIgnoreCase("CC_NR")
				||	pFieldCode.equalsIgnoreCase("DD_ACCOUNTNUMBER")
				||	pFieldCode.equalsIgnoreCase("OB_ACC_NR")
				||	pFieldCode.equalsIgnoreCase("DD_IBAN")
				||  pFieldCode.equalsIgnoreCase("PCI_TOKEN")
				){
			return 1;
		}
		else if(pFieldCode.equalsIgnoreCase("Fiscal Number")
				||	pFieldCode.equalsIgnoreCase("FiscalNumber")){
			return 2;
		} else {
			return 0;
		}
	}

	@Override
	public ProvidePaymentPreferencesResponse providePaymentPreferences(ProvidePaymentPreferencesRequest request) throws BusinessException, SystemException {
		ProvidePaymentPreferencesResponse result = new ProvidePaymentPreferencesResponse();

		String isPCIDSS, isFullPCIDSS;
		try {
			isPCIDSS = variablesPPDS.isPCIDSSon();
			isFullPCIDSS = variablesPPDS.isFullPCIDSS();
		} catch (JrafDomainException e) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN")+": invalid paymentFieldPreferences");
			errorCode.setMissingParameter("Please Contact IT : PPDS variable not initialized");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}

		List<PaymentsDetailsDTO> dtoList = null;
		String gin = request.getGin();
		if("".equalsIgnoreCase(gin)){
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_133.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_133, "EN"));
			errorCode.setMissingParameter("gin");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}
		try {
			dtoList = paymentPreferencesDS.findByGin(gin);
			if(dtoList.size()>0){
				PaymentsDetailsDTO dtoToReturn = null;
				for(PaymentsDetailsDTO dto : dtoList) {
					if(dto.getPreferred() != null && dto.getPreferred().equals("Y")) {
						dtoToReturn = dto;
					}
				}
				if(dtoToReturn == null) {
					for(PaymentsDetailsDTO dto : dtoList) {
						if(dtoToReturn == null || dto.getPaymentId() > dtoToReturn.getPaymentId()) {
							dtoToReturn = dto;
						}
					}
				}

				result.setAirlinePaymentPref(dtoToReturn.getCarrier());
				result.setPaymentGroupType(dtoToReturn.getPaymentGroup());
				result.setPaymentMethod(dtoToReturn.getPaymentMethod());
				result.setPointOfSale(dtoToReturn.getPointOfSell());

				//Declaration of cipher Decryption:
				/* We don't need to call these service - REPIND-270*/
				for(FieldsDTO fieldsDTO : dtoToReturn.getFieldsdto()){
					ProvideFields pField = new ProvideFields();
					ProvideFields pField_pci = new ProvideFields();

					try{
						String paymentFieldCode = AES.decrypt(fieldsDTO.getPaymentFieldCode());
						String paymentFieldPreference = AES.decrypt(fieldsDTO.getPaymentFieldPreference());

						pField.setPaymentFieldPreferences(paymentFieldPreference);
						pField.setPaymentFieldCode(paymentFieldCode);

						// workaround
						ProvideFields pField_tmp = new ProvideFields();

						if (paymentFieldCode.equals("BILL_FIRSTNAME")){
							pField_tmp.setPaymentFieldCode("BILL_FIRST_NAME");
							pField_tmp.setPaymentFieldPreferences(paymentFieldPreference);
							result.getProvidefields().add(pField_tmp);
						}else if(paymentFieldCode.equals("BILL_FIRST_NAME")){
							pField_tmp.setPaymentFieldCode("BILL_FIRSTNAME");
							pField_tmp.setPaymentFieldPreferences(paymentFieldPreference);
							result.getProvidefields().add(pField_tmp);
						}




						//Non numeric fieldPref are not allowed for paymentGroup CC and DC, except PCIDSS cases
						if ((result.getPaymentGroupType().equalsIgnoreCase("CC") || result.getPaymentGroupType().equalsIgnoreCase("DC"))){
							if (paymentFieldCode.equalsIgnoreCase("CC_NR") && !isFullPCIDSS.equals("Y")){
								// phase 2 = only CC numbers in database
								// phase 3 = CC and tokens in database
								if (this.isNumeric(paymentFieldPreference)){

									pField.setPaymentFieldPreferences(paymentFieldPreference);
									pField.setPaymentFieldCode("CC_NR");

									if (isPCIDSS.equals("Y")){

										// Request construction start
										// --------------------------
										pField_pci.setPaymentFieldCode("PCI_TOKEN");
										/* We don't need to call these service - REPIND-270 */
										String pci_token = "";
										/**/
										pField_pci.setPaymentFieldPreferences(pci_token);
										result.getProvidefields().add(pField_pci);
									}


								}else{
									/* We don't need to call the amadeus service - REPIND-270*/
									pField.setPaymentFieldPreferences(paymentFieldPreference);
									pField.setPaymentFieldCode("CC_NR");

								}
							}
						}

						//result.getProvidefields().add(pField_pci);
						if(!pField.getPaymentFieldCode().equalsIgnoreCase("CC_NR") ||
								(pField.getPaymentFieldCode().equalsIgnoreCase("CC_NR") && !isFullPCIDSS.equalsIgnoreCase("Y")) &&
								!result.getProvidefields().contains(pField)) {
							result.getProvidefields().add(pField);
						}
					}
					catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
							| UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
						log.error("Problem with the encryption operation: ",e);
					}
				}

			}
			else{
				//GIN INEXISTANT
				BusinessError errorCode = new BusinessError();
				errorCode.setErrorCode(BusinessErrorEnum.ERR_001.value());
				errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_001, "EN"));
				errorCode.setMissingParameter("gin");
				throw new BusinessException(errorCode.getFaultDescription(), errorCode);
			}

		}
		catch (JrafDomainException e) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
			errorCode.setMissingParameter("Technical Error : "+e);
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


		return result;
	}

	@Override
	public ProvideMaskedPaymentPreferencesResponse provideMaskedPaymentPreferences(ProvideMaskedPaymentPreferencesRequest request) throws BusinessException, SystemException {
		ProvideMaskedPaymentPreferencesResponse result = new ProvideMaskedPaymentPreferencesResponse();

		String isPCIDSS, isFullPCIDSS;
		try {
			isPCIDSS = variablesPPDS.isPCIDSSon();
			isFullPCIDSS = variablesPPDS.isFullPCIDSS();
		} catch (JrafDomainException e) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN")+": invalid paymentFieldPreferences");
			errorCode.setMissingParameter("Please Contact IT : PPDS variable not initialized");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}

		List<PaymentsDetailsDTO> dtoList = null;
		String gin = request.getGin();
		if("".equalsIgnoreCase(gin)){
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_133.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_133, "EN"));
			errorCode.setMissingParameter("gin");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}
		try {
			dtoList = paymentPreferencesDS.findByGin(gin);
			if(dtoList.size()>0){
				PaymentsDetailsDTO dtoToReturn = null;
				for(PaymentsDetailsDTO dto : dtoList) {
					if(dto.getPreferred() != null && dto.getPreferred().equals("Y")) {
						dtoToReturn = dto;
					}
				}
				if(dtoToReturn == null) {
					for(PaymentsDetailsDTO dto : dtoList) {
						if(dtoToReturn == null || dto.getPaymentId() > dtoToReturn.getPaymentId()) {
							dtoToReturn = dto;
						}
					}
				}

				result.setAirlinePaymentPref(dtoToReturn.getCarrier());
				result.setPaymentGroupType(dtoToReturn.getPaymentGroup());
				result.setPaymentMethod(dtoToReturn.getPaymentMethod());
				result.setPointOfSale(dtoToReturn.getPointOfSell());

				for(FieldsDTO fieldsDTO : dtoToReturn.getFieldsdto()){
					ProvideMaskedFields pField = new ProvideMaskedFields();
					try{
						String fieldCode = AES.decrypt(fieldsDTO.getPaymentFieldCode());
						String fieldPref = AES.decrypt(fieldsDTO.getPaymentFieldPreference());

						// workaround
						ProvideMaskedFields pField_tmp = new ProvideMaskedFields();

						if (fieldCode.equals("BILL_FIRSTNAME")){
							pField_tmp.setPaymentFieldCode("BILL_FIRST_NAME");
							pField_tmp.setPaymentFieldPreferences(fieldPref);
							result.getProvidemaskedfields().add(pField_tmp);
						}else if(fieldCode.equals("BILL_FIRST_NAME")){
							pField_tmp.setPaymentFieldCode("BILL_FIRSTNAME");
							pField_tmp.setPaymentFieldPreferences(fieldPref);
							result.getProvidemaskedfields().add(pField_tmp);
						}

						pField.setPaymentFieldCode(fieldCode);
						pField.setPaymentFieldPreferences(fieldPref);

						//Non numeric fieldPref are not allowed for paymentGroup CC and DC, except PCIDSS cases
						if ((result.getPaymentGroupType().equalsIgnoreCase("CC") || result.getPaymentGroupType().equalsIgnoreCase("DC"))){
							if (fieldCode.equalsIgnoreCase("CC_NR")){
								// phase 2 = only CC numbers in database
								if (this.isNumeric(fieldPref)){
									if (isPCIDSS.equals("Y") || isFullPCIDSS.equalsIgnoreCase("Y")){
										// Request construction start
										// --------------------------
										ProvideMaskedFields pField_pci = new ProvideMaskedFields();
										pField_pci.setPaymentFieldCode("PCI_TOKEN");

										String pci_token = "";
										pci_token = fieldPref;

										pField_pci.setPaymentFieldPreferences(pci_token);
										result.getProvidemaskedfields().add(pField_pci);

										if(doWeMaske(pField_pci.getPaymentFieldCode())==1) {
											pField_pci.setPaymentFieldPreferences(masking(fieldPref,false));
										} else if(doWeMaske(pField_pci.getPaymentFieldCode())==2) {
											pField_pci.setPaymentFieldPreferences(masking(fieldPref,false));
										} else {
											pField_pci.setPaymentFieldPreferences(fieldPref);
										}
									}

								}else{
									if (isPCIDSS.equals("Y") || isFullPCIDSS.equalsIgnoreCase("Y")){
										// Request construction start
										// --------------------------
										ProvideMaskedFields pField_pci = new ProvideMaskedFields();
										pField_pci.setPaymentFieldCode("PCI_TOKEN");

										String pci_token = "";
										pci_token = fieldPref;

										pField_pci.setPaymentFieldPreferences(pci_token);
										result.getProvidemaskedfields().add(pField_pci);

										if(doWeMaske(pField_pci.getPaymentFieldCode())==1) {
											pField_pci.setPaymentFieldPreferences(masking(fieldPref,false));
										} else if(doWeMaske(pField_pci.getPaymentFieldCode())==2) {
											pField_pci.setPaymentFieldPreferences(masking(fieldPref,false));
										} else {
											pField_pci.setPaymentFieldPreferences(fieldPref);
										}
									}
									/*
								BusinessError errorCode = new BusinessError();
								errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
								errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN")+": invalid paymentFieldPreferences");
								errorCode.setMissingParameter("paymentFieldPreferences");
								throw new BusinessException(errorCode.getFaultDescription(), errorCode);*/
								}
							}
						}


						if(doWeMaske(pField.getPaymentFieldCode())==1) {
							pField.setPaymentFieldPreferences(
									masking(AES.decrypt(fieldsDTO.getPaymentFieldPreference()), false));
						} else if(doWeMaske(pField.getPaymentFieldCode())==2) {
							pField.setPaymentFieldPreferences(
									masking(AES.decrypt(fieldsDTO.getPaymentFieldPreference()), false));
						} else {
							pField.setPaymentFieldPreferences(
									AES.decrypt(fieldsDTO.getPaymentFieldPreference()));
						}

						if(!pField.getPaymentFieldCode().equalsIgnoreCase("CC_NR") ||
								(pField.getPaymentFieldCode().equalsIgnoreCase("CC_NR") && !isFullPCIDSS.equalsIgnoreCase("Y"))) {
							result.getProvidemaskedfields().add(pField);
						}
					}
					catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
							| UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
						log.error("Problem with the encryption operation: ",e);
					}
				}
			}
			else{
				//GIN INEXISTANT
				BusinessError errorCode = new BusinessError();
				errorCode.setErrorCode(BusinessErrorEnum.ERR_001.value());
				errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_001, "EN"));
				errorCode.setMissingParameter("gin");
				throw new BusinessException(errorCode.getFaultDescription(), errorCode);
			}
		}

		catch (JrafDomainException e) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
			errorCode.setMissingParameter("Technical Error: " + e);
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

		return result;
	}




	/**
	 * Masking unsing PCI DSS Methode or custome methode
	 */
	public String masking(String fieldPreferences,boolean f){
		int fieldSize = fieldPreferences.length();
		String result="";
		for(int i =0;i<fieldPreferences.length();i++){
			if(!(fieldPreferences.charAt(i)==' ')){
				if(i>5 && fieldSize>4 && f) {
					result += 'X';
				} else if (fieldSize>4 && !f) {
					result += 'X';
				} else {
					result += fieldPreferences.charAt(i);
				}
			} else {
				result += ' ';
			}
			fieldSize--;
		}
		return result;
	}

	public boolean isNumeric(String str)
	{
		return str.matches("^\\d+$");
	}

}
