package com.afklm.batch.event.individu;

import com.airfrance.batch.common.dto.BlockDTO;
import com.airfrance.common.batch.config.WebConfigTestBatch;
import com.airfrance.repind.config.vault.VaultConfigurationRepind;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDataDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.PrefilledNumbersDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebConfigTestBatch.class})
@Transactional(value = "transactionManagerRepind")
public class SendNotifyEventIndividualTaskTest {

	@Autowired
	private SendNotifyEventIndividualTask sendNotifyEventIndividualTask;
	

	IndividuDS individuDS;
    PostalAddressDS postalAddressDS;
	PrefilledNumbersDS prefilledNumbersDS;
	AccountDataDS accountDataDS;
	TelecomDS telecomDS;
	DelegationDataDS delegationDataDS;
	ExternalIdentifierDS externalIdentifierDS;
	CommunicationPreferencesDS communicationPreferencesDS;
	RoleDS roleDS;
	EmailDS emailDS;


    @Before
    public void setUp() {
        
        individuDS = createMock(IndividuDS.class);
        accountDataDS = createMock(AccountDataDS.class);
        postalAddressDS = createMock(PostalAddressDS.class);
        prefilledNumbersDS = createMock(PrefilledNumbersDS.class);
        telecomDS = createMock(TelecomDS.class);
        delegationDataDS = createMock(DelegationDataDS.class);
        externalIdentifierDS = createMock(ExternalIdentifierDS.class);
        communicationPreferencesDS = createMock(CommunicationPreferencesDS.class);
        roleDS = createMock(RoleDS.class);
        emailDS = createMock(EmailDS.class);

        sendNotifyEventIndividualTask.setIndividuDS(individuDS);
        sendNotifyEventIndividualTask.setAccountDataDS(accountDataDS);
        sendNotifyEventIndividualTask.setPostalAddressDS(postalAddressDS);
        sendNotifyEventIndividualTask.setPrefilledNumbersDS(prefilledNumbersDS);
        sendNotifyEventIndividualTask.setTelecomDS(telecomDS);
        sendNotifyEventIndividualTask.setDelegationDataDS(delegationDataDS);
        sendNotifyEventIndividualTask.setExternalIdentifierDS(externalIdentifierDS);
        sendNotifyEventIndividualTask.setCommunicationPreferencesDS(communicationPreferencesDS);
        sendNotifyEventIndividualTask.setRoleDS(roleDS);
        sendNotifyEventIndividualTask.setEmailDS(emailDS);
        
    }
	
