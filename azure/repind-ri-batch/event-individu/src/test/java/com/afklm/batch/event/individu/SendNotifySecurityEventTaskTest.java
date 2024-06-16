package com.afklm.batch.event.individu;

import com.afklm.soa.stubs.w001690.v1.NotifySecurityEventForIndividualV1;
import com.afklm.soa.stubs.w001690.v1.data.EventBlock;
import com.afklm.soa.stubs.w001690.v1.data.NotifySecurityEventForIndividualMsg;
import com.airfrance.batch.common.enums.EventTypeEnum;
import com.airfrance.batch.common.enums.KeySecurityEventEnum;
import com.airfrance.common.batch.config.WebConfigTestBatch;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.vault.VaultConfigurationRepind;
import com.airfrance.repind.dto.reference.RefDetailsKeyDTO;
import com.airfrance.repind.dto.reference.RefTypeEventDTO;
import com.airfrance.repind.dto.tracking.TriggerChangeIndividusDTO;
import com.airfrance.repind.entity.reference.RefTypeEvent;
import com.airfrance.repind.service.reference.internal.RefDetailsKeyDS;
import com.airfrance.repind.service.reference.internal.RefTypeEventDS;
import com.airfrance.repind.service.tracking.internal.TriggerChangeIndividusDS;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {VaultConfigurationRepind.class})
@ContextConfiguration(classes = {WebConfigTestBatch.class})
@Transactional(value = "transactionManagerRepind")
@Import(value = VaultConfigurationRepind.class)

public class SendNotifySecurityEventTaskTest {

	@Autowired
	private BatchIndividualUpdateNotification batchIndividualUpdateNotification;

	private RefDetailsKeyDS detailsKeyDS;
	
	private RefTypeEventDS typeEventDS;

	private NotifySecurityEventForIndividualV1 notifySecurityEventForIndividualV1;
	
	private TriggerChangeIndividusDS triggerChangeIndividusDS;

	private final static String TYPE_NOT_FOUND = "Type not found";

	@Autowired
	private SendNotifySecurityEventTask sendNotifySecurityEventTask;
	
    @Before
    public void setUp() {
		
        triggerChangeIndividusDS = createMock(TriggerChangeIndividusDS.class);
        notifySecurityEventForIndividualV1 = createMock(NotifySecurityEventForIndividualV1.class);
        detailsKeyDS = createNiceMock(RefDetailsKeyDS.class);
        typeEventDS = createNiceMock(RefTypeEventDS.class);
        
		sendNotifySecurityEventTask.setNotifySecurityEventForIndividualV1(notifySecurityEventForIndividualV1);
        
    }
    
	/* ********************************************************************************************
	 * 
	 * UNIT TESTS ON PARSEARGS METHOD
	 * 
	 ******************************************************************************************* */
    
	@Test
	public void parseArgsValidTest() throws Exception {
		batchIndividualUpdateNotification.parseArgs(new String[] { "-l", "/tmp/", "-i"});
		// If this assert is not executed, there is something wrong
		Assert.assertTrue(true);
	}
	
	
	/* ********************************************************************************************
	 * 
	 * INTEGRATION TEST ON TREATLINE METHOD
	 * 
	 ******************************************************************************************* */
	
	@Test
	@Ignore
	public void runSendNotifySecurityEventTask() throws Exception {

		BatchIndividualUpdateNotification.nbNSEIEventSent = 0;
		// Change password
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "10/11/2012", "18/01/2017", "password changed", "secret question answer", "secret question");
		triggerChangeIndividusDTO.setSignatureModification("TEST NSEI");
		
		LinkedBlockingQueue<TriggerChangeIndividusDTO> queue = new LinkedBlockingQueue<TriggerChangeIndividusDTO>();
		queue.add(triggerChangeIndividusDTO);
		sendNotifySecurityEventTask.setQueue(queue);
		sendNotifySecurityEventTask.run();
		
