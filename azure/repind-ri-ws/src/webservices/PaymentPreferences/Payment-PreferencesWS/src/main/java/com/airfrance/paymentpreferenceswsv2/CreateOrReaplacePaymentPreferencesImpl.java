package com.airfrance.paymentpreferenceswsv2;

import com.afklm.repindpp.paymentpreference.dto.FieldsDTO;
import com.afklm.repindpp.paymentpreference.dto.PaymentsDetailsDTO;
import com.afklm.repindpp.paymentpreference.encoding.AES;
import com.afklm.repindpp.paymentpreference.service.internal.PaymentPreferencesDS;
import com.afklm.repindpp.paymentpreference.service.internal.VariablesPPDS;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000470.v1.BusinessException;
import com.afklm.soa.stubs.w000470.v1.CreateOrReplacePaymentPreferencesV1;
import com.afklm.soa.stubs.w000470.v1.createorreplacepaymentpreferencesschema.BusinessError;
import com.afklm.soa.stubs.w000470.v1.createorreplacepaymentpreferencesschema.BusinessErrorEnum;
import com.afklm.soa.stubs.w000470.v1.createorreplacepaymentpreferencesschema.CreatePaymentPreferencesRequest;
import com.afklm.soa.stubs.w000470.v1.createorreplacepaymentpreferencesschema.CreatePaymentPreferencesResponse;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.repind.util.LoggerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.jws.WebService;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebService(endpointInterface="com.afklm.soa.stubs.w000470.v1.CreateOrReplacePaymentPreferencesV1", targetNamespace = "http://www.af-klm.com/services/passenger/CreateOrReplacePaymentPreferences-v1/wsdl", serviceName = "CreateOrReplacePaymentPreferencesService-v1", portName = "CreateOrReplacePaymentPreferences-v1-soap11http")
@Slf4j
public class CreateOrReaplacePaymentPreferencesImpl implements CreateOrReplacePaymentPreferencesV1 {

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
	/* We don't need to call the amadeus service - REPIND-270*/
	private String pamadeusChannel = "";

	/*
	 * http://localhost:9080/PaymentPreferencesWSV2/ws/CreateOrReplacePaymentPreferencesService-v1
	 * (non-Javadoc)
	 * @see com.afklm.repindpp.paymentpreference.CreateOrReplacePaymentPreferencesV1#createOrReplacePaymentPreferences(com.afklm.repindpp.paymentpreference.createorreplacepaymentpreferencesschema.CreatePaymentPreferencesRequest)
	 */
	@Override
	public CreatePaymentPreferencesResponse createOrReplacePaymentPreferences(CreatePaymentPreferencesRequest request) throws BusinessException, SystemException {
		boolean existe = true;

		//Check inputs :
		/************************/
		if(request.getGin().equalsIgnoreCase("")) {
			parametersMissing("Gin");
		}
		if(request.getAirlinePaymentPref().equalsIgnoreCase("")) {
			parametersMissing("Airline Payment Preferences");
		}
		if(request.getPointOfSale().equalsIgnoreCase("")) {
			parametersMissing("Point Of Sale");
		}
		if(request.getPaymentMethod().equalsIgnoreCase("")) {
			parametersMissing("Payment Method");
		}
		if(request.getPaymentGroupType().equalsIgnoreCase("")) {
			parametersMissing("Payment Group");
		}
		if(request.getSignature().equalsIgnoreCase("")) {
			missingSignature("Signature");
		}
		if(request.getSignatureSite().equalsIgnoreCase("")) {
			missingSignature("Signature Site");
		}
		if(request.getIpAdress().equalsIgnoreCase("")) {
			missingSignature("Ip Adresse");
			/**************************/
		}

		// Check GIN
		if(!isNumeric(request.getGin()) || request.getGin().length() != 12) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
			errorCode.setMissingParameter("GIN is incorrect");
			throw new BusinessException("GIN is incorrect", errorCode);
		}

