/**
 * 
 */
package com.afklm.rigui.services.marketing;

import com.afklm.soa.stubs.w000548.v1.HandleCommunicationV1;
import com.afklm.soa.stubs.w000548.v1.data.CampaignParameter;
import com.afklm.soa.stubs.w000548.v1.data.HandleCommunicationRequest;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.dto.adresse.EmailDTO;
import com.afklm.rigui.dto.individu.IndividuDTO;
import com.afklm.rigui.util.SicStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import javax.xml.ws.soap.SOAPFaultException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

@Service("handleCommunicationImpl-v1")
@WebService(endpointInterface = "com.airfrance.crmpushws.myaccount.handlecommunicationrequest.service.HandleCommunicationV1")
public class HandleCommunication implements HandleCommunicationV1 {

	private static Log LOGGER = LogFactory.getLog(HandleCommunication.class);

	private static HashMap<String, String[]> allTagsByCampaign;
	
    private ApplicationContext context;

	// STATIC REQUEST
	private static String GLOBAL_ClientID = "REPIND";
	private static boolean GLOBAL_IsRealTime = false;
	private static Date GLOBAL_CampaignScheduleTime = null;
	private static Date GLOBAL_CampaignValidityDate = null;
	private static boolean GLOBAL_CheckContract = true;
	private static String GLOBAL_ContractVersion = "1";
	private static final int MAX_SIZE = 10;

	// DYNAMIC REQUEST KEYS
	private static String KEY_GIN = "gin";
	private static String KEY_CONNECTION_ID = "connection_id";
	private static String KEY_COMM_PREFERENCES = "comm_preferences";
	private static String KEY_AIRLINE = "airline";
	private static String KEY_POS = "pos";
	private static String KEY_PASSWORD = "password";
	private static String KEY_FORMER_EMAIL = "former_email";
	private static String KEY_NEW_EMAIL = "new_email";
	private static String KEY_LAST_CONNECTION_DATE = "last_connection_date";
	private static String KEY_REQUESTOR = "requestor";
	private static String KEY_CIN = "cin";
	private static String KEY_CHANNEL = "channel";
	private static String KEY_ACTION_CODE = "action_code";

	private static String KEY_LANGUAGE = "language";
	private static String KEY_ROUTAGE_CODE = "routage_code";
	private static String KEY_KOBA_ACTION_CODE = "koba_action_code";
	private static String KEY_KOBA_LETTER_CODE = "koba_letter_code";
	private static String KEY_EMAIL = "email";
	private static String KEY_AIN = "ain";
	private static String KEY_DATE_MAJ = "date_maj";
	private static String KEY_STREET_NB = "street_nb";
	private static String KEY_COMPL_ADDRESS = "compl_address";
	private static String KEY_THIRD_ROW_ADDRESS = "third_row_address";
	private static String KEY_FOURTH_ROW_ADDRESS = "fourth_row_address";
	private static String KEY_TIER_LEVEL = "tier_level";
	private static String KEY_LANGUAGE_ISIS = "language_isis";
	private static String KEY_PHONE_NUMBER = "phone_number";
	private static String KEY_SALUTATION_CARD = "salutation_card";
	private static String KEY_SALUTATION_LETTER = "salutation_letter";
	private static String KEY_SALUTATION_ENVELOP = "salutation_envelop";
	private static String KEY_SEQUENCE_ID = "sequence_id";
	private static String KEY_COUNTRY_CODE = "country_code";
	private static String KEY_TITLE = "title";
	private static String KEY_FIRM = "firm";

	private static String KEY_EMPLOYEE_FN = "employee_first_name";
	private static String KEY_MANAGER_FN = "manager_first_name";
	private static String KEY_EMPLOYEE_LN = "employee_last_name";
	private static String KEY_MANAGER_LN = "manager_last_name";
	private static String KEY_EMPLOYEE_EMAIL = "employee_email";
	private static String KEY_MANAGER_EMAIL = "manager_email";
	private static String KEY_EMPLOYEE_CIVILITY = "employee_civility";
	private static String KEY_MANAGER_CIVILITY = "manager_civility";
	private static String KEY_EMPLOYEE_LANG = "employee_language";
	private static String KEY_MANAGER_LANG = "manager_language";

