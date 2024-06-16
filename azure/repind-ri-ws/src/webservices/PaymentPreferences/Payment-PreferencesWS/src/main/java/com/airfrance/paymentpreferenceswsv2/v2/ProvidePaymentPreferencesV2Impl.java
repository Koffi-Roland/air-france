package com.airfrance.paymentpreferenceswsv2.v2;

import com.afklm.repindpp.paymentpreference.dto.FieldsDTO;
import com.afklm.repindpp.paymentpreference.dto.PaymentsDetailsDTO;
import com.afklm.repindpp.paymentpreference.encoding.AES;
import com.afklm.repindpp.paymentpreference.service.internal.PaymentPreferencesDS;
import com.afklm.repindpp.paymentpreference.service.internal.VariablesPPDS;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000469.v2_0_1.BusinessException;
import com.afklm.soa.stubs.w000469.v2_0_1.ProvidePaymentPreferencesV2;
import com.afklm.soa.stubs.w000469.v2_0_1.providedecryptedpaymentpreferencesschema.ProvideFields;
import com.afklm.soa.stubs.w000469.v2_0_1.providedecryptedpaymentpreferencesschema.ProvidePaymentPreferences;
import com.afklm.soa.stubs.w000469.v2_0_1.providedecryptedpaymentpreferencesschema.ProvidePaymentPreferencesRequest;
import com.afklm.soa.stubs.w000469.v2_0_1.providedecryptedpaymentpreferencesschema.ProvidePaymentPreferencesResponse;
import com.afklm.soa.stubs.w000469.v2_0_1.providepaymentpreferencesschema.*;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@WebService(endpointInterface="com.afklm.soa.stubs.w000469.v2_0_1.ProvidePaymentPreferencesV2", targetNamespace = "http://www.af-klm.com/services/passenger/ProvidePaymentPreferences-v2/wsdl", serviceName = "ProvidePaymentPreferencesService-v2", portName = "ProvidePaymentPreferences-v2-soap11http")
@Slf4j
public class ProvidePaymentPreferencesV2Impl implements ProvidePaymentPreferencesV2 {
	@Autowired
	private VariablesPPDS variablesPPDS;


	public VariablesPPDS getVariablesPPDS() {
		return variablesPPDS;
	}


	public void setVariablesPPDS(VariablesPPDS variablesPPDS) {
		this.variablesPPDS = variablesPPDS;
	}


	public PaymentPreferencesDS getPaymentPreferencesDS() {
		return paymentPreferencesDS;
	}


	public void setPaymentPreferencesDS(PaymentPreferencesDS paymentPreferencesDS) {
		this.paymentPreferencesDS = paymentPreferencesDS;
	}

	/*
	 * IPaymentPreferencesDS reference which support the operation needed by the service
	 * implementation. This ref. is injected by spring.
	 */
	@Autowired
	private PaymentPreferencesDS paymentPreferencesDS;


