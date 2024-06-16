package com.afklm.batch.invalidation.myaccount.email;

import com.airfrance.common.batch.config.WebConfigTestBatch;
import com.airfrance.ref.batch.BatchTest;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebConfigTestBatch.class)
@Transactional(value = "transactionManagerRepind")
@Ignore	// Exception in thread "main" java.lang.OutOfMemoryError: PermGen space ?
public class BatchInvalidationEmailTest extends BatchTest {
    private static final String currentBean = "batchInvalidationEmail";
    BatchInvalidationEmail batchInvalidationEmail;
    
    private String dir = getDir(BatchInvalidationEmailTest.class);
    
    private EmailDS emailDS;
    
    private IndividuDS individuDS;
    
    private CommunicationPreferencesDS communicationPreferencesDS;
    /**
     * Preparer le Test : recuperer le bean du batch via le contexte.
     * @throws IOException : exception fichier de context.
     */
    @Before
    public void setUp() throws IOException {
        // recuperation du batch (bean au sens context)
        batchInvalidationEmail = (BatchInvalidationEmail) getContext().getBean(currentBean);
        batchInvalidationEmail.setTrace(true);
        batchInvalidationEmail.setLocal(true);
        batchInvalidationEmail.setForce(true);
        batchInvalidationEmail.setLocalPath("/tmp/");
        
        emailDS = createMock(EmailDS.class);
        individuDS = createMock(IndividuDS.class);
        communicationPreferencesDS = createMock(CommunicationPreferencesDS.class);
        
        batchInvalidationEmail.setEmailDS(emailDS);
        batchInvalidationEmail.setIndividuDS(individuDS);
        batchInvalidationEmail.setCommunicationPreferencesDS(communicationPreferencesDS);
        
    }

    /**
     * Fin du test.
     */
    @After
    public void tearDown() {
    }

