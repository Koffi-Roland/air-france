package com.afklm.repind.v6.provideindividualdata.helpers;

import com.afklm.repindpp.paymentpreference.dto.FieldsDTO;
import com.afklm.repindpp.paymentpreference.dto.PaymentsDetailsDTO;
import com.afklm.repindpp.paymentpreference.encoding.AES;
import com.afklm.repindpp.paymentpreference.service.internal.PaymentPreferencesDS;
import com.afklm.repindpp.paymentpreference.service.internal.VariablesPPDS;
import com.afklm.repind.v6.provideindividualdata.type.MaskedFields;
import com.afklm.repind.v6.provideindividualdata.type.MaskedPaymentPreferences;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service("paymentPreferencesDataHelperV6")
@Slf4j
public class PaymentPreferencesDataHelper {

	@Autowired
	private VariablesPPDS variablesPPDS = null;

	public VariablesPPDS getVariablesPPDS() {
		return variablesPPDS;
	}

	public void setVariablesPPDS(VariablesPPDS variablesPPDS) {
		this.variablesPPDS = variablesPPDS;
	}
	@Autowired
	private PaymentPreferencesDS paymentPreferencesDS = null;

	public void setPaymentPreferencesDS(PaymentPreferencesDS paymentPreferencesDS){
		this.paymentPreferencesDS = paymentPreferencesDS;
	}

	public PaymentPreferencesDS getPaymentPreferencesDS() {
		return paymentPreferencesDS;
	}

