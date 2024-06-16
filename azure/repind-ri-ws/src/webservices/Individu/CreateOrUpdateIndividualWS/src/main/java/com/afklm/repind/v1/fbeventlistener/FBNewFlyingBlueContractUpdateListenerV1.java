package com.afklm.repind.v1.fbeventlistener;

import com.afklm.repind.v1.fbeventlistener.helpers.FlyingBlueUpdateEventHelper;
import com.afklm.soa.stubs.w001815.v2.FBNewFlyingBlueContractUpdateNotificationV2;
import com.afklm.soa.stubs.w001815.v2.xsd1.FBNContractUpdateEvent;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.YesNoFlagEnum;
import com.airfrance.repind.dto.environnement.VariablesDTO;
import com.airfrance.repind.dto.individu.WarningDTO;
import com.airfrance.repind.dto.individu.WarningResponseDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractRequestDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractResponseDTO;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.service.ws.internal.helpers.ActionManager;
import com.airfrance.repind.util.ReadSoaHeaderHelper;
import com.airfrance.repind.util.SicStringUtils;
import com.sun.xml.ws.api.message.Header;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.soap.SOAPException;
import javax.xml.ws.WebServiceContext;
import java.util.List;
import java.util.Properties;

@WebService(endpointInterface="com.afklm.soa.stubs.w001815.v2.FBNewFlyingBlueContractUpdateNotificationV2", targetNamespace = "http://www.af-klm.com/services/marketing/passenger_FBNewFlyingBlueContractUpdateNotification-v2/wsdl", serviceName = "FBNewFlyingBlueContractUpdateNotificationService-v2", portName = "FBNewFlyingBlueContractUpdateNotification-v2-soap11jms")
@Slf4j
public class FBNewFlyingBlueContractUpdateListenerV1 implements FBNewFlyingBlueContractUpdateNotificationV2 {

	@Autowired
	private FlyingBlueUpdateEventHelper flyingBlueUpdateEventHelper;
	@Autowired
	private ActionManager actionManager;
	@Resource
	private WebServiceContext context;
	@Resource
	private Properties headerProperties;

	@Autowired
	private CommunicationPreferencesDS communicationPreferencesDS;

	@Autowired
	private DelegationDataDS delegationDataDS;
	
	@Autowired
	private PreferenceDS preferenceDS;

	@Autowired
	private VariablesDS variablesDS=null;

	@Autowired
	private RoleDS roleDS;

	private static String FBRecognitionActivated  = "FBRECOGNITION_ACTIVATED";
	private static String UltimateProcessActivated  = "ULTIMATE_ACTIVATED";

