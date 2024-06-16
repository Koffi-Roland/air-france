package com.airfrance.repind.service;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * <p>Title : EmailDSTest.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class EmailDSTest { // extends AbstractTransactionalSpringContextTests {

    /** logger */
    private static final Log logger = LogFactory.getLog(EmailDSTest.class);

    @Autowired
    private EmailDS emailDS;
    

    @Test
    public void testSearch() {
        try {
            List<EmailDTO> resultSeach = null;
            // transaction 1
            resultSeach = emailDS.search("lawson_sika@yahoo.fr");
            
            Iterator<EmailDTO> itr = resultSeach.iterator(); 
            while(itr.hasNext()) {

                EmailDTO element = itr.next(); 
                logger.info(element + " ");

            } 

        } catch (JrafDomainException e) {
            // fail("Erreur non prevue");
            Assert.fail();
            logger.error(e);

        }

    }
    
    @Test
    public void create(){
    	EmailDTO e = new EmailDTO();
    	
    	e.setCodeMedium("L");
    	e.setStatutMedium("V");
    	e.setEmail("test@test.de");
    	e.setAutorisationMailing("A");
    	e.setSiteCreation("QVI");
    	e.setSignatureCreation("test");
    	e.setDateCreation(new Date());
    	
    	try {
			emailDS.create(e);
			Assert.assertTrue(true);
		} catch (JrafDomainException e1) {
			e1.printStackTrace();
			Assert.fail();
		}
    }

}