	/* ********************************************************************************************
	 * 
	 * INTEGRATION TEST ON TREATLINE METHOD
	 * 
	 ******************************************************************************************* */
	@Test
	@Ignore
	public void runSendNotifyEventIndividualTask() throws Exception {

		BatchIndividualUpdateNotification.nbNEIEventSent = 0;
        // Individual
		IndividuDTO indDTO = new IndividuDTO();
		indDTO.setSgin("990080119821");
		indDTO.setNom("TEST");
		indDTO.setType("I");
        expect(individuDS.getByGin("990080119821")).andReturn(indDTO);
        EasyMock.replay(individuDS);

        // Postal Address
        PostalAddressDTO paDTO = new PostalAddressDTO();
        paDTO.setScode_postal("06560");
        paDTO.setSno_et_rue("Route des lucioles");
        paDTO.setSlocalite("");
        List<PostalAddressDTO> postalAddressDTOList = new ArrayList<PostalAddressDTO>();
        postalAddressDTOList.add(paDTO);
        expect(postalAddressDS.findPostalAddress("990080119821")).andReturn(postalAddressDTOList);
        EasyMock.replay(postalAddressDS);

        // Role contracts
        List<RoleContratsDTO> roleContratsDTOList = new ArrayList<RoleContratsDTO>();
        RoleContratsDTO rcDTO = new RoleContratsDTO();
        rcDTO.setGin("990080119821");
        roleContratsDTOList.add(rcDTO);
        expect(roleDS.findRoleContrats("990080119821")).andReturn(roleContratsDTOList);
        EasyMock.replay(roleDS);
        
        
        // Commented because findByExample is from an abstract class
//        // Prospect
//        List<ProspectDTO> findProspect = new ArrayList<ProspectDTO>();
//        ProspectDTO pDTO = createMock(ProspectDTO.class);
//        pDTO.setGin(Long.valueOf("990080119821"));
//        findProspect.add(pDTO);
//        expect(prospectDS.findByExample(pDTO)).andReturn(findProspect);
//        EasyMock.replay(prospectDS);

        // Email
        List<EmailDTO> emailDTOList = new ArrayList<EmailDTO>();
        EmailDTO eDTO = new EmailDTO();
        eDTO.setEmail("test@test.fr");
        emailDTOList.add(eDTO);
        expect(emailDS.findEmail("990080119821")).andReturn(emailDTOList);
        EasyMock.replay(emailDS);

        // Account Data
        AccountDataDTO aDTO = new AccountDataDTO();
        aDTO.setSgin("990080119821");
        expect(accountDataDS.getByGin("990080119821")).andReturn(aDTO);
        EasyMock.replay(accountDataDS);

        // Delegation Data
        List<DelegationDataDTO> delegatorList = new ArrayList<DelegationDataDTO>();
        DelegationDataDTO ddDTO = new DelegationDataDTO();
        ddDTO.setStatus("I");
        ddDTO.setSender("DGR");
        indDTO.setAccountdatadto(aDTO);
        ddDTO.setDelegatorDTO(indDTO);
        delegatorList.add(ddDTO);
        List<DelegationDataDTO> delegateList = new ArrayList<DelegationDataDTO>();
        DelegationDataDTO ddDTO2 = new DelegationDataDTO();
        ddDTO2.setStatus("I");
        ddDTO2.setSender("DGR");
        ddDTO2.setDelegateDTO(indDTO);
        delegateList.add(ddDTO2);
        expect(delegationDataDS.findDelegator("990080119821")).andReturn(delegatorList);
        expect(delegationDataDS.findDelegate("990080119821")).andReturn(delegateList);
        EasyMock.replay(delegationDataDS);

        // Prefilled Number
        List<PrefilledNumbersDTO> prefilledNumbersDTOList = new ArrayList<PrefilledNumbersDTO>();
        PrefilledNumbersDTO pnDTO = new PrefilledNumbersDTO();
        pnDTO.setSgin("990080119821");
        Set<PrefilledNumbersDataDTO> pndSet = new HashSet<PrefilledNumbersDataDTO>();
        PrefilledNumbersDataDTO pndDTO = new PrefilledNumbersDataDTO();
        pndDTO.setKey("Key");
        pndSet.add(pndDTO);
        pnDTO.setPrefilledNumbersDataDTO(pndSet);
        prefilledNumbersDTOList.add(pnDTO);
        expect(prefilledNumbersDS.findPrefilledNumbers("990080119821")).andReturn(prefilledNumbersDTOList);
        EasyMock.replay(prefilledNumbersDS);
        
        
        // External Identifier
        List<ExternalIdentifierDTO> externalIdentifierDTOList = new ArrayList<ExternalIdentifierDTO>();
        ExternalIdentifierDTO eiDTO = new ExternalIdentifierDTO();
        eiDTO.setGin("990080119821");
        List<ExternalIdentifierDataDTO> eidList = new ArrayList<ExternalIdentifierDataDTO>();
        ExternalIdentifierDataDTO eidDTO = new ExternalIdentifierDataDTO();
        eidDTO.setKey("Key");
        eidDTO.setValue("Value");
        eidList.add(eidDTO);
        eiDTO.setExternalIdentifierDataList(eidList);
        externalIdentifierDTOList.add(eiDTO);
        expect(externalIdentifierDS.findExternalIdentifier("990080119821")).andReturn(externalIdentifierDTOList);
        EasyMock.replay(externalIdentifierDS);

        // Communication Preferences
        List<CommunicationPreferencesDTO> communicationPreferencesDTOList = new ArrayList<CommunicationPreferencesDTO>();
        CommunicationPreferencesDTO cpDTO = new CommunicationPreferencesDTO();
        cpDTO.setGin("990080119821");
        cpDTO.setMedia1("E");
        // MarketLanguage
        Set<MarketLanguageDTO> mlSet = new HashSet<MarketLanguageDTO>();
        MarketLanguageDTO mlDTO = new MarketLanguageDTO();
        mlDTO.setMedia1("E");
        mlDTO.setLanguage("FR");
        mlSet.add(mlDTO);
        cpDTO.setMarketLanguageDTO(mlSet);
        communicationPreferencesDTOList.add(cpDTO);
        expect(communicationPreferencesDS.findCommunicationPreferences("990080119821")).andReturn(communicationPreferencesDTOList);
        EasyMock.replay(communicationPreferencesDS);
        
        
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		
		queue.put("990080119821");
		
		ArrayList<BlockDTO> blockDTOList = new ArrayList<BlockDTO>();
		BlockDTO blockDTO = new BlockDTO();
        blockDTO.setIdentifier("11904345");
        blockDTO.setName("ACCOUNT_DATA");
        blockDTO.setNotificationType("U");
        
        blockDTOList.add(blockDTO);
        
        LinkedHashMap<String, List<BlockDTO>> ginAssociatedToBlockDTOListHm = new LinkedHashMap<String, List<BlockDTO>>();
        ginAssociatedToBlockDTOListHm.put("990080119821", blockDTOList);
        
    	sendNotifyEventIndividualTask.setGinLinkedToTriggerChangeIndIdListHm(ginAssociatedToBlockDTOListHm);
		sendNotifyEventIndividualTask.setQueue(queue);
		sendNotifyEventIndividualTask.run();
		
		Assert.assertEquals(1, BatchIndividualUpdateNotification.nbNEIEventSent);
		
	}