	private static String KEY_BRANDING = "branding";


	// All Keys by campaigns
	private static String myAccEnrolList[] = { KEY_GIN, KEY_CONNECTION_ID,
			KEY_COMM_PREFERENCES, KEY_AIRLINE, KEY_POS, KEY_REQUESTOR,
			KEY_CHANNEL };
	private static String passwordChangeList[] = { KEY_GIN, KEY_CIN,
			KEY_REQUESTOR, KEY_CHANNEL };
	private static String passwordRecoveryList[] = { KEY_GIN, KEY_REQUESTOR,
			KEY_ROUTAGE_CODE, KEY_ACTION_CODE, KEY_KOBA_ACTION_CODE,
			KEY_KOBA_LETTER_CODE, KEY_CHANNEL, KEY_EMAIL, KEY_AIN,
			KEY_DATE_MAJ, KEY_STREET_NB, KEY_COMPL_ADDRESS,
			KEY_THIRD_ROW_ADDRESS, KEY_FOURTH_ROW_ADDRESS, KEY_CIN,
			KEY_TIER_LEVEL, KEY_LANGUAGE_ISIS, KEY_LANGUAGE, KEY_PHONE_NUMBER,
			KEY_SALUTATION_CARD, KEY_SALUTATION_LETTER, KEY_SALUTATION_ENVELOP,
			KEY_PASSWORD, KEY_SEQUENCE_ID, KEY_COUNTRY_CODE, KEY_TITLE,
			KEY_FIRM };
	private static String emailChangeList[] = { KEY_GIN, KEY_CIN,
			KEY_CONNECTION_ID, KEY_REQUESTOR, KEY_FORMER_EMAIL, KEY_CHANNEL,
			KEY_NEW_EMAIL };
	private static String myAccExpirationList[] = { KEY_GIN, KEY_CONNECTION_ID,
			KEY_LAST_CONNECTION_DATE, KEY_REQUESTOR, KEY_CHANNEL };
	private static String myAccDeletionList[] = { KEY_GIN, KEY_CONNECTION_ID,
			KEY_LAST_CONNECTION_DATE, KEY_REQUESTOR, KEY_CHANNEL };
	private static String myAccDeletionExpirationList[] = { KEY_GIN, KEY_CONNECTION_ID,
			KEY_LAST_CONNECTION_DATE, KEY_REQUESTOR, KEY_CHANNEL };
	private static String lostIDList[] = { KEY_GIN, KEY_CIN, KEY_CONNECTION_ID,
			KEY_REQUESTOR };
	private static String delegationList[] = { KEY_MANAGER_FN, KEY_MANAGER_LN, KEY_MANAGER_EMAIL,
			KEY_MANAGER_CIVILITY, KEY_MANAGER_LANG, KEY_EMPLOYEE_FN, KEY_EMPLOYEE_LN, KEY_EMPLOYEE_EMAIL,
			KEY_EMPLOYEE_CIVILITY, KEY_EMPLOYEE_LANG, KEY_BRANDING };

	// Campaign codes
	private static String CAMPAIGN_MYACCOUNT_ENROL = "myacc_enrol";
	private static String CAMPAIGN_PASSWORD_CHANGE = "passwd_change";
	private static String CAMPAIGN_PASSWORD_RECOVERY = "passwd_init";
	private static String CAMPAIGN_EMAIL_CHANGE = "email_change";
	private static String CAMPAIGN_MYACCOUNT_EXPIRATION = "account_expiration";
	private static String CAMPAIGN_MYACCOUNT_DELETION = "account_deletion"; // manual deletion
	private static String CAMPAIGN_MYACCOUNT_DELETION_EXPIRATION = "acc_del_exp"; // auto deletion
	private static String CAMPAIGN_LOST_ID = "lost_id";

