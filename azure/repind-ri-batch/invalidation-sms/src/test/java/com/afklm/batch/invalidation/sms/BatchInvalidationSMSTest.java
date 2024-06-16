package com.afklm.batch.invalidation.sms;

import com.airfrance.common.batch.BatchCommon;
import com.airfrance.common.batch.config.WebConfigTestBatch;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.reference.internal.RefCodeInvalPhoneDS;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebConfigTestBatch.class)
@Transactional(value = "transactionManagerRepind")
public class BatchInvalidationSMSTest {
	
	//@Autowired
    BatchInvalidationSMS batchInvalidationSMS;
    
    @Autowired
    private TelecomDS telecomDS;

	@Autowired
    private RefCodeInvalPhoneDS refCodeInvalPhoneDS;
    
    private String dir = BatchCommon.getDir(BatchInvalidationSMSTest.class);
    /**
     * Preparer le Test : recuperer le bean du batch via le contexte.
     * @throws IOException : exception fichier de context.
     */
    @Before
    public void setUp() throws IOException {
    	
    	batchInvalidationSMS = new BatchInvalidationSMS();
    	batchInvalidationSMS.setTelecomDS(telecomDS);
    	batchInvalidationSMS.setRefCodeInvalPhoneDS(refCodeInvalPhoneDS);
    	
        // recuperation du batch (bean au sens context)
        batchInvalidationSMS.setTrace(true);
        batchInvalidationSMS.setLocal(true);
        batchInvalidationSMS.setLocalPath(dir);
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
    public void testCodeErrorEmpty() throws  JrafDomainException {
  
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchInvalidationErrorCodeEmpty_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertEquals(batchInvalidationSMS.getInvalErrorCodeEmptyNb(), 1);
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testCodeErrorTooLong() throws  JrafDomainException {
       
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchInvalidationErrorCodeTooLong_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertEquals(batchInvalidationSMS.getCheckFileFormat().getInvalErrorCodeTooLongNb(), 1);
    }
    
    @Test
    public void testCasComplet() throws  JrafDomainException {
       
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchInvalidationCasComplet_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
    }
    
    
    @Test
    @Ignore // Cela prend plus de 30 secondes
    public void testCodeErrorTooShort() throws  JrafDomainException {
       
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchInvalidationErrorCodeTooShort_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        assertEquals(batchInvalidationSMS.getCheckFileFormat().getInvalErrorCodeTooShortNb(), 1);
    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testActionValidation() throws  JrafDomainException {
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchInvalidationAction_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        assertEquals(batchInvalidationSMS.getNbOK(), 1);
        assertEquals(batchInvalidationSMS.getNbKO(), 3);

    }
    
    @Test
    public void testContactTypeValidation() throws  JrafDomainException {
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchInvalidationContactType_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        assertEquals(batchInvalidationSMS.getNbOK(), 1);
        assertEquals(batchInvalidationSMS.getNbKO(), 1);

    }
    
    @Test
    public void testContactValidation() throws  JrafDomainException {
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchInvalidationContact_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        assertEquals(batchInvalidationSMS.getNbOK(), 1);
        assertEquals(batchInvalidationSMS.getNbKO(), 1);

    }
    
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testGINValidation() throws  JrafDomainException {
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchInvalidationGIN_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        assertEquals(batchInvalidationSMS.getNbOK(), 1);
        assertEquals(batchInvalidationSMS.getNbKO(), 2);

    }
    
    @Test
    public void testFBIndentifierValidation() throws  JrafDomainException {
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchInvalidationFbIdentifier_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        assertEquals(batchInvalidationSMS.getNbOK(), 1);
        assertEquals(batchInvalidationSMS.getNbKO(), 1);

    }
    
    @Test
    public void testAccountIdentifierValidation() throws  JrafDomainException {
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchInvalidationAccountIdentifier_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        assertEquals(batchInvalidationSMS.getNbOK(), 1);
        assertEquals(batchInvalidationSMS.getNbKO(), 1);

    }
    
    @Test
    public void testCauseValidation() throws  JrafDomainException {
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchInvalidationCause_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        assertEquals(batchInvalidationSMS.getNbOK(), 1);
        assertEquals(batchInvalidationSMS.getNbKO(), 1);

    }
    
    @Test
    public void testOK() throws  JrafDomainException {
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchOK_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        assertEquals(batchInvalidationSMS.getNbOK(), 2);
        assertEquals(batchInvalidationSMS.getNbKO(), 0);

    }
    
    @Test
    public void testOKCRM() throws  JrafDomainException {
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchOKCRM_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        assertEquals(batchInvalidationSMS.getNbOK(), 8);
        assertEquals(batchInvalidationSMS.getNbKO(), 0);

    }
    
    @Test
    public void testErrorCodeBad() throws  JrafDomainException {
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchInvalidationErrorCodeBadFormat_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        assertEquals(batchInvalidationSMS.getNbOK(), 0);
        assertEquals(batchInvalidationSMS.getNbKO(), 1);

    }
    
    @Test
    public void testTotal() throws  JrafDomainException {
        try{
            batchInvalidationSMS.setCurrentFileTraited(dir + "BatchInvalidationErrorCodeBadFormat_20140807121259");
            batchInvalidationSMS.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        int total = batchInvalidationSMS.getNbProcess();
        total += batchInvalidationSMS.getNbCodeNotValid();
        total += batchInvalidationSMS.getNbNotFound();
        total += batchInvalidationSMS.getNbNotCheck();
        total += batchInvalidationSMS.getNbBD();
        
        assertEquals(batchInvalidationSMS.getTotal(), total);

    }
  

}