	public MaskedPaymentPreferences provideMaskedPaymentPreferences(String gin) throws JrafDomainException{
		MaskedPaymentPreferences result = null;
		String isPCIDSS, isFullPCIDSS;
		
		PaymentsDetailsDTO pDetails = new PaymentsDetailsDTO();
		List<PaymentsDetailsDTO> dtoList = null;
		if(!gin.isEmpty()){
			try {
				isPCIDSS = variablesPPDS.isPCIDSSon();
				isFullPCIDSS = variablesPPDS.isFullPCIDSS();
				
				log.debug("Calling the DS...");
				dtoList = paymentPreferencesDS.findByGin(gin);
				if(dtoList.size()>0){
					result = new MaskedPaymentPreferences();
					log.debug("GIN found");
					result.setAirlinePaymentPref(dtoList.get(0).getCarrier());
					result.setPaymentGroupType(dtoList.get(0).getPaymentGroup());
					result.setPaymentMethod(dtoList.get(0).getPaymentMethod());
					result.setPointOfSale(dtoList.get(0).getPointOfSell());

					for(FieldsDTO fieldsDTO : dtoList.get(0).getFieldsdto()){
						MaskedFields pField = new MaskedFields();
						try{
							String fieldCode = AES.decrypt(fieldsDTO.getPaymentFieldCode());
							String fieldPref = AES.decrypt(fieldsDTO.getPaymentFieldPreference());

							// workaround
							log.debug("Workaround");
							MaskedFields pField_tmp = new MaskedFields();

							if (fieldCode.equals("BILL_FIRSTNAME")){
								pField_tmp.setPaymentFieldCode("BILL_FIRST_NAME");
								pField_tmp.setPaymentFieldPreferences(fieldPref);
								result.getMaskedFields().add(pField_tmp);
							}else if(fieldCode.equals("BILL_FIRST_NAME")){
								pField_tmp.setPaymentFieldCode("BILL_FIRSTNAME");
								pField_tmp.setPaymentFieldPreferences(fieldPref);
								result.getMaskedFields().add(pField_tmp);
							}

							pField.setPaymentFieldCode(fieldCode);
							pField.setPaymentFieldPreferences(fieldPref);

							//Non numeric fieldPref are not allowed for paymentGroup CC and DC, except PCIDSS cases
							if ((result.getPaymentGroupType().equalsIgnoreCase("CC") || result.getPaymentGroupType().equalsIgnoreCase("DC"))){
								if (fieldCode.equalsIgnoreCase("CC_NR")){
									// phase 2 = only CC numbers in database
									log.debug("phase 2 = only CC numbers in database");
									if (this.isNumeric(fieldPref)){
										if (isPCIDSS.equals("Y") || isFullPCIDSS.equalsIgnoreCase("Y")){
											// Request construction start
											// --------------------------
											log.debug("Request construction start");
											MaskedFields pField_pci = new MaskedFields();
											pField_pci.setPaymentFieldCode("PCI_TOKEN");

											String pci_token = "";
											pci_token = fieldPref;

											pField_pci.setPaymentFieldPreferences(pci_token);
											result.getMaskedFields().add(pField_pci);

											if(doWeMask(pField_pci.getPaymentFieldCode())==1) {
												pField_pci.setPaymentFieldPreferences(masking(fieldPref,false));
											} else if(doWeMask(pField_pci.getPaymentFieldCode())==2) {
												pField_pci.setPaymentFieldPreferences(masking(fieldPref,false));
											} else {
												pField_pci.setPaymentFieldPreferences(fieldPref);
											}
										}

									}else{
										if (isPCIDSS.equals("Y") || isFullPCIDSS.equalsIgnoreCase("Y")){
											// Request construction start
											// --------------------------
											log.debug("Request construction start");
											MaskedFields pField_pci = new MaskedFields();
											pField_pci.setPaymentFieldCode("PCI_TOKEN");

											String pci_token = "";
											pci_token = fieldPref;

											pField_pci.setPaymentFieldPreferences(pci_token);
											result.getMaskedFields().add(pField_pci);

											if(doWeMask(pField_pci.getPaymentFieldCode())==1) {
												pField_pci.setPaymentFieldPreferences(masking(fieldPref,false));
											} else if(doWeMask(pField_pci.getPaymentFieldCode())==2) {
												pField_pci.setPaymentFieldPreferences(masking(fieldPref,false));
											} else {
												pField_pci.setPaymentFieldPreferences(fieldPref);
											}
										}
									}
								}
							}

							if(doWeMask(pField.getPaymentFieldCode())==1) {
								pField.setPaymentFieldPreferences(masking(AES.decrypt(fieldsDTO.getPaymentFieldPreference()),false));
							} else if(doWeMask(pField.getPaymentFieldCode())==2) {
								pField.setPaymentFieldPreferences(masking(AES.decrypt(fieldsDTO.getPaymentFieldPreference()),false));
							} else {
								pField.setPaymentFieldPreferences(AES.decrypt(fieldsDTO.getPaymentFieldPreference()));
							}

							if(!pField.getPaymentFieldCode().equalsIgnoreCase("CC_NR") ||
									(pField.getPaymentFieldCode().equalsIgnoreCase("CC_NR") && !isFullPCIDSS.equalsIgnoreCase("Y"))) {
								result.getMaskedFields().add(pField);
							}

						} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
								| UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
							log.error("Problem with the encryption operation: " + e);
						}
					}

				}
				else{
					//GIN INEXISTANT
					log.debug("GIN not found --> Exception thrown");
				}

			} catch (org.hibernate.exception.GenericJDBCException e) {
				throw new JrafDomainException("GenericJDBCException");
			}
			catch(javax.persistence.PersistenceException e){
				throw new JrafDomainException("javax.persistence.PersistenceException");
			}
		} else {
			log.debug("Empty GIN");
		}
		return result;

	}

	public int computePaymentPreferencesGauge(MaskedPaymentPreferences paymentPref) {
		log.debug("Starting computePaymentPreferencesGauge");
		int percentage = 0;

		if ( paymentPref != null &&
				paymentPref.getAirlinePaymentPref() != null &&
				paymentPref.getPaymentGroupType() != null &&
				paymentPref.getPaymentMethod() != null &&
				paymentPref.getPointOfSale() != null &&
				!paymentPref.getMaskedFields().isEmpty() ) {
			percentage+=25;
		}
		log.debug("Percentage: {}%",percentage);
		return percentage;
	}

	public int doWeMask(String pFieldCode){
		log.debug("Starting doWeMask operation");
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
	 * Masking using PCI DSS Method or custom method
	 */
	public String masking(String fieldPreferences,boolean f){
		log.debug("Starting masking operation");
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
		log.debug("Starting isNumeric operation");
		return str.matches("^\\d+$");
	}
}