	@Override
//	@Transactional(value="myTxManager")
	public void sendUpdatedContract(FBNContractUpdateEvent updatedContractDetails) {
		log.info("[FBN] - FBNewFlyingBlueContractUpdateListenerV1 start...");
		
		boolean isFBRecognitionActivate = true;
		String updatedCin = "";
		String updatedGin = "";
		
		String newStatus = "";
		boolean newMemberTypeUltimate = false;
						
		VariablesDTO var;
		try {
			var = variablesDS.getByEnvKey(FBRecognitionActivated);
			if (var != null) {	// By default, service si open... It is closed only for False value
				if (var.getEnvValue().equalsIgnoreCase("false")) {
					isFBRecognitionActivate = false;
				}
			}
		} catch (JrafDomainException e1) {
			log.error("FBNewFlyingBlueContractUpdateListenerV1 failed for FBRecognitionActivated : ",e1);
		}
				
//		isFBRecognitionActivate = false;

		// If FBRecognition is not activated, that mean that ISI is dead and we are now on FBN 
		if (!isFBRecognitionActivate) {
		
			if (updatedContractDetails != null) {
				// On recupere les GIN et les CIN
				if (updatedContractDetails.getIndividualBlock() != null) {
					updatedCin = SicStringUtils.addingZeroToTheLeft(Long.toString(updatedContractDetails.getIndividualBlock().getCin()), 12);
					updatedGin = SicStringUtils.addingZeroToTheLeft(Long.toString(updatedContractDetails.getIndividualBlock().getGin()), 12);
				}
			}
			
			Header header = ReadSoaHeaderHelper.getHeaderFromContext(context, headerProperties.getProperty("EVENT-HEADER-NAME"));
			try {
				String site = ReadSoaHeaderHelper.getHeaderChildren(header, headerProperties.getProperty("EVENT-CHILDREN-CONSUMERLOCATION"));
				//We set signature to userID, and if the userID is unkown, we set it to the consumerID
				String userID = ReadSoaHeaderHelper.getHeaderChildren(header, headerProperties.getProperty("EVENT-CHILDREN-USERID"));
				String signature;
				if (SicStringUtils.isMatricule(userID)) {
					signature = "FBN/" + userID;
				} else {
					signature = "FBN/" + ReadSoaHeaderHelper.getHeaderChildren(header, headerProperties.getProperty("EVENT-CHILDREN-CONSUMERID"));
				}
				
				//REPIND-1747: Downgrade Ultimate

				// REPIND-2227 : Disable this functionality
				// Ulti Downgrade and Upgrade is now triggered by CAST
				String ultiActivated = YesNoFlagEnum.TRUE.toString(); // true
				VariablesDTO ultiStatus = variablesDS.getByEnvKey(UltimateProcessActivated);

				// Check value from DB
				if (ultiStatus != null) {
					ultiActivated = ultiStatus.getEnvValue();
				}

				if (YesNoFlagEnum.TRUE.toString().equalsIgnoreCase(ultiActivated)) {
					if (isUltimateDowngrade(updatedContractDetails)) {

						//Delete Ultimate Consent (Compref U - S - UL_PS)
						communicationPreferencesDS.deleteUltimateCompref(updatedGin);

						//Delete Ultimate Preferences (Preferences - UCO, UFD, UFB, ULS, ULO, UMU, UOB, UPM, UST, UTS, UTF)
						preferenceDS.deleteUltimatePreferences(updatedGin);

						//Delete Delegation data for SGIN_DELEGATE
						delegationDataDS.deleteDelegateLink(updatedGin);

					} else if (isUltimateOrHippoUpgrade(updatedContractDetails)) {

						//Create Ultimate Consent (Compref U - S - UL_PS)
						communicationPreferencesDS.createUltimateCompref(updatedGin);
					}
				}

				CreateUpdateRoleContractRequestDTO request = flyingBlueUpdateEventHelper.transformFBNewContractUpdateEventToRequest(updatedContractDetails, site, signature);

				// check if combination gin and cin already exists
				boolean ifRoleContratsExist = roleDS.existFbContractWithGindAndNumeroContract(updatedGin, updatedCin);
				if(!ifRoleContratsExist) {
					//re-synchronized gin and cin
					log.info("creation of fb contract for re-synch with hachiko");
					request.setActionCode("C");
				}else{
					// default mode = update
					request.setActionCode("U");
				}
				CreateUpdateRoleContractResponseDTO flyingBlueResponse = actionManager.processByActionCode(request, isFBRecognitionActivate);
				WarningResponseDTO warningResponseDTO = flyingBlueResponse.getWarningResponse();
	
				// NPE in live env since 29/05/2018
				if (warningResponseDTO != null) {
					List<WarningDTO> warnings = warningResponseDTO.getWarning();
					for (WarningDTO warning : warnings) { 
						log.warn("{} : {}",warning.getWarningCode(),warning.getWarningDetails());
					}
				}
				if(updatedContractDetails != null){
					log.info("[FBN EVENT] FBN event successfully processed (CIN = {} )",updatedContractDetails.getIndividualBlock().getCin());

				}
				
			} catch (JrafDomainException | JrafApplicativeException | SOAPException e) {
				if (updatedContractDetails != null){
					log.error("[FBN EVENT] Cannot process FBN event (CIN = "+updatedContractDetails.getIndividualBlock().getCin()+"), trackingMessageHeader is missing",e);
				}
			}
		} else {
			log.info("[FBN EVENT] FBRecognitionActivated is true");
		}
	}

	public VariablesDS getVariablesDS() {
		return variablesDS;
	}

	public void setVariablesDS(VariablesDS variablesDS) {
		this.variablesDS = variablesDS;
	}
	
	//REPIND-1747: Check if the FBN Event contains an ULTIMATE DOWNGRADE attribut
	private boolean isUltimateDowngrade(FBNContractUpdateEvent updatedContractDetails) {
		if (updatedContractDetails.getAttributesBlock() != null && updatedContractDetails.getAttributesBlock().getAttributeExtensions() != null) {
			if (updatedContractDetails.getAttributesBlock().getAttributeExtensions().stream()
					.filter(it -> "ULTIMATE".equalsIgnoreCase(it.getKey()) && "DOWNGRADE".equalsIgnoreCase(it.getValue())).count() > 0) {
				return true;
			}
		}
		return false;
	}
	
	//REPIND-1747: Check if the FBN Event contains an ULTIMATE UPGRADE attribut
	private boolean isUltimateOrHippoUpgrade(FBNContractUpdateEvent updatedContractDetails) {
		//Check for attributeExtension = ULTIMATE UPGRADE
		if (updatedContractDetails.getAttributesBlock() != null && updatedContractDetails.getAttributesBlock().getAttributeExtensions() != null) {
			if (updatedContractDetails.getAttributesBlock().getAttributeExtensions().stream()
					.filter(it -> "ULTIMATE".equalsIgnoreCase(it.getKey()) && "UPGRADE".equalsIgnoreCase(it.getValue())).count() > 0) {
				return true;
			}
		}
		
		//Check for fbPropertiesBlock = CLUB HIPPOCAMPE
		if (updatedContractDetails.getFbPropertiesBlock() != null && updatedContractDetails.getFbPropertiesBlock().getProperty() != null) {
			if (updatedContractDetails.getFbPropertiesBlock().getProperty().stream()
					.filter(it -> "CLUB".equalsIgnoreCase(it.getPropertyType()) && "HIPPOCAMPE".equalsIgnoreCase(it.getPropertyCode())).count() > 0) {
				return true;
			}
		}
		return false;
	}
}