		String isPCIDSS, isFullPCIDSS, isPhaseTest;
		try {
			isPCIDSS = variablesPPDS.isPCIDSSon();
			isFullPCIDSS = variablesPPDS.isFullPCIDSS();
			isPhaseTest = variablesPPDS.isPhaseTest();
		} catch (JrafDomainException e) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN")+": invalid paymentFieldPreferences");
			errorCode.setMissingParameter("Please Contact IT : PPDS variable not initialized");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}

		CreatePaymentPreferencesResponse result = new CreatePaymentPreferencesResponse();
		PaymentsDetailsDTO paymentsDetails = new PaymentsDetailsDTO();

		// List of payments for this gin
		List<PaymentsDetailsDTO> listPaymentsForGin = null;
		// If there is already a preferred payment for this gin, keep it.
		// If user creates or updates a payment and set it as preferred, the old one must not be preferred any longer.
		try {
			listPaymentsForGin = paymentPreferencesDS.findByGin(request.getGin());
		} catch (JrafDomainException e) {
			// e.printStackTrace();
			log.error(LoggerUtils.buidErrorMessage(e), e);
			result.setCreationStatus("N");
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
			errorCode.setMissingParameter("Please check server log");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}

		// Check if there is a preferred payment
		/*for(PaymentsDetailsDTO dto : listPaymentsForGin) {
			if(dto.getPreferred().equalsIgnoreCase("Y")) {
				preferredPayments.add(dto);
			}
		}

		for(PaymentsDetailsDTO dto : preferredPayments) {
			dto.setPreferred("N");
			paymentPreferencesDS.update(dto);
		}*/

		int index = 0;
		for(int k = 0; k < listPaymentsForGin.size(); k++) {
			if(listPaymentsForGin.get(k).getPreferred() != null && listPaymentsForGin.get(k).getPreferred().equals("Y")) {
				index = k;
			}
		}
		try{
			if(listPaymentsForGin.size() > 0) {
				paymentPreferencesDS.remove(listPaymentsForGin.get(index));
			}
		} catch (JrafDomainException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			result.setCreationStatus("N");
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
			errorCode.setMissingParameter("Please check server's log");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}

		paymentsDetails.setPreferred("Y");
		paymentsDetails.setCorporate("N");

		/*date /gin/point of sale*/
		if(!existe) {
			paymentsDetails.setDateCreation(new Date());
		} else {
			paymentsDetails.setChangingDate(new Date());
		}

		paymentsDetails.setGin(request.getGin());
		paymentsDetails.setPointOfSell(request.getPointOfSale());

		/*signature*/
		paymentsDetails.setIpAdresse(request.getIpAdress());
		paymentsDetails.setSignatureCreation(request.getSignature());
		paymentsDetails.setSiteCreation(request.getSignatureSite());

		// AF/ KLM
		paymentsDetails.setPaymentGroup(request.getAirlinePaymentPref());
		//Carriere
		paymentsDetails.setCarrier(request.getAirlinePaymentPref());
		//PaymentGroup & Method
		paymentsDetails.setPaymentGroup(request.getPaymentGroupType());
		paymentsDetails.setPaymentMethod(request.getPaymentMethod());
		//paymentsDetails.setIsTokenized(null);


		/**
		 * **********FIELDS***************
		 */

		int nbfields = request.getCreateListfields().size();

		if (nbfields>0){

			Set<FieldsDTO> pFieldsdto = new HashSet<>();

			try {

				//Declaration of cipher Encryption for encrypting data
				String fieldCode="";
				String fieldPref="";
				boolean hasAddedToken = false;
				for(int i=0;i<nbfields;i++){
					FieldsDTO fields = new FieldsDTO();
					fieldCode = request.getCreateListfields().get(i).getPaymentFieldCode();
					fieldPref = request.getCreateListfields().get(i).getPaymentFieldPreferences();

					// -----------------------------------------------------------------------
					// PCI DSS - Tokenization/Detokenization process

					// PHASE 2 (old)
					// if I get a Token, I detokenize to store in the database

					// PHASE 3
					// if I get a PAN, I tokenize to store in the database

					// If I get a PAN (DC/CC + CC_NR + numeric)
					if (request.getPaymentGroupType().equalsIgnoreCase("CC") || request.getPaymentGroupType().equalsIgnoreCase("DC")){

						if(isPhaseTest.equals("Y")) {
							if (fieldCode.equalsIgnoreCase("CC_NR") || fieldCode.equalsIgnoreCase("PCI_TOKEN")) {
								if(this.isNumeric(fieldPref)) {
									
									// REPIND-1174 : Add a trace log on a file for detecting CCNR and PCITOKEN with NUMERIC
									log.warn("CC_NR or PCI_TOKEN value cannot be numeric, must be a token");
									log.warn(AddRequestTrace(request));
									
									BusinessError errorCode = new BusinessError();
									errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
									errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN")+"CC_NR or PCI_TOKEN value cannot be numeric, must be a token");
									errorCode.setMissingParameter("paymentFieldPreferences");
									throw new BusinessException(errorCode.getFaultDescription(), errorCode);
								} else {
									if(fieldCode.equalsIgnoreCase("CC_NR")) {
										fieldCode = "PCI_TOKEN";
									}
								}
							}
							if(fieldCode.equalsIgnoreCase("BILL_NAME")
									||fieldCode.equalsIgnoreCase("BILL_FIRSTNAME")){
								fieldPref=CreateOrReaplacePaymentPreferencesImpl.ReplaceSpecialChar(fieldPref);
								fieldPref = fieldPref.toUpperCase();
							}

							fields.setPaymentFieldCode(AES.encrypt(fieldCode));
							fields.setPaymentFieldPreference(AES.encrypt(fieldPref));
							if(fieldCode.equalsIgnoreCase("PCI_TOKEN")) {
								if(!hasAddedToken) {
									pFieldsdto.add(fields);
									hasAddedToken = true;
								}
								paymentsDetails.setIsTokenized("Y");
							} else {
								pFieldsdto.add(fields);
							}
						}
						else {
							if (fieldCode.equalsIgnoreCase("CC_NR")) {

								// PHASE 4 : Full PCI DSS : CC_NR not permitted, only PCI_TOKEN
								if(isFullPCIDSS.equals("Y")) {
									BusinessError errorCode = new BusinessError();
									errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
									errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN")+": CC_NR not valid, only PCI_TOKEN accepted");
									errorCode.setMissingParameter("paymentFieldPreferences");
									throw new BusinessException(errorCode.getFaultDescription(), errorCode);
								} else {
									// if we get a token
									/*Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
									Matcher m = p.matcher(fieldPref);
									boolean b = m.matches();*/

									if(this.isNumeric(fieldPref)) {

										if (isPCIDSS.equals("Y")){
											/* We don't need to call the amadeus service - REPIND-270*/
										}else{
											BusinessError errorCode = new BusinessError();
											errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
											errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN")+": invalid paymentFieldPreferences");
											errorCode.setMissingParameter("paymentFieldPreferences");
											throw new BusinessException(errorCode.getFaultDescription(), errorCode);
										}
									}
								}
							}
						}
					}
					if(fieldCode.equalsIgnoreCase("BILL_NAME")
							||fieldCode.equalsIgnoreCase("BILL_FIRSTNAME")){
						fieldPref=CreateOrReaplacePaymentPreferencesImpl.ReplaceSpecialChar(fieldPref);
						fieldPref = fieldPref.toUpperCase();
					}

					fields.setPaymentFieldCode(AES.encrypt(fieldCode));
					fields.setPaymentFieldPreference(AES.encrypt(fieldPref));
					if(fieldCode.equalsIgnoreCase("PCI_TOKEN")) {
						if(!hasAddedToken) {
							pFieldsdto.add(fields);
							hasAddedToken = true;
						}
					} else {
						pFieldsdto.add(fields);
					}
				}
				paymentsDetails.setFieldsdto(pFieldsdto);

			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException e) {
				result.setCreationStatus("N");
				BusinessError errorCode = new BusinessError();
				errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
				errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
				errorCode.setMissingParameter("Encryption error");
				throw new BusinessException(errorCode.getFaultDescription(), errorCode);
			}
		}
		else{
			Set<FieldsDTO> pFieldsdto = new HashSet<>();
			/*BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
			errorCode.setMissingParameter("Invalid Fields Data");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);*/
			paymentsDetails.setIsTokenized("Y");
			paymentsDetails.setFieldsdto(pFieldsdto);
		}

		try {
			//writing in data base:
			paymentPreferencesDS.create(paymentsDetails);
		} catch (JrafDomainException e) {
			// e.printStackTrace();
			log.error(LoggerUtils.buidErrorMessage(e), e);
			result.setCreationStatus("N");
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
			errorCode.setMissingParameter("Please check server's log");
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

		result.setCreationStatus("O");
		return result;
	}

	// REPIND-1174 : Add a trace log on a file for detecting CCNR and PCITOKEN with NUMERIC
	private String AddRequestTrace(CreatePaymentPreferencesRequest request)  {
		String trace = "";
		if(request.getGin().equalsIgnoreCase("")) {
			trace += "GIN=" + request.getGin() + " "; 
		}
		if(request.getAirlinePaymentPref().equalsIgnoreCase("")) {
			trace += "ALPP=" + request.getAirlinePaymentPref() + " ";
		}
		if(request.getPointOfSale().equalsIgnoreCase("")) {
			trace += "POS=" + request.getPointOfSale() + " ";
		}
		if(request.getPaymentMethod().equalsIgnoreCase("")) {
			trace += "PM=" + request.getPaymentMethod() + " ";
		}
		if(request.getPaymentGroupType().equalsIgnoreCase("")) {
			trace += "PGT=" + request.getPaymentGroupType() + " ";
		}
		if(request.getSignature().equalsIgnoreCase("")) {
			trace += "SIG=" + request.getSignature() + " ";
		}
		if(request.getSignatureSite().equalsIgnoreCase("")) {
			trace += "SS=" + request.getSignatureSite() + " ";
		}
		if(request.getIpAdress().equalsIgnoreCase("")) {
			trace += "IP=" + request.getIpAdress() + " ";
		}
		return trace;
	}
	
	public void parametersMissing(String param) throws BusinessException{
		BusinessError errorCode = new BusinessError();
		errorCode.setErrorCode(BusinessErrorEnum.ERR_133.value());
		errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_133, "EN"));
		errorCode.setMissingParameter(param);
		throw new BusinessException(errorCode.getFaultDescription(), errorCode);
	}

	public void missingSignature(String param) throws BusinessException{
		BusinessError errorCode = new BusinessError();
		errorCode.setErrorCode(BusinessErrorEnum.ERR_111.value());
		errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_111, "EN"));
		errorCode.setMissingParameter(param);
		throw new BusinessException(errorCode.getFaultDescription(), errorCode);
	}


	public static String ReplaceSpecialChar(String str){
		String sNewchaine = str;
		sNewchaine = sNewchaine.replaceAll("[ÀÁÂÄ]","A");
		sNewchaine = sNewchaine.replaceAll("[àáâä]","a");
		sNewchaine = sNewchaine.replaceAll("Ç","C");
		sNewchaine = sNewchaine.replaceAll("ç","c");
		sNewchaine = sNewchaine.replaceAll("[ÈÉÊË]","E");
		sNewchaine = sNewchaine.replaceAll("[èéêë]","e");
		sNewchaine = sNewchaine.replaceAll("[ÌÍÎÏ]","I");
		sNewchaine = sNewchaine.replaceAll("[ìíîï]","i");
		sNewchaine = sNewchaine.replaceAll("[ÒÓÔÖ]","O");
		sNewchaine = sNewchaine.replaceAll("[òóôö]","o");
		sNewchaine = sNewchaine.replaceAll("[ÙÚÛÜ]","U");
		sNewchaine = sNewchaine.replaceAll("[üùúû]","u");
		sNewchaine = sNewchaine.replaceAll("Ý","Y");
		sNewchaine = sNewchaine.replaceAll("ß","?");
		sNewchaine = sNewchaine.replaceAll("[ýÿ]","y");
		sNewchaine = sNewchaine.replaceAll("&#[0-9]+","");
		sNewchaine = sNewchaine.replaceAll("[\\^';\"ª]","");
		return sNewchaine;
	}

	public boolean isNumeric(String str)
	{
		return str.matches("^\\d+$");
	}

}
