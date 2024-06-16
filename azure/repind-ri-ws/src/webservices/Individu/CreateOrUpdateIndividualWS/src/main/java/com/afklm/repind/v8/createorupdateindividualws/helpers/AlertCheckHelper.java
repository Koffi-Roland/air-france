package com.afklm.repind.v8.createorupdateindividualws.helpers;

import com.afklm.soa.stubs.w000442.v8.request.AlertRequest;
import com.afklm.soa.stubs.w000442.v8.request.ComunicationPreferencesRequest;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.Alert;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.AlertDTO;
import com.airfrance.repind.dto.individu.AlertDataDTO;
import com.airfrance.repind.dto.reference.RefAlertDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.individu.internal.AlertDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.reference.internal.RefAlertDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class AlertCheckHelper {

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
	
	private static final String IndividualCode = "I";
	private static final String ProspectCode = "P";
	
//	public void processAlert(String gin, CreateUpdateIndividualRequest request, SignatureDTO signatureAPP) throws JrafDomainException, ParseException {
//		for (ComunicationPreferencesRequest comPrefRequest : request.getComunicationPreferencesRequest()) { //TODO : Voir si utile ?
//			if(request.getAlertRequest() != null) {
//				UpdateAlertListIndividual(gin, IndividuRequestTransform.transformToAlertDTO(request.getAlertRequest()), 
//						getComPrefId(gin, comPrefRequest, request.getRequestor()), signatureAPP);
//			}
//			// check de l'optin compref et si il est  optout toutes ses alertes
//			if (comPrefRequest != null && comPrefRequest.getCommunicationPreferences() != null) {
//				if (comPrefRequest.getCommunicationPreferences().getOptIn() != null && "N".equalsIgnoreCase(comPrefRequest.getCommunicationPreferences().getOptIn())) {
//					optoutComPrefAlert(gin, comPrefRequest, request.getRequestor());
//				}
//			}
//		}
//	}
//
//	/**
//	 * This method call to update the list of alert of an individual
//	 * 
//	 * @param gin
//	 * @param comPrefId 
//	 * @param emailDTO 
//	 * @param alertListDTO
//	 * @param signature
//	 * @throws JrafDomainException
//	 * @throws ParseException 
//	 */
//	public void UpdateAlertListIndividual(String gin, List<AlertDTO> AlertList, Integer comPrefId, SignatureDTO signature) throws JrafDomainException, ParseException {
//		updatelistsRef(AlertList.get(0).getType());
//		for (AlertDTO alert : AlertList) {
//			UpdateAlertIndividual(gin, alert, comPrefId, signature);
//		}	
//		
//		updateComPref(gin, comPrefId, signature);
//	}
//	
//	/**
//	 * Create or update An alert for an individual
//	 * @param gin
//	 * @param alert
//	 * @param comPrefId
//	 * @param signature
//	 * @throws JrafDomainException
//	 * @throws ParseException
//	 */
//	public void UpdateAlertIndividual(String gin, AlertDTO alert, Integer comPrefId, SignatureDTO signature) throws JrafDomainException, ParseException {
//		checkMaxAlert(gin, alert.getOptIn());
//		alert.setSgin(gin);
//		alert.setModificationDate(new Date());
//		alert.setModificationSignature(signature.getSignature());
//		alert.setModificationSite(signature.getSite());
//		if(alert.getAlertId() != null){
//			checkIfAlertExist(gin, alert);
//			alert = updateAlert(alert);
//			checkComPrefStatus(alert);
//		}else{
//			alert.getAlertDataDTO().add(new AlertDataDTO(PromoKeyComPrefId, comPrefId.toString()));
//			alert.setCreationDate(new Date());
//			alert.setCreationSignature(signature.getSignature());
//			alert.setCreationSite(signature.getSite());
//			alert = createAlert(alert);
//			checkComPrefStatus(alert);
//		}
//	}

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
//
//	public Integer getComPrefId(String gin, ComunicationPreferencesRequest request, RequestorV2 requestor) throws JrafDomainException {
//		
//		if(request != null) {
//			CommunicationPreferencesDTO foundComPref = communicationPreferencesDS.findComPrefId(gin, request.getCommunicationPreferences().getDomain(), request.getCommunicationPreferences().getCommunicationGroupeType(), request.getCommunicationPreferences().getCommunicationType());
//			Set<CommunicationPreferences> setCommunicationPreferences = new HashSet<CommunicationPreferences>();
//			if (foundComPref == null) { //si on ne trouve pas ses comPref
//
//				CommunicationPreferencesDTO comPrefToUpdate = IndividuRequestTransform.transformToComPrefDTO(request, requestor);
//				//List<CommunicationPreferencesDTO> sComPref = communicationPreferencesDS.findByExample(ComPrefToUpdate);
//				CommunicationPreferencesDTO sComPref = 
//						communicationPreferencesDS.findComPrefId(gin, comPrefToUpdate.getDomain(), comPrefToUpdate.getComGroupType(), comPrefToUpdate.getComGroupType());
//				if(sComPref == null){
//					setCommunicationPreferences.add(CommunicationPreferencesTransform.dto2Bo2(comPrefToUpdate));
//				}					
//				if (!UList.isNullOrEmpty(setCommunicationPreferences)) {
//					individuDS.updateIndividualComPref(setCommunicationPreferences, gin);
//				}
//				foundComPref = communicationPreferencesDS.findComPrefId(gin, request.getCommunicationPreferences().getDomain(), request.getCommunicationPreferences().getCommunicationGroupeType(), request.getCommunicationPreferences().getCommunicationType());
//				return foundComPref.getComPrefId();
//			}
//			return foundComPref.getComPrefId();
//		}
//		throw new JrafDomainException("Missing ComPref for user");
//	}
//	
//
//	public void updateIndividualForAlert(CreateUpdateIndividualRequest request) throws JrafDomainException {
//		
//		IndividuDTO individuDTO = IndividuRequestTransform.transformToIndividuDTO(request);
//		if( individuDTO != null){
//			List<PostalAddressDTO> postalAddressDTOList = IndividuRequestTransform.transformToPostalAddressDTO(request.getPostalAddressRequest());
//			List<EmailDTO> emailDTOList = IndividuRequestTransform.transformToEmailDTO(request.getEmailRequest());
//			SignatureDTO signatureAPP = IndividuRequestTransform.transformToSignatureAPP(request.getRequestor());
//					
//			List<CommunicationPreferencesDTO> comPrefsDTOList = IndividuRequestTransform.transformToComPrefsDTO(request.getComunicationPreferencesRequest(),request.getRequestor());
//			
//			// GETTING UPDATE MODE FOR COMMUNICATION PREFERENCES
//			UpdateModeEnum updateModeCommPrefs = UpdateModeEnum.getEnum(request.getUpdateCommunicationPrefMode());
//			// GETTING NEWSLETTER MEDIASENDING FLAG
//			String newsletterMediaSending = StringUtils.isNotEmpty(request.getNewsletterMediaSending()) ? request.getNewsletterMediaSending() : "";
//			// GETTING MYACCOUNT STATUS
//			String status = StringUtils.isNotEmpty(request.getStatus()) ? request.getStatus() : null;
//					
//			// SHARED EMAIL CHECKING
//			if (emailDTOList != null) {
//				checkSharedEmail(emailDTOList.get(0), individuDTO);	
//			}		
//			
//			// Preferences
////			List<PreferenceDTO> preferenceDTOList = IndividuRequestTransform.transformToPreferenceDTO(request.getPreferenceDataRequest());
////			individuDTO.setPreferenceDTO(preferenceDTOList); 		
//			
//			ReturnDetailsDTO resultDetails = myAccountDS.updateMyAccountCustomer(individuDTO, postalAddressDTOList, null, emailDTOList, null, comPrefsDTOList, newsletterMediaSending, signatureAPP, status, updateModeCommPrefs.toString());
//			LOGGER.info(resultDetails);
//			
//		}
//	}


	public void checkComprefAndAlert(AlertRequest alertRequest,	List<ComunicationPreferencesRequest> listComPref) throws InvalidParameterException{
		if (alertRequest != null && listComPref != null && !listComPref.isEmpty()) {
			for (ComunicationPreferencesRequest comPrefRequest : listComPref) {
				String type = getComPrefType(comPrefRequest);
				if (alertRequest.getAlert() != null && !alertRequest.getAlert().isEmpty()) {
					for (Alert alert : alertRequest.getAlert()) {
						validateComprefAndAlert(alert, type);
					}
				}
			}
		}
	}

	/*
	 * Transform Alert -> AlertProspect Zone
	 */
	// REPIND-555 : No need to transform Alert to AlertProspect. Moreover, these methods have nothing to do here.

//
//	public AlertProspectDTO transformAlertToAlertProspect(AlertDTO alert) {
//		AlertProspectDTO ret = new AlertProspectDTO();
//		if(alert.getAlertId() != null){
//			ret.setAlertId(alert.getAlertId());
//		}
//		if(alert.getSgin() != null){
//			ret.setSgin(alert.getSgin());
//		}
//		if(alert.getCreationDate()!= null){
//			ret.setCreationDate(alert.getCreationDate());
//		}
//		if(alert.getCreationSignature()!= null){
//			ret.setCreationSignature(alert.getCreationSignature());
//		}
//		if(alert.getCreationSite()!= null){
//			ret.setCreationSite(alert.getCreationSite());
//		}
//		if(alert.getModificationDate()!= null){
//			ret.setModificationDate(alert.getModificationDate());
//		}
//		if(alert.getModificationSignature()!= null){
//			ret.setModificationSignature(alert.getModificationSignature());
//		}
//		if(alert.getModificationSite()!= null){
//			ret.setModificationSite(alert.getModificationSite());
//		}
//		if(alert.getOptIn()!= null){
//			ret.setOptIn(alert.getOptIn());
//		}
//		if(alert.getType()!= null){
//			ret.setType(alert.getType());
//		}
//		if(alert.getAlertDataDTO()!= null && alert.getAlertDataDTO().size() > 0){
//			ret.setAlertDataDTO(transformAlertDataToAlertDataProspect(alert.getAlertDataDTO()));
//		}
//		return ret;
//	}
//	
//	public Set<AlertDataProspectDTO> transformAlertDataToAlertDataProspect(Set<AlertDataDTO> list) {
//		Set<AlertDataProspectDTO> ret = new HashSet<AlertDataProspectDTO>();
//		for (AlertDataDTO alertDataDTO : list) {
//			ret.add(transformAlertDataToAlertDataProspect(alertDataDTO));
//		}
//		return ret;
//	}
//	
//	public AlertDataProspectDTO transformAlertDataToAlertDataProspect(AlertDataDTO alertData) {
//		AlertDataProspectDTO ret = new AlertDataProspectDTO();
//		if(alertData.getAlertDataId() != null){
//			ret.setAlertDataId(alertData.getAlertDataId());
//		}
//		if(alertData.getKey() != null){
//			ret.setKey(alertData.getKey());
//		}
//		if(alertData.getValue() != null){
//			ret.setValue(alertData.getValue());
//		}
//		return ret;
//	}
//	
//	/*
//	 * Transform AlertProspect -> Alert Zone
//	 */
//	public List<AlertDTO> transformAlertProspectToAlert(List<AlertProspectDTO> list) {
//		List<AlertDTO> ret = new ArrayList<AlertDTO>();
//		for (AlertProspectDTO alertProspectDTO : list) {
//			ret.add(transformAlertProspectToAlert(alertProspectDTO));
//		}
//		return ret;
//	}
//
//	public AlertDTO transformAlertProspectToAlert(AlertProspectDTO alert) {
//		AlertDTO ret = new AlertDTO();
//		if(alert.getAlertId() != null){
//			ret.setAlertId(alert.getAlertId());
//		}
//		if(alert.getSgin() != null){
//			ret.setSgin(alert.getSgin());
//		}
//		if(alert.getCreationDate()!= null){
//			ret.setCreationDate(alert.getCreationDate());
//		}
//		if(alert.getCreationSignature()!= null){
//			ret.setCreationSignature(alert.getCreationSignature());
//		}
//		if(alert.getCreationSite()!= null){
//			ret.setCreationSite(alert.getCreationSite());
//		}
//		if(alert.getModificationDate()!= null){
//			ret.setModificationDate(alert.getModificationDate());
//		}
//		if(alert.getModificationSignature()!= null){
//			ret.setModificationSignature(alert.getModificationSignature());
//		}
//		if(alert.getModificationSite()!= null){
//			ret.setModificationSite(alert.getModificationSite());
//		}
//		if(alert.getOptIn()!= null){
//			ret.setOptIn(alert.getOptIn());
//		}
//		if(alert.getType()!= null){
//			ret.setType(alert.getType());
//		}
//		if(alert.getAlertDataDTO()!= null && alert.getAlertDataDTO().size() > 0){
//			ret.setAlertDataDTO(transformAlertDataProspectToAlertData(alert.getAlertDataDTO()));
//		}
//		return ret;
//	}
//	
//	public Set<AlertDataDTO> transformAlertDataProspectToAlertData(Set<AlertDataProspectDTO> list) {
//		Set<AlertDataDTO> ret = new HashSet<AlertDataDTO>();
//		for (AlertDataProspectDTO alertDataProspectDTO : list) {
//			ret.add(transformAlertDataToAlertDataProspect(alertDataProspectDTO));
//		}
//		return ret;
//	}
//	
//	public AlertDataDTO transformAlertDataToAlertDataProspect(AlertDataProspectDTO alertDataProspect) {
//		AlertDataDTO ret = new AlertDataDTO();
//		if(alertDataProspect.getAlertDataId() != null){
//			ret.setAlertDataId(alertDataProspect.getAlertDataId());
//		}
//		if(alertDataProspect.getKey() != null){
//			ret.setKey(alertDataProspect.getKey());
//		}
//		if(alertDataProspect.getValue() != null){
//			ret.setValue(alertDataProspect.getValue());
//		}
//		return ret;
//	}
	
	
//	/*
//	 * Private function
//	 */
//
//	// REPIND-555 : Remove check if prospect
//	private void optoutComPrefAlert(String gin, ComunicationPreferencesRequest comRequest, RequestorV2 requestor) throws JrafDomainException {
//		int comPrefId = getComPrefId(gin, comRequest, requestor);
//		alertDS.optoutByComPrefId(comPrefId);	
//	}


	// REPIND-555 : No need to keep this method. Alert and alertProspect have same behaviour
//	private String findTypeUser(String gin) throws JrafDomainException {
//		IndividuDTO sIndividu =  individuDS.getByGin(gin);
//		if(sIndividu == null){
//
//			throw new NotFoundException("Gin not found");
//			
//		} else {
//			
//			if(sIndividu.getType().equals("W")) {
//				return ProspectCode;
//			} else {
//				return IndividualCode;
//			}
//			
//		}
//	}
//	
//	
//	
//	/**
//	 * Check if we reach the maximum of alert avaiable
//	 * @param gin
//	 * @throws NumberFormatException 
//	 * @throws JrafDomainException
//	 */
//	private void checkMaxAlert(String gin, String isOptin) throws JrafDomainException {
//		AlertDTO search = new AlertDTO();
//		search.setSgin(gin);
//		search.setOptIn("Y");
//		Integer count = (CountWhereAlert(search));
//		Integer maxAllowed = Integer.parseInt(findRefAlertByString(MaxAlert, env_key_str).getValue());
//		if(count != null && count >= maxAllowed && isOptin.equals("Y")){
//			throw new JrafDomainException("Maximum promo alert reached");
//		}
//	}
//	
//	
//	private void checkComPrefStatus(AlertDTO alert) throws JrafDomainException {
//		AlertDTO search = new AlertDTO();
//		search.setSgin(alert.getSgin());
//		search.setOptIn("Y");
//		List<AlertDTO> listOptInAlert = findAlertByWhereClause(search);
//		if(alert.getOptIn().equals("N") && listOptInAlert.size() == 0){
//			AlertDataDTO comPref = findAlertDataByKey(alert.getAlertDataDTO(), this.PromoKeyComPrefId);
//			updateComPrefStatus(alert.getSgin(), comPref.getValue(), alert.getType(), alert.getOptIn());
//		}else if(alert.getOptIn().equals("Y")){//SI on repasse a oui mais que l'on est pas deja a 50
//			if(listOptInAlert.size() < Integer.parseInt(findRefAlertByString(MaxAlert, "ENV").getValue())){
//				AlertDataDTO comPref = findAlertDataByKey(alert.getAlertDataDTO(), this.PromoKeyComPrefId);
//				updateComPrefStatus(alert.getSgin(), comPref.getValue(), alert.getType(),alert.getOptIn());
//			}else{
//				throw new JrafDomainException("Maximum promo alert reached");
//			}
//		}
//	}
//	// REPIND-555 : Remove check if prospect
//	private void updateComPrefStatus(String gin, String comPrefID, String type, String status) throws JrafDomainException {
//		CommunicationPreferencesDTO search = new CommunicationPreferencesDTO();
//		search.setComPrefId(Integer.parseInt(comPrefID));
//		CommunicationPreferencesDTO comPref =  communicationPreferencesDS.get(search);
//		if(comPref == null ){
//			throw new InvalidParameterException("No comPref for current user");
//		}
//		comPref.setSubscribe(status);
//		if(comPref.getMarketLanguageDTO() != null && comPref.getMarketLanguageDTO().size() >= 1){
//			Set<MarketLanguageDTO> updML = new HashSet<MarketLanguageDTO>();
//			for (MarketLanguageDTO marketLanguageDTO : comPref.getMarketLanguageDTO()) {
//				marketLanguageDTO.setOptIn(status);
//				updML.add(marketLanguageDTO);
//			}
//			comPref.setMarketLanguageDTO(updML);
//		}
//		communicationPreferencesDS.update(comPref);
//	}
//	
//	private void updateComPref(String gin, String comPrefId, SignatureDTO signature) throws JrafDomainException {
//		updateComPref(gin, Integer.parseInt(comPrefId), signature);
//	}
//
//	// REPIND-555 : Remove check if prospect
//	private void updateComPref(String gin, Integer comPrefId, SignatureDTO signature) throws JrafDomainException {
//		CommunicationPreferencesDTO search = new CommunicationPreferencesDTO();
//		search.setComPrefId(comPrefId);
//		CommunicationPreferencesDTO comPref =  communicationPreferencesDS.get(search);
//		if(comPref == null ){
//			throw new InvalidParameterException("No comPref for current user");
//		}
//		comPref.setModificationDate(new Date());
//		comPref.setModificationSignature(signature.getSignature());
//		comPref.setModificationSite(signature.getSite());
//		communicationPreferencesDS.update(comPref);
//	}

	
//
//	private void checkSharedEmail(EmailDTO emailDTO, IndividuDTO individuDTO) throws SharedEmailException, JrafDomainException {
//		
//		if (!myAccountDS.isAccountPur(individuDTO.getSgin()) && !emailDS.isValidEmail(individuDTO.getSgin(), emailDTO.getEmail())) {
//			if (emailDS.checkSharedFlyingBlueEmail(emailDTO.getEmail(),individuDTO.getSgin())) {
//				throw new SharedEmailException("Email identifier already used", emailDTO.getEmail());				
//			}
//		}
//	}
	
	private void validateComprefAndAlert(Alert alert, String type) throws InvalidParameterException {
		if (!alert.getType().equalsIgnoreCase(type)) {
			throw new InvalidParameterException("Alert type and communication preference group type are different");
		}
	}

	private String getComPrefType(ComunicationPreferencesRequest comPrefRequest) {
		if (comPrefRequest.getCommunicationPreferences() != null) {
			return comPrefRequest.getCommunicationPreferences().getCommunicationGroupeType();
		}
		else return null;
	}
//	
//	/*
//	 * Zone for choose database
//	 */
//
//	// REPIND-555 : Remove check if prospect
//	public AlertDTO createAlert(AlertDTO alert) throws JrafDomainException {
//		alertDS.create(alert);
//		return alert;
//		
//	}
//	// REPIND-555 : Remove check if prospect
//	public AlertDTO updateAlert(AlertDTO alert) throws JrafDomainException {
//		alertDS.update(alert);
//		AlertDTO ret = new AlertDTO();
//		ret.setAlertId(alert.getAlertId());
//		return alertDS.get(ret);
//	}
//
//	// REPIND-555 : Remove check if prospect
//	public Integer CountWhereAlert(AlertDTO search) throws JrafDomainException {
//		return alertDS.countWhere(search);
//	}
//
//	// REPIND-555 : Remove check if prospect
//	public List<AlertDTO> findByGin(String gin) throws JrafDomainException {
//		return alertDS.findAlert(gin);
//	}
//
//	// REPIND-555 : Remove check if prospect
//	private List<AlertDTO> findAlertByWhereClause(AlertDTO search) throws JrafDomainException {
//		 return alertDS.findByWhereClause(search);
//	}

		
}	