    /**
     * Methode de test.
     * @throws JrafDomainException
     */
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailWrongApplicationCode() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailErrorApplicationCode_20170830121259");
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(!batchInvalidationEmail.isOk());
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailWrongActionCode() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailErrorActionCode_20170830121259");
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(!batchInvalidationEmail.isOk());
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailWrongCommunicationCode() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailErrorCommunicationCode_20170830121259");
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(!batchInvalidationEmail.isOk());
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailWrongContactCode() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailErrorContactCode_20170830121259");
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(!batchInvalidationEmail.isOk());
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailWrongDomainComTypeCGType() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailErrorDomainComTypeCGType_20170830121259");
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(!batchInvalidationEmail.isOk());
    }

    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailWrongMarket() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailErrorMarketCode_20170830121259");
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(!batchInvalidationEmail.isOk());
    }

    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailWrongLanguage() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailErrorLanguageCode_20170830121259");
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(!batchInvalidationEmail.isOk());
    }

    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailWrongGin() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailErrorGin_20170830121259");
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(!batchInvalidationEmail.isOk());
    }

    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailWrongFBIdentifier() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailErrorFBIdentifier_20170830121259");
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(!batchInvalidationEmail.isOk());
    }

    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailWrongAccountIdentifier() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailErrorAccountIdentifier_20170830121259");
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(!batchInvalidationEmail.isOk());
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailSendFailure() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailSendFailure_20170830121259");
            batchInvalidationEmail.setSignature("FBSP");

        	IndividuDTO indDTO = new IndividuDTO();
        	indDTO.setSgin("400309336975");
        	indDTO.setType("I");
        	
        	IndividuDTO indDTO2 = new IndividuDTO();
        	indDTO2.setSgin("400309336978");
        	indDTO2.setType("W");
        	
        	EmailDTO emailDTO = new EmailDTO();
        	emailDTO.setStatutMedium("V");
        	emailDTO.setSgin("400309336975");
        	
        	EmailDTO emailDTO2 = new EmailDTO();
        	emailDTO2.setStatutMedium("V");
        	emailDTO2.setSgin("400309336978");
        	
        	EmailDTO emailDTO3 = new EmailDTO();
        	emailDTO3.setStatutMedium("X");
        	emailDTO3.setSgin("400309336980");
        	
        	List<EmailDTO> listEmailDTO = new ArrayList<EmailDTO>();
        	listEmailDTO.add(emailDTO);
        	listEmailDTO.add(emailDTO2);
        	
        	EasyMock.expect(individuDS.getByGin("400309336975")).andReturn(indDTO);        	
        	EasyMock.expect(individuDS.getByGin("400309336978")).andReturn(indDTO2);
        	EasyMock.replay(individuDS);
        	
        	EasyMock.expect(emailDS.search("jmrtaylor@aol.com")).andReturn(listEmailDTO);
        	
        	emailDS.invalidOnEmail(emailDTO, "InvEmail FBSP 3");
        	EasyMock.expectLastCall().once();
        	emailDS.invalidOnEmail(emailDTO2, "InvEmail FBSP 3");
        	EasyMock.expectLastCall().once();
        	
        	EasyMock.replay(emailDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(batchInvalidationEmail.isOk());
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsHardBounceForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsSendFailureForIndividuals(), 1);
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsSendFailureForProspects(), 1);
        assertEquals(batchInvalidationEmail.getNbInvalidEmailsSendFailureForIndividuals(), 1);
        assertEquals(batchInvalidationEmail.getNbInvalidEmailsSendFailureForProspects(), 1);
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailHardBounce() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailHardBounce_20170830121259");
            batchInvalidationEmail.setSignature("FBSP");

        	IndividuDTO indDTO = new IndividuDTO();
        	indDTO.setSgin("400309336975");
        	indDTO.setType("I");
        	
        	IndividuDTO indDTO2 = new IndividuDTO();
        	indDTO2.setSgin("400309336978");
        	indDTO2.setType("W");
        	
        	EmailDTO emailDTO = new EmailDTO();
        	emailDTO.setStatutMedium("V");
        	emailDTO.setSgin("400309336975");
        	
        	EmailDTO emailDTO2 = new EmailDTO();
        	emailDTO2.setStatutMedium("V");
        	emailDTO2.setSgin("400309336978");
        	
        	EmailDTO emailDTO3 = new EmailDTO();
        	emailDTO3.setStatutMedium("X");
        	emailDTO3.setSgin("400309336980");
        	
        	List<EmailDTO> listEmailDTO = new ArrayList<EmailDTO>();
        	listEmailDTO.add(emailDTO);
        	listEmailDTO.add(emailDTO2);
        	
        	EasyMock.expect(individuDS.getByGin("400309336975")).andReturn(indDTO);        	
        	EasyMock.expect(individuDS.getByGin("400309336978")).andReturn(indDTO2);
        	EasyMock.replay(individuDS);
        	
        	EasyMock.expect(emailDS.search("jmrtaylor@aol.com")).andReturn(listEmailDTO);
        	
        	emailDS.invalidOnEmail(emailDTO, "InvEmail FBSP 6");
        	EasyMock.expectLastCall().once();
        	emailDS.invalidOnEmail(emailDTO2, "InvEmail FBSP 6");
        	EasyMock.expectLastCall().once();
        	
        	EasyMock.replay(emailDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(batchInvalidationEmail.isOk());
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsSendFailureForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsHardBounceForIndividuals(), 1);
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsHardBounceForProspects(), 1);
        assertEquals(batchInvalidationEmail.getNbInvalidEmailsHardBounceForIndividuals(), 1);
        assertEquals(batchInvalidationEmail.getNbInvalidEmailsHardBounceForProspects(), 1);
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailUnsubscribeIndividual() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailUnsubscribe_20170830121259");
        	
        	IndividuDTO indDTO = new IndividuDTO();
        	indDTO.setSgin("400309336975");
        	indDTO.setType("I");
        	
        	EasyMock.expect(individuDS.getByGin("400309336975")).andReturn(indDTO);
        	EasyMock.replay(individuDS);

        	EasyMock.expect(communicationPreferencesDS.unsubscribeMarketLanguage("400309336975", "S", "N", "AF", "FR", "FR")).andReturn(8);
        	EasyMock.replay(communicationPreferencesDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(batchInvalidationEmail.isOk());
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeCommunicationPreferenceForProspects(), 0);
        assertEquals(batchInvalidationEmail.getNbUnsubscribeMarketLanguageForIndividuals(), 8);
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeMarketLanguageForIndividuals(), 8);
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailUnsubscribeProspect() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailUnsubscribe_20170830121259");
        	
        	IndividuDTO indDTO = new IndividuDTO();
        	indDTO.setSgin("400309336975");
        	indDTO.setType("W");
        	
        	EasyMock.expect(individuDS.getByGin("400309336975")).andReturn(indDTO);
        	EasyMock.replay(individuDS);

        	EasyMock.expect(communicationPreferencesDS.unsubscribeMarketLanguage("400309336975", "S", "N", "AF", "FR", "FR")).andReturn(4);
        	EasyMock.replay(communicationPreferencesDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(batchInvalidationEmail.isOk());
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeCommunicationPreferenceForProspects(), 0);
        assertEquals(batchInvalidationEmail.getNbUnsubscribeMarketLanguageForIndividuals(), 4);
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeMarketLanguageForProspects(), 4);
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailSpamIndividual() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailSpam_20170830121259");
        	
        	IndividuDTO indDTO = new IndividuDTO();
        	indDTO.setSgin("400309336975");
        	indDTO.setType("I");
        	
        	EasyMock.expect(individuDS.getByGin("400309336975")).andReturn(indDTO);
        	EasyMock.replay(individuDS);
        	
        	EasyMock.expect(communicationPreferencesDS.unsubscribeCommPref("400309336975", "S", "N", "AF")).andReturn(3);
        	EasyMock.replay(communicationPreferencesDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(batchInvalidationEmail.isOk());
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeMarketLanguageForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getNbUnsubscribeCommunicationPreferenceForIndividuals(), 3);
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeCommunicationPreferenceForIndividuals(), 3);
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailSpamProspect() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailSpam_20170830121259");

        	IndividuDTO indDTO = new IndividuDTO();
        	indDTO.setSgin("400309336975");
        	indDTO.setType("W");
        	
        	EasyMock.expect(individuDS.getByGin("400309336975")).andReturn(indDTO);
        	EasyMock.replay(individuDS);
        	
        	EasyMock.expect(communicationPreferencesDS.unsubscribeCommPref("400309336975", "S", "N", "AF")).andReturn(5);
        	EasyMock.replay(communicationPreferencesDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(batchInvalidationEmail.isOk());
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeMarketLanguageForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getNbUnsubscribeCommunicationPreferenceForIndividuals(), 5);
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeCommunicationPreferenceForProspects(), 5);
    }
    

    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationErrorEmailInvalidation() throws  JrafDomainException {
    	
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailSendFailure_20170830121259");
            batchInvalidationEmail.setSignature("FBSP");
        	
        	EasyMock.expect(emailDS.search("jmrtaylor@aol.com")).andReturn(null);
        	EasyMock.replay(emailDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertEquals(batchInvalidationEmail.getNbInvalidationNotTreated(), 1);
    }

    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailSpamNoneComPrefIndividual() throws  JrafDomainException {
    	
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailSpam_20170830121259");

        	IndividuDTO indDTO = new IndividuDTO();
        	indDTO.setSgin("400309336975");
        	indDTO.setType("I");
        	
        	EasyMock.expect(individuDS.getByGin("400309336975")).andReturn(indDTO);
        	EasyMock.replay(individuDS);
        	
        	EasyMock.expect(communicationPreferencesDS.unsubscribeCommPref("400309336975", "S", "N", "AF")).andReturn(0);
        	EasyMock.replay(communicationPreferencesDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(batchInvalidationEmail.isOk());
        assertEquals(batchInvalidationEmail.getNbSpamAlreadyTreatedForIndividuals(), 1);
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeMarketLanguageForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getNbUnsubscribeCommunicationPreferenceForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeCommunicationPreferenceForProspects(), 0);
    }
    

    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailSpamNoneComPrefProspect() throws  JrafDomainException {
    	
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailSpam_20170830121259");

        	IndividuDTO indDTO = new IndividuDTO();
        	indDTO.setSgin("400309336975");
        	indDTO.setType("W");
        	
        	EasyMock.expect(individuDS.getByGin("400309336975")).andReturn(indDTO);
        	EasyMock.replay(individuDS);
        	
        	EasyMock.expect(communicationPreferencesDS.unsubscribeCommPref("400309336975", "S", "N", "AF")).andReturn(0);
        	EasyMock.replay(communicationPreferencesDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(batchInvalidationEmail.isOk());
        assertEquals(batchInvalidationEmail.getNbSpamAlreadyTreatedForProspects(), 1);
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeMarketLanguageForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getNbUnsubscribeCommunicationPreferenceForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeCommunicationPreferenceForProspects(), 0);
    }
    

    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailUnsubscribeNoneMLIndividual() throws  JrafDomainException {
    	
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailUnsubscribe_20170830121259");

        	IndividuDTO indDTO = new IndividuDTO();
        	indDTO.setSgin("400309336975");
        	indDTO.setType("I");
        	
        	EasyMock.expect(individuDS.getByGin("400309336975")).andReturn(indDTO);
        	EasyMock.replay(individuDS);

        	EasyMock.expect(communicationPreferencesDS.unsubscribeMarketLanguage("400309336975", "S", "N", "AF", "FR", "FR")).andReturn(0);
        	EasyMock.replay(communicationPreferencesDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(batchInvalidationEmail.isOk());
        assertEquals(batchInvalidationEmail.getNbUnsubscribeAlreadyTreatedForIndividuals(), 1);
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeCommunicationPreferenceForProspects(), 0);
        assertEquals(batchInvalidationEmail.getNbUnsubscribeMarketLanguageForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeMarketLanguageForProspects(), 0);
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailUnsubscribeNoneMLProspect() throws  JrafDomainException {
    	
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailUnsubscribe_20170830121259");

        	IndividuDTO indDTO = new IndividuDTO();
        	indDTO.setSgin("400309336975");
        	indDTO.setType("W");
        	
        	EasyMock.expect(individuDS.getByGin("400309336975")).andReturn(indDTO);
        	EasyMock.replay(individuDS);

        	EasyMock.expect(communicationPreferencesDS.unsubscribeMarketLanguage("400309336975", "S", "N", "AF", "FR", "FR")).andReturn(0);
        	EasyMock.replay(communicationPreferencesDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(batchInvalidationEmail.isOk());
        assertEquals(batchInvalidationEmail.getNbUnsubscribeAlreadyTreatedForProspects(), 1);
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeCommunicationPreferenceForProspects(), 0);
        assertEquals(batchInvalidationEmail.getNbUnsubscribeMarketLanguageForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getTotalUnsubscribeMarketLanguageForProspects(), 0);
    }
    

    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailSendFailureNone() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailSendFailure_20170830121259");
            batchInvalidationEmail.setSignature("FBSP");
            
        	List<EmailDTO> listEmailDTO = new ArrayList<EmailDTO>();
        	EasyMock.expect(emailDS.search("jmrtaylor@aol.com")).andReturn(listEmailDTO);
        	EasyMock.replay(emailDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(batchInvalidationEmail.isOk());
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsHardBounceForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsSendFailureForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsSendFailureForProspects(), 0);
        assertEquals(batchInvalidationEmail.getNbInvalidEmailsSendFailureForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getNbInvalidEmailsSendFailureForProspects(), 0);
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmailHardBounceNone() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmailHardBounce_20170830121259");
            batchInvalidationEmail.setSignature("FBSP");
        	
        	List<EmailDTO> listEmailDTO = new ArrayList<EmailDTO>();
        	
        	EasyMock.expect(emailDS.search("jmrtaylor@aol.com")).andReturn(listEmailDTO);
        	EasyMock.replay(emailDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(batchInvalidationEmail.isOk());
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsSendFailureForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsHardBounceForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsHardBounceForProspects(), 0);
        assertEquals(batchInvalidationEmail.getNbInvalidEmailsHardBounceForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getNbInvalidEmailsHardBounceForProspects(), 0);
    }

    @Test
	// @Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmail_IM02718939() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmail_IM02718939.txt");
            batchInvalidationEmail.setSignature("FBSP");
        	
//        	List<EmailDTO> listEmailDTO = new ArrayList<EmailDTO>();
//        	EasyMock.expect(emailDS.search("jmrtaylor@aol.com")).andReturn(listEmailDTO);
//        	EasyMock.replay(emailDS);
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertTrue(batchInvalidationEmail.isOk());
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsSendFailureForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsHardBounceForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getTotalInvalidEmailsHardBounceForProspects(), 0);
        assertEquals(batchInvalidationEmail.getNbInvalidEmailsHardBounceForIndividuals(), 0);
        assertEquals(batchInvalidationEmail.getNbInvalidEmailsHardBounceForProspects(), 0);
    }
}
