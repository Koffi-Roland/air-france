package com.airfrance.jraf.batch.event.individu;


import com.afklm.soa.stubs.w001539.v1.NotifyEventIndividualV1;
import com.afklm.soa.stubs.w001539.v1.data.Block;
import com.afklm.soa.stubs.w001539.v1.data.IndividualInformationData;
import com.afklm.soa.stubs.w001539.v1.data.NotifyEventIndividualMsg;
import com.afklm.soa.stubs.w001539.v1.individual.DelegationDataBlock;
import com.afklm.soa.stubs.w001539.v1.individual.PreferencesBlock;
import com.afklm.soa.stubs.w001992.v1_0_1.data.ChangedDataBlock;
import com.afklm.soa.stubs.w001992.v1_0_1.data.ChangedDatasBlock;
import com.afklm.soa.stubs.w001992.v1_0_1.individual.IndividualBlock;
import com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.IndividualInformationsV3;
import com.airfrance.jraf.batch.common.BlockDTO;
import com.airfrance.jraf.batch.common.NotifyAdhocIndividualTransform;
import com.airfrance.jraf.batch.common.NotifyIndividualTransform;
import com.airfrance.jraf.batch.common.type.BatchSicUpdateNotificationChangedBlockNameEnum;
import com.airfrance.jraf.batch.common.type.ScopesToProvideEnum;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.consent.ConsentDTO;
import com.airfrance.repind.dto.consent.ConsentDataDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.PrefilledNumbersDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.entity.tracking.TriggerChangeIndividus;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.consent.internal.ConsentDS;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.PrefilledNumbersDS;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.service.tracking.internal.TriggerChangeIndividusDS;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXParseException;

import javax.validation.constraints.NotNull;
import javax.xml.bind.MarshalException;
import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

@DependsOn("notifyEventIndividual-v1")
@Component
public class SendNotifyEventIndividualTask implements Runnable {

	// REPIND-795
	final static String TPC = "TPC";
	final static String preferredAirport = "preferredAirport";
	final static String DELETED_TYPE = "D";

	final static Log log = LogFactory.getLog(SendNotifyEventIndividualTask.class);

	@Autowired
	private IndividuDS individuDS = null;
	@Autowired
	private PostalAddressDS postalAddressDS;
	@Autowired
	private PrefilledNumbersDS prefilledNumbersDS;
	@Autowired
	private AccountDataDS accountDataDS;
	@Autowired
	private TelecomDS telecomDS;
	@Autowired
	private DelegationDataDS delegationDataDS;
	@Autowired
	private ExternalIdentifierDS externalIdentifierDS;
	@Autowired
	private CommunicationPreferencesDS communicationPreferencesDS;
	@Autowired
	private PreferenceDS preferenceDS;								// REPIND-795 : Add Preference Block
	@Autowired
	private RoleDS roleDS;
	@Autowired
	private EmailDS emailDS;
	@Autowired
	private BusinessRoleDS businessRoleDS;
	@Autowired
	private ConsentDS consentDS;
	@Autowired
	private TriggerChangeIndividusDS triggerChangeIndividusDS;

	final static String CONSENT  = "CONSENT";

	private LinkedBlockingQueue<String> queue;

	private LinkedHashMap<String, ChangeData> ginAssociatedToBlockDTOListHm;

	private String taskName;

	final static String INDIVIDUS  = "INDIVIDUS";
	private final static String ERROR_MSG = " Error during sending notifyAdhocEventIndividual for gin = ";

	static volatile List<Long> ids = new ArrayList<>();

	@Autowired
	private ApplicationContext context;

	@Autowired
	@Qualifier("notifyEventIndividual-v1")
	private NotifyEventIndividualV1 notifyEventIndividualV1;

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setQueue(LinkedBlockingQueue<String> queue) {
		this.queue = queue;
	}

	public void setGinLinkedToTriggerChangeIndIdListHm(LinkedHashMap<String, ChangeData> ginAssociatedToBlockDTOListHm) {
		this.ginAssociatedToBlockDTOListHm = ginAssociatedToBlockDTOListHm;
	}

	@Override
	public void run() {
		String taskId = taskName;
		BatchIndividualUpdateNotificationLogBatch("Start "+taskId, null);

		List<Long> blockIdToUpdate = new ArrayList<Long>();
		while (queue.size()>0) {
			String gin = null;
			blockIdToUpdate = new ArrayList<Long>();
			try {
				gin = queue.take();

				for (BlockDTO block : ginAssociatedToBlockDTOListHm.get(gin).getBlockDTOList()) {
					synchronized (ids) {
						if(!ids.contains(block.getId())) {
							blockIdToUpdate.add(block.getId());
							ids.add(block.getId());
						}
						else {
							BatchIndividualUpdateNotificationLogBatch("Id: " + block.getId() + " already used", null);
						}
					}
				}

				sendNotifyEventForIndividual(gin, ginAssociatedToBlockDTOListHm.get(gin), taskId);

				sendNotifyAdhocEventForIndividual(gin, ginAssociatedToBlockDTOListHm.get(gin), taskId);

			} catch (InterruptedException|JrafDomainException e) {
				BatchIndividualUpdateNotification.setHasError(true);
				BatchIndividualUpdateNotificationLogBatch(taskId + "--> " + BatchIndividualUpdateNotification.ERROR + " Error during sendNotifyEventForIndividual for gin = " + gin + " : ", e);
			}
			finally {
				BatchIndividualUpdateNotification.addIdToUpdate(blockIdToUpdate);
			}
		}
		BatchIndividualUpdateNotificationLogBatch("End " + taskId, null);
	}

