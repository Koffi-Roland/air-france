package com.airfrance.paymentpreferenceswsv2.v2;

import com.afklm.repindpp.paymentpreference.dto.FieldsDTO;
import com.afklm.repindpp.paymentpreference.dto.PaymentsDetailsDTO;
import com.afklm.repindpp.paymentpreference.encoding.AES;
import com.afklm.repindpp.paymentpreference.service.internal.PaymentPreferencesDS;
import com.afklm.repindpp.paymentpreference.service.internal.VariablesPPDS;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000470.v2_0_1.BusinessException;
import com.afklm.soa.stubs.w000470.v2_0_1.CreateOrReplacePaymentPreferencesV2;
import com.afklm.soa.stubs.w000470.v2_0_1.createorreplacepaymentpreferencesschema.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.repind.util.LoggerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.jws.WebService;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@WebService(endpointInterface="com.afklm.soa.stubs.w000470.v2_0_1.CreateOrReplacePaymentPreferencesV2", targetNamespace = "http://www.af-klm.com/services/passenger/CreateOrReplacePaymentPreferences-v2/wsdl", serviceName = "CreateOrReplacePaymentPreferencesService-v2", portName = "CreateOrReplacePaymentPreferences-v2-soap11http")
@Slf4j
public class CreateOrReplacePaymentPreferencesV2Impl implements CreateOrReplacePaymentPreferencesV2 {
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

	public String WARNING_CREATE_PP = "O";
	public String WARNING_REPLACE_PP = "R";

	@Autowired
	private VariablesPPDS variablesPPDS;


