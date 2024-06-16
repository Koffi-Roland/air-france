package com.airfrance.repind.dao.role;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.repind.config.WebSicTestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSicTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class RoleContratsDAOTest {

    /**
     * AccountData DAO
     */
    @Autowired
    private RoleContratsRepository roleContratsRepository;
    
    @Test
    @Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testCountFrequencePlusContractsOwnedByOtherGinsSharingSameValidEmailReturnZero() throws JrafDaoException {

        long result = roleContratsRepository.countFrequencePlusContractsOwnedByOtherGinsSharingSameValidEmail("moustapha.bengherbi@gmail.com","400368703835");
        Assert.assertTrue(result == 0);
    }
    
    @Test
    @Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void testCountFrequencePlusContractsOwnedByOtherGinsSharingSameValidEmailReturnPositive() throws JrafDaoException {

        long result = roleContratsRepository.countFrequencePlusContractsOwnedByOtherGinsSharingSameValidEmail("moustapha.bengherbi@gmail.com","900368703835");
        Assert.assertTrue(result > 0);
    }
    
    @Test
    public void testCountFrequencePlusContractsOwnedByGinsSharingSameValidEmailReturnZero() throws JrafDaoException {

        long result = roleContratsRepository.countFrequencePlusContractsOwnedByGinsSharingSameValidEmail("moustapha.bengherbi#gmail.com");
        Assert.assertTrue(result == 0);
    }
    
    @Test
    public void testCountFrequencePlusContractsOwnedByGinsSharingSameValidEmailReturnPositive() throws JrafDaoException {

        long result = roleContratsRepository.countFrequencePlusContractsOwnedByGinsSharingSameValidEmail("moustapha.bengherbi@gmail.com");
        Assert.assertTrue(result > 0);
    }

    @Test
    public void testFindSimplePropertiesByNumero() throws JrafDaoException {

        List<Map<String,?>> results = roleContratsRepository.findSimplePropertiesByNumero("002033904400");
        
        String gin = (String) results.get(0).get("gin");
        Assert.assertNotNull(gin);
        
        String typeContrat = (String) results.get(0).get("typeContrat");
        Assert.assertNotNull(typeContrat);
        
        String etat = (String) results.get(0).get("etat");
        Assert.assertNotNull(etat);
        
        Date dateFinValidite = (Date) results.get(0).get("dateFinValidite");
        Assert.assertNull(dateFinValidite);        
    }
/*    
    @Test
    public void testFindSimplePropertiesByGin() throws JrafDaoException {

        List<Map<String,?>> results = dao.findSimplePropertiesByGin("400280713205");
        
        String numeroContrat = (String) results.get(0).get("numeroContrat");
        Assert.notNull(numeroContrat);
        
        String typeContrat = (String) results.get(0).get("typeContrat");
        Assert.notNull(typeContrat);
        
        String etat = (String) results.get(0).get("etat");
        Assert.notNull(etat);
        
        Date dateFinValidite = (Date) results.get(0).get("dateFinValidite");
        Assert.notNull(dateFinValidite);
        
    }
*/    
    
    @Test
    public void testAssociationGinCinFound() {
    	
    	boolean exist = roleContratsRepository.isGinAndCinFbMaExist("400424668522", "001116447134");
    	
    	Assert.assertTrue(exist);
    }
    
    @Test
    public void testAssociationGinCinNotFound() {
    	
    	boolean exist = roleContratsRepository.isGinAndCinFbMaExist("400424668522", "1116447134");
    	
    	Assert.assertFalse(exist);
    }
}
