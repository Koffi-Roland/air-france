package com.airfrance.repind.service.ws.internal.helpers;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.email.SharedEmailException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.MediumCodeEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.ref.type.NATFieldsEnum;
import com.airfrance.ref.type.UpdateModeEnum;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.myaccountcustomerdata.ReturnDetailsDTO;
import com.airfrance.repind.dto.reference.RefAlertDTO;
import com.airfrance.repind.dto.ws.AlertRequestDTO;
import com.airfrance.repind.dto.ws.CommunicationPreferencesRequestDTO;
import com.airfrance.repind.dto.ws.EmailRequestDTO;
import com.airfrance.repind.dto.ws.RequestorDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.individu.internal.AlertDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.reference.internal.RefAlertDS;
import com.airfrance.repind.util.transformer.IndividuTransform;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AlertHelper {

	private Log LOGGER = LogFactory.getLog(AlertHelper.class);
	
	/** the Entity Manager*/
	@PersistenceContext(unitName="entityManagerFactoryRepind")
	private EntityManager entityManager;
	
	@Autowired
	protected AlertDS alertDS;
	
	@Autowired
	protected RefAlertDS refAlertDS;
	
	@Autowired
	protected IndividuDS individuDS;
	
	@Autowired
	protected CommunicationPreferencesDS communicationPreferencesDS;
	
	@Autowired
	protected MyAccountDS myAccountDS;
	
	@Autowired
	protected EmailDS emailDS;
	
	public List<RefAlertDTO> listRefInp;
	public List<RefAlertDTO> listRefEnv;
	
	public static final String PromoKeyComPrefId = "COMPREF_ID";	
	public static final String DateFormat = "DDMMYYYY";
	public static final  String MaxAlert = "MAX_ALERT";

	public static final String env_key_str = "ENV";
	
	public void processAlert(String gin, CreateUpdateIndividualRequestDTO request, SignatureDTO signatureAPP) throws JrafDomainException, ParseException {
	
		for (CommunicationPreferencesRequestDTO comPrefRequest : request.getCommunicationPreferencesRequestDTO()) { //TODO : Voir si utile ?
			if(request.getAlertRequestDTO() != null) {
				UpdateAlertListIndividual(gin, IndividuTransform.transformToAlertDTO(request.getAlertRequestDTO()), 
						getComPrefId(gin, comPrefRequest, request.getRequestorDTO()), signatureAPP);
			}
			// check de l'optin compref et si il est  optout toutes ses alertes
			if (comPrefRequest != null && comPrefRequest.getCommunicationPreferencesDTO() != null) {
				if (comPrefRequest.getCommunicationPreferencesDTO().getOptIn() != null && "N".equalsIgnoreCase(comPrefRequest.getCommunicationPreferencesDTO().getOptIn())) {
					optoutComPrefAlert(gin, comPrefRequest, request.getRequestorDTO());
				}
			}
		}
	}

	/**
	 * This method call to update the list of alert of an individual
	 * 
	 * @param gin
	 * @param comPrefId 
	 * @param emailDTO 
	 * @param alertListDTO
	 * @param signature
	 * @throws JrafDomainException
	 * @throws ParseException 
	 */
	public void UpdateAlertListIndividual(String gin, List<AlertDTO> AlertList, Integer comPrefId, SignatureDTO signature) throws JrafDomainException, ParseException {
		if(AlertList != null && AlertList.size() > 0) {
			updatelistsRef(AlertList.get(0).getType());
			for (AlertDTO alert : AlertList) {
				checkInputAlert(alert);
				UpdateAlertIndividual(gin, alert, comPrefId, signature);
			}	
			
			updateComPref(gin, comPrefId, signature);
		}
	}
	
	/**
	 * Create or update An alert for an individual
	 * @param gin
	 * @param alert
	 * @param comPrefId
	 * @param signature
	 * @throws JrafDomainException
	 * @throws ParseException
	 */
	public void UpdateAlertIndividual(String gin, AlertDTO alert, Integer comPrefId, SignatureDTO signature) throws JrafDomainException, ParseException {
		checkMaxAlert(gin, alert.getOptIn());
		alert.setSgin(gin);
		alert.setModificationDate(new Date());
		alert.setModificationSignature(signature.getSignature());
		alert.setModificationSite(signature.getSite());
		if(alert.getAlertId() != null){
			checkIfAlertExist(gin, alert);
			alert = updateAlert(alert);
			checkComPrefStatus(alert);
		}else{
			if(alert.getAlertDataDTO() == null) {
				alert.setAlertDataDTO(new HashSet<AlertDataDTO>());
			}
			alert.getAlertDataDTO().add(new AlertDataDTO(PromoKeyComPrefId, comPrefId.toString()));
			alert.setCreationDate(new Date());
			alert.setCreationSignature(signature.getSignature());
			alert.setCreationSite(signature.getSite());
			alert = createAlert(alert);
			checkComPrefStatus(alert);
		}
	}

	private void checkIfAlertExist(String gin, AlertDTO alert) throws JrafDomainException {
		AlertDTO search;
		try{
			search = alertDS.get(alert.getAlertId());
		}catch(Exception e){
			throw new JrafDomainException("Alert not found");
		}
		if(search == null || !search.getSgin().equals(gin)){
			throw new JrafDomainException("Alert not found for this GIN");
		}
	}
	public void checkInputAlert(List<AlertDTO> list, boolean existComPref) throws JrafDomainException {
		
		if(list == null || list.isEmpty()){
			throw new MissingParameterException("The list of alert must not be null nor empty");
		}
		
		// ComPref mandatory when we create an alert
		if (!existComPref) {
			throw new MissingParameterException("Communication preference mandatory to create an alert");
		}
		
		String Alerttype = list.get(0).getType(); //On recupere le premier type d'alert
		updatelistsRef(Alerttype); // On met a jour la liste
		for (AlertDTO alert : list) {
			if(!Alerttype.equals(alert.getType())){ //si on se retrouve avec un type d'alert different du precedent
				Alerttype = alert.getType(); 
				updatelistsRef(Alerttype);//On met a jour la liste
			}
			checkInputAlert(alert);
		}
	}
	
	public void updatelistsRef(String type) throws JrafDomainException {
		 this.listRefInp = getlistRef(type, "INP");
		 this.listRefEnv = getlistRef(type, env_key_str);
	}

	public List<RefAlertDTO> getlistRef(String type, String usage) throws JrafDomainException {
		RefAlertDTO searchRef = new RefAlertDTO();
		searchRef.setType(type);
		searchRef.setUsage(usage);
		return refAlertDS.findAll(searchRef);		
	}

	public void checkInputAlert(AlertDTO alertDTO) throws JrafDomainException {		
		//premier check pour voir si il manque des champs
		if(alertDTO.getAlertId() == null){//on check les alertData si il n'y as pas d'id
			if(alertDTO.getAlertDataDTO() != null && alertDTO.getAlertDataDTO().size() >= 1){
				CheckIfKeyIsMissing(alertDTO.getAlertDataDTO());
				CheckAllKey(alertDTO.getAlertDataDTO());
			}else{
				throw new MissingParameterException("Missing alert data for alert");
			}
		}else{ //on check les cles que l'on as deja
			if(alertDTO.getAlertDataDTO() != null && alertDTO.getAlertDataDTO().size() >= 1){
				CheckAllKey(alertDTO.getAlertDataDTO());
			}else if(alertDTO.getOptIn() == null){
				throw new MissingParameterException("Missing optin status");
			}
		}
	}

	
	public void CheckAllKey(Set<AlertDataDTO> listAlertDataDTO) throws JrafDomainException {
		for (AlertDataDTO alertDataDTO : listAlertDataDTO) {
			CheckIfKeyIsAllowed(alertDataDTO);
		}
	}
	

	public void CheckIfKeyIsMissing(Set<AlertDataDTO> listAlertDataDTO) throws JrafDomainException {
		for (RefAlertDTO refAlertDTO : listRefInp) {
			if(findAlertDataByKey(listAlertDataDTO, refAlertDTO) == null && refAlertDTO.getMandatory().equals("Y")){ // Cas ou la clÃ© que l'on recherche est obligatoire
				throw new MissingParameterException("Missing mandatory field : "+ refAlertDTO.getKey());
			}
		}
	}
	
	
	/**
	 * Check if key send by user is allowed
	 * @param listAlertDataDTO
	 * @return false if not allowed and throws exception
	 * @throws JrafDomainException
	 */
	public void CheckIfKeyIsAllowed(AlertDataDTO alertDataDTO) throws JrafDomainException {
		for (RefAlertDTO refAlertDTO : listRefInp) {
			if(alertDataDTO.getKey().equals(refAlertDTO.getKey())){
				return;
			}
		}
		throw new MissingParameterException("field : "+ alertDataDTO.getKey() +" not allowed");
	}
	

	public AlertDataDTO findAlertDataByKey(Set<AlertDataDTO> listAlertDataDTO, String ref) throws InvalidParameterException, MissingParameterException{
		RefAlertDTO refAlert = findRefAlertByString(ref);
		return findAlertDataByKey(listAlertDataDTO, refAlert);
	}
	
	public AlertDataDTO findAlertDataByKey(Set<AlertDataDTO> listAlertDataDTO, RefAlertDTO refAlert) throws InvalidParameterException, MissingParameterException{
		for (AlertDataDTO alertDataDTO : listAlertDataDTO) {
			if(alertDataDTO.getKey().equals(refAlert.getKey())){
				try {
					switch (refAlert.getValue()){
						case "DATE":
					        SimpleDateFormat formatter = new SimpleDateFormat(DateFormat);
							Date d = formatter.parse(alertDataDTO.getValue());
							break;
						default : 
							break;
							
					}
				} catch (ParseException e) {
					throw new InvalidParameterException("field "+ refAlert.getKey() + " is not correctely parsed");
				}
				return alertDataDTO;
			}
		}
		if(refAlert.getMandatory().equals("N")){
			return null;
		}
		throw new MissingParameterException("field "+ refAlert.getKey() + " not present in alertData");
	}
	
	public RefAlertDTO findRefAlertByString(String ref) throws InvalidParameterException {
		return findRefAlertByString(ref, "INP");
	}
	
	public RefAlertDTO findRefAlertByString(String ref, String usage) throws InvalidParameterException {
		List<RefAlertDTO> use = null;
		switch (usage) {
		case env_key_str:
			use = listRefEnv;
			break;
		case "INP":
			use = listRefInp;
			break;
		default:
			use = listRefInp;
			break;
		}
		for (RefAlertDTO refAlertDTO : use) {
			if(refAlertDTO.getKey().equals(ref) /*|| refAlertDTO.getMandatory() == "N"*/ ){
				return refAlertDTO;
			}
		}
		throw new InvalidParameterException("Key " + ref + " not found in referencial");
	}

	public Integer getComPrefId(String gin, CommunicationPreferencesRequestDTO request, RequestorDTO requestor) throws JrafDomainException {
					
		if(request != null) {
			CommunicationPreferencesDTO foundComPref = communicationPreferencesDS.findComPrefId(gin, request.getCommunicationPreferencesDTO().getDomain(), request.getCommunicationPreferencesDTO().getCommunicationGroupeType(), request.getCommunicationPreferencesDTO().getCommunicationType());
			Set<CommunicationPreferences> setCommunicationPreferences = new HashSet<CommunicationPreferences>();
			if (foundComPref == null) { //si on ne trouve pas ses comPref

				CommunicationPreferencesDTO comPrefToUpdate = IndividuTransform.transformToComPrefDTO(request, requestor);
				//List<CommunicationPreferencesDTO> sComPref = communicationPreferencesDS.findByExample(ComPrefToUpdate);
				CommunicationPreferencesDTO sComPref = 
						communicationPreferencesDS.findComPrefId(gin, comPrefToUpdate.getDomain(), comPrefToUpdate.getComGroupType(), comPrefToUpdate.getComGroupType());
				if(sComPref == null){
					setCommunicationPreferences.add(CommunicationPreferencesTransform.dto2Bo2(comPrefToUpdate));
				}					
				if (!UList.isNullOrEmpty(setCommunicationPreferences)) {
					individuDS.updateIndividualComPref(setCommunicationPreferences, gin);
				}
				foundComPref = communicationPreferencesDS.findComPrefId(gin, request.getCommunicationPreferencesDTO().getDomain(), request.getCommunicationPreferencesDTO().getCommunicationGroupeType(), request.getCommunicationPreferencesDTO().getCommunicationType());
				//REPIND-1736: Return negative value in case of Delete Compref Mode
				if (foundComPref == null) return new Integer(-1);
				return foundComPref.getComPrefId();
			}
			return foundComPref.getComPrefId();
		}
		throw new JrafDomainException("Missing ComPref for user");
	}
	

	public void updateIndividualForAlert(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {
		
		IndividuDTO individuDTO = IndividuTransform.transformToIndividuDTO(request);
		if( individuDTO != null){
			List<PostalAddressDTO> postalAddressDTOList = IndividuTransform.transformToPostalAddressDTO(request.getPostalAddressRequestDTO());
			List<EmailDTO> emailDTOList = IndividuTransform.transformToEmailDTO(request.getEmailRequestDTO());
			SignatureDTO signatureAPP = IndividuTransform.transformToSignatureAPP(request.getRequestorDTO());
					
			List<CommunicationPreferencesDTO> comPrefsDTOList = IndividuTransform.transformToComPrefsDTO(request.getCommunicationPreferencesRequestDTO(),request.getRequestorDTO());
			
			// GETTING UPDATE MODE FOR COMMUNICATION PREFERENCES
			UpdateModeEnum updateModeCommPrefs = UpdateModeEnum.getEnum(request.getUpdateCommunicationPrefMode());
			// GETTING NEWSLETTER MEDIASENDING FLAG
			String newsletterMediaSending = StringUtils.isNotEmpty(request.getNewsletterMediaSending()) ? request.getNewsletterMediaSending() : "";
			// GETTING MYACCOUNT STATUS
			String status = StringUtils.isNotEmpty(request.getStatus()) ? request.getStatus() : null;
					
			// SHARED EMAIL CHECKING
			if (emailDTOList != null) {
				checkSharedEmail(emailDTOList.get(0), individuDTO);	
			}	
			
			// Transform email
			if (request.getEmailRequestDTO() != null) {
				List<EmailRequestDTO> updatedEmailList = new ArrayList<EmailRequestDTO>();
				for (EmailRequestDTO emailWS : request.getEmailRequestDTO()) {
					EmailRequestDTO updatedEmail = null;
					
					
					if (emailWS.getEmailDTO() != null) {
						updatedEmail = new EmailRequestDTO();
						com.airfrance.repind.dto.ws.EmailDTO mail = new com.airfrance.repind.dto.ws.EmailDTO();
						mail.setEmail(emailWS.getEmailDTO().getEmail());
						
						if (emailWS.getEmailDTO().getEmailOptin() != null) {
							mail.setEmailOptin(emailWS.getEmailDTO().getEmailOptin());
						}
						else {
							mail.setEmailOptin(NATFieldsEnum.NONE.getValue());
						}
						
						if (emailWS.getEmailDTO().getMediumCode() != null) {
							mail.setMediumCode(emailWS.getEmailDTO().getMediumCode());
						}
						else {
							mail.setMediumCode(MediumCodeEnum.HOME.toString());
						}
						
						if (emailWS.getEmailDTO().getMediumStatus() != null) {
							mail.setMediumStatus(emailWS.getEmailDTO().getMediumStatus());
						}
						else {
							mail.setMediumStatus(MediumStatusEnum.VALID.toString());
						}
						
						updatedEmail.setEmailDTO(mail);
					}
					
					updatedEmailList.add(updatedEmail);
				}
				request.setEmailRequestDTO(updatedEmailList);
			}
			
			// Preferences
//			List<PreferenceDTO> preferenceDTOList = IndividuRequestTransform.transformToPreferenceDTO(request.getPreferenceDataRequest());
//			individuDTO.setPreferenceDTO(preferenceDTOList); 		
			
			ReturnDetailsDTO resultDetails = myAccountDS.updateMyAccountCustomer(individuDTO, postalAddressDTOList, null, emailDTOList, null, comPrefsDTOList, newsletterMediaSending, signatureAPP, status, updateModeCommPrefs.toString(), null, null);
			LOGGER.info(resultDetails);
			
		}
	}


	public void checkComprefAndAlert(AlertRequestDTO alertRequest,	List<CommunicationPreferencesRequestDTO> listComPref) throws InvalidParameterException{
		if (alertRequest != null && listComPref != null && !listComPref.isEmpty()) {
			for (CommunicationPreferencesRequestDTO comPrefRequest : listComPref) {
				String type = getComPrefType(comPrefRequest);
				if (alertRequest.getAlertDTO() != null && !alertRequest.getAlertDTO().isEmpty()) {
					for (com.airfrance.repind.dto.ws.AlertDTO alert : alertRequest.getAlertDTO()) {
						validateComprefAndAlert(alert, type);
					}
				}
			}
		}
	}


	// REPIND-555 : Remove check if prospect
	private void optoutComPrefAlert(String gin, CommunicationPreferencesRequestDTO comRequest, RequestorDTO requestor) throws JrafDomainException {
		int comPrefId = getComPrefId(gin, comRequest, requestor);
		alertDS.optoutByComPrefId(comPrefId);	
	}
	
	/**
	 * Check if we reach the maximum of alert avaiable
	 * @param gin
	 * @throws NumberFormatException 
	 * @throws JrafDomainException
	 */
	private void checkMaxAlert(String gin, String isOptin) throws JrafDomainException {
		AlertDTO search = new AlertDTO();
		search.setSgin(gin);
		search.setOptIn("Y");
		Integer count = (CountWhereAlert(search));
		Integer maxAllowed = Integer.parseInt(findRefAlertByString(MaxAlert, env_key_str).getValue());
		if(count != null && count >= maxAllowed && isOptin.equals("Y")){
			throw new JrafDomainException("Maximum promo alert reached");
		}
	}
	
	
	private void checkComPrefStatus(AlertDTO alert) throws JrafDomainException {
		AlertDTO search = new AlertDTO();
		search.setSgin(alert.getSgin());
		search.setOptIn("Y");
		List<AlertDTO> listOptInAlert = findAlertByWhereClause(search);
		if(alert.getOptIn().equals("N") && listOptInAlert.size() == 0){
			AlertDataDTO comPref = findAlertDataByKey(alert.getAlertDataDTO(), this.PromoKeyComPrefId);
			updateComPrefStatus(alert.getSgin(), comPref.getValue(), alert.getType(), alert.getOptIn());
		}else if(alert.getOptIn().equals("Y")){//SI on repasse a oui mais que l'on est pas deja a 50
			if(listOptInAlert.size() < Integer.parseInt(findRefAlertByString(MaxAlert, "ENV").getValue())){
				AlertDataDTO comPref = findAlertDataByKey(alert.getAlertDataDTO(), this.PromoKeyComPrefId);
				updateComPrefStatus(alert.getSgin(), comPref.getValue(), alert.getType(),alert.getOptIn());
			}else{
				throw new JrafDomainException("Maximum promo alert reached");
			}
		}
	}
	// REPIND-555 : Remove check if prospect
	private void updateComPrefStatus(String gin, String comPrefID, String type, String status) throws JrafDomainException {
		CommunicationPreferencesDTO search = new CommunicationPreferencesDTO();
		search.setComPrefId(Integer.parseInt(comPrefID));
		CommunicationPreferencesDTO comPref =  communicationPreferencesDS.get(search);
		if(comPref == null ){
			throw new InvalidParameterException("No comPref for current user");
		}
		comPref.setSubscribe(status);
		if(comPref.getMarketLanguageDTO() != null && comPref.getMarketLanguageDTO().size() >= 1){
			Set<MarketLanguageDTO> updML = new HashSet<MarketLanguageDTO>();
			for (MarketLanguageDTO marketLanguageDTO : comPref.getMarketLanguageDTO()) {
				marketLanguageDTO.setOptIn(status);
				updML.add(marketLanguageDTO);
			}
			comPref.setMarketLanguageDTO(updML);
		}
		communicationPreferencesDS.update(comPref);
	}
	
	private void updateComPref(String gin, String comPrefId, SignatureDTO signature) throws JrafDomainException {
		updateComPref(gin, Integer.parseInt(comPrefId), signature);
	}

	// REPIND-555 : Remove check if prospect
	private void updateComPref(String gin, Integer comPrefId, SignatureDTO signature) throws JrafDomainException {
		CommunicationPreferencesDTO search = new CommunicationPreferencesDTO();
		search.setComPrefId(comPrefId);
		CommunicationPreferencesDTO comPref =  communicationPreferencesDS.get(search);
		if(comPref == null ){
			throw new InvalidParameterException("No comPref for current user");
		}
		comPref.setModificationDate(new Date());
		comPref.setModificationSignature(signature.getSignature());
		comPref.setModificationSite(signature.getSite());
		communicationPreferencesDS.update(comPref);
	}

	

	private void checkSharedEmail(EmailDTO emailDTO, IndividuDTO individuDTO) throws SharedEmailException, JrafDomainException {
		
		if (!myAccountDS.isAccountPur(individuDTO.getSgin()) && !emailDS.emailExist(individuDTO.getSgin(), emailDTO.getEmail())) {
			if (emailDS.checkSharedFlyingBlueEmail(emailDTO.getEmail(),individuDTO.getSgin())) {
				throw new SharedEmailException("Email identifier already used", emailDTO.getEmail());				
			}
		}
	}
	
	private void validateComprefAndAlert(com.airfrance.repind.dto.ws.AlertDTO alert, String type) throws InvalidParameterException {
		if (!alert.getType().equalsIgnoreCase(type)) {
			throw new InvalidParameterException("Alert type and communication preference group type are different");
		}
	}

	private String getComPrefType(CommunicationPreferencesRequestDTO comPrefRequest) {
		if (comPrefRequest.getCommunicationPreferencesDTO() != null) {
			return comPrefRequest.getCommunicationPreferencesDTO().getCommunicationGroupeType();
		}
		else return null;
	}
	
	/*
	 * Zone for choose database
	 */

	// REPIND-555 : Remove check if prospect
	public AlertDTO createAlert(AlertDTO alert) throws JrafDomainException {
		alertDS.create(alert);
		return alert;
		
	}
	// REPIND-555 : Remove check if prospect
	public AlertDTO updateAlert(AlertDTO alert) throws JrafDomainException {
		alertDS.update(alert);
		AlertDTO ret = new AlertDTO();
		ret.setAlertId(alert.getAlertId());
		return alertDS.get(ret);
	}

	// REPIND-555 : Remove check if prospect
	public Integer CountWhereAlert(AlertDTO search) throws JrafDomainException {
		return alertDS.countWhere(search);
	}

	// REPIND-555 : Remove check if prospect
	public List<AlertDTO> findByGin(String gin) throws JrafDomainException {
		return alertDS.findAlert(gin);
	}

	// REPIND-555 : Remove check if prospect
	private List<AlertDTO> findAlertByWhereClause(AlertDTO search) throws JrafDomainException {
		 return alertDS.findByWhereClause(search);
	}

		
}	
