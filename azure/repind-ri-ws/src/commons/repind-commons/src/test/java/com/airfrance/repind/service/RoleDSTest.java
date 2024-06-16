/**
 * 
 */
package com.airfrance.repind.service;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.service.role.internal.RoleDS;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;


/**
 * <p>Title : RoleDSTest.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class RoleDSTest { // extends AbstractTransactionalSpringContextTests {

    /** logger */
    private static final Log logger =
        LogFactory.getLog(RoleDSTest.class);

    @Autowired
    RoleDS roleDS;
    
    /**
     * Retourne le chemin du fichier de configuration JRAF
     * 
     * @return chemin du fichier de configuration JRAF
     * @throws IOException
     */
    // specifies the Spring configuration to load for this test fixture
/*    protected String[] getConfigLocations() {
        return new String[] { "classpath:config/application-context-spring-test.xml" };
    }
*/
    
    /**
     * Constructor for RoleDSTest.
     * @param arg0 argument
     */
/*    public RoleDSTest(String arg0) {
        super(arg0);
        setDefaultRollback(false);
    }
*/
    /**
     * Test de methode Get
     *
     */
    @Test
    public void testGetRoleContratsDTO() {
        try {
        	RoleContratsDTO roleSearch = new RoleContratsDTO();
        	// roleSearch.setSrin("        32410235");
        	roleSearch.setSrin("        16130571");
        	RoleContratsDTO result = getRoleDS().get(roleSearch);
        	logger.info(result);
        	logger.info(result.getBusinessroledto());
        } catch (JrafDomainException e) {
            // fail("Erreur non prevue");
            Assert.fail();
            logger.error(e);

        }

    }
    
    /**
     * Test de methode Get
     *
     */
    @Test
    public void testcreateMyAccountContract() {
        try {
        	Date today = Calendar.getInstance().getTime();
        	SignatureDTO signature = new SignatureDTO();
        	signature.setSignature("T412211");
        	signature.setSite("QVI");
        	signature.setDate(today);
        	
        	
    		// On tente de generer une information Alleatoire afin de ne pas avoir le EXISTING INDIVIDUAL CONTRACT NUMBER
        	RandomStringUtils rsu = new RandomStringUtils();
        	@SuppressWarnings("static-access")
			String test = rsu.randomNumeric(6);

        	String result = getRoleDS().createMyAccountContract("400327611293", "TU" + test, "AF", signature);
        	// String result = getRoleDS().createMyAccountContract("400327611293", "XY123460", "AF", signature);
        	logger.info(result);
        } catch (JrafDomainException e) {
            logger.error(e);
            Assert.fail();
            // fail("Erreur non prevue");
        }

    }

	@Test(expected = Test.None.class /* no exception expected */)
	@Ignore
	public void finContractFPTest() throws JrafDomainException {
		RoleContratsDTO roleContract = roleDS.findRoleContratsFP("5C124515");
		if (roleContract != null) {
			logger.info(roleContract.getCleRole());
		}
		
	}

	@Test(expected = Test.None.class /* no exception expected */)
	@Ignore
	public void createContractFPTest() throws JrafDomainException {
		RoleContratsDTO roleContract = new RoleContratsDTO();
		SignatureDTO signature= new SignatureDTO();
		
		signature.setDate(new Date());
		signature.setSite("T02AF");
		signature.setSignature("AUTO");
		
		String numeroContrat= "070014847999";
		String gin="002001779823";
		roleContract.setTier("A");
		roleContract.setSoldeMiles(24275);
		roleContract.setMilesQualif(24275);
		roleContract.setSegmentsQualif(2425);
		
		roleContract.setGin(gin);
		roleContract.setDateCreation(signature.getDate());
		roleContract.setDateDebutValidite(signature.getDate());
		roleContract.setEtat("C");
		roleContract.setGin(gin);
		roleContract.setNumeroContrat(numeroContrat);
		roleContract.setSignatureCreation(signature.getSignature());
		roleContract.setSiteCreation(signature.getSite());
		roleContract.setTypeContrat("FP");
		
		BusinessRoleDTO businessRoleDTO = new BusinessRoleDTO();
		businessRoleDTO.setDateCreation(signature.getDate());
		businessRoleDTO.setSignatureCreation(signature.getSignature());
		businessRoleDTO.setSiteCreation(signature.getSite());
		businessRoleDTO.setNumeroContrat(numeroContrat);
		businessRoleDTO.setGinInd(gin);
		businessRoleDTO.setType("C");
//		(gin, roleContract, businessRole, signature);
		RoleContratsDTO  roleContracts = roleDS.createRoleContractFP(gin,roleContract, businessRoleDTO, signature);
		logger.info(roleContracts.getCleRole());
	
	}
	
    /**
     * Methode main
     * @param args arguments
     */
/*    public static void main(String[] args) {
        junit.textui.TestRunner.run(
            RoleDSTest.class);
    }
*/

    public RoleDS getRoleDS() {
        return roleDS;
    }


    public void setRoleDS(RoleDS roleDS) {
        this.roleDS = roleDS;
    }
}