	// Is this Individual Purged or Deleted
	public boolean isPurgedDeletedForgotten(IndividuDTO individuDTO) {

		return (individuDTO != null && individuDTO.getStatutIndividu() != null && ("X".equals(individuDTO.getStatutIndividu()) || "F".equals(individuDTO.getStatutIndividu())));
	}

	// Individual is Purged or not ?
	public boolean isPurged(IndividuDTO individuDTO, List<BlockDTO> blockDTOList) {

		if (individuDTO == null) {
			// Blocks info
			for (BlockDTO block : blockDTOList) {
				if (block != null && INDIVIDUS.equals(block.getName()) && "D".equals(block.getNotificationType())) {
					return true;
//            		break;
				}
			}
		}

		return false;
	}

	// REPIND-1526 : Individual is Hidden or Not ?
	public boolean isHidden(IndividuDTO individuDTO) {
		return (individuDTO != null && "H".equals(individuDTO.getType()));
	}

	/**
	 * sendIndividualEvent : Envoi de l'Event NotifyIndividual
	 * @param gin
	 */
	public void sendNotifyEventForIndividual(String gin, ChangeData changeData, String taskName) throws JrafDomainException
	{
		NotifyEventIndividualMsg neiMsg = new NotifyEventIndividualMsg();

//        com.afklm.soa.stubs.w001992.v1.data.NotifyEventIndividualMsg naeiMsg = new com.afklm.soa.stubs.w001992.v1.data.NotifyEventIndividualMsg();

		// REPIND-1384 : We have to get data from PURGE / DELETED
		IndividuDTO individuDTO = individuDS.getAllByGin(gin);

		boolean isPurgedDeletedForgotten = isPurgedDeletedForgotten(individuDTO);
		boolean isPurged = isPurged(individuDTO, changeData.getBlockDTOList());

		IndividualInformationData indInfoData = null;

		try{
			BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + "START getIndividualInformationData for GIN : " + gin, null);
			if (individuDTO == null) {
//        		if (isPurged) {
//        			BatchSicUpdateNotificationLogBatch(taskName + "--> " + "INFO getIndividualInformationData said GIN have been physically DELETED : " + gin, null);
//        		} else {
				BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.WARN + " Warn during getIndividualInformationData for gin = " + gin + " : NOT FOUND", null);
//        		}
			} else {

//        		if (isPurgedDeletedForgotten) {
//        			BatchSicUpdateNotificationLogBatch(taskName + "--> " + "INFO getIndividualInformationData said GIN have been logically DELETED or FORGOTTEN : " + gin, null);
//        		}
				indInfoData = getIndividualInformationData(gin, individuDTO, changeData);
			}
			BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + "END getIndividualInformationData for GIN : " + gin, null);

		} catch (Exception e) {
			BatchIndividualUpdateNotification.setHasError(true);
			BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.ERROR + " Error during getIndividualInformationData for gin = " + gin + " : ", e);
		}

		// On a des infos ou alors l individu est Purgé
		if(indInfoData != null || isPurged)
		{

			if (!isPurged) {
				if (!isPurgedDeletedForgotten) {
					neiMsg.setIndividualInformationData(indInfoData);
				}
//        		if(indInfoData.getIndividualBlock() != null && indInfoData.getIndividualBlock().getIndividualInformations() != null) {
//        			BatchSicUpdateNotification.LogBatchOutput(taskName + "--> Fusion: "+indInfoData.getIndividualBlock().getIndividualInformations().getIdentifier() + " -> "+indInfoData.getIndividualBlock().getIndividualInformations().getGinFusion());
//        		}
			}

			// if(individuDTO != null)	// Why test individuDTO that do not appear in following treatment
			if(changeData.getBlockDTOList() != null)
			{
				// Blocks info
				for (BlockDTO block : changeData.getBlockDTOList()) {

					Block currentIb = BlockDTO2BoForIndividualEvent(block);
					//REPIND-3034 : Add role contracts with previous infos when contract is deleted
					if (changeData.getContractData() != null) {
						mapContractDeletionNumberInBlock(currentIb, changeData.getContractData());
					}
					if (!isPurgedDeletedForgotten && !isPurged) {
						neiMsg.getChangedBlocks().add(currentIb);
					}

					// REPIND-795 : Add Changed Block for ALL
					// com.afklm.soa.stubs.w001992.v1.data.Block currentAIb = BlockDTO2BoForIndividualAdhocEvent(block);
					// naeiMsg.getChangedBlocks().add(currentAIb);
				}
			}

			if (!isPurged) {
				// We construct the Message for NEI and NAEI
				if (!isPurgedDeletedForgotten) {
					neiMsg.setIndividualInformationData(indInfoData);
				}
			}
			// naeiMsg.setIndividualInformationData(NotifyAdhocIndividualTransform.transformIndiInfoDataNeiV1ToNaeiV2(indInfoData, blockDTOList, individuDTO));
			// getChangedDatasBlock(gin, naeiMsg.getChangedBlocks());

			// REPIND-1314 : If Individual is UCCR - We add a ChangedBlock UCCR on NEI
			if (individuDTO != null && individuDTO.getBusinessRoleDTO() != null) {
				for (BusinessRoleDTO brd : individuDTO.getBusinessRoleDTO()) {
					if (brd != null && brd.getType() != null && "U".equalsIgnoreCase(brd.getType())) {
						Block uccrBlock = new Block();
						uccrBlock.setIdentifier(brd.getNumeroContrat().toString());	// Identifiant unique de la table en base de données
						uccrBlock.setName("ROLE_UCCR");						// Ou alors numero du contrat UCCR ?
						uccrBlock.setNotificationType("U");
						if (!isPurgedDeletedForgotten) {
							neiMsg.getChangedBlocks().add(uccrBlock);
						}
						break;													// On ne met qu un seul block
					}
				}
			}

			// REPIND-1483: If Individual has Abo Contract (RP) - We add a ChangedBlock RP on NEI
			// Need to adapt this case
	        /*if (individuDTO != null && individuDTO.getRolecontratsdto() != null) {
	        	for (RoleContratsDTO rc : individuDTO.getRolecontratsdto()) {
	        		if (rc != null && rc.getTypeContrat() != null && "RP".equalsIgnoreCase(rc.getTypeContrat())) {
	        			Block rpBlock = new Block();
	        			rpBlock.setIdentifier(rc.getSrin().toString());
	        			rpBlock.setName("ROLE_CONTRATS");
	        			rpBlock.setNotificationType("RP");
		                if (!isPurgedDeletedForgotten) {
		                	neiMsg.getChangedBlocks().add(rpBlock);
		                }
	        			break;
	        		}
	        	}
	        }*/

			if (!isPurgedDeletedForgotten && !isPurged) {
				// Send NEI event
				try {
					BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + "START Send NEI event for GIN : " + gin, null);
					notifyEventIndividualV1.notifyEventIndividual(neiMsg);
					BatchIndividualUpdateNotification.incrementNbNEIEventSent();
					BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + "END Send NEI event for GIN : " + gin, null);

				} catch (Exception e) {
					BatchIndividualUpdateNotification.setHasError(true);
					BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.ERROR + " Error during sending notifyEventIndividual for gin = " + gin, e);
				}
			}
		}
	}

	/**
	 * sendIndividualEvent : Envoi de l'Event Adhoc NotifyIndividual
	 * @param gin
	 */
	public void sendNotifyAdhocEventForIndividual(String gin, ChangeData changeData, String taskName) throws JrafDomainException
	{
//        NotifyEventIndividualMsg neiMsg = new NotifyEventIndividualMsg();

		com.afklm.soa.stubs.w001992.v1_0_1.data.NotifyEventIndividualMsg naeiMsg = new com.afklm.soa.stubs.w001992.v1_0_1.data.NotifyEventIndividualMsg();

		// REPIND-1384 : We have to get data from PURGE / DELETED
		IndividuDTO individuDTO = individuDS.getAllByGin(gin);

//    	if (gin.equals("400621178936")) {
//    		String Test = "Test";
//    	}

		boolean isPurgedDeletedForgotten = isPurgedDeletedForgotten(individuDTO);
		boolean isPurged = isPurged(individuDTO, changeData.getBlockDTOList());
		boolean isHidden = isHidden(individuDTO);				// REPIND-1526 : We must send H on NAEI for DWH

		com.afklm.soa.stubs.w001992.v1_0_1.data.IndividualInformationData indInfoData = null;
		IndividualInformationData indInfoDataFusion = null;

		try {
			// BatchSicUpdateNotificationLogBatch(taskName + "--> " + "START getIndividualInformationData for GIN : " + gin, null);
			if (individuDTO == null) {
				if (isPurged) {
					BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + "INFO getIndividualInformationData said GIN have been physically DELETED : " + gin, null);
					indInfoData = new com.afklm.soa.stubs.w001992.v1_0_1.data.IndividualInformationData();
					IndividualBlock individualBlock = new IndividualBlock();
					IndividualInformationsV3 individualInformations = new IndividualInformationsV3();

					individualInformations.setIdentifier(gin);
					individualBlock.setIndividualInformations(individualInformations);
					indInfoData.setIndividualBlock(individualBlock);
				} else {
					BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.WARN + " Warn during getIndividualInformationData for gin = " + gin + " : NOT FOUND", null);
				}
			} else {

				if (isPurgedDeletedForgotten) {
					BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + "INFO getIndividualInformationData said GIN have been logically DELETED or FORGOTTEN : " + gin, null);
				}
				if (isHidden) {
					BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + "INFO getIndividualInformationData said GIN have been HIDDEN : " + gin, null);
				}
				indInfoData = getAdhocIndividualInformationData(gin, individuDTO, changeData.getBlockDTOList());
				indInfoDataFusion = getIndividualInformationData(gin, individuDTO, changeData);

			}
			// BatchSicUpdateNotificationLogBatch(taskName + "--> " + "END getIndividualInformationData for GIN : " + gin, null);

		} catch (Exception e) {
			BatchIndividualUpdateNotification.setHasError(true);
			BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.ERROR + " Error during getIndividualInformationData for gin = " + gin + " : ", e);
		}

		// On a des infos ou alors l individu est Purgé
		if(indInfoData != null || isPurged)
		{
			if (!isPurged) {
				if(indInfoData.getIndividualBlock() != null && indInfoData.getIndividualBlock().getIndividualInformations() != null) {
					BatchIndividualUpdateNotification.LogBatchOutput(taskName + "--> Fusion: "+indInfoData.getIndividualBlock().getIndividualInformations().getIdentifier() + " -> "+indInfoDataFusion.getIndividualBlock().getIndividualInformations().getGinFusion());
				}
			}

			// if(individuDTO != null)	// Why test individuDTO that do not appear in following treatment
			if(changeData.getBlockDTOList() != null)
			{
				// Blocks info
				for (BlockDTO block : changeData.getBlockDTOList()) {

//	                Block currentIb = BlockDTO2BoForIndividualEvent(block);
//	                if (!isPurgedDeletedForgotten && !isPurged) {
//	                	neiMsg.getChangedBlocks().add(currentIb);
//	                }

					// REPIND-795 : Add Changed Block for ALL
					com.afklm.soa.stubs.w001992.v1_0_1.data.Block currentAIb = BlockDTO2BoForIndividualAdhocEvent(block);

					if (isHidden && currentAIb != null && INDIVIDUS.equals(currentAIb.getName()) && gin.equals(currentAIb.getIdentifier())) {
						// REPIND-15236 : In order to make possible a trigger by DWH
						currentAIb.setNotificationType("H");
					}

					naeiMsg.getChangedBlocks().add(currentAIb);
				}

				naeiMsg.setIndividualInformationData(indInfoData);
				getChangedDatasBlock(gin, naeiMsg.getChangedBlocks());
			}

			// Send NAEI event
			try {
				// REPIND-795 : Event AdHoc send
				BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + "START Send NAEI event for GIN : " + gin, null);
				com.afklm.soa.stubs.w001992.v1_0_1.NotifyAdhocEventIndividualV1 mServiceAdhoc = (com.afklm.soa.stubs.w001992.v1_0_1.NotifyAdhocEventIndividualV1) context.getBean("notifyAdhocEventIndividual-v1.0.1");

//	        	if (naeiMsg.getIndividualInformationData().getIndividualBlock().getIndividualInformations().getIdentifier().equals("400621178936")) {
//	        		String Test = "Test";
//	        	}


				mServiceAdhoc.notifyEventIndividual(naeiMsg);
				BatchIndividualUpdateNotification.incrementNbNAEIEventSent();
				BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + "END Send NAEI event for GIN : " + gin, null);

			} catch (WebServiceException e) {

				BatchIndividualUpdateNotification.setHasError(true);

				if (e.getCause() instanceof SAXParseException) {

					SAXParseException se = (SAXParseException) e.getCause();


					if (se.getException() instanceof MarshalException) {

						MarshalException me = (MarshalException) se.getException();


						if (me.getLinkedException() instanceof SAXParseException) {

							SAXParseException le = (SAXParseException) me.getLinkedException();

							BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.ERROR + ERROR_MSG + gin + " - " + le.getMessage(), e);
						} else {
							BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.ERROR + ERROR_MSG + gin, e);
						}
					} else {
						BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.ERROR + ERROR_MSG + gin, e);
					}
				} else {
					BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.ERROR + ERROR_MSG + gin, e);
				}

			} catch (Exception e) {
				BatchIndividualUpdateNotification.setHasError(true);
				BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.ERROR + ERROR_MSG + gin, e);
			}
		}
	}

	// REPIND-1263 : Mutualize the Logfunction INFO and ERROR
	private void BatchIndividualUpdateNotificationLogBatch(String messageToLog , Exception exception) {
		if (exception == null) {
			if(BatchIndividualUpdateNotification.debugLogActivated){
				BatchIndividualUpdateNotification.LogBatchOutput(messageToLog);
				try {
					BatchIndividualUpdateNotification.bfwOutput.flush();
				} catch (IOException eio) {
					BatchIndividualUpdateNotification.log.error(eio);
				}
			}
		} else {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			exception.printStackTrace(pw);
			log.error(exception);
			BatchIndividualUpdateNotification.LogBatchError(messageToLog, sw);
			try {
				BatchIndividualUpdateNotification.bfwError.flush();
			} catch (IOException eio) {
				BatchIndividualUpdateNotification.log.error(eio);
			}
		}
	}


	private IndividualInformationData getIndividualInformationData(String gin, IndividuDTO individuDTO, ChangeData changeData) throws JrafDomainException, JsonProcessingException {

		AccountDataDTO accountDataDTO = null;
		List<TelecomsDTO> telecomsDTOList = null;
		if(individuDTO == null) { // REPIND-795 : Temporary send Prospect to NEI in RCT for SFMC MiddleWare // || individuDTO.getType().equals("W")) {
			// REPIND-657: Fix Null Pointer Exception
			return null;
		}

		ArrayList<String> list = new ArrayList<String>();
		list.add(ScopesToProvideEnum.ALL.name());
		Set<ScopesToProvideEnum> scopesToProvideSet = ScopesToProvideEnum.getEnumSet(list);

		IndividualInformationData individualInformationData = NotifyIndividualTransform.transformToIndividualInformationData(individuDTO, scopesToProvideSet, true);

		// add postal address
		List<PostalAddressDTO> postalAddressDTOList = postalAddressDS.findPostalAddress(gin);
		NotifyIndividualTransform.transformToPostalAddressBlock(postalAddressDTOList, individualInformationData.getPostalAddressBlock());

		// REPIND-555: Prospect not returned
//		// add preference data
//		if(individuDTO.getType().equals("W")) {
//			individualInformationData.setPreferencesBlock(NotifyIndividualTransform.transformToPreferencesBlock(individuDTO.getPreferenceDTO()));
//		}

		// add mails
		if(scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.EMAIL)) {
			List<EmailDTO> emailDTOList = null;
			emailDTOList = emailDS.findEmail(gin);
			NotifyIndividualTransform.transformToEmailBlock(emailDTOList, individualInformationData.getEmailBlock());
		}

		//REPIND-1314: We retreive also Business Role in order to detect UCCR (Type=U)
		// add business roles
		long startBR = System.currentTimeMillis();
		Set<BusinessRoleDTO> businessRolesDTOList = businessRoleDS.findByIndividualGIN(gin);
		BatchIndividualUpdateNotificationLogBatch("BR = " + (System.currentTimeMillis() - startBR) + " ms", null);
		if (businessRolesDTOList != null && !businessRolesDTOList.isEmpty()) {
			individuDTO.setBusinessRolesDTO(businessRolesDTOList);
		}

		//REPIND-1314: As we have now Business Role, we can only request for Role Contract when is's necessary
		// add role contracts
		List<RoleContratsDTO> roleContratsDTOList = null;
		for (BusinessRoleDTO businessRoleDTO : businessRolesDTOList) {
			if (businessRoleDTO.getType() != null && "C".equalsIgnoreCase(businessRoleDTO.getType())) {
				roleContratsDTOList = roleDS.findRoleContrats(gin);
				break;
			}
		}

		//REPIND-3034 : Add role contracts with previous infos when contract is deleted
		if (changeData.getContractData() != null) {
			if (roleContratsDTOList == null) {
				roleContratsDTOList = new ArrayList<>();
			}
			roleContratsDTOList.add(changeData.getContractData());
		}

		NotifyIndividualTransform.transformToContractBlock(roleContratsDTOList, individualInformationData.getContractBlock());

		// add account data
		accountDataDTO = accountDataDS.getByGin(gin);
		individualInformationData.setAccountDataBlock(NotifyIndividualTransform.transformToAccountDataBlock(accountDataDTO));

		// add telecom data with usage code management
		telecomsDTOList = telecomDS.findLatestByUsageCode(gin);
		NotifyIndividualTransform.transformToTelecomBlock(telecomsDTOList, individualInformationData.getTelecomBlock());

		// add delegation data
		List<DelegationDataDTO> delegatorList = delegationDataDS.findDelegator(gin);
		List<DelegationDataDTO> delegateList = delegationDataDS.findDelegate(gin);
		DelegationDataBlock delegationDataResponse = NotifyIndividualTransform.dtoTOws(delegatorList, delegateList);
		individualInformationData.setDelegationDataBlock(delegationDataResponse);

		// add prefilled numbers
		List<PrefilledNumbersDTO> prefilledNumbersDTOList = prefilledNumbersDS.findPrefilledNumbers(gin);
		NotifyIndividualTransform.transformToPrefilledNumbersBlock(prefilledNumbersDTOList, individualInformationData.getPrefilledNumbersBlock());

		// add external identifiers
		List<ExternalIdentifierDTO> externalIdentifierDTOList = externalIdentifierDS.findExternalIdentifier(gin);
		NotifyIndividualTransform.transformToExternalIdentifierBlock(externalIdentifierDTOList, individualInformationData.getExternalIdentifierBlock());

		// add communication preferences
		List<CommunicationPreferencesDTO> communicationPreferencesDTOList = null;

		communicationPreferencesDTOList = communicationPreferencesDS.findCommunicationPreferences(gin);

		NotifyIndividualTransform.transformToCommunicationPreferencesBlock(communicationPreferencesDTOList, individualInformationData.getCommunicationPreferencesBlock());

		// REPIND-795 : Add PREFERENCE Block to the GetIndividual
		PreferenceDTO preferenceDTO = new PreferenceDTO();
		preferenceDTO.setGin(gin);
		List<PreferenceDTO> preferenceDTOList = preferenceDS.findByExample(preferenceDTO);

		// Normalize all preference data key
		List<PreferenceDTO> normalizedPrefDtoList = new ArrayList<>();
		if (preferenceDTOList != null) {
			for (PreferenceDTO prefDtoFromDB : preferenceDTOList) {
				// FIXEME ! Please uncomment where W000418V7-V7 will be available
				PreferenceDTO normalizedPrefDto = prefDtoFromDB; // preferenceHelperV7.normalizePreferenceDataKey(prefDtoFromDB);
				normalizedPrefDtoList.add(normalizedPrefDto);
			}
		}

		PreferencesBlock pb = NotifyIndividualTransform.transformToPreferencesBlock(normalizedPrefDtoList);
		individualInformationData.setPreferencesBlock(pb);

		return individualInformationData;
	}

	private void mapContractDeletionNumberInBlock(@NotNull Block currentBlock, RoleContratsDTO rc) {
		if (BatchSicUpdateNotificationChangedBlockNameEnum.CONTRACTS.toString().equalsIgnoreCase(currentBlock.getName())
				&& "D".equalsIgnoreCase(currentBlock.getNotificationType())) {
			currentBlock.setIdentifier(rc.getNumeroContrat());
		}
	}

	private com.afklm.soa.stubs.w001992.v1_0_1.data.IndividualInformationData getAdhocIndividualInformationData(String gin, IndividuDTO individuDTO, List<BlockDTO> blockDTOList) throws JrafDomainException {

		// We catch all the Individual Information
		ArrayList<String> list = new ArrayList<String>();
		list.add(ScopesToProvideEnum.IDENTIFICATION.name());
		Set<ScopesToProvideEnum> scopesToProvideSet = ScopesToProvideEnum.getEnumSet(list);
		com.afklm.soa.stubs.w001992.v1_0_1.data.IndividualInformationData individualInformationData = NotifyAdhocIndividualTransform.transformToIndividualInformationData(individuDTO, scopesToProvideSet, true);

		// We prepare to add the additional informations
		AccountDataDTO accountDataDTO = null;
		List<TelecomsDTO> telecomsDTOList = null;
		List<EmailDTO> emailDTOList = null;
		List<PostalAddressDTO> postalAddressDTOList = null;
		List<RoleContratsDTO> roleContratsDTOList = null;
		List<CommunicationPreferencesDTO> communicationPreferencesDTOList = null;
		List<ExternalIdentifierDTO> externalIdentifierDTOList = null;
		List<PreferenceDTO> preferenceDTOList = null;

//		List<DelegationDataDTO> delegatorList = null;
//		List<DelegationDataDTO> delegateList = null;

		if (blockDTOList != null) {
			// Add the specific Adhoc block that have been changed
			for (BlockDTO b : blockDTOList) {
				if (b != null) {
					switch (b.getName()) {

						case INDIVIDUS:
							// Nothing to do, because INDIVIDU is always include in message NAEI
							break;

						case "ACCOUNT DATA":
							if(b.getNotificationType() != null && !DELETED_TYPE.equalsIgnoreCase(b.getNotificationType())) {
								AccountDataDTO a = new AccountDataDTO();
								a.setId(Integer.valueOf(b.getIdentifier()));

								accountDataDTO = accountDataDS.get(a);
								if (accountDataDTO != null){
									individualInformationData.setAccountDataBlock(NotifyAdhocIndividualTransform.transformToAccountDataBlock(accountDataDTO));
								}
								else break;
							}
							break;

						case "TELECOM":

							if(b.getNotificationType() != null && !DELETED_TYPE.equalsIgnoreCase(b.getNotificationType())) {
								TelecomsDTO t = new TelecomsDTO();
								t.setSain(b.getIdentifier());

								if (telecomsDTOList == null) {
									telecomsDTOList = new ArrayList<TelecomsDTO>();
								}
								// Check if exist
								boolean tdExist = false;
								for (TelecomsDTO td : telecomsDTOList) {
									if (td != null && td.getSain().equals(t.getSain())) {
										// Cet ID existe deja
										tdExist = true;
										break;
									}
								}
								// Le Postal Address n'existe pas dans le block deja construit
								if (!tdExist) {
									TelecomsDTO telecomsDTO = telecomDS.get(t);
									if (telecomsDTO != null){
										telecomsDTOList.add(telecomsDTO);
									}
									else break;
								}
								NotifyAdhocIndividualTransform.transformToTelecomBlock(telecomsDTOList, individualInformationData.getTelecomBlock());
							}
							break;

						case "EMAIL":
							if(b.getNotificationType() != null && !DELETED_TYPE.equalsIgnoreCase(b.getNotificationType())) {
								EmailDTO e = new EmailDTO();
								e.setSain(b.getIdentifier());

								if (emailDTOList == null) {
									emailDTOList = new ArrayList<EmailDTO>();
								}
								// Check if exist
								boolean edExist = false;
								for (EmailDTO ed : emailDTOList) {
									if (ed != null && ed.getSain().equals(e.getSain())) {
										// Cet ID existe deja
										edExist = true;
										break;
									}
								}
								// Le Email n'existe pas dans le block deja construit
								if (!edExist) {
									EmailDTO emailDTO = emailDS.get(e);
									if (emailDTO != null){
										emailDTOList.add(emailDTO);
									}
									else break;
								}
								NotifyAdhocIndividualTransform.transformToEmailBlock(emailDTOList, individualInformationData.getEmailBlock());
							}
							break;

						case "POSTAL ADDRESS":
							if(b.getNotificationType() != null && !DELETED_TYPE.equalsIgnoreCase(b.getNotificationType())) {
								PostalAddressDTO p = new PostalAddressDTO();
								p.setSain(b.getIdentifier());

								if (postalAddressDTOList == null) {
									postalAddressDTOList = new ArrayList<PostalAddressDTO>();
								}
								// Check if exist
								boolean apdExist = false;
								for (PostalAddressDTO apd : postalAddressDTOList) {
									if (apd != null && apd.getSain().equals(p.getSain())) {
										// Cet ID existe deja
										apdExist = true;
										break;
									}
								}
								// Le Postal Address n'existe pas dans le block deja construit
								if (!apdExist) {
									PostalAddressDTO postalAddressDTO = postalAddressDS.get(p);
									if (postalAddressDTO != null){
										postalAddressDTOList.add(postalAddressDTO);
									}
									else break;
								}
								NotifyAdhocIndividualTransform.transformToPostalAddressBlock(postalAddressDTOList, individualInformationData.getPostalAddressBlock());
							}
							break;

						case "CONTRACTS":
							if(b.getNotificationType() != null && !DELETED_TYPE.equalsIgnoreCase(b.getNotificationType())) {
								RoleContratsDTO r = new RoleContratsDTO();
								r.setSrin(b.getIdentifier());

								if (roleContratsDTOList == null) {
									roleContratsDTOList = new ArrayList<RoleContratsDTO>();
								}
								// Check if exist
								boolean rcdExist = false;
								for (RoleContratsDTO rcd : roleContratsDTOList) {
									if (rcd != null && rcd.getSrin().equals(r.getSrin())) {
										// Cet ID existe deja
										rcdExist = true;
										break;
									}
								}
								// Le RoleCOntrat n'existe pas dans le block deja construit
								if (!rcdExist) {
									RoleContratsDTO roleContratsDTO = roleDS.get(r);
									if(roleContratsDTO != null){
										roleContratsDTOList.add(roleContratsDTO);
									}
									else break;
								}
								NotifyAdhocIndividualTransform.transformToContractBlock(roleContratsDTOList, individualInformationData.getContractBlock());
							}
							break;

						case "COMMUNICATION PREFERENCES":
							if(b.getNotificationType() != null && !DELETED_TYPE.equalsIgnoreCase(b.getNotificationType())) {
								CommunicationPreferencesDTO c = new CommunicationPreferencesDTO();
								c.setComPrefId(Integer.valueOf(b.getIdentifier()));

								if (communicationPreferencesDTOList == null) {
									communicationPreferencesDTOList = new ArrayList<CommunicationPreferencesDTO>();
								}

								// Check if exist
								boolean cpdExist = false;
								for (CommunicationPreferencesDTO cpd : communicationPreferencesDTOList) {
									if (cpd != null && cpd.getComPrefId().equals(c.getComPrefId())) {
										// Cet ID existe deja
										cpdExist = true;
										break;
									}
								}
								// La Comm Pref n'existe pas dans le block deja construit
								if (!cpdExist) {
									CommunicationPreferencesDTO communicationPreferencesDTO = communicationPreferencesDS.get(c);
									if (communicationPreferencesDTO != null){
										communicationPreferencesDTOList.add(communicationPreferencesDTO);
									}
									else break;
								}


								NotifyAdhocIndividualTransform.transformToCommunicationPreferencesBlock(communicationPreferencesDTOList, individualInformationData.getCommunicationPreferencesBlock());
							}
							break;

						case "EXTERNAL IDENTIFIER":
							if(b.getNotificationType() != null && !DELETED_TYPE.equalsIgnoreCase(b.getNotificationType())) {
								ExternalIdentifierDTO ei = new ExternalIdentifierDTO();
								ei.setIdentifierId(Long.valueOf(b.getIdentifier()));

								if (externalIdentifierDTOList == null) {
									externalIdentifierDTOList = new ArrayList<ExternalIdentifierDTO>();
								}
								// Check if exist
								boolean eidExist = false;
								for (ExternalIdentifierDTO cpd : externalIdentifierDTOList) {
									if (cpd != null && cpd.getIdentifierId().equals(ei.getIdentifierId())) {
										// Cet ID existe deja
										eidExist = true;
										break;
									}
								}
								// L Ext Id n'existe pas dans le block deja construit
								if (!eidExist) {
									ExternalIdentifierDTO  externalIdentifierDTO = externalIdentifierDS.get(ei);
									if (externalIdentifierDTO != null){
										externalIdentifierDTOList.add(externalIdentifierDTO);
									}
									else break;
								}
								NotifyAdhocIndividualTransform.transformToExternalIdentifierBlock(externalIdentifierDTOList, individualInformationData.getExternalIdentifierBlock());
							}
							break;

						case "PREFERENCE":
							if(b.getNotificationType() != null && !DELETED_TYPE.equalsIgnoreCase(b.getNotificationType())) {
								PreferenceDTO ps = new PreferenceDTO();
								ps.setPreferenceId(Long.valueOf(b.getIdentifier()));

								if (preferenceDTOList == null) {
									preferenceDTOList = new ArrayList<PreferenceDTO>();
								}

								// Check if exist
								boolean pdExist = false;
								for (PreferenceDTO pd : preferenceDTOList) {
									if (pd != null && pd.getPreferenceId().equals(ps.getPreferenceId())) {
										// Cet ID existe deja
										pdExist = true;
										break;
									}
								}
								// La Preference n'existe pas dans le block deja construit
								if (!pdExist) {
									PreferenceDTO preferenceDTO = preferenceDS.get(ps);
									if (preferenceDTO != null){
										preferenceDTOList.add(preferenceDTO);
									}
									else break;


									// Normalize all preference data key
									List<PreferenceDTO> normalizedPrefDtoList = new ArrayList<>();
									if (preferenceDTOList != null) {
										for (PreferenceDTO prefDtoFromDB : preferenceDTOList) {
											// FIXEME : Please uncomment where W000418V7-V7 will be available
											PreferenceDTO normalizedPrefDto = prefDtoFromDB; // preferenceHelperV7.normalizePreferenceDataKey(prefDtoFromDB);
											normalizedPrefDtoList.add(normalizedPrefDto);
										}
									}

									com.afklm.soa.stubs.w001992.v1_0_1.individual.PreferencesBlock pb = NotifyAdhocIndividualTransform.transformToPreferencesBlock(normalizedPrefDtoList);
									individualInformationData.setPreferencesBlock(pb);
								}
							}
							break;

						case CONSENT:
							// No details because, this is created on ChangedBlocked section...
							break;

						// TODO : Code DELEGATION_DATA
/*
						DELEGATION_DATA
*/
						default:
							BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.WARN + " Warn during getAdhocIndividualInformationData for gin = " + gin + " and block = " + b.getName(), null);
							break;
					}
				}
			}
		}

		return individualInformationData;
	}

	/**
	 * Transform ImpactedBlock dto to bo
	 * @param dto
	 * @return com.afklm.soa.stubs.w001392.v1.notifymember.data.ImpactedBlock ib
	 */
	private Block BlockDTO2BoForIndividualEvent(BlockDTO dto) {
		Block block = null;

		if(dto != null)
		{
			block = new Block();
			block.setIdentifier(dto.getIdentifier());
			block.setName(dto.getName());
			block.setNotificationType(dto.getNotificationType());

		}

		return block;
	}

	/**
	 * Transform ImpactedBlock dto to bo
	 * @param dto BlockDTO
	 * @return com.afklm.soa.stubs.w001992.v1.data.Block ib
	 */
	private com.afklm.soa.stubs.w001992.v1_0_1.data.Block BlockDTO2BoForIndividualAdhocEvent(BlockDTO dto) {
		com.afklm.soa.stubs.w001992.v1_0_1.data.Block block = null;

		if(dto != null)
		{
			block = new com.afklm.soa.stubs.w001992.v1_0_1.data.Block();
			block.setIdentifier(dto.getIdentifier());
			block.setName(dto.getName());
			block.setNotificationType(dto.getNotificationType());
			// REPIND-795 : Ce champs semble etre obligatoire alors qu il est là normalement optionnel
			ChangedDatasBlock cdbs = new ChangedDatasBlock();
			block.getChangedDatasBlock().add(cdbs);
		}

		return block;
	}

	public static void resetIds() {
		ids.clear();
	}

	/**
	 * Get changed datas block
	 * @param listBlock
	 */
	private void getChangedDatasBlock(String gin, List<com.afklm.soa.stubs.w001992.v1_0_1.data.Block> listBlock) {

		for (com.afklm.soa.stubs.w001992.v1_0_1.data.Block block : listBlock) {

			// Deletion on the Empty first "changedDataBlock"
			if (block != null && block.getChangedDatasBlock() != null && block.getChangedDatasBlock().get(0) != null &&
					block.getChangedDatasBlock().get(0).getChangedDataBlock().isEmpty()) {
				block.getChangedDatasBlock().remove(0);
			}
			if(block != null){
				switch (block.getName()) {
					case CONSENT :

						try {
							if(!"D".equals(block.getNotificationType())) {

								BatchIndividualUpdateNotificationLogBatch(taskName + "--> Block Identifier : " + block.getIdentifier(), null);
								ConsentDTO consent = consentDS.findById(Long.valueOf(block.getIdentifier()));

								if (consent != null) {

									ChangedDatasBlock cdbst = new ChangedDatasBlock();
									cdbst.getChangedDataBlock().add(AddChangedDataBlock("CONSENT_TYPE", consent.getType()));
									cdbst.getChangedDataBlock().add(AddChangedDataBlock("CONSENT_ID", consent.getConsentId().toString()));
									block.getChangedDatasBlock().add(cdbst);

									for (ConsentDataDTO consentData : consent.getConsentDataDTO()) {
										ChangedDatasBlock cdbs = new ChangedDatasBlock();
										cdbs.getChangedDataBlock().add(AddChangedDataBlock("CONSENT_DT_ID", consentData. getConsentDataId().toString()));
										cdbs.getChangedDataBlock().add(AddChangedDataBlock("CONSENT_DT_TYPE", consentData.getType()));
										cdbs.getChangedDataBlock().add(AddChangedDataBlock("CONSENT_DT_OPTIN", consentData.getIsConsent()));
										cdbs.getChangedDataBlock().add(AddChangedDataBlock("CONSENT_DT_DATE", consentData.getDateConsent().toString()));
										block.getChangedDatasBlock().add(cdbs);
									}
								} else {
									BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.WARN + ERROR_MSG + gin + " - Consent NOT FOUND", null);
								}
							}
						} catch (Exception e) {

							BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.ERROR + ERROR_MSG + gin, e);
						}

						break;

					case INDIVIDUS:
						try{
							BatchIndividualUpdateNotificationLogBatch(taskName + "--> Block Identifier : " + block.getIdentifier(), null);
							IndividuDTO individu = individuDS.get(block.getIdentifier());

							if(individu != null && individu.getDateFusion() != null) {
								ChangedDatasBlock cdbs = new ChangedDatasBlock();
								Instant instant = individu.getDateFusion().toInstant();
								cdbs.getChangedDataBlock().add(AddChangedDataBlock("dateFusion", instant.toString()));
								block.getChangedDatasBlock().add(cdbs);
							}
						} catch (JrafDomainException e) {
							BatchIndividualUpdateNotificationLogBatch(taskName + "--> " + BatchIndividualUpdateNotification.WARN + ERROR_MSG + gin + " - Individus NOT FOUND", null);
						}
						break;
				}
			}


		}
	}

	private ChangedDataBlock AddChangedDataBlock(String key, String value) {
		ChangedDataBlock changedDataBlockType = new ChangedDataBlock();
		changedDataBlockType.setKey(key);
		changedDataBlockType.setValue(value);
		return changedDataBlockType;
	}

	public IndividuDS getIndividuDS() {
		return individuDS;
	}

	public void setIndividuDS(IndividuDS individuDS) {
		this.individuDS = individuDS;
	}

	public PostalAddressDS getPostalAddressDS() {
		return postalAddressDS;
	}

	public void setPostalAddressDS(PostalAddressDS postalAddressDS) {
		this.postalAddressDS = postalAddressDS;
	}

	public PrefilledNumbersDS getPrefilledNumbersDS() {
		return prefilledNumbersDS;
	}

	public void setPrefilledNumbersDS(PrefilledNumbersDS prefilledNumbersDS) {
		this.prefilledNumbersDS = prefilledNumbersDS;
	}

	public AccountDataDS getAccountDataDS() {
		return accountDataDS;
	}

	public void setAccountDataDS(AccountDataDS accountDataDS) {
		this.accountDataDS = accountDataDS;
	}

	public TelecomDS getTelecomDS() {
		return telecomDS;
	}

	public void setTelecomDS(TelecomDS telecomDS) {
		this.telecomDS = telecomDS;
	}

	public DelegationDataDS getDelegationDataDS() {
		return delegationDataDS;
	}

	public void setDelegationDataDS(DelegationDataDS delegationDataDS) {
		this.delegationDataDS = delegationDataDS;
	}

	public ExternalIdentifierDS getExternalIdentifierDS() {
		return externalIdentifierDS;
	}

	public void setExternalIdentifierDS(ExternalIdentifierDS externalIdentifierDS) {
		this.externalIdentifierDS = externalIdentifierDS;
	}

	public CommunicationPreferencesDS getCommunicationPreferencesDS() {
		return communicationPreferencesDS;
	}

	public void setCommunicationPreferencesDS(CommunicationPreferencesDS communicationPreferencesDS) {
		this.communicationPreferencesDS = communicationPreferencesDS;
	}

	public PreferenceDS getPreferenceDS() {
		return preferenceDS;
	}

	public void setPreferenceDS(PreferenceDS preferenceDS) {
		this.preferenceDS = preferenceDS;
	}

	public RoleDS getRoleDS() {
		return roleDS;
	}

	public void setRoleDS(RoleDS roleDS) {
		this.roleDS = roleDS;
	}

	public EmailDS getEmailDS() {
		return emailDS;
	}

	public void setEmailDS(EmailDS emailDS) {
		this.emailDS = emailDS;
	}

	public BusinessRoleDS getBusinessRoleDS() {
		return businessRoleDS;
	}

	public void setBusinessRoleDS(BusinessRoleDS businessRoleDS) {
		this.businessRoleDS = businessRoleDS;
	}

	public ConsentDS getConsentDS() {
		return consentDS;
	}

	public void setConsentDS(ConsentDS consentDS) {
		this.consentDS = consentDS;
	}

}