	@Override
	public ProvidePaymentPreferencesResponse providePaymentPreferences(ProvidePaymentPreferencesRequest request) throws BusinessException, SystemException {
		ProvidePaymentPreferencesResponse response = new ProvidePaymentPreferencesResponse();

		String requestMode;

		List<PaymentsDetailsDTO> dtoList = null;
		if(isEmptyInput(request.getGin())){
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_133.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_133, "EN"));
			errorCode.setMissingParameter("Gin");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}

		if(isEmptyInput(request.getRequestMode())){
			request.setRequestMode("");
		}

		if(!request.getRequestMode().equalsIgnoreCase("N") && !request.getRequestMode().equalsIgnoreCase("P") &&
				!request.getRequestMode().equalsIgnoreCase("C") && !request.getRequestMode().equalsIgnoreCase("")
				&& request.getRequestMode() != null){
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_121.value());
			errorCode.setFaultDescription("INVALID REQUEST MODE");
			errorCode.setMissingParameter("Request Mode");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		} else {
			requestMode = request.getRequestMode();
		}

		try {
			// Retrieving payment preferences
			dtoList = paymentPreferencesDS.findByGin(request.getGin());
			// Checking response content
			// Checking response content
			if(dtoList.size()>0) {

				// Sorting depending on request mode
				// P (preferred) sorting
				if(requestMode.equalsIgnoreCase("P")) {
					// Although a gin should contain only one preferred payment,
					// we must handle possible exceptions
					List<PaymentsDetailsDTO> preferredPayments = new ArrayList<>(),
							corporateSubList = new ArrayList<>(),
							nonCorporateSubList = new ArrayList<>();

					for(PaymentsDetailsDTO dto : dtoList) {
						if(dto.getPreferred().equalsIgnoreCase("Y")) {
							preferredPayments.add(dto);
						} else {
							if(dto.getCorporate().equalsIgnoreCase("Y")) {
								corporateSubList.add(dto);
							} else {
								nonCorporateSubList.add(dto);
							}
						}
					}

					Collections.sort(preferredPayments, new Comparator<PaymentsDetailsDTO>(){
						@Override
						public int compare(PaymentsDetailsDTO p1, PaymentsDetailsDTO p2){
							return (int) (p2.getPaymentId() - p1.getPaymentId());
						}
					});

					Collections.sort(corporateSubList, new Comparator<PaymentsDetailsDTO>(){
						@Override
						public int compare(PaymentsDetailsDTO p1, PaymentsDetailsDTO p2){
							return (int) (p2.getPaymentId() - p1.getPaymentId());
						}
					});

					Collections.sort(nonCorporateSubList, new Comparator<PaymentsDetailsDTO>(){
						@Override
						public int compare(PaymentsDetailsDTO p1, PaymentsDetailsDTO p2){
							return (int) (p2.getPaymentId() - p1.getPaymentId());
						}
					});

					dtoList = new ArrayList<>();
					dtoList.addAll(preferredPayments);
					dtoList.addAll(nonCorporateSubList);
					dtoList.addAll(corporateSubList);
				}
				// C (corporate) sorting
				else if(requestMode.equalsIgnoreCase("C")) {
					List<PaymentsDetailsDTO> corporateSubList = new ArrayList<>(),
							nonCorporateSubList = new ArrayList<>();

					for(PaymentsDetailsDTO dto : dtoList) {
						if(dto.getCorporate().equalsIgnoreCase("Y")) {
							corporateSubList.add(dto);
						} else {
							nonCorporateSubList.add(dto);
						}
					}

					Collections.sort(corporateSubList, new Comparator<PaymentsDetailsDTO>(){
						@Override
						public int compare(PaymentsDetailsDTO p1, PaymentsDetailsDTO p2){
							Boolean b1 = (p1.getPreferred().equalsIgnoreCase("Y")) ? true : false;
							Boolean b2 = (p2.getPreferred().equalsIgnoreCase("Y")) ? true : false;
							int res =  Boolean.valueOf(b2).compareTo(Boolean.valueOf(b1));
							if(res == 0) {
								res = Long.compare(p2.getPaymentId(),p1.getPaymentId());
							}
							return res;
						}
					});

					Collections.sort(nonCorporateSubList, new Comparator<PaymentsDetailsDTO>(){
						@Override
						public int compare(PaymentsDetailsDTO p1, PaymentsDetailsDTO p2){
							Boolean b1 = (p1.getPreferred().equalsIgnoreCase("Y")) ? true : false;
							Boolean b2 = (p2.getPreferred().equalsIgnoreCase("Y")) ? true : false;
							int res =  Boolean.valueOf(b2).compareTo(Boolean.valueOf(b1));
							if(res == 0) {
								res = Long.compare(p2.getPaymentId(),p1.getPaymentId());
							}
							return res;
						}
					});

					dtoList = new ArrayList<>();
					dtoList.addAll(corporateSubList);
					dtoList.addAll(nonCorporateSubList);
				}
				// N (non booking) sorting
				else {
					Collections.sort(dtoList, new Comparator<PaymentsDetailsDTO>(){
						@Override
						public int compare(PaymentsDetailsDTO p1, PaymentsDetailsDTO p2){
							return (int) (p2.getPaymentId() - p1.getPaymentId());
						}
					});
				}

				// Recuperation de chaque preference de paiement (V2 : n payment preferences)
				for(int j = 0; j < dtoList.size() ; j++) {
					ProvidePaymentPreferences preferences = new ProvidePaymentPreferences();

					preferences.setAirlinePaymentPref(dtoList.get(j).getCarrier());
					preferences.setPaymentGroupType(dtoList.get(j).getPaymentGroup());
					preferences.setPaymentMethod(dtoList.get(j).getPaymentMethod());
					preferences.setPointOfSale(dtoList.get(j).getPointOfSell());
					preferences.setCorporate(dtoList.get(j).getCorporate());
					preferences.setPreferred(dtoList.get(j).getPreferred());
					preferences.setPaymentId(dtoList.get(j).getPaymentId()+"");
					if(dtoList.get(j).getCardName() == null || dtoList.get(j).getCardName().isEmpty()) {
						preferences.setCardName("");
					} else {
						preferences.setCardName(dtoList.get(j).getCardName());
					}

					response.getProvidepaymentpreferences().add(preferences);

					/*We don't need to call these service - REPIND-270*/
					for(FieldsDTO fieldsDTO : dtoList.get(j).getFieldsdto()){
						ProvideFields pField = new ProvideFields();
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
								response.getProvidepaymentpreferences().get(j).getProvidefields().add(pField_tmp);
							}else if(paymentFieldCode.equals("BILL_FIRST_NAME")){
								pField_tmp.setPaymentFieldCode("BILL_FIRSTNAME");
								pField_tmp.setPaymentFieldPreferences(paymentFieldPreference);
								response.getProvidepaymentpreferences().get(j).getProvidefields().add(pField_tmp);
							}
							//On enleve tout ce qui est lié a l'appelle amadeus - REPIND-270
							response.getProvidepaymentpreferences().get(j).getProvidefields().add(pField);
						} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
								| UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
							log.error("Problem with the encryption operation: ",e);
						}
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

		return response;
	}


	@Override
	public ProvideMaskedPaymentPreferencesResponse provideMaskedPaymentPreferences(ProvideMaskedPaymentPreferencesRequest request) throws BusinessException, SystemException {
		ProvideMaskedPaymentPreferencesResponse response = new ProvideMaskedPaymentPreferencesResponse();

		String requestMode;

		List<PaymentsDetailsDTO> dtoList = null;
		if(isEmptyInput(request.getGin())){
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_133.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_133, "EN"));
			errorCode.setMissingParameter("Gin");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}

		if(isEmptyInput(request.getRequestMode())){
			request.setRequestMode("");
		}

		if(!request.getRequestMode().equalsIgnoreCase("N") && !request.getRequestMode().equalsIgnoreCase("P") &&
				!request.getRequestMode().equalsIgnoreCase("C") && !request.getRequestMode().equalsIgnoreCase("")
				&& request.getRequestMode() != null){
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_121.value());
			errorCode.setFaultDescription("INVALID REQUEST MODE");
			errorCode.setMissingParameter("Request Mode");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		} else {
			requestMode = request.getRequestMode();
		}

		try {
			// Retrieving payment preferences
			dtoList = paymentPreferencesDS.findByGin(request.getGin());

			// Checking response content
			if(dtoList.size()>0) {

				// Sorting depending on request mode
				// P (preferred) sorting
				if(requestMode.equalsIgnoreCase("P")) {
					// Although a gin should contain only one preferred payment,
					// we must handle possible exceptions
					List<PaymentsDetailsDTO> preferredPayments = new ArrayList<>(),
							corporateSubList = new ArrayList<>(),
							nonCorporateSubList = new ArrayList<>();

					for(PaymentsDetailsDTO dto : dtoList) {
						if(dto.getPreferred().equalsIgnoreCase("Y")) {
							preferredPayments.add(dto);
						} else {
							if(dto.getCorporate().equalsIgnoreCase("Y")) {
								corporateSubList.add(dto);
							} else {
								nonCorporateSubList.add(dto);
							}
						}
					}

					Collections.sort(preferredPayments, new Comparator<PaymentsDetailsDTO>(){
						@Override
						public int compare(PaymentsDetailsDTO p1, PaymentsDetailsDTO p2){
							return (int) (p2.getPaymentId() - p1.getPaymentId());
						}
					});

					Collections.sort(corporateSubList, new Comparator<PaymentsDetailsDTO>(){
						@Override
						public int compare(PaymentsDetailsDTO p1, PaymentsDetailsDTO p2){
							return (int) (p2.getPaymentId() - p1.getPaymentId());
						}
					});

					Collections.sort(nonCorporateSubList, new Comparator<PaymentsDetailsDTO>(){
						@Override
						public int compare(PaymentsDetailsDTO p1, PaymentsDetailsDTO p2){
							return (int) (p2.getPaymentId() - p1.getPaymentId());
						}
					});

					dtoList = new ArrayList<>();
					dtoList.addAll(preferredPayments);
					dtoList.addAll(nonCorporateSubList);
					dtoList.addAll(corporateSubList);
				}
				// C (corporate) sorting
				else if(requestMode.equalsIgnoreCase("C")) {
					List<PaymentsDetailsDTO> corporateSubList = new ArrayList<>(),
							nonCorporateSubList = new ArrayList<>();

					for(PaymentsDetailsDTO dto : dtoList) {
						if(dto.getCorporate().equalsIgnoreCase("Y")) {
							corporateSubList.add(dto);
						} else {
							nonCorporateSubList.add(dto);
						}
					}

					Collections.sort(corporateSubList, new Comparator<PaymentsDetailsDTO>(){
						@Override
						public int compare(PaymentsDetailsDTO p1, PaymentsDetailsDTO p2){
							Boolean b1 = (p1.getPreferred().equalsIgnoreCase("Y")) ? true : false;
							Boolean b2 = (p2.getPreferred().equalsIgnoreCase("Y")) ? true : false;
							int res =  Boolean.valueOf(b2).compareTo(Boolean.valueOf(b1));
							if(res == 0) {
								res = Long.compare(p2.getPaymentId(),p1.getPaymentId());
							}
							return res;
						}
					});

					Collections.sort(nonCorporateSubList, new Comparator<PaymentsDetailsDTO>(){
						@Override
						public int compare(PaymentsDetailsDTO p1, PaymentsDetailsDTO p2){
							Boolean b1 = (p1.getPreferred().equalsIgnoreCase("Y")) ? true : false;
							Boolean b2 = (p2.getPreferred().equalsIgnoreCase("Y")) ? true : false;
							int res =  Boolean.valueOf(b2).compareTo(Boolean.valueOf(b1));
							if(res == 0) {
								res = Long.compare(p2.getPaymentId(),p1.getPaymentId());
							}
							return res;
						}
					});

					dtoList = new ArrayList<>();
					dtoList.addAll(corporateSubList);
					dtoList.addAll(nonCorporateSubList);
				}
				// N (non booking) sorting
				else {
					Collections.sort(dtoList, new Comparator<PaymentsDetailsDTO>(){
						@Override
						public int compare(PaymentsDetailsDTO p1, PaymentsDetailsDTO p2){
							return (int) (p2.getPaymentId() - p1.getPaymentId());
						}
					});
				}

				// Recuperation de chaque preference de paiement (V2 : n payment preferences)
				for(int i = 0; i < dtoList.size() ; i++) {
					ProvideMaskedPaymentPreferences preferences = new ProvideMaskedPaymentPreferences();

					preferences.setAirlinePaymentPref(dtoList.get(i).getCarrier());
					preferences.setPaymentGroupType(dtoList.get(i).getPaymentGroup());
					preferences.setPaymentMethod(dtoList.get(i).getPaymentMethod());
					preferences.setPointOfSale(dtoList.get(i).getPointOfSell());
					preferences.setCorporate(dtoList.get(i).getCorporate());
					preferences.setPreferred(dtoList.get(i).getPreferred());
					preferences.setPaymentId(dtoList.get(i).getPaymentId()+"");
					if(dtoList.get(i).getCardName() == null || dtoList.get(i).getCardName().isEmpty()) {
						preferences.setCardName("");
					} else {
						preferences.setCardName(dtoList.get(i).getCardName());
					}

					response.getProvideMaskedPaymentPreferences().add(preferences);

					for(FieldsDTO fieldsDTO : dtoList.get(i).getFieldsdto()){
						ProvideMaskedFields pField = new ProvideMaskedFields();
						try{
							String fieldCode = AES.decrypt(fieldsDTO.getPaymentFieldCode());
							String fieldPref = AES.decrypt(fieldsDTO.getPaymentFieldPreference());

							// workaround
							ProvideMaskedFields pField_tmp = new ProvideMaskedFields();

							if (fieldCode.equals("BILL_FIRSTNAME")) {
								pField_tmp.setPaymentFieldCode("BILL_FIRST_NAME");
								pField_tmp.setPaymentFieldPreferences(fieldPref);
								response.getProvideMaskedPaymentPreferences().get(i).getProvidemaskedfields().add(pField_tmp);
							} else if(fieldCode.equals("BILL_FIRST_NAME")) {
								pField_tmp.setPaymentFieldCode("BILL_FIRSTNAME");
								pField_tmp.setPaymentFieldPreferences(fieldPref);
								response.getProvideMaskedPaymentPreferences().get(i).getProvidemaskedfields().add(pField_tmp);
							}

							pField.setPaymentFieldCode(fieldCode);
							pField.setPaymentFieldPreferences(fieldPref);

							// V2 of PaymentPreferences will go live after PCI DSS step 4, so there won't be anymore CC_NR in responses
							//Non numeric fieldPref are not allowed for paymentGroup CC and DC, except PCIDSS cases
							/*if ((response.getProvideMaskedPaymentPreferences().get(i).getPaymentGroupType().equalsIgnoreCase("CC") ||
									response.getProvideMaskedPaymentPreferences().get(i).getPaymentGroupType().equalsIgnoreCase("DC"))) {
								if (fieldCode.equalsIgnoreCase("CC_NR")) {
									// phase 2 = only CC numbers in database
									if (this.isNumeric(fieldPref)) {
										if (isPCIDSS.equals("Y")) {
											// Request construction start
											// --------------------------
											ProvideMaskedFields pField_pci = new ProvideMaskedFields();
											pField_pci.setPaymentFieldCode("PCI_TOKEN");

											String pci_token = "";
											pci_token = fieldPref;

											pField_pci.setPaymentFieldPreferences(pci_token);
											response.getProvideMaskedPaymentPreferences().get(i).getProvidemaskedfields().add(pField_pci);

											if(doWeMask(pField_pci.getPaymentFieldCode())==1)
												pField_pci.setPaymentFieldPreferences(masking(fieldPref,false));
											else if(doWeMask(pField_pci.getPaymentFieldCode())==2)
												pField_pci.setPaymentFieldPreferences(masking(fieldPref,false));
											else
												pField_pci.setPaymentFieldPreferences(fieldPref);
										}
									} else {
										if (isPCIDSS.equals("Y")) {
											// Request construction start
											// --------------------------
											ProvideMaskedFields pField_pci = new ProvideMaskedFields();
											pField_pci.setPaymentFieldCode("PCI_TOKEN");

											String pci_token = "";
											pci_token = fieldPref;

											pField_pci.setPaymentFieldPreferences(pci_token);
											response.getProvideMaskedPaymentPreferences().get(i).getProvidemaskedfields().add(pField_pci);

											if(doWeMask(pField_pci.getPaymentFieldCode())==1)
												pField_pci.setPaymentFieldPreferences(masking(fieldPref,false));
											else if(doWeMask(pField_pci.getPaymentFieldCode())==2)
												pField_pci.setPaymentFieldPreferences(masking(fieldPref,false));
											else
												pField_pci.setPaymentFieldPreferences(fieldPref);
										}
									}
								}
							}*/

							if(doWeMask(pField.getPaymentFieldCode())==1) {
								pField.setPaymentFieldPreferences(masking(AES.decrypt(fieldsDTO.getPaymentFieldPreference()),false));
							} else if(doWeMask(pField.getPaymentFieldCode())==2) {
								pField.setPaymentFieldPreferences(masking(AES.decrypt(fieldsDTO.getPaymentFieldPreference()),false));
							} else {
								pField.setPaymentFieldPreferences(AES.decrypt(fieldsDTO.getPaymentFieldPreference()));
							}

							response.getProvideMaskedPaymentPreferences().get(i).getProvidemaskedfields().add(pField);
						} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
								| UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
							log.error("Problem with the encryption operation : ",e);
						}
					}
				}
			} else {
				// GIN not found
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

		return response;
	}


	public int doWeMask(String pFieldCode){
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

	/**
	 * Masking using PCIDSS or custom method
	 */
	public String masking(String fieldPreferences,boolean f){
		int fieldSize = fieldPreferences.length();
		String response="";
		for(int i =0;i<fieldPreferences.length();i++){
			if(!(fieldPreferences.charAt(i)==' ')){
				if(i>5 && fieldSize>4 && f) {
					response += 'X';
				} else if (fieldSize>4 && !f) {
					response += 'X';
				} else {
					response += fieldPreferences.charAt(i);
				}
			} else {
				response += ' ';
			}
			fieldSize--;
		}
		return response;
	}

	public boolean isEmptyInput(String input) {
		if(input == null || input.equalsIgnoreCase("?") || input.equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}

	public boolean isNumeric(String str)
	{
		return str.matches("^\\d+$");
	}
}
