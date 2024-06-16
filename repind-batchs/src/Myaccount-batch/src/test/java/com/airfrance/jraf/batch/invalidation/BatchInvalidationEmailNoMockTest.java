package com.airfrance.jraf.batch.invalidation;

import com.airfrance.jraf.batch.common.BatchCommon;
import com.airfrance.jraf.batch.config.WebConfigTestBatch;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import org.junit.After;
import org.junit.Before;
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
public class BatchInvalidationEmailNoMockTest {
	
	@Autowired
    BatchInvalidationEmail batchInvalidationEmail;
    
    private String dir = BatchCommon.getDir(BatchInvalidationEmailNoMockTest.class);
    
    /**
     * Preparer le Test : recuperer le bean du batch via le contexte.
     * @throws IOException : exception fichier de context.
     */
    @Before
    public void setUp() throws IOException {
        // recuperation du batch (bean au sens context)
        batchInvalidationEmail.setTrace(true);
        batchInvalidationEmail.setLocal(true);
        batchInvalidationEmail.setForce(true);
        batchInvalidationEmail.setLocalPath(dir);
        
/*        emailDS = createMock(IEmailDS.class);
        individuDS = createMock(IIndividuDS.class);
        communicationPreferencesDS = createMock(ICommunicationPreferencesDS.class);
        
        batchInvalidationEmail.setEmailDS(emailDS);
        batchInvalidationEmail.setIndividuDS(individuDS);
        batchInvalidationEmail.setCommunicationPreferencesDS(communicationPreferencesDS);
*/        
    }

    /**
     * Fin du test.
     */
    @After
    public void tearDown() {
    }


    @Test
    @Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testBatchInvalitationEmail_IM02718939() throws  JrafDomainException {
  
        try{
        	batchInvalidationEmail.setCurrentFileTraited(dir + "BatchInvalidationEmail_IM02718939.txt");
            batchInvalidationEmail.setSignature("FBSP");
        	
        	batchInvalidationEmail.execute();
        }
        catch (Exception e) {
            assertNull(e);
        }
        
        assertEquals(68, batchInvalidationEmail.getNbInvalidationAlreadyTreatedForIndivdiduals());
    }
}