		Assert.assertEquals(1, BatchIndividualUpdateNotification.nbNSEIEventSent);
		
	}
	
	/* ********************************************************************************************
	 * 
	 * UNIT TESTS ON RUN METHOD
	 * 
	 ******************************************************************************************* */
	@Test
	@Ignore
	public void testSendNotifySecurityEventTask_runWithTriggerChangeIndividusPC() throws JrafDomainException {

		BatchIndividualUpdateNotification.nbNSEIEventSent = 0;
		mockDS();
		
		// Change password
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "10/11/2012", "18/01/2017", "password changed", "secret question answer", "secret question");
		
		List<RefDetailsKeyDTO> refDetailsKeyList = new ArrayList<RefDetailsKeyDTO>();
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.STATUS.toString(), KeySecurityEventEnum.STATUS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.IP_ADDRESS.toString(), KeySecurityEventEnum.IP_ADDRESS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.PASSWORD_TO_CHANGE.toString(), KeySecurityEventEnum.PASSWORD_TO_CHANGE.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.TEMPORARY_PWD_END_DATE.toString(), KeySecurityEventEnum.TEMPORARY_PWD_END_DATE.getKey()));
		
        expect(detailsKeyDS.findByTypeEvent(EasyMock.anyObject(RefTypeEvent.class))).andReturn(refDetailsKeyList);
		EasyMock.expectLastCall().once();
        EasyMock.replay(detailsKeyDS);
        
		NotifySecurityEventForIndividualMsg nseMsg = createNiceMock(NotifySecurityEventForIndividualMsg.class);
		notifySecurityEventForIndividualV1.notifySecurityEvent(nseMsg);
		EasyMock.expectLastCall().once();
		
        expect(typeEventDS.findAll()).andReturn(setRefTypeEventList());
		EasyMock.expectLastCall().once();
        EasyMock.replay(typeEventDS);
        
		LinkedBlockingQueue<TriggerChangeIndividusDTO> queue = new LinkedBlockingQueue<TriggerChangeIndividusDTO>();
		queue.add(triggerChangeIndividusDTO);
		sendNotifySecurityEventTask.setQueue(queue);
		
		sendNotifySecurityEventTask.run();
		
		Assert.assertEquals(1, BatchIndividualUpdateNotification.nbNSEIEventSent);
		
	}
	
	/* ********************************************************************************************
	 * 
	 * UNIT TESTS ON SENDNOTIFYSECURITYEVENT METHOD
	 * 
	 ******************************************************************************************* */

	@Test
	@Ignore
	public void testSendNotifySecurityEventTask_sendNotifySecurityEventPC() throws JrafDomainException {

		BatchIndividualUpdateNotification.nbNSEIEventSent = 0;
		mockDS();
		setRefTypeEventList();
		
		// Change password
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "10/11/2012", "18/01/2017", "password changed", "secret question answer", "secret question");
		
		List<RefDetailsKeyDTO> refDetailsKeyList = new ArrayList<RefDetailsKeyDTO>();
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.STATUS.toString(), KeySecurityEventEnum.STATUS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.IP_ADDRESS.toString(), KeySecurityEventEnum.IP_ADDRESS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.PASSWORD_TO_CHANGE.toString(), KeySecurityEventEnum.PASSWORD_TO_CHANGE.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.TEMPORARY_PWD_END_DATE.toString(), KeySecurityEventEnum.TEMPORARY_PWD_END_DATE.getKey()));
		
        expect(detailsKeyDS.findByTypeEvent(EasyMock.anyObject(RefTypeEvent.class))).andReturn(refDetailsKeyList);
		EasyMock.expectLastCall().once();
        EasyMock.replay(detailsKeyDS);
        
		NotifySecurityEventForIndividualMsg nseMsg = createNiceMock(NotifySecurityEventForIndividualMsg.class);
		notifySecurityEventForIndividualV1.notifySecurityEvent(nseMsg);
		EasyMock.expectLastCall().once();
		
		sendNotifySecurityEventTask.sendNotifySecurityEvent(triggerChangeIndividusDTO, "test");
		
		Assert.assertEquals(1, BatchIndividualUpdateNotification.nbNSEIEventSent);
		
	}

	/* ********************************************************************************************
	 * 
	 * UNIT TESTS ON GETEVENTBLOCK METHOD
	 * 
	 ******************************************************************************************* */
	@Test
	public void testSendNotifySecurityEventTask_getEventTypeNull() throws Exception {
		
		mockDS();
		setRefTypeEventList();
		
		// No changes
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		
		EventBlock event = sendNotifySecurityEventTask.getEventBlock(triggerChangeIndividusDTO);
		Assert.assertNull(event);
        
	}
	
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 10 secondes
	public void testSendNotifySecurityEventTask_getEventBlockPasswordChange() throws Exception {
		
		mockDS();
		setRefTypeEventList();
		
		// Change password
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "10/11/2012", "18/01/2017", "password changed", "secret question answer", "secret question");
		
		List<RefDetailsKeyDTO> refDetailsKeyList = new ArrayList<RefDetailsKeyDTO>();
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.STATUS.toString(), KeySecurityEventEnum.STATUS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.IP_ADDRESS.toString(), KeySecurityEventEnum.IP_ADDRESS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.PASSWORD_TO_CHANGE.toString(), KeySecurityEventEnum.PASSWORD_TO_CHANGE.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.TEMPORARY_PWD_END_DATE.toString(), KeySecurityEventEnum.TEMPORARY_PWD_END_DATE.getKey()));
		
        expect(detailsKeyDS.findByTypeEvent(EasyMock.anyObject(RefTypeEvent.class))).andReturn(refDetailsKeyList);
		EasyMock.expectLastCall().once();
        EasyMock.replay(detailsKeyDS);
        
		EventBlock event = sendNotifySecurityEventTask.getEventBlock(triggerChangeIndividusDTO);
        
		assertTrue(event.getType().equals(EventTypeEnum.PASSWORD_CHANGE.toString()));
		assertTrue(event.getEventDetails().size() == 3);
		
        for (int i = 0; i < refDetailsKeyList.size() - 1; i++) {
        	assertTrue(event.getEventDetails().get(i).getKey().equals(refDetailsKeyList.get(i).getCode()));
		}
        
	}
	
	@Test
	public void testSendNotifySecurityEventTask_getEventBlockPasswordReset() throws Exception {
		
		mockDS();
		setRefTypeEventList();
		
		// Change last password reset
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "13/03/2017", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
				
		List<RefDetailsKeyDTO> refDetailsKeyList = new ArrayList<RefDetailsKeyDTO>();
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.STATUS.toString(), KeySecurityEventEnum.STATUS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.IP_ADDRESS.toString(), KeySecurityEventEnum.IP_ADDRESS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.PASSWORD_TO_CHANGE.toString(), KeySecurityEventEnum.PASSWORD_TO_CHANGE.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.LAST_PWD_RESET_DATE.toString(), KeySecurityEventEnum.LAST_PWD_RESET_DATE.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.TEMPORARY_PWD_END_DATE.toString(), KeySecurityEventEnum.TEMPORARY_PWD_END_DATE.getKey()));

        expect(detailsKeyDS.findByTypeEvent(EasyMock.anyObject(RefTypeEvent.class))).andReturn(refDetailsKeyList);
		EasyMock.expectLastCall().once();
        EasyMock.replay(detailsKeyDS);
        
		EventBlock event = sendNotifySecurityEventTask.getEventBlock(triggerChangeIndividusDTO);
        
		assertTrue(event.getType().equals(EventTypeEnum.PASSWORD_RESET.toString()));
		assertTrue(event.getEventDetails().size() == 4);
		
        for (int i = 0; i < refDetailsKeyList.size() - 1; i++) {
        	assertTrue(event.getEventDetails().get(i).getKey().equals(refDetailsKeyList.get(i).getCode()));
		}
        
	}
	
	@Test
	public void testSendNotifySecurityEventTask_getEventBlockSecretQuestionUpdate() throws Exception {
		
		mockDS();
		setRefTypeEventList();
		
		// Change secret question
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question test");
		
		List<RefDetailsKeyDTO> refDetailsKeyList = new ArrayList<RefDetailsKeyDTO>();
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.STATUS.toString(), KeySecurityEventEnum.STATUS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.IP_ADDRESS.toString(), KeySecurityEventEnum.IP_ADDRESS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.LAST_SECRET_ANSW_MODIFICATION_DATE.toString(), KeySecurityEventEnum.LAST_SECRET_ANSW_MODIFICATION_DATE.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.NB_FAILURE_SECRET_QUESTION_ANS.toString(), KeySecurityEventEnum.NB_FAILURE_SECRET_QUESTION_ANS.getKey()));

        expect(detailsKeyDS.findByTypeEvent(EasyMock.anyObject(RefTypeEvent.class))).andReturn(refDetailsKeyList);
		EasyMock.expectLastCall().once();
        EasyMock.replay(detailsKeyDS);
        
		EventBlock event = sendNotifySecurityEventTask.getEventBlock(triggerChangeIndividusDTO);
        
		assertTrue(event.getType().equals(EventTypeEnum.SECRET_QUESTION_UPDATE.toString()));
		assertTrue(event.getEventDetails().size() == 4);
		
        for (int i = 0; i < refDetailsKeyList.size(); i++) {
        	assertTrue(event.getEventDetails().get(i).getKey().equals(refDetailsKeyList.get(i).getCode()));
		}
        
	}
	
	@Test
	public void testSendNotifySecurityEventTask_getEventBlockSecretAnswerUpdate() throws Exception {
		
		mockDS();
		setRefTypeEventList();
		
		// Change answer secret question
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer test", "secret question");
		
		List<RefDetailsKeyDTO> refDetailsKeyList = new ArrayList<RefDetailsKeyDTO>();
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.STATUS.toString(), KeySecurityEventEnum.STATUS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.IP_ADDRESS.toString(), KeySecurityEventEnum.IP_ADDRESS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.LAST_SECRET_ANSW_MODIFICATION_DATE.toString(), KeySecurityEventEnum.LAST_SECRET_ANSW_MODIFICATION_DATE.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.NB_FAILURE_SECRET_QUESTION_ANS.toString(), KeySecurityEventEnum.NB_FAILURE_SECRET_QUESTION_ANS.getKey()));

        expect(detailsKeyDS.findByTypeEvent(EasyMock.anyObject(RefTypeEvent.class))).andReturn(refDetailsKeyList);
		EasyMock.expectLastCall().once();
        EasyMock.replay(detailsKeyDS);
        
		EventBlock event = sendNotifySecurityEventTask.getEventBlock(triggerChangeIndividusDTO);
        
		assertTrue(event.getType().equals(EventTypeEnum.SECRET_ANSWER_UPDATE.toString()));
		assertTrue(event.getEventDetails().size() == 4);
		
        for (int i = 0; i < refDetailsKeyList.size(); i++) {
        	assertTrue(event.getEventDetails().get(i).getKey().equals(refDetailsKeyList.get(i).getCode()));
		}
        
	}
	
	@Test
	public void testSendNotifySecurityEventTask_getEventBlockSecretQuestionAndAnswerUpdate() throws Exception {
		
		mockDS();
		setRefTypeEventList();
		
		// Change secret question and answer
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer test", "secret question test");
		
		List<RefDetailsKeyDTO> refDetailsKeyList = new ArrayList<RefDetailsKeyDTO>();
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.STATUS.toString(), KeySecurityEventEnum.STATUS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.IP_ADDRESS.toString(), KeySecurityEventEnum.IP_ADDRESS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.LAST_SECRET_ANSW_MODIFICATION_DATE.toString(), KeySecurityEventEnum.LAST_SECRET_ANSW_MODIFICATION_DATE.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.NB_FAILURE_SECRET_QUESTION_ANS.toString(), KeySecurityEventEnum.NB_FAILURE_SECRET_QUESTION_ANS.getKey()));

        expect(detailsKeyDS.findByTypeEvent(EasyMock.anyObject(RefTypeEvent.class))).andReturn(refDetailsKeyList);
		EasyMock.expectLastCall().once();
        EasyMock.replay(detailsKeyDS);
        
		EventBlock event = sendNotifySecurityEventTask.getEventBlock(triggerChangeIndividusDTO);
        
		assertTrue(event.getType().equals(EventTypeEnum.SECRET_QUESTION_AND_ANSWER_UPDATE.toString()));
		assertTrue(event.getEventDetails().size() == 4);
		
        for (int i = 0; i < refDetailsKeyList.size(); i++) {
        	assertTrue(event.getEventDetails().get(i).getKey().equals(refDetailsKeyList.get(i).getCode()));
		}
        
	}
	
	@Test
	public void testSendNotifySecurityEventTask_getEventBlockDisabledAccount() throws Exception {
		
		mockDS();
		setRefTypeEventList();
		
		// Change status
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("D", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		
		List<RefDetailsKeyDTO> refDetailsKeyList = new ArrayList<RefDetailsKeyDTO>();
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.STATUS.toString(), KeySecurityEventEnum.STATUS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.IP_ADDRESS.toString(), KeySecurityEventEnum.IP_ADDRESS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.NB_FAILURE_AUTHENTIFICATION.toString(), KeySecurityEventEnum.NB_FAILURE_AUTHENTIFICATION.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.NB_FAILURE_SECRET_QUESTION_ANS.toString(), KeySecurityEventEnum.NB_FAILURE_SECRET_QUESTION_ANS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.ACCOUNT_LOCKED_DATE.toString(), KeySecurityEventEnum.ACCOUNT_LOCKED_DATE.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.PASSWORD_TO_CHANGE.toString(), KeySecurityEventEnum.PASSWORD_TO_CHANGE.getKey()));

        expect(detailsKeyDS.findByTypeEvent(EasyMock.anyObject(RefTypeEvent.class))).andReturn(refDetailsKeyList);
		EasyMock.expectLastCall().once();
        EasyMock.replay(detailsKeyDS);
        
		EventBlock event = sendNotifySecurityEventTask.getEventBlock(triggerChangeIndividusDTO);
        
		assertTrue(event.getType().equals(EventTypeEnum.DISABLED_ACCOUNT.toString()));
		assertTrue(event.getEventDetails().size() == 6);
		
        for (int i = 0; i < refDetailsKeyList.size(); i++) {
        	assertTrue(event.getEventDetails().get(i).getKey().equals(refDetailsKeyList.get(i).getCode()));
		}
        
	}
	
	@Test
	public void testSendNotifySecurityEventTask_getEventBlockAccountLocked() throws Exception {
		
		mockDS();
		setRefTypeEventList();
		
		// Change account locked date
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "13/03/2017", "18/01/2017", "password", "secret question answer", "secret question");
		
		List<RefDetailsKeyDTO> refDetailsKeyList = new ArrayList<RefDetailsKeyDTO>();
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.STATUS.toString(), KeySecurityEventEnum.STATUS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.IP_ADDRESS.toString(), KeySecurityEventEnum.IP_ADDRESS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.NB_FAILURE_AUTHENTIFICATION.toString(), KeySecurityEventEnum.NB_FAILURE_AUTHENTIFICATION.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.NB_FAILURE_SECRET_QUESTION_ANS.toString(), KeySecurityEventEnum.NB_FAILURE_SECRET_QUESTION_ANS.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.ACCOUNT_LOCKED_DATE.toString(), KeySecurityEventEnum.ACCOUNT_LOCKED_DATE.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.PASSWORD_TO_CHANGE.toString(), KeySecurityEventEnum.PASSWORD_TO_CHANGE.getKey()));
		refDetailsKeyList.add(createRefDetailsKey(KeySecurityEventEnum.TEMPORARY_PWD_END_DATE.toString(), KeySecurityEventEnum.TEMPORARY_PWD_END_DATE.getKey()));

        expect(detailsKeyDS.findByTypeEvent(EasyMock.anyObject(RefTypeEvent.class))).andReturn(refDetailsKeyList);
		EasyMock.expectLastCall().once();
        EasyMock.replay(detailsKeyDS);
        
		EventBlock event = sendNotifySecurityEventTask.getEventBlock(triggerChangeIndividusDTO);
        
		assertTrue(event.getType().equals(EventTypeEnum.ACCOUNT_LOCKED.toString()));
		assertTrue(event.getEventDetails().size() == 6);
		
        for (int i = 0; i < refDetailsKeyList.size() - 1; i++) {
        	assertTrue(event.getEventDetails().get(i).getKey().equals(refDetailsKeyList.get(i).getCode()));
		}
        
	}

	/* ********************************************************************************************
	 * 
	 * UNIT TESTS ON GETTYPEEVENT METHOD
	 * 
	 ******************************************************************************************* */
	@Test
	public void testSendNotifySecurityEventTask_getTypeEventPasswordChange() {

		// Change password
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "10/11/2012", "18/01/2017", "password changed", "secret question answer", "secret question");
        
        String[] valuesBefore = triggerChangeIndividusDTO.getChangeBefore().split("\\|");
        String[] valuesAfter = triggerChangeIndividusDTO.getChangeAfter().split("\\|");
		
		String type = sendNotifySecurityEventTask.getTypeEvent(valuesBefore, valuesAfter);
		assertTrue(type.equals(EventTypeEnum.PASSWORD_CHANGE.toString()));
	}
	
	@Test
	public void testSendNotifySecurityEventTask_getTypeEventPasswordReset() {

		// Reset password
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "10/03/2017", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
        
        String[] valuesBefore = triggerChangeIndividusDTO.getChangeBefore().split("\\|");
        String[] valuesAfter = triggerChangeIndividusDTO.getChangeAfter().split("\\|");
		
		String type = sendNotifySecurityEventTask.getTypeEvent(valuesBefore, valuesAfter);
		assertTrue(type.equals(EventTypeEnum.PASSWORD_RESET.toString()));
	}
	
	@Test
	public void testSendNotifySecurityEventTask_getTypeEventAccountLocked() {

		// Account Locked
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "10/03/2017", "18/01/2017", "password", "secret question answer", "secret question");
        
        String[] valuesBefore = triggerChangeIndividusDTO.getChangeBefore().split("\\|");
        String[] valuesAfter = triggerChangeIndividusDTO.getChangeAfter().split("\\|");
		
		String type = sendNotifySecurityEventTask.getTypeEvent(valuesBefore, valuesAfter);
		assertTrue(type.equals(EventTypeEnum.ACCOUNT_LOCKED.toString()));
	}
	
	@Test
	public void testSendNotifySecurityEventTask_getTypeEventDisabledAccount() {

		// Disable account
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("D", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
        
        String[] valuesBefore = triggerChangeIndividusDTO.getChangeBefore().split("\\|");
        String[] valuesAfter = triggerChangeIndividusDTO.getChangeAfter().split("\\|");
		
		String type = sendNotifySecurityEventTask.getTypeEvent(valuesBefore, valuesAfter);
		assertTrue(type.equals(EventTypeEnum.DISABLED_ACCOUNT.toString()));
	}
	
	@Test
	public void testSendNotifySecurityEventTask_getTypeEventSecretQuestionAndAnswerUpdate() {

		// Secret question and answer update
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "10/11/2012", "10/01/2017", "password", "secret question answer changed", "secret question changed");
        
        String[] valuesBefore = triggerChangeIndividusDTO.getChangeBefore().split("\\|");
        String[] valuesAfter = triggerChangeIndividusDTO.getChangeAfter().split("\\|");
		
		String type = sendNotifySecurityEventTask.getTypeEvent(valuesBefore, valuesAfter);
		assertTrue(type.equals(EventTypeEnum.SECRET_QUESTION_AND_ANSWER_UPDATE.toString()));
	}
	
	@Test
	public void testSendNotifySecurityEventTask_getTypeEventSecretQuestionUpdate() {

		// Secret question update
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "10/11/2012", "10/01/2017", "password", "secret question answer", "secret question changed");
        
        String[] valuesBefore = triggerChangeIndividusDTO.getChangeBefore().split("\\|");
        String[] valuesAfter = triggerChangeIndividusDTO.getChangeAfter().split("\\|");
		
		String type = sendNotifySecurityEventTask.getTypeEvent(valuesBefore, valuesAfter);
		assertTrue(type.equals(EventTypeEnum.SECRET_QUESTION_UPDATE.toString()));
	}
	
	@Test
	public void testSendNotifySecurityEventTask_getTypeEventSecretAnswerUpdate() {

		// Secret question answer update
		// 													ORIGINAL VALUE 				  ("V", "17/10/2013", "10/11/2012", "18/01/2017", "password", "secret question answer", "secret question");
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = createTriggerChangeIndividus("V", "17/10/2013", "10/11/2012", "10/01/2017", "password", "secret question answer changed", "secret question");
        
        String[] valuesBefore = triggerChangeIndividusDTO.getChangeBefore().split("\\|");
        String[] valuesAfter = triggerChangeIndividusDTO.getChangeAfter().split("\\|");
		
		String type = sendNotifySecurityEventTask.getTypeEvent(valuesBefore, valuesAfter);
		assertTrue(type.equals(EventTypeEnum.SECRET_ANSWER_UPDATE.toString()));
	}
	

	/* ********************************************************************************************
	 * 
	 * HELPER METHOD FOR TEST
	 * 
	 ******************************************************************************************* */
	public RefTypeEventDTO createRefTypeEvent(String type) {

		RefTypeEventDTO refTypeEvent = new RefTypeEventDTO();
		refTypeEvent.setCode(type);
		
		return refTypeEvent;
		
	}
	
	public List<RefTypeEventDTO> setRefTypeEventList() {
		
		List<RefTypeEventDTO> refTypeEventList = new ArrayList<RefTypeEventDTO>();
		refTypeEventList.add(createRefTypeEvent(EventTypeEnum.PASSWORD_CHANGE.toString()));
		refTypeEventList.add(createRefTypeEvent(EventTypeEnum.PASSWORD_RESET.toString()));
		refTypeEventList.add(createRefTypeEvent(EventTypeEnum.SECRET_QUESTION_UPDATE.toString()));
		refTypeEventList.add(createRefTypeEvent(EventTypeEnum.SECRET_ANSWER_UPDATE.toString()));
		refTypeEventList.add(createRefTypeEvent(EventTypeEnum.SECRET_QUESTION_AND_ANSWER_UPDATE.toString()));
		refTypeEventList.add(createRefTypeEvent(EventTypeEnum.DISABLED_ACCOUNT.toString()));
		refTypeEventList.add(createRefTypeEvent(EventTypeEnum.ACCOUNT_LOCKED.toString()));

		sendNotifySecurityEventTask.setRefTypeEventList(refTypeEventList);
		
		return refTypeEventList;
		
	}
	
	public TriggerChangeIndividusDTO createTriggerChangeIndividus(String status, String lastPwdReset, String accountLockedDate, String lastSecretAnswerModification, String password, String secretQuestionAnswer, String secretQuestion) {

		String gin = "400351259264";
		
		TriggerChangeIndividusDTO triggerChangeIndividusDTO = new TriggerChangeIndividusDTO();
		triggerChangeIndividusDTO.setGin(gin);
		Date date = new Date();
		triggerChangeIndividusDTO.setDateChange(date);
		
		/* 
		 * STATUS|IP_ADDRESS|NB_FAILURE_AUTHENTIFICATION|PASSWORD_TO_CHANGE|LAST_PWD_RESET_DATE|TEMPORARY_PWD_END_DATE|LAST_SOCIAL_NETWORK_LOGON_DATE|LAST_SOCIAL_NETWORK_USED|LAST_SOCIAL_NETWORK_ID|LAST_CONNECTION_DATE|
		 * ACCOUNT_UPGRADE_DATE|ACCOUNT_LOCKED_DATE|ACCOUNT_DELETION_DATE|LAST_SECRET_ANSW_MODIFICATION|NB_FAILURE_SECRET_QUESTION_ANS|PASSWORD|TEMPORARY_PWD|SECRET_QUESTION_ANSWER|SECRET_QUESTION_OLD|SECRET_QUESTION|SECRET_QUESTION_ANSWER_UPPER|
		 * EMAIL_IDENTIFIER|DDATE_CREATION|SSITE_CREATION|SSIGNATURE_CREATION|DDATE_MODIFICATION|SSITE_MODIFICATION|SSIGNATURE_MODIFICATION
		*/
		triggerChangeIndividusDTO.setChangeBefore("V|168.192.0.1|0|0|17/10/2013|||||||10/11/2012||18/01/2017|0|password||secret question answer||secret question|SECRET QUESTION ANSWER|efauville59@orange.fr|31-MAY-11|SIC|MIGRATION|04-SEP-16|QVI|dallas");
		triggerChangeIndividusDTO.setChangeAfter(status +"|168.192.0.1|0|0|" + lastPwdReset +"|||||||" + accountLockedDate + "||" + lastSecretAnswerModification + "|0|" + password + "||" + secretQuestionAnswer + "||" + secretQuestion + "|SECRET QUESTION ANSWER|efauville59@orange.fr|31-MAY-11|SIC|MIGRATION|04-SEP-16|QVI|dallas");
		return triggerChangeIndividusDTO;
	}
	
	public RefDetailsKeyDTO createRefDetailsKey(String code, Integer id) {
		
		RefDetailsKeyDTO refDetailsKey = new RefDetailsKeyDTO();
		refDetailsKey.setCode(code);
		refDetailsKey.setDetailsKeyID(id);
		
		return refDetailsKey;
		
	}

    public void mockDS() {
    	
    	batchIndividualUpdateNotification.setTriggerChangeIndividusDS(triggerChangeIndividusDS);
		sendNotifySecurityEventTask.setDetailsKeyDS(detailsKeyDS);
		sendNotifySecurityEventTask.setTypeEventDS(typeEventDS);
        
    }
}