	@Test
	@Ignore
	public void runSendNotifyEventIndividualTaskWithFail() throws Exception {

		BatchIndividualUpdateNotification.nbNEIEventSent = 0;

        // Individual
		IndividuDTO indDTO = new IndividuDTO();
		indDTO.setSgin("412334392542");
		indDTO.setNom("TEST");
		indDTO.setType("I");
        expect(individuDS.getByGin("412334392542")).andReturn(indDTO);
        EasyMock.replay(individuDS);

        // Delegation Data
        List<DelegationDataDTO> delegatorList = new ArrayList<DelegationDataDTO>();
        DelegationDataDTO ddDTO = new DelegationDataDTO();
        ddDTO.setStatus("I");
        ddDTO.setSender("DGR");
        ddDTO.setDelegatorDTO(indDTO);
        delegatorList.add(ddDTO);
        List<DelegationDataDTO> delegateList = new ArrayList<DelegationDataDTO>();
        DelegationDataDTO ddDTO2 = new DelegationDataDTO();
        ddDTO2.setStatus("I");
        ddDTO2.setSender("DGR");
        ddDTO2.setDelegateDTO(indDTO);
        delegateList.add(ddDTO2);
        expect(delegationDataDS.findDelegator("412334392542")).andReturn(delegatorList);
        expect(delegationDataDS.findDelegate("412334392542")).andReturn(delegateList);
        EasyMock.replay(delegationDataDS);

        
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		
		queue.put("412334392542");
		
		ArrayList<BlockDTO> blockDTOList = new ArrayList<BlockDTO>();
		BlockDTO blockDTO = new BlockDTO();
        blockDTO.setIdentifier("47377563");
        blockDTO.setName("ACCOUNT_DATA");
        blockDTO.setNotificationType("U");
        
        blockDTOList.add(blockDTO);
        
        LinkedHashMap<String, List<BlockDTO>> ginAssociatedToBlockDTOListHm = new LinkedHashMap<String, List<BlockDTO>>();
        ginAssociatedToBlockDTOListHm.put("412334392542", blockDTOList);
        
    	sendNotifyEventIndividualTask.setGinLinkedToTriggerChangeIndIdListHm(ginAssociatedToBlockDTOListHm);
		sendNotifyEventIndividualTask.setQueue(queue);
		sendNotifyEventIndividualTask.run();
		
		Assert.assertEquals(1, BatchIndividualUpdateNotification.nbNEIEventSent);
		
	}

	@Test
	@Ignore
	public void runSendNotifyEventIndividualTaskNull() throws Exception {

		BatchIndividualUpdateNotification.nbNEIEventSent = 0;

        // Individual
		IndividuDTO indDTO = new IndividuDTO();
		indDTO.setSgin("900334392542");
		indDTO.setNom("TEST");
		indDTO.setType("W");
        // Individual
        expect(individuDS.getByGin("900334392542")).andReturn(indDTO);
        EasyMock.replay(individuDS);

        
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		
		queue.put("900334392542");
		
		ArrayList<BlockDTO> blockDTOList = new ArrayList<BlockDTO>();
		BlockDTO blockDTO = new BlockDTO();
        blockDTO.setIdentifier("47377563");
        blockDTO.setName("COMMUNICATION_PREFERENCES");
        blockDTO.setNotificationType("U");
        
        blockDTOList.add(blockDTO);
        
        LinkedHashMap<String, List<BlockDTO>> ginAssociatedToBlockDTOListHm = new LinkedHashMap<String, List<BlockDTO>>();
        ginAssociatedToBlockDTOListHm.put("900334392542", blockDTOList);
        
    	sendNotifyEventIndividualTask.setGinLinkedToTriggerChangeIndIdListHm(ginAssociatedToBlockDTOListHm);
		sendNotifyEventIndividualTask.setQueue(queue);
		sendNotifyEventIndividualTask.run();
		
		Assert.assertEquals(0, BatchIndividualUpdateNotification.nbNEIEventSent);
		
	}

	@Test
	@Ignore
	public void runSendNotifyEventIndividualTaskProspect() throws Exception {

		BatchIndividualUpdateNotification.nbNEIEventSent = 0;

        // Individual
        expect(individuDS.getByGin("412334342542")).andReturn(null);
        EasyMock.replay(individuDS);

        
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		
		queue.put("412334342542");
		
		ArrayList<BlockDTO> blockDTOList = new ArrayList<BlockDTO>();
		BlockDTO blockDTO = new BlockDTO();
        blockDTO.setIdentifier("47377563");
        blockDTO.setName("ACCOUNT_DATA");
        blockDTO.setNotificationType("U");
        
        blockDTOList.add(blockDTO);
        
        LinkedHashMap<String, List<BlockDTO>> ginAssociatedToBlockDTOListHm = new LinkedHashMap<String, List<BlockDTO>>();
        ginAssociatedToBlockDTOListHm.put("412334342542", blockDTOList);
        
    	sendNotifyEventIndividualTask.setGinLinkedToTriggerChangeIndIdListHm(ginAssociatedToBlockDTOListHm);
		sendNotifyEventIndividualTask.setQueue(queue);
		sendNotifyEventIndividualTask.run();
		
		Assert.assertEquals(0, BatchIndividualUpdateNotification.nbNEIEventSent);
		
	}
}