	@Override
	public CreatePaymentPreferencesResponse createOrReplacePaymentPreferences(CreatePaymentPreferencesRequest request) throws BusinessException, SystemException {
		CreatePaymentPreferencesResponse response = new CreatePaymentPreferencesResponse();

		String status = WARNING_CREATE_PP;
		String isPCIDSS = "";
		try {
			isPCIDSS = variablesPPDS.isPCIDSSon();
		} catch (JrafDomainException e2) {
			// e2.printStackTrace();
			log.error(LoggerUtils.buidErrorMessage(e2),e2);
		}

		// Checking Gin is specified
		PaymentsDetailsDTO pDetails = new PaymentsDetailsDTO();
		if(isEmptyInput(request.getGin())){
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_133.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_133, "EN"));
			errorCode.setMissingParameter("Gin");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}
		// Check GIN
		if(!isNumeric(request.getGin()) || request.getGin().length() != 12) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
			errorCode.setMissingParameter("GIN is incorrect");
			throw new BusinessException("GIN is incorrect", errorCode);
		}
		pDetails.setGin(request.getGin());

		// List of payments for this gin
		List<PaymentsDetailsDTO> listPaymentsForGin = null, preferredPayments = new ArrayList<>();
		// If there is already a preferred payment for this gin, keep it.
		// If user creates or updates a payment and set it as preferred, the old one must not be preferred any longer.
		try {
			listPaymentsForGin = paymentPreferencesDS.findByGin(request.getGin());
		} catch (JrafDomainException e) {
			// e.printStackTrace();
			log.error(LoggerUtils.buidErrorMessage(e), e);
			response.setCreationStatus("N");
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
			errorCode.setMissingParameter("Please check server log");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}

		// Check if there is a preferred payment
		for(PaymentsDetailsDTO dto : listPaymentsForGin) {
			if(dto.getPreferred().equalsIgnoreCase("Y")) {
				preferredPayments.add(dto);
			}
		}

		// Mandatory signature fields in both create or replace
		if(isEmptyInput(request.getSignature())) {
			missingSignature("Signature");
		}
		if(isEmptyInput(request.getSignatureSite())) {
			missingSignature("Signature Site");
		}
		if(isEmptyInput(request.getIpAdress())) {
			missingSignature("Ip Adresse");
		}

		// Checking preferred and corporate fields
		if(!isEmptyInput(request.getPreferred()) &&
				(!request.getPreferred().equalsIgnoreCase("Y") && !request.getPreferred().equalsIgnoreCase("N"))) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_111.value());
			errorCode.setFaultDescription("Wrong Preferred field");
			errorCode.setMissingParameter("Must be Y or N");
			throw new BusinessException(errorCode.getFaultDescription(),errorCode);
		}
		if(!isEmptyInput(request.getCorporate()) &&
				(!request.getCorporate().equalsIgnoreCase("Y") && !request.getCorporate().equalsIgnoreCase("N"))) {
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_111.value());
			errorCode.setFaultDescription("Wrong Corporate field");
			errorCode.setMissingParameter("Must be Y or N");
			throw new BusinessException(errorCode.getFaultDescription(),errorCode);
		}
		int nbFields = request.getCreateListfields().size();
		String ppIdAlreadyExist;
		try {
			ppIdAlreadyExist = isCarteAlreadyExist(request, nbFields);
			if(ppIdAlreadyExist != null){
				status = WARNING_REPLACE_PP;
				request.setPaymentId(ppIdAlreadyExist);
			}
		} catch (JrafDomainException | InvalidKeyException | NumberFormatException | IllegalBlockSizeException
				| BadPaddingException | UnsupportedEncodingException | NoSuchAlgorithmException
				| NoSuchPaddingException e1) {
			log.error(LoggerUtils.buidErrorMessage(e1),e1);
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
			errorCode.setMissingParameter("Please check server log");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}
		// No paymentId specified : creation of new PP
		/**
		 * ******* CREATION *****
		 **/
		if(isEmptyInput(request.getPaymentId())) {

			if(isEmptyInput(request.getAirlinePaymentPref())) {
				parametersMissing("Airline Payment Preferences");
			}
			if(isEmptyInput(request.getPointOfSale())) {
				parametersMissing("Point Of Sale");
			}
			if(isEmptyInput(request.getPaymentMethod())) {
				parametersMissing("Payment Method");
			}
			if(isEmptyInput(request.getPaymentGroupType())) {
				parametersMissing("Payment Group");
			}

			// Cannot create more than 10 PP for a GIN
			if(listPaymentsForGin.size() < 10) {
				PaymentsDetailsDTO paymentsDetails = new PaymentsDetailsDTO();
				// Parameters
				paymentsDetails.setDateCreation(new Date());
				paymentsDetails.setGin(request.getGin());
				paymentsDetails.setPointOfSell(request.getPointOfSale());
				paymentsDetails.setIpAdresse(request.getIpAdress());
				paymentsDetails.setSignatureCreation(request.getSignature());
				paymentsDetails.setSiteCreation(request.getSignatureSite());
				paymentsDetails.setCarrier(request.getAirlinePaymentPref());
				paymentsDetails.setPaymentGroup(request.getPaymentGroupType());
				paymentsDetails.setPaymentMethod(request.getPaymentMethod());
				paymentsDetails.setIsTokenized(null);
				if(request.getCardName() != null && !request.getCardName().isEmpty()) {
					paymentsDetails.setCardName(request.getCardName());
				} else {
					paymentsDetails.setCardName("");
				}
				if(isEmptyInput(request.getPreferred())) {
					// If no payment is set as preferred : the new one will be preferred, else not
					if(preferredPayments.size() == 0) {
						paymentsDetails.setPreferred("Y");
					} else {
						paymentsDetails.setPreferred("N");
					}
				} else {
					paymentsDetails.setPreferred(request.getPreferred());
				}
				if(isEmptyInput(request.getCorporate())) {
					paymentsDetails.setCorporate("N");
				} else {
					paymentsDetails.setCorporate(request.getCorporate());
				}
				// Fields

				if(nbFields > 0) {
					addFieldsToPayment(request, response, paymentsDetails, nbFields, isPCIDSS);
				} else {
					Set<FieldsDTO> pFieldsdto = new HashSet<>();
					paymentsDetails.setFieldsdto(pFieldsdto);
				}

				try {
					paymentPreferencesDS.create(paymentsDetails);
					if(request.getPreferred().equalsIgnoreCase("Y") && preferredPayments.size() > 0) {
						for(PaymentsDetailsDTO dto : preferredPayments) {
							dto.setPreferred("N");
							paymentPreferencesDS.update(dto);
						}
					}
				} catch (JrafDomainException e) {
					// e.printStackTrace();
					log.error(LoggerUtils.buidErrorMessage(e), e);
					response.setCreationStatus("N");
					BusinessError errorCode = new BusinessError();
					errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
					errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
					errorCode.setMissingParameter("Please check server log");
					throw new BusinessException(errorCode.getFaultDescription(), errorCode);
				}
			} else {
				BusinessError errorCode = new BusinessError();
				errorCode.setErrorCode(BusinessErrorEnum.ERR_180.value());
				errorCode.setFaultDescription("Gin already has max payments");
				throw new BusinessException(errorCode.getFaultDescription(),errorCode);
			}
		}
		/**
		 * ******* UPDATE *****
		 **/
		// paymentId is specified : updating a PP
		else {
			PaymentsDetailsDTO paymentsDetails = null;
			try {

				List<PaymentsDetailsDTO> list = paymentPreferencesDS.findByGinAndPaymentId(request.getGin(), Integer.parseInt(request.getPaymentId()));
				if(list.size() > 0) {
					paymentsDetails = list.get(0);
				} else {
					BusinessError errorCode = new BusinessError();
					errorCode.setErrorCode(BusinessErrorEnum.ERR_001.value());
					errorCode.setFaultDescription("No payment found");
					throw new BusinessException(errorCode.getFaultDescription(),errorCode);
				}
			} catch (JrafDomainException e) {
				// e.printStackTrace();
				log.error(LoggerUtils.buidErrorMessage(e), e);
				response.setCreationStatus("N");
				BusinessError errorCode = new BusinessError();
				errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
				errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
				errorCode.setMissingParameter("Please check server log");
				throw new BusinessException(errorCode.getFaultDescription(), errorCode);
			}

			// A corresponding payment was found
			if(paymentsDetails != null) {
				// Setting al parameters
				paymentsDetails.setChangingDate(new Date());
				paymentsDetails.setGin(request.getGin());
				if(!isEmptyInput(request.getPointOfSale())) {
					paymentsDetails.setPointOfSell(request.getPointOfSale());
				}
				if(!isEmptyInput(request.getAirlinePaymentPref())) {
					paymentsDetails.setCarrier(request.getAirlinePaymentPref());
				}
				if(!isEmptyInput(request.getPaymentGroupType())) {
					paymentsDetails.setPaymentGroup(request.getPaymentGroupType());
				}
				if(!isEmptyInput(request.getPaymentMethod())) {
					paymentsDetails.setPaymentMethod(request.getPaymentMethod());
				}
				if(!isEmptyInput(request.getPreferred())) {
					paymentsDetails.setPreferred(request.getPreferred());
				}
				if(!isEmptyInput(request.getCorporate())) {
					paymentsDetails.setCorporate(request.getCorporate());
				}
				if(!isEmptyInput(request.getCardName())) {
					paymentsDetails.setCardName(request.getCardName());
				} else {
					paymentsDetails.setCardName("");
				}
				paymentsDetails.setIpAdresse(request.getIpAdress());
				paymentsDetails.setSignatureCreation(request.getSignature());
				paymentsDetails.setSiteCreation(request.getSignatureSite());
				paymentsDetails.setPaymentId(Integer.valueOf(request.getPaymentId()));
				paymentsDetails.setIsTokenized(null);

				// Fields
				if(nbFields > 0) {
					addFieldsToPayment(request, response, paymentsDetails, nbFields, isPCIDSS);
				} else {
					Set<FieldsDTO> pFieldsdto = new HashSet<>();
					paymentsDetails.setFieldsdto(pFieldsdto);
				}

				try {
					paymentPreferencesDS.updateV2(paymentsDetails);
					if(request.getPreferred().equalsIgnoreCase("Y") && preferredPayments.size() > 0) {
						for(PaymentsDetailsDTO dto : preferredPayments) {
							// Don't check if it's the updated payment preference
							if(dto.getPaymentId() != paymentsDetails.getPaymentId()) {
								dto.setPreferred("N");
								paymentPreferencesDS.update(dto);
							}
						}
					}
				} catch (JrafDomainException e) {
					// e.printStackTrace();
					log.error(LoggerUtils.buidErrorMessage(e), e);
					response.setCreationStatus("N");
					BusinessError errorCode = new BusinessError();
					errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
					errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
					errorCode.setMissingParameter("Please check server log");
					throw new BusinessException(errorCode.getFaultDescription(), errorCode);
				}
			}
			// No corresponding payment was found
			else {
				BusinessError errorCode = new BusinessError();
				errorCode.setErrorCode(BusinessErrorEnum.ERR_001.value());
				errorCode.setFaultDescription("Payment not found");
				errorCode.setMissingParameter("Gin or PaymentId");
				throw new BusinessException(errorCode.getFaultDescription(),errorCode);
			}
		}
		if(WARNING_REPLACE_PP.equals(status)){
			Warning warning = new Warning();
			warning.setWarningCode("1001");
			warning.setWarningDetail("COBADGED_CARD_UPDATED");
			WarningResponse warningResponse = new WarningResponse();
			warningResponse.setWarning(warning);
			response.getWarningresponse().add(warningResponse);
		}
		response.setCreationStatus("O");
		return response;
	}

	private void addFieldsToPayment(CreatePaymentPreferencesRequest request, CreatePaymentPreferencesResponse response, PaymentsDetailsDTO paymentsDetails, int nbFields, String isPCIDSS) throws BusinessException {
		Set<FieldsDTO> pFieldsdto = new HashSet<>();

		paymentsDetails.setIsTokenized(null);
		try {
			//Declaration of cipher Encryption for encrypting data
			String fieldCode="";
			String fieldPref="";
			for(int i = 0 ; i < nbFields ; i++) {
				FieldsDTO fields = new FieldsDTO();
				fieldCode = request.getCreateListfields().get(i).getPaymentFieldCode();
				fieldPref = request.getCreateListfields().get(i).getPaymentFieldPreferences();

				if (paymentsDetails.getPaymentGroup().equalsIgnoreCase("CC") || paymentsDetails.getPaymentGroup().equalsIgnoreCase("DC")){

					if (fieldCode.equalsIgnoreCase("CC_NR") || fieldCode.equalsIgnoreCase("PCI_TOKEN")) {
						if(this.isNumeric(fieldPref)) {
							
							// REPIND-1174 : Add a trace log on a file for detecting CCNR and PCITOKEN with NUMERIC
							log.warn("CC_NR or PCI_TOKEN value cannot be numeric, must be a token");
							log.warn(AddRequestTrace(request));
							
							BusinessError errorCode = new BusinessError();
							errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
							errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN")+": CC_NR or PCI_TOKEN value cannot be numeric, must be a token");
							errorCode.setMissingParameter("paymentFieldPreferences");
							throw new BusinessException(errorCode.getFaultDescription(), errorCode);
						}
						/*Remove call from AMADEUS - REPIND-270*/
					}

				}
				if(fieldCode.equalsIgnoreCase("BILL_NAME")
						||fieldCode.equalsIgnoreCase("BILL_FIRSTNAME")){
					fieldPref=CreateOrReplacePaymentPreferencesV2Impl.ReplaceSpecialChar(fieldPref);
					fieldPref = fieldPref.toUpperCase();
				}

				fields.setPaymentFieldCode(AES.encrypt(fieldCode));
				fields.setPaymentFieldPreference(AES.encrypt(fieldPref));
				pFieldsdto.add(fields);
			}
			paymentsDetails.setFieldsdto(pFieldsdto);


		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			response.setCreationStatus("N");
			BusinessError errorCode = new BusinessError();
			errorCode.setErrorCode(BusinessErrorEnum.ERR_905.value());
			errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_905, "EN"));
			errorCode.setMissingParameter("Encryption error");
			throw new BusinessException(errorCode.getFaultDescription(), errorCode);
		}
	}

	public String isCarteAlreadyExist(CreatePaymentPreferencesRequest request, int nbFields) throws JrafDomainException,
			BusinessException, InvalidKeyException, NumberFormatException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
		String gin = request.getGin();
		List<String> mc_l = Arrays.asList("MAESTRO", "MC"), //MasterCard List
				visa_l = Arrays.asList("VISA_ELECTRON", "VISA", "VISA_DEBIT"), //Visa List
				cb_l = Arrays.asList("CARTE_BANCAIRE"); //CarteBancaire List
		String pm_c = request.getPaymentMethod(), pg_c =  request.getPaymentGroupType(), //on recuperer les options de paiment envoyé
				pm_r = null, //PaymentMethode Record
				pg_r = null, //PaymentGroup Record
				fc_c = null, //Field Code Current
				fp_c = null; //Field Preference Current

		for(int i = 0 ; i < nbFields ; i++) {
			fc_c = request.getCreateListfields().get(i).getPaymentFieldCode();
			fp_c = request.getCreateListfields().get(i).getPaymentFieldPreferences();
			if((fc_c.equalsIgnoreCase("CC_NR") || fc_c.equalsIgnoreCase("PCI_TOKEN")) //Si on est sur le champs du token
					&& (pg_c.equalsIgnoreCase("CC") || pg_c.equalsIgnoreCase("DC"))) //et que l'on as une carte en base de type CC ou DC
			{ //Situation, on as envoyé une carte (Avec PCI_TOKEN ou CC_NR) de type  CC ou DC
				List<PaymentsDetailsDTO> AllPP = paymentPreferencesDS.findByGin(gin);
				for(PaymentsDetailsDTO pp_r : AllPP){//on verifie dans tout les payment en base si on as une carte deja enregistré;
					pm_r = pp_r.getPaymentMethod();
					pg_r = pp_r.getPaymentGroup();
					if(pg_r.equalsIgnoreCase("CC") || pg_r.equalsIgnoreCase("DC")//Si on as une carte en base de type CC ou DC
							/* &&  (mc_l.contains(pm_r) || visa_l.contains(pm_r) || cb_l.contains(pm_r))*/  //et un type de paiment qui rentre dans notre zone de recherche
							){
						for(FieldsDTO fd_r : pp_r.getFieldsdto()){
							if ((AES.decrypt(fd_r.getPaymentFieldCode()).equalsIgnoreCase("CC_NR")
									|| AES.decrypt(fd_r.getPaymentFieldCode()).equalsIgnoreCase("PCI_TOKEN"))) { // On
								// as
								// un
								// PCI
								// TOKEN
								if (AES.decrypt(fd_r.getPaymentFieldPreference()).equalsIgnoreCase(fp_c)) {
									log.debug("On as le meme token de carte en base pour le paymentID : '{}'",pp_r.getPaymentId());
									if(request.getPaymentId() == null || //si le PP id est null ou
											Integer.parseInt(request.getPaymentId()) != pp_r.getPaymentId() || (!pm_c.equalsIgnoreCase(pm_r) || !pg_c.equals(pg_r))){//si on as un paimentID Different et type de carte diff
										if((((mc_l.contains(pm_r) || visa_l.contains(pm_r)) && cb_l.contains(pm_c)) || //si en base on as une visa ou cb et que nous avons envoyé une CB ou
												((mc_l.contains(pm_c) || visa_l.contains(pm_c)) && cb_l.contains(pm_r))) //si nous avons envoyé une visa ou cb et qu'en base on as une CB et
												){ //Si nous n'avons pas de paymentID ou un egal a celui en base
											return Long.toString(pp_r.getPaymentId()); //on retourne le paiment ID
										}else{//Sinon, on est dans le cas de notre erreur
											BusinessError errorCode = new BusinessError();
											errorCode.setErrorCode(BusinessErrorEnum.ERR_940.value()); //ERR_940
											errorCode.setFaultDescription(RefTableREF_ERREUR.instance().getLibelle(RefTableREF_ERREUR._REF_940, "EN"));
											throw new BusinessException(errorCode.getFaultDescription(), errorCode);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	// REPIND-1174 : Add a trace log on a file for detecting CCNR and PCITOKEN with NUMERIC
	private String AddRequestTrace(CreatePaymentPreferencesRequest request)  {
		String trace = "";
		if(!"".equalsIgnoreCase(request.getGin())) {
			trace += "GIN=" + request.getGin() + " "; 
		}
		if(!"".equalsIgnoreCase(request.getAirlinePaymentPref())) {
			trace += "ALPP=" + request.getAirlinePaymentPref() + " ";
		}
		if(!"".equalsIgnoreCase(request.getPointOfSale())) {
			trace += "POS=" + request.getPointOfSale() + " ";
		}
		if(!"".equalsIgnoreCase(request.getPaymentMethod())) {
			trace += "PM=" + request.getPaymentMethod() + " ";
		}
		if(!"".equalsIgnoreCase(request.getPaymentGroupType())) {
			trace += "PGT=" + request.getPaymentGroupType() + " ";
		}
		if(!"".equalsIgnoreCase(request.getSignature())) {
			trace += "SIG=" + request.getSignature() + " ";
		}
		if(!"".equalsIgnoreCase(request.getSignatureSite())) {
			trace += "SS=" + request.getSignatureSite() + " ";
		}
		if(!"".equalsIgnoreCase(request.getIpAdress())) {
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

	public boolean isEmptyInput(String input) {
		if(input == null || input.equalsIgnoreCase("?") || input.equalsIgnoreCase("")) {
			return true;
		}
		return false;
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