	private static String CAMPAIGN_INV_EMP = "INV_EMP";
	private static String CAMPAIGN_INV_TM = "INV_TM";
	private static String CAMPAIGN_ACC_EMP = "ACC_EMP";
	private static String CAMPAIGN_ACC_TM = "ACC_TM";
	private static String CAMPAIGN_REJ_EMP = "REJ_EMP";
	private static String CAMPAIGN_REJ_TM = "REJ_TM";
	private static String CAMPAIGN_DEL_EMP = "DEL_EMP";
	private static String CAMPAIGN_DEL_TM = "DEL_TM";

	private Exception exception = null;
	
	public HandleCommunication() {
	}

	public HandleCommunication(ApplicationContext context) {
		this.context = context;
	}

	/**
	 * @param gin
	 * @param password
	 * @return boolean
	 */
	public void askHandleCommPasswordRecovery(String gin, String password,
			String businessKey) throws JrafDomainException {

		LOGGER.debug("Begin askHandleCommPasswordRecovery");

		try {
			Properties myProperties = SicStringUtils.decodeBusinessKey(
					businessKey, ";", "=");

			myProperties = fillPropertiesForRecoveryProcess(gin, password, businessKey, myProperties);

			askWebServiceHandleCommunication(CAMPAIGN_PASSWORD_RECOVERY,
					myProperties);
		} catch (SOAPFaultException e) { // in case no data found ...
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e);
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Problem during askWebServiceHandleCommunication");
			}
		}
	}
	protected Properties fillPropertiesForRecoveryProcess(String gin, String password,

			String businessKey, Properties myProperties){
		
		// Si la businessKey est vide (Cas du B2C par exemple)
		// il faut quand meme remplir les champs pour le CRMPush
		if (businessKey == null
				|| "".equals(myProperties.getProperty(KEY_REQUESTOR, "").trim())) {
			myProperties.put(KEY_REQUESTOR, "unknown");
		}
		if (businessKey == null
				|| "".equals(myProperties.getProperty(KEY_ACTION_CODE, "").trim())) {
			myProperties.put(KEY_ACTION_CODE, "reinit");
		}
		if (businessKey == null
				|| "".equals(myProperties.getProperty(KEY_CHANNEL, "").trim())) {
			myProperties.put(KEY_CHANNEL, KEY_EMAIL);
		}
		// Arguments récupérés dans le RI
		if (gin != null)
			myProperties.put(KEY_GIN, gin);
		if (password != null)
			myProperties.put(KEY_PASSWORD, password);
		
		return myProperties;
	}


	/**
	 * @param gin
	 * @param connection_id
	 * @param comm_preferences
	 * @param airline
	 * @param pos
	 * @return boolean
	 */
	public void askHandleCommMyAccEnrol(String gin, String connection_id,
			String comm_preferences, String airline, String pos,
			String requestor) throws JrafDomainException {

		LOGGER.debug("Begin askHandleCommMyAccEnrol");

		try {
			Properties myProperties = new Properties();
			if (gin != null)
				myProperties.put(KEY_GIN, gin);
			if (connection_id != null)
				myProperties.put(KEY_CONNECTION_ID, connection_id);
			if (comm_preferences != null)
				myProperties.put(KEY_COMM_PREFERENCES, comm_preferences);
			if (airline != null)
				myProperties.put(KEY_AIRLINE, airline);
			if (pos != null)
				myProperties.put(KEY_POS, pos);
			if (requestor != null) {
				checkSignatureSize(MAX_SIZE, requestor);
				myProperties.put(KEY_REQUESTOR, requestor);
			}
			else
				myProperties.put(KEY_REQUESTOR, "unknown");

			askWebServiceHandleCommunication(CAMPAIGN_MYACCOUNT_ENROL,
					myProperties);
		} catch (SOAPFaultException e) { // in case no data found ...
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e);
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Problem during askWebServiceHandleCommunication");
			}
		}
	}

	/**
	 * @param gin
	 * @return boolean
	 */
	public void askHandleCommPasswordChange(String gin, String cin,
			String requestor, String channel) throws JrafDomainException {

		LOGGER.debug("Begin askHandleCommPasswordChange");

		try {
			Properties myProperties = new Properties();
			if (gin != null)
				myProperties.put(KEY_GIN, gin);
			if (cin != null)
				myProperties.put(KEY_CIN, cin);
			if (requestor != null) {
				checkSignatureSize(MAX_SIZE, requestor);
				myProperties.put(KEY_REQUESTOR, requestor);
			}
			else
				myProperties.put(KEY_REQUESTOR, "unknown");
			if (channel != null)
				myProperties.put(KEY_CHANNEL, channel);
			else
				myProperties.put(KEY_CHANNEL, "email");

			askWebServiceHandleCommunication(CAMPAIGN_PASSWORD_CHANGE,
					myProperties);
		} catch (SOAPFaultException e) { // in case no data found ...
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e);
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Problem during askWebServiceHandleCommunication");
			}
		}
	}

	/**
	 * @param gin
	 * @param connection_id
	 * @param last_connection_date
	 * @param account_status
	 * @param comm_preferences
	 * @return boolean
	 */
	public void askHandleCommMyAccDeletion(String gin, String connection_id,
			Date last_connection_date, String requestor, String channel)
					throws JrafDomainException {

		LOGGER.debug("Begin askHandleCommMyAccDeletion");

		try {
			Properties myProperties = new Properties();
			if (gin != null)
				myProperties.put(KEY_GIN, gin);
			if (connection_id != null)
				myProperties.put(KEY_CONNECTION_ID, connection_id);
			if (last_connection_date != null) {
				String slastConnection = null;
				SimpleDateFormat simpleFormat = new SimpleDateFormat("ddMMyyyy");
				slastConnection = simpleFormat.format(last_connection_date);
				myProperties.put(KEY_LAST_CONNECTION_DATE, slastConnection);
			}
			if (requestor != null) {
				checkSignatureSize(MAX_SIZE, requestor);
				myProperties.put(KEY_REQUESTOR, requestor);
			}
			else
				myProperties.put(KEY_REQUESTOR, "unknown");
			if (channel != null)
				myProperties.put(KEY_CHANNEL, channel);
			else
				myProperties.put(KEY_CHANNEL, "email");

			askWebServiceHandleCommunication(CAMPAIGN_MYACCOUNT_DELETION,
					myProperties);
		} catch (SOAPFaultException e) { // in case no data found ...
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e);
			}
		} catch (Exception e) {
			LOGGER.error("Problem during askWebServiceHandleCommunication", e);
		}
	}

	/**
	 * @param gin
	 * @param connection_id
	 * @param last_connection_date
	 * @param account_status
	 * @param comm_preferences
	 * @return boolean
	 */
	public void askHandleCommMyAccDeletionExpiration(String gin, String connection_id,
			Date last_connection_date, String requestor, String channel)
					throws JrafDomainException {

		LOGGER.debug("Begin askHandleCommMyAccDeletion");

		try {
			Properties myProperties = new Properties();
			if (gin != null)
				myProperties.put(KEY_GIN, gin);
			if (connection_id != null)
				myProperties.put(KEY_CONNECTION_ID, connection_id);
			if (last_connection_date != null) {
				String slastConnection = null;
				SimpleDateFormat simpleFormat = new SimpleDateFormat("ddMMyyyy");
				slastConnection = simpleFormat.format(last_connection_date);
				myProperties.put(KEY_LAST_CONNECTION_DATE, slastConnection);
			}
			if (requestor != null) {
				checkSignatureSize(MAX_SIZE, requestor);
				myProperties.put(KEY_REQUESTOR, requestor);
			}
			else 
				myProperties.put(KEY_REQUESTOR, "unknown");
			if (channel != null)
				myProperties.put(KEY_CHANNEL, channel);
			else
				myProperties.put(KEY_CHANNEL, "email");

			askWebServiceHandleCommunication(CAMPAIGN_MYACCOUNT_DELETION_EXPIRATION,
					myProperties);
		} catch (SOAPFaultException e) { // in case no data found ...
			LOGGER.error(e);
		} catch (Exception e) {
			LOGGER.error("Problem during askWebServiceHandleCommunication",e);
		}
	}

	/**
	 * @param gin
	 * @param connection_id
	 * @param last_connection_date
	 * @return boolean
	 */
	public void askHandleCommMyAccExpiration(String gin, String connection_id, Date last_connection_date, String requestor, String channel) throws JrafDomainException {

		LOGGER.debug("Begin askHandleCommMyAccExpiration");

		Properties myProperties = new Properties();

		if (gin != null) {
			myProperties.put(KEY_GIN, gin);
		}

		if (connection_id != null) {
			myProperties.put(KEY_CONNECTION_ID, connection_id);
		}

		if (last_connection_date != null) {
			String slastConnection = null;
			SimpleDateFormat simpleFormat = new SimpleDateFormat("ddMMyyyy");
			slastConnection = simpleFormat.format(last_connection_date);
			myProperties.put(KEY_LAST_CONNECTION_DATE, slastConnection);
		}

		if (requestor != null) {
			checkSignatureSize(MAX_SIZE, requestor);
			myProperties.put(KEY_REQUESTOR, requestor);
		} else {
			myProperties.put(KEY_REQUESTOR, "unknown");
		}

		if (channel != null) {
			myProperties.put(KEY_CHANNEL, channel);
		} else {
			myProperties.put(KEY_CHANNEL, "email");
		}

		askWebServiceHandleCommunication(CAMPAIGN_MYACCOUNT_EXPIRATION, myProperties);

		if(exception!=null) {
			throw new JrafDomainException(exception);
		}

	}

	/**
	 * @param gin
	 * @param connection_id
	 * @param former_email
	 * @param new_email
	 * @return boolean
	 */
	public void askHandleCommEmailChange(String gin, String cin,
			String connection_id, String former_email, String new_email,
			String requestor, String channel) throws JrafDomainException {

		LOGGER.debug("Begin askHandleCommEmailChange");

		try {
			Properties myProperties = new Properties();
			if (gin != null)
				myProperties.put(KEY_GIN, gin);
			if (cin != null && !cin.equals(""))
				myProperties.put(KEY_CIN, cin);
			if (connection_id != null)
				myProperties.put(KEY_CONNECTION_ID, connection_id);
			if (former_email != null)
				myProperties.put(KEY_FORMER_EMAIL, former_email);
			if (new_email != null)
				myProperties.put(KEY_NEW_EMAIL, new_email);
			if (requestor != null) {
				checkSignatureSize(MAX_SIZE, requestor);
				myProperties.put(KEY_REQUESTOR, requestor);
			}
			else
				myProperties.put(KEY_REQUESTOR, "unknown");
			if (channel != null)
				myProperties.put(KEY_CHANNEL, channel);
			else
				myProperties.put(KEY_CHANNEL, "email");

			askWebServiceHandleCommunication(CAMPAIGN_EMAIL_CHANGE,
					myProperties);
		} catch (SOAPFaultException e) { // in case no data found ...
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e);
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Problem during askWebServiceHandleCommunication");
			}
		}
	}

	/**
	 * @param gin
	 * @param connection_id
	 * @param former_email
	 * @return boolean
	 */
	public void askHandleCommLostID(String gin, String cin,
			String connection_id, String requestor) throws JrafDomainException {

		LOGGER.debug("Begin askHandleCommLostID");

		try {
			Properties myProperties = new Properties();
			// Arguments récupérés dans le RI
			if (gin != null)
				myProperties.put(KEY_GIN, gin);
			if (cin != null)
				myProperties.put(KEY_CIN, cin);

			if (connection_id != null)
				myProperties.put(KEY_CONNECTION_ID, connection_id);
			else {
				if (cin != null) {
					myProperties.put(KEY_CONNECTION_ID,
							SicStringUtils.trimLeft(cin.trim(), "0"));
				}
			}

			if (requestor != null) {
				checkSignatureSize(MAX_SIZE, requestor);
				myProperties.put(KEY_REQUESTOR, requestor);
			}
			else
				myProperties.put(KEY_REQUESTOR, "unknown");

			askWebServiceHandleCommunication(CAMPAIGN_LOST_ID, myProperties);
		} catch (SOAPFaultException e) { // in case no data found ...
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e);
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Problem during askWebServiceHandleCommunication");
			}
		}
	}

	public void askHandleCommDelegationAction(HashMap delegationInfos, String campaignId, String email_delegator, String email_delegate, String branding) {

		LOGGER.debug("Begin askHandleCommDelegationAction");

		IndividuDTO delegator = (IndividuDTO) delegationInfos.get("delegator");
		IndividuDTO delegate = (IndividuDTO) delegationInfos.get("delegate");

		try {

			Properties myProperties = new Properties();

			// Delegator firstname
			if (delegator.getPrenomSC() != null) {
				myProperties.put(KEY_EMPLOYEE_FN, delegator.getPrenomSC());
			} else
				if (delegator.getPrenom() != null) {
					myProperties.put(KEY_EMPLOYEE_FN, delegator.getPrenom());
				}

			// Delegate firstname
			if (delegate.getPrenomSC() != null) {
				myProperties.put(KEY_MANAGER_FN, delegate.getPrenomSC());
			} else 
				if (delegate.getPrenom() != null) {
					myProperties.put(KEY_MANAGER_FN, delegate.getPrenom());
				}

			// Delegator lastname
			if (delegator.getNomSC() != null) {
				myProperties.put(KEY_EMPLOYEE_LN, delegator.getNomSC());
			} else 
				if (delegator.getNom() != null) {
					myProperties.put(KEY_EMPLOYEE_LN, delegator.getNom());
				}

			// Delegate lastname
			if (delegate.getNomSC() != null) {
				myProperties.put(KEY_MANAGER_LN, delegate.getNomSC());
			} else
				if (delegate.getNom() != null) {
					myProperties.put(KEY_MANAGER_LN, delegate.getNom());
				}

			// Delegator email
			if (delegator.getEmaildto() != null) {
				// On obtient un NUllPointeurException, il faut faire le test :
				if (email_delegator != null) {				
					myProperties.put(KEY_EMPLOYEE_EMAIL, email_delegator);
				} else {
					// Si pas l'email, on en trouve une
					for (EmailDTO ed : delegator.getEmaildto()) {
						if (!"".equals(ed.getEmail())) {
							myProperties.put(KEY_EMPLOYEE_EMAIL, ed.getEmail());
							break;
						}
					}
				}
			}

			// Delegate email
			if (delegate.getEmaildto() != null) {
				myProperties.put(KEY_MANAGER_EMAIL, email_delegate);
			}

			// Delegator civility
			if (delegator.getCivilite() != null) {
				myProperties.put(KEY_EMPLOYEE_CIVILITY, delegator.getCivilite());
			}

			// Delegate civility
			if (delegate.getCivilite() != null) {
				myProperties.put(KEY_MANAGER_CIVILITY, delegate.getCivilite());
			}

			// Delegator code langue
			if (delegator.getProfilsdto() != null) {
				myProperties.put(KEY_EMPLOYEE_LANG, delegator.getProfilsdto().getScode_langue());
			}

			// Delegate code langue
			if (delegate.getProfilsdto() != null) {
				myProperties.put(KEY_MANAGER_LANG, delegate.getProfilsdto().getScode_langue());
			}

			if(branding == null || branding.isEmpty()) {
				branding = "KL";
			}
			myProperties.put(KEY_BRANDING, branding);

			askWebServiceHandleCommunication(campaignId,myProperties);

		} catch (SOAPFaultException e) { // in case no data found ...
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(e);
			}
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("Problem during askHandleCommDelegationAction", e);
			}
		}

	}

	/**
	 * @param campaignId
	 * @param paramsList
	 * @return boolean
	 */
	private void askWebServiceHandleCommunication(String campaignId, Properties paramsList) {

		LOGGER.debug("Begin askWebServiceHandleCommunication");

		try {

			HandleCommunicationRequest mdRequest = new HandleCommunicationRequest();

			// REQUEST STATIC PART
			mdRequest.setClientId(GLOBAL_ClientID);
			mdRequest.setCampaignId(campaignId);
			mdRequest.setIsRealTime(GLOBAL_IsRealTime);
			mdRequest.setCampaignScheduleTime(GLOBAL_CampaignScheduleTime);
			mdRequest.setCampaignValidityDate(GLOBAL_CampaignValidityDate);
			mdRequest.setCheckContract(GLOBAL_CheckContract);
			mdRequest.setContractVersion(GLOBAL_ContractVersion);

			// Display parameters
			Enumeration en = paramsList.propertyNames();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("handleComm Campagne : " + campaignId);

				while (en.hasMoreElements()) {
					String key = (String) en.nextElement();
					LOGGER.debug("handleComm : " + key + " -- "
							+ paramsList.getProperty(key));
				}

			}

			// REQUEST DYNAMIC PART
			// Create the parameters list
			String[] myTagList = getAllTagsByCampaign().get(campaignId);
			for (int i = 0; i < myTagList.length; i++) {
				if (!"".equals(paramsList.getProperty(myTagList[i], "").trim())) {
					CampaignParameter param = new CampaignParameter();
					param.setKey(myTagList[i].trim());
					param.setValue(paramsList.getProperty(myTagList[i], " ")
							.trim());
					mdRequest.getCampaignParameters().add(param);
				}
			}

			// Publish Event
			doHandleCommunication(mdRequest);

		} catch (SOAPFaultException e) { // in case no data found ...
			LOGGER.error(e);
		} catch (Exception e) {
			LOGGER.error("Problem during askWebServiceHandleCommunication",e);
		}

		LOGGER.debug("End askWebServiceHandleCommunication");
	}

	public void doHandleCommunication(HandleCommunicationRequest handleCommunicationRequest) {

		HandleCommunicationV1 handleCommunication = (HandleCommunicationV1) context.getBean("consumerHandleCommunication");

		LOGGER.debug("appcontext.getBean");

		if (handleCommunication == null) {

			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("The HandleCommunicationBean is null, verify the problem.");
			}

		} else {

			try {

				LOGGER.debug("doHandleCommunication 1");

				handleCommunication.doHandleCommunication(handleCommunicationRequest);

			} catch (Exception e) {

				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("Pb to publish msg in the Queue.", e);
				}

				exception = e;
			}
		}

	}

	private HashMap<String, String[]> getAllTagsByCampaign() {
		if (allTagsByCampaign == null) {
			allTagsByCampaign = new HashMap<String, String[]>();
			allTagsByCampaign.put(CAMPAIGN_MYACCOUNT_ENROL, myAccEnrolList);
			allTagsByCampaign.put(CAMPAIGN_PASSWORD_CHANGE, passwordChangeList);
			allTagsByCampaign.put(CAMPAIGN_PASSWORD_RECOVERY,
					passwordRecoveryList);
			allTagsByCampaign.put(CAMPAIGN_EMAIL_CHANGE, emailChangeList);
			allTagsByCampaign.put(CAMPAIGN_MYACCOUNT_EXPIRATION,
					myAccExpirationList);
			allTagsByCampaign.put(CAMPAIGN_MYACCOUNT_DELETION,
					myAccDeletionList);
			allTagsByCampaign.put(CAMPAIGN_MYACCOUNT_DELETION_EXPIRATION,
					myAccDeletionExpirationList);
			allTagsByCampaign.put(CAMPAIGN_LOST_ID, lostIDList);
			allTagsByCampaign.put(CAMPAIGN_INV_EMP,delegationList);
			allTagsByCampaign.put(CAMPAIGN_INV_TM,delegationList);
			allTagsByCampaign.put(CAMPAIGN_ACC_EMP,delegationList);
			allTagsByCampaign.put(CAMPAIGN_ACC_TM,delegationList);
			allTagsByCampaign.put(CAMPAIGN_DEL_EMP,delegationList);
			allTagsByCampaign.put(CAMPAIGN_DEL_TM,delegationList);
			allTagsByCampaign.put(CAMPAIGN_REJ_EMP,delegationList);
			allTagsByCampaign.put(CAMPAIGN_REJ_TM,delegationList);

		}
		return allTagsByCampaign;
	}
	
	private void checkSignatureSize(int maxSize, String requestor) {
		if (StringUtils.isEmpty(requestor)) {
			return;
		}
		
		if (requestor.length() > maxSize) {
			requestor = requestor.substring(0, maxSize);
		}
	}
}
